package com.rssl.phizic.business.connectUdbo;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Сущность для хранения условий заявления на подключение УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class UDBOClaimRules extends MultiBlockDictionaryRecordBase implements Serializable
{
	private Long id;                                //идентефикатор условий
	private Calendar startDate;                     //дата начала действия
	private UDBOClaimRulesStatus status;            //статус
	private String rulesText;                       //текст уловий в формате html

	/**
	 * @return идентефикатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Задать идентефикатор условий
	 * @param id - идентефикатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дату начала действия
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * Задать дату начала действия условий
	 * @param startDate - дата
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return статус условий
	 */
	public UDBOClaimRulesStatus getStatus()
	{
		return status;
	}

	/**
	 * Задать статус условий
	 * @param status - статус
	 */
	public void setStatus(UDBOClaimRulesStatus status)
	{
		this.status = status;
	}

	/**
	 * @return текст уловий
	 */
	public String getRulesText()
	{
		return rulesText;
	}

	/**
	 * Задать текст условий
	 * @param rulesText - текст уловий в формате html
	 */
	public void setRulesText(String rulesText)
	{
		this.rulesText = rulesText;
	}
}
