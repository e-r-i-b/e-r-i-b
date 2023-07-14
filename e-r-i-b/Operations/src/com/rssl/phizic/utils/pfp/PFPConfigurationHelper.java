package com.rssl.phizic.utils.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProductPart;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductService;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParametersService;
import com.rssl.phizic.config.*;
import com.rssl.phizic.business.ima.IMAProductPart;
import com.rssl.phizic.business.ima.IMAProductService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилитный класс для получения различных настроек ПФП
 */
public class PFPConfigurationHelper extends Config
{
	public static final String RECOMMENDATIONS_START_CAPITAL    = "condition.displaying.recommendations.START_CAPITAL";
	public static final String RECOMMENDATIONS_QUARTERLY_INVEST = "condition.displaying.recommendations.QUARTERLY_INVEST";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ProductTypeParametersService service = new ProductTypeParametersService();
	private static final ProductService productService = new ProductService();
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final IMAProductService imaProductService = new IMAProductService();

	public PFPConfigurationHelper(PropertyReader reader)
	{
		super(reader);
	}

	private static PFPConfigurationHelper getIt()
	{
		return ConfigFactory.getConfig(PFPConfigurationHelper.class);
	}

	/**
	 * @return максимально допустимая доходность портфеля "Стартовый капитал" при которой отображаются рекомандации
	 */
	public static BigDecimal getStartCapitalMaxWantedIncome()
	{
		try
		{
			return new BigDecimal(getIt().getProperty(RECOMMENDATIONS_START_CAPITAL));
		}
		catch (Exception e)
		{
			log.error("Не удалось получить максимальную доходность портфеля \"Стартовый капитал\" для отображения рекомендаций",e);
			return BigDecimal.ZERO;
		}
	}

	/**
	 * @return максимально допустимая доходность портфеля "Ежеквартальные вложения" при которой отображаются рекомандации
	 */
	public static BigDecimal getQuarterlyInvestMaxWantedIncome()
	{
		try
		{
			return new BigDecimal(getIt().getProperty(RECOMMENDATIONS_QUARTERLY_INVEST));
		}
		catch (Exception e)
		{
			log.error("Не удалось получить максимальную доходность портфеля \"Ежеквартальные вложения\" для отображения рекомендаций",e);
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Возвращает параметры для типа продукта
	 * @param productType - тип продукта
	 * @return параметры
	 * @throws BusinessException
	 */
	public static ProductTypeParameters getProductTypeParameters(String productType) throws BusinessException
	{
		return service.findByProductType(DictionaryProductType.valueOf(productType), null);
	}

	/**
	 * Возвращает депозитный продукт, который привязан к данному типу вклада ПФП
	 * @param accountId - идентификатор типа вклада ПФП
	 * @return депозитный продукт
	 * @throws BusinessException
	 */
	public static DepositProductPart getDepositByAccountPfpId(Long accountId) throws BusinessException
	{
		AccountProduct accountProduct = productService.getById(accountId, AccountProduct.class, null);
		if (accountProduct == null)
			return null;

		if (accountProduct.getAccountId() == null)
			return null;

		return depositProductService.findPartByProductId(accountProduct.getAccountId());
	}

	/**
	 * Возвращает ОМС, который привязан к данному типу ОМС ПФП
	 * @param imaId - идентификатор типа ОМС ПФП
	 * @return ОМС
	 * @throws BusinessException
	 */
	public static IMAProductPart getIMAProductIdByIMAPfpId(Long imaId) throws BusinessException
	{
		IMAProduct imaProduct = productService.getById(imaId, IMAProduct.class, null);
		if (imaProduct == null)
			return null;

		if (imaProduct.getImaId() == null)
			return null;

		return imaProductService.findByExternalId(imaProduct.getImaId(), imaProduct.getImaAdditionalId());
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
