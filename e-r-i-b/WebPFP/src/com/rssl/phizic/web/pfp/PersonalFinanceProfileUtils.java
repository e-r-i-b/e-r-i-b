package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PfpConfig;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.configure.DefaultPeriodSettingService;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.*;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductService;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.*;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.*;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolioService;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */

public class PersonalFinanceProfileUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final PersonPortfolioService personPortfolioService = new PersonPortfolioService();
	private static final InsuranceTypeService insuranceTypeService = new InsuranceTypeService();
	private static final ProductService productService = new ProductService();
	private static final DefaultPeriodSettingService defaultPeriodSettingService = new DefaultPeriodSettingService();

	public static final int QUARTER_TO_TARGET = 6;//���������� ������� �� ���� �������, ��� �������
												// ���������� ���������� ������������� ������ ����������
	public static final double ONE_PERCENT = 0.01;

	/**
	 * @return ���������� �� ������� ���� ����������,
	 * ���� ����� �� ������������ ����� �� ����� ������ N ���������, �� true
	 */
	public static boolean getNeedBeCareful(PersonalFinanceProfile profile)
	{
		for(PersonTarget target: profile.getPersonTargets())
		{
			int quarters = DateHelper.getDiffInQuarters(Calendar.getInstance(), target.getPlannedDate());
			if(quarters < QUARTER_TO_TARGET)
				return true;
		}
		return false;
	}

	/**
	 * �������� ����������� ���������� ������� ���� �������� � ��������
	 * @param portfolio - ��������
	 * @param productTypeString - ��� ��������
	 * @return
	 */
	public static boolean isProductTypeSupportedForPortfolio(PersonPortfolio portfolio, String productTypeString)
	{
		DictionaryProductType productType = DictionaryProductType.valueOf(productTypeString);
		return portfolio.getType().isProductTypeAvailable(productType);
	}

	/**
	 * �������� ����������� ���������� ������� ���� �������� � ��������
	 * @param riskProfile - ����-�������
	 * @param productTypeString - ��� ��������
	 * @return �������� �� ������� � ������ ����-�������
	 */
	public static boolean isProductTypeAvailableAdd(PersonPortfolio portfolio, PersonRiskProfile riskProfile, String productTypeString)
	{
		return isProductTypeSupportedForPortfolio(portfolio, productTypeString) && riskProfile.getAvailableProducts().contains(DictionaryProductType.valueOf(productTypeString));
	}

	/**
	 * �����! �� ��������� ��� �������������� � �������� �������� �� ��������� ������� ����.
	 * ���������� ������������� ����� ��� �������� �� ������.
	 * @param riskProfile - ���� ������� �������
	 * @param portfolio - �������� ��� ���������� ��������
	 * @param productTypeString - ��� ��������
	 * @return ����� ������������� ��� ������������� ����� ��� �������� - ����������� ����� ��� ��������.
	 *          ���� ���������� ����� ������ 0, �� ���������� 0.
	 */
	public static Money getRecommendAmount(PersonRiskProfile riskProfile, PersonPortfolio portfolio, String productTypeString)
	{
		ProductType productType = ProductType.valueOf(productTypeString);
		Money insuranceSum = portfolio.getProductWeightMap().get(ProductType.insurance);//�������� ����� ��������� ���������
		Money freeAmount = portfolio.getStartAmount().sub(insuranceSum); // �������� � �� ��������� ��������
		Long productWeight = riskProfile.getProductsWeights().get(productType);
		if (productWeight == null)
			return Money.fromCents(0,freeAmount.getCurrency());
		switch (productType)
		{
			case account:
				Long insuranceWeight = riskProfile.getProductsWeights().get(ProductType.insurance);
				if (insuranceWeight == null)
					insuranceWeight = 0L;
				return freeAmount.multiply(productWeight + insuranceWeight).multiply(ONE_PERCENT);
			case IMA:
			case fund:
			case trustManaging:
				return freeAmount.multiply(productWeight).multiply(ONE_PERCENT);
			default:
			    return Money.fromCents(0,freeAmount.getCurrency());
		}
	}

	/**
	 * �����! ��������� ��� �������������� � �������� �������� �� ��������� ������� ����.
	 * ���������� ������������� ����� ��� �������� �� ������.
	 * @param riskProfile - ���� ������� �������
	 * @param portfolio - �������� ��� ���������� ��������
	 * @param productTypeString - ��� ��������
	 * @return ����� ������������� ��� ������������� ����� ��� �������� - ����������� ����� ��� ��������.
	 *          ���� ���������� ����� ������ 0, �� ���������� 0.
	 */
	public static Money getCurrentRecommendAmount(PersonRiskProfile riskProfile, PersonPortfolio portfolio, String productTypeString)
	{
		ProductType productType = ProductType.valueOf(productTypeString);
		Money currentSunInPortfolio = portfolio.getProductWeightMap().get(productType);
		Money recommendForPortfolio = getRecommendAmount(riskProfile,portfolio,productTypeString);
		Money recommendedAmount = recommendForPortfolio.sub(currentSunInPortfolio);
		if(recommendedAmount.getAsCents()<0)
			recommendedAmount = Money.fromCents(0,recommendedAmount.getCurrency());
		return recommendedAmount;
	}

	/**
	 * ���������� ����������� ����� ��� ���������� ��������, � ������ ���� ��������.
	 * ���� ��� ������������ �������� ����������� ����� ������ ������ ����������� ����� ������������ ��������, �� ���������� ����������� ����� ������
	 * @param product - �������
	 * @param productType - ��� ��������
	 * @param type - ��� ��������
	 * @return ����������� �����
	 * @throws BusinessException
	 */
	public static Money getMinAmount(ProductBase product, DictionaryProductType productType, PortfolioType type) throws BusinessException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();
		switch (productType)
		{
			case INSURANCE:
				InsuranceProduct insuranceProduct = (InsuranceProduct) product;
				BigDecimal minAmountValue = null;
				for(InsuranceDatePeriod period: insuranceProduct.getPeriods())
					if(minAmountValue == null || minAmountValue.compareTo(period.getMinSum()) > 0)
						minAmountValue = period.getMinSum();
				if(minAmountValue == null)
					return null;
				return new Money(minAmountValue, nationalCurrency);
			case PENSION:
				PensionProduct pensionProduct = (PensionProduct) product;
				return new Money(pensionProduct.getEntryFee(),nationalCurrency);
			case ACCOUNT:
			case IMA:
			case FUND:
			case TRUST_MANAGING:
				SimpleProductBase simpleProduct = (SimpleProductBase) product;
				return new Money(simpleProduct.getParameters(type).getMinSum(), nationalCurrency);
			case COMPLEX_INSURANCE:
				ComplexInsuranceProduct complexInsuranceProduct = (ComplexInsuranceProduct) product;
				AccountProduct accountProduct = complexInsuranceProduct.getAccount();
				if(accountProduct.getParameters(type).getMinSum().compareTo(complexInsuranceProduct.getMinSum()) > 0)
					return new Money(accountProduct.getParameters(type).getMinSum(), nationalCurrency);
				return new Money(complexInsuranceProduct.getMinSum(), nationalCurrency);
			case COMPLEX_INVESTMENT_FUND:
			case COMPLEX_INVESTMENT_FUND_IMA:
				ComplexInvestmentProductBase complexProduct = (ComplexInvestmentProductBase) product;
				AccountProduct account = complexProduct.getAccount();
				if(account.getParameters(type).getMinSum().compareTo(complexProduct.getParameters(type).getMinSum()) > 0)
					return new Money(account.getParameters(type).getMinSum(), nationalCurrency);
				return new Money(complexProduct.getParameters(type).getMinSum(), nationalCurrency);
			default:
				throw new IllegalArgumentException("����������� ��� ������������ ��������");
		}
	}

	/**
	 * �������� �������� �������� ������� �� id
	 * @param id ��������
	 * @return �������� ��������
	 */
	public static String getPersonPortfolioTypeById(long id)
	{
		try
		{
			PersonPortfolio portfolio = personPortfolioService.findPersonPortfolioById(id);
			if (portfolio == null)
				return "";
			return portfolio.getType().getDescription();
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ������ �������� ������� ",e);
			return "";
		}
	}

	/**
	 * �������� parentId ���� ���������� ��������
	 * @param id ��������/���� ��������
	 * @return parentId
	 */
	public static Long getInsuranceTypeParentId(long id)
	{
		try
		{
			InsuranceType insuranceType = insuranceTypeService.getById(id);
			if (insuranceType == null)
				return null;
			if (insuranceType.getParent() == null)
				return null;
			return insuranceType.getParent().getId();
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ������ ���� ���������� �������� ",e);
			return null;
		}
	}

	/**
	 * @param productId �������������
	 * @return ������������� �������� ��� ��������� ��������
	 */
	public static Long getInsuranceProductImageId(Long productId)
	{
		try
		{
			return insuranceTypeService.getInsuranceProductImageId(productId);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ������ ���� ���������� �������� ",e);
			return null;
		}
	}

	/**
	 * @param typeId �������������
	 * @return ������������� �������� ��� ����/���� ��������� ��������
	 */
	public static Long getInsuranceTypeImageId(Long typeId)
	{
		try
		{
			return insuranceTypeService.getInsuranceTypeImageId(typeId);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ������ ���������� �������� ",e);
			return null;
		}
	}

	/**
	 * ����������� ����������� ������������� ������� � ��������.
	 * ��������� ������� ������ �� ������� � ����������� � ������������� ������ �������� � �����������
	 * @param profile ������� ����-�������
	 * @param dictionaryProductId - ������ �� ������� � �����������
	 * @param dictionaryProductType - ��� ��������
	 * @return true - ���� � ����������� ��������� ���� ������� �� ������ �������� ������� �������� � ��������.
	 */
	public static boolean isCanEditPfpProduct(PersonRiskProfile profile, Long dictionaryProductId, String dictionaryProductType)
	{
		try
		{
			if(dictionaryProductId == null)
				return false;
			DictionaryProductType productType = DictionaryProductType.valueOf(dictionaryProductType);
			DictionaryProductType globalProductType = productType;
			if (productType == DictionaryProductType.COMPLEX_INVESTMENT_FUND || productType == DictionaryProductType.COMPLEX_INVESTMENT_FUND_IMA)
				globalProductType = DictionaryProductType.COMPLEX_INVESTMENT;

			if (!profile.getAvailableProducts().contains(globalProductType))
				return false;

			ProductBase productBase = null;
			switch (productType)
			{
				case INSURANCE:
					productBase = productService.getById(dictionaryProductId, InsuranceProduct.class);
					break;
				case PENSION:
					productBase = productService.getById(dictionaryProductId,PensionProduct.class);
					break;
				case ACCOUNT:
					productBase = productService.getById(dictionaryProductId, AccountProduct.class);
					break;
				case IMA:
					productBase = productService.getById(dictionaryProductId, IMAProduct.class);
					break;
				case FUND:
					productBase = productService.getById(dictionaryProductId, FundProduct.class);
					break;
				case TRUST_MANAGING:
					productBase = productService.getById(dictionaryProductId, TrustManagingProduct.class);
					break;
				case COMPLEX_INSURANCE:
					productBase = productService.getById(dictionaryProductId, ComplexInsuranceProduct.class);
					break;
				case COMPLEX_INVESTMENT_FUND:
					productBase = productService.getById(dictionaryProductId, ComplexFundInvestmentProduct.class);
					break;
				case COMPLEX_INVESTMENT_FUND_IMA:
					productBase = productService.getById(dictionaryProductId, ComplexIMAInvestmentProduct.class);
					break;
			}
			return productBase != null && productBase.isEnabled();
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� ����������� �������������� �������� ",e);
			return false;
		}
	}

	/**
	 * @return ���������� ������������ ���������� ���, �� ������� ����� �������� ������������
	 */
	public static int getMaxPlanningYear()
	{
		return ConfigFactory.getConfig(PfpConfig.class).getYearCount();
	}


	/**
	 * @return ������, ������������ ��� ������ �� ��������� � ������� ��� ������ �������
	 */
	public static final Long getDefaultPeriodValue()
	{
		try
		{
			Long value = defaultPeriodSettingService.getDefaultPeriodSettingValue();
			return value == null? 0: value;
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� �������, ������������� �� ���������.", e);
			return 0L;
		}
	}
}
