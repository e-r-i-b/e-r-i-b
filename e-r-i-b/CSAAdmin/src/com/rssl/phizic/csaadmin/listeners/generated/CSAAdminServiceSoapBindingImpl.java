/**
 * CSAAdminServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class CSAAdminServiceSoapBindingImpl implements com.rssl.phizic.csaadmin.listeners.generated.CSAAdminService_PortType
{
    private static final com.rssl.phizic.csaadmin.listeners.CSAAdminService service = new com.rssl.phizic.csaadmin.listeners.CSAAdminService();

	public ResponseType exec(RequestType request) throws java.rmi.RemoteException
	{
        return service.exec(request);
    }
}
