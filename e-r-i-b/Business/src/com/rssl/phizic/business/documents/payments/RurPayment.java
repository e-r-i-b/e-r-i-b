package com.rssl.phizic.business.documents.payments;

import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.common.forms.*;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.decorators.TaxTransitPaymentDecorator;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.ermb.card.PrimaryCardService;
import com.rssl.phizic.business.ext.sbrf.payments.PaymentsFormatHelper;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.business.payments.forms.validators.CheckCurConditionByCardNumValidator;
import com.rssl.phizic.business.payments.forms.validators.TransferToOwnAccountValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.social.PrimaryCardSocialService;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.documents.AbstractP2PTransfer;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.depo.*;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.longoffer.*;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.counter.DefaultNamingStrategy;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.security.AccessControlException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.business.documents.payments.JurPayment.*;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class RurPayment extends ExchangeCurrencyTransferBase implements
		AccountIntraBankPayment, AccountRUSPayment, AccountJurIntraBankTransfer, AccountJurTransfer, AbstractJurTransfer, AbstractPhizTransfer,
		CardIntraBankPayment, CardRUSPayment, CardJurIntraBankTransfer, CardJurTransfer,
		InternalCardsTransfer, ExternalCardsTransferToOtherBank, ExternalCardsTransferToOurBank,
		AccountIntraBankPaymentLongOffer, AccountRUSPaymentLongOffer,
		CardJurIntraBankTransferLongOffer, CardJurTransferLongOffer,  CardIntraBankPaymentLongOffer, CardRUSPaymentLongOffer,
		AccountRUSTaxPayment, CardRUSTaxPayment, CardPaymentSystemPayment,
		AbstractP2PTransfer
{
	public static final String JUR_RECEIVER_TYPE_VALUE                              = "jur";// тип получателя - юрик
	public static final String PHIZ_RECEIVER_TYPE_VALUE                             = "ph";// тип получателя - физик
	public static final String OUR_CARD_TRANSFER_TYPE_VALUE                         = "ourCard";// тип перевода "на карту сбербанка"
	public static final String OUR_PHONE_TRANSFER_TYPE_VALUE                        = "ourPhone";// тип перевода "на карту сбербанка по номеру мобильного телефона"
	public static final String SOCIAL_TRANSFER_TYPE_VALUE                           = "social";// тип перевода "на карту Сбербанка по идентификатору клиента во внешнем приложении"
	public static final String VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE               = "visaExternalCard";// тип перевода "на карту в дургом банке"
	public static final String MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE         = "masterCardExternalCard";// тип перевода "на карту в дургом банке"
	public static final String OUR_ACCOUNT_TYPE_VALUE                               = "ourAccount";// тип перевода "на счет сбербанка"
	public static final String EXTERNAL_ACCOUNT_TYPE_VALUE                          = "externalAccount";// тип перевода "на счет в другом банке"
	public static final String OUR_CONTACT_TRANSFER_TYPE_VALUE                      = "ourContact";// тип перевода "на карту сбербанка по номеру мобильного телефона контакта из АК"
	public static final String OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE        = "ourContactToOtherCard";// тип перевода "на карту другого банка по номеру сохраненной карты контакта в АК"
	public static final String YANDEX_WALLET_TRANSFER_TYPE_VALUE                    = "yandexWallet";// тип перевода "на Яндекс кошелек"
	public static final String OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE     = "yandexWalletOurContact";// тип перевода "на Яндекс кошелек" по контакту из АК
	public static final String BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE        = "yandexWalletByPhone";// тип перевода "на Яндекс кошелек" по номеру телефона
	public static final String RECEIVER_TYPE_ATTRIBUTE_NAME                         = "receiver-type";
	public static final String RECEIVER_SUBTYPE_ATTRIBUTE_NAME                      = "receiver-subtype";
	public static final String RECEIVER_INN_ATTRIBUTE_NAME                          = "receiver-inn";
	public static final String RECEIVER_KPP_ATTRIBUTE_NAME                          = "receiver-kpp";
	public static final String RECEIVER_BANK_NAME_ATTRIBUTE_NAME                    = "receiver-bank";
	public static final String RECEIVER_ALIAS_ATTRIBUTE_NAME                        = "receiver-alias";
	public static final String RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME                  = "receiver-cor-account";
	public static final String RECEIVER_BIC_ATTRIBUTE_NAME                          = "receiver-bic";
	public static final String RECEIVER_CARD                                        = "external-card-number";
	public static final String REGISTER_NUMBER_ATTRIBUTE_NAME                       = "register-number";
	public static final String REGISTER_STRING_ATTRIBUTE_NAME                       = "register-string";
	public static final String INFO_MESSAGE_STATE_ATTRIBUTE_NAME                    = "show-info-message-state";
	public static final String TAX_PAYMENT_ATTRIBUTE_NAME                           = "tax-payment";
	public static final String TAX_PAYMENT_GROUND_ATTRIBUTE_NAME                    = "tax-ground";
	public static final String TAX_PAYMENT_DOCUMENT_NUMBER_ATTRIBUTE_NAME           = "tax-document-number";
	public static final String TAX_PAYMENT_OKATO_ATTRIBUTE_NAME                     = "tax-okato";
	public static final String TAX_PAYMENT_KBK_ATTRIBUTE_NAME                       = "tax-kbk";
	public static final String TAX_PAYMENT_DOCUMENT_DATE_ATTRIBUTE_NAME             = "tax-document-date";
	public static final String TAX_PAYMENT_TYPE                                     = "tax-type";
	public static final String TAX_PAYMENT_STATUS                                   = "tax-status";
	public static final String TAX_PAYMENT_PERIOD                                   = "tax-period";
	public static final String TAX_PAYMENT_FULL_PAYMENT_PARAMETER                   = "full-payment";
	public static final String TAX_PAYMENT_TRANSIT_BANK_NAME_PARAMETER              = "transit-bank-name";
	public static final String TAX_PAYMENT_TRANSIT_BANK_BIC_PARAMETER               = "transit-bank-bic";
	public static final String TAX_PAYMENT_TRANSIT_BANK_ACCOUNT_PARAMETER           = "transit-bank-account";
	public static final String TAX_PAYMENT_TRANSIT_BANK_CORRACCOUNT_ATTRIBUTE_NAME  = "transit-bank-coraccount";
	public static final String TAX_PAYMENT_PAYER_POSTAL_CODE                        = "payer-address-postal-code";
	public static final String TAX_PAYMENT_PAYER_DISTRICT                           = "payer-address-district";
	public static final String TAX_PAYMENT_PAYER_CITY                               = "payer-address-city";
	public static final String TAX_PAYMENT_PAYER_STREET                             = "payer-address-street";
	public static final String TAX_PAYMENT_PAYER_HOUSE                              = "payer-address-house";
	public static final String TAX_PAYMENT_PAYER_BUILDING                           = "payer-address-building";
	public static final String TAX_PAYMENT_PAYER_FLAT                               = "payer-address-flat";
	public static final String IS_CARD_TRANSFER                                     = "is-card-transfer";
	//параметры оплаты задолженности по счету депо
	public static final String DEPO_LINK_ID                                         = "depo-link-id";
	public static final String DEPO_DEBT_REC_NUMBER                                 = "debt-rec-number";
	public static final String RESTRICT_PERSON_IFO_BY_PHONE                         = "restrict-person-info-by-number";
	//была ли клиенту показана информация о возможности  выполнения операции перевода по номеру карты если счет получателя начитается на 40817 (CHG038996)
	public static final String INFO_AVAIBLE_CARD_WAS_SHOW                           = "info-avaible-card-was-show";

	public static final String MOBILE_NUMBER_ATTRIBUTE_NAME                         = "mobile-number"; //номер телефона при переводе карту по номеру телефона
	public static final String SOCIAL_ID_ATTRIBUTE_NAME                             = "social-id"; //идентификатор клиента во внешнем приложении
	public static final String MESSAGE_TO_RECEIVER                                  = "message-to-receiver"; //смс-сообщение получателю перевода (при переводе по номеру карты или по номеру телефона)
	public static final String MESSAGE_TO_RECEIVER_STATUS                           = "message-to-receiver-status"; //статус смс-сообщения получателю перевода
	public static final String RECEIVER_CONTACT                                     = "external-contact-id"; //идентификатор контакта в АК
	public static final String RECEIVER_WALLET_NUMBER                               = "external-wallet-number"; //номер кошелька яндекс

	public static final String RECEIVER_BIRTHDAY = "receiver-birthday"; // Дата рождения получателя
	public static final String RECEIVER_DOCUMENT_SERIES = "receiver-document-series"; // Серия документа получателя
	public static final String RECEIVER_DOCUMENT_NUMBER = "receiver-document-number"; // Номер документа получателя

	private static final String FROM_RESOURCE_LINK = "from-resource-link";
	public static final String FROM_RESOURCE = "from-resource";
	private static final String DICT_FIELD_ID_ATTRIBUTE_NAME = "dict-field-id";
	private static final String IS_OUR_BANK_CARD_ATTRIBUTE_NAME = "isOurBankCard";
	private static final String YANDEX_EXIST_ATTRIBUTE_NAME = "yandexExist";
	private static final String YANDEX_MESSAGE_SHOW_ATTRIBUTE_NAME = "yandexMessageShow";
	private static final String IS_FUND_PAYMENT_ATTRIBUTE_NAME = "is-fund-payment";
	private static final String FUND_RESPONSE_ID_ATTRIBUTE_NAME = "fund-response-id";

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final BankDictionaryService bankService = new BankDictionaryService();
	private static final PersonService personService = new PersonService();
	private static final AddressBookService addressBookService = new AddressBookService();
	private static final UserCountersService userCounterService = new UserCountersService();
	private static final FundSenderResponseService senderResponseService = new FundSenderResponseService();

	/**
	 * Номер карты, полученный по номеру телефона.
	 */
	private Card cardByPhone;
	private Card cardByResourceCard;
	private Card cardByOurContact;
	private Contact currentContact = null;
	private NotEqualsFIOException notEqualFIO = null;
	private String billingDocumentNumber;
	private String receiverPointCode;               // externalId поставщика в адрес которого совершается платеж
	private String multiBlockReceiverPointCode;     // межблочный идентификатор поставщика услуг
	private Long receiverInternalId;                // id поставщика в адрес которого совершается платеж;
	private ServiceProviderBase yandexProvider;     // поставщик Яндекс.Деньги
	private String extendedFieldsAsString;
	private String actualFormName;
	private FormType actualFormType;
	private FundSenderResponse fundResponse;

	public Class<? extends GateDocument> getType()
	{
		String receiverType = getReceiverType();
		ResourceType chargeOffResourceType = getChargeOffResourceType();

		if (JUR_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			//перевод юр. лицу с карты
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//если налоговый платеж c карты
				if(isTaxPayment())
					return CardRUSTaxPayment.class;

				if (isOurBank())
					//на счет внутри банка
					return CardJurIntraBankTransfer.class;
				//на счет в другой банк через платежную систему России
				return CardJurTransfer.class;
			}
			//перевод юр. лицу со счета
			if (chargeOffResourceType == ResourceType.ACCOUNT || chargeOffResourceType == ResourceType.NULL)
			{
				//если налоговый платеж со счета
				if(isTaxPayment())
					return AccountRUSTaxPayment.class;

				if (isOurBank())
					//на счет внутри банка
					return AccountJurIntraBankTransfer.class;
				//на счет в другой банк через платежную систему России
				return AccountJurTransfer.class;
			}
		}
		if (PHIZ_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			if (chargeOffResourceType == ResourceType.NULL)
			{
				return null;
			}

			if (isServiceProviderPayment())
				return CardPaymentSystemPayment.class;

			ResourceType destinationResourceType = getDestinationResourceType();
			//перевод физ. лицу с карты
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
					return isLongOffer() ? CardIntraBankPaymentLongOffer.class : CardIntraBankPayment.class;
				//на счет в другой банк через платежную систему России
				return isLongOffer() ? CardRUSPaymentLongOffer.class : CardRUSPayment.class;
			}
			//перевод физ. лицу со счета
			if (chargeOffResourceType == ResourceType.ACCOUNT)
			{
				//на карту
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
					throw new UnsupportedOperationException("Перевод со счета на чужую карту не поддерживается.");
				//на счет внутри банка
				if (isOurBank())
					return isLongOffer() ? AccountIntraBankPaymentLongOffer.class : AccountIntraBankPayment.class;
				//на счет в другой банк через платежную систему России
				return isLongOffer() ? AccountRUSPaymentLongOffer.class : AccountRUSPayment.class;
			}
		}
		throw new IllegalStateException("Невозмжно определить тип документа");
	}

	public FormType getFormType()
	{
		if (actualFormType != null)
			return actualFormType;

		if (JUR_RECEIVER_TYPE_VALUE.equals(getReceiverType()))
			actualFormType = FormType.JURIDICAL_TRANSFER;
		else if (FormConstants.NEW_RUR_PAYMENT.equals(getFormName()))
			actualFormType = FormType.INDIVIDUAL_TRANSFER_NEW;
		else
			actualFormType = FormType.INDIVIDUAL_TRANSFER;

		return actualFormType;
	}

	@Override
	public String getFormName()
	{
		if (actualFormName != null)
			return actualFormName;

		String formName = super.getFormName();
		if (FormConstants.RUR_PAYMENT_FORM.equals(formName) || FormConstants.NEW_RUR_PAYMENT.equals(formName))
			actualFormName = StringHelper.isEmpty(getFundResponseId()) && !isLongOffer() ? PaymentsFormatHelper.getActualRurPaymentForm(formName) : FormConstants.RUR_PAYMENT_FORM;
		else
			actualFormName = formName;

		return actualFormName;
	}

	public void setFormName(String formName)
	{
		actualFormName = null;
		super.setFormName(formName);
	}

	/**
	 * Тип получателя
	 * @return физик/юрик (PHIZ_RECEIVER_TYPE_VALUE/JUR_RECEIVER_TYPE_VALUE)
	 */
	public String getReceiverType()
	{
		return getNullSaveAttributeStringValue(RECEIVER_TYPE_ATTRIBUTE_NAME);
	}
	/**
	 * Подтип получателя
	 * @return на счет в Сбербанке/на карту в Сберанке/на счет в другом банке
	 */
	public String getReceiverSubType()
	{
		return getNullSaveAttributeStringValue(RECEIVER_SUBTYPE_ATTRIBUTE_NAME);
	}

	public void setReceiverSubType(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_SUBTYPE_ATTRIBUTE_NAME, value);
	}

	public String getReceiverBIC()
	{
		return getNullSaveAttributeStringValue(RECEIVER_BIC_ATTRIBUTE_NAME);
	}

	public String getReceiverCorAccount()
	{
		return getNullSaveAttributeStringValue(RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME);
	}

	private void setReceiverCorAccount(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME, value);
	}

	public String getReceiverINN()
	{
		return getNullSaveAttributeStringValue(RECEIVER_INN_ATTRIBUTE_NAME);
	}

	/**
	 * Часть данных в платеже банке может отсутствовать.
	 * Обязательным для заполнения является БИК.
	 * @return банка получателя
	 */
	public ResidentBank getReceiverBank()
	{
		ResidentBank result = new ResidentBank();
		result.setAccount(getReceiverCorAccount());
		result.setBIC(getReceiverBIC());
		result.setName(getReceiverBankName());
		return result;
	}

	/**
	 * установить банк получателя.
	 * @param receiverBank банк получателя
	 */
	public void setReceiverBank(ResidentBank receiverBank)
	{
		if (receiverBank == null)
		{
			setReceiverCorAccount(null);
			setReceiverBIC(null);
			setReceiverBankName(null);
		}
		else
		{
			setReceiverCorAccount(receiverBank.getAccount());
			setReceiverBIC(receiverBank.getBIC());
			setReceiverBankName(receiverBank.getName());
		}
	}


	public String getReceiverKPP()
	{
		return getNullSaveAttributeStringValue(RECEIVER_KPP_ATTRIBUTE_NAME);
	}

	public String getReceiverBankName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_BANK_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverAlias()
	{
		return getNullSaveAttributeStringValue(RECEIVER_ALIAS_ATTRIBUTE_NAME);
	}

	protected void setReceiverAlias(String receiverAlias)
	{
		setNullSaveAttributeStringValue(RECEIVER_ALIAS_ATTRIBUTE_NAME, receiverAlias);
	}

	private void setReceiverBIC(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_BIC_ATTRIBUTE_NAME, value);
	}

	private void setReceiverBankName(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, value);
	}

	public void setReceiverKPP(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_KPP_ATTRIBUTE_NAME, value);
	}

	public void setReceiverINN(String receiverINN)
	{
		setNullSaveAttributeStringValue(RECEIVER_INN_ATTRIBUTE_NAME, receiverINN);
	}

	public boolean getInfoMessageState()
	{
		Object value = getNullSaveAttributeValue(INFO_MESSAGE_STATE_ATTRIBUTE_NAME);
		if (value == null)
			return false;
		else
			return (Boolean) getNullSaveAttributeValue(INFO_MESSAGE_STATE_ATTRIBUTE_NAME);
	}

	public void setInfoMessageState(boolean infoMessageState)
	{
		setNullSaveAttributeBooleanValue(INFO_MESSAGE_STATE_ATTRIBUTE_NAME, infoMessageState);
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		try
		{
			if (!StringHelper.isEmpty(getDepoLinkId()) && !StringHelper.isEmpty(getDepoDebtRecNumber()))
			{
				storeDepoPaymentInfo();
			}
			else if(!isCardTransfer(getType()) && StringHelper.isNotEmpty(getReceiverBIC()))
			{
				ResidentBank bank = bankService.findByBIC(this.getReceiverBIC());
				setOurBank((bank != null) && (bank.isOur() != null) && (bank.isOur()));
			}

			if (isServiceProviderPayment())
			{
				ServiceProviderBase serviceProvider = getServiceProvider();
				setReceiverPointCode(serviceProvider.getSynchKey().toString());
				setReceiverInternalId(serviceProvider.getId());
				setExtendedFields((List) serviceProvider.getFieldDescriptions());
				storeProviderFieldsValues();
			}
			initNewRurPaymentParameters(this);
			initFundPayment();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public String getReceiverCard()
	{
		try
		{
			if (getFundResponse() != null)
				return getFundResponse().getToResource();
			return super.getReceiverCard();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.EXTERNAL_PAYMENT_OPERATION;
	}

	private boolean isCardTransfer(Class<? extends GateDocument> type)
	{
		return type == InternalCardsTransfer.class || type == ExternalCardsTransferToOurBank.class || type == ExternalCardsTransferToOtherBank.class;
	}

	//сохраняем данные для оплаты задолженности по счету депо
	private void storeDepoPaymentInfo() throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!AuthModule.getAuthModule().implies(new ServicePermission("DepoDebtPayment")))
				throw new AccessControlException("Нет доступа к операции");
			String recNumber = getDepoDebtRecNumber();
			DepoAccountLink depoLink = externalResourceService.findLinkById(DepoAccountLink.class, Long.parseLong(getDepoLinkId()));
			DepoAccountService depoAccountService = GateSingleton.getFactory().service(DepoAccountService.class);
			DepoAccount depoAccount = depoLink.getDepoAccount();
			if (MockHelper.isMockObject(depoAccount))
				throw new DocumentException("Не найден счет депо, номер " + depoLink.getAccountNumber());
			DepoDebtInfo depoDebtInfo = depoAccountService.getDepoDebtInfo(depoAccount);
			DepoDebtItem depoDebtItem = null;
			for (DepoDebtItem item : depoDebtInfo.getDebtItems())
			{
				if (recNumber.equals(item.getRecNumber()))
				{
					depoDebtItem = item;
					break;
				}
			}
			if (depoDebtItem == null)
				throw new DocumentException("Не найдена задолженность с номером счета" + recNumber + ", номер счета депо " + depoAccount.getAccountNumber());

			DepoDebtItemInfo depoDebtItemInfo = depoAccountService.getDepoDebtItemInfo(depoAccount, depoDebtItem).getResult(depoDebtItem);
			if (depoDebtItemInfo == null)
				throw new DocumentException("Не найдена расширенная информация о задолженности с номером счета" + recNumber + ", номер счета депо " + depoAccount.getAccountNumber());

			setReceiverName(depoDebtItemInfo.getRecipientName());
			setReceiverAccount(depoDebtItemInfo.getRecipientAccount());
			setReceiverINN(depoDebtItemInfo.getRecipientINN());
			setReceiverKPP(depoDebtItemInfo.getRecipientKPP());

			String bic = depoDebtItemInfo.getBankBIC();
			String bankName = depoDebtItemInfo.getBankName();
			String corAccount = depoDebtItemInfo.getBankCorAccount();
			ResidentBank bank = bankService.findByBIC(bic);
			if (bank != null)
			{
				bankName = bank.getName();
				corAccount = bank.getAccount();
			}
			setReceiverBankName(bankName);
			setReceiverBIC(bic);
			setReceiverCorAccount(corAccount);

			setOurBank((bank != null) && (bank.isOur() != null) && (bank.isOur()));

			setDestinationAmount(depoDebtItem.getAmount().add(depoDebtItem.getAmountNDS()));

			if (depoDebtItem.getRecNumber().endsWith("V"))//счет на возмещение затрат
			{
				String text = "Оплата счета № %s от %s за услуги по счету депо № %s";
				setGround(String.format(text, depoDebtItem.getRecNumber(),
											  String.format("%1$td.%1$tm.%1$tY", depoDebtItem.getRecDate()),
											  depoAccount.getAccountNumber()));
			}
			else //обычный счет
			{
				String text = "Оплата счета № %s от %s за услуги по счету депо № %s, в т.ч. НДС %s рублей";
				setGround(String.format(text, depoDebtItem.getRecNumber(),
											  String.format("%1$td.%1$tm.%1$tY", depoDebtItem.getRecDate()),
											  depoAccount.getAccountNumber(),
											  String.format("%.2f", depoDebtItem.getAmountNDS().getDecimal())));
			}
		}
		catch (BusinessException be)
		{
			throw new DocumentException(be);
		}
		catch (GateException ge)
		{
			throw new DocumentException(ge);
		}
		catch (GateLogicException gle)
		{
			throw new DocumentLogicException(gle);
		}
	}

	private void storeProviderFieldsValues() throws DocumentException
	{
		String receiverSubType = getReceiverSubType();
		ServiceProviderBase serviceProvider = getServiceProvider();
		if (serviceProvider == null)
			throw new DocumentException("Не найден поставщик услуг");

		if (ResourceType.NULL != getChargeOffResourceType())
			setDestinationAmount(getExactAmount());

		FieldDescription fieldDescription = serviceProvider.getKeyFields().get(0);
		if (BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(receiverSubType))
		{
			PhoneNumber phoneNumber = PhoneNumberFormat.ADDRESS_BOOK.parse(getNullSaveAttributeStringValue(MOBILE_NUMBER_ATTRIBUTE_NAME));
			setNullSaveAttributeStringValue(fieldDescription.getExternalId(), phoneNumber.operator() +  phoneNumber.abonent());
		}
		else if (OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(receiverSubType))
		{
			PhoneNumber phoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.parse(findContact(getContactId()).getPhone());
			setNullSaveAttributeStringValue(fieldDescription.getExternalId(), phoneNumber.operator() +  phoneNumber.abonent());
		}
	}

	// сохраняем данные карты
	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			if ( FormConstants.NEW_RUR_PAYMENT.equals(getFormName()) &&
					MobileApiUtil.isMobileApiGE(MobileAPIVersions.V9_00) )
			{
				fillPaymentInfoForMAPI();
			}
			String receiverSubType = getReceiverSubType();
			boolean isTransferByPhone = OUR_PHONE_TRANSFER_TYPE_VALUE.equals(receiverSubType);
			boolean isTransferByOurContact = OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType);
			boolean isTransferByOurContactToOtherCard = OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType);
			boolean isTransferBySocialId = SOCIAL_TRANSFER_TYPE_VALUE.equals(receiverSubType);

			//При переводе с карты дату закрытия берем из ЦОДа, она хранится в линке
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//Сохраняем инфу о сроке действия карты зачисления
				setChargeOffCardExpireDate(getChargeOffCardExpireDate(chargeOffResourceType, messageCollector));
			}
			if (destinationResourceType == ResourceType.CARD || destinationResourceType == ResourceType.EXTERNAL_CARD)
			{
				if (isTransferByOurContactToOtherCard)
					saveCardByOurContactToOtherCard();

				//Сохраняем инфу о сроке действия карты зачисления
				Card destinationCard;
				if (isTransferByPhone)
					destinationCard = getCardByPhone(getMobileNumber());
				else if (isTransferByOurContact)
					destinationCard = getCardByOurContact();
				else if (isTransferBySocialId)
					destinationCard = getCardByResourceCard();
				else
					destinationCard = receiveDestinationCard(messageCollector);
				setReceiverCardExpireDate(destinationCard == null ? null: destinationCard.getExpireDate());
			}
			// только для перевода "на карту Сбербанка" или "Перевод на карту по номеру мобильного телефона"
			boolean isExternalCard = VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType) ||
					MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType) || isTransferByOurContactToOtherCard;
			boolean isOurCard = OUR_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType);

			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if (documentOwner != null && getDictFieldId() != null)
			{
				if (isTransferByPhone && StringHelper.isEmpty(getMobileNumber()))
				{
					Contact contact = addressBookService.findContactByLoginAndId(getDictFieldId(), documentOwner.getLogin().getId());
					if (contact != null)
						setMobileNumber(contact.getPhone());
				}
				else if (isOurCard && StringHelper.isEmpty(getReceiverCard()))
				{
					Contact contact = addressBookService.findContactByLoginAndId(getDictFieldId(), documentOwner.getLogin().getId());
					if (contact != null)
						setReceiverCard(contact.getCardNumber());
				}
			}

			// для перевода "на карту Сбербанка" или "Перевод на карту по номеру мобильного телефона" или "на карту в другом банке"
			if (!StringHelper.isEmpty(getReceiverType()))
			{
				// если перевод "На карту по номеру мобильного телефона", "Перевод клиенту Сбербанка по выбранному из АК"
				if (isTransferByPhone || isTransferBySocialId || isTransferByOurContact)
				{
					Card card;
					if (isTransferByPhone)
					{
						String mobileNumber = getMobileNumber();
						// если номера нет, например при создании шаблона, то дальше делать нечего
						if (StringHelper.isEmpty(mobileNumber))
							return;

						//Платеж по номеру телефона. Если зашли через mApi
						if (getCreationType() == CreationType.mobile)
						{
							//счетчик увеличиваем только если контакт не из адресной книги
							if (!isContactFromAddressBook(mobileNumber) && !userCounterService.increment(documentOwner.getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
								throw new PhoneLimitExceededException("$5$Превышен кумулятивный лимит запросов по номеру телефона. Используйте перевод по номеру карты.");
						}
						card = getCardByPhone(getMobileNumber());
						if (getCreationType() == CreationType.mobile)
							updateUserAddressBook(card, mobileNumber);
					}
					else if (isTransferByOurContact)
					{
						card = getCardByOurContact();
						//Платеж по номеру телефона. Если зашли через mApi
						if (getCreationType() == CreationType.mobile)
							updateUserAddressBook(card, null);
					}
					else
					{
						String socialId = getSocialId();
						if (StringHelper.isEmpty(socialId))
							return;
						card = getCardByResourceCard();
					}

					if (card != null || (card == null && !isTemplate()))
					{
						if (card == null)
						{
							if (ApplicationUtil.isErmbSms())
								throw new DocumentLogicException("Операция не выполнена. Не удалось найти получателя по указанному номеру телефона.");
							throw new DocumentLogicException("Не удается получить карту перевода по указанному номеру телефона");
						}

						ActivePerson owner = documentOwner.getPerson();
						if(!new CheckCurConditionByCardNumValidator().validateCurConditionByCardNumber(card.getNumber(), owner))
							throw new DocumentLogicException("Переводить валюту отличную от рублей можно только на свои валютные счета (карты).");

						PaymentAbilityERL paymentAbilityERL = getChargeOffResourceLink();
						if(paymentAbilityERL != null && card.getNumber().equals(paymentAbilityERL.getNumber()))
							throw new DocumentLogicException("Счет списания и счет зачисления должны быть различны.");

						setReceiverCard(card.getNumber());

						if (isTransferByPhone)
						{
							TransferToOwnAccountValidator ownAccountValidator = new TransferToOwnAccountValidator();
							Money destinationAmount = getDestinationAmount();
							Money chargeOffAmount = getChargeOffAmount();
							if (!(ownAccountValidator.validate(getChargeOffResourceLink(), getReceiverAccount(), isOurBank(), getNullSaveAttributeStringValue(EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME),
									chargeOffAmount != null ? chargeOffAmount.getDecimal() : null, destinationAmount != null ? destinationAmount.getDecimal() : null)))
								throw new DocumentLogicException(ownAccountValidator.getMessage());
						}
					}
					setReceiverCardExternal(true);
					// если перевод физ лицу по номеру телефона то не заполняем ФИО получателя, заполнение идет в UpdateReceiverInfoByPhoneHandler
					// с учетом счетчика CHG041044
					//fillReceiverInfoByCardOwner(card);
					if (isTransferBySocialId)
						fillReceiverInfoByCardOwner(card);
				}

				// перевод "на карту Сбербанка"
				else if (isOurCard)
				{
					// если карты нет(например при создании шаблона) то выходим
					if(StringHelper.isEmpty(getReceiverCard()))
						return;

					fillReceiverInfoByCardOwner(receiveDestinationCard(messageCollector));
				}

				// перевод "на карту в другом банке"
				else if(isExternalCard)
				{
					// если карты нет(например при создании шаблона) то выходим
					if(StringHelper.isEmpty(getReceiverCard()))
						return;

					Card card = getCardIfOurBank(getReceiverCard());
					if(card != null)
					{
						setOurBankCard(true);
						fillReceiverInfoByCardOwner(card);
					}
					else
					{
						setOurBankCard(false);
						clearReceiverInfo();
					}
				}
			}
		}
		catch (DocumentException e)
		{
			if(updateState != InnerUpdateState.INIT)
				throw e;
			// при инициализации идем дальше, обновим нормальными данными в свое время
			log.warn(e);
		}
		catch (DocumentLogicException e)
		{
			if(updateState != InnerUpdateState.INIT)
				throw e;
			// при инициализации идем дальше, обновим нормальными данными в свое время
			log.warn(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public Long getDictFieldId()
	{
		return getNullSaveAttributeLongValue(DICT_FIELD_ID_ATTRIBUTE_NAME);
	}

	private void fillPaymentInfoForMAPI() throws DocumentLogicException, DocumentException, BusinessException
	{
		String externalCardNumber = getReceiverCardNumber();
		String externalPhoneNumber = getReceiverPhoneNumber();
		Long externalContactId = getContactId();

		if (StringHelper.isEmpty(externalCardNumber) &&
				StringHelper.isEmpty(externalPhoneNumber) &&
				(externalContactId == null) )
			return;

		String receiverSubType = null;

		//Универсальный перевод на карту получателя по номеру карты
		if ( StringHelper.isNotEmpty(externalCardNumber) )
		{
			//Определение банка эмитента карты, и типа получателя платежа
			if (!isSBRFCardIssuerBank(externalCardNumber))
			{
				if (externalCardNumber.startsWith("4") || externalCardNumber.startsWith("5") || externalCardNumber.startsWith("6"))
					//на карту VISA другого банка по номеру сохраненной карты контакта из адресной книги (АК) ЕРИБ
					receiverSubType = RurPayment.VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;
				else if (externalCardNumber.startsWith("5") || externalCardNumber.startsWith("6"))
					//на карту Mastercard другого банка по номеру сохраненной карты контакта из адресной книги (АК) ЕРИБ
					receiverSubType = RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;
				else
					//на карту другого банка по номеру сохраненной карты контакта из адресной книги (АК) ЕРИБ
					receiverSubType = RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE;
			}
		}
		//Если передали ИД контакта из АК
		if (externalContactId != null )
		{
			Contact contact = findContact(externalContactId);
			receiverSubType = contact.isSberbankClient() ?  RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE :  RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE;

		}
		else if (StringHelper.isEmpty(receiverSubType) && StringHelper.isNotEmpty(externalPhoneNumber))
		{
			receiverSubType = RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE;
		}
		else if (StringHelper.isEmpty(receiverSubType))
		{
			receiverSubType = getReceiverSubType();
		}

		if (currentContact != null)
			setContactId(currentContact.getId());

		setReceiverSubType(receiverSubType);
		setCreationType(CreationType.mobile);
	}

	public boolean isEinvoicing()
	{
		return false;
	}

	public static class NotEqualsFIOException extends DocumentLogicException
	{
		public NotEqualsFIOException()
		{
			super("NotEqualFIO");
		}
	}

	/**
	 * Обновление данных для адресной книги.
	 *
	 * @param mobileNumber номер мобильного телефона.
	 */
	private void updateUserAddressBook(Card card, String mobileNumber) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (card == null)
				return;

			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Contact contact = StringHelper.isEmpty(mobileNumber) ?
					addressBookService.findContactById(getContactId()) :
					addressBookService.findContactClientByPhone(documentOwner.getLogin().getId(), "7"+mobileNumber);
			if (contact == null)
				return;

			if (StringHelper.isEmpty(contact.getFio()))
				return;

			Client cl = card.getCardClient();

			if (contact.getFio().equals(cl.getSurName() + " " + cl.getFirstName() + " " + cl.getPatrName()))
				return;

			if (MobileApiUtil.isLightSchemeAuthContext())
				throw new NotEqualsFIOException();
			else
				notEqualFIO = new NotEqualsFIOException();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Если ФИО контакта в адресной книге не совпадает с ФИО, пришедшим из WAY.
	 *
	 * @return объект, если проблема есть.
	 */
	public NotEqualsFIOException getNotEqualFIO()
	{
		return notEqualFIO;
	}

	/**
	 * @return true -- переврд на карту другого банка на самом деле является переводом в сбербанк
	 */
	public boolean isOurBankCard()
	{
		return Boolean.parseBoolean(getNullSaveAttributeStringValue(IS_OUR_BANK_CARD_ATTRIBUTE_NAME));
	}

	private void setOurBankCard(boolean value)
	{
		setNullSaveAttributeBooleanValue(IS_OUR_BANK_CARD_ATTRIBUTE_NAME, value);
	}

	/**
	 * получить карту по номеру телефона
	 * @param  phone - номер телефона
	 * @return Card
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private Card getCardByPhone(String phone) throws DocumentException
	{
		if (cardByPhone != null)
			return cardByPhone;

		if (StringHelper.isEmpty(phone))
			return null;

		PrimaryCardService primaryCardService = new PrimaryCardService();
		PaymentAbilityERL link = getChargeOffResourceLink();
		BusinessDocumentOwner documentOwner = null;
		try
		{
			documentOwner = getOwner();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		try
		{
			Office office = null;
			if (ApplicationUtil.isMobileApi())
				office = documentOwner.getPerson().asClient().getOffice();
			else
			{
				if (link == null)
					throw new IllegalStateException("Не задан источник списания");
				office = link.getOffice();
			}
			cardByPhone = primaryCardService.getPrimaryCard(documentOwner.getPerson().asClient(), PhoneNumber.fromString(phone), office);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		return cardByPhone;
	}

	/**
	 * Получить карту по идентификатору контакта
	 * @return Card
	 */
	private Card getCardByOurContact() throws DocumentException
	{
		if (cardByOurContact != null)
			return cardByOurContact;

		Long contactId = getContactId();
		if (contactId == null)
			return null;

		cardByOurContact = getCardByPhone(findContact(contactId).getPhone());
		return cardByOurContact;
	}

	/**
	 * Установить в платеж сохраненную карту контакта из АК
	 * @return Card
	 */
	private void saveCardByOurContactToOtherCard() throws DocumentException, DocumentLogicException
	{
		Long contactId = getContactId();
		if (contactId == null)
			throw new DocumentException("Не задан идентификатор контакта");

		Contact tmp = findContact(contactId);
		String cardNumber = tmp.getCardNumber();
		if (StringHelper.isEmpty(cardNumber))
			throw new DocumentLogicException("Укажите другого получателя или введите полный номер карты вручную");

		setReceiverCard(cardNumber);
	}

	private Contact findContact(Long contactId) throws DocumentException
	{
		if (currentContact != null && currentContact.getId().equals(contactId))
			return currentContact;
		try
		{
			Contact result = addressBookService.findContactById(contactId);
			if (result == null)
				throw new DocumentException("Контакт с id=" + contactId + " не найден в адресной книге клиента = " + getOwner().getPerson());

			currentContact = result;
			return currentContact;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * получить карту по ресурсу списания
	 * @return
	 * @throws GateLogicException
	 * @throws GateException
	 * @throws DocumentException
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private Card getCardByResourceCard() throws DocumentException, DocumentLogicException
	{
		if (cardByResourceCard != null)
			return cardByResourceCard;

		if (StringHelper.isEmpty(getSocialId()))
			return null;
		PrimaryCardSocialService socialService = new PrimaryCardSocialService();
		try
		{
			PaymentAbilityERL link = getChargeOffResourceLink();
			if (link == null)
				throw new IllegalStateException("Не задан источник списания");
			cardByResourceCard = socialService.getPrimaryCard(getSocialId(), AuthenticationContext.getContext().getDeviceInfo(), link.getOffice());
		}
		catch (BackLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		return cardByResourceCard;
	}

	/**
	 * получить строку вида имя отчество Ф.
	 * @param client Клиент.
	 */
	public String getFIOByClient(Client client)
	{
		if (client == null)
		{
			return null;
		}
		StringBuilder clientName = new StringBuilder();
		clientName.append(client.getFirstName()).append(" ");
		if (!StringHelper.isEmpty(client.getPatrName()))
		{
			clientName.append(client.getPatrName()).append(" ");
		}
		clientName.append(client.getSurName().charAt(0)).append(".");
		return clientName.toString();
	}

	/**
	 * Заполнение информации о получателе по его карте
	 * @param card карта, по которой будет заполнятся инфа
	 */
	public void fillReceiverInfoByCardOwner(Card card)
	{
		if(card == null)
		{
			return;
		}

		try
		{
			Client client = card.getCardClient();

			setReceiverName(getFIOByClient(client));

			//данные для чека берется из хвостов.
			setReceiverFirstName(client.getFirstName());
			setReceiverSurName(client.getSurName());
			setReceiverPatrName(client.getPatrName());
			setReceiverBirthday(client.getBirthDay());

			ClientDocument document = client.getDocuments().get(0);
			setReceiverDocSeries(document.getDocSeries());
			setReceiverDocNumber(document.getDocNumber());

		}
		catch (Exception ex)
		{
			log.error("Проблема при получения ФИО пользователя получателя", ex);
		}

	}

	private void clearReceiverInfo()
	{
		setReceiverName(null);
		setReceiverFirstName(null);
		setReceiverSurName(null);
		setReceiverPatrName(null);
		setReceiverBirthday(null);
		setReceiverDocSeries(null);
		setReceiverDocNumber(null);
	}

	protected boolean needRates() throws DocumentException
	{
		try
		{
			return DocumentHelper.postConfirmCommissionSupport(this) && isConvertion() && !isTemplate();
		}
		catch (BusinessException ignore)
		{
			return false;
		}
	}

	public ResourceType getDestinationResourceType()
	{
		if (isReceiverCardExternal())
		{
			return ResourceType.EXTERNAL_CARD;
		}
		return ResourceType.EXTERNAL_ACCOUNT;
	}

	/**
	 * Сравнить документы по ключевым полям. Если у переданного документа не заполнено ключевое поле,
	 *  а у текущего заполнено, документы отличаются.
	 * @param template шаблон, на основе которого был создан документ
	 * @return true - ключевые поля у документов одинаковые, false - ключевые поля разные
	 */
	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		if (!super.equalsKeyEssentials(template))
		{
			return false;
		}

		ResidentBank residentBank = template.getReceiverBank();
		return StringHelper.equalsNullIgnore(getReceiverBIC(), residentBank.getBIC())
			&& StringHelper.equalsNullIgnore(getReceiverCorAccount(), residentBank.getAccount())
			&& StringHelper.equalsNullIgnore(getReceiverBankName(), residentBank.getName())
			&& StringHelper.equalsNullIgnore(getReceiverINN(), template.getReceiverINN())
			&& StringHelper.equalsNullIgnore(getReceiverKPP(), template.getReceiverKPP())
			&& equalsGrounds(template);
	}

	/**
	 * Сравниваем поля "назначение платежа"
	 * @param template шаблон, на основе которого был создан документ
	 * @return результат сравнения
	 */
	protected boolean equalsGrounds(TemplateDocument template)
	{
		if (FormType.JURIDICAL_TRANSFER == template.getFormType())
		{
			return StringHelper.equalsNullIgnore(getGround(), template.getGround());
		}
		return true;
	}

	public void setRegisterNumber(String registerNumber)
	{
		setNullSaveAttributeStringValue(REGISTER_NUMBER_ATTRIBUTE_NAME, registerNumber);
	}

	public void setRegisterString(String registerString)
	{
		setNullSaveAttributeStringValue(REGISTER_STRING_ATTRIBUTE_NAME, registerString);
	}

	public GateDocument asGateDocument()
	{
		if (!isTaxPayment())
			return this;

		if (isFullPayment())
			return this;

		return new TaxTransitPaymentDecorator(this);
	}

	/**
	 * Налоговый платеж
	 * @return да/нет
	 */
	public boolean isTaxPayment()
	{
		return Boolean.parseBoolean(getNullSaveAttributeStringValue(TAX_PAYMENT_ATTRIBUTE_NAME));
	}

	/**
	 * Код КБК
	 * Domain: KBK
	 *
	 * @return кбк
	 */
	public String getTaxKBK()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_KBK_ATTRIBUTE_NAME);
	}

	/**
	 * Код ОКАТО
	 * Domain: OKATO
	 *
	 * @return окато
	 */
	public String getTaxOKATO()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_OKATO_ATTRIBUTE_NAME);
	}

	/**
	 * Дата налогового документа
	 * Domain: Date
	 *
	 * @return дата
	 */
	public Calendar getTaxDocumentDate()
	{
		String taxDocumentDate = getNullSaveAttributeStringValue(TAX_PAYMENT_DOCUMENT_DATE_ATTRIBUTE_NAME);

		if (!StringHelper.isEmpty(taxDocumentDate))
		{
           try
		   {
		      return DateHelper.fromDMYDateToDate(taxDocumentDate);
		   }
		   catch (ParseException ignored)
		   {
		      return null;
		   }
		}

		return null;
	}

	/**
	 * Номер налогового документа
	 * Domain: Text
	 *
	 * @return номер
	 */
	public String getTaxDocumentNumber()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_DOCUMENT_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * Налоговый период
	 * Domain: TaxPeriod
	 *
	 * @return период
	 */
	public String getTaxPeriod()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PERIOD);
	}

	/**
	 * Основание налогового платежа
	 * Domain: Text
	 *
	 * @return основание
	 */
	public String getTaxGround()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_GROUND_ATTRIBUTE_NAME);
	}

	/**
	 * Тип налогового платежа
	 * Domain: Text
	 *
	 * @return тип
	 */
	public String getTaxPaymentType()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_TYPE);
	}

	/**
	 * Статаус составителя налог.платежа
	 * Domain: Text
	 *
	 * @return тип
	 */
	public String getTaxPaymentStatus()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_STATUS);
	}

		/**
	 * Почтовый индекс проживания платильщика платежа
	 * @return - postal code
	 */
	public String getPayerPostalCode()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_POSTAL_CODE);
	}

	/**
	 * Район проживания платильщика платежа
	 * @return - district
	 */
	public String getPayerDistrict()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_DISTRICT);
	}

	/**
	 * Город проживания платильщика платежа
	 * @return - city
	 */
	public String getPayerCity()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_CITY);
	}

	/**
	 * Улица проживания платильщика платежа
	 * @return - street
	 */
	public String getPayerStreet()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_STREET);
	}

	/**
	 * Дом проживания платильщика платежа
	 * @return - house
	 */
	public String getPayerHouse()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_HOUSE);
	}

	/**
	 * Корпус дома платильщика платежа
	 * @return - building
	 */
	public String getPayerBuilding()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_BUILDING);
	}

	/**
	 * Номер квартиры платильщика платежа
	 * @return - flat
	 */
	public String getPayerFlat()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_FLAT);
	}

	/**
	 * @return true - платеж полноформатный
	 */
	public boolean isFullPayment()
	{
		String fullPayment = getNullSaveAttributeStringValue(TAX_PAYMENT_FULL_PAYMENT_PARAMETER);
		return Boolean.parseBoolean(StringHelper.isEmpty(fullPayment) ? "true" : fullPayment); //если fullPayment is null (т.е. получатель не из базы), по-эт. полноформатный платеж
	}

	public void setFullPayment(boolean value)
	{
		setNullSaveAttributeBooleanValue(TAX_PAYMENT_FULL_PAYMENT_PARAMETER, value);
	}

	public String getTransitBankName()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_NAME_PARAMETER);
	}

	public void setTransitBankName(String value)
	{
		setNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_NAME_PARAMETER, value);
	}

	public String getTransitBankBIC()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_BIC_PARAMETER);
	}

	public void setTransitBankBIC(String value)
	{
		setNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_ACCOUNT_PARAMETER, value);
	}

	public String getTransitBankAccount()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_ACCOUNT_PARAMETER);
	}

	public void setTransitBankAccount(String value)
	{
		setNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_ACCOUNT_PARAMETER, value);
	}

	public void setTransitBankCorAccount(String value)
	{
		setNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_CORRACCOUNT_ATTRIBUTE_NAME, value);
	}

	public String getTransitBankCorAccount()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_TRANSIT_BANK_CORRACCOUNT_ATTRIBUTE_NAME);
	}

	public String getDepoLinkId()
	{
		return getNullSaveAttributeStringValue(DEPO_LINK_ID);
	}

	public String getDepoDebtRecNumber()
	{
		return getNullSaveAttributeStringValue(DEPO_DEBT_REC_NUMBER);
	}

	public boolean isDepoPayment()
	{
		return !StringHelper.isEmpty(getDepoLinkId());
	}

	public String getMobileNumber()
	{
		return getNullSaveAttributeStringValue(MOBILE_NUMBER_ATTRIBUTE_NAME);
	}

	public Long getContactId()
	{
		return getNullSaveAttributeLongValue(RECEIVER_CONTACT);
	}

	public void setContactId(Long value)
	{
		setNullSaveAttributeLongValue(RECEIVER_CONTACT, value);
	}

	public String getSocialId()
	{
		return getNullSaveAttributeStringValue(SOCIAL_ID_ATTRIBUTE_NAME);
	}

	public void setMobileNumber(String value)
	{
		setNullSaveAttributeStringValue(MOBILE_NUMBER_ATTRIBUTE_NAME, value);
	}

	/**
	 * Вернуть ссылку на счет депо
	 * Внимание: ссылка возвращается по текущему состоянию системы,
	 * т.е. может отсутствовать для старых документов (в данном случае возвращается null)
	 * @return ссылка или null, если линк удалён либо не задан
	 */
	public DepoAccountLink getDepoLink() throws DocumentException
	{
		String linkId = getDepoLinkId();
		if (StringHelper.isEmpty(linkId))
			return null;
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return externalResourceService.findInSystemLinkById(documentOwner.getLogin(), DepoAccountLink.class, Long.parseLong(linkId));
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Сбой при получении линка-на-источник счета депо", e);
		}
	}

	/**
	 * Установить флажок об ограничении получения информации о получателе
	 * @param flag флажок
	 */
	public void setRestrictReceiverInfoByPhone(Boolean flag)
	{
		setNullSaveAttributeBooleanValue(RESTRICT_PERSON_IFO_BY_PHONE, flag);
	}

	/**
	 * @return true - сработало ограничение на информацию о получателе
	 */
	public Boolean hasRestrictReceiverInfoByPhone()
	{
		return (Boolean) getNullSaveAttributeValue(RESTRICT_PERSON_IFO_BY_PHONE);
	}

	public Boolean getInfoAvailableCardWasShow()
	{
		return (Boolean) getNullSaveAttributeValue(INFO_AVAIBLE_CARD_WAS_SHOW);
	}

	public void setInfoAvailableCardWasShow(Boolean flag)
	{
		setNullSaveAttributeBooleanValue(INFO_AVAIBLE_CARD_WAS_SHOW, flag);
	}

	public ReceiverCardType getReceiverCardType()
	{
		String receiverSubType = getReceiverSubType();

		if(OUR_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType) || isOurBankCard())
			return ReceiverCardType.SB;

		if(VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType))
			return ReceiverCardType.VISA;

		if(MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType))
			return ReceiverCardType.MASTERCARD;

		if (OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType))
		{
			String receiverCard = getReceiverCard();

			if (StringHelper.isEmpty(receiverCard))
				throw new FormException("Не задана карта получения");

			if (receiverCard.startsWith("4"))
				return ReceiverCardType.VISA;

			if (receiverCard.startsWith("5") || receiverCard.startsWith("6"))
				return ReceiverCardType.MASTERCARD;
		}
		return null;
	}

	/**
	 * Возвращает текст для смс-сообщения получателю перевода, если он задан на форме редактирования
	 * @return текст для смс-сообщения или пустая строка
	 */
	public String getMessageToReceiver()
	{
		return getNullSaveAttributeStringValue(MESSAGE_TO_RECEIVER);		
	}

    public void setMessageToReceiverStatus(String status)
    {
        setNullSaveAttributeStringValue(MESSAGE_TO_RECEIVER_STATUS, status);
    }

	/**
	 * @return код источника списания
	 */
	public String getFromResourceLink()
	{
		return getNullSaveAttributeStringValue(FROM_RESOURCE_LINK);
	}

	/**
	 * @return код источника списания
	 */
	public String getFromResource()
	{
		return getNullSaveAttributeStringValue(FROM_RESOURCE);
	}

	public AbstractAccountsTransfer createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		RurPayment payment = (RurPayment) super.createCopy(instanceClass);
		if (isServiceProviderPayment())
		{
			copyExtendedFields(payment);

			payment.setReceiverPointCode(getReceiverPointCode());
			payment.setReceiverInternalId(getReceiverInternalId());
			payment.setIdFromPaymentSystem(null);
		}
		initNewRurPaymentParameters(payment);
		return payment;
	}
	/**
	 * скопировать описание расширенных полей из текущего платежа в переданный
	 * @param payment источник копирования.
	 * @throws DocumentException
	 */
	protected void copyExtendedFields(RurPayment payment) throws DocumentException
	{
		payment.setExtendedFieldsAsString(ExtendedFieldsHelper.rebuild(extendedFieldsAsString));
	}

	protected String getDefaultTemplateName(Metadata metadata) throws DocumentException
	{
		String templateName = null;
		if (StringHelper.isNotEmpty(getReceiverFirstName()) && StringHelper.isNotEmpty(getReceiverSurName())
			&& FormType.INDIVIDUAL_TRANSFER != getFormType() && FormType.INDIVIDUAL_TRANSFER_NEW != getFormType())
		{
			templateName = getReceiverFirstName() + " " +  getReceiverPatrName() + " " + String.format("%s.", getReceiverSurName().charAt(0));
		}

		if (StringHelper.isEmpty(templateName))
		{
			return super.getDefaultTemplateName(metadata);
		}
		return templateName;
	}

	/**
	 * @return номер телефона получателя (контакта из АК)
	 */
	public String getContactPhone() throws DocumentException
	{
		Long contactId = getContactId();
		if (contactId == null)
			throw new DocumentException("Не задан идентификатор контакта");

		return findContact(contactId).getPhone();
	}

	//номер карты получателя
	public String getReceiverCardNumber()
	{
		return getNullSaveAttributeStringValue(RECEIVER_CARD);
	}
	//номер телефона получателя
	public String getReceiverPhoneNumber()
	{
		return getNullSaveAttributeStringValue(MOBILE_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * Определяет является ли Сбербанк банком-эмитентом
	 * @param cardNumber
	 * @return
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private boolean isSBRFCardIssuerBank(String cardNumber) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (StringHelper.isEmpty(cardNumber))
				return false;
			Pair<String, Office> request = new Pair<String, Office>(cardNumber, null);
			ActivePerson person = null;
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if (PersonContext.isAvailable())
				person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			else
				person = documentOwner.getPerson();
			if (person == null)
				return false;

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			GroupResult<Pair<String, Office>, Card> result = bankrollService.getCardByNumber(person.asClient(), request);
			Card card = GroupResultHelper.getOneResult(result);
			return (card != null);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	public String getBillingDocumentNumber()
	{
		return billingDocumentNumber;
	}

	public void setBillingDocumentNumber(String id)
	{
		this.billingDocumentNumber = id;
	}

	/**
	 * Является ли документ платежом на Яндекс.Деньги
	 * @return true - платеж на Яндекс.Деньги
	 */
	public boolean isServiceProviderPayment()
	{
		return YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(getReceiverSubType()) || OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(getReceiverSubType())|| BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(getReceiverSubType());
	}

	/**
	 * получить запись о поставщике из БД.
	 * В текущей реализации используется для оплаты Яндекс.Деньги
	 * @return запись о поставщике яндекс деньги
	 */
	public ServiceProviderBase getServiceProvider() throws DocumentException
	{
		if (yandexProvider != null)
			return yandexProvider;

		yandexProvider = ConfigFactory.getConfig(ProvidersConfig.class).getYandexProvider();

		return yandexProvider;
	}

	public Service getService()
	{
		String code = getNullSaveAttributeStringValue(CODE_SERVICE_ATTRIBUTE_NAME);
		String name = getNullSaveAttributeStringValue(NAME_SERVICE_ATTRIBUTE_NAME);
		return new ServiceImpl(code, name);
	}

	public void setService(Service service)
	{
		if (service == null)
			throw new FormException("Не задана услуга");

		setNullSaveAttributeStringValue(CODE_SERVICE_ATTRIBUTE_NAME, service.getCode());
		setNullSaveAttributeStringValue(NAME_SERVICE_ATTRIBUTE_NAME, service.getName());
	}

	public String getIdFromPaymentSystem()
	{
		return getBillingDocumentNumber();
	}

	public void setIdFromPaymentSystem(String id)
	{
		setBillingDocumentNumber(id);
	}

	public String getSalesCheck()
	{
		return getNullSaveAttributeStringValue(SALES_CHECK_ATTRIBUTE_NAME);
	}

	public void setSalesCheck(String salesCheck)
	{
		setNullSaveAttributeStringValue(SALES_CHECK_ATTRIBUTE_NAME, salesCheck);
	}

	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	/**
	 * Установить межблочный идентификатор поставщика услуг
	 * @param multiBlockReceiverPointCode - межблочный идентификатор поставщика услуг
	 */
	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	/**
	 * @return внутреннний идентфикатор поставщика услуг
	 */
	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	/**
	 * установить идентификатор поставщика
	 * @param receiverInternalId идентификатор поставщика
	 */
	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}


	public String getBillingClientId()
	{
		return null;
	}

	public List<Field> getExtendedFields() throws DocumentException
	{
		List<Field> recipientFields = ExtendedFieldsHelper.deserialize(getExtendedFieldsAsString());
		if (recipientFields == null)
		{
			return null;
		}
		for (Field recipientField : recipientFields)
		{
			recipientField.setValue(getPaymentFieldValue(recipientField));
		}
		return recipientFields;
	}

	public void setExtendedFields(List<Field> extendedFields) throws DocumentException
	{
		setExtendedFieldsAsString(ExtendedFieldsHelper.serialize(ExtendedFieldsHelper.getFormat(extendedFieldsAsString), extendedFields));
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

	public String getReceiverPhone()
	{
		return null;
	}

	public void setReceiverPhone(String receiverPhone)
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

	public String getBillingCode()
	{
		return null;
	}

	public void setBillingCode(String billingCode)
	{
	}

	public String getFundResponseId()
	{
		return getNullSaveAttributeStringValue(FUND_RESPONSE_ID_ATTRIBUTE_NAME);
	}

	private void initFundPayment() throws DocumentException
	{
		if (getFundResponse() != null)
		{
			setInputSumType(InputSumType.DESTINATION);
			setNullSaveAttributeBooleanValue(IS_CARD_TRANSFER, true);
			setReceiverAccount(getFundResponse().getToResource());
		}
	}

	private FundSenderResponse getFundResponse() throws DocumentException
	{
		if (fundResponse != null)
			return fundResponse;

		try
		{
			//если платеж-сбор средств
			if(StringHelper.isNotEmpty(getFundResponseId()))
			{
				fundResponse = senderResponseService.getByExternalId(getFundResponseId());
				if (fundResponse == null)
					throw new DocumentException("По указанному идентификатору краудгифтингового ответа не найдено данных. Операция недоступна.");
				return fundResponse;
			}
			return null;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Проверка, платёж совершён в рамках краудгифтинга или нет.
	 * @return Да, если плажёт был совершён в рамках краудгифтинга. Нет в остальных случаях
	 */
	public boolean isFundPayment()
	{
		return getNullSaveAttributeBooleanValue(IS_FUND_PAYMENT_ATTRIBUTE_NAME);
	}

	/**
	 * Установка флага "платёж совершён в рамках крадгифтинга"
	 * @param isFundPayment Флаг, сигнализирующий, совершён ли платёж в рамках краудгифтинга
	 */
	public void setFundPayment(Boolean isFundPayment)
	{
		setNullSaveAttributeBooleanValue(IS_FUND_PAYMENT_ATTRIBUTE_NAME, isFundPayment);
	}

	/**
	 * сохранить расширенные описания полей в виде строки
	 * @param extendedFieldsAsString описание полей
	 */
	protected void setExtendedFieldsAsString(String extendedFieldsAsString) throws DocumentException
	{
		this.extendedFieldsAsString = extendedFieldsAsString;
	}

	/**
	 * @return описание расширенных полей ввиде строки
	 */
	protected String getExtendedFieldsAsString() throws DocumentException
	{
		return extendedFieldsAsString;
	}

	/**
	 * @return описание расширенных полей ввиде DOM
	 */
	public Document getExtendedFieldsAsDOM() throws DocumentException
	{
		return ExtendedFieldsHelper.deserializeToDOM(extendedFieldsAsString);
	}

	/**
	 * Получить значение поля из документа
	 * @param field поле, для которого нужно получить значение. не может быть null
	 * @return значени
	 */
	protected String getPaymentFieldValue(Field field)
	{
		if (field.isKey())
		{
			String receiverSubType = getReceiverSubType();
			if (YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(receiverSubType))
				return getNullSaveAttributeStringValue(RECEIVER_WALLET_NUMBER);
			else if (BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(receiverSubType) || OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(receiverSubType))
			{
				return getNullSaveAttributeStringValue(field.getExternalId());
			}
			else return null;
		}
		//главная сумма хранится в DestinationAmount
		Money amount = getDestinationAmount();
		if (amount == null)
			return null;

		return amount.getDecimal().toString();
	}

	private void initNewRurPaymentParameters(RurPayment payment) throws DocumentException
	{
		if (FormConstants.NEW_RUR_PAYMENT.equals(payment.getFormName()))
		{
			ServiceProviderBase serviceProvider = payment.getServiceProvider();
			payment.setNullSaveAttributeStringValue(YANDEX_EXIST_ATTRIBUTE_NAME, Boolean.toString(serviceProvider != null));
			payment.setNullSaveAttributeStringValue(YANDEX_MESSAGE_SHOW_ATTRIBUTE_NAME, Boolean.toString(ConfigFactory.getConfig(com.rssl.phizic.business.payments.PaymentsConfig.class).isSendMessageToReceiverYandex()));
		}
	}

	/**
	 * Проверяет есть ли в адресной книге ЕРИБ контакт с таким номером телефона
	 * @param phoneNumber - номер телефона
	 * @return true - есть такой контакт
	 * @throws BusinessException
	 */
	private boolean isContactFromAddressBook(String phoneNumber) throws BusinessException
	{
		if (StringHelper.isEmpty(phoneNumber) ||
				!PersonContext.isAvailable() ||
				!AuthModule.getAuthModule().implies(new ServicePermission("AddressBook")))
			return false;
		try
		{
			String formatPhone = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phoneNumber.trim()));
			Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
			Contact contact = addressBookService.findContactClientByPhone(loginId, formatPhone);
			return contact != null;
		}
		catch (NumberFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getDefaultTemplateName() throws BusinessException, BusinessLogicException
	{
		if (isLongOffer())
		{
			return super.getDefaultTemplateName();
		}

		try
		{
			Metadata metadata = MetadataCache.getBasicMetadata(getFormName());
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return TemplateHelper.addCounter(new ClientImpl(documentOwner.getPerson()), getDefaultTemplateName(metadata), new DefaultNamingStrategy());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение даты рождения получателя
 	 * @return Дата рождения получателя
	 */
	public Calendar getReceiverBirthday()
	{
		return getNullSaveAttributeCalendarValue(RECEIVER_BIRTHDAY);
	}

	/**
	 * Установка даты рождения получателя
	 * @param birthday Дата рождения
	 */
	public void setReceiverBirthday(Calendar birthday)
	{
		 setAttributeValue(ExtendedAttribute.CALENDAR_TYPE, RECEIVER_BIRTHDAY, birthday);
	}

	/**
	 * Получения серии документа получателя
	 * @return Серия документа
	 */
	public String getReceiverDocSeries()
	{
		return getNullSaveAttributeStringValue(RECEIVER_DOCUMENT_SERIES);
	}

	/**
	 * Установка серии домента клиента
	 * @param series Серия документа
	 */
	public void setReceiverDocSeries(String series)
	{
		setNullSaveAttributeStringValue(RECEIVER_DOCUMENT_SERIES, series);
	}

	/**
	 * Получение номера документа получателя
	 * @return Номер документа
	 */
	public String getReceiverDocNumber()
	{
		return getNullSaveAttributeStringValue(RECEIVER_DOCUMENT_NUMBER);
	}

	/**
	 * Установка номера документа получателя
	 * @param number  Номер документа
	 */
	public void setReceiverDocNumber(String number)
	{
		setNullSaveAttributeStringValue(RECEIVER_DOCUMENT_NUMBER, number);
	}
}
