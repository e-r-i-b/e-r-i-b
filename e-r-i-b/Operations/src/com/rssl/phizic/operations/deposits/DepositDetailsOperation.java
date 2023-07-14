package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.deposits.*;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyUtils;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDepositService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityCurrencyComparator;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.operations.restrictions.DepositProductRestriction;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Kosyakov
 * @ created 12.05.2006
 * @ $Author: egorovaav $
 * @ $Revision: 85158 $
 */
public class DepositDetailsOperation extends OperationBase<DepositProductRestriction> implements ViewEntityOperation
{
	private static DepositProductService service = new DepositProductService();
	private static final DepositEntityService depositEntityService = new DepositEntityService();
	private static final ClientPromoCodeService clientPromoCodeService = new ClientPromoCodeService();
	private static final PromoCodeDepositService promoCodeDepositService = new PromoCodeDepositService();

	private DepositProduct depositProduct;

	public Source getTemplateSource() throws BusinessException
	{
		DepositGlobal global = service.getGlobal();
		return new StreamSource(new StringReader(global.getDefaultDetailsTransformation()));
	}

	public Source getMobileTemplateSource() throws BusinessException
	{
		DepositGlobal global = service.getGlobal();
		return new StreamSource(new StringReader(global.getMobileDetailsTransformation()));
	}

	public void initialize ( Long depositId ) throws BusinessException
	{
		DepositProduct temp = service.findById(depositId);
		if(!getRestriction().accept(temp))
			new AccessException("Нет доступа. Депозитный продукт id:" + temp.getId());
		depositProduct = temp;
	}

	public DepositProduct getEntity()
	{
		return depositProduct;
	}

	/**
	 * Определение подвида вклада, для вкладов с первоначальным взносом также определяем процентную ставку и
	 * минимальный размер доп. взноса
	 * @param depositType вид вклада
	 * @param openingDate дата открытия
	 * @param period срок вклада
	 * @param currencyCode буквенный ISO-код валюты вклада
	 * @param minBalance неснижаемый остаток/первоначальный взнос
	 * @return карта <поле AccountOpeningClaim, значение>
	 */
	public Map<String, String> getDepositInfo(Long depositType, Calendar openingDate, DateSpan period, String currencyCode, BigDecimal minBalance, String depositGroup, String segmentCode, Boolean isPromoDeposit) throws BusinessException, BusinessLogicException
	{
		Map<String, String> result = null;
		try
		{
			boolean pension = PersonHelper.isPensioner();
			String tarifPlanCodeType = PersonContext.getPersonDataProvider().getPersonData().getPerson().getTarifPlanCodeType();
			if (!ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				DepositProduct product = service.findByProductId(depositType);
				Element description = XmlHelper.parse(product.getDescription()).getDocumentElement();
				result = DepositProductHelper.calculateRate(openingDate, period, currencyCode, minBalance,
						pension, description, tarifPlanCodeType, tarifPlanCodeType, depositGroup, segmentCode, isPromoDeposit);
			}
			else
			{
				if ((MobileApiUtil.isMobileApiLT(MobileAPIVersions.V8_00) || ApplicationUtil.isATMApi()) && StringHelper.isEmpty(depositGroup))
				{
					MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
					Map<String, String> allowedCodes = mobileApiConfig.getOldDepositCodesList();
					depositGroup = allowedCodes.get(depositType.toString());
					depositGroup = "-22".equals(depositGroup) ? "0" : depositGroup;
				}
				result = DepositProductHelper.calculateEntityRate(openingDate, period, currencyCode, minBalance, pension, depositType, tarifPlanCodeType, tarifPlanCodeType, depositGroup, segmentCode);
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		String errorText = result.get(DepositProductHelper.CALC_RATE_ERROR);
		if (StringHelper.isNotEmpty(errorText))
			throw new BusinessLogicException(errorText);

		return result;
	}

	/**
	 * Полная информация о вкладном продукте (из загруженных справочников ЦАС НСИ)
	 * @param depositType - вид вклада
	 * @param group - группа вклада
	 * @param segment - код промо-сегмента
	 * @param pensioner - искать ли пенсионные ставки
	 * @param clientTariffPlan - тарифный план
	 * @return депозитный продукт
	 * @throws BusinessException
	 */
	public DepositProductEntity getDepositProductEntity(Long depositType, Long group, String segment, boolean pensioner, Long clientTariffPlan, String tb) throws BusinessException
	{
		List<PromoCodeDeposit> actualClientPromoCodes = new ArrayList<PromoCodeDeposit>();

		if (ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
		{
			actualClientPromoCodes.addAll(promoCodeDepositService.getActualPromoCodesDepositList());
		}
		else
		{
			ClientPromoCode clientPromoCode = clientPromoCodeService.getPromoCodeBySegment(segment);
			if (clientPromoCode != null)
				actualClientPromoCodes.add(clientPromoCode.getPromoCodeDeposit());
		}

		ApplicationConfig config = ApplicationConfig.getIt();
		boolean admin = config.getApplicationInfo().isAdminApplication();

		return depositEntityService.getDepositProductEntity(depositType, group, actualClientPromoCodes, TariffPlanHelper.getDictionaryTariffCodes(), pensioner, clientTariffPlan, admin, tb);
	}

	/**
	 * Получить расширенную информацию о вкладном продукте
	 * @param type - вид вклада
	 * @param group - группа
	 * @return поля из таблицы FIELD_TDOG, использующиеся на странице детальной информации
	 * @throws BusinessException
	 */
	public DepositsTDOG getDepositAdditionalInfo(Long type, Long group) throws BusinessException
	{
		DepositsTDOG depositsTDOG = service.getDepositAdditionalInfo(type, group, null);
		prepareDepositAdditionalInfo(depositsTDOG);
		return depositsTDOG;
	}

	/**
	 * Обработать переменные в описании вклада
	 * @param depositsTDOG - параметры условий по вкладам
	 * @throws BusinessException
	 */
	public void prepareDepositAdditionalInfo(DepositsTDOG depositsTDOG) throws BusinessException
	{
		if (depositsTDOG == null)
			return;

		String incomeOrder = depositsTDOG.getIncomeOrder();
		if (StringHelper.isEmpty(incomeOrder))
			return;

		if (incomeOrder.contains("{KOD_VAL}"))
		{
			List<String> currencies = service.getMinSubTypeCurrencies(depositsTDOG.getDepositType(), depositsTDOG.getDepositSubType());
			StringBuffer sb = new StringBuffer();
			for (String currency : currencies)
			{
				String currCode = CurrencyUtils.getCurrencyCodeByNumericCode(currency);
				String currDescription = com.rssl.phizic.utils.CurrencyUtils.getCurrencySign(currCode);

				if(sb.length() > 0)
					sb.append(", ");
				sb.append(currDescription);
			}

			depositsTDOG.setIncomeOrder(incomeOrder.replace("{KOD_VAL}", sb.toString()));
		}
	}

	/**
	 * Получить значения минимальны доп. взносов и валют для вкладного продукта
	 * @param type - вид вклада
	 * @param group - группа
	 * @return мапа <валюта, сумма>
	 * @throws BusinessException
	 */
	public Map<String, BigDecimal> getMinAdditionalFeeValues(Long type, Long group) throws BusinessException
	{
		Map<String, BigDecimal> values = service.getMinAdditionalFeeValues(type, group);
		SortedMap<String, BigDecimal> sortedValues = new TreeMap<String, BigDecimal>(DepositEntityCurrencyComparator.getInstance());
		for (String key : values.keySet())
		{
			String currencyCode = CurrencyUtils.getCurrencyCodeByNumericCode(key);

			if(StringHelper.isEmpty(currencyCode))
				continue;
			sortedValues.put(currencyCode, values.get(key));
		}
		return sortedValues;
	}
}
