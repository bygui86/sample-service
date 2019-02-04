
# Base
FROM openjdk:11-jdk-slim

# Set the SHELL option -o pipefail before RUN with a pipe in
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Set environment variables
ENV JVM_OPTIMIZATIONS='-noverify -server -XX:TieredStopAtLevel=1'

# Expose ports
EXPOSE 8080 8090

# Set default working directory
WORKDIR /opt

# Change root password
RUN echo "root:cm9vdHNlY3JldA==" | chpasswd

# Define groups
RUN addgroup --system --gid 1002 id-micros-group
# Define users
RUN adduser --no-create-home --system -u 1001 --ingroup id-micros-group service-user

# Change owner of important folders
RUN chown -hR 1001:1002 /opt/

# Run as user different than root
USER 1001

# Copy jar inside image
COPY target/*service.jar /opt/app.jar

# Start application
ENTRYPOINT exec java $JVM_OPTIMIZATIONS -Xms$HEAP_SIZE -Xmx$HEAP_SIZE -XX:MaxMetaspaceSize=$META_SIZE $JAVA_OPTS -jar app.jar