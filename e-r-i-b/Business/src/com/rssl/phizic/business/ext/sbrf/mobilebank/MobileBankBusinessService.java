package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

import static com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils.buildTemplatesList;

/**
 * @author Erkin
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */

/**
 * Сервис для работы с Мобильным Банком в бизнесе
 */
@Deprecated
//todo CHG059738 удалить
public class MobileBankBusinessService extends MobileBankBusinessServiceBase
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService";

	private static final SimpleService simpleService = new SimpleService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает профили регистраций в Мобильном Банке
	 * @param login - логин клиента
	 * @return коллекция регистраций (never null)
	 */
	public List<RegistrationProfile> getRegistrationProfiles(Login login) throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("Аргумент 'login' не может быть null");

		// 1. Загрузка карт
		List<CardLink> cardlinks = loadPersonCardLinks(login);
		String[] cards = collectCardNumbers(cardlinks);

		// 2. Загрузка регистраций
		List<MobileBankRegistration> registrations = loadRegistrations(cards);

		// 3. Загрузка шаблонов SMS-запросов
		List<MobileBankTemplate> smsTemplates = loadSmsTemplates(registrations);

		// 4. Формирование профилей подключений (не содержат кард-линки и SMS-операции)
		List<RegistrationProfile> profiles = buildProfiles(registrations);

		// 5. Добавление SMS-операций и кард-линков в профили
		bindSmsCommands(profiles, smsTemplates);
		bindCardLinks(profiles, cardlinks);

		return profiles;
	}

	/**
	 * Получение профиля регистрации по коду номера телефона и карты
	 * @param login - логин клиента
	 * @param phoneCode - закодированный номер телефона (хеш-код номера в виде строки)
	 * @param cardCode - закодированный номер карты (последние N цифр номера)
	 * @return профиль регистрации либо null, если не найден
	 */
	public RegistrationProfile getRegistrationProfile(Login login, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("Аргумент 'login' не может быть null");
		if (StringHelper.isEmpty(phoneCode))
			throw new IllegalArgumentException("Argument 'phoneCode' cannot be null nor empty");
		if (StringHelper.isEmpty(cardCode))
			throw new IllegalArgumentException("Argument 'cardCode' cannot be null nor empty");

		// 1. Подгрузка карт
		Collection<CardLink> cardlinks = loadPersonCardLinks(login);
		String[] cards = collectCardNumbers(cardlinks);

		// 2. Выбор нужной карты
		CardLink cardlink = selectCardLinkByCardCode(cardlinks, cardCode);
		if (cardlink == null)
			return null;
		String cardNumber = cardlink.getNumber();

		// 3. Загрузка всех МБ-регистраций клиента
		List<MobileBankRegistration> allRegistrations = loadRegistrations(cards);

		// 4. Выбор нужной МБ-регистрации по коду номера телефона и номеру карты
		MobileBankRegistration registration = selectRegistration(allRegistrations, phoneCode, cardlink.getNumber());
		if (registration == null)
			return null;
		String phoneNumber = registration.getMainCardInfo().getMobilePhoneNumber();

		// 5. Загрузка шаблонов SMS-запросов для выбранной МБ-регистрации
		List<MobileBankTemplate> smsTemplates = loadSmsTemplates(Collections.singletonList(registration));

		// 6. Формирование профилей подключения (без учёта кард-линков и SMS-операций)
		// Для правильного формирования профиля нужны все МБ-регистрации (см. флажок RegistrationProfile.showCardNumberInSms)
		List<RegistrationProfile> allProfiles = buildProfiles(allRegistrations);

		// 7. Выбор нужного профиля подключения по номеру телефона и номеру карты
		RegistrationProfile profile = selectProfile(allProfiles, phoneNumber, cardNumber);

		// 8. Добавление SMS-операций и кард-линков к выбранному профилю подключения
		List<RegistrationProfile> profileAsList = Collections.singletonList(profile);
		bindSmsCommands(profileAsList, smsTemplates);
		bindCardLinks(profileAsList, cardlinks);

		return profile;
	}

	/**
	 * Возвращает шорткаты регистраций для указанного клиента
	 * @param login - логин клиента
	 * @return список шорткатов регистрации (never null)
	 */
	public List<RegistrationShortcut> getRegistrationShortcuts(Login login) throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("Аргумент 'login' не может быть null");

		// 1. Подгрузка карт
		Collection<CardLink> cardlinks = loadPersonCardLinks(login);
		String[] cards = collectCardNumbers(cardlinks);

		// 2. Подгрузка регистраций
		List<MobileBankRegistration> registrations = loadRegistrations(cards);

		// 3. Составление шорткатов
		List<RegistrationShortcut> shortcuts = new ArrayList<RegistrationShortcut>(registrations.size());
		for (MobileBankRegistration registration : registrations)
			shortcuts.add(createRegistrationShortcut(registration));

		return shortcuts;
	}

	/**
	 * Возвращает шорткат регистрации по коду номера телефона и карты
	 * @param login - логин клиента
	 * @param phoneCode - закодированный номер телефона (хеш-код номера в виде строки)
	 * @param cardCode - закодированный номер карты (последние N цифр номера)
	 * @return шорткат регистрации
	 */
	public RegistrationShortcut getRegistrationShortcut(Login login, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("Аргумент 'login' не может быть null");
		if (StringHelper.isEmpty(phoneCode))
			throw new IllegalArgumentException("Argument 'phoneCode' cannot be null nor empty");
		if (StringHelper.isEmpty(cardCode))
			throw new IllegalArgumentException("Argument 'cardCode' cannot be null nor empty");

		// 1. Подгрузка карты
		Card card = getPersonCardByNumberCode(login, cardCode);
		if (card == null)
			return null;

		// 2. Подгрузка регистраций по карте
		List<MobileBankRegistration> registrations = loadRegistrations(new String[]{card.getNumber()});

		// 3. Выбор нужной регистрации по номеру телефона
		for (MobileBankRegistration registration : registrations) {
			String phoneNumber = registration.getMainCardInfo().getMobilePhoneNumber();
			if (MobileBankUtils.getPhoneCode(phoneNumber).equals(phoneCode))
				return createRegistrationShortcut(registration);
		}

		return null;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает МБ-апдейт по ID
	 * @param login - логин клиента
	 * @param updateId - идентификатор МБ-апдейта
	 * @return МБ-апдейт или null, если не найден
	 */
	public PaymentTemplateUpdate getUpdate(Login login, long updateId) throws BusinessException
	{
		PaymentTemplateUpdate update = simpleService.findById(PaymentTemplateUpdate.class, updateId);
		if (update == null)
			return null;

		if (!login.getId().equals(update.getLogin().getId()))
			throw new BusinessException("Апдейт шаблонов SMS-платежей " +
					"не доступен текущему пользователю. " +
					"PaymentTemplateUpdate ID = " + updateId + ", " +
					"LoginID = " + login.getId());

		return update;
	}

	/**
	 * Сохраняет МБ-апдейт в базе ИКФЛ
	 * @param update - МБ-апдейт
	 */
	public void saveUpdate(PaymentTemplateUpdate update) throws BusinessException
	{
		if (update == null)
			throw new NullPointerException("Аргумент 'update' не может быть null");

		simpleService.addOrUpdate(update);
	}

	/**
	 * Отправляет МБ-апдейт в Мобильный Банк
	 * @param update - МБ-апдейт
	 */
	public void applyUpdate(PaymentTemplateUpdate update) throws BusinessException, BusinessLogicException
	{
		Login login = (Login) update.getLogin();

		CardLink cardLink = getPersonCardByNumber(login, update.getCardNumber());
		if (cardLink == null)
			throw new BusinessException("Не найдена карта регистрации");

		MobileBankCardInfo cardInfo = new BusinessMobileBankCardInfo(
				cardLink.getNumber(), MobileBankCardStatus.ACTIVE, update.getPhoneNumber());

		List<MobileBankTemplate> templates = buildTemplatesList(cardInfo, update.getDestlist());
		switch (update.getType()) {
			case ADD:
				addTemplates(cardInfo, templates);
				break;
			case REMOVE:
				removeTemplates(cardInfo, templates);
				break;
			default:
				log.warn("Неизвестный тип обновления шаблонов платежей: " + update.getType());
				return;
		}
	}

	/**
	 * Удаляет МБ-апдейт из базы ИКФЛ
	 * @param update - МБ-апдейт
	 */
	public void deleteUpdate(PaymentTemplateUpdate update) throws BusinessException
	{
		if (update == null)
			throw new NullPointerException("Аргумент 'update' не может быть null");
		simpleService.remove(update);
	}

	/**
	 * Возвращает SMS-операции МБ-апдейта
	 * @param update - update для списка шаблонов
	 * @return список SMS-операций
	 */
	public List<SmsCommand> getUpdateSmsCommands(PaymentTemplateUpdate update) throws BusinessException, BusinessLogicException
	{
		Login login = (Login) update.getLogin();
		String phoneNumber = update.getPhoneNumber();
		String cardNumber = update.getCardNumber();
		RegistrationProfile profile = getRegistrationProfile(login, MobileBankUtils.getPhoneCode(phoneNumber), MobileBankUtils.getCardCode(cardNumber));
		if (profile == null)
			return Collections.emptyList();

		// Определим вид будущего списка шаблонов
		// Для того чтобы узнать, как будут выглядеть новые шаблоны для пользователя,
		// их нужно "сцепить" со старыми (индекс нового шаблона зависит от предшествующего),
		// а затем в общем списке найти шаблоны из апдейта
		CardShortcut mainCardShortcut = profile.getMainCard();
		MobileBankCardInfo mainCardInfo = getProfileMainCardInfo(profile);

		List<SmsCommand> oldCommands = mainCardShortcut.getPaymentSmsTemplates();

		List<MobileBankTemplate> oldTemplates = MobileBankUtils.buildTemplatesList(mainCardInfo, oldCommands);
		List<MobileBankTemplate> newTemplates = MobileBankUtils.buildTemplatesList(mainCardInfo, update.getDestlist());

		// Получение общего списка шаблонов
		List<MobileBankTemplate> allTemplates = new LinkedList<MobileBankTemplate>();
		allTemplates.addAll(oldTemplates);
		allTemplates.addAll(newTemplates);

		Map<String, String> subServiceCodes = getSubServiceCodes(allTemplates);
		List<SmsCommand> allCommands;
		if (profile.isShowCardNumberInSms())
			allCommands = smsComposer.composePaymentSmsTemplates(allTemplates, subServiceCodes, cardNumber);
		else allCommands = smsComposer.composePaymentSmsTemplates(allTemplates, subServiceCodes, null);

		// Выборка новых шаблонов
		List<SmsCommand> newCommands = new LinkedList<SmsCommand>();
		for (SmsCommand command : allCommands)
		{
			if (StringHelper.isEmpty(command.getRecipientCode()))
				continue;
			if (StringHelper.isEmpty(command.getPayerCode()))
				continue;
			for (MobileBankTemplate template : newTemplates)
			{
				boolean eq = StringUtils.equals(template.getRecipient(), command.getRecipientCode());
				eq=eq && ArrayUtils.contains(template.getPayerCodes(), command.getPayerCode());
				if (eq) {
					newCommands.add(command);
					break;
				}
			}
		}
		return newCommands;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Загружает МБ-регистрации по указанным картам указанного пользователя
	 * @param cardNumbers - массив номеров карт
	 * @return список МБ-регистраций (never null)
	 */
	public List<MobileBankRegistration> loadRegistrations(String[] cardNumbers) throws BusinessException, BusinessLogicException
	{
		return loadRegistrations(cardNumbers, false);
	}

	/**
	 * Загружает МБ-регистрации по указанным картам указанного пользователя
	 * @param cardNumbers - массив номеров карт
	 * @param alternative - использовать альтернативный способ получения регистраций (подробности в GetRegistrationsJDBCAction)
	 * @return список МБ-регистраций (never null)
	 */
	public List<MobileBankRegistration> loadRegistrations(String[] cardNumbers, boolean alternative) throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isEmpty(cardNumbers))
			return Collections.emptyList();

		// 1. Запрос к шлюзу
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
		{
			try
			{
				return mobileBankService.getRegistrationsPack(alternative, cardNumbers);
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
		else
		{
			GroupResult<String, List<MobileBankRegistration>> result = mobileBankService.getRegistrations(alternative, cardNumbers);
			List<MobileBankRegistration> registrations = new ArrayList<MobileBankRegistration>();
			// 2. Адаптация результата
			for (String cardNumber : cardNumbers)
			{
				IKFLException ex = result.getException(cardNumber);
				if (ex != null)
					log.error("Сбой при получении списка подключений по карте " + MaskUtil.getCutCardNumberForLog(cardNumber), ex);

				List<MobileBankRegistration> cardRegistrations = result.getResult(cardNumber);
				if (CollectionUtils.isEmpty(cardRegistrations))
					continue;

				registrations.addAll(cardRegistrations);
			}
			return registrations;
		}
	}

	/**
	 *
	 * Установить статус Быстрых сервисов для указанного телефона
	 *
	 * @param phoneNumber номер телефона
	 * @param status статус
	 * @throws BusinessException
	 */
	public QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws BusinessException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			return mobileBankService.setQuickServiceStatus(phoneNumber, status);
		}
		catch (GateException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Загружает МБ-шаблоны SMS-платежей для указанных МБ-регистраций
	 * @param registrations - коллекция МБ-регистраций
	 * @return список шаблонов SMS-платежей (never null)
	 */
	private List<MobileBankTemplate> loadSmsTemplates(Collection<MobileBankRegistration> registrations) throws BusinessException
	{
		if (registrations.isEmpty())
			return Collections.emptyList();

		MobileBankCardInfo[] mainCardInfos = collectMainCardInfos(registrations);

		// 1. Запрос к шлюзу
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result =
				mobileBankService.getTemplates(null, mainCardInfos);

		// 2. Адаптация результата
		List<MobileBankTemplate> templates = new LinkedList<MobileBankTemplate>();
		for (MobileBankCardInfo cardInfo : mainCardInfos)
		{
			Exception ex = result.getException(cardInfo);
			if (ex != null) {
				log.error("Сбой при получении списка шаблонов платежей. " +
						"телефон: " + cardInfo.getMobilePhoneNumber() + ". " +
						"карта: " + MaskUtil.getCutCardNumberForLog(cardInfo.getCardNumber()), ex);
			}

			List<MobileBankTemplate> cardTemplates = result.getResult(cardInfo);
			if (CollectionUtils.isEmpty(cardTemplates))
				continue;

			templates.addAll(cardTemplates);
		}

		// 3. Удаление префиксов в кодах плательщиков
		return templates;
	}

	private void addTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws BusinessException, BusinessLogicException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.addTemplates(cardInfo, templates);
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

	private void removeTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws BusinessException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.removeTemplates(cardInfo, templates);
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

	private List<RegistrationProfile> buildProfiles(Collection<MobileBankRegistration> registrations) throws BusinessException
	{
		if (registrations.isEmpty())
			return Collections.emptyList();

		List<String> multiregPhones = collectMultiRegPhones(registrations);

		List<RegistrationProfile> profiles = new ArrayList<RegistrationProfile>(registrations.size());
		for (MobileBankRegistration registration : registrations)
		{
			RegistrationProfile profile = createProfile(registration);
			String mainPhoneNumber = profile.getMainPhoneNumber();

			// Если получено несколько регистраций на один номер телефона,
			// то в конец шаблонов по услуге нужно добавить номер карты
			profile.setShowCardNumberInSms(multiregPhones.contains(mainPhoneNumber));

			/*
			 * Получаем статус "Быстрых сервисов" для номера в mainPhoneNumber
			 */
			try
			{
				MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
				QuickServiceStatusCode statusCode = mobileBankService.getQuickServiceStatus(mainPhoneNumber);
				profile.setQuickServiceStatusCode(statusCode);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessException(e);
			}

			profiles.add(profile);
		}

		return profiles;
	}

	/**
	 * Возвращает заявку на подключение к МБ
	 * @param claimId - ID заявки
	 * @return заявка или null, если не найдена
	 */
	public MobileBankRegistrationClaim getRegistrationClaim(Long claimId) throws BusinessException
	{
		if (claimId == null)
			throw new NullPointerException("Аргумент 'claimId' не может быть null");
		return simpleService.findById(MobileBankRegistrationClaim.class, claimId);
	}

	/**
	 * Возвращает последнюю исполненную заявку 
	 * @param loginId - LoginID пользователя, заявки которого нужно проверить
	 * @return заявка или null, если не найдена
	 */
	public MobileBankRegistrationClaim getLastCompletedRegistrationClaim(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MobileBankRegistrationClaim>()
			{
				public MobileBankRegistrationClaim run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".getLastCompletedRegistrationClaim");
					query.setLong("loginId", loginId);
					query.setMaxResults(1);
					return (MobileBankRegistrationClaim)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Добавляет новую или обновляет в бд имеющуюся заявку на подключение к МБ
	 * @param claim - заявка
	 */
	public void addOrUpdateRegistrationClaim(MobileBankRegistrationClaim claim) throws BusinessException
	{
		simpleService.addOrUpdate(claim);
	}

	/**
	 * Удалить все неоформленные (неподтверждённые) заявки на подключение для заданного пользователя
	 * @param loginId - LoginID пользователя
	 */
	public void removeUncompletedRegistrationClaims(final long loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".removeUncompletedRegistrationClaims");
					query.setLong("loginId", loginId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Отправка заявки на подключение в МБ
	 * @param person - клиент, оформивший заявку
	 * @param claim - заявка
	 */
	public void sendRegistrationClaim(ActivePerson person, MobileBankRegistrationClaim claim) throws BusinessException
	{
		try
		{
			Client client = person.asClient();
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.addRegistration(client, claim.getCardNumber(), claim.getPhoneNumber(), "001", claim.getTariff());
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
}
