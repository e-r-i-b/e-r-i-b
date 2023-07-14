package com.rssl.phizic.business.dictionaries.currencies.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResources;

/**
 * �������������� ������� ��� ����� �����
 * @author lepihina
 * @ created 09.06.15
 * $Author$
 * $Revision$
 */
public class CurrencyImplResources implements LanguageResources<String>
{
	private String id;
	private String localeId;
	private String name;

	/**
	 * @return ������������� ������ �� ����������� �����
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ������ �� ����������� �����
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return �������� ������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name �������� ������
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
