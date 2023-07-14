package com.rssl.phizic.business.dictionaries.pfp.common;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс записей справочников ПФП
 */

public abstract class PFPDictionaryRecord extends MultiBlockDictionaryRecordBase implements DictionaryRecord
{
	private Long id;

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор записи
	 * @param id идентификатор записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * задать в соответствие записи картинку
	 * нужно при репликации
	 * @param id идентификатор картинки
	 */
	@SuppressWarnings({"NoopMethodInAbstractClass"})
	public void setImageId(Long id){}

	/**
	 * нужно при репликации
	 * @return идентификатор картинки записи
	 */
	public Long getImageId()
	{
		return null;
	}

	/**
	 * необходим только при репликации
	 * @return ключ записи
	 */
	public abstract Comparable getSynchKey();
}
