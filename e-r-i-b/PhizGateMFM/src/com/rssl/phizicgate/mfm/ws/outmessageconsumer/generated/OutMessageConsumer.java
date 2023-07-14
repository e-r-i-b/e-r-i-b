/**
 * OutMessageConsumer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated;

public interface OutMessageConsumer extends java.rmi.Remote {
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.ConsumeOutMessageResult[] consumeOutMessage(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.ConsumeOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessageConsumerFault;
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.FindOutMessageResult[] findOutMessage(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.FindOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessageConsumerFault;
}
