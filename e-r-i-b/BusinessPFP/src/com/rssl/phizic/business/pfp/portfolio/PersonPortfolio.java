package com.rssl.phizic.business.pfp.portfolio;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.*;
import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Портфель клиента, сформированный при прохождении ПФП
 */
public class PersonPortfolio
{
	private Long id;
	private PortfolioType type; // тип портфеля
	private Money startAmount;  // сумма для формирования портфеля
	private Money temporaryStartAmount;  // временная сумма для формирования портфеля (предыдущая)
	private List<PortfolioProduct> portfolioProductList;//список продуктов в портфеле
	private PortfolioState portfolioState = PortfolioState.SAVED;
	private BigDecimal wantedIncome; // доходность портфеля для достижения желаемой стоимости портфеля

	public PersonPortfolio()
	{
	}

	public PersonPortfolio(PortfolioType type)
	{
		this.type = type;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public PortfolioType getType()
	{
		return type;
	}

	public void setType(PortfolioType type)
	{
		this.type = type;
	}

	public Money getStartAmount()
	{
		if (PortfolioState.SAVED != portfolioState && temporaryStartAmount != null)
			return temporaryStartAmount;
		
		return startAmount;
	}

	public void setStartAmount(Money startAmount)
	{
		this.startAmount = startAmount;
	}

	/**
	 * @return возвращает количество неиспользованных средств от суммы для формирования портфеля
	 */
	public Money getFreeAmount()
	{	
		return getStartAmount().sub(getProductSum());
	}

	public void setTemporaryStartAmount(Money temporaryStartAmount)
	{
		this.temporaryStartAmount = temporaryStartAmount;
	}

	public List<PortfolioProduct> getPortfolioProductList()
	{
		return portfolioProductList;
	}

	public void clearTemporaryStartAmount()
	{
		temporaryStartAmount = null;
	}

	public void upadteStartAmount()
	{
		if(temporaryStartAmount != null)
			startAmount = temporaryStartAmount;
		clearTemporaryStartAmount();
	}

	/**
	 * @return Возвращает список продуктов с учетом текущего состояния портфеля(редактируем портфель или он уже сохранен) + сортирует их
	 */
	public List<PortfolioProduct> getCurrentProductList()
	{
		return getCurrentProductList(true);
	}

	private List<PortfolioProduct> getCurrentProductList(boolean useSort)
	{
		Predicate filter = null;
		if(portfolioState == PortfolioState.SAVED)
			filter = SavedProductFilter.INSTANCE;
		else
			filter = NotSavedProductFilter.INSTANCE;

		List<PortfolioProduct> result = new ArrayList<PortfolioProduct>(portfolioProductList);
		CollectionUtils.filter(result,filter);
		if (useSort)
			Collections.sort(result, PFPProductComparator.getInstance());
		return result;
	}

	/**
	 * @return Проверка портфеля на пустоту.
	 */
	public boolean getIsEmpty()
	{
		return CollectionUtils.isEmpty(getCurrentProductList(false));
	}

	/**
	 * @return проверка портфеля на полноту. Портфель считается полностью сформированным, если количество свободных средств равно 0.
	 */
	public boolean getIsFull()
	{
		 return getFreeAmount().isZero();
	}

	public void setPortfolioState(PortfolioState portfolioState)
	{
		this.portfolioState = portfolioState;
	}

	public void setPortfolioProductList(List<PortfolioProduct> portfolioProductList)
	{
		this.portfolioProductList = portfolioProductList;
	}

	public void addPortfolioProduct(PortfolioProduct product)
	{
		if(CollectionUtils.isEmpty(portfolioProductList))
			portfolioProductList = new ArrayList<PortfolioProduct>();
		if(product != null)
			portfolioProductList.add(product);
	}

	/**
	 * @return Возвращает сумму всех продуктов в портфеле
	 */
	public Money getProductSum()
	{
		Money sum = getZeroMoney();
		List<PortfolioProduct> currentProductList = getCurrentProductList(false);
		for(PortfolioProduct portfolioProduct: currentProductList)
			for(BaseProduct baseProduct: portfolioProduct.getBaseProductList())
				sum = baseProduct.getAmount().add(sum);
		return sum;
	}

	/**
	 * Считаем сумму для каждого базового продукта ПФП для портфеля
	 * @return
	 */
	public Map<ProductType, Money> getProductWeightMap()
	{
		Map<ProductType,Money> porductWeights = new HashMap<ProductType,Money>();

		Money zero = getZeroMoney();
		for(ProductType type: ProductType.values())
			porductWeights.put(type,zero);

		List<PortfolioProduct> currentProductList = getCurrentProductList(false);
		for(PortfolioProduct portfolioProduct: currentProductList)
			for(BaseProduct baseProduct: portfolioProduct.getBaseProductList())
			{
				ProductType productType = ProductType.getByDictionaryProductTypeSingle(baseProduct.getProductType());
				Money productAmount = baseProduct.getAmount();
				Money amountInMap = porductWeights.get(productType);
				porductWeights.put(productType,amountInMap.add(productAmount));
			}
		return porductWeights;
	}

	/**
	 * Считаем процентое содержание для каждого базового продукта ПФП для портфеля
	 * @return процентое содержание для каждого базового продукта ПФП для портфеля
	 */
	public Map<ProductType, Long> getProductWeightPercentMap()
	{
		Map<ProductType,Money> porductWeights = getProductWeightMap();
		Map<ProductType,Long> porductWeightsPercent = new HashMap<ProductType,Long>();
		Money totalAmount = getProductSum();

		if (!totalAmount.isZero())
			for(Map.Entry<ProductType, Money> entry: porductWeights.entrySet())
				porductWeightsPercent.put(entry.getKey(), entry.getValue().multiply(1.0/totalAmount.getWholePart()).getAsCents());
		return porductWeightsPercent;
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
			return null;
		}
	}

	/**
	 * @return доходность портфеля
	 */
	public BigDecimal getIncome()
	{
		BigDecimal dividend = BigDecimal.ZERO;
		BigDecimal divider = BigDecimal.ZERO;
		List<PortfolioProduct> currentProductList = getCurrentProductList();
		for(PortfolioProduct portfolioProduct: currentProductList)
			for(BaseProduct baseProduct: portfolioProduct.getBaseProductList())
			{
				dividend = dividend.add(baseProduct.getAmount().getDecimal().multiply(baseProduct.getIncome()));
				divider = divider.add(baseProduct.getAmount().getDecimal());
			}

		if (divider.compareTo(BigDecimal.ZERO) == 0)
			return BigDecimal.ZERO;
		else
			return dividend.divide(divider, 1, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getWantedIncome()
	{
		return wantedIncome;
	}

	public void setWantedIncome(BigDecimal wantedIncome)
	{
		this.wantedIncome = wantedIncome;
	}
}
