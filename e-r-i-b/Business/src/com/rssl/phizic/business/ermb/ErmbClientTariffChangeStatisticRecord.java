package com.rssl.phizic.business.ermb;

import java.util.Calendar;

/**
 * Историческая запись о подключении/изменении тарифного плана ЕРМБ
 * @author Rtischeva
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbClientTariffChangeStatisticRecord
{
	private Long id;  //идентификатор

	private Long ermbProfileId; //id ермб-профиля

	private Long ermbTariffId; //id тарифа

	private String ermbTariffName; //название тарифа

	private Calendar changeDate; //дата подключения тарифа

	private String tb; //ТБ клиента

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getErmbProfileId()
	{
		return ermbProfileId;
	}

	public void setErmbProfileId(Long ermbProfileId)
	{
		this.ermbProfileId = ermbProfileId;
	}

	public Long getErmbTariffId()
	{
		return ermbTariffId;
	}

	public void setErmbTariffId(Long ermbTariffId)
	{
		this.ermbTariffId = ermbTariffId;
	}

	public String getErmbTariffName()
	{
		return ermbTariffName;
	}

	public void setErmbTariffName(String ermbTariffName)
	{
		this.ermbTariffName = ermbTariffName;
	}

	public Calendar getChangeDate()
	{
		return changeDate;
	}

	public void setChangeDate(Calendar changeDate)
	{
		this.changeDate = changeDate;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
