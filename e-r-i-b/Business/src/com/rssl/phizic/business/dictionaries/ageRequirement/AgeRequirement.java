package com.rssl.phizic.business.dictionaries.ageRequirement;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Возрастные ограничения клиентов для включения в сегмент
 *
 * @author EgorovaA
 * @ created 10.12.14
 * @ $Author$
 * @ $Revision$
 */
public class AgeRequirement extends DictionaryRecordBase implements Serializable
{
	private Long id;
	private String code;
	private Calendar dateBegin;
	private Long lowLimitFemale;
	private Long lowLimitMale;
	private Long topLimit;

	public Comparable getSynchKey()
	{
		return getCode()+getDateBegin();
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

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public Long getLowLimitFemale()
	{
		return lowLimitFemale;
	}

	public void setLowLimitFemale(Long lowLimitFemale)
	{
		this.lowLimitFemale = lowLimitFemale;
	}

	public Long getLowLimitMale()
	{
		return lowLimitMale;
	}

	public void setLowLimitMale(Long lowLimitMale)
	{
		this.lowLimitMale = lowLimitMale;
	}

	public Long getTopLimit()
	{
		return topLimit;
	}

	public void setTopLimit(Long topLimit)
	{
		this.topLimit = topLimit;
	}

	public void updateFrom(DictionaryRecord that)
	{
		((AgeRequirement) that).setId(getId());
		super.updateFrom(that);
	}

}
