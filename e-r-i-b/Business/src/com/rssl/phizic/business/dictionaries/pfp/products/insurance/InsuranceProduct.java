package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 * Страховой продукт
 */
public class InsuranceProduct extends ProductBase
{
	private InsuranceCompany insuranceCompany; //страховая компания
	private InsuranceType type;                //тип страхования
	private boolean forComplex = false;        //страховой продукт является частью комплексного продукта
	private Long minAge;                       //минимальный возраст
	private Long maxAge;                       //максимальный возраст
	private List<InsuranceDatePeriod> periods; //Периоды

	public InsuranceCompany getInsuranceCompany()
	{
		return insuranceCompany;
	}

	public void setInsuranceCompany(InsuranceCompany insuranceCompany)
	{
		this.insuranceCompany = insuranceCompany;
	}

	public InsuranceType getType()
	{
		return type;
	}

	public void setType(InsuranceType type)
	{
		this.type = type;
	}

	public boolean isForComplex()
	{
		return forComplex;
	}

	public void setForComplex(boolean forComplex)
	{
		this.forComplex = forComplex;
	}

	public Long getMinAge()
	{
		return minAge;
	}

	public void setMinAge(Long minAge)
	{
		this.minAge = minAge;
	}

	public Long getMaxAge()
	{
		return maxAge;
	}

	public void setMaxAge(Long maxAge)
	{
		this.maxAge = maxAge;
	}

	public List<InsuranceDatePeriod> getPeriods()
	{
		return periods;
	}

	public void setPeriods(List<InsuranceDatePeriod> periods)
	{
		this.periods = periods;
	}

	/**
	 * добавить информацию о периоде
	 * @param datePeriod информация о периоде
	 */
	public void addPeriodInformation(InsuranceDatePeriod datePeriod)
	{
		periods.add(datePeriod);
	}

	/**
	 * добавить информацию о периодах
	 * @param datePeriods информация о периодах
	 */
	public void addPeriodInformation(List<InsuranceDatePeriod> datePeriods)
	{
		periods.addAll(datePeriods);
	}
	/**
	 * Очистить (или создать новый) список информации для периодов
	 */
	public void clearPeriodInformation()
	{
		if (periods == null)
			periods = new ArrayList<InsuranceDatePeriod>();
		else
			periods.clear();
	}

	public Comparable getSynchKey()
	{
		return String.valueOf(forComplex) + getName();
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		InsuranceProduct source = (InsuranceProduct) that;
		setInsuranceCompany(source.getInsuranceCompany());
		setType(source.getType());
		setForComplex(source.isForComplex());
		setMinAge(source.getMinAge());
		setMaxAge(source.getMaxAge());
		clearPeriodInformation();
		addPeriodInformation(source.getPeriods());
	}

	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.INSURANCE;
	}
}
