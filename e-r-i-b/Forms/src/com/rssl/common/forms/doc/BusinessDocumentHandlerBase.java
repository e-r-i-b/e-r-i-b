package com.rssl.common.forms.doc;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roshka
 * @ created 02.02.2007
 * @ $Author$
 * @ $Revision$
 */

public abstract class BusinessDocumentHandlerBase<SO extends StateObject> extends ParameterListHandlerBase<SO>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

	protected DateFormat getDateFormat()
	{
		return (DateFormat)dateFormat.clone();
	}

	private Map<String, String> parameters = new HashMap<String, String>();

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	public void setParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	public String getParameter(String name)
	{
		return parameters.get(name);
	}

	protected DocumentLogicException makeValidationFail(String text)
	{
		if (log.isInfoEnabled())
			log.info("[Ошибка валидации(" + this.getClass().getSimpleName() + ")]: " + text);
		return new DocumentLogicException(text);
	}
}