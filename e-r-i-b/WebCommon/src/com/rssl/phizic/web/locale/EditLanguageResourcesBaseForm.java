package com.rssl.phizic.web.locale;

import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author koptyaev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLanguageResourcesBaseForm extends FilterActionForm
{
	private String localeId;
	private ERIBLocale locale;
	private Long id;
	private String stringId;

	/**
	 * @return ������� ������
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ERIBLocale getLocale()
	{
		return locale;
	}

	/**
	 * �������� ������� ������
	 * @param locale ������
	 */
	public void setLocale(ERIBLocale locale)
	{
		this.locale = locale;
	}

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId ������������� ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return ������������� ��������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������� ��������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��������� ������������� ��������
	 */
	public String getStringId()
	{
		return stringId;
	}

	/**
	 * ���������� ��������� ������������� ��������
	 * @param stringId ��������� �������������
	 */
	public void setStringId(String stringId)
	{
		this.stringId = stringId;
	}
}
