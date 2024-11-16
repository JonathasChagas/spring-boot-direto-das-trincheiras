package academy.devdojo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class ConnectionBeanConfiguration {
    private final ConnectionConfigurationProperties connectionConfigurationProperties;

    @Bean
    @Primary
    public Connection connectionMySql() {
        return new Connection(connectionConfigurationProperties.url(),
                connectionConfigurationProperties.username(),
                connectionConfigurationProperties.password());
    }

    @Bean
    @Profile("mongo")
    public Connection connectionMongo() {
        return new Connection(connectionConfigurationProperties.url(),
                connectionConfigurationProperties.username(),
                connectionConfigurationProperties.password());
    }
}
