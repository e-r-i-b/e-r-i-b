package com.rssl.phizic.logging.finances.category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ������ ������� ���������� �� ��������� �������� ���������
 * @author lepihina
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class CardOperationCategoryChangingLogEntry  implements Serializable
{
	private Long id;
	private Calendar date;
	private String TB;
	private String VSP;
	private String FIO;
	private String operationName;
	private Long mccCode;
	private BigDecimal amount;
	private String parentCategory;
	private String newCategories;
	private Integer newCategoriesCount;

	/**
	 * @return id ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - id ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� �������������� ��������
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - ���� �������������� ��������
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return �� �������
	 */
	public String getTB()
	{
		return TB;
	}

	/**
	 * @param TB - �� �������
	 */
	public void setTB(String TB)
	{
		this.TB = TB;
	}

	/**
	 * @return ��� �������
	 */
	public String getVSP()
	{
		return VSP;
	}

	/**
	 * @param VSP - ��� �������
	 */
	public void setVSP(String VSP)
	{
		this.VSP = VSP;
	}

	/**
	 * @return ��� �������
	 */
	public String getFIO()
	{
		return FIO;
	}

	/**
	 * @param FIO - ��� �������
	 */
	public void setFIO(String FIO)
	{
		this.FIO = FIO;
	}

	/**
	 * @return �������� ������������� ��������
	 */
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * @param operationName - �������� ������������� ��������
	 */
	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	/**
	 * @return ���-��� ������������� ��������
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - ���-��� ������������� ��������
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return ����� (� ������������ ������) ������������� ��������
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - ����� (� ������������ ������) ������������� ��������
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return �������� ��������� ������������� ��������
	 */
	public String getParentCategory()
	{
		return parentCategory;
	}

	/**
	 * @param parentCategory - �������� ��������� ������������� ��������
	 */
	public void setParentCategory(String parentCategory)
	{
		this.parentCategory = parentCategory;
	}

	/**
	 * @return ����� ��������� ������������� �������� (����, ���� ��������� � ������ ���������; ��������� - ���� ������� ��������)
	 */
	public String getNewCategories()
	{
		return newCategories;
	}

	/**
	 * @param newCategories - ����� ��������� ������������� �������� (����, ���� ��������� � ������ ���������; ��������� - ���� ������� ��������)
	 */
	public void setNewCategories(String newCategories)
	{
		this.newCategories = newCategories;
	}

	/**
	 * @return ���������� ����� ���������
	 */
	public Integer getNewCategoriesCount()
	{
		return newCategoriesCount;
	}

	/**
	 * @param newCategoriesCount - ���������� ����� ���������
	 */
	public void setNewCategoriesCount(Integer newCategoriesCount)
	{
		this.newCategoriesCount = newCategoriesCount;
	}
}
