package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.web.common.ServletContextPropertyReader;

import java.util.Map;

/**
 * @author Evgrafov
 * @ created 25.07.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class SkinConfig
{
	private Map<String, ServletContextPropertyReader> readerByTempate;

	SkinConfig(Map<String, ServletContextPropertyReader> readerByTempate)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.readerByTempate = readerByTempate;
	}

	/**
	 * �������� ��������� ������� �� ��� �����
	 * @param templateName ��� �������
	 * @return ���������
	 */
	public ServletContextPropertyReader getProperty(String templateName)
	{
		return readerByTempate.get(templateName);
	}
}