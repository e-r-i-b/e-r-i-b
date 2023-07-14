package com.rssl.phizic.business.departments;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * Реквизиты тербанков
 * @author Pankin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TerBankDetails extends DictionaryRecordBase
{
	private Long id; // Идентификатор в БД
	private String code; // Код ТБ
	private String offCode; //Код ОСБ
	private String name; // Название
	private String BIC; // БИК
	private String OKPO; // ОКПО
	private String address; // Адрес

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

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getOKPO()
	{
		return OKPO;
	}

	public void setOKPO(String OKPO)
	{
		this.OKPO = OKPO;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Comparable getSynchKey()
	{
		return getCode();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((TerBankDetails) that).setId(getId());
		super.updateFrom(that);
	}

	public String getOffCode()
	{
		return offCode;
	}

	public void setOffCode(String offCode)
	{
		this.offCode = offCode;
	}
}
