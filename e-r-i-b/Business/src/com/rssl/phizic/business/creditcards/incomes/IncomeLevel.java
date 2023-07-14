package com.rssl.phizic.business.creditcards.incomes;

import com.rssl.phizic.business.creditcards.conditions.IncomeCondition;
import com.rssl.phizic.business.creditcards.conditions.IncomeConditionComparator;
import com.rssl.phizic.common.types.Money;

import java.util.Collections;
import java.util.List;

/**
 * Соответствие уровня дохода клиента доступным кредитным лимитам (доступным продуктам)
 * @author Dorzhinov
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class IncomeLevel
{
	private Long id;
	private Money minIncome;            //мин. доход
	private Money maxIncome;            //макс. доход
	private Boolean isMaxIncomeInclude; //"включительно" для макс. дохода
	private List<IncomeCondition> conditions;   //условия в разрезе валют

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Money getMinIncome()
	{
		return minIncome;
	}

	public void setMinIncome(Money minIncome)
	{
		this.minIncome = minIncome;
	}

	public Money getMaxIncome()
	{
		return maxIncome;
	}

	public void setMaxIncome(Money maxIncome)
	{
		this.maxIncome = maxIncome;
	}

	public Boolean isMaxIncomeInclude()
	{
		return isMaxIncomeInclude;
	}

	public Boolean getMaxIncomeInclude()
	{
		return isMaxIncomeInclude();
	}

	public void setMaxIncomeInclude(Boolean maxIncomeInclude)
	{
		isMaxIncomeInclude = maxIncomeInclude;
	}

	public List<IncomeCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<IncomeCondition> conditions)
	{
		Collections.sort(conditions, new IncomeConditionComparator());
		this.conditions = conditions;
	}
}
