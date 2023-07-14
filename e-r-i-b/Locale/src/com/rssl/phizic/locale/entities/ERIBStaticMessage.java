package com.rssl.phizic.locale.entities;

/**
 * ������ ����������� ������������� ���������
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ERIBStaticMessage
{
	private Long id;
	private String localeId;  //������������� ������
	private String bundle;    //�����
	private String key;       //����
	private String message;   //�������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getBundle()
	{
		return bundle;
	}

	public void setBundle(String bundle)
	{
		this.bundle = bundle;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getLocaleId()
	{
		return localeId;
	}

	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}
}
