package com.helloworld.restaurant.config;

import com.helloworld.restaurant.ws.lab.LabService;
import com.helloworld.restaurante.lab.LabWS;
import jakarta.xml.ws.BindingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LabSoapConfig {

    @Value("${soap.lab.endpoint}")
    private String labEndpoint;

    @Bean
    public LabWS labWSClient() {
        LabWS port = new LabService().getLabWSImplPort();
        ((BindingProvider) port).getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, labEndpoint);
        return port;
    }
}