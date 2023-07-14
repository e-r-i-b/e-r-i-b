package com.rssl.phizic.business.finances.recategorization;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

/**
 * @author lepihina
 * @ created 28.03.14
 * $Author$
 * $Revision$
 * ������� ����������������� ��� ��������
 */
public class ALFRecategorizationRule
{
	private Long id;
	private Long loginId;
	private String description;
	private Long mccCode;
	private CardOperationCategory newCategory;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ����� �������, �������� ����������� ������� �����������������
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId - ����� �������, �������� ����������� ������� �����������������
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return �������� ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - �������� ��������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ���-��� ��������
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - ���-��� ��������
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return ������������� ���������, �� ������� ��������� �����������������
	 */
	public CardOperationCategory getNewCategory()
	{
		return newCategory;
	}

	/**
	 * @param newCategory - ������������� ���������, �� ������� ��������� �����������������
	 */
	public void setNewCategory(CardOperationCategory newCategory)
	{
		this.newCategory = newCategory;
	}
}
