package com.rssl.phizgate.common.credit.bki.dictionary;

/**
 * User: Moshenko
 * Date: 03.10.14
 * Time: 15:31
 * справочник ДУЛ
 */
public class BkiPrimaryIDType extends BkiEsbDictionaryEntry
{
	private Long id;
	private String bkiCode;

	Long getId()
	{
		return id;
	}

	void setId(Long id)
	{
		this.id = id;
	}

	public String getBkiCode()
	{
		return bkiCode;
	}

	public void setBkiCode(String bkiCode)
	{
		this.bkiCode = bkiCode;
	}
}
