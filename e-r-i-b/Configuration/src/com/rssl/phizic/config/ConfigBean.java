package com.rssl.phizic.config;

/**
 * @author Erkin
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���, ���������� ������ ������� � ��������������� ���� (xml, json � �.�.)
 */
class ConfigBean
{
	private String codename;

	private String data;

	///////////////////////////////////////////////////////////////////////////

	String getCodename()
	{
		return codename;
	}

	void setCodename(String codename)
	{
		this.codename = codename;
	}

	String getData()
	{
		return data;
	}

	void setData(String data)
	{
		this.data = data;
	}
}
