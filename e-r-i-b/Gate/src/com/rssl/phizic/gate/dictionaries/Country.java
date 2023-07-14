package com.rssl.phizic.gate.dictionaries;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class Country  extends DictionaryRecordBase implements DictionaryRecord
{
	//Внешний идентификатор
	private Long id;
	//Код страны. 2х или 3х символьный
	private String code;
	//Название
    private String name;
	//Полное название
    private String fullName;
	//Цифровой код страны
	private String intCode;

	public String getIntCode()
	{
		return intCode;
	}

	public void setIntCode(String intCode)
	{
		this.intCode = intCode;
	}

	public Comparable getSynchKey ()
    {
        return id;
    }

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name=name;
    }

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
}
