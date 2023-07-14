package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.mdm.web.service.generated.GetExternalSystemMDMIdParametersType;
import com.rssl.phizic.mdm.web.service.generated.GetExternalSystemMDMIdResultType;
import com.rssl.phizic.mdm.web.service.generated.RequestData;
import com.rssl.phizic.mdm.web.service.generated.ResponseData;
import com.rssl.phizicgate.mdm.operations.GetExternalSystemMDMIdOperation;
import com.rssl.phizicgate.mdm.operations.Operation;
import com.rssl.phizicgate.mdm.common.SearchClientInfo;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Процессор поиска mdm_id во вншней системе
 */

public class GetExternalSystemMDMIdRequestProcessor extends ProcessorBase<GetExternalSystemMDMIdParametersType>
{
	@Override
	public GetExternalSystemMDMIdParametersType getData(RequestData requestData)
	{
		return requestData.getGetExternalSystemMDMIdRq();
	}

	@Override
	public void process(GetExternalSystemMDMIdParametersType source, ResponseData destination) throws ProcessorException
	{
		try
		{
			Operation<SearchClientInfo, String> operation = new GetExternalSystemMDMIdOperation();
			operation.initialize(getClientInfo(source));
			String result = operation.execute();
			destination.setGetExternalSystemMDMIdRs(new GetExternalSystemMDMIdResultType(result));
		}
		catch (GateLogicException e)
		{
			processError("Ошибка получения mdm_id клиента с идентификатором " + source.getInnerId() + " во внешней системе.", e);
		}
		catch (GateException e)
		{
			processError("Ошибка получения mdm_id клиента с идентификатором " + source.getInnerId() + " во внешней системе.", e);
		}
	}

	private SearchClientInfo getClientInfo(GetExternalSystemMDMIdParametersType source)
	{
		SearchClientInfo clientInfo = new SearchClientInfo();
		clientInfo.setInnerId(source.getInnerId());
		clientInfo.setLastName(source.getLastName());
		clientInfo.setFirstName(source.getFirstName());
		clientInfo.setMiddleName(source.getMiddleName());
		clientInfo.setBirthday(parseCalendar(source.getBirthday()));
		clientInfo.setCardNum(source.getCardNum());
		clientInfo.setDocumentSeries(source.getDocumentSeries());
		clientInfo.setDocumentNumber(source.getDocumentNumber());
		clientInfo.setDocumentType(ClientDocumentType.valueOf(source.getDocumentType()));
		return clientInfo;
	}
}
