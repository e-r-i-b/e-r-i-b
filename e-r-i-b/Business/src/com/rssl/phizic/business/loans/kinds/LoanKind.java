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
	private Boolean calculator; //���� �� ��������� ����������� ��� ������� ���� �������
	private Boolean target; //true - ������� ������
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
	 *������������ ���� �������
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
	 * ������� �������� ���� �������
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
	 *XML ����, ����������� ������, ��������� � ���� ����� �������.
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
	 * ��� �������� � ������� ��������
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
