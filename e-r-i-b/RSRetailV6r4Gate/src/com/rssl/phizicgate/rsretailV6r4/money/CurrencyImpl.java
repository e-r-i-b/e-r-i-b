package com.rssl.phizicgate.rsretailV6r4.money;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyISOComparator;

import java.io.Serializable;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyImpl extends DictionaryRecordBase implements Currency, Serializable
{
	private Long   id;
	private String code;
	private String number;
	private String name;


	public String getExternalId()
	{
		return id.toString();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getCode ()
	{
		return code;
	}

	public void setCode ( String code )
	{
		this.code = code;
	}

	public String getName ()
	{
		return name;
	}

	public void setName ( String name )
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return id.toString();//number;
	}

	public boolean compare(Currency c)
	{
		return CurrencyISOComparator.compare(c, this);
	}
}
