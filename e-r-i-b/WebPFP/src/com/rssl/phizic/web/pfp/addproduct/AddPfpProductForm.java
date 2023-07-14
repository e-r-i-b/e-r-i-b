package com.rssl.phizic.web.pfp.addproduct;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.web.pfp.EditPersonalFinanceProfileForm;

/**
 * @author mihaylov
 * @ created 22.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class AddPfpProductForm extends EditPersonalFinanceProfileForm
{
	private Long portfolioId;
	private String portfolioType;
	private Long editProductId;//идентификатор редактируемого продукта
	private Long productId;
	private String dictionaryProductType;
	private ProductBase product;
	private PersonPortfolio portfolio;
	private PersonalFinanceProfile profile;

	public Long getPortfolioId()
	{
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId)
	{
		this.portfolioId = portfolioId;
	}

	public String getPortfolioType()
	{
		return portfolioType;
	}

	public void setPortfolioType(String portfolioType)
	{
		this.portfolioType = portfolioType;
	}

	public Long getEditProductId()
	{
		return editProductId;
	}

	public void setEditProductId(Long editProductId)
	{
		this.editProductId = editProductId;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public String getDictionaryProductType()
	{
		return dictionaryProductType;
	}

	public DictionaryProductType getDictionaryProductTypeEnum()
	{
		return DictionaryProductType.valueOf(dictionaryProductType);
	}

	public void setDictionaryProductType(String dictionaryProductType)
	{
		this.dictionaryProductType = dictionaryProductType;
	}

	public ProductBase getProduct()
	{
		return product;
	}

	public void setProduct(ProductBase product)
	{
		this.product = product;
	}

	public PersonPortfolio getPortfolio()
	{
		return portfolio;
	}

	public void setPortfolio(PersonPortfolio portfolio)
	{
		this.portfolio = portfolio;
	}

	public PersonalFinanceProfile getProfile()
	{
		return profile;
	}

	public void setProfile(PersonalFinanceProfile profile)
	{
		this.profile = profile;
	}

	/**
	 * Обновить филды формы данными сохраненного продукта
	 * @param product - продукт из портфеля
	 */
	public void updateEditProductFields(PortfolioProduct product)
	{
		DictionaryProductType productType = product.getProductType();
		switch (productType)
		{
			case INSURANCE:
				InsuranceProductFormBuilder.updateFormFields(this,product);
				break;
			case COMPLEX_INSURANCE:
				ComplexInsuranceProductFormBuilder.updateFormFields(this, product);
				break;
			case COMPLEX_INVESTMENT_FUND:
				ComplexInvestmentProductFormBuilder.updateFormFields(this,product);
				break;
			case COMPLEX_INVESTMENT_FUND_IMA:
				ComplexInvestmentProductFormBuilder.updateFormFields(this, product);
				break;
			case PENSION:
				PensionProductFormBuilder.updateFormFields(this,product);
				break;
			default:
				BaseProductFormBuilder.updateFormFields(this, product);
		}
	}

	public Form getEditForm(PersonalFinanceProfile profile, PersonPortfolio portfolio,ProductBase productBase)
	{
		switch (getDictionaryProductTypeEnum())
		{
			case INSURANCE:
				return InsuranceProductFormBuilder.getForm(profile, portfolio,productBase);
			case PENSION:
				return PensionProductFormBuilder.getForm(profile, productBase);
			case COMPLEX_INSURANCE:
				return ComplexInsuranceProductFormBuilder.getForm(profile,portfolio,productBase);
			case COMPLEX_INVESTMENT_FUND:
				return ComplexInvestmentProductFormBuilder.getFundForm(portfolio,productBase);
			case COMPLEX_INVESTMENT_FUND_IMA:
				return ComplexInvestmentProductFormBuilder.getFundImaForm(portfolio,productBase);
			default:
				return BaseProductFormBuilder.getForm(portfolio, productBase);
		}
	}
}
