package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.doc.BusinessDocumentHandler;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Вазовый класс хендлеров шаблонов
 *
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TemplateHandlerBase<SO extends StateObject> implements BusinessDocumentHandler<SO>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Map<String, String> parameters = new HashMap<String, String>();

	public Map<String, String> getParameters()
	{
		return Collections.unmodifiableMap(parameters);
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters.putAll(parameters);
	}

	public void setParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	public String getParameter(String name)
	{
		return parameters.get(name);
	}
}
