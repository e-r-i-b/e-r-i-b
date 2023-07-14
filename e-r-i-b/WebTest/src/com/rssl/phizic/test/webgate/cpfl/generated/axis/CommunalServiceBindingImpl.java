/**
 * CommunalServiceBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.cpfl.generated.axis;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.cpfl.mock.MockRequestProcessor;

import java.rmi.RemoteException;

public class CommunalServiceBindingImpl implements com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServicePT{
    public java.lang.String sendMessage(java.lang.String sendMessageRequest) throws java.rmi.RemoteException {
	    try
	    {
		    return MockRequestProcessor.processMessage(sendMessageRequest);
	    }
	    catch (GateException e)
	    {
		    throw new RemoteException("Ошибка при обработке запроса", e);
	    }
    }
}
