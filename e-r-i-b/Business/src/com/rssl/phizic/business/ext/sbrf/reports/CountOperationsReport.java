package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Общая информация отчетов по бизнес-операциям, где требуется для вывода тип опреации, валюта, сумма
 */
public  abstract class CountOperationsReport extends CountOperations
{
	private String type;           // тип (название) бизнес-операции
	private String currency;       
	private float amount;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public float getAmount()
	{
		return amount;
	}

	public void setAmount(float amount)
	{
		this.amount = amount;
	}

	/**
	 * Узнает, строится ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */
	public boolean isIds()
	{
		return true;
	}

	/**
	 * Получаем допольнительные параметры для sql-запроса
	 * @param report - отчет
	 * @return хеш вида "ключ => значение", кот. потом будут подставляться в sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> map = super.getAdditionalParams(report);
		map.putAll(sqlParamsDescription);     // добавляем параметры для поиску в userlog по description 

		return map;
	}
}
