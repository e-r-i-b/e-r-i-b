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
	private String userId; //userId полученный из МБ.
	private String gflTB;
	private String gflOSB;
	private String gflVSP;
	private AdditionalCardType additionalCardType; //Тип доп. карты
	private Long countAdditionalCards; //Количество дополнительных карт
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
	 * @return номер карты
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number - номер счета
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
				log.error("Ошибка при получении информации по карте №" + getNumber());
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
			log.error("Ошибка при получении информации по карте №" + getNumber(),e);
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
			log.error("Ошибка при получении информации по карте №" + getNumber(), e);
			return toLinkedObjectInDBView();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.CARD_TYPE_KEY, storedResource.getEntityUpdateTime()))
		{
			return (Card) storedResource;
		}
		else
		{
			// Если данные потеряли актуальность, недоступен баланс
			StoredCard result = (StoredCard) storedResource;
			result.setAvailableLimit(null);
			return result;
		}
	}

	/**
	 * Получить карт-счет (СКС)
	 * @return Account - карт-счет
	 */
	public Account getCardAccount()
	{
		Card card = getCard();
		if(MockHelper.isMockObject(card))
		{
			log.error("Ошибка при получении информации о карт-счете карты №"+getNumber());
			return null;
		}

		try
		{
			Account account = GroupResultHelper.getOneResult(getBankrollService().getCardPrimaryAccount(card));
			if (account == null)
			{
				log.error("Не обнаружен карт-счет для карты: "+card.getNumber());
				return new MockAccount();
			}
			return account;
		}
		catch (IKFLException e)
		{
			log.error("Ошибка при получении информации о карт-счете карты №"+getNumber(),e);
			return new MockAccount();
		}
	}

	public Client getCardClient() throws BusinessException, BusinessLogicException
	{
		Card card = getCard();
		if (MockHelper.isMockObject(card))
		{
			log.error("Ошибка при получении информации о владельце карты №" + getNumber());
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
	 * Возвращает код подразделения банка с нескорректированным тербанком (не используются синонимы для замены)
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
		// для карты нет максималной суммы списания
		return null;
	}

	public String toString()
	{
		return "Карта №" + MaskUtil.getCutCardNumber(getNumber());
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
	 * @return true, если карта активна,
	 * false - если карта заблокирована или не найдена
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
	 * Использовать только для отображения даты закрытия см. CHG032182
	 * 
	 * @return строка в виде 'yyMM'
	 */
	public String getDisplayedExpireDate()
	{
		return getCard().getDisplayedExpireDate();
	}

	/**
	 * 	Получить дату закрытия.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	//Установить цодовскую дату закрытия
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}


	/**
	 * Получить карт-счет (String) из базы данных
	 * @return карт-счет
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
	 * Преобразует линк к гейтовому интерфейсу, заполняя только то, что есть у нас в БД.
	 * ОСТОРОЖНО!!!!Вам того что есть в БД может не хватить.
	 * @return card заполненый только тем что есть в БД
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
	 * @return основная или нет
	 */
	public boolean isMain()
	{
		return main == null ? true : main;
	}

	/**
	 * Установить признак карты (основная/дополнительная)
	 * @param main признак карты
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
	 * Получить номер основной карты
	 * @return номер основной карты, либо null, если карта основная
	 */
	public String getMainCardNumber()
	{
		return mainCardNumber;
	}

	/**
	 * установить номер основной карты
	 * @param mainCardNumber номер основной карты
	 */
	public void setMainCardNumber(String mainCardNumber)
	{
		this.mainCardNumber = mainCardNumber;
	}

	/**
	 * Возвращает признак карты: разрешена/запрещена печать одноразовых паролей
	 * @return true - разрешена
	 */
	public Boolean getOTPGet()
	{
		return OTPGet;
	}

	/**
	 * Установить признак карты: разрешена/запрещена печать одноразовых паролей
	 * @param OTPGet - true - разрешена
	 */
	public void setOTPGet(Boolean OTPGet)
	{
		this.OTPGet = OTPGet;
	}

	/**
	 * Возвращает признак карты: используются или заблокированы распечатанные ранее чековые пароли
	 * @return true - используются
	 */
	public Boolean getOTPUse()
	{
		return OTPUse;
	}

	/**
	 * Установить признак карты: используются или заблокированы распечатанные ранее чековые пароли
	 * @param OTPUse - true - используются
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
	 * @return внешний идентификатор клиента
	 */
	public String getClientId()
	{
		return clientId;
	}

	/**
	 * задать внешний идентификатор клиента
	 * @param clientId внешний идентификатор клиента
	 */
	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	/**
	 * задать признак используемости отчета
	 * @param useReportDelivery признак используемости отчета
	 */
	public void setUseReportDelivery(boolean useReportDelivery)
	{
		this.useReportDelivery = useReportDelivery;
	}

	/**
	 * @return использовать ли подписку
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
	 * задать e-mail
	 * @param emailAddress e-mail
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * задать тип данных отчета
	 * @param reportDeliveryType тип данных отчета
	 */
	public void setReportDeliveryType(ReportDeliveryType reportDeliveryType)
	{
		this.reportDeliveryType = reportDeliveryType;
	}

	/**
	 * @return тип данных отчета в подписке
	 */
	public ReportDeliveryType getReportDeliveryType()
	{
		return reportDeliveryType;
	}

	/**
	 * задать язык отчета
	 * @param reportDeliveryLanguage язык отчета
	 */
	public void setReportDeliveryLanguage(ReportDeliveryLanguage reportDeliveryLanguage)
	{
		this.reportDeliveryLanguage = reportDeliveryLanguage;
	}

	/**
	 * @return язык данных отчета в подписке
	 */
	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return reportDeliveryLanguage;
	}

	/**
	 * @return Дата создания.
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
	 * Проверка, наложен ли на счёт арест.
	 * @return Да, если на счёт наложен арест. Нет, в противном случае.
	 */
	public boolean isArrest()
	{
		AccountState state = getCard().getCardAccountState();
		return state != null && state == AccountState.ARRESTED;
	}

	/**
	 * @return признак необходимости показа сообщения о закрытии карты
	 */
	public Boolean getClosedState()
	{
		return closedState;
	}

	/**
	 * Задать признак необходимости показа сообщения о закрытии карты
	 * @param closedState - признак
	 */
	public void setClosedState(Boolean closedState)
	{
		this.closedState = closedState;
	}
}