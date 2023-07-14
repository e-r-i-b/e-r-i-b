package com.rssl.phizgate.common.credit.bki.dictionary;

/**
 * User: Moshenko
 * Date: 02.10.14
 * Time: 15:45
 */
public abstract class BkiAbstractDictionaryEntry
{
	private String code;

	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}
