package com.rssl.phizic.web.common.messages;

import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.web.WebContext;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roshka
 * @ created 09.11.2005
 * @ $Author: mihaylov $
 * @ $Revision$
 */
public class StrutsMessageConfig implements MessageConfig
{
    public String message(String bundle, String key)
    {
        return message(bundle, key, null);
    }

	public String message(String bundle, String key, Object[] args)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

	    MessageResources messages = (MessageResources) request.getAttribute(bundle);

	    if(messages == null)
	        throw new ConfigurationException("Bundle " + bundle + " не найден в Request.Attributes");

	    String message = messages.getMessage(key, args);
	    if(message == null)
	        throw new ConfigurationException("Message " + key + " Bundle " + bundle + " не найден");

        return message;
	}
}
