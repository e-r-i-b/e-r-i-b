package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

/**
 * Формирует пустой ответ
 * @author Jatsky
 * @ created 08.05.14
 * @ $Author$
 * @ $Revision$
 */

public class SimpleResponseConstructor extends JAXBResponseConstructor<Request, SimpleResponse>
{
	@Override protected SimpleResponse makeResponse(Request request) throws Exception
	{
		return new SimpleResponse(new Status(StatusCode.SUCCESS));
	}
}
