package com.rssl.phizic.business.finances.financeCalendar;

import java.math.BigDecimal;

/**
 * @author lepihina
 * @ created 25.04.14
 * $Author$
 * $Revision$
 * ¬ыписка по операци€м дл€ финансового календар€
 */
public class CalendarDayExtractByOperationDescription
{
	private Long id;
	private String description;
	private String categoryName;
	private String categoryColor;
	private BigDecimal amount;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return описание операции
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - описание операции
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return название категории
	 */
	public String getCategoryName()
	{
		return categoryName;
	}

	/**
	 * @param categoryName - название категории
	 */
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	/**
	 * @return цвет категории
	 */
	public String getCategoryColor()
	{
		return categoryColor;
	}

	/**
	 * @param categoryColor - цвет категории
	 */
	public void setCategoryColor(String categoryColor)
	{
		this.categoryColor = categoryColor;
	}

	/**
	 * @return сумма операции в национальной валюте
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - сумма операции в национальной валюте
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
