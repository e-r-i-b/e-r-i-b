package com.rssl.phizic.web.util;

import com.rssl.phizic.business.exception.ExceptionEntryHelper;
import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ELException;

/**
 * @author Roshka
 * @ created 16.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class ExceptionLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final org.apache.commons.logging.Log apacheLog = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());

	private static final String ERROR_MESSAGE_KEY = "errorMessage";

	private static final List<Application> internalExceptionApplications = new ArrayList<Application>(10);

	static
	{
		//при добавлении не забываем про:
		// 1) com/rssl/phizic/web/configure/exceptions/resources.properties    (application.?)
		// 2) /WEB-INF/jsp/configure/exceptions/list.jsp                       (filter(application))
		internalExceptionApplications.add(Application.PhizIA);
		internalExceptionApplications.add(Application.PhizIC);
		internalExceptionApplications.add(Application.mobile5);
		internalExceptionApplications.add(Application.mobile6);
		internalExceptionApplications.add(Application.mobile7);
		internalExceptionApplications.add(Application.mobile8);
		internalExceptionApplications.add(Application.mobile9);
		internalExceptionApplications.add(Application.socialApi);
		internalExceptionApplications.add(Application.WebAPI);
		internalExceptionApplications.add(Application.ErmbSmsChannel);
		internalExceptionApplications.add(Application.atm);
	}

	/**
	 * Обработка и запись в журнал о сбое.
	 * @param e
	 */
	public static void writeLogMessage ( Throwable e)
	{
		Throwable logableCause = e;
		if (e instanceof ServletException)
		{
			Throwable rootCause = ((ServletException)e).getRootCause();
			if (rootCause!=null)
			{
				logableCause = rootCause;
			}
		}

		try
		{
			log.error(ExceptionUtil.printStackTrace(logableCause));
		}
		catch(Throwable te) // нужно, чтобы можно было сделать редирект на error.do в случае ошибки
		{
			System.out.println("Не удалось записать лог");
			te.printStackTrace();
		}
	}

	/**
	 * Поиск вложенного исключения
	 * @param throwable исключение
	 * @return исключение
	 */
	public static Throwable getRootCause(Throwable throwable)
	{
		Throwable t = throwable;

		while (isThrowableUsed(t))
		{
			if (t instanceof ServletException)
			{
				ServletException servletException = (ServletException) t;
				t = servletException.getRootCause();
			}

			if (t instanceof ELException)
			{
				ELException elException = (ELException) t;
				t = elException.getRootCause();
			}
		}
		return t;
	}

	/**
	 * Устанавливаем ошибку в pageContext
	 * @param throwable исключение
	 * @param pageContext pageContext
	 */
	public static void setException(Throwable throwable, PageContext pageContext)
	{
		//устанавливаем ошибку неактивности внешней системы в сессию
		if (throwable instanceof InactiveExternalSystemException)
		{
			InactiveExternalSystemException exception = (InactiveExternalSystemException) throwable;
			ActionMessages messages = (ActionMessages) pageContext.getSession().getAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey());
			if (messages == null || messages.isEmpty())
			{
				messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(exception.getMessage(), false));
			}
			else
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(exception.getMessage(), false));
			}
			pageContext.getSession().setAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey(), messages);
		}
	}

	private static boolean isThrowableUsed(Throwable throwable)
	{
		return throwable instanceof ELException || throwable instanceof ServletException;
	}

	/**
	 * Обработать ошибку. Сохранить исключение в справочнике маппинга ошибок.
	 * Если исключение уже есть в справочнике и для него задана текстовка, то поместить эту текстовку в реквест для вывода пользователю.
	 * @param throwable - исключение
	 * @param pageContext - контекст страницы
	 */
	public static void processExceptionEntry(Throwable throwable, PageContext pageContext)
	{
		processExceptionEntry(throwable,pageContext.getSession());
	}

	/**
	 * Обработать ошибку
	 * Сохранить ошибку в справочнике ошибок, если её там нет.
	 * Увеличить счетчик количества ошибок данного типа за день.
	 * Поместить в сессию сообщение об ошибке: из справочника ошибок либо дефолтное.
	 * @param throwable - исключение
	 * @param session - сессия
	 */
	public static void processExceptionEntry(Throwable throwable, HttpSession session)
	{
		try
		{
			String exceptionEntryMessage = processExceptionEntryWithWriteLog(throwable);
			if(session == null)
				return;
			Application currentApplication = LogThreadContext.getApplication();
			String exceptionMessage = ExceptionSettingsService.getDefaultMessageForApplication(currentApplication);
			if(StringHelper.isNotEmpty(exceptionEntryMessage))
				session.setAttribute(ERROR_MESSAGE_KEY, exceptionEntryMessage);
			else if(StringHelper.isNotEmpty(exceptionMessage))
				session.setAttribute(ERROR_MESSAGE_KEY, exceptionMessage);
		}
		catch (Exception e)
		{
			log.error("Не удалось обработать ошибку через маппинг ошибок",e);
		}
	}

	/**
	 * Обработать ошибку
	 * Сохранить ошибку в справочнике ошибок, если её там нет.
	 * Увеличить счетчик количества ошибок данного типа за день.
	 * Если ошибка не отправляется в справочник, то просто логируем
	 * @param throwable - исключение
	 * @return сообщение
	 */
	public static String processExceptionEntryWithWriteLog(Throwable throwable)
	{
		try
		{
			Application currentApplication = LogThreadContext.getApplication();

			if (internalExceptionApplications.contains(currentApplication))
			{
				SystemLogEntry entry = (SystemLogEntry) log.createEntry(throwable);

				if (entry == null)
				{
					writeLogMessage(throwable);
				}
				else
				{
					apacheLog.error(throwable);
				}

				return ExceptionEntryHelper.getMessage(throwable, entry);
			}
		}
		catch (Exception e)
		{
			log.error("Не удалось обработать ошибку через маппинг ошибок",e);
			return StringUtils.EMPTY;
		}

		writeLogMessage(throwable);
		return StringUtils.EMPTY;
	}

	/**
	 * Обработать ошибку
	 * Сохранить ошибку в справочнике ошибок, если её там нет.
	 * Увеличить счетчик количества ошибок данного типа за день.
	 * Если ошибка не отправляется в справочник, то просто логируем
	 * @param throwable - исключение
	 * @param message - сообщение для логирования
	 * @return сообщение
	 */
	public static String processExceptionEntryWithWriteLog(Throwable throwable, String message)
	{
		try
		{
			Application currentApplication = LogThreadContext.getApplication();

			if (internalExceptionApplications.contains(currentApplication))
			{
				SystemLogEntry entry = (SystemLogEntry) log.createEntry(message, throwable);

				if (entry == null)
				{
					log.error(message, throwable);
				}
				else
				{
					apacheLog.error(message, throwable);
				}

				return ExceptionEntryHelper.getMessage(throwable, entry);
			}
		}
		catch (Exception e)
		{
			log.error("Не удалось обработать ошибку через маппинг ошибок",e);
			return StringUtils.EMPTY;
		}

		log.error(message, throwable);
		return StringUtils.EMPTY;
	}


	/**
	 * Возвращает сообщение об ошибке из сессии, если оно там есть. Заодно и удаляет это сообщение из сессии.
	 * @param pageContext - контекст сртраницы
	 * @return сообщение об ошибке
	 */
	public static String getErrorMessage(PageContext pageContext)
	{
		HttpSession session = pageContext.getSession();
		String errorMessage = (String) session.getAttribute(ERROR_MESSAGE_KEY);
		if (errorMessage != null)
			session.removeAttribute(errorMessage);
		return errorMessage;
	}
}