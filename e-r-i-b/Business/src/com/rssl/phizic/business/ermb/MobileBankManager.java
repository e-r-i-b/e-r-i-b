package com.rssl.phizic.business.ermb;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.ermb.card.PrimaryCardService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationShortcut;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

import java.util.*;

/**
 * Класс для получения информации о мобильном банке клиента.
 * Для ЕРМБ-клиентов получает информацию из ЕРМБ.
 * Для МБК-клиентов - из МБК.
 * Пока все методы статические. После перевода всех клиентов в ЕРМБ развилка ЕРМБ/МБК будет удалена.
 * @author Dorzhinov
 * @ created 19.08.13
 * @ $Author$
 * @ $Revision$
 */
//todo CHG059738 Удалить все переключатели ЕРМБ/МБК и удалить все ветки для МБК
public class MobileBankManager
{
	private static final PersonService PERSON_SERVICE = new PersonService();

	private static final MobileBankBusinessService MOBILE_BANK_BUSINESS_SERVICE = new MobileBankBusinessService();

	/**
	 * Определитель телефонов-получателей «веерной» СМС по логину
	 * - для клиентов МБК используются MobileBankUtils.getMobilebankPhonesByInfoCards либо getMobilebankPhonesByAllInfoCards
	 * - для клиентов ЕРМБ используются все телефоны в профиле
	 * @param login    логин клиента
	 * @param alternative    использовать ли альтернативный вариант получения регистраций (только для МБК)
	 * @param checkPhonesInERMB  проверять ли телефоны в ЕРМБ
	 * @return коллекция телефонов-получателей «веерной» СМС
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static Collection<String> getInfoPhones(Login login, Boolean alternative, Boolean checkPhonesInERMB) throws BusinessException
	{
		Collection<String> phones = null;
		ErmbProfile ermbProfile = checkPhonesInERMB ? ErmbHelper.getErmbProfileByLogin(login) : null;

		if (ermbProfile != null && ermbProfile.isServiceStatus())
		{
			phones = ermbProfile.getPhoneNumbers();
		}else
		{
			if (alternative == null)
				phones = MobileBankUtils.getMobilebankPhonesByAllInfoCards(login);
			else
				phones = MobileBankUtils.getMobilebankPhonesByInfoCards(login, alternative);
		}

		if (CollectionUtils.isEmpty(phones))
		{
			Person person = PERSON_SERVICE.findByLogin(login);
			if (person == null)
				throw new NotFoundException("Не найдена учётка клиента для логина " + login.getId());

			phones = findErmbPhonesInAnyTB(person);
		}

		return phones;
	}

	/**
	 * Определитель телефонов-получателей «веерной» СМС по персоне
	 * - для клиентов МБК используются MobileBankUtils.getMobilePhones либо getMobilePhonesForNotification
	 * - для клиентов ЕРМБ используются все телефоны в профиле
	 * @param person    клиент
	 * @param notification    оповещение о входе (только для МБК)
	 * @return коллекция телефонов-получателей «веерной» СМС
	 */
	public static Collection<String> getInfoPhones(ActivePerson person, Boolean notification) throws BusinessException
	{
		Collection<String> phones = null;

		ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByPerson(person);
		if (ermbProfile != null && ermbProfile.isServiceStatus())
			phones = ermbProfile.getPhoneNumbers();

		else
		{
			if (BooleanUtils.isFalse(notification))
				phones = MobileBankUtils.getMobilePhones(person);
			else
				phones = MobileBankUtils.getMobilePhonesForNotification(person);
		}

		if (CollectionUtils.isEmpty(phones))
			phones = findErmbPhonesInAnyTB(person);

		return phones;
	}

	/**
	 * Определитель телефонов-получателей «веерной» СМС для персоны из PersonContext-а
	 * - для клиентов МБК используются MobileBankUtils.getPhonesByAllInfoCardsForCurrentUser
	 * - для клиентов ЕРМБ используются все телефоны в профиле
	 * @return коллекция телефонов-получателей «веерной» СМС
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static Collection<String> getInfoPhones() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			throw new IllegalStateException("Не найдена сессия клиента");

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		Collection<String> phones = null;

		ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByPerson(person);
		if (ermbProfile != null && ermbProfile.isServiceStatus())
			phones = ermbProfile.getPhoneNumbers();

		else
			phones = MobileBankUtils.getPhonesByAllInfoCardsForCurrentUser();

		if (CollectionUtils.isEmpty(phones))
			phones = findErmbPhonesInAnyTB(person);

		return phones;
	}

	private static Collection<String> findErmbPhonesInAnyTB(Person person) throws BusinessException
	{
		Set<PersonDocument> personDocuments = person.getPersonDocuments();
		//в цса нет клиента без документов
		if (CollectionUtils.isEmpty(personDocuments))
			return Collections.emptyList();
		Set<String> passports = new HashSet<String>(personDocuments.size());
		for (PersonDocument document : personDocuments)
		{
			String series = StringHelper.getEmptyIfNull(document.getDocumentSeries());
			String number = StringHelper.getEmptyIfNull(document.getDocumentNumber());
			passports.add(series + number);
		}

		try
		{
			return CSABackRequestHelper.findErmbPhones(person.getSurName(), person.getFirstName(), person.getPatrName(), passports, person.getBirthDay());
		}
		catch (BackLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Определитель телефона-получателя «платёжной» СМС
	 * - для клиентов МБК используется MobileBankUtils.getCardPhones
	 * - для клиентов ЕРМБ используется основной телефон в профиле
	 * @param cardNumber    номер карты
	 * @param alternative    использовать ли альтернативный вариант получения регистраций (только для МБК)
	 * @return коллекция телефонов-получателей «платёжной» СМС
	 */
	public static Collection<String> getMainPhones(String cardNumber, boolean alternative)
	{
		List<ErmbProfile> ermbProfiles = ErmbHelper.getErmbProfilesByCardNumber(cardNumber);
		if (!CollectionUtils.isEmpty(ermbProfiles))
		{
			Collection<String> mainPhones = new ArrayList<String>(ermbProfiles.size());
			for (ErmbProfile ermbProfile : ermbProfiles)
			{
				if (ermbProfile.isServiceStatus())
					mainPhones.add(ermbProfile.getMainPhoneNumber());
			}
			return mainPhones;
		}
		else
			return MobileBankUtils.getCardPhones(cardNumber, alternative);
	}

	/**
	 * Определитель телефона-получателя «платёжной» СМС
	 * - для клиентов МБК используется MobileBankUtils.getMobilePhone
	 * - для клиентов ЕРМБ используется основной телефон в профиле
	 * @return телефон-получатель «платёжной» СМС
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static String getMainPhoneForCurrentUser() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return null;

		String phone = null;
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByPerson(person);
		if (ermbProfile != null && ermbProfile.isServiceStatus())
			phone = ermbProfile.getMainPhoneNumber();

		else phone = MobileBankUtils.getMobilePhoneForCurrentUser();

		if (StringHelper.isEmpty(phone))
		{
			Collection<String> phones = findErmbPhonesInAnyTB(person);
			if (!CollectionUtils.isEmpty(phones))
				phone = phones.iterator().next();
		}

		return phone;
	}

	/**
	 * Определитель принадлежности телефона «текущему» клиенту
	 *  - телефон в анкете клиента
	 *  - в профиле ЕРМБ текущего блока
	 *  - в МБК MobileBankUtils.getMobilebankPhonesByInfoCards
	 *  - в ЦСА без учета ТБ (ЕРМБ телефоны по всем блокам)
	 * @param number    номер телефона. ВАЖНО! Номер может прийти в маскированном виде
	 * @param searcher    объект, реализующий логику обработки "своего" телефона
	 * @return
	 */
	public static <T> T checkOurPhone(String number, OurPhoneSearcher<T> searcher) throws BusinessException
	{
		if (StringHelper.isEmpty(number))
			throw new IllegalArgumentException("Не указан телефон");

		if (!PersonContext.isAvailable())
			return searcher.returnOnFail();

		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		// A. Телефоны ЕРМБ-профиля в текущем блоке
		ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByPerson(person);
		if (ermbProfile != null && ermbProfile.isServiceStatus())
		{
			Set<String> ermbProfilePhones = ermbProfile.getPhoneNumbers();
			if (!CollectionUtils.isEmpty(ermbProfilePhones))
			{
				String ermbPhone = searcher.selectOurPhone(number, ermbProfilePhones);
				if (ermbPhone != null)
					return searcher.returnIfOur(ermbPhone);
			}

			return searcher.returnOnFail();
		}
		else
		{
			// B. Телефоны МБК
			Collection<String> phonesMBK = MobileBankUtils.getMobilebankPhonesByInfoCards(person.getLogin());
			if (!CollectionUtils.isEmpty(phonesMBK))
			{
				String phoneMBK = searcher.selectOurPhone(number, phonesMBK);
				if (phoneMBK != null)
					return searcher.returnIfOur(phoneMBK);

				return searcher.returnOnFail();
			}

			// C. Телефон из анкеты клиента в текущем блоке
			String personPhone = person.getMobilePhone();
			if (StringHelper.isNotEmpty(personPhone))
			{
				if (searcher.isEquals(number, personPhone))
					return searcher.returnIfOur(personPhone);
				return searcher.returnOnFail();
			}

			// D. Телефоны в ЦСА без учёта ТБ
			Collection<String> ermbPhonesOfAnyTB = findErmbPhonesInAnyTB(person);
			if (!CollectionUtils.isEmpty(ermbPhonesOfAnyTB))
			{
				String ermbPhone = searcher.selectOurPhone(number, ermbPhonesOfAnyTB);
				if (ermbPhone != null)
					return searcher.returnIfOur(ermbPhone);

				return searcher.returnOnFail();
			}
		}

		return searcher.returnOnFail();
	}

	/**
	 * Проверка подключения к Мобильному Банку
	 * - для клиентов МБК использует GetRegistration
	 * - для клиентов ЕРМБ проверяет профиль
	 * @param cardNumber    номер карты
	 * @return true - у клиента есть МБ
	 */
	public static boolean hasMB(String cardNumber)
	{
		return hasMBConnection(cardNumber, false);
	}

	/**
	 * Проверка подключения к Мобильному Банку
	 * - для клиентов МБК использует GetRegistration и GetRegistration2
	 * - для клиентов ЕРМБ проверяет профиль
	 * @param cardNumber - номер карты
	 * @return true - у клиента есть МБ
	 */
	public static boolean hasAnyMB(String cardNumber)
	{
		return hasMBConnection(cardNumber, true);
	}

	/**
	 * Проверка подключения к Мобильному Банку
	 * @param cardNumber - номер карты
	 * @param useAlternative использовать ли альтернативный вариант получения регистраций
	 * @return true/false
	 */
	private static boolean hasMBConnection(String cardNumber, Boolean useAlternative)
	{
		List<ErmbProfile> ermbProfiles = ErmbHelper.getErmbProfilesByCardNumber(cardNumber);
		if (!ermbProfiles.isEmpty())
		{
			for (ErmbProfile ermbProfile : ermbProfiles)
				if (ermbProfile.isServiceStatus())
					return true;
		}

		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		GroupResult<String, List<MobileBankRegistration>> groupResult = mobileBankService.getRegistrations(useAlternative, cardNumber);
		return !groupResult.getResult(cardNumber).isEmpty();
	}

	/**
	 * Определитель номеров телефона МБ по логину
	 * - для клиентов МБК использует MobileBankService.getRegistrations
	 * - для клиентов ЕРМБ использует профиль (поиск по логину)
	 * @param login    логин
	 * @return коллекция телефонов мобильного банка
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public static Collection<String> getPhones(Login login) throws BusinessException, BusinessLogicException
	{
		Collection<String> phones = null;

		ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByLogin(login);
		if (ermbProfile != null && ermbProfile.isServiceStatus())
			phones = ermbProfile.getPhoneNumbers();

		else
		{
			phones = new ArrayList<String>();
			List<RegistrationShortcut> shortcuts = MOBILE_BANK_BUSINESS_SERVICE.getRegistrationShortcuts(login);
			for(RegistrationShortcut shortcut : shortcuts)
				phones.add(shortcut.getPhoneNumber());
		}

		if (CollectionUtils.isEmpty(phones))
		{
			Person person = PERSON_SERVICE.findByLogin(login);
			if (person == null)
				throw new NotFoundException("Не найдена учётка клиента для логина " + login.getId());

			phones = findErmbPhonesInAnyTB(person);
		}

		return phones;
	}

	/**
	 * Определитель карты по телефону для указанной персоны
	 * - для клиентов МБК используется MobileBankService.getCardByPhone
	 * - для клиентов ЕРМБ используется алгоритм выбора карты (см. PersonBankrollManager#getPriorityCardToChargeOff())
	 * @param phone    номер телефона
	 * @param person    персона, которая или в контексте которой запрашивается карта. Только для МБК
	 * @return карта
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static Card getCardByPhone(String phone, ActivePerson person) throws BusinessException
	{
		PrimaryCardService primaryCardService = new PrimaryCardService();
		return primaryCardService.getPrimaryCard(person.asClient(), PhoneNumber.fromString(phone), null);
	}

	/**
	 * Получить телефоны, отключенные ОСС
	 * @param maxResults максимальное количество записей
	 * @return - список отключенных телефонов
	 * @throws BusinessException
	 */
	public static List<DisconnectedPhoneResult> getDisconnectedPhones(int maxResults) throws BusinessException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			return mobileBankService.getDisconnectedPhones(maxResults);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновить статус отключенных телефонов
	 * @param processedPhones - телефоны
	 */
	public static void updateDisconnectedPhonesState(List<Integer> processedPhones) throws BusinessException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.updateDisconnectedPhonesState(processedPhones);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить телефоны из мобильного банка по картам
	 * @param cardNumbers - список номеров карт
	 * @return
	 */
	public static Set<String> getPhonesByCardNumbers(List<String> cardNumbers) throws BusinessException, BusinessLogicException
	{
		if((CollectionUtils.isEmpty(cardNumbers)))
			return Collections.emptySet();

		Set<String> phones = new HashSet<String>();

		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		String[] cards = new String[cardNumbers.size()];
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
		{
			try
			{
				List<MobileBankRegistration> registrations = mobileBankService.getRegistrationsPack(false, cardNumbers.toArray(cards));
				addPhones(cardNumbers, phones, registrations);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessLogicException(e);
			}
		}
		GroupResult<String, List<MobileBankRegistration>> registrations = mobileBankService.getRegistrations(false, cardNumbers.toArray(cards));

		for (List<MobileBankRegistration> list : registrations.getResults().values())
		{
			if (CollectionUtils.isEmpty(list))
				continue;
			addPhones(cardNumbers, phones, list);
		}
		return phones;
	}

	private static void addPhones(List<String> cardNumbers, Set<String> phones, List<MobileBankRegistration> list)
	{
		for (MobileBankRegistration registration : list)
		{
			String phone = registration.getMainCardInfo().getMobilePhoneNumber();
			for (MobileBankCardInfo cardInfo : registration.getLinkedCards())
			{
				String cNumber = cardInfo.getCardNumber();
				if (!cardNumbers.contains(cNumber))
					continue;

				phones.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone));
			}
		}
	}
}
