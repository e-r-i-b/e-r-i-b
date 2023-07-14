package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFStatusResponse;

/**
 * Заполняет ответ на запрос статуса АЛФ
 * @author Jatsky
 * @ created 12.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFStatusResponseConstructor extends ALFBaseResponseConstructor<Request, ALFStatusResponse>
{
	@Override protected ALFStatusResponse makeResponse(Request request) throws Exception
	{
		ALFStatusResponse response = new ALFStatusResponse();
		fillALFStatus(response);
		return response;
	}
}
