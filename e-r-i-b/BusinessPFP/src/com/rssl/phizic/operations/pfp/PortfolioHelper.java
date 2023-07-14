package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.*;
import com.rssl.phizic.business.pfp.portfolio.product.InsuranceBaseProduct;
import com.rssl.phizic.business.pfp.portfolio.product.LinkedBaseProduct;
import com.rssl.phizic.business.pfp.portfolio.product.PensionBaseProduct;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

/**
 * @author mihaylov
 * @ created 16.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хелпер для работы с портфелями. Необходим из-за специфики отображения страховых взносов.
 * При изменениии страховых продуктов в портфеле "стартовый капитал", должна измениться сумма для страховых взносов в "ежеквартальных вложениях"
 */
public class PortfolioHelper
{

	/**
	 * Применить все изменения по портфелю: добавить добавленные продукты, удалить удаленные, подтвердить изменение суммы.
	 * @param portfolio - изменяемый портфель
	 * @param personalFinanceProfile - анкета прохождения ПФП
	 * @return были ли произведены изменения
	 */
	public static boolean completePortfolioChanges(PersonPortfolio portfolio,PersonalFinanceProfile personalFinanceProfile)
	{
		boolean result = false;
		List<PortfolioProduct> portfolioProductList = portfolio.getPortfolioProductList();
		if (!CollectionUtils.isEmpty(portfolioProductList))
		{
			for (Iterator<PortfolioProduct> iter = portfolio.getPortfolioProductList().iterator();iter.hasNext();)
			{
				PortfolioProduct product = iter.next();
				//действия производим только с продуктами, которые отображаются на странице редактирования портфеля
				if(!portfolio.getType().isProductTypeAvailable(product.getProductType()))
					continue;
				if(product.getState() == PortfolioProductState.ADD)
				{
					upadateAdditionalPortfolioState(personalFinanceProfile, product, portfolio, PortfolioProductState.SAVE);
					product.setState(PortfolioProductState.SAVE);
					result = true;
				}
				else if(product.getState() == PortfolioProductState.DELETED)
				{
					upadateAdditionalPortfolioState(personalFinanceProfile, product, portfolio, null);
					iter.remove();
					result = true;
				}
			}
		}
		portfolio.upadteStartAmount();
		portfolio.setWantedIncome(null);
		return result;
	}

	/**
	 * Обновить сумму продукта в дополнительных портфелях
	 * @param personalFinanceProfile - анкета прохождения ПФП клиента
     * @param product - продукт, который изменяется текущем портфеле
     * @param currentPortfolio - текущий портфель
	 */
	public static void updateAdditionalPortfolioAmount(PersonalFinanceProfile personalFinanceProfile, PortfolioProduct product, PersonPortfolio currentPortfolio)
	{
		if(product.getProductType() != DictionaryProductType.INSURANCE && product.getProductType() != DictionaryProductType.COMPLEX_INSURANCE && product.getProductType() != DictionaryProductType.PENSION)
			return;
		for(PersonPortfolio additionalPortfolio: personalFinanceProfile.getPortfolioList())
			if(additionalPortfolio.getType() != currentPortfolio.getType())
			{
				for (PortfolioProduct portfolioProduct : additionalPortfolio.getPortfolioProductList())
				{
					updateInsuranceProductAmount(product, portfolioProduct, currentPortfolio, additionalPortfolio);
					updatePensionProductAmount(product, portfolioProduct, currentPortfolio, additionalPortfolio);
				}
				if(additionalPortfolio.getStartAmount().compareTo(additionalPortfolio.getProductSum())<0)
					additionalPortfolio.setStartAmount(additionalPortfolio.getProductSum());
			}

	}

	private static void updatePensionProductAmount(PortfolioProduct changedProduct, PortfolioProduct updatedProduct,PersonPortfolio fromPortfolio,PersonPortfolio toPortfolio)
	{
		PensionBaseProduct changedPensionBaseProduct = (PensionBaseProduct)changedProduct.getBaseProduct(DictionaryProductType.PENSION);
		PensionBaseProduct updatedPensionBaseProduct = (PensionBaseProduct)updatedProduct.getBaseProduct(DictionaryProductType.PENSION);
		if(changedPensionBaseProduct != null && updatedPensionBaseProduct != null && changedPensionBaseProduct.getId().equals(updatedPensionBaseProduct.getLinkedProductId()))
		{
			if(fromPortfolio.getType() == PortfolioType.QUARTERLY_INVEST && toPortfolio.getType() == PortfolioType.START_CAPITAL)
			{
				updatedPensionBaseProduct.setQurterlyInvest(changedPensionBaseProduct.getAmount().getDecimal());
			}
		}
	}

	private static void updateInsuranceProductAmount(PortfolioProduct changedProduct, PortfolioProduct updatedProduct,PersonPortfolio fromPortfolio,PersonPortfolio toPortfolio)
	{
		InsuranceBaseProduct changedInsuranceBaseProduct = (InsuranceBaseProduct)changedProduct.getBaseProduct(DictionaryProductType.INSURANCE);
		InsuranceBaseProduct insuranceBaseProduct = (InsuranceBaseProduct)updatedProduct.getBaseProduct(DictionaryProductType.INSURANCE);
		if(changedInsuranceBaseProduct != null && insuranceBaseProduct != null && changedInsuranceBaseProduct.getId().equals(insuranceBaseProduct.getLinkedProductId()))
		{
			Money convertAmount = convertMoneyFromPortfolio(changedInsuranceBaseProduct,fromPortfolio,toPortfolio);
			insuranceBaseProduct.setAmount(convertAmount);
		}
	}

	private static Money convertMoneyFromPortfolio(InsuranceBaseProduct insuranceBaseProduct,PersonPortfolio fromPortfolio,PersonPortfolio toPortfolio)
	{
		Money amount = insuranceBaseProduct.getAmount();
		BigDecimal convertAmount = null;
		Long monthInPeriod = insuranceBaseProduct.getMonthInPeriod();
		if(fromPortfolio.getType() == PortfolioType.START_CAPITAL && toPortfolio.getType() == PortfolioType.QUARTERLY_INVEST)
		{
			convertAmount = amount.getDecimal().multiply(new BigDecimal(toPortfolio.getType().getMonthCount())).divide(new BigDecimal(monthInPeriod),2,RoundingMode.UP);
		}
		else if(fromPortfolio.getType() == PortfolioType.QUARTERLY_INVEST && toPortfolio.getType() == PortfolioType.START_CAPITAL)
		{
			convertAmount = amount.getDecimal().multiply(new BigDecimal(monthInPeriod)).divide(new BigDecimal(fromPortfolio.getType().getMonthCount()),2,RoundingMode.UP);
		}
		return new Money(convertAmount,amount.getCurrency());
	}

	/**
	 * Обновить статус продукта в дополнительных портфелях
	 * @param personalFinanceProfile - анкета прохождения ПФП клиента
	 * @param product - продукт, который изменяется текущем портфеле
	 * @param currentPortfolio - текущий портфель
	 * @param state - статус, который необходимо выставить "связанному продукту" в других портфелях. Если null, продукт удаляем из портфеля
	 */
	public static void upadateAdditionalPortfolioState(PersonalFinanceProfile personalFinanceProfile, PortfolioProduct product, PersonPortfolio currentPortfolio, PortfolioProductState state)
	{
		if(product.getProductType() != DictionaryProductType.INSURANCE && product.getProductType() != DictionaryProductType.COMPLEX_INSURANCE && product.getProductType() != DictionaryProductType.PENSION)
			return;
		if(currentPortfolio.getType() != PortfolioType.START_CAPITAL)
			return;
		for(PersonPortfolio additionalPortfolio: personalFinanceProfile.getPortfolioList())
			if(additionalPortfolio.getType() != currentPortfolio.getType())
				for (Iterator<PortfolioProduct> iter = additionalPortfolio.getPortfolioProductList().iterator();iter.hasNext();)
				{
					PortfolioProduct portfolioProduct = iter.next();
					if(linkedProductEquals(portfolioProduct,product) || insuranceProductEquals(portfolioProduct,product,additionalPortfolio.getType(),currentPortfolio.getType()))
					{
						if(state == null)
							iter.remove();
						else
							portfolioProduct.setState(state);
						return;
					}
				}
	}

	private static boolean linkedProductEquals(PortfolioProduct product1,PortfolioProduct product2)
	{
		LinkedBaseProduct linkedBaseProduct1 = product1.getLinkedBaseProduct();
		LinkedBaseProduct linkedBaseProduct2 = product2.getLinkedBaseProduct();
		return linkedBaseProduct1 != null && linkedBaseProduct2 != null && linkedBaseProduct1.getId().equals(linkedBaseProduct2.getLinkedProductId());
	}

	private static boolean insuranceProductEquals(PortfolioProduct product1,PortfolioProduct product2,PortfolioType type1,PortfolioType type2)
	{
		if(product1.getProductType() != DictionaryProductType.INSURANCE || product2.getProductType() != DictionaryProductType.INSURANCE)
			return false;

		if(product1.getDictionaryProductId() == null ^ product2.getDictionaryProductId() == null)
			return false;
		if(product1.getDictionaryProductId() != null && product2.getDictionaryProductId() != null && !product1.getDictionaryProductId().equals(product2.getDictionaryProductId()))
			return false;
		
		if(product1.getState() != product2.getState())
			return false;

		InsuranceBaseProduct insuranceProduct1 = (InsuranceBaseProduct)product1.getBaseProduct(DictionaryProductType.INSURANCE);
		InsuranceBaseProduct insuranceProduct2 = (InsuranceBaseProduct)product2.getBaseProduct(DictionaryProductType.INSURANCE);

		if(insuranceProduct1.getSelectedPeriodId() == null ^ insuranceProduct2.getSelectedPeriodId() == null)
			return false;
		if(insuranceProduct1.getSelectedPeriodId() != null && insuranceProduct2.getSelectedPeriodId() != null && !insuranceProduct1.getSelectedPeriodId().equals(insuranceProduct2.getSelectedPeriodId()))
			return false;

		Money amount1 = getInsuranceProductAmount(insuranceProduct1,type1);
		Money amount2 = getInsuranceProductAmount(insuranceProduct2,type2);

		return amount1.equals(amount2);
	}

	private static Money getInsuranceProductAmount(InsuranceBaseProduct insuranceProduct,PortfolioType portfolioType)
	{
		Money amount = insuranceProduct.getAmount();
		if(portfolioType.getMonthCount() != null)
		{
			amount = amount.multiply(insuranceProduct.getMonthInPeriod());//делим на колличество месяцев в выбранной периодичности страхового продукта
			amount = amount.multiply(1.0/portfolioType.getMonthCount());//умножаем на количество месяцев в периоде портфеля
		}
		return amount;
	}

}
