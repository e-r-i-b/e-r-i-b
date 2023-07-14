package com.rssl.phizic.business.dictionaries.finances;

/**
 * Класс описывающий выписку по категории для графика "Структура финансов по категориям"
 * @author rydvanskiy
 * @ created 01.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationCategoryGraphAbstract
{
	// Идентификатор категории
	private Long id;
	// имя категория
	private String name;
	// Сумма операций по категории в национальной валюте
	private Double categorySum;
	private Double visibleSum;
	private Double budgetSum;
	private boolean avgBudget;
	private boolean incomeType;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Double getCategorySum()
	{
		return categorySum;
	}

	public void setCategorySum(Double categorySum)
	{
		this.categorySum = categorySum;
	}

	public Double getBudgetSum()
	{
		return budgetSum;
	}

	public void setBudgetSum(Double budgetSum)
	{
		this.budgetSum = budgetSum;
	}

	public boolean getAvgBudget()
	{
		return avgBudget;
	}

	public void setAvgBudget(boolean avgBudget)
	{
		this.avgBudget = avgBudget;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CardOperationCategoryGraphAbstract that = (CardOperationCategoryGraphAbstract) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	/**
	 * Получить сумма по тем операциям, у которых нет статуса "скрытая"
	 * @return сумма
	 */
	public Double getVisibleSum()
	{
		return visibleSum;
	}

	/**
	 * Установить сумму по "нескрытым" операциям
	 * @param visibleSum сумма
	 */
	public void setVisibleSum(Double visibleSum)
	{
		this.visibleSum = visibleSum;
	}

	public boolean isIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(boolean incomeType)
	{
		this.incomeType = incomeType;
	}
}
