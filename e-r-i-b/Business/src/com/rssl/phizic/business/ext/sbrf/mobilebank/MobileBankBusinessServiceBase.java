package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankCardStatus;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;

import java.util.*;

import static com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils.selectCardLinkByCardNumber;

/**
 * @author Erkin
 * @ created 11.03.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
abstract class MobileBankBusinessServiceBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	private static final ServiceProviderService providerService = new ServiceProviderService();

	protected static final SmsCommandComposer smsComposer = new SmsCommandComposer();

	///////////////////////////////////////////////////////////////////////////

	protected CardLink getPersonCardByNumber(Login login, String cardNumber) throws BusinessException, BusinessLogicException
	{
		List<CardLink> cardlinks = loadPersonCardLinks(login);
		CardLink cardlink = selectCardLinkByCardNumber(cardlinks, cardNumber, false);
		if (cardlink != null && !MockHelper.isMockObject(cardlink.getCard()))
			return cardlink;
		return null;
	}

	/**
	 * Получить карту по коду номера карты
	 * @param login логин клиента
	 * @param cardNumberCode код номера карты
	 * @return карта или null
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Card getPersonCardByNumberCode(Login login, String cardNumberCode) throws BusinessException, BusinessLogicException
	{
		List<CardLink> cardlinks = loadPersonCardLinks(login);
		CardLink cardlink = selectCardLinkByCardCode(cardlinks, cardNumberCode);
		if (cardlink != null)
			return cardlinkToCard(cardlink);
		return null;
	}

	protected List<CardLink> loadPersonCardLinks(Login login) throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getInSystemLinks(login, CardLink.class);
	}

	/**
	 * Формирует профиль подключения.
	 * Сформированный профиль не содержит SMS-операций
	 * @param registration - МБ-подключение
	 * @return новый профиль подключения
	 */
	protected RegistrationProfile createProfile(MobileBankRegistration registration)
	{
		MobileBankCardInfo mainCardInfo = registration.getMainCardInfo();
		List<MobileBankCardInfo> linkedCardInfos = registration.getLinkedCards();

		RegistrationProfile profile = new RegistrationProfile();

		// 1. Общие атрибуты подключения
		profile.setMainPhoneNumber(mainCardInfo.getMobilePhoneNumber());
		profile.setStatus(registration.getStatus());
		profile.setTariff(registration.getTariff());

		// 2. Атрибуты основной (платёжной) карты
		CardShortcut mainCardShortcut = createCardShortcut(mainCardInfo);
		profile.setMainCard(mainCardShortcut);

		// 3. Атрибуты связанных (информационных) карт
		List<CardShortcut> infoCards = new ArrayList<CardShortcut>(linkedCardInfos.size());
		for (MobileBankCardInfo cardInfo : linkedCardInfos) {
			CardShortcut cardShortcut = createCardShortcut(cardInfo);
			infoCards.add(cardShortcut);
		}
		profile.setInfoCards(infoCards);

		return profile;
	}

	/**
	 * Формирует шорткат МБ-регистрации
	 * @param registration - МБ-регистрация
	 * @return шорткат
	 */
	protected RegistrationShortcut createRegistrationShortcut(MobileBankRegistration registration)
	{
		MobileBankCardInfo mainCardInfo = registration.getMainCardInfo();

		RegistrationShortcut shortcut = new RegistrationShortcut();
		shortcut.setPhoneNumber(mainCardInfo.getMobilePhoneNumber());
		shortcut.setCardNumber(mainCardInfo.getCardNumber());

		return shortcut;
	}

	/**
	 * Формирует шорткат карты.
	 * Сформированный шорткат не содержит кард-линк
	 * @param cardInfo - карта МБ
	 * @return новый шорткат карты
	 */
	private CardShortcut createCardShortcut(MobileBankCardInfo cardInfo)
	{
		CardShortcut shortcut = new CardShortcut();
		shortcut.setCardNumber(cardInfo.getCardNumber());
		shortcut.setCardStatus(cardInfo.getStatus());

		return shortcut;
	}

	/**
	 * Привязывает SMS-операции к картам профилей подключения
	 * @param profiles - коллекция профилей подключения
	 * @param paymentTemplates - коллекция шаблонов платёжных SMS-запросов
	 */
	protected void bindSmsCommands(Collection<RegistrationProfile> profiles, Collection<MobileBankTemplate> paymentTemplates) throws BusinessException
	{
		if (profiles.isEmpty())
			return;

		Map<String, String> subServiceCodes = getSubServiceCodes(paymentTemplates);
		for (RegistrationProfile profile : profiles)
		{
			CardShortcut mainCardShortcut = profile.getMainCard();
			String mainCardNumber = mainCardShortcut.getCardNumber();
			String mainPhoneNumber = profile.getMainPhoneNumber();

			// 1. SMS-операции подключения
			// помещаются в конец списка избранных SMS-запросов (изменение от 24.03.2011 (BUG026781))
			List<SmsCommand> serviceSmsCommands;
			if (profile.isShowCardNumberInSms())
				serviceSmsCommands = smsComposer.composeRegistrationSmsRequests(mainCardNumber);
			else serviceSmsCommands = smsComposer.composeRegistrationSmsRequests(null);

			// 2. SMS-операции по главной (платёжной) карте
			// 2.1 Управляющие SMS-запросы по карте
			mainCardShortcut.setControlSmsRequests(smsComposer.composeCardControlSmsRequests(mainCardNumber));

			// 2.2 SMS-шаблоны на оплату
			List<MobileBankTemplate> templates = collectCardSmsTemplates(paymentTemplates, mainCardNumber, mainPhoneNumber);
			List<SmsCommand> cardPaymentSmsCommands;
			if (profile.isShowCardNumberInSms())
				cardPaymentSmsCommands = smsComposer.composePaymentSmsTemplates(templates, subServiceCodes, mainCardNumber);
			else cardPaymentSmsCommands = smsComposer.composePaymentSmsTemplates(templates, subServiceCodes, null);
			mainCardShortcut.setPaymentSmsTemplates(cardPaymentSmsCommands);

			// 2.3 SMS-запросы на оплату с карты
			mainCardShortcut.setPaymentSmsRequests(smsComposer.composePaymentSmsRequest());

			// 2.4 Избранные SMS-запросы (для главной страницы)
			List<SmsCommand> favoriteSmsRequests = smsComposer.composeFavoriteSmsRequests(mainCardShortcut);
			favoriteSmsRequests.addAll(serviceSmsCommands);
			mainCardShortcut.setFavoriteSmsRequests(favoriteSmsRequests);

			// 3. SMS-операции по дополнительным (информационным) картам
			for (CardShortcut cardShortcut : profile.getInfoCards())
			{
				String cardNumber = cardShortcut.getCardNumber();

				// 3.1 Управляющие SMS-запросы по карте
				cardShortcut.setControlSmsRequests(smsComposer.composeCardControlSmsRequests(cardNumber));

				// 3.2 Избранные SMS-запросы (для главной страницы)
				cardShortcut.setFavoriteSmsRequests(smsComposer.composeFavoriteSmsRequests(cardShortcut));
			}
		}
	}

	/**
	 * Привязывает кард-линки к кард-шорткатам профилей подключения
	 * @param profiles - коллекция профилей подключения
	 * @param cardlinks - коллекция кард-линков
	 */
	protected void bindCardLinks(Collection<RegistrationProfile> profiles, Collection<CardLink> cardlinks)
	{
		if (profiles.isEmpty())
			return;

		for (RegistrationProfile profile : profiles)
		{
			CardShortcut mainCardShortcut = profile.getMainCard();
			mainCardShortcut.setCardlink(selectCardLinkByCardNumber(cardlinks, mainCardShortcut.getCardNumber(), true));
			for (CardShortcut cardShortcut : profile.getInfoCards())
				cardShortcut.setCardlink(selectCardLinkByCardNumber(cardlinks, cardShortcut.getCardNumber(), true));
		}
	}

	/**
	 * Возвращает коды подуслуги для указанных МБ-шаблонов (CHG026525)
	 * @param templates - коллекция МБ-шаблонов
	 * @return мап "код_получателя -> код_подуслуги"
	 */
	protected Map<String, String> getSubServiceCodes(Collection<MobileBankTemplate> templates) throws BusinessException
	{
		if (templates.isEmpty())
			return Collections.emptyMap();

		List<String> recipientCodes = collectRecipientCodes(templates);
		return providerService.getSubServiceCodes(recipientCodes);
	}

	///////////////////////////////////////////////////////////////////////////

	protected static MobileBankCardInfo[] collectMainCardInfos(Collection<MobileBankRegistration> registrations)
	{
		MobileBankCardInfo[] cards = new MobileBankCardInfo[registrations.size()];

		int i = 0;
		for (MobileBankRegistration registration : registrations)
			cards[i++] = registration.getMainCardInfo();

		return cards;
	}

	protected static String[] collectCardNumbers(Collection<CardLink> cardlinks) throws BusinessException
	{
		List<String> cards = new ArrayList<String>(cardlinks.size());
		for (CardLink cardlink : cardlinks)
		{
			if (!MockHelper.isMockObject(cardlink.getCard()))
				cards.add(cardlink.getNumber());
		}
		return cards.toArray(new String[cards.size()]);
	}

	/**
	 * Возвращает номера телефонов,
	 * на каждый из которого приходится более одного МБ-подключения
	 * @param registrations - МБ-подключения
	 * @return список номеров телефонов (never null)
	 */
	protected static List<String> collectMultiRegPhones(Collection<MobileBankRegistration> registrations)
	{
		if (registrations.isEmpty())
			return Collections.emptyList();

		Map<String, Integer> counter = new HashMap<String, Integer>(registrations.size());
		for (MobileBankRegistration registration : registrations)
		{
			MobileBankCardInfo cardInfo = registration.getMainCardInfo();
			String phoneNumber = cardInfo.getMobilePhoneNumber();
			Integer cnt = counter.get(phoneNumber);
			if (cnt == null)
				cnt = 1;
			else cnt++;
			counter.put(phoneNumber, cnt);
		}

		List<String> phones = new ArrayList<String>(counter.size());
		for (Map.Entry<String, Integer> entry : counter.entrySet())
		{
			if (entry.getValue()>1)
				phones.add(entry.getKey());
		}
		return phones;
	}

	protected static CardLink selectCardLinkByCardCode(Collection<CardLink> cardlinks, String cardCode)
	{
		for (CardLink cardlink : cardlinks) {
			if (cardCode.equals(MobileBankUtils.getCardCode(cardlink.getNumber())))
				return cardlink;
		}
		return null;
	}

	/**
	 * Выбирает МБ-шаблоны по номеру телефона и карты
	 * @param templates - коллекция МБ-шаблонов
	 * @param cardNumber - номер карты
	 * @param phoneNumber - номер телефона
	 * @return список МБ-шаблонов (never null)
	 */
	private static List<MobileBankTemplate> collectCardSmsTemplates(Collection<MobileBankTemplate> templates, String cardNumber, String phoneNumber)
	{
		if (templates.isEmpty())
			return Collections.emptyList();

		List<MobileBankTemplate> cardTemplates = new LinkedList<MobileBankTemplate>();
		for (MobileBankTemplate template : templates) {
			MobileBankCardInfo cardInfo = template.getCardInfo();
			if (cardNumber.equals(cardInfo.getCardNumber()) && phoneNumber.equals(cardInfo.getMobilePhoneNumber()))
				cardTemplates.add(template);
		}
		return cardTemplates;
	}

	/**
	 * Выбирает МБ-регистрацию по коду номера телефона и номеру карты
	 * @param registrations - коллекция МБ-регистраций
	 * @param phoneCode - код номера телефона
	 * @param cardNumber - номер карты
	 * @return найденная МБ-регистрация или null
	 */
	protected static MobileBankRegistration selectRegistration(Collection<MobileBankRegistration> registrations, String phoneCode, String cardNumber)
	{
		for (MobileBankRegistration registration : registrations)
		{
			MobileBankCardInfo cardInfo = registration.getMainCardInfo();
			String phoneNumber = cardInfo.getMobilePhoneNumber();
			if (MobileBankUtils.getPhoneCode(phoneNumber).equals(phoneCode) && cardInfo.getCardNumber().equals(cardNumber))
				return registration;
		}
		return null;
	}

	/**
	 * Выбирает профиль подключения по номеру телефона и номеру карты
	 * @param profiles - коллекция профилей подключения
	 * @param phoneNumber - номер телефона
	 * @param cardNumber - номер карты
	 * @return найденный профиль подключения или null
	 */
	protected static RegistrationProfile selectProfile(Collection<RegistrationProfile> profiles, String phoneNumber, String cardNumber)
	{
		for (RegistrationProfile profile : profiles)
		{
			CardShortcut card = profile.getMainCard();
			if (profile.getMainPhoneNumber().equals(phoneNumber) && card.getCardNumber().equals(cardNumber))
				return profile;
		}
		return null;
	}

	/**
	 * Возвращает карту по карт-линку
	 * Возникающее в процессе работы исключение
	 * пишется в лог, а на выходе возвращается null
	 * @param cardlink - карт-линк
	 * @return карта или null если проблемы с получением карты
	 */
	protected static Card cardlinkToCard(CardLink cardlink)
	{
		Card card = cardlink.getCard();
		if (MockHelper.isMockObject(card))
			return null;
		return card;
	}

	/**
	 * Возвращает коды получателей для указанных МБ-шаблонов
	 * @param templates - коллекция МБ-шаблонов
	 * @return список кодов получателей (never null)
	 */
	private static List<String> collectRecipientCodes(Collection<MobileBankTemplate> templates)
	{
		if (templates.isEmpty())
			return Collections.emptyList();

		List<String> recipients = new ArrayList<String>(templates.size());
		for (MobileBankTemplate template : templates)
			recipients.add(template.getRecipient());
		return recipients;
	}

	protected static MobileBankCardInfo getProfileMainCardInfo(RegistrationProfile profile) throws BusinessException
	{
		CardShortcut cardShortcut = profile.getMainCard();
		CardLink cardLink = cardShortcut.getCardlink();
		if (MockHelper.isMockObject(cardLink.getCard()))
			throw new BusinessException("Не найдена карта " + MaskUtil.getCutCardNumber(cardShortcut.getCardNumber()));
		MobileBankCardStatus cardStatus = cardShortcut.getCardStatus();
		String phoneNumber = profile.getMainPhoneNumber();
		return new BusinessMobileBankCardInfo(cardLink.getNumber(), cardStatus, phoneNumber);
	}
}
