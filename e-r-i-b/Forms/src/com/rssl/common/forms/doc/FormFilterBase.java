package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.StateObject;
import org.apache.commons.lang.BooleanUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Базовый класс фильтров формы машины состояний
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class FormFilterBase<SO extends StateObject> implements FormFilter<SO>
{
	private static final String INVERT_PARAMETER_NAME           = "invert";


	private Map<String, String> parameters = new HashMap<String, String>();

	public Map<String, String> getParameters()
	{
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * Получить значение парамметра по ключу
	 * @param key ключ
	 * @return значение
	 */
	public String getParameter(String key)
	{
		return parameters.get(key);
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters.putAll(parameters);
	}

	protected boolean isInverted()
	{
		return BooleanUtils.toBoolean(parameters.get(INVERT_PARAMETER_NAME));
	}
}
