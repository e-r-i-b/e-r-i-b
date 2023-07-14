/**
 * AdapterInfoServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.multiblock.adapterinfo.generated;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.manager.routing.AdapterService;

public class AdapterInfoServiceSoapBindingImpl implements com.rssl.phizic.multiblock.adapterinfo.generated.AdapterInfoService{

	private static final AdapterService adapterService = new AdapterService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);
	private static final long STATUS_OK = 0;
	private static final long STATUS_ERROR = -1;

    public com.rssl.phizic.multiblock.adapterinfo.generated.AdapterInfoRs adapterInfo(com.rssl.phizic.multiblock.adapterinfo.generated.AdapterInfoRq parameters) throws java.rmi.RemoteException {
        AdapterInfoRs rs = new AdapterInfoRs();
        try
        {
            rs.setAddress(adapterService.getAdapterListenerUrl(parameters.getUuid()));
            rs.setStatus(new StatusType(STATUS_OK, null));
        }
        catch (GateException e)
        {
            log.error(e.getMessage(), e);
            rs.setStatus(new StatusType(STATUS_ERROR, e.getMessage()));
        }
        return rs;
    }

}
