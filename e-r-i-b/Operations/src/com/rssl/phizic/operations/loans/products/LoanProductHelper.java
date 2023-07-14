package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductBase;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author gulov
 * @ created 12.08.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Класс-помощник для кредитных продуктов.
 * При изменении, добавлении, удалении свойств классов LoanProduct или ModifiableLoanProduct, необходимо
 * модифицировать метод loanProductEquals.
 */
public class LoanProductHelper
{
	public static boolean loanProductEquals(LoanProductBase product, LoanProductBase other)
	{
		if (!product.getClass().getName().equals(other.getClass().getName()))
			return false;
		if (!StringHelper.equalsNullIgnore(product.getName(), other.getName()))
			return false;
		if (!NumericUtil.equalsNullIgnore(product.getLoanKind().getId(), other.getLoanKind().getId()))
			return false;
		if (product instanceof LoanProduct)
		{
			LoanProduct product1 = (LoanProduct) product;
			LoanProduct product2 = (LoanProduct) other;

			if (!StringHelper.equalsNullIgnore(product1.getBriefDescription(), product2.getBriefDescription()))
				return false;
			if (!StringHelper.equalsNullIgnore(product1.getDescription(), product2.getDescription()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getPackageTemplateId(), product2.getPackageTemplateId()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getGuarantorsCount(), product2.getGuarantorsCount()))
				return false;
			if (product1.getAnonymousClaim() != product2.getAnonymousClaim())
				return false;
		}
		else if (product instanceof ModifiableLoanProduct)
		{
			ModifiableLoanProduct product1 = (ModifiableLoanProduct) product;
			ModifiableLoanProduct product2 = (ModifiableLoanProduct) other;

			if (!NumericUtil.equalsNullIgnore(product1.getMinDuration().getYear(), product2.getMinDuration().getYear()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getMinDuration().getMonth(), product2.getMinDuration().getMonth()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getMaxDuration().getYear(), product2.getMaxDuration().getYear()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getMaxDuration().getMonth(), product2.getMaxDuration().getMonth()))
				return false;
			if (!equalsNullIgnore(product1.getMaxDurationInclude(), product2.getMaxDurationInclude()))
				return false;
			if (!equalsNullIgnore(product1.getNeedInitialInstalment(), product2.getNeedInitialInstalment()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getMinInitialInstalment(), product2.getMinInitialInstalment()))
				return false;
			if (!NumericUtil.equalsNullIgnore(product1.getMaxInitialInstalment(), product2.getMaxInitialInstalment()))
				return false;
			if (!equalsNullIgnore(product1.isMaxInitialInstalmentInclude(), product2.isMaxInitialInstalmentInclude()))
				return false;
			if (!equalsNullIgnore(product1.getNeedSurety(), product2.getNeedSurety()))
				return false;
			if (!StringHelper.equalsNullIgnore(product1.getAdditionalTerms(), product2.getAdditionalTerms()))
				return false;
			if (product1.getPublicity() != product2.getPublicity())
				return false;
			//проверка тербанков
			if (product1.getTerbanks().size() != product2.getTerbanks().size())
				return false;
			for (int i = 0; i < product1.getTerbanks().size(); i++)
			{
				Department department1 = product1.getTerbanks().get(i);
				Department department2 = product2.getTerbanks().get(i);

				if (!department1.getId().equals(department2.getId()))
					return false;
			}
			//проверка условий
			if (product1.getConditions().size() != product2.getConditions().size())
				return false;
			for (int i = 0; i < product1.getConditions().size(); i++)
			{
				LoanCondition condition1 = product1.getConditions().get(i);
				LoanCondition condition2 = product2.getConditions().get(i);

				if (!condition1.getProductId().equals(condition2.getProductId()))
					return false;
				if (!condition1.getCurrency().compare(condition2.getCurrency()))
					return false;
				if (!MoneyUtil.equalsNullIgnore(condition1.getMinAmount(), condition2.getMinAmount()))
					return false;
				if (!MoneyUtil.equalsNullIgnore(condition1.getMaxAmount(), condition2.getMaxAmount()))
					return false;
				if (!equalsNullIgnore(condition1.isMaxAmountInclude(), condition2.isMaxAmountInclude()))
					return false;
				if (!NumericUtil.equalsNullIgnore(condition1.getMaxAmountPercent(), condition2.getMaxAmountPercent()))
					return false;
				if (condition1.getAmountType() != condition2.getAmountType())
					return false;
				if (!NumericUtil.equalsNullIgnore(condition1.getMinInterestRate(), condition2.getMinInterestRate()))
					return false;
				if (!NumericUtil.equalsNullIgnore(condition1.getMaxInterestRate(), condition2.getMaxInterestRate()))
					return false;
				if (!equalsNullIgnore(condition1.isMaxInterestRateInclude(), condition2.isMaxInterestRateInclude()))
					return false;
			}
		}
		return true;
	}

	public static boolean loanCardProductEquals(CreditCardProduct product, CreditCardProduct other)
	{
		if (!StringHelper.equalsNullIgnore(product.getName(), other.getName()))
			return false;
		if (!StringHelper.equalsNullIgnore(product.getName(), other.getName()))
			return false;
		if (!equalsNullIgnore(product.getAllowGracePeriod(), other.getAllowGracePeriod()))
			return false;
		if (!NumericUtil.equalsNullIgnore(product.getGracePeriodDuration(), other.getGracePeriodDuration()))
			return false;
		if (!NumericUtil.equalsNullIgnore(product.getGracePeriodInterestRate(), other.getGracePeriodInterestRate()))
			return false;
		if (!StringHelper.equalsNullIgnore(product.getAdditionalTerms(), other.getAdditionalTerms()))
			return false;
		if (product.getPublicity() != other.getPublicity())
			return false;
		if (!product.getCardTypeCode().equals(other.getCardTypeCode()))
			return false;
		//проверка условий
		if (product.getConditions().size() != other.getConditions().size())
			return false;
		for (int i = 0; i < product.getConditions().size(); i++)
		{
			CreditCardCondition condition1 = product.getConditions().get(i);
			CreditCardCondition condition2 = other.getConditions().get(i);

			if (!condition1.getProductId().equals(condition2.getProductId()))
				return false;
			if (!condition1.getCurrency().compare(condition2.getCurrency()))
				return false;
			if (!NumericUtil.equalsNullIgnore(condition1.getMinCreditLimit().getId(), condition2.getMinCreditLimit().getId()))
				return false;
			if (!NumericUtil.equalsNullIgnore(condition1.getMaxCreditLimit().getId(), condition2.getMaxCreditLimit().getId()))
				return false;
			if (!equalsNullIgnore(condition1.getMaxCreditLimitInclude(), condition2.getMaxCreditLimitInclude()))
				return false;
			if (!NumericUtil.equalsNullIgnore(condition1.getInterestRate(), condition2.getInterestRate()))
				return false;
			if (!condition1.getFirstYearPayment().equals(condition2.getFirstYearPayment()))
				return false;
			if (!condition1.getNextYearPayment().equals(condition2.getNextYearPayment()))
				return false;
		}

		return true;
	}

	private static Boolean getEmptyIfNull(Boolean value)
	{
		return value == null ? false : value;
	}

	private static boolean equalsNullIgnore(Boolean val1, Boolean val2)
	{
		return getEmptyIfNull(val1).equals(getEmptyIfNull(val2));
	}
}
