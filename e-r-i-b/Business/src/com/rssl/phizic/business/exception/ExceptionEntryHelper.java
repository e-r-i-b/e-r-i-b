package com.rssl.phizic.business.exception;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.utils.CihperHelper;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizic.logging.exceptions.InternalExceptionEntry;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;

/**
 * @author mihaylov
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 * Хелпер для работы со справочником классификации ошибок
 */
public class ExceptionEntryHelper
{
	private static final String EXCEPTION_IDENTIFIER_DELIMITER = " ";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ExceptionEntryUpdateService service = new ExceptionEntryUpdateService();
	private static AdapterService adapterService = new AdapterService();

	private static final Cache<String, String> CACHE = new Cache<String, String>(new OnCacheOutOfDateListener<String, String>()
	{
		public String onRefresh(String key)
		{
			try
			{
				Adapter adapter = adapterService.getAdapterByUUID(key);
				return adapter == null ? key : adapter.getName();
			}
			catch (GateException e)
			{
				log.error("Ошибка при получении адаптера по uuid " + key, e);
				return key;
			}
		}
	}, 15L);

	/**
	 * Получить наименование внешней системы по идентификатору
	 * @param uuid идентификатор
	 * @return наименование внешней системы или uuid
	 */
	public static String getSystemName(String uuid)
	{
		return StringHelper.isEmpty(uuid) ? StringUtils.EMPTY : CACHE.getValue(uuid);
	}

	/**
	 * Получить запись об ошибке из справочника ошибок ЕРИБ
	 * @param throwable - исключение для которого необходимо получить запись
	 * @param entry - запись в журнал логов
	 * @return сообщение, которое задал сотрудник для данной ошибки
	 */
	public static String getMessage(Throwable throwable, SystemLogEntry entry)
	{
		try
		{
			InternalExceptionEntry exceptionEntryTemplate = new InternalExceptionEntry();
			exceptionEntryTemplate.setHash(getHash(throwable, LogThreadContext.getApplication()));
			exceptionEntryTemplate.setDetail(ExceptionUtil.printStackTrace(throwable));
			exceptionEntryTemplate.setOperation(OperationContext.getCurrentOperationName());
			exceptionEntryTemplate.setApplication(LogThreadContext.getApplication());

			ExceptionEntryApplication exceptionEntryApplication = ExceptionEntryApplication.fromApplication(LogThreadContext.getApplication());
			String tb = LogThreadContext.getDepartmentRegion();
			return service.getMessageAndUpdate(exceptionEntryTemplate,exceptionEntryApplication,tb, entry);
		}
		catch (Exception e)
		{
			log.error("Не удалось зафиксировать ошибку в справочнике классификации ошибок",e);
			return null;
		}
	}

	/**
	 * Получить hash для исключения
	 * @param throwable - исключение
	 * @return hash
	 */
	private static String getHash(Throwable throwable, Application application)
	{
		try
		{
			String identifier = getExceptionIdentifier(throwable);
			return new CihperHelper().SHA1(identifier) + ExceptionEntry.DELIMITER + application.name();
		}
		catch (Exception ignore)
		{
			return "";
		}
	}

	/**
	 * Получить идентификатор исключения
	 * @param throwable - исключение
	 * @return идентификатор
	 */
	private static String getExceptionIdentifier(Throwable throwable)
	{
		StringBuilder identifier = new StringBuilder();
		if(throwable.getCause()!= null && throwable.getCause() != throwable)
			 identifier.append(getExceptionIdentifier(throwable.getCause()));
		identifier.append(getStacktraceIdentifier(throwable.getStackTrace(), throwable instanceof ServletException));
		return identifier.toString();
	}

	/**	  
	 * @param stackTraceElements - стек трейс
	 * @param allElements - вычислять идентификатор по всему стеку или достаточно только 1-го элемента
	 * @return идентификатор ошибки на основе стек трейса
	 */
	private static String getStacktraceIdentifier(StackTraceElement[] stackTraceElements, boolean allElements)
	{
		StringBuilder identifier = new StringBuilder();
		if(ArrayUtils.isEmpty(stackTraceElements))
			return identifier.toString();
		for(StackTraceElement element : stackTraceElements)
		{
			identifier.append(element.getClassName());
			identifier.append(EXCEPTION_IDENTIFIER_DELIMITER);
			identifier.append(element.getMethodName());
			identifier.append(EXCEPTION_IDENTIFIER_DELIMITER);
			identifier.append(element.getLineNumber());
			if(!allElements)
				break;
		}
		return identifier.toString();
	}
}
