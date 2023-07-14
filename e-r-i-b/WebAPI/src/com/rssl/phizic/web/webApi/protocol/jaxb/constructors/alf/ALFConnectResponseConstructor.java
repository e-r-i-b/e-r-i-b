package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.phizic.operations.finances.ChangePersonalFinanceAccessOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFStatusResponse;

/**
 * ��������� ����� �� ������ ������� ����������� ���
 * @author Jatsky
 * @ created 13.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFConnectResponseConstructor extends ALFBaseResponseConstructor<Request, ALFStatusResponse>
{
	@Override protected ALFStatusResponse makeResponse(Request request) throws Exception
	{
		ALFStatusResponse response = new ALFStatusResponse();
		ChangePersonalFinanceAccessOperation accessOperation = createOperation(ChangePersonalFinanceAccessOperation.class, "AddFinanceOperationsService");
		// ���������� �������
		accessOperation.initialize();
		accessOperation.setShowPersonalFinance(true);
		accessOperation.save();

		// ��������� ������
		fillALFStatus(response);
		return response;
	}
}
