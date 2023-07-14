package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.loans.conditions.LoanCondition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ��������� ������� �������������
 * @author Dorzhinov
 * @ created 19.05.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ���������, ����������, �������� �������, ����������
 * �������������� ����� loanProductEquals ������ LoanProductHelper.
 */
public class ModifiableLoanProduct extends LoanProductBase
{
	private YearMonth minDuration = new YearMonth();     //���. ���� �������
	private MaxDuration maxDuration = new MaxDuration(); //����. ���� �������
	private Boolean isMaxDurationInclude;           //"������������" ��� ����. �����
	private Boolean needInitialInstalment;          //��������� �� �������������� �����
	private BigDecimal minInitialInstalment;        //���. ����. ����� � %
	private BigDecimal maxInitialInstalment;        //����. ����. ����� � %
	private Boolean isMaxInitialInstalmentInclude;  //"������������" ��� ����. ����. ������
	private Boolean needSurety;                     //��������� �� �����������
	private String additionalTerms;                 //���. �������
	private Publicity publicity;                    //�������� �������
	private List<Department> terbanks = new ArrayList<Department>();
	private List<LoanCondition> conditions = new ArrayList<LoanCondition>(); //������� � ������� �����

	public YearMonth getMinDuration()
	{
		return minDuration;
	}

	public MaxDuration getMaxDuration()
	{
		return maxDuration;
	}

	public void setMinDuration(YearMonth minDuration)
	{
		if (minDuration == null)
			this.minDuration = new YearMonth();
		else
			this.minDuration = minDuration;
	}

	public void setMaxDuration(MaxDuration maxDuration)
	{
		if (maxDuration == null)
			this.maxDuration = new MaxDuration();
		else
			this.maxDuration = maxDuration;
	}

	public Boolean isMaxDurationInclude()
	{
		return isMaxDurationInclude;
	}

	public Boolean getMaxDurationInclude()
	{
		return isMaxDurationInclude();
	}

	public void setMaxDurationInclude(Boolean maxDurationInclude)
	{
		isMaxDurationInclude = maxDurationInclude;
	}

	public Boolean isNeedInitialInstalment()
	{
		return needInitialInstalment;
	}

	public Boolean getNeedInitialInstalment()
	{
		return isNeedInitialInstalment();
	}

	public void setNeedInitialInstalment(Boolean needInitialInstalment)
	{
		this.needInitialInstalment = needInitialInstalment;
	}

	public BigDecimal getMinInitialInstalment()
	{
		return minInitialInstalment;
	}

	public void setMinInitialInstalment(BigDecimal minInitialInstalment)
	{
		this.minInitialInstalment = minInitialInstalment;
	}

	public BigDecimal getMaxInitialInstalment()
	{
		return maxInitialInstalment;
	}

	public void setMaxInitialInstalment(BigDecimal maxInitialInstalment)
	{
		this.maxInitialInstalment = maxInitialInstalment;
	}

	public Boolean isMaxInitialInstalmentInclude()
	{
		return isMaxInitialInstalmentInclude;
	}

	public Boolean getMaxInitialInstalmentInclude()
	{
		return isMaxInitialInstalmentInclude();
	}

	public void setMaxInitialInstalmentInclude(Boolean maxInitialInstalmentInclude)
	{
		isMaxInitialInstalmentInclude = maxInitialInstalmentInclude;
	}

	public Boolean isNeedSurety()
	{
		return needSurety;
	}

	public Boolean getNeedSurety()
	{
		return isNeedSurety();
	}

	public void setNeedSurety(Boolean needSurety)
	{
		this.needSurety = needSurety;
	}

	public String getAdditionalTerms()
	{
		return additionalTerms;
	}

	public void setAdditionalTerms(String additionalTerms)
	{
		this.additionalTerms = additionalTerms;
	}

	public Publicity getPublicity()
	{
		return publicity;
	}

	public void setPublicity(Publicity publicity)
	{
		this.publicity = publicity;
	}

	public List<Department> getTerbanks()
	{
		return terbanks;
	}

	public void setTerbanks(List<Department> terbanks)
	{
		this.terbanks = terbanks;
	}

	public List<LoanCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<LoanCondition> conditions)
	{
		this.conditions = conditions;
	}
}
