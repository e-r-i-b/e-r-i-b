package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.bankroll.CardBonusSign;
import com.rssl.phizic.gate.bankroll.CardLevel;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.types.CardBonusSignWrapper;
import com.rssl.phizicgate.esberibgate.types.CardLevelWrapper;
import com.rssl.phizicgate.esberibgate.types.CardTypeWrapper;
import com.rssl.phizicgate.esberibgate.types.LimitType;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.math.BigDecimal;
import java.util.*;

/**
 @author Pankin
 @ created 24.11.2010
 @ $Author$
 @ $Revision$
 */
public class CardResponseHelper extends BaseResponseHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	private static final SimpleService simpleService = new SimpleService();

	/*
	��������������� ���� ��� ����, ���� �� "����"-�������� ����������� ���������� ��� (���������, ���������,
	������������) ��� ������ �������� (GFL, ��������� ��������� ����������, �������, �������) �� ����� � ��� �� �����
	key - ����� �����, value - ��� �����
	*/
	private static final Map<String, String> staticCardTypes  = new HashMap<String, String>();
	private static final Map<String, String> staticCardLevels = new HashMap<String, String>();
	private static final Map<String, String> staticCardBonusSignes = new HashMap<String, String>();

	private static final Map<String, String> staticCurrencies = new HashMap<String, String>();
	/*
	���������� ��� ���� ����� (�������� ��� ��������������). ���� ���������� ������ ����� �������� ��������,
	�� ���� ����� ����������� ��������� ���������� �� ������ ������������ �����, ����� ��� �� ���������.
	key - ����� �����, value - ����� �������� ����� ��� �������������� ���� (���� null, ����� ���� ��������)
	 */
	private static final Map<String, String> staticIsMain = new HashMap<String, String>();
	//������ ����� ����������� ����
	private static final List<String> virtualCardNumber = new ArrayList<String>();

	private static final String CLIENT_TO_CLIENT = "Client2Client";
	private static final String CLIENT_TO_OTHER = "Client2Other";
	private static final String OTHER_TO_CLIENT = "Other2Client";

	static
	{
		virtualCardNumber.add("427432");
		virtualCardNumber.add("547932");

		staticCurrencies.put("RUR", "810");
		staticCurrencies.put("EUR", "978");
		staticCurrencies.put("USD", "840");
	}

	private static final List<String> filterBlockedCards = new ArrayList<String>();

	static
	{
		filterBlockedCards.add("N - ������� ������");
		filterBlockedCards.add("Y - ����� / ����������");
		filterBlockedCards.add("y - NO PBT - ����� / ����������");
	}

	/*
	  ��������� ����� ��� GFL
	 */
	public void buildCards(BankAcctInqRs_Type bankAcctInqRs, Login login, boolean onlyCardWay) throws BusinessException, BusinessLogicException
	{
		Long loginId = (login==null) ? null : login.getId();
		List<CardLink> cards = (login==null) ? null : resourceService.getLinks(login, CardLink.class);
	    boolean isNew = (cards == null || cards.isEmpty());

		Random rand = new Random();
		int j = rand.nextInt(5);
		boolean additional_card = (j == 1);

		//��������, �� ��������� � ������� ����� 2 �����
		int count = 0;
		if (isNightBuildsEnabled() && isNew)
		{
			count = 10;
		}
		else
		{
			count = isNew ? 10 : cards.size();
		}

		CardAcctRec_Type[] cardAcctRecs = new CardAcctRec_Type[count + 1];
		String mainCardNumber = null;
		boolean isMainCard = false;
		String systemId = "urn:sbrfsystems:99-way";
		for (int i = 0; i < count; i++)
		{
			CardAcctRec_Type cardAcctRec = new CardAcctRec_Type();
			cardAcctRec.setBankAcctStatus(getCardState());

			/*
			 * ���� � ������������ ��� ����� � ������� ������������� ��� ����� ����� �� �������� ��������� ��� ������ ��������
			 * ������� ����� � ����� �������, � ��������� ������ ������ �������� �����.
			 */
			CardLink cardLink = isNew ? getLastLogonCardLink(cards, login, i) : cards.get(i);
			CardAcctId_Type cardAcctId = createPartlyMockCard(cardLink, loginId, systemId);
//  		������������� ����� � ������  ���������������,  ���� ���������  �������� ������������� �� ��� � ������  �����
//			cardAcctRec.setBankAcctStatus(new BankAcctStatus_Type("Blocked", "���������������"));
//			cardAcctId.setEndDt(DateHelper.formatDateToString(DateHelper.getPreviousWeek(DateHelper.getPreviousYear(DateHelper.getCurrentDate()))));

			if (!onlyCardWay)
				systemId = "urn:sbrfsystems:40-cod";
			cardAcctRec.setCardAcctId(cardAcctId);
			cardAcctRec.setBankInfo(getMockBankInfo());

			if (isNew && additional_card)
			{
				isMainCard = (mainCardNumber == null);
				//������, �������� � ����, ����� - ��������, ��������� - �������������� � ���.
				if (isMainCard)
				{
					//����� ��������
					mainCardNumber = cardAcctId.getCardNum();
					staticIsMain.put(mainCardNumber, null);
					//����������� ��� ����������� �������
					Boolean get = rand.nextBoolean();
					if (get)
						cardAcctRec.setOTPRestriction(new OTPRestriction_Type(get, null));
					else
						cardAcctRec.setOTPRestriction(new OTPRestriction_Type(get, rand.nextBoolean()));
				}
				else
				{
					staticIsMain.put(cardAcctId.getCardNum(), mainCardNumber);
					//����������� ��� ����������� �������
					cardAcctRec.setOTPRestriction(null);
				}
			}
			else
			{
				isMainCard = (isNew || cardLink.isMain());
				if (isNew)
				{
					//����� ��������
					staticIsMain.put(cardAcctId.getCardNum(), null);
					//����������� ��� ����������� �������
					Boolean get = rand.nextBoolean();
					if (get)
						cardAcctRec.setOTPRestriction(new OTPRestriction_Type(get, null));
					else
						cardAcctRec.setOTPRestriction(new OTPRestriction_Type(get, rand.nextBoolean()));
				}
				else
				{
					if (cardLink.isMain())
					{
						staticIsMain.put(cardAcctId.getCardNum(), null);
						//����������� ��� ����������� �������
						cardAcctRec.setOTPRestriction(new OTPRestriction_Type(cardLink.getOTPGet()!=null ? cardLink.getOTPGet():false, cardLink.getOTPUse()));
					}
					else
					{
						mainCardNumber = cardLink.getMainCardNumber();
						cardAcctRec.setCardAcctInfo(getCardAcctInfo(StringHelper.getEmptyIfNull(cardLink.getMainCardNumber())));
						staticIsMain.put(cardAcctId.getCardNum(), StringHelper.getEmptyIfNull(cardLink.getMainCardNumber()));
						//����������� ��� ����������� �������
						cardAcctRec.setOTPRestriction(null);
					}
				}
			}
			//������ �ardAcctInfo �������� ��� ���� ����, � �� ������ ��� ��������������
			cardAcctRec.setCardAcctInfo(getCardAcctInfo(!isMainCard ? mainCardNumber : null));
			cardAcctRec.setAcctBal(getLimits(cardAcctId));

			cardAcctRecs[i] = cardAcctRec;
		}

		// ������� �������� �� ����������� ��� ���������� �� ������� �����.
		CardAcctRec_Type cardAcctRec = new CardAcctRec_Type();
		cardAcctRec.setBankAcctStatus(new BankAcctStatus_Type("Blocked", filterBlockedCards.get(rand.nextInt(3))));
		CardAcctId_Type cardAcctId = createPartlyMockCard(null, loginId, systemId);

		cardAcctRec.setCardAcctId(cardAcctId);
		cardAcctRec.setBankInfo(getMockBankInfo());
		cardAcctRec.setAcctBal(getLimits(cardAcctId));

		cardAcctRecs[cardAcctRecs.length - 1] = cardAcctRec;

		bankAcctInqRs.setCardAcctRec(cardAcctRecs);
	}

	private CardLink getLastLogonCardLink(List<CardLink> cards, Login login, int i)
	{
		CardLink lastLogonCardLink = null;
		if (cards != null && login != null && i == 0)
		{
			String lastCardNumber = login.getLastLogonCardNumber();

			for (CardLink link : cards)
			{
				if (link.getNumber().equals(lastCardNumber))
				{
					lastLogonCardLink = link;
					break;
				}
			}

			if (lastLogonCardLink == null && !StringHelper.isEmpty(lastCardNumber))
			{
				lastLogonCardLink = new CardLink();
				lastLogonCardLink.setNumber(lastCardNumber);
			}
		}
		return lastLogonCardLink;
	}

	/*
	��������� ��������� ���������� �� �����
	*/
	public IFXRs_Type createCardAcctDInqRs(IFXRq_Type parameters)
	{
		CardAcctDInqRq_Type cardAcctDInqRq = parameters.getCardAcctDInqRq();

		CardAcctDInqRs_Type cardAcctDInqRs = new CardAcctDInqRs_Type();

		cardAcctDInqRs.setRqUID(cardAcctDInqRq.getRqUID());
		cardAcctDInqRs.setRqTm(getRqTm());
		cardAcctDInqRs.setOperUID(cardAcctDInqRq.getOperUID());

		CardAcctRec_Type[] cardAcctRecs = new CardAcctRec_Type[cardAcctDInqRq.getCardInfo().length];
		for (int i = 0; i < cardAcctDInqRq.getCardInfo().length; i++)
		{
			Status_Type responseStatus = getStatus();
			CardAcctRec_Type cardAcctRec = new CardAcctRec_Type();
			cardAcctRec.setStatus(responseStatus);
			String cardNumber = cardAcctDInqRq.getCardInfo()[i].getCardAcctId().getCardNum();
			//�������� CardAcctId ���� � ������ ������, ���� ����� ���� �������� ����� �����, �� ������� ��������� ������
			CardAcctId_Type cardAcctId = createMockCard(cardNumber);
			cardAcctId.setSystemId(null);//��� �� ��������
			cardAcctId.setCardBonusSign(getCardBonusSign(cardNumber));
			cardAcctId.setCardLevel(getCardLevel(cardNumber));
//			������������� ����� � ������  ���������������,  ���� ���������  �������� ������������� �� ��� � ������  �����
//			cardAcctRec.setBankAcctStatus(new BankAcctStatus_Type("Blocked", "���������������"));
//			cardAcctId.setEndDt(DateHelper.formatDateToString(DateHelper.getPreviousWeek(DateHelper.getPreviousYear(DateHelper.getCurrentDate()))));
			cardAcctRec.setCardAcctId(cardAcctId);
			cardAcctRec.setCardAcctInfo(getCardAcctInfo(null));
			if (responseStatus.getStatusCode() == 0L)
			{
				cardAcctRec.setBankAcctStatus(getCardState());
				cardAcctRec.setAcctBal(getLimits(cardAcctId));
			}
			cardAcctRecs[i] = cardAcctRec;
		}

		cardAcctDInqRs.setBankAcctRec(cardAcctRecs);

		IFXRs_Type response = new IFXRs_Type();
		response.setCardAcctDInqRs(cardAcctDInqRs);

		return response;
	}

	/*
	��������� ������� �� �����
	*/
	public IFXRs_Type createCardAcctInqRs(IFXRq_Type parameters)
	{
		AcctInqRq_Type cardAcctInqRq = parameters.getAcctInqRq();
		AcctInqRs_Type cardAcctInqRs = new AcctInqRs_Type();
		cardAcctInqRs.setRqUID(cardAcctInqRq.getRqUID());
		cardAcctInqRs.setRqTm(getRqTm());
		cardAcctInqRs.setOperUID(cardAcctInqRq.getOperUID());

		Status_Type responseStatus = getStatus();
		cardAcctInqRs.setStatus(responseStatus);

		if (responseStatus.getStatusCode() == 0L)
		{
			CardAcctId_Type cardAcctId = cardAcctInqRq.getCardAcctId();

			AcctInqRec_Type acctInqRec = new AcctInqRec_Type();

			CardAcctRes_Type cardAcctRes = new CardAcctRes_Type();
			cardAcctRes.setCardAcctId(createMockCard(cardAcctId.getCardNum()));
			cardAcctRes.setAcctBal(getLimits(cardAcctId));
			acctInqRec.setCardAcctRes(new CardAcctRes_Type[]{cardAcctRes});

			cardAcctInqRs.setAcctInqRec(acctInqRec);
		}
		IFXRs_Type response = new IFXRs_Type();
		response.setAcctInqRs(cardAcctInqRs);

		return response;
	}

	/*
	�������� ������ �� e-mail
	 */
	public IFXRs_Type createBankAcctStmtImgInqRs(IFXRq_Type parameters)
	{
		BankAcctStmtImgInqRq_Type bankAcctStmtImgInqRq = parameters.getBankAcctStmtImgInqRq();
		BankAcctStmtImgInqRs_Type bankAcctStmtImgInqRs = new BankAcctStmtImgInqRs_Type();

		bankAcctStmtImgInqRs.setRqUID(bankAcctStmtImgInqRq.getRqUID());
		bankAcctStmtImgInqRs.setRqTm(getRqTm());
		bankAcctStmtImgInqRs.setOperUID(bankAcctStmtImgInqRq.getOperUID());
		bankAcctStmtImgInqRs.setStatus(getStatus());

		IFXRs_Type response = new IFXRs_Type();
		response.setBankAcctStmtImgInqRs(bankAcctStmtImgInqRs);

		return response;
	}

	/*
	��������� ����������� ������� �� �����
	*/
	public IFXRs_Type createCCAcctExtStmtInqRs(IFXRq_Type parameters)
	{
		CCAcctExtStmtInqRq_Type cCAcctExtStmtInqRq = parameters.getCCAcctExtStmtInqRq();
		CCAcctExtStmtInqRs_Type cCAcctExtStmtInqRs = new CCAcctExtStmtInqRs_Type();

		cCAcctExtStmtInqRs.setRqUID(cCAcctExtStmtInqRq.getRqUID());
		cCAcctExtStmtInqRs.setRqTm(getRqTm());
		cCAcctExtStmtInqRs.setOperUID(cCAcctExtStmtInqRq.getOperUID());
		cCAcctExtStmtInqRs.setStatus(getStatus());

		CCAcctExtStmtInqRs_TypeCardAcctRec[] cardAcctRecs = new CCAcctExtStmtInqRs_TypeCardAcctRec[cCAcctExtStmtInqRq.getCardInfo().length];
		for (int i = 0; i < cCAcctExtStmtInqRq.getCardInfo().length; i++)
		{
			CCAcctExtStmtInqRs_TypeCardAcctRec cardAcctRec = new CCAcctExtStmtInqRs_TypeCardAcctRec();

			Status_Type responseStatus = getStatus();
			cardAcctRec.setStatus(responseStatus);

			CardAcctId_Type cardAcctId = cCAcctExtStmtInqRq.getCardInfo()[i].getCardAcctId();

			cardAcctRec.setCardAcctId(createMockCard(cardAcctId.getCardNum()));
			cardAcctRec.setAcctBal(getLimits(cardAcctRec.getCardAcctId()));

			if (responseStatus.getStatusCode() == 0L)
			{
				cardAcctRec.setCCAcctStmtRec(createMockOperations((int) cCAcctExtStmtInqRq.getOpCount(), cardAcctRec.getCardAcctId().getAcctCur()));
			}

			cardAcctRecs[i] = cardAcctRec;
		}

		cCAcctExtStmtInqRs.setCardAcctRec(cardAcctRecs);

		IFXRs_Type response = new IFXRs_Type();
		response.setCCAcctExtStmtInqRs(cCAcctExtStmtInqRs);
		return response;
	}

	/*
	������ �� ���������� �����
	*/
	public IFXRs_Type createCardBlockRs(IFXRq_Type parameters)
	{
		CardBlockRq_Type cardBlockRq = parameters.getCardBlockRq();
		CardBlockRs_Type cardBlockRs = new CardBlockRs_Type();

		cardBlockRs.setRqUID(cardBlockRq.getRqUID());
		cardBlockRs.setRqTm(getRqTm());
		cardBlockRs.setOperUID(cardBlockRq.getOperUID());
		cardBlockRs.setStatus(getStatus());

		IFXRs_Type response = new IFXRs_Type();
		response.setCardBlockRs(cardBlockRs);

		return response;
	}

	/*
	������ �� ���������� �����
	*/
	public IFXRs_Type createCardReissuePlaceRs(IFXRq_Type parameters)
	{
		CardReissuePlaceRq_Type cardReissuePlaceRq = parameters.getCardReissuePlaceRq();
		CardReissuePlaceRs_Type cardReissuePlaceRs = new CardReissuePlaceRs_Type();

		cardReissuePlaceRs.setRqUID(cardReissuePlaceRq.getRqUID());
		cardReissuePlaceRs.setRqTm(getRqTm());
		cardReissuePlaceRs.setSPName(cardReissuePlaceRq.getSPName());
		cardReissuePlaceRs.setSystemId(cardReissuePlaceRq.getSystemId());
		cardReissuePlaceRs.setStatus(getStatus());

		IFXRs_Type response = new IFXRs_Type();
		response.setCardReissuePlaceRs(cardReissuePlaceRs);

		return response;
	}

	/**
	 * ��������� ���������� �� �������������� ������
	 * @param ifxRq ������
	 * @return ���������� �� �������������� ������
	 */
	public IFXRs_Type createCardAdditionalInfoRs(IFXRq_Type ifxRq)
	{
		Random rand = new Random();
		CardAdditionalInfoRq_Type cardAdditionalInfoRq = ifxRq.getCardAdditionalInfoRq();
		CardAdditionalInfoRs_Type cardAdditionalInfoRs = new CardAdditionalInfoRs_Type();

		cardAdditionalInfoRs.setRqUID(cardAdditionalInfoRq.getRqUID());
		cardAdditionalInfoRs.setRqTm(getRqTm());
		cardAdditionalInfoRs.setOperUID(cardAdditionalInfoRq.getOperUID());
		// ������ ���������������, ���� �� ����� AXIS (� ����� ����� ���, �� �� XSD ����������)
		cardAdditionalInfoRs.setStatus(getStatus());

		List<AdditionalCardInfo_Type> additionalCardInfos = new ArrayList<AdditionalCardInfo_Type>(0);
		for (CardInfo_Type cardInfo : cardAdditionalInfoRq.getCardInfo())
		{
			CardAcctId_Type cardAcctId = cardInfo.getCardAcctId();
			String cardNum = cardAcctId.getCardNum();
			AdditionalCardInfo_Type additionalCardInfo = new AdditionalCardInfo_Type();
			additionalCardInfo.setSystemId(cardAcctId.getSystemId());
			additionalCardInfo.setCardNum(cardNum);

			// ���������� ������ ���. ���� � ���������������
			List<String> additionalCards = new ArrayList<String>(0);
			if (staticIsMain.containsValue(cardNum))
			{
				for (String additionalCard : staticIsMain.keySet())
				{
					if (StringHelper.equals(cardNum, staticIsMain.get(additionalCard)))
					{
						additionalCards.add(additionalCard);
					}
				}
			}

			List<AdditionalCardInfo_TypeCards> additionalCardInfoCards = new ArrayList<AdditionalCardInfo_TypeCards>(0);
			for (String additionalCard : additionalCards)
			{
				AdditionalCardInfo_TypeCards additionalCardInfoCard = new AdditionalCardInfo_TypeCards();
				additionalCardInfoCard.setCardNum(additionalCard);
				additionalCardInfoCard.setSystemId("urn:sbrfsystems:40-cod");
				if (rand.nextBoolean())
					additionalCardInfoCard.setAdditionalCard(CLIENT_TO_CLIENT);
				else
					additionalCardInfoCard.setAdditionalCard(CLIENT_TO_OTHER);
				additionalCardInfoCard.setBankInfo(cardAcctId.getBankInfo());
				additionalCardInfoCards.add(additionalCardInfoCard);
			}

			additionalCardInfo.setCards(additionalCardInfoCards.toArray(
					new AdditionalCardInfo_TypeCards[additionalCardInfoCards.size()]));
			additionalCardInfo.setStatus(getStatus());
			additionalCardInfos.add(additionalCardInfo);
		}
		cardAdditionalInfoRs.setAdditionalCardInfo(additionalCardInfos.toArray(
				new AdditionalCardInfo_Type[additionalCardInfos.size()]));

		IFXRs_Type response = new IFXRs_Type();
		response.setCardAdditionalInfoRs(cardAdditionalInfoRs);

		return response;
	}

	/*
	������� ����������� ������ �� ������
	*/
	public void clearStoredCards()
	{
		staticCardTypes.clear();
		staticIsMain.clear();
		staticCardBonusSignes.clear();
		staticCardLevels.clear();
	}

	private BankAcctStatus_Type getCardState()
	{
		Random rand = new Random();
		int i = rand.nextInt(20);
		switch (i) {
		case 0:
		case 3:
            return new BankAcctStatus_Type("Blocked", "S - ��������");
		case 1:
		case 4:
			return new BankAcctStatus_Type("Blocked", "L - ��������");
		case 2:
			return new BankAcctStatus_Type("Blocked", "K - �������� ��������������");
		case 5:
		case 6:
			return new BankAcctStatus_Type("Blocked", "B - ��������");
		case 7:
		case 8:
			return new BankAcctStatus_Type("Blocked", "C - �������������");
		case 9:
		case 12:
			return new BankAcctStatus_Type("Blocked", "P - ���������������� ���");
		case 10:
		case 11:
			return new BankAcctStatus_Type("Stoped", "H - �� ������ �������");
		case 13:
		case 14:
			return new BankAcctStatus_Type("Blocked", "Y - �����/����������");
		case 15:
		case 16:
			return new BankAcctStatus_Type("Blocked", "J - �����.�������������� �� ��. �����");
		default:
			return new BankAcctStatus_Type("Active", "+ - �������� �������");
	}
	}

	private CardAcctInfo_Type getCardAcctInfo(String mainCardNumber)
	{
		CardAcctInfo_Type cardAcctInfo = new CardAcctInfo_Type();
        Calendar nextReportDate = Calendar.getInstance();
        nextReportDate.add(Calendar.WEEK_OF_MONTH, 1);
        cardAcctInfo.setNextReportDate(getStringDate(nextReportDate));
		if (mainCardNumber != null)
		{
			Random rand = new Random();
			cardAcctInfo.setMainCard(mainCardNumber);
			//��� �������������� ����� ������������ ��������� �������
			int i = rand.nextInt(10);
			if (0 <= i && i <= 3)
			{
				cardAcctInfo.setAdditionalCard(CLIENT_TO_CLIENT);
			}
			else if (4 <= i && i <= 6)
			{
				cardAcctInfo.setAdditionalCard(CLIENT_TO_OTHER);
			}
			else if (7 <= i && i <= 10)
			{
				cardAcctInfo.setAdditionalCard(OTHER_TO_CLIENT);
			}
		}
		cardAcctInfo.setEndDtForWay("1211");

		return cardAcctInfo;
	}

	/*
	������� �� �����
	 */
	private AcctBal_Type[] getLimits(CardAcctId_Type cardAcctId)
	{
		boolean isVirtual = virtualCardNumber.contains(cardAcctId.getCardNum().substring(0,6));
		BigDecimal amount = new BigDecimal(Math.random()*10000);
		//�� ����������� ����� ������� ������ ���� �����
		int count = 1;
		//3 ������� �����, ���� ����� ����������
		if (!isVirtual)
			count += 2;
		String cardType = cardAcctId.getCardType();
		String currency = cardAcctId.getAcctCur();
		if (currency == null)
			currency = "RUR";
		CardType externalCardType = CardTypeWrapper.getCardType(cardType);
		//���� ����� ��������� ��� ������������ � �� �����������, �� + ��� 4
		boolean isCreditOrOverdraft = externalCardType != null && (externalCardType.equals(CardType.credit) || externalCardType.equals(CardType.overdraft));
		if (isCreditOrOverdraft && !isVirtual)
			count += 4;
		AcctBal_Type[] limits = new AcctBal_Type[count];

		//��������� ��������� ����� �� �����
		AcctBal_Type limit = new AcctBal_Type();
		limit.setBalType(LimitType.Avail.toString());
		limit.setCurAmt(amount);
		limit.setAcctCur(currency);
		limits[0] = limit;

		if (isVirtual)
		{
			return limits;
		}

		//��������� ����� ��� ��������� ��������
		limit = new AcctBal_Type();
		limit.setBalType(LimitType.AvailCash.toString());
		limit.setCurAmt(getRandomDecimal());
		limit.setAcctCur(currency);
		limits[1] = limit;

		//��������� ����� ��� ������ ������� � �����
		limit = new AcctBal_Type();
		limit.setBalType(LimitType.AvailPmt.toString());
		limit.setCurAmt(getRandomDecimal());
		limit.setAcctCur(currency);
		limits[2] = limit;

		if (isCreditOrOverdraft)
		{
			//����� �������������
			limit = new AcctBal_Type();
			limit.setBalType(LimitType.Debt.toString());
			limit.setCurAmt(getRandomDecimal());
			limit.setAcctCur(currency);
			limits[3] = limit;

			//����� ������������ �������
			limit = new AcctBal_Type();
			limit.setBalType(LimitType.MinPmt.toString());
			limit.setCurAmt(getRandomDecimal());
			limit.setAcctCur(currency);
			limits[4] = limit;

			//���� ���������� ������ � ��������������� ������ ����������� ���������
			limit = new AcctBal_Type();
			limit.setBalType(LimitType.CR_LIMIT.toString());
			limit.setCurAmt(getRandomDecimal());
			limit.setAcctCur(currency);
			limits[5] = limit;

			//����������� ������ ��������� ��� ����� �������� ���������� liability-����������
			limit = new AcctBal_Type();
			limit.setBalType(LimitType.OWN_BALANCE.toString());
			limit.setCurAmt(getRandomDecimal());
			limit.setAcctCur(currency);
			limits[6] = limit;
		}
		return limits;
	}

	/**
	 * ��������� ��������� ���� �����.
	 * @param cardNumber - ����� �����
	 * @return ��� �����. �� ��������� - MasterCard Business
	 */
	private String getCardLevel(String cardNumber)
	{
		if (staticCardLevels.containsKey(cardNumber))
		{
			return staticCardLevels.get(cardNumber);
		}
		Map<String, CardLevel> cardLevels = CardLevelWrapper.getCardLevel();
		Set<String> levels = cardLevels.keySet();
		Random rand = new Random();
		int index = rand.nextInt(levels.size());
		int count = 0;
		for (String cardLevel : levels)
		{
			if (count == index)
			{
				staticCardLevels.put(cardNumber, cardLevel);
				return cardLevel;
			}
			count++;
		}
		staticCardLevels.put(cardNumber, "MB");
		return "MB";
	}

	/**
	 * ��������� ��������� �������������� ����� � �������� ���������.
	 * @return �������������� ����� � �������� ���������. �� ��������� Y -����������
	 */
	private String getCardBonusSign(String cardNumber)
	{
		if (staticCardBonusSignes.containsKey(cardNumber))
		{
			return staticCardBonusSignes.get(cardNumber);
		}
		Map<String, CardBonusSign> cardBonusSignes = CardBonusSignWrapper.getCardBonusSign();
		Set<String> bonusSignes = cardBonusSignes.keySet();
		Random rand = new Random();
		int index = rand.nextInt(bonusSignes.size());
		int count = 0;
		for (String cardBonusSign : bonusSignes)
		{
			if (count == index)
			{
				staticCardBonusSignes.put(cardNumber, cardBonusSign);
				return cardBonusSign;
			}
			count++;
		}
		staticCardBonusSignes.put(cardNumber, "Y");
		return "Y";
	}


	/*
	��������� ���� ����� ��������. �� ��������� ���������� ��������� �����
	 */
	private String getCardType(String cardNumber)
	{
		if (staticCardTypes.containsKey(cardNumber))
		{
			return staticCardTypes.get(cardNumber);
		}
		Map<String, CardType> cardTypes = CardTypeWrapper.getCardType();
		Set<String> types = cardTypes.keySet();
		Random rand = new Random();
		int index = rand.nextInt(types.size());
		int count = 0;
		for (String cardType : types)
		{
			if (count == index)
			{
				staticCardTypes.put(cardNumber, cardType);
				return cardType;
			}
			count++;
		}
		staticCardTypes.put(cardNumber, "DC");
		return "DC";
	}

	/*
	�������� �������
	 */
	private CCAcctStmtRec_Type[] createMockOperations(int count, String acctCur)
	{
		Random rand = new Random();
		CCAcctStmtRec_Type[] operations = new CCAcctStmtRec_Type[count];
		Random currencyRandom = new Random();
		List<String> currencies = new ArrayList<String>(staticCurrencies.keySet());
		for (int i = 0; i < count; i++)
		{
			CCAcctStmtRec_Type rec = new CCAcctStmtRec_Type();
			rec.setTrnSrc("Hotel.ru");
			rec.setIsDebit(rand.nextBoolean());
			rec.setOrigCurAmt(new CCAcctStmtRec_TypeOrigCurAmt(getRandomDecimal(), null));
			GregorianCalendar effData = new GregorianCalendar();
			effData.add(Calendar.DATE, -1 * rand.nextInt(100));
			rec.setEffDate(getStringDateTime(effData));
			String currency = currencies.get(currencyRandom.nextInt(currencies.size()));
			rec.setStmtSummAmt(new StmtSummAmt_Type("Financial", getRandomDecimal(), null, currency, null));
			rec.setOrigCurAmt(new CCAcctStmtRec_TypeOrigCurAmt(getRandomDecimal(), acctCur));
			rec.setTrnDesc("������ ���������.");
			operations[i] = rec;
		}
		return operations;
	}

	private CardAcctId_Type createMockCard(String cardNumber)
	{
		return createMockCard(cardNumber, "SystemId");
	}

	/*
	���������� �����. ���������.
	 */
	private CardAcctId_Type createMockCard(String cardNumber, String systemId)
	{
		if (StringHelper.isEmpty(cardNumber))
			cardNumber = RandomHelper.rand(16, RandomHelper.DIGITS);
		CardLink cardLink = findFirstCardLinkByNumber(cardNumber);
		String cardAccount = (cardLink != null) ? cardLink.getCardPrimaryAccount() : ("42301810" + RandomHelper.rand(12, RandomHelper.DIGITS));
		String cardName = (cardLink != null) ? cardLink.getName() : "Visa Classic";
		String cardCurrencyCode;
		if (cardLink == null)
		{
			cardCurrencyCode = "RUR";
		}
		else
		{
			Currency currency = cardLink.getCurrency();
			cardCurrencyCode = currency != null ? currency.getCode() : "RUR";
			if (cardCurrencyCode.equals("RUB"))
				cardCurrencyCode = "RUR";
		}

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId(systemId);
		cardAcctId.setCardNum(cardNumber);
		cardAcctId.setCardType(getCardType(cardNumber));
		cardAcctId.setCardName(cardName);
		cardAcctId.setAcctId(cardAccount);
		cardAcctId.setContractNumber(cardNumber);
		cardAcctId.setAcctCode(123L);
		cardAcctId.setAcctSubCode(234L);

		cardAcctId.setAcctCur(cardCurrencyCode);

		Calendar endData = cardLink != null ? cardLink.getExpireDate() : null;
		if (endData == null) {
			endData = Calendar.getInstance();
			endData.add(Calendar.MONTH, (int)(Math.random()*24));
		}
		cardAcctId.setEndDt(getStringDate(endData));

		GregorianCalendar issueData = new GregorianCalendar();
		issueData.add(Calendar.MONTH, -(int)(Math.random()*24));
		cardAcctId.setIssDt(getStringDate(issueData));
		cardAcctId.setPmtDt(getStringDate(issueData));

		cardAcctId.setCustInfo(getMockCustInfo());

		BankInfo_Type bankInfoType = getMockBankInfo();
		cardAcctId.setBankInfo(bankInfoType);

		cardAcctId.setCardHolder("CardHolder Name");
		cardAcctId.setUNIAcctType(new String[]{"F", ""}[(int)(Math.random()*2)]);
		cardAcctId.setUNICardType((int)(Math.random()*30)+"");
		return cardAcctId;
	}

	private CardLink findFirstCardLinkByNumber(String number)
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(CardLink.class)
					.add(Expression.eq("number", number));
			List<CardLink> list = simpleService.find(criteria, 1);
			if (!CollectionUtils.isEmpty(list))
				return list.iterator().next();
			return null;
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			// �������, ��� ��� �� ������� � ������� �����
			return null;
		}
	}

	private void fillAccountRandomCurrency(CardAcctId_Type cardAcctId, CardLink cardLink, Long loginId)
	{
		String code = null;
		StringBuffer cardPrimaryAccountBuffer = new StringBuffer();

		boolean needCardPrimaryAccount = true;
		if (cardLink == null)
		{
			code = getRandomCurrencyCode();
			cardAcctId.setCardNum(RandomHelper.rand(16, RandomHelper.DIGITS));
			cardPrimaryAccountBuffer.append("42301");
		}
		else
		{
			String value = cardLink.getCardPrimaryAccount();

			if (StringHelper.isEmpty(value))
			{
				cardPrimaryAccountBuffer.append("42301");
				value = staticCurrencies.get("RUR");
			}
			else
			{
				cardPrimaryAccountBuffer.append(value);
				value = value.substring(5, 8);

				needCardPrimaryAccount = false;
			}
			code = findCurrencyISOCode(value);
			cardAcctId.setCardNum(cardLink.getNumber());
		}

		if (loginId != null && needCardPrimaryAccount)
		{
			cardPrimaryAccountBuffer.append(staticCurrencies.get(code));
			cardPrimaryAccountBuffer.append(RandomHelper.rand(6, RandomHelper.DIGITS));
			cardPrimaryAccountBuffer.append(StringHelper.appendLeadingZeros(String.valueOf(loginId), 6));
		}
		else if (needCardPrimaryAccount)
		{
			cardPrimaryAccountBuffer.append(staticCurrencies.get(code));
			cardPrimaryAccountBuffer.append(RandomHelper.rand(12, RandomHelper.DIGITS));
		}

		cardAcctId.setAcctCur(code);
		cardAcctId.setAcctId(cardPrimaryAccountBuffer.toString());
	}

	private String findCurrencyISOCode(String numericCode)
	{
		for (String key : staticCurrencies.keySet())
		{
			if (staticCurrencies.get(key).equals(numericCode))
			{
				return key;
			}
		}
		return null;
	}

	private String getRandomCurrencyCode()
	{
		int x = 0;
		int y = new Random().nextInt(staticCurrencies.size());

		for (String key : staticCurrencies.keySet())
		{
			if (x++ == y)
			{
				return key;
			}
		}
		return "RUR";
	}

		/*
	���������� �����. ���������.
	 */
	private CardAcctId_Type createPartlyMockCard(CardLink cardLink, Long loginId, String systemId)
	{
		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId(systemId);
		fillAccountRandomCurrency(cardAcctId, cardLink, loginId);
		cardAcctId.setCardType(getCardType(cardAcctId.getCardNum()));
		cardAcctId.setCardLevel(getCardLevel(cardAcctId.getCardNum()));
		cardAcctId.setCardBonusSign(getCardBonusSign(cardAcctId.getCardNum()));
		cardAcctId.setCardName("Visa Classic");
		cardAcctId.setAcctCode(123L);
		cardAcctId.setAcctSubCode(234L);

		//������ ����� ������������� ������ ��� GFL �������. � ������ ������ ��� �� �������������.
		Random rand = new Random();
		if (rand.nextInt(8) == 3)
			cardAcctId.setStatus(CardStatusEnum_Type.Arrested);

		GregorianCalendar endData = new GregorianCalendar();
		endData.add(Calendar.MONTH, (int)(Math.random()*24));
		cardAcctId.setEndDt(getStringDate(endData));

		GregorianCalendar issueData = new GregorianCalendar();
		issueData.add(Calendar.MONTH, -(int)(Math.random()*24));
		cardAcctId.setIssDt(getStringDate(issueData));
		cardAcctId.setPmtDt(getStringDate(issueData));

		cardAcctId.setBankInfo(getMockBankInfo());

		cardAcctId.setCustInfo(getMockCustInfo());
		cardAcctId.setUNIAcctType(new String[]{"F", ""}[(int)(Math.random()*2)]);
		cardAcctId.setUNICardType((int)(Math.random()*30)+"");
		cardAcctId.setContractNumber(cardAcctId.getCardNum());

		return cardAcctId;
	}
}
