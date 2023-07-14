package com.rssl.phizic.web.gate.services.documents;

import com.rssl.phizic.web.gate.services.documents.types.GateDocument;

import java.rmi.Remote;
import java.util.Map;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public interface CommissionCalculator_PortType extends Remote
{
	void setParameters(Map<String, ?> params)  throws java.rmi.RemoteException;

	void calcCommission(GateDocument transfer) throws java.rmi.RemoteException;
}
