package com.reakaf.reactbackend.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${spring.data.cassandra.contactpoints}")
    private String contactPoints;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

//    @Override
//    public String[] getEntityBasePackages() {
//        return new String[]{"com.example.spring.reactive.cassandra.config.models"};
//    }

    @Override
    protected boolean getMetricsEnabled() { return false; }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Collections.singletonList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Collections.singletonList(DropKeyspaceSpecification.dropKeyspace(keyspace));
    }

    @Override
    protected List<String> getStartupScripts() {
        return Collections.singletonList("CREATE TABLE IF NOT EXISTS "+keyspace+".restaurant(id UUID PRIMARY KEY, name text, city text, noOfLikes int, isApproved boolean, dateCreated timestamp) WITH default_time_to_live = 600;");

        //return super.getStartupScripts();
//        return Collections.singletonList("CREATE TABLE IF NOT EXISTS " +
//                keyspace + ".restaurants(full_name text, " +
//                "date_of_birth timestamp, " +
//                "employee_id UUID, " +
//                "title text, " +
//                "department text, " +
//                "salary double, " +
//                "PRIMARY KEY ((full_name, date_of_birth), employee_id) " +
//                ") WITH default_time_to_live = 600;");
    }
}