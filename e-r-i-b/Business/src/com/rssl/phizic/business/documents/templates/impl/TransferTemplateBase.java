package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.depo.DeliveryType;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Базовый класс шаблона перевода
 *
 * @author khudyakov
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class TransferTemplateBase extends PaymentTemplateBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final ProfileService profileService = new ProfileService();


	//поля перевода между счетами/картами клиента
	private PaymentAbilityERL chargeOffResourceLink;
	private PaymentAbilityERL destinationResourceLink;
	private String chargeOffResource;
	private String destinationResource;
	private Money chargeOffAmount;
	private Money destinationAmount;
	private InputSumType inputSumType;
	private String ground;


	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();

		//счета
		appendNullSaveString(formData, FROM_ACCOUNT_NUMBER_ATTRIBUTE_NAME, getChargeOffResource());

		appendNullSaveString(formData, FROM_ACCOUNT_SELECT_ATTRIBUTE_NAME, getChargeOffResource());
		if (!FormType.isPaymentSystemPayment(getFormType()))
		{
			appendNullSaveString(formData, TO_ACCOUNT_SELECT_ATTRIBUTE_NAME, getDestinationResource());
			appendNullSaveString(formData, TO_ACCOUNT_NUMBER_ATTRIBUTE_NAME, getDestinationResource());
		}

		//суммы
		appendNullSaveMoney(formData,  SELL_AMOUNT_VALUE_ATTRIBUTE_NAME, getChargeOffAmount());
		appendNullSaveString(formData, EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME, getInputSumType() == null ? null : getInputSumType().toValue());

		//остальные поля
		appendNullSaveString(formData, GROUND_ATTRIBUTE_NAME, getGround());

		PaymentAbilityERL fromResourceLink = getChargeOffResourceLink();
		if (fromResourceLink != null)
		{
			appendNullSaveString(formData, FROM_RESOURCE_ATTRIBUTE_NAME, fromResourceLink.getCode());
		}

		return formData;
	}

	public void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		super.setFormData(source);

		setNullSaveAttributeBooleanValue(IS_OUR_BANK_ATTRIBUTE_NAME, BooleanUtils.toBoolean(source.getValue(IS_OUR_BANK_ATTRIBUTE_NAME)));
		setGround(source.getValue(GROUND_ATTRIBUTE_NAME));

		storeCashOperationInformation(source);
		storeCardsInfo();
	}

	protected void storeCashOperationInformation(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		setChargeOffResource(source.getValue(getChargeOffResourceAttributeName()));
		setDestinationResource(source.getValue(getDestinationResourceAttributeName()));

		storeChargeOffResourceLink();
		setNullSaveAttributeStringValue(getChargeOffResourceCurrencyAmountAttributeName(), getChargeOffResourceLink() == null ? null : getChargeOffResourceLink().getCurrency().getCode());

		storeDestinationResourceLink();
		setNullSaveAttributeStringValue(getDestinationResourceCurrencyAmountAttributeName(), getDestinationAmountCurrencyValue());

		String course = source.getValue(CONVERTION_RATE_ATTRIBUTE_NAME);
		setNullSaveAttributeDecimalValue(CONVERTION_RATE_ATTRIBUTE_NAME, StringHelper.isEmpty(course) ? null : new BigDecimal(course));

		setInputSumType(InputSumType.fromValue(source.getValue(EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME)));

		InputSumType sumType = getInputSumType();
		if (sumType == null)
		{
			setChargeOffAmount(null);
			setDestinationAmount(null);
		}
		else if (InputSumType.CHARGEOFF == sumType)
		{
			//передаем только одну сумму, вторую обнуляем
			storeChargeOffAmount(source);
			setDestinationAmount(null);
		}
		else if (InputSumType.DESTINATION == sumType)
		{
			//передаем только одну сумму, вторую обнуляем
			storeDestinationAmount(source);
			setChargeOffAmount(null);
		}
	}

	protected void storeChargeOffAmount(FieldValuesSource source) throws BusinessException
	{
		try
		{
			String chargeOffResourceAmount = source.getValue(getChargeOffResourceAmountAttributeName());
			String chargeOffResourceCurrencyAmount = getNullSaveAttributeStringValue(getChargeOffResourceCurrencyAmountAttributeName());

			if (StringHelper.isNotEmpty(chargeOffResourceAmount) && StringHelper.isNotEmpty(chargeOffResourceCurrencyAmount))
			{
				CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
				setChargeOffAmount(new Money(new BigDecimal(chargeOffResourceAmount), currencyService.findByAlphabeticCode(chargeOffResourceCurrencyAmount)));
			}
			else
			{
				setChargeOffAmount(null);
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	protected void storeDestinationAmount(FieldValuesSource source) throws BusinessException
	{
		try
		{
			String destinationResourceAmount = source.getValue(getDestinationResourceAmountAttributeName());
			String destinationResourceCurrencyAmount = getNullSaveAttributeStringValue(getDestinationResourceCurrencyAmountAttributeName());

			if (StringHelper.isNotEmpty(destinationResourceAmount) && StringHelper.isNotEmpty(destinationResourceCurrencyAmount))
			{
				CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
				setDestinationAmount(new Money(new BigDecimal(destinationResourceAmount), currencyService.findByAlphabeticCode(destinationResourceCurrencyAmount)));
			}
			else
			{
				setDestinationAmount(null);
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Установить информацию по карте (списания, зачисления)
	 */
	protected void storeCardsInfo() throws BusinessLogicException, BusinessException
	{
		if (ResourceType.CARD == getChargeOffResourceType() && StringHelper.isNotEmpty(getChargeOffResource()))
		{
			Card card = getFromResourceCard();
			setChargeOffCardExpireDate(card.getExpireDate());
		}

		ResourceType destinationResourceType = getDestinationResourceType();
		if ((ResourceType.CARD == destinationResourceType || ResourceType.EXTERNAL_CARD == destinationResourceType) &&
				StringHelper.isNotEmpty(getDestinationResource()))
		{
			Card card = getToResourceCard();
			setReceiverCardExpireDate(card == null ? null : card.getExpireDate());
		}
	}

	protected String getChargeOffResourceAttributeName()
	{
		return FROM_ACCOUNT_SELECT_ATTRIBUTE_NAME;
	}

	protected String getChargeOffResourceAmountAttributeName()
	{
		return SELL_AMOUNT_VALUE_ATTRIBUTE_NAME;
	}

	protected String getChargeOffResourceCurrencyAmountAttributeName()
	{
		return FROM_RESOURCE_CURRENCY_ATTRIBUTE_NAME;
	}

	protected String getDestinationResourceAttributeName()
	{
		return TO_ACCOUNT_SELECT_ATTRIBUTE_NAME;
	}

	protected String getDestinationResourceAmountAttributeName()
	{
		return BUY_AMOUNT_VALUE_ATTRIBUTE_NAME;
	}

	protected String getDestinationResourceCurrencyAmountAttributeName()
	{
		return TO_RESOURCE_CURRENCY_ATTRIBUTE_NAME;
	}

	protected String getDestinationAmountCurrencyValue() throws BusinessLogicException, BusinessException
	{
		ResourceType resourceType = getDestinationResourceType();
		if (ResourceType.NULL == resourceType)
		{
			return StringUtils.EMPTY;
		}
		if (ResourceType.EXTERNAL_CARD == resourceType)
		{
			Card card = getToResourceCard();
			if (card != null)
			{
				return card.getCurrency().getCode();
			}
			return MoneyUtil.getNationalCurrency().getCode();
		}
		if (ResourceType.EXTERNAL_ACCOUNT == resourceType)
		{
			if (StringHelper.isEmpty(getDestinationResource()))
			{
				return StringUtils.EMPTY;
			}

			Currency currency = MoneyUtil.findCurrencyByNumericCode(getDestinationResource().substring(5, 8));
			if (currency != null)
			{
				return currency.getCode();
			}
			throw new BusinessLogicException("Невозможно определить валюту счета/карты зачисления");
		}

		if (getDestinationResourceLink() != null)
		{
			return getDestinationResourceLink().getCurrency().getCode();
		}
		throw new BusinessLogicException("Невозможно определить валюту счета/карты зачисления");
	}

	/**
	 * Получить карту ресурса списания
	 * получение происходит из контекста. метод используется только для своих карт.
	 * @return линк
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	protected Card getFromResourceCard() throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(getChargeOffResource()))
		{
			return null;
		}

		return CardLinkHelper.getCardByCardNumber(getOwner().getLogin(), getChargeOffResource());
	}

	/**
	 * Получить карту по номеру карты
	 * получение происходит из контекста. метод используется только для своих карт.
	 * @return линк
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	protected Card getToResourceCard() throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(getDestinationResource()))
		{
			return null;
		}

		ResourceType resourceType = getDestinationResourceType();
		if (ResourceType.CARD == resourceType)
		{
			return CardLinkHelper.getCardByCardNumber(getOwner().getLogin(), getDestinationResource());
		}
		if (ResourceType.EXTERNAL_CARD == resourceType)
		{
			return isOurBank() ? getToResourceExternalCard() : null;
		}
		throw new IllegalStateException("Неверный тип приемника зачисления " + resourceType);
	}

	public Card getExternalCard() throws BusinessLogicException, BusinessException
	{
		if (StringHelper.isEmpty(getDestinationResource()))
		{
			return null;
		}

		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = getClientOwner();
			if (client == null)
			{
				throw new BusinessLogicException("Невозможно получить информацию о владельце документа");
			}
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(getDestinationResource(), null)));
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить внешнюю карту по номеру карты
	 * получение происходит из контекста. метод используется только для своих карт.
	 * @return линк
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	protected Card getToResourceExternalCard() throws BusinessLogicException, BusinessException
	{
		Card card = getExternalCard();
		if (card == null)
			throw new BusinessLogicException("Не найдена информация по карте " + MaskUtil.getCutCardNumber(getDestinationResource()));
		return card;
	}

	//////////////////////////////////////////////////////////
	//методы для работы с полями перевода между моими счетами/картами

	public String getChargeOffResource()
	{
		return chargeOffResource;
	}

	public void setChargeOffResource(String chargeOffResource)
	{
		this.chargeOffResource = chargeOffResource;
	}

	/**
	 * Установить дату окончания срока действия карты списания
	 * @param date дата
	 */
	protected void setChargeOffCardExpireDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(CHARGEOFF_CARD_EXPIRE_DATE_ATTRIBUTE_NAME, date);
	}

	/**
	 *
	 * @return тип ресурса списания
	 */
	public ResourceType getChargeOffResourceType()
	{
		String resourceType = getNullSaveAttributeStringValue(FROM_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(resourceType))
		{
			return ResourceType.NULL;
		}
		return ResourceType.fromValue(resourceType);
	}

	public String getDestinationResource()
	{
		return destinationResource;
	}

	public void setDestinationResource(String destinationResource)
	{
		this.destinationResource = destinationResource;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return getCurrencyByCode(getNullSaveAttributeStringValue(getDestinationResourceCurrencyAmountAttributeName()));
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return getCurrencyByCode(getNullSaveAttributeStringValue(getChargeOffResourceCurrencyAmountAttributeName()));
	}

	private Currency getCurrencyByCode(String currencyCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		return StringHelper.isEmpty(currencyCode) ? null : currencyService.findByAlphabeticCode(currencyCode);
	}

	/**
	 * @return тип ресурса зачисления
	 */
	public ResourceType getDestinationResourceType()
	{
		String resourceType = getNullSaveAttributeStringValue(TO_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(resourceType))
		{
			return ResourceType.NULL;
		}
		return ResourceType.fromValue(resourceType);
	}

	public String getChargeOffAccount()
	{
		return getChargeOffResource();
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		setChargeOffResource(chargeOffAccount);
	}

	public String getChargeOffCard()
	{
		return getChargeOffResource();
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		setChargeOffResource(chargeOffCard);
	}

	public String getChargeOffCardAccount()
	{
		return getNullSaveAttributeStringValue(CHARGEOFF_CARD_ACCOUNT_ATTRIBUTE_NAME);
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return getNullSaveAttributeCalendarValue(CHARGEOFF_CARD_EXPIRE_DATE_ATTRIBUTE_NAME);
	}

	public String getChargeOffCardDescription()
	{
		//для шаблона документа не используем
		return null;
	}

	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	public void setChargeOffAmount(Money chargeOffAmount)
	{
		this.chargeOffAmount = chargeOffAmount;
	}

	public Money getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(Money destinationAmount)
	{
		this.destinationAmount = destinationAmount;
	}

	/**
	 * @return сумма шаблона платежа
	 */
	public Money getExactAmount()
	{
		if (InputSumType.CHARGEOFF == getInputSumType())
		{
			return getChargeOffAmount();
		}
		if (InputSumType.DESTINATION == getInputSumType())
		{
			return getDestinationAmount();
		}
		return null;
	}

	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
		this.inputSumType = inputSumType;
	}

	public void setInputSumType(String inputSumType)
	{
		this.inputSumType = InputSumType.valueOf(inputSumType);
	}


	public PaymentAbilityERL getChargeOffResourceLink() throws BusinessException
	{
		if (chargeOffResourceLink == null)
		{
			storeChargeOffResourceLink();
		}

		return chargeOffResourceLink;
	}

	public PaymentAbilityERL getDestinationResourceLink() throws BusinessException
	{
		if (destinationResourceLink == null)
		{
			storeDestinationResourceLink();
		}

		return destinationResourceLink;
	}

	public CurrencyRate getDebetSaleRate()
	{
		//для шаблона документа курсы не используем
		return null;
	}

	public CurrencyRate getDebetBuyRate()
	{
		//для шаблона документа курсы не используем
		return null;
	}

	public CurrencyRate getCreditSaleRate()
	{
		//для шаблона документа курсы не используем
		return null;
	}

	public CurrencyRate getCreditBuyRate()
	{
		//для шаблона документа курсы не используем
		return null;
	}

	public BigDecimal getConvertionRate()
	{
		return getNullSaveAttributeDecimalValue(CONVERTION_RATE_ATTRIBUTE_NAME);
	}

	public String getOperationCode()
	{
		return getNullSaveAttributeStringValue(OPERATION_CODE_ATTRIBUTE_NAME);
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public String getAuthorizeCode()
	{
		return getNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME);
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		setNullSaveAttributeStringValue(Constants.AUTHORIZE_CODE_ATTRIBUTE_NAME, authorizeCode);
	}

	public Calendar getAuthorizeDate()
	{
		return getNullSaveAttributeCalendarValue(Constants.AUTHORIZE_DATE_ATTRIBUTE_NAME);
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		setNullSaveAttributeCalendarValue(Constants.AUTHORIZE_DATE_ATTRIBUTE_NAME, authorizeDate);
	}

	public String getReceiverCard()
	{
		return getDestinationResource();
	}

	public void setReceiverCard(String receiverCard)
	{
		setDestinationResource(receiverCard);
	}

	public Calendar getReceiverCardExpireDate()
	{
		//в шаблонах не используется
		return null;
	}

	public void setReceiverCardExpireDate(Calendar receiverCardExpireDate)
	{
		//в шаблонах не используется
	}

	public ReceiverCardType getReceiverCardType()
	{
		//в шаблонах не используется
		return null;
	}

	public void setReceiverCardType(ReceiverCardType receiverCardType)
	{
		//в шаблонах не используется
	}

	public void setReceiverCardType(String receiverCardType)
	{
		//в шаблонах не используется
	}

	/**
	 * @return тип лолучателя
	 */
	public String getReceiverType()
	{
		return null;
	}

	/**
	 * @return подтип лолучателя (внешний счет, карта сбера, внешняя карта(visa/maestro)...)
	 */
	public String getReceiverSubType()
	{
		return null;
	}

	public void setRestrictReceiverInfoByPhone(boolean value)
	{
		setNullSaveAttributeBooleanValue(RESTRICT_RECEIVER_INFO_ATTRIBUTE_NAME, value);
	}

	public boolean hasRestrictReceiverInfoByPhone()
	{
		return false;
	}

	public void fillReceiverInfoByCardOwner(Card card)
	{

	}

	public void setReceiverFirstName(String value)
	{

	}

	public void setReceiverSurName(String value)
	{

	}

	public void setReceiverPatrName(String value)
	{

	}

	public boolean isOurBank()
	{
		ExtendedAttribute attribute = getAttribute(IS_OUR_BANK_ATTRIBUTE_NAME);
		if (attribute == null)
		{
			return false;
		}

		return BooleanUtils.toBoolean(attribute.getStringValue());
	}

	//////////////////////////////////////////////////////////
	//кредиты

	public String getLoanExternalId()
	{
		return getNullSaveAttributeStringValue(LOAN_EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	public String getAccountNumber()
	{
		return getNullSaveAttributeStringValue(LOAN_ACCOUNT_NUMBER_ATTRIBUTE_NAME);
	}

	public String getAgreementNumber()
	{
		return getNullSaveAttributeStringValue(LOAN_AGREEMENT_NUMBER_ATTRIBUTE_NAME);
	}

	public String getIdSpacing()
	{
		//в шаблонах не используется
		return null;
	}

	public Calendar getSpacingDate()
	{
		//в шаблонах не используется
		return null;
	}

	//////////////////////////////////////////////////////////
	//ценные бумаги

	public TransferOperation getOperType()
	{
		return null;
	}

	public TransferSubOperation getOperationSubType()
	{
		return null;
	}

	public String getOperationDesc()
	{
		return null;
	}

	public String getDivisionType()
	{
		return null;
	}

	public String getDivisionNumber()
	{
		return null;
	}

	public String getInsideCode()
	{
		return null;
	}

	public Long getSecurityCount()
	{
		return null;
	}

	public String getOperationReason()
	{
		return null;
	}

	public String getCorrDepositary()
	{
		return null;
	}

	public String getCorrDepoAccount()
	{
		return null;
	}

	public String getCorrDepoAccountOwner()
	{
		return null;
	}

	public String getAdditionalInfo()
	{
		return null;
	}

	public DeliveryType getDeliveryType()
	{
		return null;
	}

	public String getRegistrationNumber()
	{
		return null;
	}

	public String getDepoExternalId()
	{
		return null;
	}

	public String getDepoAccountNumber()
	{
		return null;
	}

	//////////////////////////////////////////////////////////
	//оплата с переменным количеством полей

	public List<Field> getExtendedFields() throws DocumentException
	{
		return null;
	}

	public void setExtendedFields(List<Field> extendedFields) throws DocumentException
	{
	}

	public String getBillingCode()
	{
		return null;
	}

	public void setBillingCode(String code)
	{
	}

	public String getBillingClientId()
	{
		return null;
	}

	public Service getService()
	{
		return null;
	}

	public void setService(Service service)
	{
	}

	public String getReceiverTransitAccount()
	{
		return null;
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
	}

	public ResidentBank getReceiverTransitBank()
	{
		return null;
	}

	public void setReceiverTransitBank(ResidentBank bank)
	{
	}

	public String getReceiverNameForBill()
	{
		return null;
	}

	public void setReceiverNameForBill(String receiverNameForBill)
	{
	}

	public boolean isNotVisibleBankDetails()
	{
		return false;
	}

	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
	}

	public Code getReceiverOfficeCode()
	{
		return null;
	}

	public void setReceiverOfficeCode(Code code)
	{
	}

	public String getIdFromPaymentSystem()
	{
		return null;
	}

	public void setIdFromPaymentSystem(String idFromPaymentSystem)
	{
	}

	public String getSalesCheck()
	{
		//для шаблонов не используется
		return null;
	}

	public void setSalesCheck(String salesCheck)
	{
		//для шаблонов не используется
	}

	public Long getReceiverInternalId()
	{
		return null;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{

	}

	public String getReceiverPointCode()
	{
		return null;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
	}

	public String getMultiBlockReceiverPointCode()
	{
		return null;
	}

	//////////////////////////////////////////////////////////
	// кредиты

	public String getLoanLinkId()
	{
		return null;
	}

	//////////////////////////////////////////////////////////
	//внешний перевод

	public String getReceiverName()
	{
		return null;
	}

	public void setReceiverName(String receiverName)
	{
	}

	public String getReceiverAccount()
	{
		return getDestinationResource();
	}

	public void setReceiverAccount(String receiverAccount)
	{
		setDestinationResource(receiverAccount);
	}

	public String getReceiverSurName()
	{
		return null;
	}

	public String getReceiverFirstName()
	{
		return null;
	}

	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverINN()
	{
		return null;
	}

	public void setReceiverINN(String receiverINN)
	{
	}

	public String getReceiverKPP()
	{
		return null;
	}

	public void setReceiverKPP(String receiverKPP)
	{
	}

	public String getReceiverPhone()
	{
		return null;
	}

	public void setReceiverPhone(String receiverPhone)
	{
	}

	public ResidentBank getReceiverBank()
	{
		return null;
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
	}

	public String getRegisterNumber()
	{
		//в шаблонах не используется
		return null;
	}

	public void setRegisterNumber(String registerNumber)
	{
		//в шаблонах не используется
	}

	public String getRegisterString()
	{
		//в шаблонах не используется
		return null;
	}

	public void setRegisterString(String registerString)
	{
		//в шаблонах не используется
	}

	public void setNextState(String nextState)
	{
		//в шаблонах не используется
	}

	public String getNextState()
	{
		return null;
	}

	private void storeChargeOffResourceLink() throws BusinessException
	{
		String chargeOffNumber = getChargeOffResource();
		if (StringHelper.isEmpty(chargeOffNumber))
		{
			return;
		}

		ResourceType resourceType = getChargeOffResourceType();
		if (!(ResourceType.ACCOUNT == resourceType || ResourceType.CARD == resourceType || ResourceType.IM_ACCOUNT == resourceType))
		{
			return;
		}

		ExternalResourceLink link = externalResourceService.findLinkByNumber(getOwner().getLogin(), resourceType, chargeOffNumber);
		if (link == null)
		{
			return;
		}

		if (!(link instanceof PaymentAbilityERL))
		{
			throw new UnsupportedOperationException("Неизвесный тип источника списания " + resourceType);
		}

		chargeOffResourceLink = (PaymentAbilityERL) link;
	}

	private void storeDestinationResourceLink() throws BusinessException
	{
		String destinationNumber = getDestinationResource();
		if (StringHelper.isEmpty(destinationNumber))
		{
			return;
		}

		ResourceType resourceType = getDestinationResourceType();
		if (!(ResourceType.ACCOUNT == resourceType || ResourceType.CARD == resourceType || ResourceType.IM_ACCOUNT == resourceType))
		{
			return;
		}

		ExternalResourceLink link = externalResourceService.findLinkByNumber(getOwner().getLogin(), resourceType, destinationNumber);
		if (link == null)
		{
			return;
		}

		if (!(link instanceof PaymentAbilityERL))
		{
			throw new UnsupportedOperationException("Неизвесный тип источника списания " + resourceType);
		}

		destinationResourceLink = (PaymentAbilityERL) link;
	}

	protected Currency getNationalCurrency() throws DocumentException
	{
		try
		{
			return MoneyUtil.getNationalCurrency();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public String getTarifPlanCodeType() throws DocumentException
	{
		try
		{
			Profile profile = profileService.findByLogin(getOwner().getLogin());
			return profile.getTariffPlanCode();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
