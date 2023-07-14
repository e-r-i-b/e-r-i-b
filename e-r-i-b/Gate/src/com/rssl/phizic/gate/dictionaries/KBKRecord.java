package com.rssl.phizic.gate.dictionaries;

/**
 * Справочник КБК (Кодов бюджетной классификации)
 * @author Kosyakova
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$                                                                     
 */
public class KBKRecord  extends DictionaryRecordBase implements DictionaryRecord
{
	//внешний идентификатор
	private Long id;
	//todo delete
	private Long taxonomy;
	//Код бюджетной классификации
	private String code;
	//Описание кода бюджетной классификации
	private String note;
	//todo delete
	private Long superior;

	public Comparable getSynchKey()
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

	@Deprecated
	public Long getTaxonomy()
	{
		return taxonomy;
	}

	@Deprecated
	public void setTaxonomy(Long taxonomy)
	{
		this.taxonomy = taxonomy;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	@Deprecated
	public Long getSuperior()
	{
		return superior;
	}

	@Deprecated
	public void setSuperior(Long superior)
	{
		this.superior = superior;
	}
}
