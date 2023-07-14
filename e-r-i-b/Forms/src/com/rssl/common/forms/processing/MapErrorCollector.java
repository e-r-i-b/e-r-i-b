package com.rssl.common.forms.processing;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.MessageHolder;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Сборщик ошибок в мапу "сообщение - ИД элемента"
 * @author Jatsky
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 */

public class MapErrorCollector implements ErrorsCollector<Map<String, String>>
{

	private boolean isTemporalUnAccesible = false;
	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> errors()
	{
		return errors;
	}

	public void add(String value, Field field, MessageHolder messageHolder)
	{
		String message = messageHolder.getMessage();
		if (message != null)
		{
			errors.put(message, field.getName());
		}
		else
		{
			String messageKey = messageHolder.getClass().getName() + ".message";
			String fieldName = field == null ? "" : field.getDescription();
			errors.put(getMessage(messageKey, fieldName), field.getName());
		}
	}

	public void add(Map<String, Object> values, Collection<String> errorFieldNames, MessageHolder messageHolder)
	{
		String message = messageHolder.getMessage();
		if (message != null)
		{
			errors.put(message, null);
		}
		else
		{
			String messageKey = messageHolder.getClass().getName() + ".message";
			errors.put(getMessage(messageKey, ""), null);
		}
	}

	public void add(MessageHolder messageHolder)
	{
		String message = messageHolder.getMessage();
		if (StringHelper.isNotEmpty(message))
		{
			errors.put(message, null);
		}
	}

	public void setTemporalUnAccesible()
	{
		if (isTemporalUnAccesible)
		{
			isTemporalUnAccesible = true;
			errors.put(null, "Операция временно недоступна. Повторите попытку позже.");
		}
	}

	public int count()
	{
		return errors.size();
	}

	private String getMessage(String key, String fieldName)
	{
		Properties properties = new Properties();
		try
		{
			properties.load(ResourceHelper.loadResourceAsStream("validator.properties"));
			String property = properties.getProperty(key, fieldName);
			MessageFormat messageFormat = new MessageFormat(property);
			return messageFormat.format(new String[]{fieldName});
		}
		catch (IOException e)
		{
			return "Информация временно недоступна";
		}
	}

	public boolean isEmpty()
	{
		return errors.size() == 0;
	}
}
