package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.log.operations.config.LogEntryDescriptor;
import com.rssl.phizic.business.log.operations.config.OperationDescriptorsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.operations.*;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.log.ClientErrorLogDataReader;
import com.rssl.phizic.web.log.SystemErrorLogDataReader;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class LoggableAction extends DispatchAction
{
	protected static final LogWriter logWriter = OperationLogFactory.getLogWriter();
	private static final ThreadLocal<DefaultLogDataReader> currentReader = new ThreadLocal<DefaultLogDataReader>();
	private static final ThreadLocal<Boolean> stopLog = new ThreadLocal<Boolean>(); //признак

	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name) throws Exception
	{
		LogEntryDescriptor logEntryDescriptor = getLogEntryDescriptor(name);
		OperationContext.setCurrentOperationName(logEntryDescriptor.getDescription());
		LogDataReader dataReader = new DefaultLogDataReader(logEntryDescriptor.getDescription());

		String path = getRequesURL(request);
		((DefaultLogDataReader) dataReader).setOperationKey(path);
		((DefaultLogDataReader) dataReader).setOperationPath(path);
		((DefaultLogDataReader) dataReader).setKey(getLogEntryKey(name));
		currentReader.set((DefaultLogDataReader) dataReader);
		stopLog.set(Boolean.FALSE);
		Calendar start = GregorianCalendar.getInstance();
		try
		{
			return super.dispatchMethod(mapping, form, request, response, name);
		}
		catch (Exception e)
		{
			dataReader = new SystemErrorLogDataReader(dataReader, e);
			throw e;
		}
		finally //записать в лог действие
		{
			//смотрим были ли пользовательские ошибки.
			ActionMessages errors = getErrors(request);
			if (!errors.isEmpty())
			{
				dataReader = new ClientErrorLogDataReader(dataReader, errors);
			}
			Calendar end = GregorianCalendar.getInstance();
			write(logEntryDescriptor.getType(), dataReader, start, end);
			currentReader.set(null);
		}
	}

	private void write(OperationType type, LogDataReader dataReader, Calendar start, Calendar end) throws Exception
	{
		if (type == OperationType.ACTIVE)
		{
			logWriter.writeActiveOperation(dataReader, start, end);
		}
		else if (type == OperationType.PASSIVE)
		{
			logWriter.writePassiveOperation(dataReader, start, end);
		}
	}

	private LogEntryDescriptor getLogEntryDescriptor(String name) throws BusinessException
	{
		OperationDescriptorsConfig operationDescriptorsConfig = ConfigFactory.getConfig(OperationDescriptorsConfig.class);
		LogEntryDescriptor descriptor = operationDescriptorsConfig.getDescriptor(getClass(), name);
		if (descriptor == null)
		{
			return new LogEntryDescriptor(getClass().getName() + "." + name, OperationType.PASSIVE);
		}
		return descriptor;
	}

	private String getLogEntryKey(String name) throws BusinessException
	{
		return getClass().getName() + "." + name;
	}

	/**
	 * ƒобавить параметры логируемого действи€
	 * @param reader ридер параметров
	 */
	public final void addLogParameters(LogParametersReader reader)
	{
		if (!BooleanUtils.toBoolean(stopLog.get()))
		{
			DefaultLogDataReader dataReader = currentReader.get();
			dataReader.addParametersReader(reader);
		}
	}

	/**
	 * ќстановить логирование параметров.
	 * »спользутс€ когда передаетс€ управление методу, в котором ведетс€ логирование.
	 * Ќапример, из метода save вызываетс€ start.
	 */
	protected final void stopLogParameters()
	{
		stopLog.set(Boolean.TRUE);
	}

	protected String getRequesURL(HttpServletRequest request)
	{
		String queryString = request.getQueryString();
		String qs = (queryString != null) ? "?" + queryString : "";
		return request.getRequestURI() + qs;
	}

	protected void writeLogInformation(Calendar startDate, String readerDescription, String readerKey)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		String operationKey = request.getRequestURI();

		Calendar end =  GregorianCalendar.getInstance();
		DefaultLogDataReader reader = new DefaultLogDataReader(readerDescription);
		reader.setKey(readerKey);
		reader.setOperationKey(operationKey);
		try
		{
			logWriter.writePassiveOperation(reader, startDate, end);
		}
		catch (Exception e)
		{
			log.error("ќшибка при добавлении записи в лог", e);
		}
	}
}
