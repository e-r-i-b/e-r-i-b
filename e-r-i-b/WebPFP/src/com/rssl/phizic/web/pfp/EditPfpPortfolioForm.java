package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.common.types.Money;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpPortfolioForm extends EditPersonalFinanceProfileForm 
{
	private Long portfolioId; //идентификатор портфеля
	private PersonPortfolio portfolio;
	private PersonRiskProfile riskProfile;
	private Long productId;//идентификатор удаляемого из портфеля продукта
	private Boolean needBeCareful; // необходимо ли выводить сообщение с рекомендациями об осторожности
	private Boolean isClientOwner;
	private Map<DictionaryProductType, Money> productDistribution;

	public Long getPortfolioId()
	{
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId)
	{
		this.portfolioId = portfolioId;
	}

	public PersonPortfolio getPortfolio()
	{
		return portfolio;
	}

	public void setPortfolio(PersonPortfolio portfolio)
	{
		this.portfolio = portfolio;
	}

	public PersonRiskProfile getRiskProfile()
	{
		return riskProfile;
	}

	public void setRiskProfile(PersonRiskProfile riskProfile)
	{
		this.riskProfile = riskProfile;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public Boolean getNeedBeCareful()
	{
		return needBeCareful;
	}

	public void setNeedBeCareful(Boolean needBeCareful)
	{
		this.needBeCareful = needBeCareful;
	}

	/**
	 * @return true - планирование проводит клиент, false - планирование проводит сотрудник
	 */
	public Boolean getClientOwner()
	{
		return isClientOwner;
	}

	/**
	 * @param clientOwner: true - планирование проводит клиент, false - планирование проводит сотрудник
	 */
	public void setClientOwner(Boolean clientOwner)
	{
		isClientOwner = clientOwner;
	}

	/**
	 * @return Возвращает значение енума типа продукта для вклада
	 */
	public DictionaryProductType getAccountType()
	{
		return DictionaryProductType.ACCOUNT;
	}

	/**
	 * @return Возвращает значение енума типа продукта для страхования
	 */
	public DictionaryProductType getInsuranceType()
	{
		return DictionaryProductType.INSURANCE;
	}

	/**
	 * @return Возвращает значение енума типа продукта для страхования
	 */
	public DictionaryProductType getPensionType()
	{
		return DictionaryProductType.PENSION;
	}

	/**
	 * @return Возвращает значение енума типа продукта для ПИФ-а
	 */
	public DictionaryProductType getFundType()
	{
		return DictionaryProductType.FUND;
	}

	/**
	 * @return Возвращает значение енума типа продукта для ОМС 
	 */
	public DictionaryProductType getImaType()
	{
		return DictionaryProductType.IMA;
	}

	/**
	 * @return Возвращает значение енума типа продукта для Доверительного управления
	 */
	public DictionaryProductType getTrustManagingType()
	{
		return DictionaryProductType.TRUST_MANAGING;
	}

	/**
	 * задать распределение средств в портфеле
	 * @param productDistribution распределение средств в портфеле
	 */
	public void setProductDistribution(Map<DictionaryProductType, Money> productDistribution)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.productDistribution = productDistribution;
	}

	/**
	 * @return распределение средств в портфеле
	 */
	public Map<DictionaryProductType, Money> getProductDistribution()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return productDistribution;
	}
}
