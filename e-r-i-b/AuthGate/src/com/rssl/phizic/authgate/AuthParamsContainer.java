package com.rssl.phizic.authgate;

import java.util.HashMap;
import java.util.Map;

/**
 * ��������� ���������� �����������.
 * ����������� � ������ �������������� �� ������� ������� �� ������� �������� ������������ � ������� ����������
 *
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class AuthParamsContainer
{
	// �������� ���������
	private Map<String, String> parameters;

	public AuthParamsContainer()
	{
		parameters = new HashMap<String, String>();
	}

	/**
	 * ��������  ��������
	 * @param name ���
	 * @param value ��������
	 */
	public void addParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	/**
	 * ��������  ��������
	 * @param name ���
	 * @return ��������
	 */
	public String getParameter(String name)
	{
		return parameters.get(name);
	}

	public void clear()
	{
		parameters.clear();
	}
}
