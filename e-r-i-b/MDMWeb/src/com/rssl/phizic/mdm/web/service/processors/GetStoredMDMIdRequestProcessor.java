package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.mdm.web.service.generated.GetStoredMDMIdParametersType;
import com.rssl.phizic.mdm.web.service.generated.GetStoredMDMIdResultType;
import com.rssl.phizic.mdm.web.service.generated.RequestData;
import com.rssl.phizic.mdm.web.service.generated.ResponseData;
import com.rssl.phizicgate.mdm.operations.GetStoredMDMIdOperation;
import com.rssl.phizicgate.mdm.operations.Operation;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������ mdm_id � ��
 */

public class GetStoredMDMIdRequestProcessor extends ProcessorBase<GetStoredMDMIdParametersType>
{
	@Override
	public GetStoredMDMIdParametersType getData(RequestData requestData)
	{
		return requestData.getGetStoredMDMIdRq();
	}

	@Override
	public void process(GetStoredMDMIdParametersType source, ResponseData destination) throws ProcessorException
	{
		try
		{
			Operation<Long, String> operation = new GetStoredMDMIdOperation();
			operation.initialize(source.getInnerId());
			String result = operation.execute();
			destination.setGetStoredMDMIdRs(new GetStoredMDMIdResultType(result));
		}
		catch (GateLogicException e)
		{
			processError("������ ��������� mdm_id ������� � ��������������� " + source.getInnerId() + " � ��.", e);
		}
		catch (GateException e)
		{
			processError("������ ��������� mdm_id ������� � ��������������� " + source.getInnerId() + " � ��.", e);
		}
	}
}
