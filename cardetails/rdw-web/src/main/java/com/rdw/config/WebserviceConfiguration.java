package com.rdw.config;

import com.rdw.service.HeaderHandlerResolver;
import nl.webservices.soap.WebservicesNlPortType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mithilesh Ravindran on 5/7/2017.
 * Webservice Configuration class is used to create bean to consume the WebservicesNl soap service
 */
@Configuration
public class WebserviceConfiguration {

    /**
     * Webservice nl bean creation is done that can be used across the application for consuming the services
     *
     * @return WebservicesNlPortType
     */
    @Bean
    public WebservicesNlPortType carRDWData() {
        final String WS_URL = "https://ws1.webservices.nl/soap_doclit";
        URL url = null;
        try {
            url = new URL(WS_URL);
        } catch (MalformedURLException e) {

        }
        QName qname = new QName("http://www.webservices.nl/soap/", "Webservices.nl");

        Service service = Service.create(url, qname);
        HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
        service.setHandlerResolver(handlerResolver);

        WebservicesNlPortType webservicesNlPortType = service.getPort(WebservicesNlPortType.class);
        return webservicesNlPortType;
    }
}
