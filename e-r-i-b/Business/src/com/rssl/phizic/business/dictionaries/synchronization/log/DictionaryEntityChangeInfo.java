package com.rssl.phizic.business.dictionaries.synchronization.log;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 21.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����� ����� ��������� ������������
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
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���������� ���� ��������
	 */
	public String getUid()
	{
		return uid;
	}

	/**
	 * ������ ���������� ���� ��������
	 * @param uid ���������� ���� ��������
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
	}

	/**
	 * @return ��� ����������� ��������
	 */
	public String getDictionaryType()
	{
		return dictionaryType;
	}

	/**
	 * ������ ��� ����������� ��������
	 * @param dictionaryType ��� ����������� ��������
	 */
	public void setDictionaryType(String dictionaryType)
	{
		this.dictionaryType = dictionaryType;
	}

	/**
	 * @return ��� ���������
	 */
	public ChangeType getChangeType()
	{
		return changeType;
	}

	/**
	 * ������ ��� ���������
	 * @param changeType ��� ���������
	 */
	public void setChangeType(ChangeType changeType)
	{
		this.changeType = changeType;
	}

	/**
	 * @return ���� ���������� ����������
	 */
	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * ������ ���� ���������� ����������
	 * @param updateDate ���� ���������� ����������
	 */
	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	/**
	 * @return ������ ��������
	 */
	public String getEntityData()
	{
		return entityData;
	}

	/**
	 * ������ ������ ��������
	 * @param entityData ������ ��������
	 */
	public void setEntityData(String entityData)
	{
		this.entityData = entityData;
	}
}
