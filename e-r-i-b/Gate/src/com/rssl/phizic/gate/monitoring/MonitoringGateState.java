package com.rssl.phizic.gate.monitoring;

/**
 * @author akrenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * —осто€ни€ мониторинга сервисов внешней системы
 */

public enum MonitoringGateState
{
	NORMAL(0, "—тандартный"),           //обычный режим
	DEGRADATION(1, "ƒеградированный"),  //деградированный режим
	INACCESSIBLE(2, "Ќедоступный");     //недоступный режим

	private int rang; // ранг состо€ни€. Ќеобходим дл€ задани€ пор€дка состо€ний
	private final String description; // описание состо€ни€

	MonitoringGateState(int rang, String description)
	{
		this.rang = rang;
		this.description = description;
	}

	/**
	 * @return ранг состо€ни€ сервиса
	 */
	public int getRang()
	{
		return rang;
	}

	/**
	 * @return описание состо€ни€
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * —равнение с другим состо€нием сервиса.
	 * @param otherState - другое состо€ние сервиса
	 * @return  <0 текущее состо€ние меньше переданного. ѕереход из текущего состо€нее в переданное возможен.
	 *          0 - состо€ни€ равны
	 *          >0 текущее состо€ние больше переданного. ѕереход из текущего состо€нее в переданное Ќ≈ возможен.
	 */
	public int compare(MonitoringGateState otherState)
	{
		return this.rang - otherState.getRang();
	}
}
