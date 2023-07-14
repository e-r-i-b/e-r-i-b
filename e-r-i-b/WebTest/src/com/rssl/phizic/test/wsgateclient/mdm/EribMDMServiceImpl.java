package com.rssl.phizic.test.wsgateclient.mdm;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.test.wsgateclient.mdm.generated.*;
import org.apache.axis.client.Stub;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author egorova
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class EribMDMServiceImpl
{
	private static final String URL_MDM_UPSTREAM = "http://localhost:8888/ESBERIBListener/axis-services/eribMDMService";

	private IFXRs_Type getRequest(MDMClientInfoUpdateRq_Type mdmRq) throws GateException
	{
		try
		{
			EribMDM_ServiceLocator service = new EribMDM_ServiceLocator();
			EribMDMBindingStub stub = (EribMDMBindingStub) service.geteribMDMService();
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, URL_MDM_UPSTREAM);
			return stub.doIFX(new IFXRq_Type(mdmRq));
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	public void updateClientInfo(ActivePerson person) throws GateException
	{
		ClientRequestHelper helper = new ClientRequestHelper();
		MDMClientInfoUpdateRq_Type mdmRq = helper.getClientInfo(person);
		IFXRs_Type rs = getRequest(mdmRq);
		Status_Type status = rs.getMDMClientInfoUpdateRs().getStatus();
		if (status.getStatusCode() == 0L)
			return;
		throw new GateException(status.getStatusDesc());

	}
}
