package com.rssl.phizic.locale.entities;

/**
 * Хранит статическую мультиязычную текстовку
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ERIBStaticMessage
{
	private Long id;
	private String localeId;  //идентификатор локали
	private String bundle;    //бандл
	private String key;       //ключ
	private String message;   //месседж

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
