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
	 * ���������� ����� �����, ������� �������� �� ������������
	 *  (carlink.getName()),
	 * ���� � �������� (cardlink.getCard().getDescription()) (VISA, MasterCard � �.�.)
	 * @param cardlink - ����-���� ���� null
	 * @return ����� �����, ���� � ��������, ���� �����
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
			log.error("������ ����������� ������ �����", e);
			return "";
		}
	}

	/**
	 * ���������� ����-���� �� ������ �����
	 * @param cardNumber - ����� �����
	 * @return ����-���� ���� null
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
				log.warn("�� ���� ����� card-link �� ������ " + cardNumber);

			return cardlink;
		}
		catch (Exception ex)
		{
			log.error("������ ������ ����-�����", ex);
			return null;
		}
	}

	/**
	 * ���������� ����-���� �� ������ ����� ��� ����� ���������
	 * @param cardNumber - ����� �����
	 * @return ����-���� ���� null
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
				log.warn("�� ���� ����� card-link �� ������ " + cardNumber);
			}
			return cardlink;
		}
		catch (Exception ex)
		{
			log.error("������ ������ ����-�����", ex);
			return null;
		}
	}

	/**
	 * ���������� �� ����� � ������ ������
	 * @param list ������ ������ ����
	 * @param cardNumber ����� �����
	 * @return true = ����������
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
	 * ���������� ���� ���� �� �������������� �����
	 * @param id �������������
	 * @return ���� ����
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
				log.warn("�� ���� ����� card-link �� id= " + id);
			}
			return cardLink;
		}
		catch (Exception ex)
		{
			log.error("������ ������ ����-�����", ex);
			return null;
		}
	}

	/**
	 * ���������� ���� ���� �� �������������� ����� � ��������� �������������� ����� �������
	 * @param cardId �������������
	 * @return ���� ����
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
			log.warn("����� c id = " + cardId + " �� ����������� ������� c loginId = " + loginId);
			return null;
		}
		return cardLink;
	}

	/**
	 *
	 * @param expireDate ���� ��������� ����� �������� �����
	 * @return true, ���� ����� �� ��������� ����� �������� �����
	 * ������ �������������� � ����������.
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
			log.error("������ ����������� ����� �������� ����� �� ��������", e);
			return false;
		}
	}

	/**
	 *
	 * @return true, ���� � ������� ���� ������������ �����
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
				log.error("������ ��� ��������� ������ ���� ������������. " + ex.getMessage());
				return false;
			}
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ �������", e);
			return false;
		}
	}

	/**
	 * ���������� �� ������ �������� ��� ���
	 * ������� ��� ������ "��������� ����"
	 * @param phoneNumber ����� ��������
	 * @return ��� ��������
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
			log.error("������ ����������� ���� �������� �� ��� ������", ignored);
			return "";
		}
	}

	/**
	 * ���������� �� ������ ����� � ���
	 * ������� ��� ������ "��������� ����"
	 * @param cardNumber ����� �����
	 * @return ��� �����
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
			log.error("������ ����������� ���� ����� �� �� ������", ignored);
			return "";
		}
	}

	/**
	 *  ���������� ��� ����� ��� ������ �����������
	 * @param cardDescription - �������� �����
	 * @return �������� ����������� ��� ����� (�����������)
	 */
	public static String getCardImageCode(String cardDescription)
	{
		try
		{
			return CardDescriptionWrapper.getImageName(cardDescription);
		}
		catch (Exception e)
		{
			log.error("������ ����������� �������� ����������� ��� �����", e);
			return CardDescriptionWrapper.DEFAULT_CARD_IMAGE_NAME;
		}
	}

 	 /**
 	 * ������� �������� �� ��������� ����� �������,
     * ���� ��������� ���� � ������� ��������� �� ���������� ������
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
			log.error("������ ��� ��������� ������ ���� ������������. " + e.getMessage());
			return null;
		}
		catch (BusinessLogicException e)
		{
			log.error("������ ��� ��������� ������ ���� ������������. " + e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * ����������� ���� ��������� ����� ������������ �����
	 *
	 * @param  link �� �������� �������� ����
	 * @return ������ ���������� ���� ���� MM/yyyy (������������)
	 */
	public static String formatExpirationCardDate( CardLink link )
	{
		String date = link.getDisplayedExpireDate();
		if (StringHelper.isEmpty(date))
		{
			return null;
		}

		//date ����� ������ YYMM
		String year  = date.substring(0, 2);
		String month = date.substring(2, 4);
		return month + "/20" + year;
	}


	/**
	 * ���������, ��� ����� �� �������� ��������� �/��� ��������������
	 * @param card - �����
	 * @return true - ����� �� �������� ��������� �/��� ��������������
	 */
	public static boolean isCardNotCreditNotMain(Card card)
	{
		return card != null && card.getCardType() != CardType.credit  && card.isMain();
	}

	/**
	 * ���������� ������ ��������� ��������� ����� ��� ������� �� ���� ��������� � ���� ���� (���� �� �������)
	 * @return ��������� ��������� �����, ���� null
	 * @throws BusinessException
	 */
	public static CardLink getCreditCardAllCards() throws BusinessException, BusinessLogicException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		return findCreditCardLink(externalService.getLinks(login, CardLink.class));
	}

	/**
	 * ���� � ��������� ������ ��������� ����� ����� ����
	 * @param cardLinks - ������ ����
	 * @return ��������� ��������� �����, ���� null
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
	 * ����� �� ������ ��������� �����, �������� ��� �� ��������
	 * @return true - �����
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

			//���� ��������� ����� �������� active, �� ���������� ��
			if (CardType.credit == card.getCardType() && card.getCardState() == CardState.active)
				return true;

			//���� ��������� ����� �������� blocked, �� ��������� ������ ��� ��������
			//+ - �������� �������
			//H - �� ������ �������
			//X - �������., �� ������
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
	 * ����� �� ������ ��������� �����, �������� ��� �� ��������
	 * (BUG042632)
	 * @return true - �����
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
	 * ����������� ������������ ����� � ���� �������� ����� + ������������� �����
	 * @param link �����
	 * @return ������������ ���� "Visa Classic 1234*****7890"
	 */
	public static String getFullFormatCardName( CardLink link )
	{
		return new StringBuilder(link.getName()).append(" ").append(MaskUtil.getCutCardNumber(link.getNumber())).toString();
	}

	/**
	 * @return ������� �� � ������� �����, �������� � ��� �� ��, ��� � ���� ����� � ������� ������� ���� �������� ������ ��� ����������� � ����.
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
			log.error("������ ��������� ���� ������� ", ex);
			return false;
		}
	}

	/**
	 * ���������� ���������� �����, ��� ���
	 * @param card �����
	 * @return true - ����������, false - ������
	 */
	public static boolean isSalaryCard(Card card)
	{
		return ArrayUtils.contains(SALARY_TYPE, card.getUNIAccountType());
	}

	/**
	 * �������� �� ����� ������� ����������, �� �������� � �� ���������������
	 * @param cardId �� ����-�����
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
			log.error("������ ������ ����-�����", ex);
			return false;
		}
	}

	/**
	 * �������� �� ����� ������� ����������, �� �������� � �� ���������������
	 * @param cardId �� ����-�����
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
			log.error("������ ������ ����-�����", ex);
			return false;
		}
	}

	/**
	 * �������� ����� � ������������ ������ ��������
	 * @param cards ������ ����
	 * @return ����� � ������������ ������ ��������
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
	 * @return �������� �� ����������� ��������� � ����������� ����������� �� �������� �������
	 */
	public static boolean isShowAvailableEmailReportDeliveryMessage()
	{
		return getCardConfig().isShowAvailableEmailReportDeliveryMessage();
	}

	/**
	 * @return ����� ��������� � ����������� ����������� �� �������� �������
	 */
	public static String getTextAvailableEmailReportDeliveryMessage()
	{
		return getCardConfig().getTextAvailableEmailReportDeliveryMessage();
	}

	/**
	 * @return �������� �� �������������� ��������� ��������
	 */
	public static boolean isShowAdditionalReportDeliveryParameters()
	{
		return getCardConfig().isShowAdditionalReportDeliveryParameters();
	}

	/**
	 * ���������� ����� ������� �� ������� �� ����� ��� ��������
	 * @param card �����
	 * @param chargeOffAmount ����� ��������
	 * @return true - �������, false - ���
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
	 * �������� �� ����� �������������
	 * ����� �������� �������������, ���� ����� ����� ���������� �� 4274 (visa) ��� 5479 (mc), ��� ���� ��� �� �����:
		 �	427417 (visa infinity)
		 �	427427 (visa platinum)
		 �	427448 (visa platinum)
		 �	427432 (visa virtual)
		 �	547927 (mc platinum)
		 �	547948 (mc platinum)
		 �	547932 (mc virtual).
	 * @param cardNumber ����� �����
	 * @return true - �������������
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
	 * �������� �� ����� ��������������, ���������� �������� ��� ������� ���
	 * @param card - �����
	 * @return true - ����� �������� �������� ��� ������� ���
	 */
	public static boolean isClient2OtherCard(Card card)
	{
		boolean isAdditionalClient2Other = false;
		if (!card.isMain())
			isAdditionalClient2Other = card.getAdditionalCardType().equals(AdditionalCardType.CLIENTTOOTHER);
		return isAdditionalClient2Other;
	}

	/**
	 * �������� �� ����� ���������
	 * @param card - �����
	 * @return true: ����� ���������
	 */
	public static boolean isCreditCard(Card card)
	{
		return card!=null && card.getCardType().equals(CardType.credit);
	}

	/**
	 * �������� ����������������� ����� �����
	 * @param cardNumber - ����� �����
	 * @return ����������������� ����� �����
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
			log.error("������ �������������� ������ �����", e);
			return "";
		}
	}

	/**
	 * ������� �� ������ ���� ��������� ��� ������ ������ ����
	 * @param cardLinks ��� �����
	 * @return ������������ ��������� ����� ����
	 */
	public static List<CardLink> filterPotentialErmbPaymentCards(Collection<CardLink> cardLinks)
	{
		List<CardLink> result = new ArrayList<CardLink>(cardLinks.size());
		CollectionUtils.select(cardLinks, new ErmbPaymentCardLinkFilter(), result);
		return result;
	}
}
