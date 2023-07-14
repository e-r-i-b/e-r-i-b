/**
 * IService1BindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.ryazan.axis;

import com.rssl.phizic.test.webgate.ryazan.common.MessageUpdate;

import java.rmi.RemoteException;

public class IService1BindingImpl implements com.rssl.phizic.test.webgate.ryazan.axis.generated.IService1_PortType
{
    public String echoString(String sendMessageRequest) throws RemoteException
	{
		return sendMessageRequest;
	}

	public String requestAttr(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().requestAttr(sendMessageRequest);
	}

	public String preparePayment(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().preparePayment(sendMessageRequest);
	}

	public String executePayment(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().executePayment(sendMessageRequest);
	}

	public String revokePayment(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().revokePayment(sendMessageRequest);
	}

}
