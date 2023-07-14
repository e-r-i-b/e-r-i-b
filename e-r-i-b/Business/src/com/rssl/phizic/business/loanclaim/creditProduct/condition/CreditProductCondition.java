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
 * Общие условие по кредитному продукту.
 */
public class CreditProductCondition implements Entity
{
	@JsonExclusion
	private Long id;
	/**
	 * Тип кредитного продукта.
	 */
	private CreditProductType creditProductType;
	/**
	 * Кредитный продукт.
	 */
	private CreditProduct creditProduct;
	/**
	 * Департаменты для которых действует данное условия.(Храним в виде строки, разделитель "|")
	 */
	private String departmentsStr;
	/**
	 * Возможно рассмотрение через "Transact SM"
	 */
	private boolean transactSMPossibility = true;
	/**
	 *Мин. срок кредита
	 */
	private YearMonth minDuration = new YearMonth();
	/**
	 *Макс. срок кредита
	 */
	private MaxDuration maxDuration = new MaxDuration();
	/**
	 * Включительно ли с верхней границей диапазон.
	 */
	private boolean maxRangeInclude = true;
	/**
	 * Первоночальный взнос.
	 */
	private boolean useInitialFee;
	/**
	 * Минимальная первоначальный взнос в процентах. (Заполняется если initialFee == true)
	 */
	private BigDecimal minInitialFee;
	/**
	 * Максимальный первоначальный взнос в процентах. (Заполняется если initialFee == true)
	 */
	private BigDecimal maxInitialFee;
	/**
	 * Признак того что включительно верхней границы максимального взноса.
	 */
	private boolean includeMaxInitialFee = true;
	/**
	 * Дополнительные условия.
	 */
	@JsonExclusion
	private  String additionalConditions;
	/**
	 * * По валютные условия.
	 */
	@JsonExclusion
	private Set<CurrencyCreditProductCondition> currConditions;
	/**
	 * Состояние условия.
	 */
	private boolean published;
	/**
	 * Доступен в меню выбора
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
	 * @param departmentsList Список tb.
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
	 * @return Список tb.
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
