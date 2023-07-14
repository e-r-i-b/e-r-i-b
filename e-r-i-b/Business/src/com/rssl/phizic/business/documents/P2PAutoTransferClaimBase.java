package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ermb.card.PrimaryCardService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.payments.forms.validators.CheckCurConditionByCardNumValidator;
import com.rssl.phizic.business.payments.forms.validators.TransferToOwnCardP2PValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.documents.AbstractP2PTransfer;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.gate.payments.autosubscriptions.EditInternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.StringHelper;

/**
 * Базовый класс заявки на автоплатеж Карта-Карта
 *
 * @author khudyakov
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class P2PAutoTransferClaimBase extends ExchangeCurrencyTransferBase implements
		ExternalCardsTransferToOurBankLongOffer, InternalCardsTransferLongOffer, AbstractPhizTransfer,
		AbstractP2PTransfer
{
	public static final String SEVERAL_RECEIVER_TYPE_VALUE                          = "several";
	public static final String SEVERAL_RECEIVER_SUB_TYPE_VALUE                      = "severalCard";
	public static final String AUTO_SUBSCRIPTION_NUMBER                             = "auto-sub-number";
	public static final String AUTO_SUBSCRIPTION_FRIENDLY_NAME                      = "auto-sub-friendly-name";
	public static final String AUTOPAY_NUMBER_ATTRIBUTE_NAME                        = "autoPay-number";

	private static final AddressBookService addressBookService = new AddressBookService();


	protected abstract boolean isOperationDenied(AutoSubscriptionLink subscriptionLink);

	@Override
	protected boolean needRates() throws DocumentException
	{
		return false;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.EXTERNAL_PAYMENT_OPERATION;
	}

	public boolean isLongOffer()
	{
		return true;
	}

	/**
	 * Тип получателя
	 * @return между_моими_картами/физик/юрик (ME_RECEIVER_TYPE, PHIZ_RECEIVER_TYPE_VALUE, JUR_RECEIVER_TYPE_VALUE)
	 */
	public String getReceiverType()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_TYPE_ATTRIBUTE_NAME);
	}
	/**
	 * Подтип получателя
	 * @return на счет в Сбербанке/на карту в Сберанке/на счет в другом банке
	 */
	public String getReceiverSubType()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_SUBTYPE_ATTRIBUTE_NAME);
	}

	public String getMobileNumber()
	{
		return getNullSaveAttributeStringValue(RurPayment.MOBILE_NUMBER_ATTRIBUTE_NAME);
	}

	public String getContactPhone() throws DocumentException
	{
		Long contactId = getContactId();
		if (contactId == null)
		{
			throw new DocumentException("Не задан идентификатор контакта");
		}

		return findContact(contactId).getPhone();
	}

	public boolean isOurBankCard()
	{
		return true;
	}

	public String getMessageToReceiver()
	{
		return getNullSaveAttributeStringValue(RurPayment.MESSAGE_TO_RECEIVER);
	}

	public void setMessageToReceiverStatus(String status)
	{
		setNullSaveAttributeStringValue(RurPayment.MESSAGE_TO_RECEIVER_STATUS, status);
	}

	public String getFriendlyName()
	{
		return getNullSaveAttributeStringValue(AUTO_SUBSCRIPTION_FRIENDLY_NAME);
	}

	@Override
	public boolean isOurBank()
	{
		return true;
	}

	public ResourceType getDestinationResourceType()
	{
		if (isReceiverCardExternal())
		{
			return ResourceType.EXTERNAL_CARD;
		}

		String type = getNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
		{
			return ResourceType.NULL;
		}

		if (CardLink.class.getName().equals(type))
		{
			return ResourceType.CARD;
		}
		throw new IllegalStateException("Неизвестный тип приемника зачисления " + type);
	}

	public String getReceiverINN()
	{
		return null;
	}

	public ResidentBank getReceiverBank()
	{
		return null;
	}

	/**
	 * @return номер телефона получателя автоплатежа
	 */
	public String getReceiverPhone()
	{
		return getNullSaveAttributeStringValue(RurPayment.MOBILE_NUMBER_ATTRIBUTE_NAME);
	}

	public boolean isNeedConfirmation()
	{
		// Если документ создан в мобильном приложении или с помощью УС, клиент так или иначе подтвердит его
		// в доверенном канале
		if (getCreationType() == CreationType.mobile || getCreationType() == CreationType.atm)
		{
			return false;
		}

		if (ChannelType.IB == getCurrentChannelType() && SEVERAL_RECEIVER_TYPE_VALUE.equals(getReceiverType()))
		{
			//для переводов на свои карты в канале IB всегда false
			return false;
		}

		return !isConfirmed();
	}

	public ReceiverCardType getReceiverCardType()
	{
		return ReceiverCardType.SB;
	}

	public String getMessageToRecipient()
	{
		return getNullSaveAttributeStringValue(RurPayment.MESSAGE_TO_RECEIVER);
	}

	protected AutoSubscriptionLink findAutoSubscriptionLink() throws DocumentException, DocumentLogicException
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return personData.getAutoSubscriptionLink(Long.parseLong(getNullSaveAttributeStringValue(AUTO_SUBSCRIPTION_NUMBER)));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	protected void storeAutoSubscriptionData(AutoSubscriptionLink link, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		try
		{
			AutoSubscriptionDetailInfo detailInfo = link.getAutoSubscriptionInfo();

			storeSubscriptionData(detailInfo);
			storePaymentData(detailInfo, messageCollector);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	protected void storePaymentData(AutoSubscriptionDetailInfo detailInfo, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		try
		{
			Class<? extends GateDocument> autoSubscriptionType = detailInfo.getType();
			if (ExternalCardsTransferToOurBankLongOffer.class == autoSubscriptionType || ExternalCardsTransferToOtherBankLongOffer.class == autoSubscriptionType)
			{
				setNullSaveAttributeStringValue(RurPayment.RECEIVER_TYPE_ATTRIBUTE_NAME, RurPayment.PHIZ_RECEIVER_TYPE_VALUE);
				setNullSaveAttributeStringValue(RurPayment.RECEIVER_SUBTYPE_ATTRIBUTE_NAME, RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE);

				storeExternalDestinationCardInfo(detailInfo.getReceiverAccount(), messageCollector);
			}

			if (InternalCardsTransferLongOffer.class == autoSubscriptionType)
			{
				setNullSaveAttributeStringValue(RurPayment.RECEIVER_TYPE_ATTRIBUTE_NAME, SEVERAL_RECEIVER_TYPE_VALUE);
				setNullSaveAttributeStringValue(RurPayment.RECEIVER_SUBTYPE_ATTRIBUTE_NAME, SEVERAL_RECEIVER_SUB_TYPE_VALUE);

				storeInternalDestinationCardInfo(detailInfo.getReceiverAccount(), messageCollector);
			}

			storeInternalChargeOffCardInfo(detailInfo.getChargeOffCard(), messageCollector);

			setNumber(detailInfo.getNumber());
			setOffice(detailInfo.getOffice());
			setChannelType(detailInfo.getChannelType());
			setLongOffer(true);

			setReceiverName(detailInfo.getReceiverName());

			setChargeOffAmount(detailInfo.getAmount());
			setInputSumType(InputSumType.CHARGEOFF);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	private void storeInternalChargeOffCardInfo(String chargeOffCardNumber, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		setChargeOffAccount(chargeOffCardNumber);
		setChargeOffResourceType(ResourceType.CARD);

		Card chargeOffCard = receiveChargeOffCard(messageCollector);
		if (chargeOffCard == null)
		{
			throw new DocumentLogicException("Операция по карте списания временно невозможна.");
		}
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME, chargeOffCard.getCurrency().getCode());
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME, chargeOffCard.getDescription());
		setChargeOffCardExpireDate(chargeOffCard.getExpireDate());

		PaymentAbilityERL chargeOffResourceLink = getChargeOffResourceLink();
		if (chargeOffResourceLink == null)
		{
			throw new DocumentLogicException("Операция по карте списания временно невозможна.");
		}
		setNullSaveAttributeStringValue(RurPayment.FROM_RESOURCE, chargeOffResourceLink.getCode());
		setNullSaveAttributeStringValue(FROM_RESOURCE_LINK_CODE_ATTRIBUTE_NAME, chargeOffResourceLink.getCode());
	}

	private void storeInternalDestinationCardInfo(String destinationCardNumber, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		setReceiverCard(destinationCardNumber);
		setNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME, ResourceType.CARD.getResourceLinkClass().getName());

		Card destinationCard = receiveCardLinkByCardNumber(destinationCardNumber, messageCollector).getCard();
		if (destinationCard == null)
		{
			throw new DocumentLogicException("Операция по карте зачисления временно невозможна.");
		}
		setNullSaveAttributeStringValue(DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME, destinationCard.getCurrency().getCode());
		setNullSaveAttributeStringValue(TO_ACCOUNT_SELECT_ATTRIBUTE_NAME, destinationCard.getNumber());
		setNullSaveAttributeStringValue(TO_ACCOUNT_NAME_ATTRIBUTE_NAME, destinationCard.getDescription());

		ExternalResourceLink destinationResourceLink = getDestinationResourceLink();
		if (destinationResourceLink == null)
		{
			throw new DocumentLogicException("Операция по карте зачисления временно невозможна.");
		}
		setNullSaveAttributeStringValue(RECEIVER_RESOURCE_ATTRIBUTE_NAME, destinationResourceLink.getCode());
		setNullSaveAttributeStringValue(TO_RESOURCE_LINK_CODE_ATTRIBUTE_NAME, destinationResourceLink.getCode());
	}

	private void storeExternalDestinationCardInfo(String destinationCardNumber, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		setReceiverCard(destinationCardNumber);
		setNullSaveAttributeStringValue(RurPayment.RECEIVER_CARD, destinationCardNumber);
		setReceiverCardExternal(true);

		Card receiverCard = getCardIfOurBank(destinationCardNumber);
		if (receiverCard == null)
		{
			throw new DocumentLogicException("Вы не можете выполнить данную операцию. Карта получателя платежа не найдена.");
		}

		setReceiverCardExpireDate(receiverCard.getExpireDate());
	}

	protected void storeSubscriptionData(AutoSubscriptionDetailInfo detailInfo)
	{
		setNullSaveAttributeEnumValue(LONG_OFFER_SUM_TYPE_ATTRIBUTE_NAME, detailInfo.getSumType());
		setNullSaveAttributeStringValue(AUTO_SUBSCRIPTION_FRIENDLY_NAME, detailInfo.getFriendlyName());
		setExecutionEventType(detailInfo.getExecutionEventType());
		setNextPayDate(detailInfo.getNextPayDate());
		setStartDate(detailInfo.getStartDate());
		setNullSaveAttributeEnumValue(AUTOSUB_TYPE_ATTRIBUTE_NAME, AutoSubType.ALWAYS);
	}

	public boolean isSameTB() throws GateException, GateLogicException
	{
		Class<? extends GateDocument> type = getType();
		if (EditInternalP2PAutoTransfer.class == type || InternalCardsTransferLongOffer.class == type)
		{
			return true;
		}

		return isSameTb(getCardOfficeCode(getChargeOffCard()), getCardOfficeCode(getReceiverCard()));
	}

	protected Code getCardOfficeCode(String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			return receiveExternalCard(cardNumber).getOffice().getCode();
		}
		catch (DocumentLogicException e)
		{
			throw new GateException(e);
		}
		catch (DocumentException e)
		{
			throw new GateLogicException(e);
		}
	}

	protected boolean isSameTb(Code payerOfficeCode, Code receiverOfficeCode)
	{
		SBRFOfficeCodeAdapter payerAdaptOfficeCode      = new SBRFOfficeCodeAdapter(payerOfficeCode);
		SBRFOfficeCodeAdapter receiverAdaptOfficeCode   = new SBRFOfficeCodeAdapter(receiverOfficeCode);

		if (TBSynonymsDictionary.isSameTB(payerAdaptOfficeCode.getRegion(), receiverAdaptOfficeCode.getRegion()))
		{
			return payerAdaptOfficeCode.getBranch().equals(receiverAdaptOfficeCode.getBranch());
		}
		return false;
	}

	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия карты списания
				Card chargeOffCard = receiveChargeOffCard(messageCollector);
				setChargeOffCardExpireDate(chargeOffCard.getExpireDate());
			}
			catch (DocumentLogicException e)
			{
				// при инициализации идем дальше
				if (updateState != InnerUpdateState.INIT)
				{
					throw e;
				}

				log.warn(e);
			}
		}

		if (destinationResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия карты зачисления
				Card destinationCard = receiveDestinationCard(messageCollector);
				setReceiverCardExpireDate(destinationCard == null ? null: destinationCard.getExpireDate());
			}
			catch (DocumentLogicException e)
			{
				// при инициализации идем дальше
				if (updateState != InnerUpdateState.INIT)
				{
					throw e;
				}

				log.warn(e);
			}
		}

		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			try
			{
				Card destinationCard = getDestinationCard(messageCollector);
				fillDestinationCard(destinationCard);

				setReceiverCard(destinationCard.getNumber());
				setReceiverCardExpireDate(destinationCard.getExpireDate());
				setReceiverCardExternal(true);

				if (isNeedUpdateReceiverInfo())
				{
					fillReceiverInfoByCardOwner(destinationCard);
				}
			}
			catch (DocumentLogicException e)
			{
				// при инициализации идем дальше
				if (updateState != InnerUpdateState.INIT)
				{
					throw e;
				}

				log.warn(e);
			}
		}
	}

	private void fillDestinationCard(Card destinationCard) throws DocumentLogicException, DocumentException
	{
		if (destinationCard == null)
		{
			throw new DocumentLogicException("Карта зачисления не найдена.");
		}
		if (!(new NotBlockedCardFilter().accept(destinationCard)))
		{
			throw new DocumentLogicException("Карта " + MaskUtil.getCutCardNumber(destinationCard.getNumber()) + " заблокирована.");
		}
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if (!(new TransferToOwnCardP2PValidator().validate(destinationCard.getNumber(), documentOwner.getLogin())))
			{
				throw new DocumentLogicException(TransferToOwnCardP2PValidator.WARNING_MESSAGE_PARAMETER_NAME);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (!(new CheckCurConditionByCardNumValidator().validateCurConditionByCardNumber(destinationCard)))
		{
			throw new DocumentLogicException("Переводить валюту отличную от рублей можно только на свои валютные счета (карты).");
		}
	}

	private boolean isNeedUpdateReceiverInfo()
	{
		return RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE.equals(getReceiverSubType()) || RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(getReceiverSubType());
	}

	/**
	 * получить карту списания
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return получить карту списания
	 */
	protected Card getDestinationCard(MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE.equals(getReceiverSubType()))
		{
			return getReceiveCardByPhoneNumber(getReceiverPhone());
		}

		if (RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE.equals(getReceiverSubType()))
		{
			return receiveDestinationCard(messageCollector);
		}

		if (RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(getReceiverSubType()))
		{
			return getReceiverCardByContact(getContactId());
		}

		throw new IllegalArgumentException("Некорректный тип получателя платежа, receiverSubType = " + getReceiverSubType());
	}

	/**
	 * получить карту списания
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return получить карту списания
	 */
	protected Card getReceiveCardByPhoneNumber(String phoneNumber) throws DocumentException, DocumentLogicException
	{
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new DocumentException("Не задан номер телефона");
		}

		try
		{
			PrimaryCardService primaryCardService = new PrimaryCardService();
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ActivePerson person = documentOwner.getPerson();
			Card phoneCard = primaryCardService.getPrimaryCard(person.asClient(), PhoneNumber.fromString(phoneNumber), getOffice());
			if (phoneCard == null)
			{
				throw new DocumentLogicException("Не удается получить карту перевода по указанному номеру телефона");
			}
			return phoneCard;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Получить карту по номеру контакта в адресной книге
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return получить карту списания
	 */
	protected Card getReceiverCardByContact(Long contactId) throws DocumentException, DocumentLogicException
	{
		Contact contact = findContact(contactId);
		setContactId(contactId);
		return getReceiveCardByPhoneNumber(contact.getPhone());
	}

	protected Contact findContact(Long contactId) throws DocumentException
	{
		try
		{
			Contact contact = addressBookService.findContactById(contactId);
			if (contact == null)
			{
				throw new DocumentException("Контакт с id=" + contactId + " не найден в адресной книге клиента=" + getOwner().getPerson());
			}

			return contact;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (NumberFormatException nfe)
		{
			throw new DocumentException(nfe);
		}
	}

	/**
	 * Заполнить данные по получателю платежа
	 * @param receiverCard карта
	 */
	public void fillReceiverInfoByCardOwner(Card receiverCard)
	{
		if (receiverCard == null)
		{
			return;
		}

		try
		{
			Client client = receiverCard.getCardClient();

			setReceiverName(client.getFirstName() + " " + client.getPatrName() + " " + String.format("%s.", client.getSurName().charAt(0)));

			//данные для чека берется из хвостов.
			setReceiverFirstName(client.getFirstName());
			setReceiverSurName(client.getSurName());
			setReceiverPatrName(client.getPatrName());
		}
		catch (Exception ex)
		{
			log.error("Проблема при получения ФИО пользователя получателя", ex);
		}
	}

	/**
	 * Установить флажок об ограничении получения информации о получателе
	 * @param flag флажок
	 */
	public void setRestrictReceiverInfoByPhone(Boolean flag)
	{
		setNullSaveAttributeBooleanValue(RurPayment.RESTRICT_PERSON_IFO_BY_PHONE, flag);
	}

	/**
	 * @return идентификатор контакта в адресной книге
	 */
	public Long getContactId()
	{
		return getNullSaveAttributeLongValue(RurPayment.RECEIVER_CONTACT);
	}

	public void setContactId(Long value)
	{
		setNullSaveAttributeLongValue(RurPayment.RECEIVER_CONTACT, value);
	}

	protected ChannelType getCurrentChannelType()
	{
		Application application = ApplicationConfig.getIt().getApplicationInfo().getApplication();

		switch (application)
		{
			case atm: return ChannelType.US;
			case mobile9: return ChannelType.IB;
			case PhizIA: return ChannelType.VSP;
			case PhizIC: return ChannelType.IB;
			default:throw new IllegalArgumentException();
		}
	}

	public String getAutopayNumber()
	{
		return getNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME);
	}

	public void setAutopayNumber(String number)
	{
		setNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME, number);
	}
}
