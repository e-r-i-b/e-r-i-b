package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.web.util.PersonInfoUtil;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

/**
 * Скрытие сообщения о переезде мобильного банка в профиль.
 * @author Pankin
 * @ created 02.08.14
 * @ $Author$
 * @ $Revision$
 */
public class HideMobileBankResponseConstructor extends JAXBResponseConstructor<Request, SimpleResponse>
{
	protected SimpleResponse makeResponse(Request request) throws Exception
	{
		PersonInfoUtil.setMobileItemsMovedClosed();
		return new SimpleResponse();
	}
}
