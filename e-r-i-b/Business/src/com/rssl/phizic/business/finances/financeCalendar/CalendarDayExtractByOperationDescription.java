package com.rssl.phizic.business.finances.financeCalendar;

import java.math.BigDecimal;

/**
 * @author lepihina
 * @ created 25.04.14
 * $Author$
 * $Revision$
 * ������� �� ��������� ��� ����������� ���������
 */
public class CalendarDayExtractByOperationDescription
{
	private Long id;
	private String description;
	private String categoryName;
	private String categoryColor;
	private BigDecimal amount;

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
	 * @return �������� ���������
	 */
	public String getCategoryName()
	{
		return categoryName;
	}

	/**
	 * @param categoryName - �������� ���������
	 */
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	/**
	 * @return ���� ���������
	 */
	public String getCategoryColor()
	{
		return categoryColor;
	}

	/**
	 * @param categoryColor - ���� ���������
	 */
	public void setCategoryColor(String categoryColor)
	{
		this.categoryColor = categoryColor;
	}

	/**
	 * @return ����� �������� � ������������ ������
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - ����� �������� � ������������ ������
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
