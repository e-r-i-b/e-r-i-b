package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.InvalidSessionException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SessionContextOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 08.11.2012
 * @ $Author$
 * @ $Revision$
 * Ѕазовый класс обработчика запроса, выполн€ющегос€ в контексте сессии
 */
public abstract class SessionContextRequestProcessorBase<T extends SessionContextOperation> extends RequestProcessorBase
{
	protected final ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String sid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.SID_TAG);
		final IdentificationContext identificationContext =  IdentificationContext.createBySessionId(sid);
		trace("обрабатываем запрос дл€ профил€" + identificationContext.getProfile().getId());
		return processRequest(createOperation(identificationContext, sid), requestInfo);
	}

	protected abstract T createOperation(IdentificationContext identificationContext, String sid) throws Exception;

	protected abstract ResponseInfo processRequest(T operation, RequestInfo requestInfo) throws Exception;
}
