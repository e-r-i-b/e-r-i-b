package com.rssl.phizic.business.ext.sbrf.deposits.locale;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Локализованная сущность для записей из таблицы FIELD_TDOG справочника ЦАС НСИ  (параметры для договоров условий по вкладам)
 * @author lepihina
 * @ created 11.06.15
 * $Author$
 * $Revision$
 */
public class LocaledDepositsTDOG extends DepositsTDOG
{
	private Set<DepositsTDOGResources> resources;

	@Override
	public String getSum()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getSum();
		return super.getSum();
	}

	@Override
	public String getCurrency()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getCurrency();
		return super.getCurrency();
	}

	@Override
	public String getPeriod()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getPeriod();
		return super.getPeriod();
	}

	@Override
	public String getDateEnd()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getDateEnd();
		return super.getDateEnd();
	}

	@Override
	public String getRate()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getRate();
		return super.getRate();
	}

	@Override
	public String getIncomingTransactions()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getIncomingTransactions();
		return super.getIncomingTransactions();
	}

	@Override
	public String getMinAdditionalFee()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getMinAdditionalFee();
		return super.getMinAdditionalFee();
	}

	@Override
	public String getAdditionalFeePeriod()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getAdditionalFeePeriod();
		return super.getAdditionalFeePeriod();
	}

	@Override
	public String getDebitTransactions()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getDebitTransactions();
		return super.getDebitTransactions();
	}

	@Override
	public String getSumMinBalance()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getSumMinBalance();
		return super.getSumMinBalance();
	}

	@Override
	public String getFrequencyPercent()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getFrequencyPercent();
		return super.getFrequencyPercent();
	}

	@Override
	public String getPercentOrder()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getPercentOrder();
		return super.getPercentOrder();
	}

	@Override
	public String getIncomeOrder()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getIncomeOrder();
		return super.getIncomeOrder();
	}

	@Override
	public String getRenewals()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getRenewals();
		return super.getRenewals();
	}
}
