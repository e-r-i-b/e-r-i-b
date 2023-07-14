package com.rssl.phizic.business.dictionaries.defCodes;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * Запись справочника def-кодов
 * @author Rtischeva
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class DefCode extends DictionaryRecordBase
{
	private Long id;
	private String defCodeFrom;
	private String defCodeTo;
	private String providerCode;
	private Long mnc;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDefCodeFrom()
	{
		return defCodeFrom;
	}

	public void setDefCodeFrom(String defCodeFrom)
	{
		this.defCodeFrom = defCodeFrom;
	}

	public String getDefCodeTo()
	{
		return defCodeTo;
	}

	public void setDefCodeTo(String defCodeTo)
	{
		this.defCodeTo = defCodeTo;
	}

	public String getProviderCode()
	{
		return providerCode;
	}

	public void setProviderCode(String providerCode)
	{
		this.providerCode = providerCode;
	}

	public Comparable getSynchKey()
	{
		return defCodeFrom + " - " + defCodeTo;
	}

	public Long getMnc()
	{
		return mnc;
	}

	public void setMnc(Long mnc)
	{
		this.mnc = mnc;
	}
}
