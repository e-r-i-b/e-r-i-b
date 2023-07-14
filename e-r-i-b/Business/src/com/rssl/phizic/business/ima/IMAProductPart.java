package com.rssl.phizic.business.ima;

/**
 * @author akrenev
 * @ created 14.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * частичная информация об ОМС
 */

public class IMAProductPart
{
	private Long id; // id в базе
	private Long type; // вид счета из справочника
	private Long subType; // подвид счета из справочника
	private String name; // название счета ОМС ("золото", "серебро", "палладий", "платина")

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return вид счета из справочника
	 */
	public Long getType()
	{
		return type;
	}

	/**
	 * задать вид счета из справочника
	 * @param type вид счета из справочника
	 */
	public void setType(Long type)
	{
		this.type = type;
	}

	/**
	 * @return подвид счета из справочника
	 */
	public Long getSubType()
	{
		return subType;
	}

	/**
	 * задать подвид счета из справочника
	 * @param subType подвид счета из справочника
	 */
	public void setSubType(Long subType)
	{
		this.subType = subType;
	}

	/**
	 * @return название счета ОМС ("золото", "серебро", "палладий", "платина")
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название счета ОМС ("золото", "серебро", "палладий", "платина")
	 * @param name название счета ОМС ("золото", "серебро", "палладий", "платина")
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
