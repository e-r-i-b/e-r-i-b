package com.rssl.phizicgate.esberibgate.insurance;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.InsuranceService;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.messaging.InsuranceResponseSerializer;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InsuranceServiceImpl extends AbstractService implements InsuranceService
{
	private InsuranceResponseSerializer respSerializer = new InsuranceResponseSerializer();
	private InsuranceAppRequestHelper requestHelper = new InsuranceAppRequestHelper(getFactory());

	protected InsuranceServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<InsuranceApp> getInsuranceList(Client client) throws GateException, GateLogicException
	{
		List<? extends ClientDocument> documents = client.getDocuments();

		ClientDocument passportRF = null;
		for (ClientDocument document : documents)
		{
			if (document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
			{
				passportRF = document;
				break;
			}
		}

		List<InsuranceApp> result = new ArrayList<InsuranceApp>();
		if (passportRF != null)
		{
			IFXRq_Type ifxRq_type =  requestHelper.createInsuranceListRq(client, passportRF);
			IFXRs_Type ifxRs = getRequest(ifxRq_type);
			result = respSerializer.fillInsurance(ifxRq_type, ifxRs, client.getInternalOwnerId());
		}
		return result;
	}

	public InsuranceApp getInsuranceApp(String externalId)  throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = requestHelper.createInsuranceAppRq(externalId);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		return respSerializer.getInsuranceApp(ifxRq, ifxRs, EntityIdHelper.getInsuranceCompositeId(externalId).getLoginId());
	}
}
