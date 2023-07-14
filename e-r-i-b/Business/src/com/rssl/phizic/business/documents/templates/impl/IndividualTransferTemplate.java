package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.ext.sbrf.payments.PaymentsFormatHelper;
import com.rssl.phizic.business.payments.forms.validators.CheckCurConditionByCardNumValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Ўаблон перевода частному лицу
 *
 * @author khudyakov
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class IndividualTransferTemplate extends ExternalTransferTemplate
{
	private static final BankDictionaryService bankService = new BankDictionaryService();
	private static final AddressBookService addressBookService = new AddressBookService();;

	private Card mobileCard;
	private Card cardByOurContact;
	private Contact contact;
	private FormType actualFormType;

	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		if (actualFormType != null)
			return actualFormType;

		if (JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
			actualFormType = FormType.JURIDICAL_TRANSFER;
		else
		{
			String actualPaymentForm = PaymentsFormatHelper.getActualRurPaymentForm(isNewForm() ? FormConstants.NEW_RUR_PAYMENT : FormConstants.RUR_PAYMENT_FORM);
			actualFormType = FormConstants.NEW_RUR_PAYMENT.equals(actualPaymentForm) ? FormType.INDIVIDUAL_TRANSFER_NEW : FormType.INDIVIDUAL_TRANSFER;
		}
		return actualFormType;
	}

	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();

		if (chargeOffResourceType == ResourceType.NULL)
		{
			return null;
		}

		ResourceType destinationResourceType = getDestinationResourceType();
		//перевод физ. лицу с карты
		if (PHIZ_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//на карту
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
				{
					// если перевод внутрибанковский
					return isOurBank() ? ExternalCardsTransferToOurBank.class : ExternalCardsTransferToOtherBank.class;
				}

				//на счет внутри банка
				if (isOurBank())
				{
					return CardIntraBankPayment.class;
				}
				//на счет в другой банк через платежную систему –оссии
				return CardRUSPayment.class;
			}
			//перевод физ. лицу со счета
			if (chargeOffResourceType == ResourceType.ACCOUNT)
			{
				//на карту
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
				{
					throw new UnsupportedOperationException("ѕеревод со счета на чужую карту не поддерживаетс€.");
				}
				//на счет внутри банка
				if (isOurBank())
				{
					return AccountIntraBankPayment.class;
				}
				//на счет в другой банк через платежную систему –оссии
				return AccountRUSPayment.class;
			}
		}

		if (JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
			//перевод юр. лицу с карты
			if (chargeOffResourceType == ResourceType.CARD)
			{
				if (isOurBank())
				{
					//на счет внутри банка
					return CardJurIntraBankTransfer.class;
				}
				//на счет в другой банк через платежную систему –оссии
				return CardJurTransfer.class;
			}
			//перевод юр. лицу со счета
			if (chargeOffResourceType == ResourceType.ACCOUNT || chargeOffResourceType == ResourceType.NULL)
			{
				if (isOurBank())
				{
					//на счет внутри банка
					return AccountJurIntraBankTransfer.class;
				}
				//на счет в другой банк через платежную систему –оссии
				return AccountJurTransfer.class;
			}
		}
		throw new IllegalStateException("Ќевозмжно определить тип документа");
	}

	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();
		appendNullSaveMoney(formData, BUY_AMOUNT_VALUE_ATTRIBUTE_NAME, getDestinationAmount());

		return formData;
	}

	public void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		super.setFormData(source);

		String receiverBIC = getReceiverBank().getBIC();
		if (!isCardTransfer(getReceiverSubType()) && StringHelper.isNotEmpty(receiverBIC))
		{
			ResidentBank bank = bankService.findByBIC(receiverBIC);
			setNullSaveAttributeBooleanValue(IS_OUR_BANK_ATTRIBUTE_NAME, (bank != null) && (bank.isOur() != null) && (bank.isOur()));
		}

		String isOurBankCardValue = source.getValue(IS_OUR_BANK_CARD_ATTRIBUTE_NAME);
		if (StringHelper.isNotEmpty(isOurBankCardValue))
			setNullSaveAttributeBooleanValue(IS_OUR_BANK_CARD_ATTRIBUTE_NAME, Boolean.parseBoolean(isOurBankCardValue));
		storeReceiverInfo();

		if (getFormType() == FormType.INDIVIDUAL_TRANSFER_NEW)
		{
			ServiceProviderBase serviceProvider = ConfigFactory.getConfig(ProvidersConfig.class).getYandexProvider();
			setNullSaveAttributeStringValue(YANDEX_EXIST_ATTRIBUTE_NAME, Boolean.toString(serviceProvider != null));
			setNullSaveAttributeStringValue(YANDEX_MESSAGE_SHOW_ATTRIBUTE_NAME, Boolean.toString(ConfigFactory.getConfig(com.rssl.phizic.business.payments.PaymentsConfig.class).isSendMessageToReceiverYandex()));
		}
	}

	protected String getDestinationResourceCurrencyAmountAttributeName()
	{
		return BUY_AMOUNT_VALUE_ATTRIBUTE_NAME + CURRENCY_ATTRIBUTE_SUFFIX;
	}

	protected void storeReceiverInfo() throws BusinessLogicException, BusinessException
	{
		String receiverSubType = getReceiverSubType();
		if (StringHelper.isEmpty(getReceiverType()))
		{
			return;
		}

		try
		{
			boolean isOurTransferByContact = OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType);
			boolean isTransferByPhone = OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType);
			boolean isOurCard = OUR_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType);
			boolean isTransferByOurContactToOtherCard = OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType);
			boolean isExternalCard = VISA_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) || MASTERCARD_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) || isTransferByOurContactToOtherCard;

			if (isTransferByPhone || isOurTransferByContact)
			{
				Card card = isOurTransferByContact ? getCardByOurContact() : getMobileCard();
				if (card == null)
				{
					return;
				}

				if (!new CheckCurConditionByCardNumValidator().validateCurConditionByCardNumber(card.getNumber(), (ActivePerson) getOwner()))
				{
					throw new BusinessLogicException("ѕереводить валюту отличную от рублей можно только на свои валютные счета (карты).");
				}

				if (card.getNumber().equals(getChargeOffResourceLink().getNumber()))
				{
					throw new BusinessLogicException("—чет списани€ и счет зачислени€ должны быть различны.");
				}

				setReceiverCard(card.getNumber());
				setReceiverCardExternal(true);
			}
			else if (isOurCard)
			{
				// если карты нет(например при создании шаблона) то выходим
				if (StringHelper.isEmpty(getReceiverCard()))
				{
					return;
				}

				fillReceiverInfoByCardOwner(getToResourceCard());
			}
			else if (isExternalCard && getClientCreationChannel() == CreationType.internet)
			{
				if (isTransferByOurContactToOtherCard)
					saveCardByOurContactToOtherCard();

				Card card = getExternalCard();
				if (card != null)
				{
					fillReceiverInfoByCardOwner(card);
					setOurBankExternalCard(true);
				}
				else
				{
					clearReceiverInfo();
					setOurBankExternalCard(false);
				}
			}
		}
		catch (TemporalDocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public void fillReceiverInfoByCardOwner(Card card)
	{
		if (card == null)
		{
			return;
		}

		try
		{
			Client client = card.getCardClient();

			setReceiverName(client.getFirstName() + " " + client.getPatrName() + " " + String.format("%s.", client.getSurName().charAt(0)));

			setReceiverFirstName(client.getFirstName());
			setReceiverSurName(client.getSurName());
			setReceiverPatrName(client.getPatrName());
		}
		catch (Exception ex)
		{
			log.error("ѕроблема при получени€ ‘»ќ пользовател€ получател€", ex);
		}

	}

	protected void clearReceiverInfo()
	{
		setReceiverFirstName(null);
		setReceiverSurName(null);
		setReceiverPatrName(null);
		setReceiverName(null);
	}

	private void saveCardByOurContactToOtherCard() throws BusinessException, BusinessLogicException
	{
		Long contactId = getContactId();
		if (contactId == null)
			throw new BusinessException("Ќе задан идентификатор контакта");

		Contact tmp = findContact(contactId);
		String cardNumber = tmp.getCardNumber();
		if (StringHelper.isEmpty(cardNumber))
			throw new BusinessLogicException("”кажите другого получател€ или введите полный номер карты вручную");

		setReceiverCard(cardNumber);
	}

	private Card getCardByOurContact() throws BusinessException, BusinessLogicException
	{
		if (cardByOurContact != null)
			return cardByOurContact;

		Long contactId = getContactId();
		if (contactId == null)
			return null;

		cardByOurContact = getCardByPhoneNumber(findContact(contactId).getPhone());
		return cardByOurContact;
	}

	private Contact findContact(Long contactId) throws BusinessException
	{
		if (contact != null)
			return contact;

		Contact result = addressBookService.findContactById(contactId);
		if (result == null)
			throw new BusinessException(" онтакт с id=" + contactId + " не найден в адресной книге клиента loginId=" + getOwner().getId());

		contact = result;
		return contact;
	}

	private Long getContactId()
	{
		String externalContactId = getNullSaveAttributeStringValue(RECEIVER_CONTACT);
		return externalContactId == null ? null : Long.parseLong(externalContactId);
	}

	private void setReceiverCardExternal(boolean value)
	{

	}

	public ResourceType getDestinationResourceType()
	{
		String receiverSubType = getReceiverSubType();
		if (isCardTransfer(receiverSubType))
		{
			return ResourceType.EXTERNAL_CARD;
		}
		return ResourceType.EXTERNAL_ACCOUNT;
	}

	/**
	 * получить карту по номеру телефона
	 * @return Card
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected Card getCardByPhoneNumber(String phone) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(phone))
		{
			return null;
		}

		return MobileBankManager.getCardByPhone(phone, (ActivePerson) getOwner());
	}

	protected Card getMobileCard() throws BusinessLogicException, BusinessException
	{
		if (mobileCard == null)
		{
			mobileCard = getCardByPhoneNumber(getReceiverPhone());
		}
		return mobileCard;
	}

	protected Card getToResourceCard() throws BusinessException, BusinessLogicException
	{
		if (OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(getReceiverSubType()))
		{
			return getMobileCard();
		}

		return super.getToResourceCard();
	}

	public String getReceiverPhone()
	{
		return getNullSaveAttributeStringValue(RECEIVER_EXTERNAL_PHONE_NUMBER_ATTRIBUTE_NAME);
	}

	public void setReceiverPhone(String receiverPhone)
	{
		setNullSaveAttributeStringValue(RECEIVER_EXTERNAL_PHONE_NUMBER_ATTRIBUTE_NAME, receiverPhone);
	}

	public ResidentBank getReceiverBank()
	{
		if (FormType.INDIVIDUAL_TRANSFER == getFormType() || FormType.INDIVIDUAL_TRANSFER_NEW == getFormType())
		{
			//различные наименовани€ в pfd.xml форм
			return super.getReceiverBank();
		}
		return new ResidentBank(
				getNullSaveAttributeStringValue(RECEIVER_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
			);
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
		if (FormType.INDIVIDUAL_TRANSFER == getFormType() || FormType.INDIVIDUAL_TRANSFER_NEW == getFormType())
		{
			//различные наименовани€ в pfd.xml форм
			super.setReceiverBank(receiverBank);
			return;
		}

		setNullSaveAttributeStringValue(INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getName());
		setNullSaveAttributeStringValue(RECEIVER_BANK_BIK_ATTRIBUTE_NAME,  receiverBank == null ? null : receiverBank.getBIC());
		setNullSaveAttributeStringValue(RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getAccount());
	}

	/**
	 * @return тип получател€ (физик/юрик)
	 */
	public String getReceiverType()
	{
		return getNullSaveAttributeStringValue(RECEIVER_TYPE_ATTRIBUTE_NAME);
	}

	/**
	 * @return подтип лолучател€ (внешний счет, карта сбера, внешн€€ карта(visa/maestro)...)
	 */
	public String getReceiverSubType()
	{
		if (JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
			return "juridicalExternalAccount";
		}

		return getNullSaveAttributeStringValue(RECEIVER_SUB_TYPE_ATTRIBUTE_NAME);
	}

	public String generateDefaultName(Metadata metadata) throws BusinessException
	{
		String templateName = null;
		if (StringHelper.isNotEmpty(getReceiverFirstName()) && StringHelper.isNotEmpty(getReceiverSurName())
				&& FormType.INDIVIDUAL_TRANSFER != getFormType() && FormType.INDIVIDUAL_TRANSFER_NEW != getFormType())
		{
			templateName = getReceiverFirstName() + " " +  getReceiverPatrName() + " " + String.format("%s.", getReceiverSurName().charAt(0));
		}

		if (StringHelper.isEmpty(templateName))
		{
			return super.generateDefaultName(metadata);
		}
		return templateName;
	}

	private boolean isCardTransfer(Class<? extends GateDocument> type)
	{
		return type == InternalCardsTransfer.class || type == ExternalCardsTransferToOurBank.class || type == ExternalCardsTransferToOtherBank.class;
	}

	private boolean isCardTransfer(String receiverSubType)
	{
		return VISA_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) || MASTERCARD_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) ||
				OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType) || OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType) ||
				OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) || OUR_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType);
	}

	private void setOurBankExternalCard(boolean value)
	{
		setNullSaveAttributeBooleanValue(IS_OUR_BANK_CARD_ATTRIBUTE_NAME, value);
	}

	/**
	 * @return true -- переврд на карту другого банка на самом деле €вл€етс€ переводом в сбербанк
	 */
	public boolean isOurBankExternalCard()
	{
		return getNullSaveAttributeBooleanValue(IS_OUR_BANK_CARD_ATTRIBUTE_NAME);
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}

	private boolean isNewForm()
	{
		return BooleanUtils.toBoolean(getNullSaveAttributeStringValue(IS_NEW_FORM_ATTRIBUTE_NAME));
	}
}
