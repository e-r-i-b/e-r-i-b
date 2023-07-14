package com.rssl.phizic.locale;

/**
 * @author mihaylov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ ��� ������ ���������.
 * ��������� �� ���������� ���������� ��������� ���������.
 */
class ERIBMessageKey
{
	private String key;
	private String bundle;
	private String localeId;

	/**
	 * �����������
	 * @param key - ����
	 * @param bundle - �����
	 * @param localeId - ������������� ������
	 */
	ERIBMessageKey(String key, String bundle, String localeId)
	{
		this.key = key;
		this.bundle = bundle;
		this.localeId = localeId;
	}

	/**
	 * @return ����
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @return �����
	 */
	public String getBundle()
	{
		return bundle;
	}

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}
}
