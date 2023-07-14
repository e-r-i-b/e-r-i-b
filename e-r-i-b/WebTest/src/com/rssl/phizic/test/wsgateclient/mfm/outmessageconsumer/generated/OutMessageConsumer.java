/**
 * OutMessageConsumer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated;

public interface OutMessageConsumer extends java.rmi.Remote {
    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[] consumeOutMessage(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault;
    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[] findOutMessage(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault;
}
