package com.rssl.phizic.business.dictionaries.synchronization.log;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 21.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Общий класс логов изменения справочников
 */

public class DictionaryEntityChangeInfo
{
	private Long id;
	private String uid;
	private String dictionaryType;
	private ChangeType changeType;
	private Calendar updateDate;
	private String entityData;

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
	 * @return уникальный ключ сущности
	 */
	public String getUid()
	{
		return uid;
	}

	/**
	 * задать уникальный ключ сущности
	 * @param uid уникальный ключ сущности
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
	}

	/**
	 * @return тип справочника сущности
	 */
	public String getDictionaryType()
	{
		return dictionaryType;
	}

	/**
	 * задать тип справочника сущности
	 * @param dictionaryType тип справочника сущности
	 */
	public void setDictionaryType(String dictionaryType)
	{
		this.dictionaryType = dictionaryType;
	}

	/**
	 * @return тип изменения
	 */
	public ChangeType getChangeType()
	{
		return changeType;
	}

	/**
	 * задать тип изменения
	 * @param changeType тип изменения
	 */
	public void setChangeType(ChangeType changeType)
	{
		this.changeType = changeType;
	}

	/**
	 * @return дата последнего обновления
	 */
	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * задать дату последнего обновления
	 * @param updateDate дата последнего обновления
	 */
	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	/**
	 * @return данные сущности
	 */
	public String getEntityData()
	{
		return entityData;
	}

	/**
	 * задать данные сущности
	 * @param entityData данные сущности
	 */
	public void setEntityData(String entityData)
	{
		this.entityData = entityData;
	}
}
