package com.rssl.phizic.business.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;

/**
 * Описание обезличенного металлического счета
 * @author Pankin
 * @ created 18.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAProduct extends DictionaryRecordBase implements MultiBlockDictionaryRecord
{
	private Long id; // id в базе
	private String uuid; //сквозной идентификатор сущности между блоками
	private Long type; // вид счета из справочника
	private Long subType; // подвид счета из справочника
	private String name; // название счета ОМС ("золото", "серебро", "палладий", "платина")
	private Currency currency; // валюта
	private String contractTemplate; //шаблон договора

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getType()
	{
		return type;
	}

	public void setType(Long type)
	{
		this.type = type;
	}

	public Long getSubType()
	{
		return subType;
	}

	public void setSubType(Long subType)
	{
		this.subType = subType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return название ОМС для базовой локали
	 */
	public String getDefaultLocaleName()
	{
		return name;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Comparable getSynchKey()
	{
		return type.toString() + "(" + subType.toString() + ")";
	}

	public String getContractTemplate()
	{
		return contractTemplate;
	}

	public void setContractTemplate(String contractTemplate)
	{
		this.contractTemplate = contractTemplate;
	}

	public void updateFrom(DictionaryRecord that)
    {
	    ((IMAProduct) that).setId(getId());
	    ((IMAProduct) that).setUuid(getUuid());
		super.updateFrom(that);
    }

	public String getMultiBlockRecordId()
	{
		return uuid;
	}

	/**
	 * @return уникальный ключ сущности
	 */
	public String getUuid()
	{
		if (StringHelper.isEmpty(uuid))
			uuid = new RandomGUID().getStringValue();

		return uuid;
	}

	/**
	 * задать уникальный ключ сущности
	 * @param uuid уникальный ключ сущности
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}
