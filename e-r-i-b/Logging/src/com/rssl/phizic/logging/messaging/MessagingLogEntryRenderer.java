package com.rssl.phizic.logging.messaging;

import org.apache.log4j.or.ObjectRenderer;

/**
 * @author lukina
 * @ created 29.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class MessagingLogEntryRenderer implements ObjectRenderer
{
	public String doRender(Object o)
	{
		if (!(o instanceof MessagingLogEntry)){
		    return "";
		}
		MessagingLogEntry entry = (MessagingLogEntry) o;

		StringBuffer buffer = new StringBuffer();
		buffer.append("\nЗапрос:\n");
		buffer.append(entry.getMessageRequest());
		buffer.append("\nОтвет:\n");
		buffer.append(entry.getMessageResponse());
		return buffer.toString();
	}
}
