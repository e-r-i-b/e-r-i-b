package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.log.CSAActionLogHelper;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Базовый класс обработки запроса с логированием  действий идентифицированного пользователя.
 */
public abstract class LogableProcessorBase extends RequestProcessorBase
{
	protected final ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		LogIdentificationContext context = getIdentificationContext(requestInfo);

        try
        {
            LogableResponseInfo responseInfo = processRequest(requestInfo, context.getIdentificationContext());
	        CSAActionLogHelper.writeToActionLog(requestInfo, context, responseInfo.getError());
            return responseInfo.getResponseInfo();
        }
        catch (Exception e)
        {
	        exceptionProcessing(context, requestInfo);
	        CSAActionLogHelper.writeToActionLog(requestInfo, context, e);
            throw e;
        }
	}

	/**
	 * Обработка исключительной ситуации
	 * @param context - идентификационный контекст
	 * @param requestInfo - запрос из Front
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void exceptionProcessing(LogIdentificationContext context, RequestInfo requestInfo) throws GateLogicException, GateException {};

	protected abstract LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception;

	protected abstract LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception;
}
