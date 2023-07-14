package com.rssl.phizic.business.dictionaries.mnp;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 * Сущность - телефон, для которого сменили оператора сотовой связи
 * @author Rtischeva
 * @ created 17.04.15
 * @ $Author$
 * @ $Revision$
 */
public class MNPPhone extends DictionaryRecordBase
{
	private Long id;  //идентификатор записи

	private String phoneNumber; //телефон, для которого поменяли ОСС

	private String providerCode; //код провайдера (поле CODE)

	private Calendar movingDate; //дата смены ОСС

	private String sourceFileName; //название файла - справочника

	private Long mnc; //уникальный код оператора сотовой связи

	public Comparable getSynchKey()
	{
		return phoneNumber;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getProviderCode()
	{
		return providerCode;
	}

	public void setProviderCode(String providerCode)
	{
		this.providerCode = providerCode;
	}

	public Calendar getMovingDate()
	{
		return movingDate;
	}

	public void setMovingDate(Calendar movingDate)
	{
		this.movingDate = movingDate;
	}

	public String getSourceFileName()
	{
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName)
	{
		this.sourceFileName = sourceFileName;
	}

	public Long getMnc()
	{
		return mnc;
	}

	public void setMnc(Long mnc)
	{
		this.mnc = mnc;
	}

	public void updateFrom(DictionaryRecord that)
	{
		((MNPPhone) that).setId(getId());
		super.updateFrom(that);
	}
}
