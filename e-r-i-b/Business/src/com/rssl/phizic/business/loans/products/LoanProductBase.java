package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.business.loans.kinds.LoanKind;

import java.util.Calendar;

/**
 * @author Dorzhinov
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoanProductBase
{
	private Long id;
	private String name;
	private LoanKind loanKind;
	private Calendar changeDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Имя кредитного продукта
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Id вида кредита ИКФЛ
	 */
	public LoanKind getLoanKind()
	{
		return loanKind;
	}

	public void setLoanKind(LoanKind loanKind)
	{
		this.loanKind = loanKind;
	}

	/**
	 * Дата изменения продукта (необходима для отслеживания изменений продукта)
	 * @return - Дата изменения продукта
	 */
	public Calendar getChangeDate()
	{
		return changeDate;
	}

	public void setChangeDate(Calendar changeDate)
	{
		this.changeDate = changeDate;
	}

	public String toString()
	{
		return name;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof LoanProductBase))
			return false;

		LoanProductBase that = (LoanProductBase) o;

		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		return name != null ? name.hashCode() : 0;
	}
}
