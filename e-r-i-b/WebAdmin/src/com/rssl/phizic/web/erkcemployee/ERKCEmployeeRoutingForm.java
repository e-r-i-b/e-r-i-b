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
 * ����� ����������������� ������ ����������� ����
 */

public class ERKCEmployeeRoutingForm extends ActionForm
{
	private Map<String,Object> parameters = new HashMap<String, Object>();

	/**
	 * @return �������������� ���������� �����������
	 */
	public Map<String, Object> getParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return parameters;
	}

	/**
	 * ������ �������������� ���������� �����������
	 * @param parameters ����������
	 */
	public void setParameters(Map<String, Object> parameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.parameters = parameters;
	}
}
