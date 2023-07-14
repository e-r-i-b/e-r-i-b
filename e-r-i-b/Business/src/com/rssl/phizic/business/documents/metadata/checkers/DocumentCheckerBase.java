package com.rssl.phizic.business.documents.metadata.checkers;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class DocumentCheckerBase implements DocumentChecker
{
	private Map<String, String> parameters = new HashMap<String, String>();

	/**
	 * ���������� �������� ���������
	 * @param name ���
	 * @param value ��������
	 */
	public void setParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	/**
	 * �������� ��������
	 * @param name ��� ���������
	 * @return ��������
	 */
	public String getParameter(String name)
	{
		return parameters.get(name);
	}
}
