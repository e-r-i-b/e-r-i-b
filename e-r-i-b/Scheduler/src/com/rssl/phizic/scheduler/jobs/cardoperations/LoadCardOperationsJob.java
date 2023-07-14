package com.rssl.phizic.scheduler.jobs.cardoperations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.finances.CardOperationClaim;
import com.rssl.phizic.business.finances.CardOperationClaimService;
import com.rssl.phizic.business.finances.CardOperationClaimStatus;
import com.rssl.phizic.business.finances.processed.ProcessedCardOperationClaimLoginService;
import com.rssl.phizic.business.ips.IPSCardOperationClaimImpl;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.gate.ips.IPSExecutiveService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mihaylov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */
public class LoadCardOperationsJob extends BaseJob implements InterruptableJob
{
	private static final CardOperationClaimService claimService = new CardOperationClaimService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final AdapterService adapterService = new AdapterService();
	private static final PersonService personService = new PersonService();
	private static final ProcessedCardOperationClaimLoginService processedCardOperationClaimLoginService = new ProcessedCardOperationClaimLoginService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);

	private static final AtomicInteger counter = new AtomicInteger(0);

	private static boolean initialized = false;
	private volatile boolean isInterrupt = false;


	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
		if(config.getLoadCardOperationsJobStateful())
			return;

		int jobNumber = counter.incrementAndGet();
		try
		{
			if(jobNumber >config.getMaxLoadCardOperationsJobsCount())
				return;
			initialize();
			startJob();
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			counter.decrementAndGet();
		}
	}

	private synchronized void initialize()
	{
		if (initialized)
			return;

		runStartupServices();

		initialized = true;
	}

	private void runStartupServices()
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	private void startJob()
	{
		try
		{
			IPSConfig config = ConfigFactory.getConfig(IPSConfig.class);
			processedCardOperationClaimLoginService.removeOld(config.getClaimExecutionMaxTime());
			while (!isInterrupt)
			{
				//0. Проверяем установлен ли технологический перерыв
				Adapter ipsAdapter =  adapterService.getAdapterByUUID(ExternalSystemHelper.getIpsSystemCode());
				if(ipsAdapter != null && !ExternalSystemHelper.isActive(ipsAdapter))
					break;

				// 1. Получаем очередную порццию заявок, которые нужно выполнить
				List<CardOperationClaim> claims = getClaims();
				if (claims.isEmpty() || isInterrupt)
					// заявок на обработку больше нет или нужно остановится
					break;

				log.info("Найдены заявки на получение карточных операций => начинаю процедуру загрузки карточных операций");

				processClaims(claims);
			}
		}
		catch(GateException ge)
		{
			log.error(ge);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Получение заявок для выполнения
	 * @return список заявок, нуждающихся в выполнении
	 */
	private List<CardOperationClaim> getClaims()
	{
		List<CardOperationClaim> claims = Collections.emptyList();
		try
		{
			claims	= claimService.getClaimsToLoadOperations();
		}
		catch (BusinessException e)
		{
			log.error("Сбой на получении списка зависших заявок", e);
		}

		return claims;
	}

	/**
	 * Обработать заявки
	 * @param claims - список заявок для обработки.
	 * @throws BusinessException
	 */
	private void processClaims(List<CardOperationClaim> claims) throws BusinessException
	{
		List<CardOperationClaim> filteredClaims = filterProcessingClaims(claims);
		if(CollectionUtils.isEmpty(filteredClaims))
			return;
		try
		{
			// 2. Обновляем статус заявок на "выполняются"
			List<CardOperationClaim> processedClaims = markClaimsProcessing(filteredClaims);
			if(CollectionUtils.isEmpty(processedClaims))
				return;
			log.info("В обработку приняты заявки: " + StringUtils.join(processedClaims, ", "));

			// 3. Конвертируем заявки в шлюзовое представление
			List<IPSCardOperationClaim> gateClaims = convertClaimsToGate(processedClaims);
			if (gateClaims.isEmpty())
				return;

			// 4. Передаём заявки в шлюз ИПС
			log.info("Передаю заявки в шлюз: " + StringUtils.join(gateClaims, ", "));
			executeGateClaims(gateClaims);
		}
		finally
		{
			removeFromProcessing(filteredClaims);
		}
	}

	/**
	 * Отвильтровать заявки на выполнение.
	 * Проверяем не находится ли логин владельца заявки в обрабатываемых. Если нет, то добавляем его в список обрабатываемых.
	 * @param claims - список заявок для фильтрации
	 * @return список заявок прошедших фильтрацию
	 * @throws BusinessException
	 */
	private List<CardOperationClaim> filterProcessingClaims(List<CardOperationClaim> claims) throws BusinessException
	{
		Map<Long, List<CardOperationClaim>> loginClaimsMap = convertToLoginClaimsMap(claims);
		List<CardOperationClaim> resultClaims = new ArrayList<CardOperationClaim>();
		for(Map.Entry<Long,List<CardOperationClaim>> entry: loginClaimsMap.entrySet())
		{
			if(addLoginToProcessed(entry.getKey()))
				resultClaims.addAll(entry.getValue());
		}
		return resultClaims;
	}

	/**
	 * Пометить логин, как обрабатываемый
	 * @param loginId - идентификатор логина клиента
	 * @return  true - логин пометили как обрабатываемый
	 *          false - логин не удалось пометить как обрабатываемый, видимо кто-то другой его обрабатывает
	 */
	private boolean addLoginToProcessed(Long loginId)
	{
		try
		{
			processedCardOperationClaimLoginService.add(loginId);
			return true;
		}
		catch (Exception e)
		{
			log.info("Не удалось добавить логин владельца заявки в список обрабатываемых заявок",e);
			return false;
		}
	}

	/**
	 * Пометить заявки обрабатываемыми
	 * @param claims - список заявок, которые необходимо обновить
	 * @return список заявок, которые удалось обновить
	 * @throws BusinessException
	 */
	private List<CardOperationClaim> markClaimsProcessing(List<CardOperationClaim> claims) throws BusinessException
	{
		List<CardOperationClaim> resultClaims = new ArrayList<CardOperationClaim>();
		for(final CardOperationClaim claim: claims)
		{
			if(claimService.markClaimsProcessing(claim))
				resultClaims.add(claim);
		}
		return resultClaims;
	}

	/**
	 * Удалить логины клиента из обрабатываемых
	 * @param claims - заявки, логины владельцев которых надо удалить из базы
	 * @throws BusinessException
	 */
	private void removeFromProcessing(List<CardOperationClaim> claims) throws BusinessException
	{
		Map<Long, List<CardOperationClaim>> loginClaimsMap = convertToLoginClaimsMap(claims);
		for(Map.Entry<Long,List<CardOperationClaim>> entry: loginClaimsMap.entrySet())
			processedCardOperationClaimLoginService.remove(entry.getKey());
	}

	private Map<Long, List<CardOperationClaim>> convertToLoginClaimsMap(List<CardOperationClaim> claims)
	{
		Map<Long, List<CardOperationClaim>> loginClaimsMap = new HashMap<Long, List<CardOperationClaim>>();
		for(CardOperationClaim claim: claims)
		{
			List<CardOperationClaim> loginClaimList = loginClaimsMap.get(claim.getOwnerId());
			if(loginClaimList == null)
			{
				loginClaimList = new ArrayList<CardOperationClaim>();
				loginClaimsMap.put(claim.getOwnerId(),loginClaimList);
			}
			loginClaimList.add(claim);
		}
		return loginClaimsMap;
	}

	private List<IPSCardOperationClaim> convertClaimsToGate(List<CardOperationClaim> claims)
	{
		IPSConfig config = ConfigFactory.getConfig(IPSConfig.class);
		// считаем от начала дня
		final Calendar today = DateHelper.getCurrentDate();
		final Calendar defaultStartDate = DateHelper.addDays(today, -config.getCardOperationMaxTime());

		// 1. Получаем мап "номер -> карта"
		Map<String, Card> cardsByNumber = getClaimCards(claims);

		// 2. Конвертируем заявки в шлюзовое представление
		List<IPSCardOperationClaim> gateClaims = new ArrayList<IPSCardOperationClaim>(cardsByNumber.size());
		for (CardOperationClaim claim : claims)
		{
			ActivePerson person;
			try
			{
				person = personService.findByLoginId(claim.getOwnerId());
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				failClaim(claim, e.getMessage());
				continue;
			}

			Card card = cardsByNumber.get(claim.getCardNumber());
			if (card == null) {
				log.error("Не найдена карта для заявки " + claim);
				failClaim(claim, "Не найдена карта для заявки");
				continue;
			}

			Calendar startDate = claim.getLastOperationDate();
			if (startDate == null)
				startDate = defaultStartDate;

			try
			{
				IPSCardOperationClaimImpl gateClaim = new IPSCardOperationClaimImpl();
				gateClaim.setClient(person.asClient());
				gateClaim.setCard(card);
				gateClaim.setStartDate(startDate);

				gateClaims.add(gateClaim);
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				failClaim(claim, e.getMessage());
			}
		}
		return gateClaims;
	}

	private Map<String, Card> getClaimCards(Collection<CardOperationClaim> claims)
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		// 1. Получаем (внешние) идентификаторы карт
		Set<String> cardIdsSet = new HashSet<String>(claims.size());
		// мап "id_карты -> заявка" для логирования ошибок получения карт
		Map<String, CardOperationClaim> claimsByCardId = new HashMap<String, CardOperationClaim>(claims.size());
		for (CardOperationClaim claim : claims)
		{
			CardLink cardLink;
			try
			{
				cardLink = resourceService.findLinkByNumber(claim.getOwnerId(), ResourceType.CARD, claim.getCardNumber());
				if (cardLink == null) {
					failClaim(claim, "Кард-линк не найден");
					continue;
				}

			}
			catch (BusinessException e)
			{
				log.error("Сбой при получении кард-линка. " + claim, e);
				failClaim(claim, "Сбой при получении кард-линка");
				continue;
			}

			String cardId = cardLink.getExternalId();
			cardIdsSet.add(cardId);
			claimsByCardId.put(cardId, claim);
		}

		// 2. Получаем карты
		String[] cardIdsArray = cardIdsSet.toArray(new String[cardIdsSet.size()]);
		GroupResult<String, Card> cardsResult = bankrollService.getCard(cardIdsArray);

		// 3. Логируем проблемы получения карт
		Map<String, IKFLException> cardsExceptions = cardsResult.getExceptions();
		for (Map.Entry<String, IKFLException> exceptionEntry : cardsExceptions.entrySet())
		{
			String cardId = exceptionEntry.getKey();
			IKFLException exception = exceptionEntry.getValue();
			CardOperationClaim claim = claimsByCardId.get(cardId);
			log.error("Не удалось получить карту для " + claim, exception);
		}

		// 4. Составляем мап карт
		Collection<Card> cards = cardsResult.getResults().values();
		Map<String, Card> cardsByNumber = new HashMap<String, Card>(cards.size());
		for (Card card : cards)
			cardsByNumber.put(card.getNumber(), card);

		return cardsByNumber;
	}

	private void executeGateClaims(List<IPSCardOperationClaim> claims) throws BusinessException
	{
		IPSExecutiveService ipsService = GateSingleton.getFactory().service(IPSExecutiveService.class);
		try
		{
			ipsService.executeCardOperationClaims(claims);
		}
		catch (GateException e)
		{
			throw new BusinessException("Сбой на выполнении заявок", e);
		}
	}



	private void failClaim(CardOperationClaim claim, String error)
	{
		try
		{
			claim.setStatus(CardOperationClaimStatus.FAILED);
			claim.setLastError(error);
			claim.setExecutingEndTime(Calendar.getInstance());
			claimService.addOrUpdate(claim);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось забраковать заявку", e);
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}

}
