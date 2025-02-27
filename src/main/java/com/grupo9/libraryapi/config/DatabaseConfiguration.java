package com.grupo9.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    public DataSource dataSource() {

        // Configuração do HikariCP

        HikariConfig hikariConfig = new HikariConfig();

        // Configuração das propriedades do datasource
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/library");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        // Configurações adicionais (opcionais)
        hikariConfig.setMaximumPoolSize(10); // Número máximo de conexões no pool
        hikariConfig.setIdleTimeout(30000);  // Tempo de inatividade para fechar a conexão
        hikariConfig.setConnectionTimeout(30000); // Tempo para aguardar uma conexão
        hikariConfig.setPoolName("HikariCP-Postgres");

        // Retorna a instância do HikariDataSource configurado
        return new HikariDataSource(hikariConfig);
    }
}
