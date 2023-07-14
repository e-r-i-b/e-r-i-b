/**
 * PfrDoneServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.pfr.generated;

import com.rssl.phizic.ws.esberiblistener.ESBListenerSingleton;
import com.rssl.phizic.ws.esberiblistener.pfr.EsbEribPFRBackService;

public class PfrDoneServiceSoapBindingImpl implements com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneService{

	public com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRsType pfrDone(com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRqType parameters) throws java.rmi.RemoteException
    {
	    EsbEribPFRBackService impl = ESBListenerSingleton.getEsbEribPFRBackServiceImpl();
	    return impl.pfrDone(parameters);
    }
}
