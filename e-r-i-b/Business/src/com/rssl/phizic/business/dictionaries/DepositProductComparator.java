package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.generated.*;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.util.*;
import javax.xml.bind.JAXBException;

/**
 * Компаратор для сравнения записей справочника по депозитным продуктам. Первичное сравнение идет по номеру
 * вида вклада, если номера совпадают, сравнивается описание продукта.
 @author Pankin
 @ created 10.03.2011
 @ $Author$
 @ $Revision$
 */
public class DepositProductComparator implements Comparator<DepositProduct>
{
	public int compare(DepositProduct o1, DepositProduct o2)
	{
		int compareResult = (o1.getProductId()).compareTo(o2.getProductId());
		if (compareResult == 0)
		{
			try
			{
				DepositDictionaryProduct product1 = JAXBUtils.unmarshalBean(DepositDictionaryProduct.class, o1.getDescription());
				DepositDictionaryProduct product2 = JAXBUtils.unmarshalBean(DepositDictionaryProduct.class, o2.getDescription());

				DepositDictionaryMain mainParameters1 = product1.getData().getMain();
				DepositDictionaryMain mainParameters2 = product2.getData().getMain();

				if (mainParameters1.isCapitalization() != mainParameters2.isCapitalization())
					return -1;
				if (mainParameters1.isInitialFee() != mainParameters2.isInitialFee())
					return -1;
				if (mainParameters1.isMinimumBalance() != mainParameters2.isMinimumBalance())
					return -1;

				return compareDepositSubType(product1, product2);
			}
			catch (JAXBException e)
			{
				return -1;
			}
		}
		return compareResult;
	}



	/**
	 * Сравнить подвиды вкладов по процентной ставке
	 * @param product1 - сравниваемый вклад
	 * @param product2 - сравниваемый вклад
	 * @return 0, если подвиды вкладов совпадают по значениям процентной ставки
	 * @throws JAXBException
	 */
	private int compareDepositSubType(DepositDictionaryProduct product1, DepositDictionaryProduct product2) throws JAXBException
	{
		List<DepositDictionaryElement> options1 = product1.getData().getOptions().getElement();
		List<DepositDictionaryElement> options2 = product2.getData().getOptions().getElement();

		if (options1.size() != options2.size())
			return -1;

		Collections.sort(options1, new DepositSubTypeComparator());
		Collections.sort(options2, new DepositSubTypeComparator());

		for (int i = 0; i < options1.size(); i++)
		{
			DepositDictionaryElement elem1 = options1.get(i);
			DepositDictionaryElement elem2 = options2.get(i);

			int result = elem1.getInterestRate().compareTo(elem2.getInterestRate());
			if (result != 0)
				return result;
			if (NumericUtil.compare(elem1.getTariffPlanCode(), elem2.getTariffPlanCode()) != 0)
				return -1;
			if (NumericUtil.compare(elem1.getSegmentCode(), elem2.getSegmentCode()) != 0)
				return -1;
			if (elem1.isCreditOperations() != elem2.isCreditOperations())
				return -1;
			if (elem1.isDebitOperations() != elem2.isDebitOperations())
				return -1;
			if (elem1.isInterestOperations() != elem2.isInterestOperations())
				return -1;
			if (elem1.getDebitOperationsCode() == null && elem2.getDebitOperationsCode() != null)
				return -1;
			if (elem1.getDebitOperationsCode() != null && !elem1.getDebitOperationsCode().equals(elem2.getDebitOperationsCode()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getIncomingTransactions(), elem2.getIncomingTransactions()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getFrequencyAdd(), elem2.getFrequencyAdd()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getDebitTransactions(), elem2.getDebitTransactions()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getFrequencyPercent(), elem2.getFrequencyPercent()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getPercentOrder(), elem2.getPercentOrder()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getIncomeOrder(), elem2.getIncomeOrder()))
				return -1;
			if (!StringHelper.equalsNullIgnore(elem1.getRenewals(), elem2.getRenewals()))
				return -1;
			if (NumericUtil.compare(elem1.getTariffPlanAgreementId(), elem2.getTariffPlanAgreementId()) != 0)
				return -1;
			if (compareDepositGroup(elem1.getGroup(), elem2.getGroup()) != 0)
				return -1;
		}
		return 0;
	}

	private int compareDepositGroup(DepositDictionaryGroup group1, DepositDictionaryGroup group2) throws JAXBException
	{
		if (group1 == null)
			return -1;
		if (group2 == null)
			return 1;

		if (NumericUtil.compare(group1.getGroupCode(), group2.getGroupCode()) != 0)
			return -1;
		if (!StringHelper.equalsNullIgnore(group1.getGroupName(), group2.getGroupName()))
			return -1;

		DepositDictionaryGroupParams params1 = group1.getGroupParams();
		DepositDictionaryGroupParams params2 = group2.getGroupParams();

		if (params1 == null && params2 == null)
			return 0;
		if (params1 == null)
			return -1;
		if (params2 == null)
			return 1;

		if (params1.isCharitableContribution() != params2.isCharitableContribution())
			return -1;
		if (params1.isPensionRate() != params2.isPensionRate())
			return -1;
		if (params1.isPensionSumLimit() != params2.isPensionSumLimit())
			return -1;
		if (NumericUtil.compare(params1.getPercentCondition(), params2.getPercentCondition()) != 0)
			return -1;
		if (NumericUtil.compare(params1.getSumLimit(), params2.getSumLimit()) != 0)
			return -1;
		if (NumericUtil.compare(params1.getSumLimitCondition(), params2.getSumLimitCondition()) != 0)
			return -1;
		if (params1.isSocialType() != params2.isSocialType())
			return -1;
		if (params1.isPromoCodeSupported() != params2.isPromoCodeSupported())
			return -1;

		return 0;
	}
}
