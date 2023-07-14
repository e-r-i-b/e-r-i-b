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
 * ����� ����� ����� ���� �����������
 */

public class ERKCEmployeeChangeBlockForm extends ChangeNodeForm
{
	private String functional;
	private Map<String,Object> parameters = new HashMap<String, Object>();

	/**
	 * @return ����������, � ������ �������� ���������� ����� �����
	 */
	public String getFunctional()
	{
		return functional;
	}

	/**
	 * ������ ����������, � ������ �������� ���������� ����� �����
	 * @param functional ����������
	 */
	public void setFunctional(String functional)
	{
		this.functional = functional;
	}

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
