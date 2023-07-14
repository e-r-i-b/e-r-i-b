package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.InvalidSessionException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SessionContextOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author vagin
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *  Базовый класс обработчика запроса, выполняющегося в контексте сессии. С лоигрованием действий идентифицированного пользователя.
 */
public abstract class LogableSessionContextProcessorBase<T extends SessionContextOperation> extends LogableProcessorBase
{
	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext identificationContext) throws Exception
	{
		Document body = requestInfo.getBody();
		String sid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.SID_TAG);
		trace("обрабатываем запрос для профиля" + identificationContext.getProfile().getId());
		try
		{
			return new LogableResponseInfo(processRequest(createOperation(identificationContext, sid), requestInfo));
		}
		catch (InvalidSessionException e)
		{
			error("ошибка обработки запроса", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBadSIDResponse(), e);
		}
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String sid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.SID_TAG);
		return LogIdentificationContext.createBySessionId(sid);
	}

	protected abstract T createOperation(IdentificationContext identificationContext, String sid) throws Exception;

	protected abstract ResponseInfo processRequest(T operation, RequestInfo requestInfo) throws Exception;
}
