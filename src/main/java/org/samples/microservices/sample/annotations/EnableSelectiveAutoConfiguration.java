package org.samples.microservices.sample.annotations;

import org.springframework.boot.actuate.autoconfigure.amqp.RabbitHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.cassandra.CassandraHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.reactive.ReactiveCloudFoundryActuatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet.CloudFoundryActuatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.couchbase.CouchbaseHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.flyway.FlywayEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.influx.InfluxDbHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.jms.JmsHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.jolokia.JolokiaEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ldap.LdapHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.liquibase.LiquibaseEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.mail.MailHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.amqp.RabbitMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.atlas.AtlasMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.ganglia.GangliaMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.graphite.GraphiteMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic.NewRelicMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.signalfx.SignalFxMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.statsd.StatsdMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.wavefront.WavefrontMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.neo4j.Neo4jHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableAutoConfiguration(
		exclude = {
				RabbitHealthIndicatorAutoConfiguration.class,
				CassandraHealthIndicatorAutoConfiguration.class,
				ReactiveCloudFoundryActuatorAutoConfiguration.class,
				CloudFoundryActuatorAutoConfiguration.class,
				CouchbaseHealthIndicatorAutoConfiguration.class,
				FlywayEndpointAutoConfiguration.class,
				InfluxDbHealthIndicatorAutoConfiguration.class,
				JmsHealthIndicatorAutoConfiguration.class,
				JolokiaEndpointAutoConfiguration.class,
				LdapHealthIndicatorAutoConfiguration.class,
				LiquibaseEndpointAutoConfiguration.class,
				MailHealthIndicatorAutoConfiguration.class,
				RabbitMetricsAutoConfiguration.class,
				AtlasMetricsExportAutoConfiguration.class,
				GangliaMetricsExportAutoConfiguration.class,
				GraphiteMetricsExportAutoConfiguration.class,
				NewRelicMetricsExportAutoConfiguration.class,
				SignalFxMetricsExportAutoConfiguration.class,
				StatsdMetricsExportAutoConfiguration.class,
				WavefrontMetricsExportAutoConfiguration.class,
				Neo4jHealthIndicatorAutoConfiguration.class,
				BatchAutoConfiguration.class,
				CassandraAutoConfiguration.class,
				CouchbaseAutoConfiguration.class,
				CassandraDataAutoConfiguration.class,
				CassandraReactiveDataAutoConfiguration.class,
				CassandraReactiveRepositoriesAutoConfiguration.class,
				CassandraRepositoriesAutoConfiguration.class,
				CouchbaseDataAutoConfiguration.class,
				CouchbaseReactiveDataAutoConfiguration.class,
				CouchbaseReactiveRepositoriesAutoConfiguration.class,
				CouchbaseRepositoriesAutoConfiguration.class,
				ElasticsearchAutoConfiguration.class,
				ElasticsearchDataAutoConfiguration.class,
				ElasticsearchRepositoriesAutoConfiguration.class,
				Neo4jDataAutoConfiguration.class,
				Neo4jRepositoriesAutoConfiguration.class,
				JestAutoConfiguration.class,
				FlywayAutoConfiguration.class,
				FreeMarkerAutoConfiguration.class,
				GsonAutoConfiguration.class,
				HazelcastAutoConfiguration.class,
				HazelcastJpaDependencyAutoConfiguration.class,
				InfluxDbAutoConfiguration.class,
				JdbcTemplateAutoConfiguration.class,
				JndiDataSourceAutoConfiguration.class,
				JerseyAutoConfiguration.class,
				JmsAutoConfiguration.class,
				JndiConnectionFactoryAutoConfiguration.class,
				ActiveMQAutoConfiguration.class,
				ArtemisAutoConfiguration.class,
				JooqAutoConfiguration.class,
				JsonbAutoConfiguration.class,
				LiquibaseAutoConfiguration.class,
				MailSenderAutoConfiguration.class,
				MailSenderValidatorAutoConfiguration.class,
				MustacheAutoConfiguration.class,
				SendGridAutoConfiguration.class,
				JtaAutoConfiguration.class
		}
)
public @interface EnableSelectiveAutoConfiguration {

	// no-op
}
