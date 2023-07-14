package com.rssl.phizic.logging.finances.recategorization;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 * ������ � ����������/���������� ������� �����������������
 */
public class ALFRecategorizationRuleEntry implements Serializable
{
	private Long id;
	private Calendar date;
	private String description;
	private Long mccCode;
	private String originalCategory;
	private String newCategory;
	private ALFRecategorizationRuleEntryType type;
	private int count;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� ����������/���������� ������� �����������������
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - ���� ����������/���������� ������� �����������������
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return �������� ��������, ��� ������� ���� ������� ������� �����������������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - �������� ��������, ��� ������� ���� ������� ������� �����������������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ���-��� ��������, ��� ������� ���� ������� ������� �����������������
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - ���-��� ��������, ��� ������� ���� ������� ������� �����������������
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return �������� �������� ���������
	 */
	public String getOriginalCategory()
	{
		return originalCategory;
	}

	/**
	 * @param originalCategory - �������� �������� ���������
	 */
	public void setOriginalCategory(String originalCategory)
	{
		this.originalCategory = originalCategory;
	}

	/**
	 * @return �������� ����� ���������
	 */
	public String getNewCategory()
	{
		return newCategory;
	}

	/**
	 * @param newCategory - �������� ����� ���������
	 */
	public void setNewCategory(String newCategory)
	{
		this.newCategory = newCategory;
	}

	/**
	 * @return ��� �������� (���������� ��� ���������� ������� �����������������)
	 */
	public ALFRecategorizationRuleEntryType getType()
	{
		return type;
	}

	/**
	 * @param type - ��� ��������
	 */
	public void setType(ALFRecategorizationRuleEntryType type)
	{
		this.type = type;
	}

	/**
	 * @return ���������� ����������� �������� (��������, ���������� �������� ��� ������� ����������� ������� �����������������)
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * @param count - ���������� ����������� ��������
	 */
	public void setCount(int count)
	{
		this.count = count;
	}
}
