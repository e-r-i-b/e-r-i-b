package com.rssl.phizic.gate.dictionaries;

/**
 * коды видов валютных операций
 * @author Egorova
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOperationType extends DictionaryRecordBase
{
	private Long id;
	private String code;
    private String name;
    private String description;

	public Comparable getSynchKey ()
    {
        return code;
    }

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
