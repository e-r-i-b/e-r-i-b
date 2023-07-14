/**
 * BackServiceImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.depo.generated;

import com.rssl.phizic.ws.esberiblistener.ESBListenerSingleton;
import com.rssl.phizic.ws.esberiblistener.depo.EsbEribDepoBackService;

public class BackServiceImpl implements com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_PortType{
    public java.lang.String doIFX(java.lang.String req) throws java.rmi.RemoteException {
	    EsbEribDepoBackService backService = ESBListenerSingleton.getEsbEribDepoBackServiceImpl();
	    return backService.doIFX(req);
    }

}
