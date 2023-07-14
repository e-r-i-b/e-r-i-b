package com.rssl.phizic.operations.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.finances.CardOperationClaim;
import com.rssl.phizic.business.finances.CardOperationClaimService;
import com.rssl.phizic.business.finances.CardOperationClaimStatus;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author rydvanskiy
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class FinancesOperationBase extends OperationBase
{
	//Период (кол-во дней) до которого включительно график строится за каждый день, сверх этого кол-ва
	//график группирует данные по "group_day_count" дней
	protected static final int EVERY_DAY_LIMIT = 41;
	protected static final int GROUP_DAY_COUNT = 7;

	protected static final CardOperationCategoryService cardOperationCategoryService = new CardOperationCategoryService();

	private static final CardOperationClaimService cardOperationClaimService = new CardOperationClaimService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ProfileService profileService = new ProfileService();

	private Person person;

	/**
	 * Логин
	 */
	private Login login;

	private Calendar lastModified;

	private List<CardLink> personCardLinks;

	private List<String> personCards;

	private List<String> personOwnCards;

	// признак наличия неудачных запросов
	private boolean hasFailedClaims;
	private FinancesStatus financesStatus;
	private Calendar thresholdUpdate;
	/////////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции
	 */
	@Transactional
	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		person = personData.getPerson();

		//Логин
		login = person.getLogin();

		Profile profile = personData.getProfile();

		// проверяем статус сервиса анализ личных финансов
		if (!profile.isShowPersonalFinance())
			financesStatus = FinancesStatus.notConnected;

		// Карты: все и личные
		personCardLinks = personData.getCards();
		personCards = new ArrayList<String>(personCardLinks.size());
		personOwnCards = new ArrayList<String>(personCardLinks.size());
		for (CardLink cardLink: personCardLinks)
		{
			personCards.add(cardLink.getNumber());
			if (cardLink.isMain() || cardLink.getCard().getAdditionalCardType() != AdditionalCardType.OTHERTOCLIENT)
				personOwnCards.add(cardLink.getNumber());
		}
		// если клиент еще не подключен то заявок у него нет
		if ( financesStatus == FinancesStatus.notConnected )
				return;

		if (personCards.isEmpty())
		{
			financesStatus = FinancesStatus.noProducts;
			return;
		}
		// порог обновления
		thresholdUpdate = Calendar.getInstance();
		thresholdUpdate.add(Calendar.MINUTE, -1 * Integer.valueOf(ConfigFactory.getConfig(IPSConfig.class).getProperty("claim.update.frequency")) );
		collectClaimsInfo();
		calculateStatistics(profile);
	}

	/**
	 * Инициализация операции
	 */
	public void simpleInitialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		login = personData.getLogin();
		personCardLinks = personData.getCards();
	}

	/**
	 * @return логин текущего пользователя
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * @return текущий клиент
	 */
	public Person getPerson()
	{
		return person;
	}

	/**
	 * @return дата последнего обновления заявок
	 */
	public Calendar getLastModified()
	{
		return lastModified;
	}

	/**
	 * @return все карты клиента в виде списка кард-линков
	 */
	protected List<CardLink> getPersonCardLinks()
	{
		return Collections.unmodifiableList(personCardLinks);
	}

	/**
	 * @return все карты клиента в виде списка номеров карт
	 */
	public List<String> getPersonCards()
	{
		return Collections.unmodifiableList(personCards);
	}

	/**
	 * @return карты клиента, владельцем которых он является, в виде списка номеров карт
	 */
	protected List<String> getPersonOwnCards()
	{
		return Collections.unmodifiableList(personOwnCards);
	}

	/**
	 * Метод для актуализации заявок и знаний по ним
	 */
	private void collectClaimsInfo() throws BusinessException
	{
		// получаем список заявок
		List<CardOperationClaim> claims = cardOperationClaimService.findByLoginAndCard(login.getId(), personCards);
		// заявки которые необходимо добавить или обновить
		List<CardOperationClaim> claimsToAddOrUpdate = new ArrayList<CardOperationClaim>(personCardLinks.size());
		// список номеров карт клиента которые будут вычеркиваться
		List<String> newCards = new ArrayList<String>(personCards);
		//обновляем заявки
		for (CardOperationClaim claim: claims)
		{
			// определяем дату последнего обновления операций по картам
			if ( claim.getExecutingEndTime() != null )
			{
				if (lastModified == null)
					lastModified = claim.getExecutingEndTime();
				else
				{
					if (claim.getExecutingEndTime().compareTo(lastModified) > 0)
						lastModified = claim.getExecutingEndTime();
				}
			}

			// проверяем является ли заявка проваленой
			if (claim.getStatus() == CardOperationClaimStatus.FAILED)
				hasFailedClaims = true;

			//проверяем нужно ли обновить данные по заявке
			if (claim.getStatus() == CardOperationClaimStatus.EXECUTED || claim.getStatus() == CardOperationClaimStatus.FAILED )
				if (claim.getExecutingEndTime().compareTo(thresholdUpdate) < 0 )
				{
					claim.setStatus(CardOperationClaimStatus.WAITING);
					claim.setWaitingStartTime(Calendar.getInstance());
					claim.setExecutionAttemptNum(0);
					claimsToAddOrUpdate.add(claim);
				}
			// вычеркиваем карту из списка карт для которых необходимо создать заявку
			newCards.remove(claim.getCardNumber());
		}

		// новые заявки
		for (String cardNumber: newCards)
		{
			CardOperationClaim newClaim = new CardOperationClaim();
			//Новая заявка
			newClaim.setOwnerId(login.getId());
			newClaim.setStatus(CardOperationClaimStatus.WAITING);
			newClaim.setCardNumber(cardNumber);
			newClaim.setWaitingStartTime(Calendar.getInstance());
			claimsToAddOrUpdate.add(newClaim);
			// добавляем в общий список заявок
			claims.add(newClaim);
		}
		// обновляем и сохраняем заявки
		cardOperationClaimService.addOrUpdateList(claimsToAddOrUpdate);

		// получив информацию о дате последнего обновления вносим изменения в статус услуги
		if (lastModified != null)
			financesStatus = FinancesStatus.allOk;
		else
			financesStatus = FinancesStatus.waitingClaims;
	}

	/**
	 * Метод для получения информации о состоянии доступности сервиса
	 * "Структура личных финансов" пользователю
	 * @return информация о состоянии доступности сервиса "Структура личных финансов" пользователю
	 */
	public FinancesStatus getStatus()
	{
		return financesStatus;
	}

	/**
	 * Имеются ли заваленые заявки
	 * @return имеются ли проваленные заявки
	 */
	public boolean hasFailedClaims()
	{
		return hasFailedClaims;
	}

	/**
	 * Формирует список из номеров карт
	 * @param cards карты
	 * @return список из номеров карт
	 */
	protected List<String> getCardNumbersList(List<CardLink> cards)
	{
		return FinanceHelper.getCardNumbersList(cards);
	}

	/**
	 * Возвращает список карт клиента, владельцем которых он является
	 * @param cards все карты клиента
	 * @return список карт клиента, владельцем которых он является
	 */
	protected List<CardLink> getPersonOwnCards(List<CardLink> cards)
	{
		List<CardLink> personOwnCardList = new ArrayList<CardLink>(cards.size());
		for (CardLink cardLink: cards)
			if (cardLink.isMain() || cardLink.getCard().getAdditionalCardType() != AdditionalCardType.OTHERTOCLIENT)
				personOwnCardList.add(cardLink);
		return personOwnCardList;
	}

	/**
	 * Получить список карт пользователя по фильтру.
	 * @param cardFilter фильтр
	 * @param cards список карт клиента
	 * @return список карт пользователя.
	 * @throws BusinessException
	 */
	protected List<CardLink> getFilterCardLinks(CardFilter cardFilter, List<CardLink> cards) throws BusinessException
	{
		try
		{
			List<CardLink> result = new ArrayList<CardLink>(cards);
			CollectionUtils.filter(result, cardFilter);
			return result;
		}
		catch (Exception ignore)
		{
			return null;
		}
	}

	/**
	 * Возвращает подразделение текущего клиента
	 * @return подразделение текущего клиента
	 * @throws BusinessException
	 */
	public Department getPersonDepartment() throws BusinessException
	{
		return departmentService.findById(getPerson().getDepartmentId());
	}

	private void calculateStatistics(Profile profile) throws BusinessException
	{
		int maxPauseInUsingFinances = ConfigFactory.getConfig(IPSConfig.class).getMaxPauseInUsingFinances();

		if (profile.getLastUsingFinancesDate() == null)
		{
			profile.setUsingFinancesCount(1);
			profile.setUsingFinancesEveryThreeDaysCount(1);
		}
		else
		{
			long diff = DateHelper.daysDiff(profile.getLastUsingFinancesDate(), DateHelper.getCurrentDate());
			if (diff <= 0)
				return;

			if(diff > maxPauseInUsingFinances)
			{
				profile.setUsingFinancesCount(1);
				profile.setUsingFinancesEveryThreeDaysCount(1);
			}
			else if (diff > 3)
			{
				profile.setUsingFinancesCount(profile.getUsingFinancesCount() + 1);
			}
			else if (diff >= 1)
			{
				profile.setUsingFinancesCount(profile.getUsingFinancesCount() + 1);
				profile.setUsingFinancesEveryThreeDaysCount(profile.getUsingFinancesEveryThreeDaysCount() + 1);
			}
		}

		profile.setLastUsingFinancesDate(DateHelper.getCurrentDate());
		profile.setLastUpdateCardOperationClaimsDate(DateHelper.getCurrentDate());
		profileService.update(profile);
	}
}
