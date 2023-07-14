package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyISOComparator;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.io.Serializable;

/**
 * @author: Pakhomova
 * @created: 08.06.2009
 * @ $Author$
 * @ $Revision$
 * currencyImpl from 6th gate
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

	public void setExternalId(String extId)
	{
		id = Long.decode(extId);
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
