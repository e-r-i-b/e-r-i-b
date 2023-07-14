package com.rssl.phizic.business.mobileOperators;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Mescheryakova
 * @ created 04.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * Сущность, реализующая мобильного оператора в системе, загружаемого из справочника ЦАС НСИ
 *
 */

public class MobileOperator extends DictionaryRecordBase
{
	private Long id;     // идентификатор
	private Long code;   // код оператора, пришедший из цас нси
	private String name; // название моб. оператора
	private boolean flAuto; // признак приема автоплатежей: true - принимает, false - не принимает
	private Integer balance; // остаток на балансе телефона, при котором безакцептно со счета карты должна списываться сумма для его пополнении, при предоплатной схеме автоплатежа
	private Integer minSumm; // минимальная сумма автоплатежа (в рублях)
	private Integer maxSumm; // максимальная сумма автоплатежа (в рублях)

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
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

	public Comparable getSynchKey()
	{
		return code;
	}

	/**
	 * @return признак приема автоплатежей
	 */
	public boolean isFlAuto()
	{
		return flAuto;
	}

	/**
	 * Задать признак приема автоплатежей
	 * @param flAuto - признак
	 */
	public void setFlAuto(boolean flAuto)
	{
		this.flAuto = flAuto;
	}

	/**
	 * @return остаток на балансе телефона, при котором безакцептно со счета карты должна списываться сумма для его пополнении
	 */
	public Integer getBalance()
	{
		return balance;
	}

	/**
	 * Задать остаток на балансе телефона, при котором безакцептно со счета карты должна списываться сумма для его пополнении
	 * @param balance - остаток
	 */
	public void setBalance(Integer balance)
	{
		this.balance = balance;
	}

	/**
	 * @return минимальную сумму автоплатежа
	 */
	public Integer getMinSumm()
	{
		return minSumm;
	}

	/**
	 * Задать минимальную сумму автоплатежа
	 * @param minSumm - сумма
	 */
	public void setMinSumm(Integer minSumm)
	{
		this.minSumm = minSumm;
	}

	/**
	 * @return максимальную сумму автоплатежа
	 */
	public Integer getMaxSumm()
	{
		return maxSumm;
	}

	/**
	 * Задать максимальную сумму автоплатежа
	 * @param maxSumm - сумма
	 */
	public void setMaxSumm(Integer maxSumm)
	{
		this.maxSumm = maxSumm;
	}
}
