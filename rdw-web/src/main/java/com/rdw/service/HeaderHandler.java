package com.rdw.service;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * HeaderHandler class is used to create Soap header for Webservice consuming in out application
 * Created by Mithilesh at 07/05/2017
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * Method handles the incoming soap messages and adds header to the soap messages
     *
     * @param smc of type SOAPMessageContext {@link SOAPMessageContext}
     * @return boolean
     */
    public boolean handleMessage(SOAPMessageContext smc) {

        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {

            SOAPMessage message = smc.getMessage();

            try {

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = message.getSOAPHeader();
                if (header == null) {
                    header = envelope.addHeader();
                }


                QName qNameUserCredentials = new QName("http://www.webservices.nl/soap/", "HeaderLogin");
                SOAPHeaderElement headerLogin =
                        header.addHeaderElement(qNameUserCredentials);


                QName qNameUserName = new QName("http://www.webservices.nl/soap/", "username");
                SOAPHeaderElement username =
                        header.addHeaderElement(qNameUserName);
                username.addTextNode("solicitant_User");

                QName qNamePassword = new QName("http://www.webservices.nl/soap/", "password");
                SOAPHeaderElement password =
                        header.addHeaderElement(qNamePassword);
                password.addTextNode("a1fefd74c35225f8eab80e2865dec014");

                headerLogin.addChildElement(username);
                headerLogin.addChildElement(password);

                message.saveChanges();
                //Print out the outbound SOAP message to System.out
                message.writeTo(System.out);
                System.out.println("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {

                //This handler does nothing with the response from the Web Service so
                //we just print out the SOAP message.
                SOAPMessage message = smc.getMessage();
                message.writeTo(System.out);
                System.out.println("");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        return outboundProperty;

    }

    /**
     * Method to ger Headers
     * @return
     */
    @Override
    public Set<QName> getHeaders() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    /**
     * Method to handle fault
     * @param context
     * @return boolean
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    /**
     * Method to close
     * @param context
     */
    @Override
    public void close(MessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}