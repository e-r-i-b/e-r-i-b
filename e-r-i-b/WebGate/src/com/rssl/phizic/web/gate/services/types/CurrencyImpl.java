package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyISOComparator;

/**
 * @author: Pakhomova
 * @created: 19.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyImpl implements Currency
{
	private Long   id;
	private String code;
	private String number;
	private String name;


	public String getExternalId()
	{
		return id.toString();
	}

	public void setExternalId(String id)
	{
		this.id = Long.decode(id);
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