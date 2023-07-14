package com.rssl.phizgate.common.credit.bki.dictionary;

/**
 * Запись в справочнике БКИ, для которой есть шинный код
 * @author Puzikov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class BkiEsbDictionaryEntry extends BkiAbstractDictionaryEntry
{
	private String esbCode;
	private boolean isDefault;

	public String getEsbCode()
	{
		return esbCode;
	}

	public void setEsbCode(String esbCode)
	{
		this.esbCode = esbCode;
	}

	public boolean getIsDefault()
	{
		return isDefault;
	}

	public void setIsDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}
}
