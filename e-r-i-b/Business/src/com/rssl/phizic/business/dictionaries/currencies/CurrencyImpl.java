package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyISOComparator;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 18.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyImpl extends DictionaryRecordBase implements Currency, Serializable
{
	private String id;
	private String numericCode;
	private String alphabeticCode;
	private String name;

	public String getNumber ()
	{
		return numericCode;
	}

	public void setNumber(String code)
	{
		numericCode = code;
	}

	public String getCode()
	{
		return alphabeticCode;
	}

	public void setCode(String code)
	{
		alphabeticCode = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return id;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.id = synchKey.toString();
	}


	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getExternalId()
	{
		return id;
	}

	public void setExternalId(String extId)
	{
		id = extId;
	}

	public boolean compare(Currency c)
	{
		return CurrencyISOComparator.compare(c, this);
	}
}
