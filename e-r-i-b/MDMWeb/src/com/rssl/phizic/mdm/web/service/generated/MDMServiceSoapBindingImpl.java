/**
 * MDMServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.mdm.web.service.generated;

import com.rssl.phizic.mdm.web.service.MDMServerService;

public class MDMServiceSoapBindingImpl implements com.rssl.phizic.mdm.web.service.generated.MDMService_PortType
{
	private static final MDMServerService SERVICE = new MDMServerService();

	public com.rssl.phizic.mdm.web.service.generated.ResponseType exec(com.rssl.phizic.mdm.web.service.generated.RequestType request) throws java.rmi.RemoteException
	{
        return SERVICE.process(request);
    }
}
