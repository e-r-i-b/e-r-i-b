package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductShortCut;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntitySubType;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductRate;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import net.sf.ehcache.Cache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * Хелпер для работы с вкладами.
 * @author Pankin
 * @ created 09.12.2011
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"MethodWithTooManyParameters"})
public class DepositProductHelper
{
	public static final String ELEMENT_FOREACH_PATH = "/product/data/options/element";
	// XPath выражение для получения имени вклада по нашему описанию
	public static final String NAME_XPATH = "/product/data/name";
	// XPath выражение для получения имени подвида вклада по нашему описанию
	private static final String SUB_KIND_NAME_PATH = "/product/data/options/element[subTypeName != '' and availToOpen = 'true']/subTypeName";

	// Часть XPath выражения для получения описаний подвидов вклада, образующих вкладной продукт (основная часть, используется всегда)
	private static final String BASIC_PATH = "/product/data/options/element[translate(dateBegin, '.', '')<='%s'" +
			" and translate(dateEnd, '.', '')>'%<s' and translate(interestDateBegin, '.', '')<='%<s'" +
			" and currencyCode = '%s' and availToOpen = 'true' ";
	// Часть XPath выражения для получения описаний подвидов вклада с учетом известного ТП клиента
	private static final String KNOWN_TARIFF_PATH = "and tariffPlanCode = %s ";
	// Часть XPath выражения для получения описаний подвидов вклада, для которых ТП отсутствует
	private static final String UNKNOWN_TARIFF_PATH = "and tariffPlanCode != 1 and tariffPlanCode != 2 and tariffPlanCode != 3 ";
	// Часть XPath выражения для получения описаний подвидов вклада с учетом группы. Может не использоваться.
	private static final String GROUP_PATH = "and group/groupCode = %s ";
	// Часть XPath выражения для получения описаний подвидов вклада с учетом зависимости от ТП
	private static final String TARIFF_DEPENDENCE_PATH = "and ((count(tariffDependence) = 0) or (tariffDependence/tariff/tariffCode = %s and translate(tariffDependence/tariff/tariffDateBegin, '.', '')<='%s' and translate(tariffDependence/tariff/tariffDateEnd, '.', '')>'%<s'))";

	private static final String PRIOR_SEGMENT_PATH = "and (segmentCode = '%s' and group/groupParams/promoCodeSupported = 'true') ";

	private static final String NOT_PRIOR_SEGMENT_PATH = "and (segmentCode = '%d' or (segmentCode = '%s' and group/groupParams/promoCodeSupported = 'true')) ";

	private static final String PENSION_GROUP_PARAMS_PATH = "and group/groupParams/pensionRate = 'true' ";

	private static final String MIN_BALANCE_PATH = "and minBalance='%s' ";

	private static final String SUBTYPE_PATH = "and id='%d' ";


	public static final String CALC_RATE_ERROR = "calculateRateError"; //ключ для текста ошибки при определении процентной ставки

	private static final String PRODUCT_TYPE = "./data/productId";

	protected static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final DepositProductService depositProductService = new DepositProductService();
	private static final DepositEntityService depositEntityService = new DepositEntityService();
	private static final ClientPromoCodeService clientPromoCodeService = new ClientPromoCodeService();

	private static final String ERR_MSG_COMMON = "На выбранных условиях невозможно открыть вклад.";
	private static final String ERR_MSG_BALANCE = "Сумма вклада не соответствует условиям вклада.";
	private static final String ERR_MSG_PERIOD = "Введённый срок вклада не соответствует условиям вклада.";
	private static final String DCF_TAR_CACHE_NAME = "dcf-tar-cache";
	private static final String DCF_TAR_CACHE_KEY_DELIMITER = "^";

	/**
	 * Получить из описания депозитного продукта процентную ставку, номер подвида, размер минимального доп.
	 * взноса для переданных условий. Для вкладов без первоначального взноса возвращаем только подвид.
	 * @param openingDate дата открытия
	 * @param period срок
	 * @param currencyCode буквенный код валюты
	 * @param minBalance нижняя граница диапазона суммы, для которой открывается данный подвид (либо сумма
	 * зачисления для вкладов без нснижаемого остатка). Если null, считаем, что вклад без первоначального взноса
	 * @param pension льготная ставка или нет
	 * @param description описание вклада
	 * @param depositTariffPlanCode тарифный план вклада
	 * @param clientTariffPlanCode тарифный план клиента
	 * @return карта < поле AccountOpeningClaim, значение>. Если процентная ставка не найдена, то в карту помещается пара < CALC_RATE_ERROR, текст ошибки для отображения пользователю >,
	 * поэтому проверка ненайденной ставки сводится к проверке наличия ключа CALC_RATE_ERROR.
	 */
	public static Map<String, String> calculateRate(Calendar openingDate, DateSpan period, String currencyCode, BigDecimal minBalance, boolean pension, Element description, String depositTariffPlanCode, String clientTariffPlanCode,String depositGroup, String segmentCode, Boolean isPromoDeposit) throws Exception
	{
		List<DepositSubTypeDescription> deposits = new ArrayList<DepositSubTypeDescription>();
		Set<BigDecimal> minBalances = new HashSet<BigDecimal>();

		fillDeposits(description, openingDate, currencyCode, pension, deposits, minBalances, depositTariffPlanCode, clientTariffPlanCode, depositGroup, segmentCode, isPromoDeposit);

		return prepareRates(minBalance, openingDate, period, minBalances, deposits);
	}

	/**
	 * Используя сущности, построенные из справочников ЦАС НСИ, получить  процентную ставку, номер подвида, размер минимального доп.
	 * взноса для переданных условий. Для вкладов без первоначального взноса возвращаем только подвид.
	 * @param openingDate дата открытия
	 * @param period срок
	 * @param currencyCode буквенный код валюты
	 * @param minBalance нижняя граница диапазона суммы, для которой открывается данный подвид (либо сумма
	 * зачисления для вкладов без нснижаемого остатка). Если null, считаем, что вклад без первоначального взноса
	 * @param pension льготная ставка или нет
	 * @param depositType вид вкладного продукта
	 * @param depositTariffPlanCode тарифный план вклада
	 * @param clientTariffPlanCode тарифный план клиента
	 * @param depositGroup код группы вклада
	 * @param segmentCode код сегмента
	 * @return карта < поле AccountOpeningClaim, значение>. Если процентная ставка не найдена, то в карту помещается пара < CALC_RATE_ERROR, текст ошибки для отображения пользователю >,
	 * поэтому проверка ненайденной ставки сводится к проверке наличия ключа CALC_RATE_ERROR.
	 * @throws Exception
	 */
	public static Map<String, String> calculateEntityRate(Calendar openingDate, DateSpan period, String currencyCode, BigDecimal minBalance, boolean pension, Long depositType, String depositTariffPlanCode, String clientTariffPlanCode,String depositGroup, String segmentCode) throws Exception
	{
		List<DepositSubTypeDescription> deposits = new ArrayList<DepositSubTypeDescription>();
		Set<BigDecimal> minBalances = new HashSet<BigDecimal>();

		fillDepositsEntities(depositType, currencyCode, pension, deposits, minBalances, depositTariffPlanCode, depositGroup, segmentCode);

		return prepareRates(minBalance, openingDate, period, minBalances, deposits);
	}

	private static Map<String, String> prepareRates(BigDecimal minBalance, Calendar openingDate, DateSpan period, Set<BigDecimal> minBalances, List<DepositSubTypeDescription> deposits)
	{
		Map<String, String> result = new HashMap<String, String>();

		// Для вкладов без первоначального взноса нужно вернуть только подвид.
		if (minBalance == null)
		{
			if (CollectionUtils.isEmpty(deposits))
			{
				result.put(CALC_RATE_ERROR, ERR_MSG_COMMON);
				return result;
			}

			// Для таких вкладов подвид определяется только валютой, поэтому берем первый, они уже
			// отфильтрованы по валюте.
			result.put(AccountOpeningClaim.DEPOSIT_SUB_TYPE, deposits.get(0).subType.toString());
			return result;
		}

		// Если нет доступных диапазонов сумм
		if (minBalances.isEmpty())
		{
			result.put(CALC_RATE_ERROR, ERR_MSG_COMMON);
			return result;
		}

		// Среди доступных диапазонов сумм (нижних границ этих диапазонов) выбираем подходящий
		BigDecimal minBal = BigDecimal.ZERO;
		for (BigDecimal balance : minBalances)
		{
			if (balance.compareTo(minBalance) <= 0 && balance.compareTo(minBal) > 0)
				minBal = balance;
		}

		// Если не найдена подходящая сумма для расчета процентов
		if (minBal.compareTo(BigDecimal.ZERO) == 0)
		{
			result.put(CALC_RATE_ERROR, ERR_MSG_BALANCE);
			return result;
		}

		// Ищем подходящие подвиды с учетом нижней границы диапазона сумм для открываемого подвида и срока
		// вклада в годах, месяцах, днях
		List<DepositSubTypeDescription> respondConditionsDeposits = new ArrayList<DepositSubTypeDescription>();
		for (DepositSubTypeDescription deposit : deposits)
		{
			if (deposit.minBalance.compareTo(minBal) == 0)
			{
				Long periodInDays = DateHelper.daysDiffByIncrease(openingDate, DateHelper.add(openingDate, period), true);
				// Если конечной даты срока не указано, то вклад открывается на конкретный срок, а не на период
				if (deposit.periodEnd == null)
				{
					if (DateHelper.daysDiffByIncrease(openingDate, DateHelper.add(openingDate, deposit.periodStart), true).compareTo(periodInDays) == 0)
						respondConditionsDeposits.add(deposit);
				}
				else
				{
					if (DateHelper.daysDiffByIncrease(openingDate, DateHelper.add(openingDate, deposit.periodStart), true) <= periodInDays &&
						DateHelper.daysDiffByIncrease(openingDate, DateHelper.add(openingDate, deposit.periodEnd), true) > periodInDays)
						respondConditionsDeposits.add(deposit);
				}
			}
		}

		if (respondConditionsDeposits.size() > 1)
		{
			// Если подходит несколько подвидов, смотрим на срок в днях
			Iterator<DepositSubTypeDescription> iter = respondConditionsDeposits.iterator();
			while (iter.hasNext())
			{
				Long periodDays = DateHelper.daysDiffByIncrease(openingDate, DateHelper.add(openingDate, period), true);
				DepositSubTypeDescription deposit = iter.next();
				if (deposit.daysStart > periodDays || (deposit.daysEnd < periodDays && deposit.periodEnd != null))
					iter.remove();
			}
		}

		if (respondConditionsDeposits.size() > 1)
		{
			// Если опять несколько подвидов, то нужно смотреть дату вступления в силу процентной ставки.
			// Выбираем самую последнюю.
			Collections.sort(respondConditionsDeposits, new Comparator<DepositSubTypeDescription>()
			{
				public int compare(DepositSubTypeDescription o1, DepositSubTypeDescription o2)
				{
					int result = isPriorSegment(o2.segmentCode).compareTo(isPriorSegment(o1.segmentCode));
					if (result != 0)
						return result;
					return o2.rateStart.compareTo(o1.rateStart);
				}
			});
		}

		// Если не нашли подходящих подвидов, возвращаем null
		if (CollectionUtils.isEmpty(respondConditionsDeposits))
		{
			result.put(CALC_RATE_ERROR, ERR_MSG_PERIOD);
			return result;
		}

		// Иначе берем первый из списка
		DepositSubTypeDescription depositDescription = respondConditionsDeposits.get(0);
		result.put(AccountOpeningClaim.INTEREST_RATE, depositDescription.rate);
		result.put(AccountOpeningClaim.DEPOSIT_SUB_TYPE, depositDescription.subType.toString());
		result.put(AccountOpeningClaim.MIN_ADDITIONAL_FEE, depositDescription.minAdditionalFee.toString());
		result.put(AccountOpeningClaim.MIN_DEPOSIT_BALANCE, minBal.toString());
		return result;
	}

	/**
	 * Является ли код сегмента "приоритетным", т.е. относится ли к промо-коду
	 * @param segmentCode - код сегмента
	 * @return
	 */
	public static Boolean isPriorSegment(String segmentCode)
	{
		if (StringHelper.isNotEmpty(segmentCode) && !StringHelper.equals(segmentCode, "0") && !StringHelper.equals(segmentCode, "1"))
			return true;
		return false;
	}

	/**
	 * Удаление из описания депозитного продукта определенных подвидов
	 * @param description корневой DOM-элемент описания
	 * @param subKinds список удаляемых подвидов
	 */
	public static void removeSubKinds(Element description, final List<Long> subKinds) throws Exception
	{
		if (CollectionUtils.isEmpty(subKinds))
			return;

		XmlHelper.foreach(description, ELEMENT_FOREACH_PATH, new ForeachElementAction()
		{

			public void execute(Element element) throws Exception
			{
				Long subKind = Long.parseLong(XmlHelper.getSimpleElementValue(element, "id"));
				if (subKinds.contains(subKind))
					element.getParentNode().removeChild(element);
			}
		});
	}

	/**
	 * Обновление наименования депозитного продукта на основе названий загруженных подвидов
	 * @param description корневой DOM-элемент описания депозитного продукта
	 */
	public static void updateProductName(Element description) throws TransformerException
	{
		String subKindName = XmlHelper.getElementValueByPath(description, SUB_KIND_NAME_PATH);
		if (!StringHelper.isEmpty(subKindName))
		{
			Element name = XmlHelper.selectSingleNode(description, NAME_XPATH);
			name.setTextContent(subKindName);
		}
	}

	/**
	 * Получить название вклада по разрешенным для открытия онлайн подвидам
	 * @param product описание депозитного продукта
	 * @return название
	 */
	public static String getAvailableSubKindsName(DepositProduct product)
	{
		try
		{
			Element description = XmlHelper.parse(product.getDescription()).getDocumentElement();
			String subKindName = XmlHelper.getElementValueByPath(description, SUB_KIND_NAME_PATH);
			if (StringHelper.isNotEmpty(subKindName))
				return subKindName;

			return product.getName();
		}
		catch (Exception e)
		{
			LOG.error("Ошибка при получении названия вклада", e);
			return "";
		}
	}

	/**
	 * Возможно ли открытие вклада в переданном подразделении
	 * @param depositProduct описание вклада
	 * @param department подразделение
	 * @return true - можно открывать
	 */
	public static boolean isAvailableOnline(DepositProduct depositProduct, Department department) throws BusinessException
	{
		if (!depositProduct.isAvailableOnline())
			return false;

		List<String> allowedTerbanks = depositProduct.getAllowedDepartments();

		if (CollectionUtils.isEmpty(allowedTerbanks))
			return false;

		return allowedTerbanks.contains(department.getRegion());
	}

	/**
	 * Доступна ли капитализация по вкладу
	 * @param depositProductId
	 * @return boolean true - доступна
	 * @throws BusinessException
	 */

	public static boolean isAvailableCapitalization(String depositProductId) throws BusinessException
	{
		DepositProduct depositProduct = depositProductService.findById(Long.valueOf(depositProductId));
		return depositProduct != null ? depositProduct.isCapitalization() : false;
	}

	/**
	 * Доступна ли капитализация (по типу вклада)
	 * @param depositProductType - тип вклада
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isAvailableCapitalizationByType(String depositProductType) throws BusinessException
	{
		return depositProductService.getCapitalization(Long.valueOf(depositProductType));
	}

	/**
	 * Доступна ли капитализация по вкладу
	 * @param depositProductId номер вида депозитного продукта
	 * @return boolean true - доступна
	 * @throws BusinessException
	 */

	public static boolean isAvailableCapitalizationByProductId(String depositProductId) throws BusinessException
	{
		if (StringHelper.isEmpty(depositProductId))
			return false;

		DepositProductShortCut depositProduct = depositProductService.findShortByProductId(Long.valueOf(depositProductId));
		return depositProduct != null ? depositProduct.isCapitalization() : false;
	}

	/**
	 * Определим путь выборки элементов в зависимости от параметров
	 * @param openingDate - дата открытия вклада
	 * @param depositTariffPlanCode - код тарифного плана открываемого вклада
	 * @param clientTariffPlanCode - код тарифного плана клиента (для проверки зависимости)
	 * @param knownTariff - если true, то в тариф эелемента должен совпадать с переданным кодом ТП
	 * @return строка, содержащая путь для выборки элементов
	 */
	private static String getTariffElementPath(Calendar openingDate, String depositTariffPlanCode, String clientTariffPlanCode, boolean knownTariff)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String openDate = dateFormat.format(openingDate.getTime());

		String depositTariff = StringHelper.getZeroIfEmptyOrNull(depositTariffPlanCode);
		String clientTariff = StringHelper.getZeroIfEmptyOrNull(clientTariffPlanCode);

		String tariffPath = null;
		if (knownTariff)
			tariffPath =  String.format(KNOWN_TARIFF_PATH, depositTariff);
		else
			tariffPath =  UNKNOWN_TARIFF_PATH;

		String tariffDependenceElementPath = String.format(TARIFF_DEPENDENCE_PATH, clientTariff, openDate);
		return tariffPath + tariffDependenceElementPath;
	}

	/**
	 * Параметры выборки ставок с учетом признака пенсионера и кода сегмента (промо-ставки)
	 * @param pension
	 * @param groupCode
	 * @param segmentCode
	 * @param promoDeposit
	 * @return
	 */
	public static String getPensionAndSegmentElementPath(boolean pension, String groupCode, String segmentCode, Boolean promoDeposit)
	{
		// Если обрабатывается промо-вклад, то признак пенсионера не учитывается. Выбираются только ставки с промо-сегментами
		if (promoDeposit)
		{
			return StringHelper.isEmpty(segmentCode) ? "" : String.format(PRIOR_SEGMENT_PATH, segmentCode);
		}
		else
		{
			String elementPath = String.format(NOT_PRIOR_SEGMENT_PATH, BooleanUtils.toInteger(pension), StringHelper.getEmptyIfNull(segmentCode));
			if (!StringHelper.equalsNullIgnore(groupCode, "-22") && pension)
				elementPath = elementPath + PENSION_GROUP_PARAMS_PATH;
			return elementPath;
		}
	}

	public static String getElementPath(Element description, Calendar openingDate, String currencyCode, boolean pension, String depositTariffPlanCode, String clientTariffPlanCode, String depositGroup, String segmentCode, Boolean isPromoDeposit, String minBalance, Long sybType) throws Exception
	{
		String allowedDepositGroup = getAllowedDepositGroup(description, depositGroup);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String openDate = dateFormat.format(openingDate.getTime());

		String basicElementPath = String.format(BASIC_PATH, openDate, currencyCode);
		String groupElementPath = StringHelper.isEmpty(allowedDepositGroup) ? "" : String.format(GROUP_PATH, allowedDepositGroup);
		String minBalanceElementPath = StringHelper.isEmpty(minBalance) ? "" : String.format(MIN_BALANCE_PATH, minBalance);
		String idElementPath = sybType == null ? "" : String.format(SUBTYPE_PATH, sybType);

		String fullPath = basicElementPath + groupElementPath + minBalanceElementPath + idElementPath;

		// Проверяем на совпадение с ТП и пенсионером
		String elementTariffPath = null;
		String pensionElementPath = getPensionAndSegmentElementPath(pension, allowedDepositGroup, segmentCode, isPromoDeposit);
		String notPensionElementPath = getPensionAndSegmentElementPath(false, allowedDepositGroup, segmentCode, isPromoDeposit);

		//Если ТП установлен, пробуем найти элементы, соответствующие этому тарифу и признаку пенсионера
		if (!TariffPlanHelper.isUnknownTariffPlan(depositTariffPlanCode))
		{
			String checkTariffAndPensionPath = null;
			String tariffPath = getTariffElementPath(openingDate, depositTariffPlanCode, clientTariffPlanCode, true);

			checkTariffAndPensionPath = fullPath + tariffPath + pensionElementPath + "]";
			NodeList tariffElements = XmlHelper.selectNodeList(description, checkTariffAndPensionPath);
			//Если элементы не найдены, ищем все элементы, для которых ТП отсутствует
			if (tariffElements == null || tariffElements.getLength() == 0)
				tariffPath = getTariffElementPath(openingDate, depositTariffPlanCode, clientTariffPlanCode, false);

			if (pension)
			{
				checkTariffAndPensionPath = fullPath + tariffPath + pensionElementPath + "]";
				NodeList pensionTariffElements = XmlHelper.selectNodeList(description, checkTariffAndPensionPath);
				if (pensionTariffElements == null || pensionTariffElements.getLength() == 0)
					elementTariffPath = tariffPath + notPensionElementPath;
				else
					elementTariffPath = tariffPath + pensionElementPath;
			}
			else
				elementTariffPath = tariffPath + pensionElementPath;
		}
		else
		{
			String unknownTariffPath = getTariffElementPath(openingDate, depositTariffPlanCode, clientTariffPlanCode, false);
			String checkTariffAndPensionPath = fullPath + unknownTariffPath + pensionElementPath + "]";

			NodeList tariffElements = XmlHelper.selectNodeList(description, checkTariffAndPensionPath);
			if (tariffElements == null || tariffElements.getLength() == 0)
				elementTariffPath = unknownTariffPath + notPensionElementPath;
			else
				elementTariffPath = unknownTariffPath + pensionElementPath;
		}

		fullPath = fullPath + elementTariffPath + "]";


		return fullPath;
	}

	private static void fillDeposits(Element description, Calendar openingDate, String currencyCode, boolean pension, final List<DepositSubTypeDescription> deposits, final Set<BigDecimal> minBalances, String depositTariffPlanCode, String clientTariffPlanCode, String depositGroup, String segmentCode, Boolean isPromoDeposit) throws Exception
	{
		final SimpleDateFormat rateStartDateFormat = new SimpleDateFormat("yyyy.MM.dd");

		String fullPath = getElementPath(description, openingDate, currencyCode, pension, depositTariffPlanCode, clientTariffPlanCode, depositGroup, segmentCode, isPromoDeposit, null, null);
		XmlHelper.foreach(description, fullPath, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				DepositSubTypeDescription deposit = new DepositSubTypeDescription();
				deposit.subType = Long.parseLong(XmlHelper.getSimpleElementValue(element, "id"));

				String periodString = XmlHelper.getSimpleElementValue(element, "period");
				if (!StringHelper.isEmpty(periodString))
				{
					String[] period = XmlHelper.getSimpleElementValue(element, "period").split("U");
					String[] periodStart = period[0].split("-");
					deposit.periodStart = new DateSpan(Integer.parseInt(periodStart[0]),
							Integer.parseInt(periodStart[1]), Integer.parseInt(periodStart[2]));
					if (period.length > 1)
					{
						String[] periodEnd = period[1].split("-");
						deposit.periodEnd = new DateSpan(Integer.parseInt(periodEnd[0]),
								Integer.parseInt(periodEnd[1]), Integer.parseInt(periodEnd[2]));
					}
				}

				String daysPeriodString = XmlHelper.getSimpleElementValue(element, "periodInDays");
				if (!StringHelper.isEmpty(daysPeriodString))
				{
					String[] daysPeriod = XmlHelper.getSimpleElementValue(element, "periodInDays").split("-");
					if (daysPeriod.length > 0)
					{
						if (!StringHelper.isEmpty(daysPeriod[0]))
							deposit.daysStart = Long.parseLong(daysPeriod[0]);

						if (daysPeriod.length > 1)
						{
							deposit.daysEnd = Long.parseLong(daysPeriod[1]);
						}
					}
				}

				deposit.segmentCode = XmlHelper.getSimpleElementValue(element, "segmentCode");
				deposit.minAdditionalFee = new BigDecimal(XmlHelper.getSimpleElementValue(element, "minAdditionalFee"));
				deposit.minBalance = new BigDecimal(XmlHelper.getSimpleElementValue(element, "minBalance"));
				deposit.rate = XmlHelper.getSimpleElementValue(element, "interestRate");
				deposit.rateStart = DateHelper.toCalendar(
						rateStartDateFormat.parse(XmlHelper.getSimpleElementValue(element, "interestDateBegin")));

				deposits.add(deposit);
				minBalances.add(deposit.minBalance);
			}
		});
	}

	private static class DepositSubTypeDescription
	{
		private Long subType;
		private DateSpan periodStart;
		private DateSpan periodEnd;
		private Long daysStart;
		private Long daysEnd;
		private BigDecimal minAdditionalFee;
		private BigDecimal minBalance;
		private String rate;
		private Calendar rateStart;
		private String segmentCode;
	}

	/**
	 * Получить список возможных значений неснижаемых остатков для изменения условий вклада
	 * @param link линк на счет
	 * @return список пар <неснижаемый остаток, процентная ставка>, либо null, если нет подходящих
	 */
	public static List<Pair<BigDecimal, String>> getMinBalancesToChange(AccountLink link)
	{
		if (link == null)
			return null;

		Account account = link.getAccount();
		DepositConfig depositConfig = ConfigFactory.getConfig(DepositConfig.class);
		if (account.getAccountState() != AccountState.OPENED && account.getAccountState() != AccountState.LOST_PASSBOOK && account.getAccountState() != AccountState.ARRESTED || account.getOpenDate() == null || (depositConfig.isNewAlgorithmForProlongationDate() && account.getProlongationDate() == null))
		{
			return null;
		}

		Long depositType = account.getKind();
		Long depositSubType = account.getSubKind();
		Currency currency = account.getCurrency();
		String currencyCode = "643".equals(currency.getNumber()) ? "810" : currency.getNumber();
		Money minBalance = account.getMinimumBalance();
		Money balance = account.getBalance();
		Long segment = account.getClientKind() == null ? 0L : account.getClientKind();

		try
		{
			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				if (!depositProductService.isWithMinimumBalance(depositType))
					return null;
			}
			else
			{
				DepositProductShortCut product = depositProductService.findShortByProductId(depositType);
				if (product == null || !product.isWithMinimumBalance())
					return null;
			}

			List<DepositsDCFTAR> dcftars = getDepositsDCFTARByTypeAndSubType(depositType, depositSubType);
			if (CollectionUtils.isEmpty(dcftars))
				return null;

			// Собираем мапу ставок по неснижаемым остаткам с учетом сегмента, валюты, даты открытия, текущего баланса и текущего неснижаемого остатка
			Map<BigDecimal, List<DepositsDCFTAR>> allRates = new HashMap<BigDecimal, List<DepositsDCFTAR>>();
			Calendar openDate = getActualRatesDateBegin(link);
			for (DepositsDCFTAR dcftar : dcftars)
			{
				if (dcftar.getSegment().equals(segment) && dcftar.getCurrencyCode().equals(currencyCode) &&
						dcftar.getSumBegin().compareTo(minBalance.getDecimal()) > 0 && dcftar.getSumBegin().compareTo(balance.getDecimal()) < 0 &&
						!(dcftar.getDateBegin().after(openDate)))
				{
					if (allRates.containsKey(dcftar.getSumBegin()))
					{
						List<DepositsDCFTAR> values = allRates.get(dcftar.getSumBegin());
						values.add(dcftar);
					}
					else
					{
						List<DepositsDCFTAR> values = new ArrayList<DepositsDCFTAR>();
						values.add(dcftar);
						allRates.put(dcftar.getSumBegin(), values);
					}
				}
			}

			if (MapUtils.isEmpty(allRates))
				return null;

			List<Pair<BigDecimal, String>> result = new ArrayList<Pair<BigDecimal, String>>();
			// для каждого значения мин. баланса находим самую "свежую" ставку (актуальную на момент открытия вклада)
			for (BigDecimal minBalanceValue : allRates.keySet())
			{
				List<DepositsDCFTAR> rates = allRates.get(minBalanceValue);

				Collections.sort(rates, new Comparator<DepositsDCFTAR>()
				{
					public int compare(DepositsDCFTAR o1, DepositsDCFTAR o2)
					{
						return o2.getDateBegin().compareTo(o1.getDateBegin());
					}
				});
				result.add(new Pair<BigDecimal, String>(minBalanceValue, rates.get(0).getBaseRate().toString()));
			}

			if (CollectionUtils.isNotEmpty(result))
			{
				Collections.sort(result, new Comparator<Pair<BigDecimal, String>>()
				{
					public int compare(Pair<BigDecimal, String> o1, Pair<BigDecimal, String> o2)
					{
						return o1.getFirst().compareTo(o2.getFirst());
					}
				});
				return result;
			}
		}
		catch (Exception e)
		{
			LOG.error(e);
		}

		return null;
	}

	public static List<DepositsDCFTAR> getDepositsDCFTARByTypeAndSubType(Long type, Long subType) throws BusinessException
	{
		Cache dcfTarCache = CacheProvider.getCache(DCF_TAR_CACHE_NAME);
		net.sf.ehcache.Element cacheValue = null;
		String cacheKey = new StringBuilder().append(type).append(DCF_TAR_CACHE_KEY_DELIMITER).append(subType).toString();
		if (dcfTarCache != null)
			cacheValue = dcfTarCache.get(cacheKey);

		if (cacheValue != null)
			//noinspection unchecked
			return (List<DepositsDCFTAR>) cacheValue.getObjectValue();

		List<DepositsDCFTAR> dcftars = depositProductService.findDepositsDCFTARByTypeAndSubType(type, subType);
		if (CollectionUtils.isNotEmpty(dcftars))
			dcfTarCache.put(new net.sf.ehcache.Element(cacheKey, dcftars));

		return dcftars;
	}

	/**
	 * Получить дату, для которой будут выбираться актуальные ставки : дату открытия или пролонгации.
	 * Дата пролонгации рассчитывается следующим образом: из даты закрытия вычесть срок хранения в днях.
	 * Если дата пролонгации больше даты открытия, используем ее. Иначе считаем, что пролонгации не было и используем дату открытия.
	 * @param link - линк вклада
	 * @return - дата, для которой будут выбираться ставки вклада
	 */
	private static Calendar getActualRatesDateBegin(AccountLink link)
	{
		Calendar currentDate = Calendar.getInstance();
		Calendar accOpenDate = link.getAccount().getOpenDate();
		Calendar accountOpenDate = accOpenDate != null ? accOpenDate : currentDate;

		Account accInfo = link.getAccount();
		if (accInfo == null)
			return accountOpenDate;

		Calendar prolongationDate = null;
		DateSpan period =  accInfo.getPeriod();
		DepositConfig depositConfig = ConfigFactory.getConfig(DepositConfig.class);
		if (depositConfig.isNewAlgorithmForProlongationDate())
		{
			prolongationDate = accInfo.getProlongationDate();
		}
		else if (accInfo.getCloseDate() != null && period != null)
		{
			prolongationDate = DateHelper.getPreviousNDay(accInfo.getCloseDate(), period.getDays());
		}

		if (DateHelper.nullSafeCompare(accountOpenDate, prolongationDate) != 1)
			return prolongationDate;
		else
			return accountOpenDate;
	}

	/**
	 * Если группа депозитного продукта не указана явно, возможно, можно использовать одну из групп из настроек (совместимость с mApi < 8)
	 * @param description - описание вклада
	 * @param group - переданная группа
	 * @return - группа, которая будет использована для дальнейшего поиска
	 * @throws Exception
	 */
	private static String getAllowedDepositGroup(Element description, String group) throws Exception
	{
		if (StringHelper.isEmpty(group))
		{
			if (MobileApiUtil.isMobileApiLT(MobileAPIVersions.V8_00))
			{
				String productType = XmlHelper.getElementValueByPath(description, PRODUCT_TYPE);

				MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
				Map<String, String> allowedCodes = mobileApiConfig.getOldDepositCodesList();

				return StringHelper.getNullIfEmpty(allowedCodes.get(productType));
			}
			else if (ApplicationUtil.isATMApi())
			{
				String productType = XmlHelper.getElementValueByPath(description, PRODUCT_TYPE);

				AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
				Map<String, String> allowedCodes = atmApiConfig.getOldDepositCodesList();

				return StringHelper.getNullIfEmpty(allowedCodes.get(productType));                                                 
			}
		}
		return group;
	}

	private static void fillDepositsEntities(Long depositType, String currencyCode, boolean pension, final List<DepositSubTypeDescription> deposits, final Set<BigDecimal> minBalances, String depositTariffPlanCode, String depositGroup, String segmentCode) throws Exception
	{
		List<PromoCodeDeposit> actualClientPromoCodes = new ArrayList<PromoCodeDeposit>();
		ClientPromoCode clientPromoCode = clientPromoCodeService.getPromoCodeBySegment(segmentCode);
		if (clientPromoCode != null)
			actualClientPromoCodes.add(clientPromoCode.getPromoCodeDeposit());

		DepositProductEntity depositProductEntity = depositEntityService.getDepositProductEntity(depositType, Long.valueOf(depositGroup), actualClientPromoCodes, TariffPlanHelper.getDictionaryTariffCodes(), pension, Long.valueOf(depositTariffPlanCode), false, null);

		List<DepositProductEntitySubType> entitySubTypes = depositProductService.getEntitySubTypesInfo(depositType, Long.valueOf(depositGroup));
		Map<Long, BigDecimal> subTypesMinAdditionalFeeValues = new HashMap<Long, BigDecimal>();
		for (DepositProductEntitySubType subType : entitySubTypes)
		{
			subTypesMinAdditionalFeeValues.put(subType.getId(), subType.getMinAdditionalFee());
		}

		Map<BigDecimal, Map<String, List<DepositProductRate>>> currencyRates = depositProductEntity.getDepositRates().get(currencyCode);
		for (BigDecimal balance : currencyRates.keySet())
		{
			Map<String, List<DepositProductRate>> periodRates = currencyRates.get(balance);
			for (List<DepositProductRate> rates : periodRates.values())
			{
				for (DepositProductRate rate : rates)
				{
					DepositSubTypeDescription deposit = new DepositSubTypeDescription();
					deposit.subType = rate.getDepositSubType();
					if (StringHelper.isNotEmpty(rate.getPeriod()))
					{
						String[] period = rate.getPeriod().split("U");
						String[] periodStart = period[0].split("-");
						deposit.periodStart = new DateSpan(Integer.parseInt(periodStart[0]),
								Integer.parseInt(periodStart[1]), Integer.parseInt(periodStart[2]));
						if (period.length > 1)
						{
							String[] periodEnd = period[1].split("-");
							deposit.periodEnd = new DateSpan(Integer.parseInt(periodEnd[0]),
									Integer.parseInt(periodEnd[1]), Integer.parseInt(periodEnd[2]));
						}
					}

					deposit.daysStart = rate.getPeriodBegin();
					deposit.daysEnd = rate.getPeriodEnd();

					deposit.segmentCode = String.valueOf(rate.getSegment());
					deposit.minAdditionalFee = subTypesMinAdditionalFeeValues.get(rate.getDepositSubType());
					deposit.minBalance = rate.getSumBegin();
					deposit.rate = String.valueOf(rate.getBaseRate());
					deposit.rateStart = rate.getDateBegin();

					deposits.add(deposit);
					minBalances.add(deposit.minBalance);
				}
			}
		}
	}
}
