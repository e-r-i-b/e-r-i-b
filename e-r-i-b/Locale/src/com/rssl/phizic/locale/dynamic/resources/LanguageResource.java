package com.rssl.phizic.locale.dynamic.resources;

/**
 * ������� ����� ��� �������� ������������ ������������� ���������
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class LanguageResource implements LanguageResources<Long>
{
	private Long id;
	private String localeId;

	public Long getId()
	{
		return id;
	}

	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param localeId ������������� ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}
}
