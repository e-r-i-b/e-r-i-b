package com.rssl.phizic.business.dictionaries.tariffPlan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.TariffPlan;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Настройки тарифного плана клиента
 *
 * @ author: Gololobov
 * @ created: 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TariffPlanConfig extends DictionaryRecordBase implements TariffPlan, Serializable
{
	private Long id;
	private String code;
	private String name;
	private Calendar dateBegin;
	private Calendar dateEnd;
	private boolean state;
	//Отображать ли стандартный курс при совершении конверсионных операций
	private boolean needShowStandartRate;
	//Сообщение, отображаемое при использовании льготного курса
	private String privilegedRateMessage;

	private String EMPTY_TARIF_PLAN_EXCEPTION = "Не указан тарифный план";

	public TariffPlanConfig() {

	}

	public Comparable getSynchKey()
	{
		return getCode();
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

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public Calendar getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public boolean isState()
	{
		return state;
	}

	public String getUnknownCode()
	{
		return "0";
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public boolean isNeedShowStandartRate()
	{
		return needShowStandartRate;
	}

	public void setNeedShowStandartRate(boolean needShowStandartRate)
	{
		this.needShowStandartRate = needShowStandartRate;
	}

	public String getPrivilegedRateMessage()
	{
		return privilegedRateMessage;
	}

	public void setPrivilegedRateMessage(String privilegedRateMessage)
	{
		this.privilegedRateMessage = privilegedRateMessage;
	}

	public void updateFrom(DictionaryRecord that)
	{
		((TariffPlanConfig) that).setId(getId());
		((TariffPlanConfig) that).setPrivilegedRateMessage(getPrivilegedRateMessage());
		((TariffPlanConfig) that).setNeedShowStandartRate(isNeedShowStandartRate());
		super.updateFrom(that);

	}


}
