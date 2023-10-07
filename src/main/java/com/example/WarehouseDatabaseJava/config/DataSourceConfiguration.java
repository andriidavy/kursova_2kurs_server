package com.example.WarehouseDatabaseJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

//for more details https://stackoverflow.com/questions/30337582/spring-boot-configure-and-use-two-data-sources

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource1")
    public DataSource dataSource1(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource2")
    public DataSource dataSource2(){
        return DataSourceBuilder.create().build();
    }

    //for using @Transactional this will use the first datasource because it is @primary
    @Bean(name="tm1")
    @Autowired
    @Primary
    DataSourceTransactionManager tm1(@Qualifier ("dataSource1") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }

    //for using @Transactional("tm2")
    @Bean(name="tm2")
    @Autowired
    DataSourceTransactionManager tm2(@Qualifier ("dataSource2") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }

    //if you want a method to commit/rollback transactions of both databases,
    //you need ChainedTransactionManager for tm1 and tm2 , like this:
    //To use it, add this annotation in a method @Transactional(value="chainedTransactionManager")
    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager getChainedTransactionManager(@Qualifier ("tm1") DataSourceTransactionManager tm1, @Qualifier ("tm2") DataSourceTransactionManager tm2){
        return new ChainedTransactionManager(tm1, tm2);
    }
}
