package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.country.CountryCode;
import com.rssl.phizic.business.dictionaries.country.CountryService;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCode;
import com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCodeService;
import com.rssl.phizic.business.finances.*;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRule;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleLogHelper;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.finances.FinancesConfig;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ips.IPSCardOperation;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Erkin
 * @ created 10.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Приёмник карточных операций
 * Имеет внутреннее состояние, т.е. не потокобезопасен
 */
@SuppressWarnings("MethodWithTooManyParameters")
class CardOperationReceiver
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final CardOperationClaimService claimService = new CardOperationClaimService();
	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final CountryService countryService = new CountryService();
	private static final CardOperationCategoryService operationCategoryService = new CardOperationCategoryService();
	private static final MerchantCategoryCodeService merchantCategoryCodeService = new MerchantCategoryCodeService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ALFRecategorizationRuleService recategorizationRuleService = new ALFRecategorizationRuleService();

	private final Currency nationalCurrency;
	private final boolean needLog;

	private final Map<String, CardOperationClaim> claims = new HashMap<String, CardOperationClaim>(); // Пул обрабатываемых заявок
	private final Set<CardOperationClaim> failedClaims = new HashSet<CardOperationClaim>(); // Операции по этим заявкам не удалось принять полностью

	private Map<ALFRecategorizationRule,Pair<String,Integer>> recategorizationStatistics = new HashMap<ALFRecategorizationRule,Pair<String,Integer>>();

	CardOperationReceiver(GateFactory gateFactory) throws GateException
	{
		CurrencyService currencyService = gateFactory.service(CurrencyService.class);
		nationalCurrency = currencyService.getNationalCurrency();
		ALFRecategorizationRuleLogConfig config = ConfigFactory.getConfig(ALFRecategorizationRuleLogConfig.class);
		needLog = config.isLoggingOn();
	}

	List<IPSCardOperationClaim> receive(GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result)
	{
		List<IPSCardOperationClaim> failedGateClaims = new LinkedList<IPSCardOperationClaim>();
		for (IPSCardOperationClaim gateClaim : result.getKeys())
		{
			try
			{
				// 1. Ищем заявку в бд
				CardOperationClaim claim = getDbClaim(gateClaim);

				// 2. Определяем, что делать с заявкой
				IKFLException exception = result.getException(gateClaim);
				List<IPSCardOperation> operations = result.getResult(gateClaim);

				// 2.A Пришла информация о том, что заявку не удаётся обработать
				if (exception != null)
					failClaim(claim, exception);

				// 2.B Пришли новые операции по заявке, находящейся в обработке
				else if (!operations.isEmpty())
				{
					saveClaimOperations(claim, operations);
					// если операции не удалось сохранить, сообщаем отправителю,
					// что больше операций по этой заявке присылать не надо
					if (failedClaims.contains(claim))
						failedGateClaims.add(gateClaim);
				}

				// 2.C Пришло уведомление о том, что по заявке получены все операции
				else finishClaim(claim);

			}
			catch (BusinessException e)
			{
				// С заявкой проблема
				log.error(e.getMessage(), e);
				failedGateClaims.add(gateClaim);
			}
		}

		// 3. Возвращаем заявки, по которым не удалось сохранить операции
		return failedGateClaims;
	}

	private CardOperationClaim getDbClaim(IPSCardOperationClaim claim) throws BusinessException
	{
		String claimKey = getClaimKey(claim);
		CardOperationClaim dbClaim = claims.get(claimKey);
		if (dbClaim == null)
		{
			Client client = claim.getClient();
			Card card = claim.getCard();
			List<CardOperationClaim> dbClaimList = claimService.findByLoginAndCard(client.getInternalOwnerId(), Collections.singletonList(card.getNumber()));
			if (CollectionUtils.isEmpty(dbClaimList))
				throw new BusinessException("В базе не найдена заявка " + claim);
			dbClaim = dbClaimList.iterator().next();
			claims.put(claimKey, dbClaim);
		}
		return dbClaim;
	}

	private String getClaimKey(IPSCardOperationClaim gateClaim)
	{
		return gateClaim.getClient().getInternalOwnerId() + "|" + gateClaim.getCard().getNumber();
	}

	/**
	 * Сохранение очередной порции операций по заявке
	 * @param claim - заявка
	 * @param gateOperations - операции по заявке
	 */
	private void saveClaimOperations(CardOperationClaim claim, List<IPSCardOperation> gateOperations)
	{
		// 1. Проверяем, что заявка находится в состоянии "обрабатывается"
		if (claim.getStatus() != CardOperationClaimStatus.PROCESSING) {
			// noinspection ThrowableInstanceNeverThrown
			failClaim(claim, new BusinessException("Пришли карточные операции по заявке, которая не обрабатывается. Заявка " + claim));
			return;
		}

		try
		{
			// 2. Конвертируем операции из гейтового представления в бизнесовое
			// На выходе может быть меньшее число операций в случае проблем конвертации (тогда заявка забракована)
			List<Pair<CardOperation, CardOperationExtendedFields>> operations = createCardOperations(claim, gateOperations);

			// 3. Сохраняем операции в бд
			if (!operations.isEmpty())
				cardOperationService.addOperations(operations);

			if (!recategorizationStatistics.isEmpty())
				saveLogs();

			// 4. Обновляем в заявке дату последней операции и дату последнего выполнения заявки (статус не трогаем)
			claim.setProcessingStartTime(Calendar.getInstance());
			if (!operations.isEmpty())
			{
				CardOperation lastOperation = operations.get(operations.size() - 1).getFirst();
				claim.setLastOperationDate(lastOperation.getDate());
			}
			claimService.addOrUpdate(claim);
		}
		catch (Exception e)
		{
			failClaim(claim, e);
		}
	}

	/**
	 * Создаёт по списку карточных операций в гейтовом представлении операции в представлении бизнесовом.
	 * Список на выходе может быть меньше входного списка в случае, если при конвертации какой-либо операции случился сбой
	 * (не найден курс валюты и т.п.).
	 * В этом случае возвращаются операции, сконвертированные до сбоя, а завка бракуется
	 * @param claim - заявка, по которой получены гейтовые операции
	 * @param gateOperations - операции в гейтовом представлении
	 * @return список операций в бизнесовом представлении
	 */
	@SuppressWarnings("OverlyLongMethod")
	private List<Pair<CardOperation, CardOperationExtendedFields>> createCardOperations(CardOperationClaim claim, List<IPSCardOperation> gateOperations) throws BusinessException
	{
		if (gateOperations.isEmpty())
			return Collections.emptyList();

		// 1. Получаем МСС-коды и категории операций
		Map<Long, MerchantCategoryCode> mccCodes = getOperationMCCCodes(gateOperations);
		MerchantCategoryCode defaultMccCode = getDefaultMCCCode();
		List<Long> cardToCardMCCCodes = ConfigFactory.getConfig(IPSConfig.class).getCardToCardMCCCodesList();
		CardOperationCategory internalIncomeCategory = operationCategoryService.findInternalCategory(true);
		CardOperationCategory internalOutcomeCategory = operationCategoryService.findInternalCategory(false);
		CardOperationCategory defaultIncomeCategory = operationCategoryService.getDefaultCategory(true);
		CardOperationCategory defaultOutcomeCategory = operationCategoryService.getDefaultCategory(false);

		List<ALFRecategorizationRule> recategorizationRules = recategorizationRuleService.getAllClientRules(claim.getOwnerId());

		// 2. Получаем конвертер валют
		MoneyExchanger exchanger = getMoneyExchanger(gateOperations);

		// 3. Конвертируем операции
		Calendar minDate = gateOperations.get(0).getOperationDate();
		Calendar maxDate = gateOperations.get(gateOperations.size() - 1).getOperationDate();
		CardOperationResolver cardOperationResolver = new CardOperationResolver(claim.getOwnerId());
		cardOperationResolver.initialize(minDate, maxDate);

		List<Pair<CardOperation, CardOperationExtendedFields>> operations = new LinkedList<Pair<CardOperation, CardOperationExtendedFields>>();
		for (IPSCardOperation gateOperation : gateOperations)
		{
			//TODO: Временное решение. Необходимо операции "из будущего" отсеивать при получении из ИПС.
			if (Calendar.getInstance().before(gateOperation.getOperationDate()))
				continue;

			if (cardOperationResolver.hasEqualOperation(gateOperation))
				continue;

			Card card = gateOperation.getOperationCard();
			Calendar operationDate = gateOperation.getOperationDate();
			Money accountCredit = gateOperation.getAccountCreditSum();
			Money accountDebit = gateOperation.getAccountDebitSum();
			Money operationCredit = gateOperation.getCreditSum();
			Money operationDebit = gateOperation.getDebitSum();
			String deviceNumber = CardOperationHelper.getDeviceNumber(gateOperation);
			String description = CardOperationHelper.getOperationDescription(gateOperation);

			// 3.1 Определяем MCC-код операции
			MerchantCategoryCode mcc = mccCodes.get(gateOperation.getMccCode());
			if (mcc == null)
				mcc = defaultMccCode;

			// 3.2 Определяем сумму в валюте карты
			// Сумма может быть передана либо как кредит, либо как дебит => нужно выбрать
			Money operationCardMoney = selectNotEmptyMoney(accountCredit, accountDebit);
			if (!operationCardMoney.getCurrency().compare(card.getCurrency())) {
				// Если валюта карты не совпадает с валютой счёта, это плохо
				// заявку бракуем, сконвертированные операции сохраняем
				// noinspection ThrowableInstanceNeverThrown
				failClaim(claim, new BusinessException("Валюта счёта операции не совпадает с валютой карты " + gateOperation));
				break;
			}

			// 3.3 Определяем сумму в национальной валюте
			Money operationNationalMoney;
			try
			{
				// Сумма может быть передана либо как кредит, либо как дебит => нужно выбрать
				Money operationMoney = selectNotEmptyMoney(operationCredit, operationDebit);
				// Постараемся не конвертировать в рубли,
				// а взять из суммы в валюте операции, либо суммы в валюте счёта операции
				if (operationCardMoney.getCurrency().compare(nationalCurrency))
					operationNationalMoney = operationCardMoney;
				else if(operationMoney.getCurrency().compare(nationalCurrency))
					operationNationalMoney = operationMoney;
				else operationNationalMoney = exchanger.convert(operationCardMoney, nationalCurrency, operationDate);
			}
			catch (MoneyExchangeException e)
			{
				// заявку бракуем, сконвертированные операции сохраняем
				// noinspection ThrowableInstanceNeverThrown
				failClaim(claim, new BusinessException("Не удалось получить сумму операции в национальной валюте по операции " + gateOperation, e));
				break;
			}

			//3.4. Ищем категорию операции: расходную или доходную
			CardOperationCategory category = null;
			// Проверяем есть ли для операции правило перекатегоризации
			ALFRecategorizationRule recategorizationRule = getCategoryByRule(recategorizationRules, gateOperation);

			//3.4.1. Если категория не определена, проверяем не является ли операция потенциальной для выделения в категорию «Переводы между своими картами»
			// (это все операции с mcc-кодами, по которым возможны операции карта-карта)
			if (recategorizationRule == null && internalIncomeCategory != null && internalOutcomeCategory != null && cardToCardMCCCodes.contains(mcc.getCode()))
			{
				// Ищем парную операцию по условиям:
				//1) Совпадение по полям «id устройства» и «сумма операции» (с противоположным знаком)
				//2) Расхождение по полю «время проведения операции» в пределах установленного интервала.
				CardOperation internalOperation = cardOperationResolver.findInternalOperation(gateOperation, operationCardMoney, mcc, internalIncomeCategory, internalOutcomeCategory);
				if (internalOperation != null)
				{
					// Есть однозначная пара. Необходимо обновить операцию: перенести в категорию «Переводы между своими картами».
					if(operationCardMoney.getDecimal().compareTo(BigDecimal.ZERO) < 0)
					{
						category = internalOutcomeCategory;
						internalOperation.setCategory(internalIncomeCategory);
					}
					else
					{
						category = internalIncomeCategory;
						internalOperation.setCategory(internalOutcomeCategory);
					}
					operations.add(new Pair<CardOperation, CardOperationExtendedFields>(internalOperation, null));
				}
			}

			//3.4.2. Если категория не определена, то пытаемся определить категорию по МСС-коду.
			CardOperationCategory categoryByMCC = null;
			if(operationCardMoney.getDecimal().compareTo(BigDecimal.ZERO) < 0)
			{
				categoryByMCC = mcc.getOutcomeOperationCategory();
			}
			else
			{
				categoryByMCC = mcc.getIncomeOperationCategory();
			}

			//3.4.3. Если нет категории, то тащим дефолтную для данного типа
			if (categoryByMCC == null)
			{
				if (operationCardMoney.getDecimal().compareTo(BigDecimal.ZERO) >= 0)
				{
					categoryByMCC = defaultIncomeCategory;
				}
				else
				{
					categoryByMCC = defaultOutcomeCategory;
				}
			}

			//3.4.4. И уж если нет дефолтной, то фейлим заявку
			if (category == null && categoryByMCC == null) {
				// заявку бракуем, сконвертированные операции сохраняем
				// noinspection ThrowableInstanceNeverThrown
				failClaim(claim, new BusinessException("Не найдена категория для операции " + gateOperation));
				break;
			}

			CardOperation preloadedOperation = null;
			//Если включена загрузка операций из Б_Д и МСС операции в списке загружаемых - ищем в базе предзагруженную операцию
			if (ConfigFactory.getConfig(FinancesConfig.class).getOperationLinkingEnabled() && ConfigFactory.getConfig(IPSConfig.class).getLinkingOperationsMccCodeList().contains(mcc.getCode()))
			{
				preloadedOperation = cardOperationResolver.findPreloadedOperation(gateOperation, operationNationalMoney);
			}

			// Сохраняем правило перекатегоризации. Категорию для операции берем из правила.
			if (recategorizationRule != null)
			{
				addRecategorizationStatistic(recategorizationRule, categoryByMCC);
				category = recategorizationRule.getNewCategory();
			}

			// 3.5 Создаём и добавляем в список бизнесовую операцию
			CardOperation operation = new CardOperation();
			operation.setExternalId(gateOperation.getDocumentNumber());
			operation.setDate(operationDate);
			operation.setLoadDate(Calendar.getInstance());
			operation.setOperationType(OperationType.BY_CARD);
			operation.setCardNumber(card.getNumber());
			operation.setOriginalDescription(description);
			operation.setDescription(description);
			operation.setCardAmount(operationCardMoney.getDecimal());
			operation.setNationalAmount(operationNationalMoney.getDecimal());
			operation.setCash(gateOperation.isCash());
			operation.setDeviceNumber(deviceNumber);
			operation.setOwnerId(claim.getOwnerId());
			operation.setCategory(category != null ? category : categoryByMCC);
			operation.setOriginalCategoryName(categoryByMCC != null ? categoryByMCC.getName() : category.getName());
			operation.setMccCode(mcc.getCode());
			if (card.getNumber().charAt(0)=='4')
			{
				if (StringHelper.isNotEmpty(description) && description.length()>2)
				{
					String iso2 = description.substring(description.length()-2, description.length());
					CountryCode country = countryService.getCountryByISO2(iso2);
					operation.setOriginalCountry(country);
				}
			}
			else
			{
				if (StringHelper.isNotEmpty(description) && description.length()>3)
				{
					String iso3 = description.substring(description.length()-3,description.length());
					CountryCode country = countryService.getCountryByISO3(iso3);
					operation.setOriginalCountry(country);
				}
			}
			operation.setClientCountry(operation.getOriginalCountry());

			if (preloadedOperation != null)
			{
				preloadedOperation.updateFrom(operation);
				if (recategorizationRule != null)
				{
					operation.setCategory(recategorizationRule.getNewCategory());
				}
				operations.add(new Pair<CardOperation, CardOperationExtendedFields>(preloadedOperation, null));
			}
			else
			{
				CardOperationExtendedFields cardOperationExtendedFields = new CardOperationExtendedFields();
				cardOperationExtendedFields.setAuthCode(gateOperation.getAuthCode());
				cardOperationExtendedFields.setDate(gateOperation.getOperationDate());
				operations.add(new Pair<CardOperation, CardOperationExtendedFields>(operation, cardOperationExtendedFields));
			}
		}
		return operations;
	}

	/**
	 * Возвращает категорию, если для операции есть правило перекатегоризации
	 * @param rules - список правил для клиента
	 * @param operation - обрабатываемая операция
	 * @return категория
	 */
	private ALFRecategorizationRule getCategoryByRule(List<ALFRecategorizationRule> rules,  IPSCardOperation operation)
	{
		for (ALFRecategorizationRule rule : rules)
		{
			String description = CardOperationHelper.getOperationDescription(operation);
			if (rule.getDescription().equals(description) && rule.getMccCode().equals(operation.getMccCode()))
				return rule;
		}
		return null;
	}

	private void addRecategorizationStatistic(ALFRecategorizationRule recategorizationRule, CardOperationCategory category)
	{
		if (!needLog)
			return;

		Pair<String, Integer> pair = recategorizationStatistics.get(recategorizationRule);
		if (pair == null)
		{
			Pair<String, Integer> count = new Pair<String, Integer>(category.getName(), 1);
			recategorizationStatistics.put(recategorizationRule, count);
		}
		else
		{
			pair.setSecond(pair.getSecond()+1);
		}
	}

	// Выбирает из 2х денег первую, которая не-null и не-0, либо первую, которая не-null
	private Money selectNotEmptyMoney(Money money1, Money money2)
	{
		if (money1 != null && money2 != null)
			return !money1.isZero() ? money1 : money2;

		if (money1 != null)
			return money1;

		return money2;
	}

	private MoneyExchanger getMoneyExchanger(Collection<IPSCardOperation> operations) throws BusinessException
	{
		Department currencyRateOffice = getCurrencyRateOffice();

		MoneyExchangeBuilder builder = new MoneyExchangeBuilder();
		for (IPSCardOperation operation : operations)
		{
			Calendar operationDate = operation.getOperationDate();
			Currency operationCurrency = operation.getCreditSum().getCurrency();
			Currency accountCurrency = operation.getAccountCreditSum().getCurrency();

			if (!operationCurrency.equals(nationalCurrency) && !accountCurrency.equals(nationalCurrency))
				builder.addExchange(accountCurrency, nationalCurrency, operationDate);
		}
		return builder.build(currencyRateOffice, CurrencyRateType.CB);
	}

	private Department getCurrencyRateOffice() throws BusinessException
	{
		IPSConfig config = ConfigFactory.getConfig(IPSConfig.class);
		String officeRegion = config.getCbCurrencyOfficeRegion();
		Department rateOffice = departmentService.getDepartmentTBByTBNumber(officeRegion);
		if (rateOffice == null)
			throw new BusinessException("Не найден офис с ЦБ-курсами (" + officeRegion + ")");
		return rateOffice;
	}

	private Map<Long, MerchantCategoryCode> getOperationMCCCodes(List<IPSCardOperation> gateOperations) throws BusinessException
	{
		Set<Long> mccCodes = new HashSet<Long>();
		for (IPSCardOperation gateOperation : gateOperations)
			mccCodes.add(gateOperation.getMccCode());

		List<MerchantCategoryCode> mccList = merchantCategoryCodeService.findMCCByCode(mccCodes);

		Map<Long, MerchantCategoryCode> mccMap = new HashMap<Long, MerchantCategoryCode>(mccList.size());
		for (MerchantCategoryCode mcc : mccList)
			mccMap.put(mcc.getCode(), mcc);
		return mccMap;
	}

	/**
	 * @return дефолтный MCCCODE для тех кодов, которые не удалось найти в справочнике
	 */
	private MerchantCategoryCode getDefaultMCCCode() throws BusinessException
	{
		MerchantCategoryCode code = new MerchantCategoryCode();
		code.setIncomeOperationCategory(operationCategoryService.getDefaultCategory(true));
		code.setOutcomeOperationCategory(operationCategoryService.getDefaultCategory(false));
		return code;
	}

	///////////////////////////////////////////////////////////////////////////
	// Отбраковка заявки

	private void failClaim(CardOperationClaim claim, Exception exception)
	{
		failedClaims.add(claim);
		try
		{
			log.error("Заявка выполнена с ошибками " + claim, exception);
			claim.setStatus(CardOperationClaimStatus.FAILED);
			claim.setLastError(exception.getMessage());
			claim.setExecutingEndTime(Calendar.getInstance());
			claimService.addOrUpdate(claim);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось сохранить заявку", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// Завершение выполнения заявки

	private void finishClaim(CardOperationClaim claim)
	{
		try
		{
			log.info("Заявка успешно выполнена " + claim);
			claim.setStatus(CardOperationClaimStatus.EXECUTED);
			claim.setExecutingEndTime(Calendar.getInstance());
			claim.setLastError(null);
			claimService.addOrUpdate(claim);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось сохранить заявку", e);
		}
	}

	/**
	 * Переводит заявки в статус TIMEOUT
	 * @param claims - список заявок
	 */
	public void setTimeoutStatusClaims(List<IPSCardOperationClaim> claims)
	{
		for(IPSCardOperationClaim gateClaim : claims)
		{
			try
			{
				CardOperationClaim claim = getDbClaim(gateClaim);
				setTimeoutStatusClaim(claim);
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	private void setTimeoutStatusClaim(CardOperationClaim claim)
	{
		try
		{
			log.error("Заявка переведа в статус TIMEOUT: " + claim);
			claim.setStatus(CardOperationClaimStatus.TIMEOUT);
			claim.setExecutingEndTime(Calendar.getInstance());
			claimService.addOrUpdate(claim);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось сохранить заявку", e);
		}
	}

	private void saveLogs()
	{
		for (Map.Entry<ALFRecategorizationRule, Pair<String, Integer>> rule : recategorizationStatistics.entrySet())
		{
			ALFRecategorizationRuleLogHelper.writeApplyEntryToLog(rule.getKey(), rule.getValue().getFirst(), rule.getValue().getSecond());
		}
	}
}
