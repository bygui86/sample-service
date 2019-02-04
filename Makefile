
# VARIABLES

# # general
BUILD_TOOL := ./mvnw
NAME := sample-service
JAR_FILE := $(shell find target -name '*.jar' 2>/dev/null)
PROTOCOL := http
PORT := 8183
METRICS_PORT := 8193
DEBUG_PORT := 5005
# # docker
DOCKER_IMAGE_NAME := microservices/$(NAME)
DOCKER_IMAGE_TAG := 0.0.1
DOCKER_IMAGE_PORT := $(PORT)
DOCKER_IMAGE_METRICS_PORT := $(METRICS_PORT)
DOCKER_IMAGE_DEBUG_PORT := $(DEBUG_PORT)
DOCKER_IMAGE_JMX_PORT := 3$(METRICS_PORT)
DOCKER_IMAGE_PORTS := -p $(DOCKER_IMAGE_PORT)\:$(DOCKER_IMAGE_PORT) -p $(DOCKER_IMAGE_METRICS_PORT)\:$(DOCKER_IMAGE_METRICS_PORT) -p $(DOCKER_IMAGE_DEBUG_PORT)\:$(DOCKER_IMAGE_DEBUG_PORT) -p $(DOCKER_IMAGE_JMX_PORT)\:$(DOCKER_IMAGE_JMX_PORT)
DOCKER_REGISTRY_HOST := localhost
DOCKER_REGISTRY_PORT := 5000
DOCKER_HOST_NAME := dockerhost
# # retrieving docker IP address on localhost
DOCKER_HOST_IP := $$(ifconfig en0 | grep inet | grep -v inet6 | awk '{print $$2}')
DOCKER_HOST_LOCAL_BRIDGE := --network bridge --add-host=$(DOCKER_HOST_NAME)\:$(DOCKER_HOST_IP)
# # sonarqube
SONAR_VERSION := 7.5-community
SONAR_PORTS := -p 9000\:9000
# # jvm
MEM_OPTS := -Xms128m -Xmx128m -XX:MaxMetaspaceSize=300m
JMX_OPTS := -Dcom.sun.management.jmxremote.port=$(DOCKER_IMAGE_JMX_PORT) -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
DEBUG_OPTS := -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$(DOCKER_IMAGE_DEBUG_PORT)
OTHER_OPTS := -Dspring.profiles.active=insecure,local,in-rest,out-mock
# # makefile
.PHONY: help
.DEFAULT_GOAL := help


# FUNCTIONS

# # building

dependencies :		## Show dependency tree
	$(BUILD_TOOL) dependency:tree -Dverbose

clean :		## Clean the application
	$(BUILD_TOOL) clean

compile :		## Compile the application
	$(BUILD_TOOL) compile

build :		## Build the application package including unit tests only
	$(BUILD_TOOL) -Dtest=*UnitTest install

build-full :		## Build the application package including unit tests only
	$(BUILD_TOOL) install


# # testing

test :		## Run all tests
	$(BUILD_TOOL) -DargLine='-Dspring.profiles.active=test,test-local,insecure,in-rest,out-rest' test

unit-test :		## Run Unit tests only
	$(BUILD_TOOL) -Dtest=*UnitTest test

integration-test :		## Run Integration tests only
	$(BUILD_TOOL) -Dtest=*IntegrationTest test

startup-test :		## Run Startup tests only
	$(BUILD_TOOL) -Dtest=*StartupTest test

contract-test :		## Run Contract tests only (surefire-plugin syntax for additional parameters)
	$(BUILD_TOOL) -Dtest=*ContractTest -DargLine='-Dspring.profiles.active=test,test-local,insecure' test


# # running

run :		## Run the application through Spring Boot plugin (spring-boot-plugin syntax for additional parameters)
	$(BUILD_TOOL) spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments='$(MEM_OPTS) $(JMX_OPTS) $(OTHER_OPTS)'

debug :		## Run the application in debug mode through Spring Boot plugin (spring-boot-plugin syntax for additional parameters)
	$(BUILD_TOOL) spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments='$(MEM_OPTS) $(JMX_OPTS) $(DEBUG_OPTS) $(OTHER_OPTS)'

java-run :		## Run the application through the generated fat-jar
	java $(MEM_OPTS) $(JMX_OPTS) $(OTHER_OPTS) -jar $(JAR_FILE)

java-debug :		## Run the application in debug mode through the generated fat-jar
	java $(MEM_OPTS) $(JMX_OPTS) $(DEBUG_OPTS) $(OTHER_OPTS) -jar $(JAR_FILE)


# # docker

docker-prepare :		## Prepare the application to be containerised
	$(BUILD_TOOL) -DfailIfNoTests=false clean package

docker-build :		## Build the docker image of the application
	docker build -f Dockerfile_local -t $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG) .

docker-run :		## Run the containerised application through docker
	@echo Docker host [$(DOCKER_HOST_NAME)] ip [$(DOCKER_IP)]
	docker run --rm -it --name $(NAME) $(DOCKER_IMAGE_PORTS) $(DOCKER_HOST_LOCAL_BRIDGE) $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

docker-run-deamon :		## Run the containerised application as deamon through docker
	@echo Docker host [$(DOCKER_HOST_NAME)] ip [$(DOCKER_IP)]
	docker run --rm -d --name $(NAME) $(DOCKER_IMAGE_PORTS) $(DOCKER_HOST_LOCAL_BRIDGE) $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

docker-stop :		## Stop the containerised application
	docker container stop $(NAME)

docker-kill :		## Kill the containerised application
	docker container kill $(NAME)

docker-delete-local : docker-kill		## Delete the docker image of the application
	docker container rm -f $(NAME)
	docker image rm -f $(NAME)

docker-push : docker-build		## Push the docker application to the docker registry
	@echo Docker Registry host [$(DOCKER_REGISTRY_HOST)] ip [$(DOCKER_REGISTRY_PORT)]
	docker push $(DOCKER_REGISTRY_HOST):$(DOCKER_REGISTRY_PORT)/$(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

docker-delete-remote : docker-stop		## Delete the docker image of the application from the docker registry
	@echo Docker Registry host [$(DOCKER_REGISTRY_HOST)] ip [$(DOCKER_REGISTRY_PORT)]
	docker image rm $(DOCKER_REGISTRY_HOST):$(DOCKER_REGISTRY_PORT)/$(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

docker-file-check :		## Verify Dockerfile with hadolint
	docker run --rm -i hadolint/hadolint < Dockerfile_local


# # pmd - source code analyser

pmd-run :		## Run a source code analysis based on PMD
	$(BUILD_TOOL) pmd:pmd

pmd-check :		## Verifies that the PMD report is empty and fails the build if it is not
	$(BUILD_TOOL) pmd:check


# # findbugs - static analysis

findbugs-run :		## Run a static analysis based on FindBugs
	$(BUILD_TOOL) findbugs:findbugs xslt:transform

findbugs-check :		## Verifies FindBugs report
	$(BUILD_TOOL) findbugs:check


# # checkstyle - code analysis

checkstyle-run :		## Run a code analysis based on Checkstyle
	$(BUILD_TOOL) checkstyle:checkstyle

checkstyle-check :		## Verifies Checkstyle report
	$(BUILD_TOOL) checkstyle:check


# # maven surefire report - testing report

# WARNING: before running the report, maven executes all tests
surefire-report-run :		## Build the testing report based on Maven Surefire
	$(BUILD_TOOL) -DargLine='-Dspring.profiles.active=test,test-local,insecure,in-rest,out-rest' surefire-report:report

surefire-report-run-no-tests :		## Build the testing report based on Maven Surefire (this goal does not run the tests)
	$(BUILD_TOOL) surefire-report:report-only


# # jacoco - code coverage

jacoco-run :		## Generate a code coverage report based on Jacoco
	$(BUILD_TOOL) jacoco:report

jacoco-check :		## Checks that the code coverage metrics are being met
	$(BUILD_TOOL) jacoco:check


# # pitest - mutation testing

pitest-run :		## Run a mutation testing session based on Pitest
	$(BUILD_TOOL) pitest:mutationCoverage


# # sonarqube - quality analysis dashboard

sonar-server :		## Start SonarQube server
	docker run -d --name sonarqube $(SONAR_PORTS) sonarqube:$(SONAR_VERSION)

sonar-run :		## Run a code quality analysis based on SonarQube
	$(BUILD_TOOL) sonar:sonar -Dsonar.host.url=http://localhost:9000


# # general

help :		## Help
	@echo ""
	@echo "*** $(NAME) Makefile help ***"
	@echo ""
	@echo "Targets list:"
	@grep -E '^[a-zA-Z_-]+ :.*?## .*$$' $(MAKEFILE_LIST) | sort -k 1,1 | awk 'BEGIN {FS = ":.*?## "}; {printf "\t\033[36m%-30s\033[0m %s\n", $$1, $$2}'
	@echo ""

print-variables :		## Print variables values
	@echo "- - - makefile - - -"
	@echo "MAKE: $(MAKE)"
	@echo "MAKEFILES: $(MAKEFILES)"
	@echo "MAKEFILE_LIST: $(MAKEFILE_LIST)"
	@echo "- - - "
	@echo "- - - general - - -"
	@echo "BUILD_TOOL: $(BUILD_TOOL)"
	@echo "NAME: $(NAME)"
	@echo "JAR_FILE: $(JAR_FILE)"
	@echo "PROTOCOL: $(PROTOCOL)"
	@echo "PORT: $(PORT)"
	@echo "METRICS_PORT: $(METRICS_PORT)"
	@echo "DEBUG_PORT: $(DEBUG_PORT)"
	@echo "- - - "
	@echo "- - - docker - - -"
	@echo "DOCKER_IMAGE_NAME: $(DOCKER_IMAGE_NAME)"
	@echo "DOCKER_IMAGE_TAG: $(DOCKER_IMAGE_TAG)"
	@echo "DOCKER_IMAGE_PORT: $(DOCKER_IMAGE_PORT)"
	@echo "DOCKER_IMAGE_METRICS_PORT: $(DOCKER_IMAGE_METRICS_PORT)"
	@echo "DOCKER_IMAGE_DEBUG_PORT: $(DOCKER_IMAGE_DEBUG_PORT)"
	@echo "DOCKER_IMAGE_JMX_PORT: $(DOCKER_IMAGE_JMX_PORT)"
	@echo "DOCKER_DOCKER_IMAGE_PORTS: $(DOCKER_DOCKER_IMAGE_PORTS)"
	@echo "DOCKER_REGISTRY_HOST: $(DOCKER_REGISTRY_HOST)"
	@echo "DOCKER_REGISTRY_PORT: $(DOCKER_REGISTRY_PORT)"
	@echo "DOCKER_HOST_NAME: $(DOCKER_HOST_NAME)"
	@echo "DOCKER_HOST_IP: $(DOCKER_HOST_IP)"
	@echo "- - - "
	@echo "- - - sonarqube - - -"
	@echo "SONAR_VERSION: $(SONAR_VERSION)"
	@echo "SONAR_PORTS: $(SONAR_PORTS)"
	@echo "- - - "
	@echo "- - - jvm - - -"
	@echo "MEM_OPTS: $(MEM_OPTS)"
	@echo "JMX_OPTS: $(JMX_OPTS)"
	@echo "DEBUG_OPTS: $(DEBUG_OPTS)"
	@echo "OTHER_OPTS: $(OTHER_OPTS)"
	@echo "- - - "
	@echo ""


# LINKS

# Makefile manual	https://www.gnu.org/software/make/manual/make.html
# JVM arguments		https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/maven-plugin/run-mojo.html#jvmArguments
