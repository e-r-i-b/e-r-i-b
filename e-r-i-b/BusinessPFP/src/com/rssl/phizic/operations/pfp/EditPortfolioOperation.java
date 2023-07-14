package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProductState;
import com.rssl.phizic.business.pfp.portfolio.PortfolioState;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.State;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция редактирования риск профиля клиента
 */
public class EditPortfolioOperation extends EditPfpOperationBase
{
	private static final State EDIT_PERSON_PORTFOLIOS = new State("EDIT_PERSON_PORTFOLIOS");
	private static final Map<ProductType, DictionaryProductType> productTypeForDistributionMapping = new HashMap<ProductType, DictionaryProductType>(5);
	private PersonPortfolio portfolio;

	static
	{
		productTypeForDistributionMapping.put(ProductType.account,       DictionaryProductType.ACCOUNT);
		productTypeForDistributionMapping.put(ProductType.fund,          DictionaryProductType.FUND);
		productTypeForDistributionMapping.put(ProductType.IMA,           DictionaryProductType.IMA);
		productTypeForDistributionMapping.put(ProductType.trustManaging, DictionaryProductType.TRUST_MANAGING);
		productTypeForDistributionMapping.put(ProductType.insurance,     DictionaryProductType.INSURANCE);
	}

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(EDIT_PERSON_PORTFOLIOS);
	}

	public void setPortfolioId(Long portfolioId) throws BusinessException, BusinessLogicException
	{
		//TODO попробовать перевести на поиск портфеля в БД по логину клиента и идентификатору
		for(PersonPortfolio portfolio: personalFinanceProfile.getPortfolioList())
			if(portfolioId.equals(portfolio.getId()))
				this.portfolio = portfolio;
		if(this.portfolio == null)
			throw new ResourceNotFoundBusinessException("Запрашиваемый портфель не найден",PersonPortfolio.class);
		portfolio.setPortfolioState(PortfolioState.EDIT);
	}

	public PersonPortfolio getPortfolio()
	{
		return portfolio;
	}

	/**
	 * Удаляет выбранный продукт из портфеля клиента
	 * @param productId - идентификатор продукта
	 * @throws BusinessException
	 */
	public void removeProductFromPortfolio(Long productId) throws BusinessException, BusinessLogicException
	{
		if(productId == null)
			throw new IllegalArgumentException("Аргумент productId не может быть null");
		List<PortfolioProduct> portfolioProductList = portfolio.getPortfolioProductList();
		for (Iterator<PortfolioProduct> iter = portfolio.getPortfolioProductList().iterator();iter.hasNext();)
		{
			PortfolioProduct product = iter.next();
			if(!productId.equals(product.getId()))
				continue;
			//если удаляемый продукт ещё не был добавлен в портфель по кнопке "Сформировать", то удаляем его из базы
			else if(product.getState() == PortfolioProductState.ADD)
			{
				PortfolioHelper.upadateAdditionalPortfolioState(personalFinanceProfile, product, portfolio, null);
				iter.remove();
			}
			//если удаляемый продукт ещё был добавлен в портфель по кнопке "Сформировать", то помечаем его на удаление при формировании портфеля
			else if(product.getState() == PortfolioProductState.SAVE)
			{
				PortfolioHelper.upadateAdditionalPortfolioState(personalFinanceProfile, product, portfolio, PortfolioProductState.DELETED);
				product.setState(PortfolioProductState.DELETED);
			}
		}
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * Отменяет все изменения в портфеле.
	 * Удаляет все добавленные продукты, отменяет удаление всех удаленных продуктов
	 * @throws BusinessException
	 */
	public void undoChanges() throws BusinessException, BusinessLogicException
	{
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
					PortfolioHelper.upadateAdditionalPortfolioState(personalFinanceProfile, product, portfolio, null);
					iter.remove();
				}
				else if(product.getState() == PortfolioProductState.DELETED)
				{
					PortfolioHelper.upadateAdditionalPortfolioState(personalFinanceProfile, product, portfolio, PortfolioProductState.SAVE);
					product.setState(PortfolioProductState.SAVE);
				}
			}
		}
		portfolio.clearTemporaryStartAmount();
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * Применяет все изменения к портфелю.
	 * Сохраняет добавленные продукты. Удаляет из БД удаленные. 
	 * @throws BusinessException
	 */
	public void applyChanges() throws BusinessException, BusinessLogicException
	{
		if (PortfolioHelper.completePortfolioChanges(portfolio,personalFinanceProfile))
		{
			personalFinanceProfile.setExecutionDate(null); //если изменили портфель, то убиваем дату завершения планирования
			personalFinanceProfile.setStartPlaningDate(null);
		}
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	private Money getZeroMoney()
	{
		try
		{
			Currency nationalCurrency = MoneyUtil.getNationalCurrency();
			return Money.fromCents(0L,nationalCurrency);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения национальной валюты.", e);
			return null;
		}
	}

	private Map<DictionaryProductType, Money> getEmptyProductDistribution()
	{
		Map<DictionaryProductType, Money> productDistribution = new HashMap<DictionaryProductType, Money>();
		Money zeroMoney = getZeroMoney();
		if (zeroMoney == null)
			return productDistribution;

		PersonRiskProfile personRiskProfile = personalFinanceProfile.getPersonRiskProfile();
		List<DictionaryProductType> availableProducts = personRiskProfile.getAvailableProducts();
		Map<ProductType, Long> productsWeights = personRiskProfile.getProductsWeights();
		for (ProductType productType : productsWeights.keySet())
		{
			DictionaryProductType dictionaryProductType = productTypeForDistributionMapping.get(productType);
			if (DictionaryProductType.INSURANCE == dictionaryProductType)
			{
				if (availableProducts.contains(DictionaryProductType.INSURANCE) || availableProducts.contains(DictionaryProductType.COMPLEX_INSURANCE))
					productDistribution.put(DictionaryProductType.INSURANCE, zeroMoney);
				if (availableProducts.contains(DictionaryProductType.PENSION))
					productDistribution.put(DictionaryProductType.PENSION, zeroMoney);
			}
			else
			{
				productDistribution.put(dictionaryProductType, zeroMoney);
			}
		}
		return productDistribution;
	}

	/**
	 * @return распределение средств в портфеле
	 */
	public Map<DictionaryProductType, Money> getProductDistribution()
	{
		Map<DictionaryProductType, Money> productDistribution = getEmptyProductDistribution();
		List<PortfolioProduct> currentProductList = portfolio.getCurrentProductList();
		for(PortfolioProduct portfolioProduct: currentProductList)
		{
			for(BaseProduct baseProduct: portfolioProduct.getBaseProductList())
			{
				DictionaryProductType productType = baseProduct.getProductType();
				Money productAmount = baseProduct.getAmount();
				Money amountInMap = productDistribution.get(productType);
				productDistribution.put(productType,amountInMap.add(productAmount));
			}
		}
		return productDistribution;
	}
}
