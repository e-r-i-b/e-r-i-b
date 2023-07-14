package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceDatePeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.*;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.*;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.*;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.*;
import com.rssl.phizic.business.pfp.portfolio.product.*;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.documents.State;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mihaylov
 * @ created 22.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция добавления продукта в портфель
 */
public class AddPfpProductOperation extends EditPfpOperationBase
{
	private static final State EDIT_PERSON_PORTFOLIOS = new State("EDIT_PERSON_PORTFOLIOS");
	private static final ProductService productService = new ProductService();
	private static final ComplexProductService complexProductService = new ComplexProductService();

	private PersonPortfolio portfolio;
	private ProductBase product; // добавляемый продукт
	private PortfolioProduct editProduct;
	private DictionaryProductType productType;

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(EDIT_PERSON_PORTFOLIOS);
	}

	public void initializeForEdit(Long portfolioId, Long editProductId) throws BusinessException
	{
		portfolio = getPortfolio(personalFinanceProfile,portfolioId);
		portfolio.setPortfolioState(PortfolioState.EDIT);

		for(PortfolioProduct portfolioProduct: portfolio.getPortfolioProductList())
			if(portfolioProduct.getId().equals(editProductId))
				editProduct = portfolioProduct;
		if(editProduct == null)
			throw new BusinessException("Продукт с идентификатором " + editProductId + " не найден в портфеле клиента");

		updateEditableProduct(editProduct);

		productType = editProduct.getProductType();
		product = getProduct(editProduct.getDictionaryProductId(),productType);
		if(product == null)
			throw new BusinessException("Вы не можете отредактировать продукт, так как он был удален из системы «Сбербанк Онлайн». Если Вы хотите изменить распределение средств в портфеле, удалите этот продукт и выберите другой.");
	}

	public void initialize(Long portfolioId, Long productId, DictionaryProductType dictionaryProductType) throws BusinessException
	{
		DictionaryProductType productTypeForChecking = dictionaryProductType;
		if (dictionaryProductType == DictionaryProductType.COMPLEX_INVESTMENT_FUND || dictionaryProductType == DictionaryProductType.COMPLEX_INVESTMENT_FUND_IMA)
			productTypeForChecking = DictionaryProductType.COMPLEX_INVESTMENT;
		if (!personalFinanceProfile.getPersonRiskProfile().getAvailableProducts().contains(productTypeForChecking))
			throw new BusinessException("Данный тип продукта ("+ dictionaryProductType.getDescription()+ ") не может быть добавлен в портфель.");

		portfolio = getPortfolio(personalFinanceProfile,portfolioId);
		portfolio.setPortfolioState(PortfolioState.EDIT);
		productType = dictionaryProductType;
		PortfolioType portfolioType = portfolio.getType();
		if(!portfolioType.isProductTypeAvailable(productType))
			throw new BusinessException("Данный тип продукта ("+ productType.getDescription()+ ") не может быть добавлен в портфель " + portfolioType.getDescription());

		product = getProduct(productId,dictionaryProductType);
		if(product == null)
			throw new BusinessException("Вы не можете добавить продукт, так как он был удален из системы «Сбербанк Онлайн». Пожалуйста выберите другой продукт.");

		//дополнительная проверка продукта по сегменту клиента
		SegmentCodeType segmentCodeType = getCurrentClientSegment();
		Set<SegmentCodeType> productTargetGroup = product.getTargetGroup();
		if (!productTargetGroup.contains(segmentCodeType))
			throw new BusinessException("Данный продукт не может быть добавлен в портфель " + portfolioType.getDescription());
	}


	private ProductBase getProduct(Long productId, DictionaryProductType productType) throws BusinessException
	{
		switch (productType)
		{
			case INSURANCE:
				return productService.getById(productId, InsuranceProduct.class);
			case PENSION:
				return productService.getById(productId, PensionProduct.class);
			case ACCOUNT:
				return productService.getById(productId, AccountProduct.class);
			case IMA:
				return productService.getById(productId, IMAProduct.class);
			case FUND:
				return productService.getById(productId, FundProduct.class);
			case TRUST_MANAGING:
				return productService.getById(productId, TrustManagingProduct.class);
			case COMPLEX_INSURANCE:
				return complexProductService.getByIdFilterByAge(productId, PersonHelper.getPersonAge(getPerson()).longValue());
			case COMPLEX_INVESTMENT_FUND:
				return productService.getById(productId, ComplexFundInvestmentProduct.class);
			case COMPLEX_INVESTMENT_FUND_IMA:
				return productService.getById(productId, ComplexIMAInvestmentProduct.class);
			default:
				throw new IllegalArgumentException("Неизвестный тип добавляемого продукта");
		}
	}

	public ProductBase getProduct()
	{
		return product;
	}

	public PortfolioProduct getEditProduct()
	{
		return editProduct;
	}

	public PersonPortfolio getPortfolio()
	{
		return portfolio;
	}

	public DictionaryProductType getProductType()
	{
		return productType;
	}

	public void addProduct(Map<String,Object> result) throws BusinessException, BusinessLogicException
	{
		//сохраняем текущее состояние в БД. Этот код необходим для корректного редактирования списка продуктов у клиента, когда
		//в портфеле всего один продукт. Hibernate не может корректно удалить один продукт из листа и добавить другой в одной транзакции.
		pfpService.addOrUpdateProfile(personalFinanceProfile);
		PortfolioProduct additionalPortfolioProduct = updateAdditionalPortfolio(result);
		PortfolioProduct portfolioProduct = getProduct(result,portfolio.getType());
		portfolio.addPortfolioProduct(portfolioProduct);
		pfpService.addOrUpdateProfile(personalFinanceProfile);

		linkProduct(portfolioProduct,additionalPortfolioProduct);
	}

	private void linkProduct(PortfolioProduct product1, PortfolioProduct product2) throws BusinessException
	{
		if(product1 == null || product2 == null)
			return;
		LinkedBaseProduct linkedBaseProduct1 = product1.getLinkedBaseProduct();
		LinkedBaseProduct linkedBaseProduct2 = product2.getLinkedBaseProduct();

		if(linkedBaseProduct1 == null || linkedBaseProduct2 == null)
			return;

		linkedBaseProduct1.setLinkedProductId(linkedBaseProduct2.getId());
		linkedBaseProduct2.setLinkedProductId(linkedBaseProduct1.getId());
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * Обновляем данные других портфелей. В связи с тем, что при добавлении страховых продуктов в "стартовый капитал"
	 * они автоматически должны учитываться и в "ежеквартальных вложениях" приходится писать такие методы
	 * @param result
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private PortfolioProduct updateAdditionalPortfolio(Map<String,Object> result) throws BusinessException, BusinessLogicException
	{
		//выполняем только для страховых продуктов
		if(productType != DictionaryProductType.INSURANCE && productType != DictionaryProductType.COMPLEX_INSURANCE && productType != DictionaryProductType.PENSION)
			return null;
		List<PersonPortfolio> portfolioList = personalFinanceProfile.getPortfolioList();
		for(PersonPortfolio additionalPortfolio: portfolioList)
			if(additionalPortfolio.getType() != portfolio.getType())
			{
				PortfolioProduct addProduct = getProduct(result,additionalPortfolio.getType());
				additionalPortfolio.addPortfolioProduct(addProduct);
				return addProduct;
			}
		return null;
	}

	private PortfolioProduct getProduct(Map<String,Object> result, PortfolioType portfolioType) throws BusinessException, BusinessLogicException
	{
		List<BaseProduct> baseProductList = getBaseProductList(result,portfolioType);
		if(CollectionUtils.isEmpty(baseProductList))
			return null;//продукт без базовых не имеет смысла, не создаем такие продукты
		PortfolioProduct portfolioProduct = new PortfolioProduct();
		portfolioProduct.setName(getPortfolioProductName(product, productType));
		portfolioProduct.setProductType(productType);
		portfolioProduct.setDictionaryProductId(product.getId());
		portfolioProduct.setImageId(product.getImageId());
		portfolioProduct.setBaseProductList(baseProductList);
		return portfolioProduct;
	}

	private String getPortfolioProductName(ProductBase productBase, DictionaryProductType productType)
	{
		if(productBase instanceof ComplexInsuranceProduct)
		{
			ComplexProductBase complexProduct = (ComplexProductBase) productBase;
			StringBuilder sb = new StringBuilder();
			sb.append(productType.getDescription());
			sb.append(" «");
			sb.append(complexProduct.getAccount().getName());
			sb.append("»");
			return sb.toString();
		}
		else
		    return productType.getDescription();
	}


	private List<BaseProduct> getBaseProductList(Map<String,Object> result, PortfolioType portfolioType) throws BusinessException, BusinessLogicException
	{
		List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
		switch (productType)
		{
			case INSURANCE:
				CollectionUtils.addIgnoreNull(baseProductList,getInsuranceProduct(result,product,portfolioType));
				break;
			case PENSION:
				CollectionUtils.addIgnoreNull(baseProductList,getPensionProduct(result, (PensionProduct) product, portfolioType));
				break;
			case ACCOUNT:
				CollectionUtils.addIgnoreNull(baseProductList,getAccountProduct(result, product));
				break;
			case IMA:
				CollectionUtils.addIgnoreNull(baseProductList,getInvestmentProduct(result, (IMAProduct) product));
				break;
			case FUND:
				CollectionUtils.addIgnoreNull(baseProductList,getInvestmentProduct(result, (FundProduct) product));
				break;
			case TRUST_MANAGING:
				CollectionUtils.addIgnoreNull(baseProductList,getTrustManagingProduct(result, (TrustManagingProduct) product));
				break;
			case COMPLEX_INSURANCE:
				baseProductList = getComplexInsuranceProductList(result,product,portfolioType);
				break;
			case COMPLEX_INVESTMENT_FUND:
				baseProductList = getComplexInvestmentFundProductList(result,product);
				break;
			case COMPLEX_INVESTMENT_FUND_IMA:
				baseProductList = getComplexInvestmentFundIMAProductList(result,product);
				break;
		}
		return baseProductList;
	}

	private BaseProduct getAccountProduct(Map<String,Object> result, ProductBase productBase) throws BusinessException
	{
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setProductType(productBase.getProductType());
		baseProduct.setProductName(productBase.getName());
		baseProduct.setDescription(productBase.getDescription());
		baseProduct.setDictionaryProductId(productBase.getId());
		BigDecimal amount = (BigDecimal)result.get("accountAmount");
		baseProduct.setAmount(new Money(amount, MoneyUtil.getNationalCurrency()));
		if(productBase.getMinIncome() == null && productBase.getMaxIncome() == null)
			baseProduct.setIncome(productBase.getDefaultIncome());
		else
			baseProduct.setIncome((BigDecimal)result.get("accountIncome"));
		return baseProduct;
	}

	private InvestmentBaseProduct getInvestmentProduct(Map<String,Object> result, InvestmentProduct productBase) throws BusinessException
	{
		String productType = productBase.getProductType().name().toLowerCase();
		InvestmentBaseProduct investmentProduct = new InvestmentBaseProduct();
		investmentProduct.setProductType(productBase.getProductType());
		investmentProduct.setProductName(productBase.getName());
		investmentProduct.setDescription(productBase.getDescription());
		investmentProduct.setDictionaryProductId(productBase.getId());
		BigDecimal amount = (BigDecimal)result.get(productType + "Amount");
		investmentProduct.setAmount(new Money(amount, MoneyUtil.getNationalCurrency()));
		if(productBase.getMinIncome() == null && productBase.getMaxIncome() == null)
			investmentProduct.setIncome(productBase.getDefaultIncome());
		else
			investmentProduct.setIncome((BigDecimal)result.get(productType + "Income"));

		if (productBase.getRisk() != null)
			investmentProduct.setRiskName(productBase.getRisk().getName());
		if (productBase.getInvestmentPeriod() != null)
			investmentProduct.setInvestmentPeriod(productBase.getInvestmentPeriod().getPeriod());

		return investmentProduct;
	}

	private InvestmentBaseProduct getTrustManagingProduct(Map<String,Object> result, TrustManagingProduct productBase) throws BusinessException
	{
		InvestmentBaseProduct investmentProduct = new InvestmentBaseProduct();
		investmentProduct.setProductType(productBase.getProductType());
		investmentProduct.setProductName(productBase.getName());
		investmentProduct.setDescription(productBase.getDescription());
		investmentProduct.setDictionaryProductId(productBase.getId());
		BigDecimal amount = (BigDecimal)result.get("trust_managingAmount");
		investmentProduct.setAmount(new Money(amount, MoneyUtil.getNationalCurrency()));
		if(productBase.getMinIncome() == null && productBase.getMaxIncome() == null)
			investmentProduct.setIncome(productBase.getDefaultIncome());
		else
			investmentProduct.setIncome((BigDecimal)result.get("trust_managingIncome"));

		if (productBase.getRisk() != null)
			investmentProduct.setRiskName(productBase.getRisk().getName());
		if (productBase.getInvestmentPeriod() != null)
			investmentProduct.setInvestmentPeriod(productBase.getInvestmentPeriod().getPeriod());

		return investmentProduct;
	}

	private PensionBaseProduct getPensionProduct(Map<String,Object> result, PensionProduct pensionProduct, PortfolioType portfolioType) throws BusinessException
	{
		PensionBaseProduct pensionBaseProduct = new PensionBaseProduct();
		pensionBaseProduct.setProductType(DictionaryProductType.PENSION);
		pensionBaseProduct.setProductName(pensionProduct.getName());
		pensionBaseProduct.setDescription(pensionProduct.getDescription());
		pensionBaseProduct.setPensionFund(pensionProduct.getPensionFund().getName());
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();
		if(portfolioType == PortfolioType.START_CAPITAL)
		{
			BigDecimal amount = (BigDecimal)result.get("pensionStartAmount");
			pensionBaseProduct.setAmount(new Money(amount,nationalCurrency));
			BigDecimal quarterlyAmount = (BigDecimal)result.get("pensionQuarterlyAmount");
			pensionBaseProduct.setQurterlyInvest(quarterlyAmount);
		}
		else
		{
			BigDecimal amount = (BigDecimal)result.get("pensionQuarterlyAmount");
			pensionBaseProduct.setAmount(new Money(amount,nationalCurrency));
		}

		pensionBaseProduct.setSelectedPeriod((Long) result.get("pensionPeriod"));
		pensionBaseProduct.setDictionaryProductId(pensionProduct.getId());
		if(pensionProduct.getMinIncome() == null && pensionProduct.getMaxIncome() == null)
			pensionBaseProduct.setIncome(pensionProduct.getDefaultIncome());
		else
			pensionBaseProduct.setIncome((BigDecimal)result.get("pensionIncome"));
		return pensionBaseProduct;
	}

	private InsuranceBaseProduct getInsuranceProduct(Map<String,Object> result, ProductBase productBase, PortfolioType portfolioType) throws BusinessException, BusinessLogicException
	{
		InsuranceProduct insuranceProduct = (InsuranceProduct) productBase;
		Long selectedPeriodId = (Long) result.get("selectedPeriodId");
		InsuranceDatePeriod selectedPeriod = null;
		for(InsuranceDatePeriod insurancePeriod : insuranceProduct.getPeriods())
			if(insurancePeriod.getId().equals(selectedPeriodId))
				selectedPeriod = insurancePeriod;
		if(selectedPeriod == null)
			throw new BusinessLogicException("Выбранный период не найден в справочнике");

		if(selectedPeriod.getType().getMonths() == null && !portfolioType.isProductTypeAvailable(DictionaryProductType.INSURANCE))
			return null;

		InsuranceBaseProduct insuranceBaseProduct = new InsuranceBaseProduct();
		insuranceBaseProduct.setProductType(DictionaryProductType.INSURANCE);
		insuranceBaseProduct.setProductName(insuranceProduct.getName());
		insuranceBaseProduct.setDescription(insuranceProduct.getDescription());
		insuranceBaseProduct.setDictionaryProductId(insuranceProduct.getId());
		insuranceBaseProduct.setSelectedPeriodId(selectedPeriod.getId());
		insuranceBaseProduct.setSelectedPeriodName(selectedPeriod.getType().getName());
		insuranceBaseProduct.setMonthInPeriod(selectedPeriod.getType().getMonths());
		insuranceBaseProduct.setInsuranceCompanyName(insuranceProduct.getInsuranceCompany().getName());
		if(insuranceProduct.getMinIncome() == null && insuranceProduct.getMaxIncome() == null)
			insuranceBaseProduct.setIncome(insuranceProduct.getDefaultIncome());
		else
			insuranceBaseProduct.setIncome((BigDecimal)result.get("insuranceIncome"));
		Long selectedTerm = (Long) result.get("selectedTerm");
		insuranceBaseProduct.setSelectedTermValue(selectedTerm);

		BigDecimal amount = (BigDecimal)result.get("insuranceAmount");
		if(portfolioType.getMonthCount() != null)
		{
			amount = amount.multiply(new BigDecimal(portfolioType.getMonthCount()));//умножаем на количество месяцев в периоде портфеля
			amount = amount.divide(new BigDecimal(selectedPeriod.getType().getMonths()), 2, RoundingMode.UP);//делим на колличество месяцев в выбранной периодичности страхового продукта
		}
		insuranceBaseProduct.setAmount(new Money(amount, MoneyUtil.getNationalCurrency()));

		BigDecimal insuranceSumAmount = (BigDecimal) result.get("insuranceSum");
		insuranceBaseProduct.setInsuranceSum(insuranceSumAmount);
		return insuranceBaseProduct;
	}

	private List<BaseProduct> getComplexInsuranceProductList(Map<String,Object> result, ProductBase productBase, PortfolioType portfolioType) throws BusinessException, BusinessLogicException
	{
		List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
		ComplexInsuranceProduct complexInsuranceProduct = (ComplexInsuranceProduct) productBase;

		/*формируем данные по вкладу*/
		if(portfolioType.isProductTypeAvailable(DictionaryProductType.COMPLEX_INSURANCE))
		{
			BaseProduct accountProduct = getAccountProduct(result, complexInsuranceProduct.getAccount());
			baseProductList.add(accountProduct);
		}		

		/*формируем данные по страховому продукту*/
		Long insuranceProductId = (Long) result.get("insuranceProductId");
		InsuranceProduct selectedInsuranceProduct = null;
		for(InsuranceProduct tempProduct : complexInsuranceProduct.getInsuranceProducts())
		{
			if(tempProduct.getId().equals(insuranceProductId))
				selectedInsuranceProduct = tempProduct;
		}
		if(selectedInsuranceProduct == null)
			throw new BusinessLogicException("Заданный страховой продукт не доступен для данного комплексного продукта");

		InsuranceBaseProduct insuranceProduct = getInsuranceProduct(result, selectedInsuranceProduct, portfolioType);
		if(insuranceProduct != null)
			baseProductList.add(insuranceProduct);

		return baseProductList;
	}

	private List<BaseProduct> getComplexInvestmentFundProductList(Map<String,Object> result, ProductBase productBase) throws BusinessException, BusinessLogicException
	{
		List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
		ComplexInvestmentProductBase complexInvestmentProduct = (ComplexInvestmentProductBase) productBase;

		/*формируем данные по вкладу*/
		BaseProduct accountProduct = getAccountProduct(result, complexInvestmentProduct.getAccount());
		baseProductList.add(accountProduct);

		/*формируем данные по ПИФУ*/
		Long fundProductId = (Long) result.get("fundProductId");
		FundProduct selectedFundProduct = null;
		for(FundProduct tempFundProduct: complexInvestmentProduct.getFundProducts())
			if(tempFundProduct.getId().equals(fundProductId))
				selectedFundProduct = tempFundProduct;
		if(selectedFundProduct == null)
			throw new BusinessLogicException("Заданный страховой продукт не доступен для данного комплексного инвестиционного продукта");

		BaseProduct fundProduct = getInvestmentProduct(result, selectedFundProduct);
		baseProductList.add(fundProduct);

		return baseProductList;
	}

	private List<BaseProduct> getComplexInvestmentFundIMAProductList(Map<String,Object> result, ProductBase productBase) throws BusinessException, BusinessLogicException
	{
		List<BaseProduct> baseProductList = getComplexInvestmentFundProductList(result,productBase);
		ComplexIMAInvestmentProduct complexIMAInvestmentProduct = (ComplexIMAInvestmentProduct) productBase;

		/*формируем данные по ОМС*/
		Long imaProductId = (Long) result.get("imaProductId");
		IMAProduct selectedImaProduct = null;
		for(IMAProduct tempImaProduct: complexIMAInvestmentProduct.getImaProducts())
			if(tempImaProduct.getId().equals(imaProductId))
				selectedImaProduct = tempImaProduct;
		if(selectedImaProduct == null)
			throw new BusinessLogicException("Заданный страховой продукт не доступен для данного комплексного инвестиционного продукта");

		BaseProduct imaProduct = getInvestmentProduct(result, selectedImaProduct);
		baseProductList.add(imaProduct);

		return baseProductList;
	}

	/**
	 * @param profile - финансовый профиль клиента
	 * @param portfolioId - идентификатор портфеля
	 * @return возвращает портфель клиента
	 */
	private PersonPortfolio getPortfolio(PersonalFinanceProfile profile, Long portfolioId) throws BusinessException
	{
		for(PersonPortfolio portfolio : profile.getPortfolioList())
			if(portfolio.getId().equals(portfolioId))
				return portfolio;
		throw new BusinessException("Заданный портфель не является портфелем клиента");
	}


	private void updateEditableProduct(PortfolioProduct product)
	{
		if(product.getState() == PortfolioProductState.SAVE)
		{
			PortfolioHelper.upadateAdditionalPortfolioState(personalFinanceProfile,product,portfolio,PortfolioProductState.DELETED);
			product.setState(PortfolioProductState.DELETED);
		}
		else if(product.getState() == PortfolioProductState.ADD)
		{
			PortfolioHelper.upadateAdditionalPortfolioState(personalFinanceProfile,product,portfolio,null);
			portfolio.getPortfolioProductList().remove(product);
		}
	}
}
