package com.rssl.phizic.business.web;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.ConfirmResponseReader;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: osminin $
 * @ $Revision: 64767 $
 */

public class ConfirmationManager
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static final String CONFIRM_REQUEST_KEY = ConfirmationManager.class.getName() + ".confirm_request_";

	/**
	 * ќтправить запрос на подтверждение
	 * @param operation операци€, которую надо подтвердить
	 * @return ошибки при создании подтверждени€
	 */
	public static boolean sendRequest(ConfirmableOperation operation) throws BusinessException, BusinessLogicException
	{
		Store store = StoreManager.getCurrentStore();
		String key = generateConfirmRequestKey(operation.getConfirmableObject());

		ConfirmRequest request = operation.createConfirmRequest(store.getId());
		log.trace("ќтправлен запрос на подтверждение");

		store.save(key, request);
		return request.isError();
	}

	private static String generateConfirmRequestKey(ConfirmableObject confirmableObject)
	{
		return CONFIRM_REQUEST_KEY + confirmableObject.getId();
	}

	/**
	 * “екущий запрос на подтверждение
	 * @return запрос на подтверждение
	 * @param confirmableObject объет дл€ подтверждени€
	 */
	public static ConfirmRequest currentConfirmRequest(ConfirmableObject confirmableObject)
	{
		if (confirmableObject == null)
		{
			return null;
		}
		try
		{
			Store store = StoreManager.getCurrentStore();
			String key = generateConfirmRequestKey(confirmableObject);
			return (ConfirmRequest) store.restore(key);
		}
		catch (Exception e)
		{
			log.error("ќшибка определени€ запроса на подтверждение", e);
			return null;
		}
	}

	/**
	 * ѕрочтитать ответ на подтверждение и записать его в операцию
	 * @param operation подтверждаема€ операци€
	 * @return ошибки если есть
	 */
	public static List<String> readResponse(ConfirmableOperation operation, FieldValuesSource source)
	{
		Calendar start = GregorianCalendar.getInstance();
		ConfirmRequest request = currentConfirmRequest(operation.getConfirmableObject());
		operation.setConfirmRequest(request);

		ConfirmResponseReader responseReader = operation.getConfirmResponseReader();
		responseReader.setValuesSource(source);

		if (!responseReader.read())
		{
			writeToOperationLog(start, "Ќеудачна€ попытка подтверждени€ операции");
			return responseReader.getErrors();
		}

		operation.setConfirmResponse(responseReader.getResponse());
		log.trace("ѕолучен ответ на подтверждение");
		return new ArrayList<String>();
	}

	private static void writeToOperationLog(Calendar start, String message)
	{
		try
		{
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			DefaultLogDataReader reader = new DefaultLogDataReader(message);
			reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGIN_DEFAULT_OPERATION_KEY);
			Calendar end = GregorianCalendar.getInstance();
			logWriter.writeSecurityOperation(reader, start, end);
		}
		catch (Exception ex)
		{
			//ничего не делаем
		}
	}

	/**
	 * @return - получаем режим работы текущего пользовател€: стандартный, переход с внешней ссылки
	 */
	public static UserVisitingMode getUserVisitingMode()
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		if (authContext != null)
			return authContext.getVisitingMode();
		return null;
	}
}
