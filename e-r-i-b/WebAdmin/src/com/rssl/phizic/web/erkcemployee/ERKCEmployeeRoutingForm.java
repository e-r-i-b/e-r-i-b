package com.rssl.phizic.web.erkcemployee;

import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 02.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма маршрутизирующего экшена сотрудников ЕРКЦ
 */

public class ERKCEmployeeRoutingForm extends ActionForm
{
	private Map<String,Object> parameters = new HashMap<String, Object>();

	/**
	 * @return дополнительные прараметры функционала
	 */
	public Map<String, Object> getParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return parameters;
	}

	/**
	 * задать дополнительные прараметры функционала
	 * @param parameters прараметры
	 */
	public void setParameters(Map<String, Object> parameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.parameters = parameters;
	}
}
