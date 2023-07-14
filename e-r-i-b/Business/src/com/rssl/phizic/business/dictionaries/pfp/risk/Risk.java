package com.rssl.phizic.business.dictionaries.pfp.risk;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сущность риска
 */

public class Risk extends PFPDictionaryRecord
{
	private Long id;
	private String name;

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
		return name;
	}

	/**
	 * Обновить содержимое записи из другой
	 * @param that запись из которой надо обновить
	 */
	public void updateFrom(DictionaryRecord that)
	{
		setName(((Risk)that).getName());
	}

	/**
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
