/**
 * EribMDMBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.mdm.generated;

import com.rssl.phizic.ws.esberiblistener.mdm.ClientResponseSerializer;

public class EribMDMBindingImpl implements com.rssl.phizic.ws.esberiblistener.mdm.generated.EribMDM_PortType{
    public com.rssl.phizic.ws.esberiblistener.mdm.generated.IFXRs_Type doIFX(com.rssl.phizic.ws.esberiblistener.mdm.generated.IFXRq_Type parameters) throws java.rmi.RemoteException {
	    ClientResponseSerializer serializer = new ClientResponseSerializer();
	    return serializer.updatePerson(parameters.getMDMClientInfoUpdateRq());
    }

}
