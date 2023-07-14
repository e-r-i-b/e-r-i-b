package com.rssl.phizic.business.loans.kinds;

/**
 * @author gladishev
 * @ created 17.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanKind
{
	private Long id;
	private String name;
	private Boolean calculator; //есть ли кредитный калькулятор для данного вида кредита
	private Boolean target; //true - целевой кредит
	private String description;
	private String claimDescription;
	private String detailsTemplate;
	private String upperName;

	public Long getId ()
	{
		return id;
	}

	public void setId ( Long id )
	{
		this.id = id;
	}

	/**
	 *Наименование вида кредита
	 */
	public String getName ()
	{
		return name;
	}

	public void setName ( String name )
	{
		this.name = name;
	}

	/**
	 * краткое описание вида кредита
	 */
	public String getDescription ()
	{
		return description;
	}

	public void setDescription ( String description )
	{
		this.description = description;
	}

	/**
	 *XML файл, описывающий заявку, связанную с этим видом кредита.
	 */
	public String getClaimDescription ()
	{
		return claimDescription;
	}

	public void setClaimDescription ( String claimDescription )
	{
		this.claimDescription = claimDescription;
	}

	public String toString()
	{
		return name;
	}

	public Boolean isCalculator()
	{
		return calculator;
	}

	public void setCalculator(Boolean calculator)
	{
		this.calculator = calculator;
	}

	public Boolean isTarget()
	{
		return target;
	}

	public void setTarget(Boolean target)
	{
		this.target = target;
	}

	public void setDetailsTemplate(String detailsTemplate)
	{
		this.detailsTemplate = detailsTemplate;
	}

	public String getDetailsTemplate()
	{
		return detailsTemplate;
	}

	/**
	 * Имя продукта в верхнем регистре
	 * @return
	 */
	public String getUpperName()
	{
		return upperName;
	}

	public void setUpperName(String upperName)
	{
		this.upperName = upperName;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof LoanKind))
			return false;

		LoanKind loanKind = (LoanKind) o;

		if (!name.equals(loanKind.name))
			return false;

		return true;
	}

	public int hashCode()
	{
		return name.hashCode();
	}
}
