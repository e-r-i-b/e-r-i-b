package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.processing.FieldValuesSource;

import java.util.Map;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * Извлекает значения полей из HttpServletRequest
 * @author Evgrafov
 * @ created 15.12.2005
 * @ $Author: niculichev $
 * @ $Revision: 52888 $
 */
public class RequestValuesSource implements FieldValuesSource
{
	private HttpServletRequest request;

	/**
	 * @param  request HTTP запрос из параметров которго достаются значения
	 */
	public RequestValuesSource(HttpServletRequest request)
	{
		this.request = request;
	}

	//получаем НЕ первое значение из параметров, а последнее.BUG010835
	public String getValue(String name)
	{
		String[] values = request.getParameterValues(name);
		if (values ==  null){
			return null;
		}
		return values[values.length-1];
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> values = new HashMap<String, String>();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = getValue(name);
			if (value != null)
				values.put(name, value);
		}
		return values;
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return !request.getParameterNames().hasMoreElements();
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
