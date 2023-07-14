package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.phizic.operations.finances.ChangePersonalFinanceAccessOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFStatusResponse;

/**
 * Заполняет ответ на запрос выписки подключения АЛФ
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
		// подключаем клиента
		accessOperation.initialize();
		accessOperation.setShowPersonalFinance(true);
		accessOperation.save();

		// обновляем заявки
		fillALFStatus(response);
		return response;
	}
}
