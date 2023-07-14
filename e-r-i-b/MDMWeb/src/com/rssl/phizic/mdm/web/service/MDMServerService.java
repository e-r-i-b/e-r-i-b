package com.rssl.phizic.mdm.web.service;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.mdm.web.service.generated.RequestType;
import com.rssl.phizic.mdm.web.service.generated.ResponseData;
import com.rssl.phizic.mdm.web.service.generated.ResponseType;
import com.rssl.phizic.mdm.web.service.generated.StatusType;
import com.rssl.phizic.mdm.web.service.processors.MessageType;
import com.rssl.phizic.mdm.web.service.processors.ProcessorBase;
import com.rssl.phizic.mdm.web.service.processors.ProcessorException;

/**
 * @author akrenev
 * @ created 08.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Серверная часть сервиса работы с приложением МДМ
 */

public final class MDMServerService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final int UNKNOWN_STATE_CODE = -10;
	private static final String UNKNOWN_STATE_DESCRIPTION = "Неизвестный тип запроса.";

	/**
	 * обработать запрос
	 * @param request -- данные запроса
	 * @return ответ
	 */
	public final ResponseType process(RequestType request)
	{
		ResponseType response = new ResponseType();

		response.setRqUID(request.getRqUID());
		response.setRqTm(request.getRqTm());
		response.setOperUID(request.getOperUID());

		MessageType messageType = MessageType.getType(request.getData());

		if (messageType == MessageType.unknown)
			return processUnknownState(response);

		try
		{
			ProcessorBase processor = messageType.getProcessor();
			ResponseData responseData = new ResponseData();
			response.setData(responseData);
			processor.process(request.getData(), responseData);
			return processOkState(response);
		}
		catch (ProcessorException e)
		{
			log.error("Ошибка обработки запроса " + getRequestIdentifier(request, messageType) + " в приложение мдм.", e);
			return processErrorState(response, e);
		}
	}

	private String getRequestIdentifier(RequestType request, MessageType messageType)
	{
		return "[RqUID=" + request.getRqUID() + ";RqTm=" + request.getRqTm() + ";OperUID=" + request.getOperUID() + ";messageType=" + messageType + "]";
	}

	private ResponseType processOkState(ResponseType response)
	{
		return processState(response, 0, null);
	}

	private ResponseType processErrorState(ResponseType response, ProcessorException exception)
	{
		return processState(response, exception.getErrorCode(), exception.getErrorDescription());
	}

	private ResponseType processUnknownState(ResponseType response)
	{
		return processState(response, UNKNOWN_STATE_CODE, UNKNOWN_STATE_DESCRIPTION);
	}

	private ResponseType processState(ResponseType response, long code, String description)
	{
		StatusType status = new StatusType();
		response.setStatus(status);
		status.setStatusCode(code);
		status.setStatusDesc(description);
		return response;
	}
}
