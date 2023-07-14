package com.rssl.phizic.web.erkcemployee;

import com.rssl.phizic.web.security.node.ChangeNodeForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 02.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма смены блока ЕРКЦ сотрудником
 */

public class ERKCEmployeeChangeBlockForm extends ChangeNodeForm
{
	private String functional;
	private Map<String,Object> parameters = new HashMap<String, Object>();

	/**
	 * @return функционал, в рамках которого происходит смена блока
	 */
	public String getFunctional()
	{
		return functional;
	}

	/**
	 * задать функционал, в рамках которого происходит смена блока
	 * @param functional функционал
	 */
	public void setFunctional(String functional)
	{
		this.functional = functional;
	}

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
