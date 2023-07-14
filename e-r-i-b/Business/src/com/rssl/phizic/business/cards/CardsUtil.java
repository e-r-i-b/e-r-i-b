package com.rssl.phizic.business.cards;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.card.ErmbPaymentCardLinkFilter;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author Pakhomova
 * @ created 19.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class CardsUtil
{
	private static final String CARD_FORMAT_REGEX = "([0-9]{4})([0-9]{4})([0-9]{4})([0-9]*)";
	private static final String CARD_FORMAT_MASK = "$1 $2 $3 $4";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String[] SALARY_TYPE = {"N", "O", "Z", "NA", "OA", "ZA"};

	private static final ExternalResourceService externalService = new ExternalResourceService();

	private static final DepartmentService departmentService = new DepartmentService();

	private static CardsConfig getCardConfig()
	{
		return ConfigFactory.getConfig(CardsConfig.class);
	}

	/**
	 * Возвращает алиас карты, который присвоил ей пользователь
	 *  (carlink.getName()),
	 * либо её описание (cardlink.getCard().getDescription()) (VISA, MasterCard и т.п.)
	 * @param cardlink - кард-линк либо null
	 * @return алиас карты, либо её описание, либо пусто
	 */
	public static String getCardUserName(CardLink cardlink)
	{
		if (cardlink == null)
			return "";
		try
		{
			String cardName = null;

			if (cardlink != null) {
				cardName = cardlink.getName();
				if (StringHelper.isEmpty(cardName)) {
					Card card = cardlink.getCard();
					if (card != null)
						cardName = card.getDescription();
				}
			}

			return cardName;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения алиаса карты", e);
			return "";
		}
	}

	/**
	 * Возвращает кард-линк по номеру карты
	 * @param cardNumber - номер карты
	 * @return кард-линк либо null
	 */
	public static CardLink getCardLink(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			return null;
		try
		{
			if (!PersonContext.isAvailable())
				return null;

			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			CardLink cardlink = personData.findCard(cardNumber);
			if (cardlink == null)
				log.warn("Не могу найти card-link по номеру " + cardNumber);

			return cardlink;
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска кард-линка", ex);
			return null;
		}
	}

	/**
	 * Возвращает кард-линк по номеру карты без учета видимости
	 * @param cardNumber - номер карты
	 * @return кард-линк либо null
	 */
	public static CardLink getCardLinkWithoutVisible(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			return null;
		}

		try
		{
			if (!PersonContext.isAvailable())
			{
				return null;
			}
			Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
			CardLink cardlink = externalService.findLinkByNumber(loginId, ResourceType.CARD, cardNumber);
			if (cardlink == null)
			{
				log.warn("Не могу найти card-link по номеру " + cardNumber);
			}
			return cardlink;
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска кард-линка", ex);
			return null;
		}
	}

	/**
	 * Содержится ли карта в списке линков
	 * @param list список линков карт
	 * @param cardNumber номер карты
	 * @return true = содержится
	 */
	public static boolean isCardLinkInList(List<CardLink> list, String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			return false;
		}
		boolean contain = false;
		for (CardLink cardLink: list)
		{
			if (cardNumber.equals(cardLink.getNumber()))
			{
				contain = true;
				break;
			}
		}
		return contain;
	}

	/**
	 * Возвращает кард линк по идентификатору карты
	 * @param id идентификатор
	 * @return кард линк
	 */
	public static CardLink getCardLinkById (Long id)
	{
		if(id == null)
		{
			return null;
		}

		try
		{
			CardLink cardLink = externalService.findLinkById(CardLink.class, id);
			if (cardLink == null)
			{
				log.warn("Не могу найти card-link по id= " + id);
			}
			return cardLink;
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска кард-линка", ex);
			return null;
		}
	}

	/**
	 * Возвращает кард линк по идентификатору карты с проверкой принадлежности карты клиенту
	 * @param cardId идентификатор
	 * @return кард линк
	 */
	public static CardLink getCardLinkOfClientById (Long cardId)
	{
		CardLink cardLink = getCardLinkById(cardId);
		if (cardLink == null)
		{
			return null;
		}
		if (!PersonContext.isAvailable())
		{
			return null;
		}
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		if (!cardLink.getLoginId().equals(loginId))
		{
			log.warn("Карта c id = " + cardId + " не принадлежит клиенту c loginId = " + loginId);
			return null;
		}
		return cardLink;
	}

	/**
	 *
	 * @param expireDate дата окончания срока действия карты
	 * @return true, если время до окончания срока действия карты
	 * меньше установленного в настройках.
	 */
	public static boolean  warnUserCardJustAboutToExpire(Calendar expireDate)
	{
		try
		{
			long daysToExpire = DateHelper.calculatePeriodLeft(expireDate).getValueInDays();
			return daysToExpire < getCardConfig().getWarningPeriod();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения срока действия карты из настроек", e);
			return false;
		}
	}

	/**
	 *
	 * @return true, если у клиента есть подключенные карты
	 */
	public static boolean userHasCards()
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			try
			{
				return personData.getCards().size() > 0;
			}
			catch (BusinessException ex)
			{
				log.error("Ошибка при получении списка карт пользователя. " + ex.getMessage());
				return false;
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка определения данных клиента", e);
			return false;
		}
	}

	/**
	 * Возвращает по номеру телефона его код
	 * Сделано для услуги "Мобильный банк"
	 * @param phoneNumber номер телефона
	 * @return код телефона
	 */
	public static String getMobileBankPhoneCode(String phoneNumber)
	{
		if (StringHelper.isEmpty(phoneNumber))
			return "";
		try
		{
			return MobileBankUtils.getPhoneCode(phoneNumber);
		}
		catch (Exception ignored)
		{
			log.error("Ошибка определения кода телефона по его номеру", ignored);
			return "";
		}
	}

	/**
	 * Возвращает по номеру карты её код
	 * Сделано для услуги "Мобильный банк"
	 * @param cardNumber номер карты
	 * @return код карты
	 */
	public static String getMobileBankCardCode(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			return "";
		try
		{
			return MobileBankUtils.getCardCode(cardNumber);
		}
		catch (Exception ignored)
		{
			log.error("Ошибка определения кода карты по ее номеру", ignored);
			return "";
		}
	}

	/**
	 *  Определяем тип катры для вывода пиктограммы
	 * @param cardDescription - описание карты
	 * @return название изображения для карты (сокращенное)
	 */
	public static String getCardImageCode(String cardDescription)
	{
		try
		{
			return CardDescriptionWrapper.getImageName(cardDescription);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения названия изображения для карты", e);
			return CardDescriptionWrapper.DEFAULT_CARD_IMAGE_NAME;
		}
	}

 	 /**
 	 * Вернуть кардлинк на кредитную карту клиента,
     * если кредитных карт у клиента несколько то возвращаем первую
	 * @return
	 */
	public static CardLink getClientCreditCard()
	{

	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		try
		{
			List<CardLink> cardLinks = personData.getCards();
			return findCreditCardLink(cardLinks);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при получении списка карт пользователя. " + e.getMessage());
			return null;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при получении списка карт пользователя. " + e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * Форматирует дату истечения срока обслуживания карты
	 *
	 * @param  link из которого получаем дату
	 * @return строка содержащая дату вида MM/yyyy (включительно)
	 */
	public static String formatExpirationCardDate( CardLink link )
	{
		String date = link.getDisplayedExpireDate();
		if (StringHelper.isEmpty(date))
		{
			return null;
		}

		//date имеет формат YYMM
		String year  = date.substring(0, 2);
		String month = date.substring(2, 4);
		return month + "/20" + year;
	}


	/**
	 * Проверяет, что карта не является кредитной и/или дополнительной
	 * @param card - карта
	 * @return true - карта не является кредитной и/или дополнительной
	 */
	public static boolean isCardNotCreditNotMain(Card card)
	{
		return card != null && card.getCardType() != CardType.credit  && card.isMain();
	}

	/**
	 * Возвращает первую найденную кредитную карту для клиента из всех имеющихся у него карт (даже не видимых)
	 * @return найденную кредитную карту, либо null
	 * @throws BusinessException
	 */
	public static CardLink getCreditCardAllCards() throws BusinessException, BusinessLogicException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		return findCreditCardLink(externalService.getLinks(login, CardLink.class));
	}

	/**
	 * Ищет и возвращет первую кредитную карту среди карт
	 * @param cardLinks - списко карт
	 * @return найденная кредитная карта, либо null
	 */
	private static CardLink findCreditCardLink(List<CardLink> cardLinks)
	{
		for (CardLink cardLink : cardLinks)
		{
			if (CardType.credit == cardLink.getCard().getCardType())
				return cardLink;
		}
		return null;
	}

	/**
	 * Имеет ли клиент кредитную карту, возможно еще не выданную
	 * @return true - имеет
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static boolean hasClientActiveCreditCard(List<CardLink> cardLinks) throws BusinessException, BusinessLogicException
	{
		if(cardLinks == null)
			return false;

		for (CardLink cardLink : cardLinks)
		{
			Card card = cardLink.getCard();
			StatusDescExternalCode externalCode = card.getStatusDescExternalCode();

			//если кредитная карта приходит active, то возвращаем ее
			if (CardType.credit == card.getCardType() && card.getCardState() == CardState.active)
				return true;

			//если кредитная карта приходит blocked, то запрещать только для статусов
			//+ - КАРТОЧКА ОТКРЫТА
			//H - НЕ ВЫДАНА КЛИЕНТУ
			//X - ПЕРЕВЫП., НЕ ВЫДАНА
			if(CardType.credit == card.getCardType()
					&& card.getCardState() == CardState.blocked
					&& (externalCode == StatusDescExternalCode.S_PLUS
						|| externalCode == StatusDescExternalCode.S_H
						|| externalCode == StatusDescExternalCode.S_X))
				return true;
		}

		return false;
	}

	/**
	 * Имеет ли клиент кредитную карту, возможно еще не выданную
	 * (BUG042632)
	 * @return true - имеет
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static boolean hasClientActiveCreditCard() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = personData.getCardsAll();
		return hasClientActiveCreditCard(cardLinks);
	}

	/**
	 * Форматирует наименование карты в виде название карты + маскированный номер
	 * @param link карта
	 * @return наименование типа "Visa Classic 1234*****7890"
	 */
	public static String getFullFormatCardName( CardLink link )
	{
		return new StringBuilder(link.getName()).append(" ").append(MaskUtil.getCutCardNumber(link.getNumber())).toString();
	}

	/**
	 * @return Имеются ли у клиента карты, открытые в том же ТБ, что и счет карты с помощью которой были получены данные для авторизации в ЕРИБ.
	 */
	public static boolean haveCardsWithTBLogined()
	{
		try
		{
			if (!PersonContext.isAvailable())
				return false;

			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			String lastLogonCardTB = personData.getLogin().getLastLogonCardTB();

			if (StringHelper.isEmpty(lastLogonCardTB))
				return false;

			List<CardLink> list = PersonContext.getPersonDataProvider().getPersonData().getCards(new ActiveWithTBLoginedCardFilter());
			if (list.size() != 0)
				return true;
			else
				return false;
		}
		catch (Exception ex)
		{
			log.error("Ошибка получения карт клиента ", ex);
			return false;
		}
	}

	/**
	 * Определяет зарплатная карта, или нет
	 * @param card карта
	 * @return true - зарплатная, false - личная
	 */
	public static boolean isSalaryCard(Card card)
	{
		return ArrayUtils.contains(SALARY_TYPE, card.getUNIAccountType());
	}

	/**
	 * является ли карта Маэстро Социальная, не закрытой и не заблокированной
	 * @param cardId ИД карт-линка
	 * @return
	 */
	public static boolean isMaestroSocialNotClosedNotBlockedCard(String cardId)
	{
		if (StringHelper.isEmpty(cardId))
			return false;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			CardLink cardLink = personData.getCard(Long.valueOf(cardId));
			MaestroSocialNotClosedNotBlockedCardFilter filter = new MaestroSocialNotClosedNotBlockedCardFilter();

			return filter.evaluate(cardLink);
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска карт-линка", ex);
			return false;
		}
	}

	/**
	 * является ли карта Маэстро Социальная, не закрытой и не заблокированной
	 * @param cardId ИД карт-линка
	 * @return
	 */
	public static boolean isMaestroSocialNotClosedNotBlockedCardWithPrimaryAccountFilter(String cardId)
	{
		if (StringHelper.isEmpty(cardId))
			return false;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			CardLink cardLink = personData.getCard(Long.valueOf(cardId));
			MaestroSocialNotClosedNotBlockedCardWithPrimaryAccountFilter filter = new MaestroSocialNotClosedNotBlockedCardWithPrimaryAccountFilter();

			return filter.evaluate(cardLink);
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска карт-линка", ex);
			return false;
		}
	}

	/**
	 * Получить карту с максимальным сроком действия
	 * @param cards список карт
	 * @return карта с максимальным сроком действия
	 */
	public static CardLink getLongTermCard(List<CardLink> cards)
	{
		if (CollectionUtils.isEmpty(cards))
			return null;

		return Collections.max(cards, new Comparator<CardLink>()
		{
			public int compare(CardLink o1, CardLink o2)
			{
                if (o1.getExpireDate() != null && o2.getExpireDate() != null)
                    return o1.getExpireDate().after(o2.getExpireDate()) ? 1 : -1;
                if (o1.getExpireDate() != null)
                    return 1;
                if (o2.getExpireDate() != null)
                    return -1;
                return 0;
			}
		});
	}

	/**
	 * @return доступно ли отображение сообщения о возможности подписаться на рассылку отчетов
	 */
	public static boolean isShowAvailableEmailReportDeliveryMessage()
	{
		return getCardConfig().isShowAvailableEmailReportDeliveryMessage();
	}

	/**
	 * @return текст сообщения о возможности подписаться на рассылку отчетов
	 */
	public static String getTextAvailableEmailReportDeliveryMessage()
	{
		return getCardConfig().getTextAvailableEmailReportDeliveryMessage();
	}

	/**
	 * @return доступны ли дополнительные параметры подписки
	 */
	public static boolean isShowAdditionalReportDeliveryParameters()
	{
		return getCardConfig().isShowAdditionalReportDeliveryParameters();
	}

	/**
	 * Определяет можно хватает ли средств на карте для списания
	 * @param card карта
	 * @param chargeOffAmount сумма списания
	 * @return true - хватает, false - нет
	 * @throws BusinessException
	 */
	public static boolean hasAvailableLimit(Card card, Money chargeOffAmount) throws BusinessException
	{
		if (chargeOffAmount == null)
			return true;
		Money availableLimit = card.getAvailableLimit();
		if (availableLimit == null)
			return true;

		if (!availableLimit.getCurrency().compare(chargeOffAmount.getCurrency()))
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			CurrencyRateService service = GateSingleton.getFactory().service(CurrencyRateService.class);
			CurrencyRate rate = null;
			try
			{
				rate = service.convert(availableLimit, chargeOffAmount.getCurrency(), departmentService.findById(person.getDepartmentId()), person.getTarifPlanCodeType());
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
			if (rate == null)
				return false;
			availableLimit = new Money(rate.getToValue(), rate.getToCurrency());
		}
		return availableLimit.compareTo(chargeOffAmount) >= 0;
	}

	/**
	 * Является ли карта корпоративной
	 * Карта является корпоративной, если номер карты начинается на 4274 (visa) или 5479 (mc), при этом БИН не равен:
		 •	427417 (visa infinity)
		 •	427427 (visa platinum)
		 •	427448 (visa platinum)
		 •	427432 (visa virtual)
		 •	547927 (mc platinum)
		 •	547948 (mc platinum)
		 •	547932 (mc virtual).
	 * @param cardNumber номер карты
	 * @return true - корпоративная
	 */
	public static boolean isCorporate(String cardNumber)
	{
		String cardPrefix = cardNumber.substring(0, 4);
		String cardBIN = cardNumber.substring(0, 6);

		boolean isVisa = "4274".equals(cardPrefix);
		boolean isVisaInfinity = "427417".equals(cardBIN);
		boolean isVisaPlatinum = "427427".equals(cardBIN) || "427448".equals(cardBIN);
		boolean isVisaVirtual = "427432".equals(cardBIN);

		boolean isMC = "5479".equals(cardPrefix);
		boolean isMCPlatinum = "547927".equals(cardBIN) || "547948".equals(cardBIN);
		boolean isMCVirtual = "547932".equals(cardBIN);

		return isVisa && !isVisaInfinity && !isVisaPlatinum && !isVisaVirtual
				|| isMC && !isMCPlatinum && !isMCVirtual;
	}

	/**
	 * Является ли карта дополнительной, выпущенной клиентом для третьих лиц
	 * @param card - карта
	 * @return true - карта выпущена клиентом для третьих лиц
	 */
	public static boolean isClient2OtherCard(Card card)
	{
		boolean isAdditionalClient2Other = false;
		if (!card.isMain())
			isAdditionalClient2Other = card.getAdditionalCardType().equals(AdditionalCardType.CLIENTTOOTHER);
		return isAdditionalClient2Other;
	}

	/**
	 * Является ли карта кредитной
	 * @param card - карта
	 * @return true: карта кредитная
	 */
	public static boolean isCreditCard(Card card)
	{
		return card!=null && card.getCardType().equals(CardType.credit);
	}

	/**
	 * Получить отформатированный номер карты
	 * @param cardNumber - номер карта
	 * @return отформатированный номер карты
	 */
	public static String getFormattedCardNumber(String cardNumber)
	{
		try
		{
			if (StringHelper.isEmpty(cardNumber))
				return "";

			return cardNumber.replaceAll(CARD_FORMAT_REGEX, CARD_FORMAT_MASK);
		}
		catch (Exception e)
		{
			log.error("Ошибка форматирования номера карты", e);
			return "";
		}
	}

	/**
	 * Выбрать из списка карт доступные для оплаты услуги ЕРМБ
	 * @param cardLinks все карты
	 * @return потенциально платежные карты ЕРМБ
	 */
	public static List<CardLink> filterPotentialErmbPaymentCards(Collection<CardLink> cardLinks)
	{
		List<CardLink> result = new ArrayList<CardLink>(cardLinks.size());
		CollectionUtils.select(cardLinks, new ErmbPaymentCardLinkFilter(), result);
		return result;
	}
}
