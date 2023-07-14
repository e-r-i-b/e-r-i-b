package com.rssl.phizic.ws.esberiblistener.pfr;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRqType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRsType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.StatusType;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Базовый класс обработчика входящих сообщений по ПФР от шины
 *
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class EsbEribPFRBackServiceBase<T> implements EsbEribPFRBackService
{
	/**
	 * Журнал для логгирования сообщений
	 */
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public PfrDoneRsType pfrDone(PfrDoneRqType parameters) throws RemoteException
	{
		if (parameters.getStatus().getStatusCode() != 0) // ошибка
		{
			log.warn("Сообщение из шины с RqUID = " + parameters.getRqUID() + " пришло с ошибкой");
			return createResponse(-1L);
		}

		if (parameters.getResult() == null)
		{
			log.warn("В сообщение из шины с RqUID = " + parameters.getRqUID() + " параметр Result = null");
			return createResponse(-1L);
		}

		if (!parameters.getResult().isResponseExists())
		{
			log.warn("В сообщение из шины с RqUID = " + parameters.getRqUID() + " параметр ResponseExists = false");
			return createResponse(-1L);
		}

		T document = findDocument(parameters.getResult().getOperationId());

		if (document == null)
		{
			log.warn("Не найдена заявка с operationId = " + parameters.getResult().getOperationId());
			return createResponse(-1L);
		}

		return updateDocument(document, parameters);
	}

	/**
	 * Обновить документ
	 * @param document
	 * @param parameters
	 */
	protected abstract PfrDoneRsType updateDocument(T document, PfrDoneRqType parameters);

	/**
	 * Найти документ
	 * @param operationId - идентификатор документа
	 * @return - документ
	 */
	protected abstract T findDocument(String operationId);

	protected PfrDoneRsType createResponse(Long statusCode)
	{
		PfrDoneRsType result = new PfrDoneRsType();

		result.setRqUID(new RandomGUID().getStringValue());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		result.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));
		result.setOperUID(new RandomGUID().getStringValue());
		result.setStatus(createStatus(statusCode));

		return result;
	}

	protected void logException(Exception e)
	{
		ExceptionLogHelper.writeLogMessage(e);
	}

	private StatusType createStatus(Long statusCode)
	{
		return statusCode.equals(0L) ? new StatusType(statusCode, "") : new StatusType(statusCode, "Error");
	}
}
