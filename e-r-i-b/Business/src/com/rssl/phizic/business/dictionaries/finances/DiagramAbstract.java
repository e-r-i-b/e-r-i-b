package com.rssl.phizic.business.dictionaries.finances;

import java.util.Calendar;

/**
 * @author Balovtsev
 * @since 25.12.2014.
 */
public class DiagramAbstract
{
	private Double   incomeAmount;
	private Double   outcomeAmount;
	private Boolean  incomeType;
	private Calendar operationDate;

	public DiagramAbstract(Boolean incomeType, Double amount, Calendar operationDate)
	{
		if (incomeType)
		{
			this.incomeAmount  = amount;
			this.outcomeAmount  = new Double(0);
		}
		else
		{
			this.outcomeAmount = amount;
			this.incomeAmount =  new Double(0);
		}

		this.incomeType    = incomeType;
		this.operationDate = operationDate;
	}

	public Double getIncomeAmount()
	{
		return incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount)
	{
		this.incomeAmount = incomeAmount;
	}

	public Double getOutcomeAmount()
	{
		return outcomeAmount;
	}

	public void setOutcomeAmount(Double outcomeAmount)
	{
		this.outcomeAmount = outcomeAmount;
	}

	public Boolean isIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(Boolean incomeType)
	{
		this.incomeType = incomeType;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

    public boolean hasSameYearAndMonth(DiagramAbstract other)
    {
        if(other == null || other.getOperationDate() == null || this.getOperationDate() == null)
            return false;

        return this.getOperationDate().get(Calendar.YEAR) == other.getOperationDate().get(Calendar.YEAR)
                && this.getOperationDate().get(Calendar.MONTH) == other.getOperationDate().get(Calendar.MONTH);
	}
}
