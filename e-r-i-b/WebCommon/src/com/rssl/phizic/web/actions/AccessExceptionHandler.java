package com.rssl.phizic.web.actions;

import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 02.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccessExceptionHandler extends DefaultExceptionHandler
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
	                             HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			Calendar date = GregorianCalendar.getInstance();

			String message = "Доступ запрещен: ";
			if (ex.getMessage() != null ) message = message.concat(ex.getMessage());

			DefaultLogDataReader reader = new DefaultLogDataReader(message);
			reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGIN_DEFAULT_OPERATION_KEY);

			logWriter.writeSecurityOperation(reader, date, date);

			log.error(message);
		}
		catch(Exception e) {}

		return super.execute(ex, ae, mapping, formInstance, request, response);
	}
}
