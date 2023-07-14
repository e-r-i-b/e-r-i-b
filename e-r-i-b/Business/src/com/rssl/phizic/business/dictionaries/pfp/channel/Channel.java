package com.rssl.phizic.business.dictionaries.pfp.channel;

import com.rssl.phizic.business.dictionaries.pfp.common.MarkDeletedRecord;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

/**
 * Сущность канал
 * @author komarov
 * @ created 09.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class Channel extends MultiBlockDictionaryRecordBase implements MarkDeletedRecord
{
	private Long id;
	private String name;
	private boolean deleted;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return имя канала
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name имя канала
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @return канал удалён да/нет(true/false)
	 */
	public boolean isDeleted()
	{
		return deleted;
	}
}
