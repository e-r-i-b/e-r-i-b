package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.MockAccount;
import com.rssl.phizic.business.accounts.MockOffice;
import com.rssl.phizic.business.cards.CardImpl;
import com.rssl.phizic.business.cards.MockCard;
import com.rssl.phizic.business.dictionaries.offices.common.CodeImpl;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ResourceTypeKey;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.*;

import java.util.Calendar;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 16:51:14 */
public class CardLink extends ErmbProductLink implements PaymentAbilityERL<Card>
{
	public static final String CODE_PREFIX = "card";
	private static final ServiceService serviceService = new ServiceService();
	private String number;
	private Calendar expireDate;
	private Calendar creationDate = Calendar.getInstance();
	private String cardPrimaryAccount;
	private Boolean main;
	private String mainCardNumber;
	private Boolean OTPGet;
	private Boolean OTPUse;
	private String description;
	private Currency currency;
	private Long kind;
	private Long subkind;
	private String userId; //userId ���������� �� ��.
	private String gflTB;
	private String gflOSB;
	private String gflVSP;
	private AdditionalCardType additionalCardType; //��� ���. �����
	private Long countAdditionalCards; //���������� �������������� ����
	private String clientId;
	private boolean useReportDelivery;
	private String emailAddress;
	private ReportDeliveryType reportDeliveryType;
	private ReportDeliveryLanguage reportDeliveryLanguage;
	private String contractNumber;
	private Boolean closedState;

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	/**
	 * @return ����� �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number - ����� �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}


	public Card getValue()
	{
		return getCard();
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setAdditionalCardType(AdditionalCardType additionalCardType)
	{
		this.additionalCardType = additionalCardType;
	}

	public AdditionalCardType getAdditionalCardType()
	{
		return additionalCardType;
	}
	
	public Card getCard()
	{
		try
		{
			Card card = (StringHelper.isEmpty(getExternalId())) ? toLinkedObjectInDBView() : GroupResultHelper.getOneResult(getBankrollService().getCard(getExternalId()));

			boolean accessArrested = false;
			if (ApplicationInfo.getCurrentApplication() == Application.PhizIA)
			{
				if (PersonContext.isAvailable())
					accessArrested = serviceService.isPersonServices(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId(), "ArrestedCardsInfo");
			}
			else
			{
				accessArrested = PermissionUtil.impliesServiceRigid("ArrestedCardsInfo");
			}
			if (!accessArrested && card instanceof com.rssl.phizicgate.esberibgate.bankroll.CardImpl)
				((com.rssl.phizicgate.esberibgate.bankroll.CardImpl)card).setCardAccountState(null);

			if (!MockHelper.isMockObject(card))
			{
				card = getResultCard(card);
			}
			if (card==null)
			{
				log.error("������ ��� ��������� ���������� �� ����� �" + getNumber());
				return toLinkedObjectInDBView();
			}

			return card;
		}
		catch (InactiveExternalSystemException e)
	    {
		    return getStoredCard(e);
	    }
		catch (OfflineExternalSystemException e)
		{
			return getStoredCard(e);
		}
		catch (Exception e)
		{
			log.error("������ ��� ��������� ���������� �� ����� �" + getNumber(),e);
			return toLinkedObjectInDBView();
		}
	}

	private Card getResultCard(Card card)
	{
		CardImpl result = new CardImpl(card);
		result.setMain(isMain());
		if (!isMain() && ConfigFactory.getConfig(ESBEribConfig.class).isRewriteAvailWithAvailPmnt())
			result.setAvailableLimit(card.getPurchaseLimit());
		result.setMainCardNumber(getMainCardNumber());
		result.setAdditionalCardType(getAdditionalCardType());
		return result;
	}

	private Card getStoredCard(Exception e)
	{
		AbstractStoredResource storedResource = StoredResourceHelper.findStoredResource(this, e);
		if (storedResource == null)
		{
			log.error("������ ��� ��������� ���������� �� ����� �" + getNumber(), e);
			return toLinkedObjectInDBView();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.CARD_TYPE_KEY, storedResource.getEntityUpdateTime()))
		{
			return (Card) storedResource;
		}
		else
		{
			// ���� ������ �������� ������������, ���������� ������
			StoredCard result = (StoredCard) storedResource;
			result.setAvailableLimit(null);
			return result;
		}
	}

	/**
	 * �������� ����-���� (���)
	 * @return Account - ����-����
	 */
	public Account getCardAccount()
	{
		Card card = getCard();
		if(MockHelper.isMockObject(card))
		{
			log.error("������ ��� ��������� ���������� � ����-����� ����� �"+getNumber());
			return null;
		}

		try
		{
			Account account = GroupResultHelper.getOneResult(getBankrollService().getCardPrimaryAccount(card));
			if (account == null)
			{
				log.error("�� ��������� ����-���� ��� �����: "+card.getNumber());
				return new MockAccount();
			}
			return account;
		}
		catch (IKFLException e)
		{
			log.error("������ ��� ��������� ���������� � ����-����� ����� �"+getNumber(),e);
			return new MockAccount();
		}
	}

	public Client getCardClient() throws BusinessException, BusinessLogicException
	{
		Card card = getCard();
		if (MockHelper.isMockObject(card))
		{
			log.error("������ ��� ��������� ���������� � ��������� ����� �" + getNumber());
			return null;
		}

		return card.getCardClient();
	}

	public void reset() throws BusinessLogicException, BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearCardCache(toLinkedObjectInDBView());
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

	public Office getOffice() throws BusinessException
	{
		Card card = getCard();
		if (MockHelper.isMockObject(card))
			return new MockOffice();

		return card.getOffice();
	}

	/**
	 * ���������� ��� ������������� ����� � ������������������� ��������� (�� ������������ �������� ��� ������)
	 * @return
	 */
	public Code getOriginalTbCode()
	{
		Card card = getCard();
		if (MockHelper.isMockObject(card))
			return new CodeImpl();

		return card.getOriginalTbCode();
	}

	public Money getMaxSumWrite()
	{
		// ��� ����� ��� ����������� ����� ��������
		return null;
	}

	public String toString()
	{
		return "����� �" + MaskUtil.getCutCardNumber(getNumber());
	}

	public int compareTo(Object o)
	{
		return number.compareTo( ((CardLink)o).getNumber());
	}

	public static BankrollService getBankrollService()
	{
		return  GateSingleton.getFactory().service(BankrollService.class);
	}

	/**
	 * @return true, ���� ����� �������,
	 * false - ���� ����� ������������� ��� �� �������
	 */
	public boolean isActive()
	{
		Card card = getCard();
		return card != null && card.getCardState() == CardState.active;
	}

	public Money getRest()
	{                       
		return getCard().getAvailableLimit();
	}

	/**
	 *
	 * ������������ ������ ��� ����������� ���� �������� ��. CHG032182
	 * 
	 * @return ������ � ���� 'yyMM'
	 */
	public String getDisplayedExpireDate()
	{
		return getCard().getDisplayedExpireDate();
	}

	/**
	 * 	�������� ���� ��������.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	//���������� ��������� ���� ��������
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}


	/**
	 * �������� ����-���� (String) �� ���� ������
	 * @return ����-����
	 */
	public String getCardPrimaryAccount()
	{
		return cardPrimaryAccount;
	}

	public void setCardPrimaryAccount(String cardPrimaryAccount)
	{
		this.cardPrimaryAccount = cardPrimaryAccount;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.CARD;
	}

	/**
	 * ����������� ���� � ��������� ����������, �������� ������ ��, ��� ���� � ��� � ��.
	 * ���������!!!!��� ���� ��� ���� � �� ����� �� �������.
	 * @return card ���������� ������ ��� ��� ���� � ��
	 */
	public Card toLinkedObjectInDBView()
	{
		MockCard cardInt = new MockCard();
		cardInt.setNumber(getNumber());
		cardInt.setExpireDate(getExpireDate());
		cardInt.setId(getExternalId());
		cardInt.setMain(isMain());
		cardInt.setMainCardNumber(getMainCardNumber());
		cardInt.setAdditionalCardType(getAdditionalCardType());
		return cardInt;
	}

	/**
	 * @return �������� ��� ���
	 */
	public boolean isMain()
	{
		return main == null ? true : main;
	}

	/**
	 * ���������� ������� ����� (��������/��������������)
	 * @param main ������� �����
	 */
	public void setMain(Boolean main)
	{
		this.main = main;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$cardName:" + this.getId();
	}

	/**
	 * �������� ����� �������� �����
	 * @return ����� �������� �����, ���� null, ���� ����� ��������
	 */
	public String getMainCardNumber()
	{
		return mainCardNumber;
	}

	/**
	 * ���������� ����� �������� �����
	 * @param mainCardNumber ����� �������� �����
	 */
	public void setMainCardNumber(String mainCardNumber)
	{
		this.mainCardNumber = mainCardNumber;
	}

	/**
	 * ���������� ������� �����: ���������/��������� ������ ����������� �������
	 * @return true - ���������
	 */
	public Boolean getOTPGet()
	{
		return OTPGet;
	}

	/**
	 * ���������� ������� �����: ���������/��������� ������ ����������� �������
	 * @param OTPGet - true - ���������
	 */
	public void setOTPGet(Boolean OTPGet)
	{
		this.OTPGet = OTPGet;
	}

	/**
	 * ���������� ������� �����: ������������ ��� ������������� ������������� ����� ������� ������
	 * @return true - ������������
	 */
	public Boolean getOTPUse()
	{
		return OTPUse;
	}

	/**
	 * ���������� ������� �����: ������������ ��� ������������� ������������� ����� ������� ������
	 * @param OTPUse - true - ������������
	 */
	public void setOTPUse(Boolean OTPUse)
	{
		this.OTPUse = OTPUse;
	}

	public Long getKind()
	{
		return kind;
	}

	public void setKind(Long kind)
	{
		this.kind = kind;
	}

	public Long getSubkind()
	{
		return subkind;
	}

	public void setSubkind(Long subkind)
	{
		this.subkind = subkind;
	}

	@Override
	public Class getStoredResourceType()
	{
		return StoredCard.class;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getGflTB()
	{
		return gflTB;
	}

	public void setGflTB(String gflTB)
	{
		this.gflTB = gflTB;
	}

	public String getGflOSB()
	{
		return gflOSB;
	}

	public void setGflOSB(String gflOSB)
	{
		this.gflOSB = gflOSB;
	}

	public String getGflVSP()
	{
		return gflVSP;
	}

	public void setGflVSP(String gflVSP)
	{
		this.gflVSP = gflVSP;
	}

	public Long getCountAdditionalCards()
	{
		return countAdditionalCards;
	}

	public void setCountAdditionalCards(Long countAdditionalCards)
	{
		this.countAdditionalCards = countAdditionalCards;
	}

	/**
	 * @return ������� ������������� �������
	 */
	public String getClientId()
	{
		return clientId;
	}

	/**
	 * ������ ������� ������������� �������
	 * @param clientId ������� ������������� �������
	 */
	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	/**
	 * ������ ������� �������������� ������
	 * @param useReportDelivery ������� �������������� ������
	 */
	public void setUseReportDelivery(boolean useReportDelivery)
	{
		this.useReportDelivery = useReportDelivery;
	}

	/**
	 * @return ������������ �� ��������
	 */
	public boolean isUseReportDelivery()
	{
		return useReportDelivery;
	}

	/**
	 * @return e-mail
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * ������ e-mail
	 * @param emailAddress e-mail
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * ������ ��� ������ ������
	 * @param reportDeliveryType ��� ������ ������
	 */
	public void setReportDeliveryType(ReportDeliveryType reportDeliveryType)
	{
		this.reportDeliveryType = reportDeliveryType;
	}

	/**
	 * @return ��� ������ ������ � ��������
	 */
	public ReportDeliveryType getReportDeliveryType()
	{
		return reportDeliveryType;
	}

	/**
	 * ������ ���� ������
	 * @param reportDeliveryLanguage ���� ������
	 */
	public void setReportDeliveryLanguage(ReportDeliveryLanguage reportDeliveryLanguage)
	{
		this.reportDeliveryLanguage = reportDeliveryLanguage;
	}

	/**
	 * @return ���� ������ ������ � ��������
	 */
	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return reportDeliveryLanguage;
	}

	/**
	 * @return ���� ��������.
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getContractNumber()
	{
		return contractNumber;
	}

	public void setContractNumber(String contractNumber)
	{
		this.contractNumber = contractNumber;
	}

	/**
	 * ��������, ������� �� �� ���� �����.
	 * @return ��, ���� �� ���� ������� �����. ���, � ��������� ������.
	 */
	public boolean isArrest()
	{
		AccountState state = getCard().getCardAccountState();
		return state != null && state == AccountState.ARRESTED;
	}

	/**
	 * @return ������� ������������� ������ ��������� � �������� �����
	 */
	public Boolean getClosedState()
	{
		return closedState;
	}

	/**
	 * ������ ������� ������������� ������ ��������� � �������� �����
	 * @param closedState - �������
	 */
	public void setClosedState(Boolean closedState)
	{
		this.closedState = closedState;
	}
}