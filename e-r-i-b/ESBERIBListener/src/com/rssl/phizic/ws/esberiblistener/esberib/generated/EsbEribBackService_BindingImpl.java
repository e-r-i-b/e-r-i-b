/**
 * EsbEribBackService_BindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.esberib.generated;

import com.rssl.phizic.ws.esberiblistener.ESBListenerSingleton;
import com.rssl.phizic.ws.esberiblistener.esberib.EsbEribBackService;

public class EsbEribBackService_BindingImpl implements com.rssl.phizic.ws.esberiblistener.esberib.generated.EsbEribBackService_PortType{
    public com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRs_Type doIFX(com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRq_Type parameters) throws java.rmi.RemoteException {
	    EsbEribBackService impl = ESBListenerSingleton.getEsbEribBackServiceImpl();
        return impl.doIFX(parameters);
    }

}
