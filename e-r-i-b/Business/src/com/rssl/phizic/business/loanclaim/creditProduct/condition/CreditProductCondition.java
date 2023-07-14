package com.rssl.phizic.business.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loans.products.MaxDuration;
import com.rssl.phizic.business.loans.products.YearMonth;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.common.types.annotation.JsonExclusion;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Moshenko
 * @ created 13.01.2014
 * @ $Author$
 * @ $Revision$
 * ����� ������� �� ���������� ��������.
 */
public class CreditProductCondition implements Entity
{
	@JsonExclusion
	private Long id;
	/**
	 * ��� ���������� ��������.
	 */
	private CreditProductType creditProductType;
	/**
	 * ��������� �������.
	 */
	private CreditProduct creditProduct;
	/**
	 * ������������ ��� ������� ��������� ������ �������.(������ � ���� ������, ����������� "|")
	 */
	private String departmentsStr;
	/**
	 * �������� ������������ ����� "Transact SM"
	 */
	private boolean transactSMPossibility = true;
	/**
	 *���. ���� �������
	 */
	private YearMonth minDuration = new YearMonth();
	/**
	 *����. ���� �������
	 */
	private MaxDuration maxDuration = new MaxDuration();
	/**
	 * ������������ �� � ������� �������� ��������.
	 */
	private boolean maxRangeInclude = true;
	/**
	 * �������������� �����.
	 */
	private boolean useInitialFee;
	/**
	 * ����������� �������������� ����� � ���������. (����������� ���� initialFee == true)
	 */
	private BigDecimal minInitialFee;
	/**
	 * ������������ �������������� ����� � ���������. (����������� ���� initialFee == true)
	 */
	private BigDecimal maxInitialFee;
	/**
	 * ������� ���� ��� ������������ ������� ������� ������������� ������.
	 */
	private boolean includeMaxInitialFee = true;
	/**
	 * �������������� �������.
	 */
	@JsonExclusion
	private  String additionalConditions;
	/**
	 * * �� �������� �������.
	 */
	@JsonExclusion
	private Set<CurrencyCreditProductCondition> currConditions;
	/**
	 * ��������� �������.
	 */
	private boolean published;
	/**
	 * �������� � ���� ������
	 */
	private boolean selectionAvaliable;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CreditProductType getCreditProductType()
	{
		return creditProductType;
	}

	public void setCreditProductType(CreditProductType creditProductType)
	{
		this.creditProductType = creditProductType;
	}

	public CreditProduct getCreditProduct()
	{
		return creditProduct;
	}

	public void setCreditProduct(CreditProduct creditProduct)
	{
		this.creditProduct = creditProduct;
	}

	public String getDepartmentsStr()
	{
		return departmentsStr;
	}

	public void setDepartmentsStr(String departmentsStr)
	{
		this.departmentsStr = departmentsStr;
	}

	/**
	 * @param departmentsList ������ tb.
	 */
	public void setDepartmentsList(List<String> departmentsList)
	{
		StringBuilder sb = new StringBuilder();
		int size = departmentsList.size();
		for(int i=0; i < size; i++)
		{
			sb.append(departmentsList.get(i));
			if (i != size - 1)
				sb.append("|");
		}
		departmentsStr = sb.toString();
	}

	/**
	 * @return ������ tb.
	 */
	public List<String> getDepartmentsList()
	{
		if (StringHelper.isEmpty(departmentsStr))
		{
			return Collections.emptyList();
		}

		return Arrays.asList(StringUtils.split(departmentsStr,"|"));
	}



	public boolean isTransactSMPossibility()
	{
		return transactSMPossibility;
	}

	public void setTransactSMPossibility(boolean transactSMPossibility)
	{
		this.transactSMPossibility = transactSMPossibility;
	}

	public MaxDuration getMaxDuration()
	{
		return maxDuration;
	}

	public void setMaxDuration(MaxDuration maxDuration)
	{
		this.maxDuration = maxDuration;
	}

	public YearMonth getMinDuration()
	{
		return minDuration;
	}

	public void setMinDuration(YearMonth minDuration)
	{
		this.minDuration = minDuration;
	}

	public boolean isMaxRangeInclude()
	{
		return maxRangeInclude;
	}

	public void setMaxRangeInclude(boolean maxRangeInclude)
	{
		this.maxRangeInclude = maxRangeInclude;
	}

	public boolean isUseInitialFee()
	{
		return useInitialFee;
	}

	public void setUseInitialFee(boolean useInitialFee)
	{
		this.useInitialFee = useInitialFee;
	}

	public BigDecimal getMinInitialFee()
	{
		return minInitialFee;
	}

	public void setMinInitialFee(BigDecimal minInitialFee)
	{
		this.minInitialFee = minInitialFee;
	}

	public BigDecimal getMaxInitialFee()
	{
		return maxInitialFee;
	}

	public void setMaxInitialFee(BigDecimal maxInitialFee)
	{
		this.maxInitialFee = maxInitialFee;
	}

	public boolean isIncludeMaxInitialFee()
	{
		return includeMaxInitialFee;
	}

	public void setIncludeMaxInitialFee(boolean includeMaxInitialFee)
	{
		this.includeMaxInitialFee = includeMaxInitialFee;
	}

	public String getAdditionalConditions()
	{
		return additionalConditions;
	}

	public void setAdditionalConditions(String additionalConditions)
	{
		this.additionalConditions = additionalConditions;
	}

	public Set<CurrencyCreditProductCondition> getCurrConditions()
	{
		return currConditions;
	}

	public void setCurrConditions(Set<CurrencyCreditProductCondition> currConditions)
	{
		this.currConditions = currConditions;
	}

	public boolean isPublished()
	{
		return published;
	}

	public void setPublished(boolean published)
	{
		this.published = published;
	}

	public boolean isSelectionAvaliable()
	{
		return selectionAvaliable;
	}

	public void setSelectionAvaliable(boolean selectionAvaliable)
	{
		this.selectionAvaliable = selectionAvaliable;
	}
}
