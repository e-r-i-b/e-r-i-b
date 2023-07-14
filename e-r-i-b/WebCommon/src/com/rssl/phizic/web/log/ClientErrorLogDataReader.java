package com.rssl.phizic.web.log;

import com.rssl.phizic.logging.operations.LogDataReader;
import org.apache.struts.action.ActionMessages;

import java.util.LinkedHashMap;

/**
 * @author Krenev
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class ClientErrorLogDataReader implements LogDataReader
{
	private LogDataReader original;
	private ActionMessages errors;

	public ClientErrorLogDataReader(LogDataReader original, ActionMessages errors)
	{

		this.original = original;
		this.errors = errors;
	}

	public String getOperationPath()
	{
		return original.getOperationPath();
	}

	public String getOperationKey()
	{
		return original.getOperationKey();
	}

	public String getDescription()
	{
		return original.getDescription() + "(ошибка пользователя)";
	}

	public String getKey()
	{
		return original.getKey();
	}

	public ResultType getResultType()
	{
		return ResultType.CLIENT_ERROR;
	}

	public LinkedHashMap readParameters() throws Exception
	{
		return original.readParameters();
	}
/*
    ActionMessages ae = (ActionMessages)o;

	// Get the locale and message resources bundle
	Locale locale =
			(Locale) session.getAttribute(Globals.LOCALE_KEY);
	MessageResources messages =
			(MessageResources) request.getAttribute
					(Globals.MESSAGES_KEY);

	// Loop thru all the labels in the ActionMessage's
	for(Iterator i = ae.properties();i.hasNext();){
		String property = (String) i.next();
		out.println("<br>property " + property + ": ");

		// Get all messages for this label
		for (Iterator it = ae.get(property); it.hasNext();)
		{
			ActionMessage a = (ActionMessage) it.next();
			String key = a.getKey();
			Object[] values = a.getValues();
			out.println(" [key=" + key + ", message=" + messages.getMessage(locale, key, values) +"]");
		}
	}
*/
}