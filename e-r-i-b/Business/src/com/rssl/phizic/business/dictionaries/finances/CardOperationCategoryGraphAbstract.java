package com.rssl.phizic.business.dictionaries.finances;

/**
 * ����� ����������� ������� �� ��������� ��� ������� "��������� �������� �� ����������"
 * @author rydvanskiy
 * @ created 01.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationCategoryGraphAbstract
{
	// ������������� ���������
	private Long id;
	// ��� ���������
	private String name;
	// ����� �������� �� ��������� � ������������ ������
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
	 * �������� ����� �� ��� ���������, � ������� ��� ������� "�������"
	 * @return �����
	 */
	public Double getVisibleSum()
	{
		return visibleSum;
	}

	/**
	 * ���������� ����� �� "���������" ���������
	 * @param visibleSum �����
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
