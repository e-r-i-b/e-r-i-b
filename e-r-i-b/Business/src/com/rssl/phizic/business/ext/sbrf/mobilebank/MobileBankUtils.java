package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.SendSMSPreferredMethod;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public final class MobileBankUtils
{
	public static final String INFO_CARDS_PHONES_CACHE = "info-cards-phones-cache";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final int MOBILE_BANK_CARD_CODE_LENGTH = 4;

	private static final ExternalResourceService cardLinkService = new ExternalResourceService();
	private static final DepartmentService departmentService = new DepartmentService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает по номеру телефона его код
	 * @param phoneNumber номер телефона
	 * @return код телефона
	 */
	public static String getPhoneCode(String phoneNumber)
	{
		if (StringHelper.isEmpty(phoneNumber))
			throw new IllegalArgumentException("Argument 'phoneNumber' cannot be null nor empty");

		String normalizedPhoneNumber =
				PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);
		return String.valueOf(normalizedPhoneNumber.hashCode());
	}

	/**
	 * Возвращает по номеру карты её код
	 * @param cardNumber номер карты
	 * @return код карты
	 */
	public static String getCardCode(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			throw new IllegalArgumentException("Argument 'cardNumber' cannot be null nor empty");

		int len = cardNumber.length();
		if (len < MOBILE_BANK_CARD_CODE_LENGTH)
			return "";
		else return cardNumber.substring(len - MOBILE_BANK_CARD_CODE_LENGTH);
	}

	/**
	 * Выбирает кард-линк по номеру карты
	 * @param cardlinks - коллекция кард-линоков
	 * @param cardNumber - номер карты
	 * @param createIfNone - флажок "создавать пустышку, если кард-линк не найден"
	 * @return найденный кард-линк, либо пустышка, либо null
	 */
	public static CardLink selectCardLinkByCardNumber(Collection<CardLink> cardlinks, String cardNumber, boolean createIfNone)
	{
		for (CardLink cardlink : cardlinks) {
			if (cardNumber.equals(cardlink.getNumber()))
				return cardlink;
		}

		if (createIfNone) {
			CardLink cardlink = new CardLink();
			cardlink.setNumber(cardNumber);
			cardlink.setName(MobileBankConstants.DEFAULT_CARD_NAME);
			return cardlink;
		}

		return null;
	}

	/**
	 * Заворачивает список пар "получатель - плательщик" в строку вида
	 *   получатель1:плательщик1_1:плательщик1_2;получатель2:плательщик2_1...
	 * @param destlist - список пар "получатель - плательщик"
	 * @return строка
	 */
	public static String encodeDestlist(Collection<Pair<String, String>> destlist)
	{
		Map<String, Set<String>> destmap = new LinkedHashMap<String, Set<String>>(destlist.size());
		for (Pair<String, String> dest : destlist) {
			String recipient = dest.getFirst();
			String payerToAdd = dest.getSecond();

			Set<String> payers = destmap.get(recipient);
			if (payers == null) {
				payers = new LinkedHashSet<String>();
				destmap.put(recipient, payers);
			}
			payers.add(payerToAdd);
		}

		List<String> strings = new ArrayList<String>(destmap.size());
		for (Map.Entry<String, Set<String>> entry : destmap.entrySet()) {
			String recipient = entry.getKey();
			Set<String> payers = entry.getValue();
			strings.add(recipient + ":" + StringUtils.join(payers, ":"));
		}
		return StringUtils.join(strings, ';');
	}

	/**
	 * Разворачивает строку вида
	 *   получатель1:плательщик1_1:плательщик1_2;получатель2:плательщик2;...
	 * в коллекцию пар "получатель - плательщик"
	 * @param destlistAsString - коллекция пар "получатель - плательщик" в виде строки
	 * @return коллекция пар "получатель - плательщик"
	 */
	public static Collection<Pair<String, String>> decodeDestlist(String destlistAsString)
	{
		if (StringHelper.isEmpty(destlistAsString))
			throw new IllegalArgumentException("Argument 'destlistAsString' cannot be null nor empty");

		String[] samples =  destlistAsString.split(";");
		Collection<Pair<String, String>> destlist =
				new LinkedList<Pair<String, String>>();
		for (String sample : samples) {
			String[] chunks = sample.split(":");
			for (int i=1; i<chunks.length; i++)
				destlist.add(new Pair<String, String>(chunks[0], chunks[i]));
		}
		return destlist;	
	}

	/**
	 * Формирует список шаблонов в формате шлюза
	 * @param cardInfo
	 * @param destlistAsString - коллекция пар "получатель - плательщик" в виде строки
	 * @return список шаблонов
	 */
	public static List<MobileBankTemplate> buildTemplatesList(MobileBankCardInfo cardInfo, String destlistAsString)
	{
		return buildTemplatesList(cardInfo, decodeDestlist(destlistAsString));
	}

	/**
	 * Формирует список шаблонов в формате шлюза
	 * @param cardInfo
	 * @param smsPaymentCommands - список SMS-платежей или SMS-шаблонов
	 * @return список шаблонов
	 */
	public static List<MobileBankTemplate> buildTemplatesList(MobileBankCardInfo cardInfo, List<SmsCommand> smsPaymentCommands)
	{
		List<Pair<String, String>> destlist = new LinkedList<Pair<String, String>>();
		for (SmsCommand smsCommand : smsPaymentCommands) {
			String recipient = smsCommand.getRecipientCode();
			if (StringHelper.isEmpty(recipient))
				continue;
			String payer = smsCommand.getPayerCode();
			if (StringHelper.isEmpty(payer))
				continue;
			destlist.add(new Pair<String, String>(recipient, payer));
		}
		return buildTemplatesList(cardInfo, destlist);
	}

	/**
	 * Формирует список шаблонов в формате шлюза
	 * @param cardInfo
	 * @param destlist - коллекция пар "получатель - плательщик"
	 * @return список шаблонов
	 */
	private static List<MobileBankTemplate> buildTemplatesList(MobileBankCardInfo cardInfo, Collection<Pair<String, String>> destlist)
	{
		Map<String, Set<String>> map = new LinkedHashMap<String, Set<String>>();
		for (Pair<String, String> pair : destlist) {
			Set<String> codes = map.get(pair.getFirst());
			if (codes == null) {
				codes = new LinkedHashSet<String>();
				map.put(pair.getFirst(), codes);
			}
			codes.add(pair.getSecond());
		}

		List<MobileBankTemplate> templates = new ArrayList<MobileBankTemplate>(map.size());
		for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
			String recipient = entry.getKey();
			Set<String> payers = entry.getValue();
			MobileBankTemplate template =
					new BusinessMobileBankTemplate(cardInfo, recipient, payers.toArray(new String[payers.size()]));
			templates.add(template);
		}
		return templates;
	}

	/**
	 * Проверяет поставщика услуг на совместимость с Мобильным Банком
	 * @param provider - поставщик услуг
	 * @throws UncompatibleServiceProviderException - исключение с описанием проблемы
	 */
	public static void testServiceProviderMobilebankCompatible(BillingServiceProvider provider)
			throws UncompatibleServiceProviderException
	{
        FieldDescription keyField = null;
		for (FieldDescription field : provider.getFieldDescriptions())
		{
			if (field.isKey()) {
				if (keyField == null)
					keyField = field;
				else throw new UncompatibleServiceProviderException("Для поставщика услуг " +
						"указано более одного ключевого поля.", provider);
			}
		}

		if (keyField == null)
			throw new UncompatibleServiceProviderException("Не указано ключевое поле " +
					"для поставщика услуг.", provider);

		if (!keyField.isVisible())
			throw new UncompatibleServiceProviderException("Ключевое поле поставщика услуг " +
					"указано как невидимое. Поле: " + keyField.getName() + ".", provider);

		if (!keyField.isRequired())
			throw new UncompatibleServiceProviderException("Ключевое поле поставщика услуг " +
					"указано как необязательное. Поле: " + keyField.getName() + ".", provider);

		if (!keyField.isEditable())
			throw new UncompatibleServiceProviderException("Ключевое поле поставщика услуг " +
					"указано как нередактируемое. Поле: " + keyField.getName() + ".", provider);
	}

	/**
	 * Возвращает код подуслуги поставщика
	 * @param provider - поставщик
	 * @return код подуслуги или null, если у поставщика нет кода подуслуги
	 */
	public static String getProviderSubserviceCode(ServiceProviderBase provider)
	{
		Field subserviceField = provider.getField(PaymentFieldKeys.SUBSERVICE_CODE);
		if (subserviceField != null)
			return subserviceField.getDefaultValue();
		else return null;
	}

	/**
	 * Получить номера телефонов из мобильного банка по информационным картам
	 * @param login логин
	 * @return список номеров телефонов
	 */
	public static Collection<String> getMobilebankPhonesByInfoCards(Login login) throws BusinessException
	{
		return getMobilebankPhonesByInfoCards(login, false);
	}

	/**
	 * Получить номера телефонов из мобильного банка по информационным картам
	 * @param login логин
	 * @param alternative использовать ли альтернативный способ получения регистраций
	 * @return список номеров телефонов
	 */
	public static Collection<String> getMobilebankPhonesByInfoCards(Login login, boolean alternative) throws BusinessException
	{
		Cache cache = CacheProvider.getCache(INFO_CARDS_PHONES_CACHE);
		Element cacheValue = null;
		String cacheKey = getInfoCardsPhonesCacheKey(login, alternative);
		if (cache != null)
			cacheValue = cache.get(cacheKey);

		if (cacheValue != null)
		    return new HashSet<String>((Collection<String>)cacheValue.getObjectValue());

		List<CardLink> cardLinks = CardLinkHelper.getMainCardLinks(login);
		if (cardLinks == null)
			cardLinks = Collections.emptyList();

		Collection<String> resultPhones = getMobilebankPhones(login, cardLinks, false, alternative);

		if (!CollectionUtils.isEmpty(resultPhones) && !cardLinks.isEmpty()) //результаты первого подключения не кешируем
			cache.put(new Element(cacheKey, resultPhones));

		return new HashSet<String>(resultPhones);
	}

	/**
	 * Сформировать ключ для кеша номеров телефонов клиента из МБ
	 * @param login - логин клиента
	 * @param alternative - флаг, использовать ли альтернативный способ получения регистраций
	 * @return ключ для кеша номеров телефонов клиента
	 */
	public static String getInfoCardsPhonesCacheKey(CommonLogin login, boolean alternative)
	{
		return Long.toString(login.getId()) + Boolean.toString(alternative);
	}

	/**
	 * @param login - Логин пользователя
	 * @param cardLinks - список карт, по которым получаем регистрации
	 * @param isNotification - если true - используем алгоритм оповещений о входе
	 * @return список телефонов
	 * @throws BusinessException
	 */
	private static Collection<String> getMobilebankPhones(Login login, List<CardLink> cardLinks, boolean isNotification, boolean alternative) throws BusinessException
	{
		String lastLogonCardNumber = login.getLastLogonCardNumber();
		List<String> mainCardNumbers = getMainCardNumbers(login, cardLinks, lastLogonCardNumber);
		MobileBankService mobilebankService = GateSingleton.getFactory().service(MobileBankService.class);
		GroupResult<String, List<MobileBankRegistration>> registrations = mobilebankService.getRegistrations(alternative, mainCardNumbers.toArray(new String[mainCardNumbers.size()]));

		//формируем map infoCardsPhones <номер информационной карты -> список номеров телефонов для рассылки оповещений по этой карте>
		//пробегаемся по всем регистрациям и для каждой карты, подключенной к регистрации как информационная сохраняем номер телефона из регистрации
		Map<String, Set<String>> infoCardsPhones = new HashMap<String, Set<String>>();
		for (List<MobileBankRegistration> list : registrations.getResults().values())
		{
			if (CollectionUtils.isEmpty(list))
				continue;
			for (MobileBankRegistration registration : list)
			{
				if (CollectionUtils.isEmpty(registration.getLinkedCards()))
                    continue;

				String phone = registration.getMainCardInfo().getMobilePhoneNumber();
				for (MobileBankCardInfo cardInfo : registration.getLinkedCards())
				{
					String cardNumber = cardInfo.getCardNumber();
					if (!mainCardNumbers.contains(cardNumber))
						continue;

					Set<String> phones = infoCardsPhones.get(cardNumber);
					if (phones == null)
					{
						phones = new HashSet<String>();
						infoCardsPhones.put(cardNumber, phones);
					}
					phones.add(phone);
				}
			}
		}

		//Если перечень представляет собой пустое множество, СМС-сообщения не отправляются.
		if (MapUtils.isEmpty(infoCardsPhones))
			return new HashSet<String>();

		Set<String> resultPhones = new HashSet<String>();

		if(!isNotification){
			//если карта последнего входа есть в списке ключей infoCardsPhones то необходимо вернуть телефоны из infoCardsPhones для этой карты
			Set<String> lastLogonCardPhones = infoCardsPhones.get(lastLogonCardNumber);
			if(lastLogonCardPhones != null)
				for (String phone : lastLogonCardPhones)
				{
					resultPhones.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone));
				}
		}

		if (resultPhones.isEmpty())
		{
			// все номера телефонов, для которых есть хотя бы одна информационная карта
			for (Set<String> phones : infoCardsPhones.values())
			{
				for (String phone : phones)
				{
					resultPhones.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone));
				}
			}
		}

		return resultPhones;
	}

	/**
	 * Получить номера телефонов из мобильного банка по всем основным картам, подключенным как информационные
	 * @param login логин
	 * @return список номеров телефонов
	 */
	public static Collection<String> getMobilebankPhonesByAllInfoCards(Login login) throws BusinessException
	{
		List<CardLink> cardLinks = CardLinkHelper.getMainCardLinks(login);
		if (cardLinks == null)
			cardLinks = Collections.emptyList();

		return getMobilebankPhones(login, cardLinks, true, false);
	}

	private static List<String> getMainCardNumbers(Login login, List<CardLink> cardLinks, String lastLogonCardNumber)
	{
		if (cardLinks.isEmpty() && StringHelper.isEmpty(lastLogonCardNumber))
		{
			log.warn("У пользователя нет карт для получения телефонных номеров из мобильного банка. LOGIN_ID=" + login.getId());
			return Collections.emptyList();
		}

		List<String> mainCardNumbers = new ArrayList<String>();
		for (CardLink link : cardLinks)
		{
			mainCardNumbers.add(link.getNumber());
		}

		if (!StringHelper.isEmpty(lastLogonCardNumber) && !mainCardNumbers.contains(lastLogonCardNumber))
		{
			mainCardNumbers.add(lastLogonCardNumber);
		}
		return mainCardNumbers;
	}

	private static CardLink getCardLink(String cardNumber, List<CardLink> cardLinks) throws BusinessException
	{
		if (StringHelper.isEmpty(cardNumber))
			return null;

		for (CardLink cardLink : cardLinks) {
			if (cardNumber.equals(cardLink.getNumber()))
				return cardLink;
		}

		return null;
	}


	/**
	 * Получение номеров телефонов по карте
	 * @param cardNumber номер карта, по которой необходимо получить номер
	 * @param alternative использовать ли альтернативный вариант получения регистраций
	 * false - используется GetRegistrations, true - используются GetRegistrations и GetRegistrations2
	 * @return номера телефонов
	 */
	public static List<String> getCardPhones(String cardNumber, boolean alternative)
	{
		// Проверяем, можно ли отправить СМС карте

		if (!alternative) //если используем альтернативный способ получения регистраций, не проверяем
		{
			//считаем, что отправка по номеру карты недоступна
			return null;
		}

		// Если можно отправить, то телефоны получаем по переданной карте.
		List<MobileBankRegistration> registrations = getCardMBRegistrations(cardNumber, alternative);
		if (CollectionUtils.isEmpty(registrations))
			return null;

		// Соберём список телефонов
		List<String> phones = new ArrayList<String>(registrations.size());
		for (MobileBankRegistration registration : registrations)
		{
			for (MobileBankCardInfo cardInfo : registration.getLinkedCards())
			{
				//Выбираем номера телефонов, для которых переданная карта указана как информационная
				if(cardInfo.getCardNumber().equals(cardNumber))
				{
					String phone = registration.getMainCardInfo().getMobilePhoneNumber();
					phones.add(phone);
				}
			}
		}

		return phones;
	}

	private static List<MobileBankRegistration> getCardMBRegistrations(String cardNumber, boolean alternative)
	{
		MobileBankService mobilebankService = GateSingleton.getFactory().service(MobileBankService.class);

		GroupResult<String, List<MobileBankRegistration>> result1
				= mobilebankService.getRegistrations(alternative, cardNumber);

		IKFLException cardException = result1.getException(cardNumber);
		if (cardException != null) {
			log.warn("Сбой при получении регистраций по карте", cardException);
		}

		return result1.getResult(cardNumber);
	}

	/**
	 * Получить один номер из списка номеров текущего клиента на которые должны посылаться оповещения.
	 * @return номер телефона
	 */
	public static String getMobilePhoneForCurrentUser() throws BusinessException
	{
		Collection<String> phones = getMobilePhonesForCurrentUser();
		return CollectionUtils.isEmpty(phones) ? StringUtils.EMPTY : phones.iterator().next();
	}

	/**
	 * список номеров клиента на которые должны посылаться оповещения о новых письмах и смс с паролями
	 * @param person клиент
	 * @return список номеров
	 * @throws BusinessException
	 */
	public static List<String> getMobilePhones(ActivePerson person) throws BusinessException
	{
		return getMobilePhones(person, (ExtendedDepartment) departmentService.getTB(person.getDepartmentId()));
	}

	/**
	 * список номеров  на для текущего клиента которые должны посылаться оповещения о новых письмах и смс с паролями
	 * @return список номеров
	 * @throws BusinessException
	 */
	public static List<String> getMobilePhonesForCurrentUser() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return Collections.emptyList();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return getMobilePhones(personData.getPerson(), (ExtendedDepartment) personData.getTb());
	}

	/**
	 * список номеров клиента на которые должны посылаться оповещения о новых письмах и смс с паролями
	 * @param person клиент
	 * @return список номеров
	 * @throws BusinessException
	 */
	public static List<String> getMobilePhones(ActivePerson person, ExtendedDepartment userTB) throws BusinessException
	{
		SendSMSPreferredMethod sendSMSPreferredTBMethod = userTB.getSendSMSPreferredMethod();
		return new ArrayList<String>(sendSMSPreferredTBMethod.getPhones(person, false));
	}

	/**
	 * список номеров клиента на которые должны посылаться оповещения о входе
	 * @param person клиент
	 * @return список номеров
	 * @throws BusinessException
	 */
	public static List<String> getMobilePhonesForNotification(ActivePerson person) throws BusinessException
	{
		ExtendedDepartment userTB = (ExtendedDepartment) departmentService.getTB(person.getDepartmentId());
		SendSMSPreferredMethod sendSMSPreferredTBMethod = userTB.getSendSMSPreferredMethod();
		return new ArrayList<String>(sendSMSPreferredTBMethod.getPhones(person, true));
	}

	/**
	 * @return список номеров по информационыым картам для текущего пользователя
	 * @throws BusinessException
	 */
	public static Collection<String> getPhonesByAllInfoCardsForCurrentUser() throws BusinessException
	{
		if (PersonContext.isAvailable())
			return getMobilebankPhonesByAllInfoCards(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin());
		return null;
	}
}
