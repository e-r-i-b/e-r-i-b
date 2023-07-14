package com.rssl.phizic.business.dictionaries.pfp.period;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * "горизонт инвестирования"
 */

public class InvestmentPeriod extends PFPDictionaryRecord
{
	private Long id;
	private String period;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * необходим только при репликации
	 * @return ключ записи
	 */
	@Override public Comparable getSynchKey()
	{
		return period;
	}

	/**
	 * Обновить содержимое записи из другой
	 * @param that запись из которой надо обновить
	 */
	public void updateFrom(DictionaryRecord that)
	{
		setPeriod(((InvestmentPeriod)that).getPeriod());
	}

	/**
	 * @return период
	 */
	public String getPeriod()
	{
		return period;
	}

	/**
	 * задать период
	 * @param period период
	 */
	public void setPeriod(String period)
	{
		this.period = period;
	}
}
