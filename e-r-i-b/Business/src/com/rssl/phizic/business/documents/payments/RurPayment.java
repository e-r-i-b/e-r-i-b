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
	public static final String JUR_RECEIVER_TYPE_VALUE                              = "jur";// ��� ���������� - ����
	public static final String PHIZ_RECEIVER_TYPE_VALUE                             = "ph";// ��� ���������� - �����
	public static final String OUR_CARD_TRANSFER_TYPE_VALUE                         = "ourCard";// ��� �������� "�� ����� ���������"
	public static final String OUR_PHONE_TRANSFER_TYPE_VALUE                        = "ourPhone";// ��� �������� "�� ����� ��������� �� ������ ���������� ��������"
	public static final String SOCIAL_TRANSFER_TYPE_VALUE                           = "social";// ��� �������� "�� ����� ��������� �� �������������� ������� �� ������� ����������"
	public static final String VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE               = "visaExternalCard";// ��� �������� "�� ����� � ������ �����"
	public static final String MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE         = "masterCardExternalCard";// ��� �������� "�� ����� � ������ �����"
	public static final String OUR_ACCOUNT_TYPE_VALUE                               = "ourAccount";// ��� �������� "�� ���� ���������"
	public static final String EXTERNAL_ACCOUNT_TYPE_VALUE                          = "externalAccount";// ��� �������� "�� ���� � ������ �����"
	public static final String OUR_CONTACT_TRANSFER_TYPE_VALUE                      = "ourContact";// ��� �������� "�� ����� ��������� �� ������ ���������� �������� �������� �� ��"
	public static final String OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE        = "ourContactToOtherCard";// ��� �������� "�� ����� ������� ����� �� ������ ����������� ����� �������� � ��"
	public static final String YANDEX_WALLET_TRANSFER_TYPE_VALUE                    = "yandexWallet";// ��� �������� "�� ������ �������"
	public static final String OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE     = "yandexWalletOurContact";// ��� �������� "�� ������ �������" �� �������� �� ��
	public static final String BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE        = "yandexWalletByPhone";// ��� �������� "�� ������ �������" �� ������ ��������
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
	//��������� ������ ������������� �� ����� ����
	public static final String DEPO_LINK_ID                                         = "depo-link-id";
	public static final String DEPO_DEBT_REC_NUMBER                                 = "debt-rec-number";
	public static final String RESTRICT_PERSON_IFO_BY_PHONE                         = "restrict-person-info-by-number";
	//���� �� ������� �������� ���������� � �����������  ���������� �������� �������� �� ������ ����� ���� ���� ���������� ���������� �� 40817 (CHG038996)
	public static final String INFO_AVAIBLE_CARD_WAS_SHOW                           = "info-avaible-card-was-show";

	public static final String MOBILE_NUMBER_ATTRIBUTE_NAME                         = "mobile-number"; //����� �������� ��� �������� ����� �� ������ ��������
	public static final String SOCIAL_ID_ATTRIBUTE_NAME                             = "social-id"; //������������� ������� �� ������� ����������
	public static final String MESSAGE_TO_RECEIVER                                  = "message-to-receiver"; //���-��������� ���������� �������� (��� �������� �� ������ ����� ��� �� ������ ��������)
	public static final String MESSAGE_TO_RECEIVER_STATUS                           = "message-to-receiver-status"; //������ ���-��������� ���������� ��������
	public static final String RECEIVER_CONTACT                                     = "external-contact-id"; //������������� �������� � ��
	public static final String RECEIVER_WALLET_NUMBER                               = "external-wallet-number"; //����� �������� ������

	public static final String RECEIVER_BIRTHDAY = "receiver-birthday"; // ���� �������� ����������
	public static final String RECEIVER_DOCUMENT_SERIES = "receiver-document-series"; // ����� ��������� ����������
	public static final String RECEIVER_DOCUMENT_NUMBER = "receiver-document-number"; // ����� ��������� ����������

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
	 * ����� �����, ���������� �� ������ ��������.
	 */
	private Card cardByPhone;
	private Card cardByResourceCard;
	private Card cardByOurContact;
	private Contact currentContact = null;
	private NotEqualsFIOException notEqualFIO = null;
	private String billingDocumentNumber;
	private String receiverPointCode;               // externalId ���������� � ����� �������� ����������� ������
	private String multiBlockReceiverPointCode;     // ���������� ������������� ���������� �����
	private Long receiverInternalId;                // id ���������� � ����� �������� ����������� ������;
	private ServiceProviderBase yandexProvider;     // ��������� ������.������
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
			//������� ��. ���� � �����
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//���� ��������� ������ c �����
				if(isTaxPayment())
					return CardRUSTaxPayment.class;

				if (isOurBank())
					//�� ���� ������ �����
					return CardJurIntraBankTransfer.class;
				//�� ���� � ������ ���� ����� ��������� ������� ������
				return CardJurTransfer.class;
			}
			//������� ��. ���� �� �����
			if (chargeOffResourceType == ResourceType.ACCOUNT || chargeOffResourceType == ResourceType.NULL)
			{
				//���� ��������� ������ �� �����
				if(isTaxPayment())
					return AccountRUSTaxPayment.class;

				if (isOurBank())
					//�� ���� ������ �����
					return AccountJurIntraBankTransfer.class;
				//�� ���� � ������ ���� ����� ��������� ������� ������
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
			//������� ���. ���� � �����
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//�� �����
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
				{
					// ���� ������� ����������������
					return isOurBank() ? ExternalCardsTransferToOurBank.class : ExternalCardsTransferToOtherBank.class;
				}

				//�� ���� ������ �����
				if (isOurBank())
					return isLongOffer() ? CardIntraBankPaymentLongOffer.class : CardIntraBankPayment.class;
				//�� ���� � ������ ���� ����� ��������� ������� ������
				return isLongOffer() ? CardRUSPaymentLongOffer.class : CardRUSPayment.class;
			}
			//������� ���. ���� �� �����
			if (chargeOffResourceType == ResourceType.ACCOUNT)
			{
				//�� �����
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
					throw new UnsupportedOperationException("������� �� ����� �� ����� ����� �� ��������������.");
				//�� ���� ������ �����
				if (isOurBank())
					return isLongOffer() ? AccountIntraBankPaymentLongOffer.class : AccountIntraBankPayment.class;
				//�� ���� � ������ ���� ����� ��������� ������� ������
				return isLongOffer() ? AccountRUSPaymentLongOffer.class : AccountRUSPayment.class;
			}
		}
		throw new IllegalStateException("��������� ���������� ��� ���������");
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
	 * ��� ����������
	 * @return �����/���� (PHIZ_RECEIVER_TYPE_VALUE/JUR_RECEIVER_TYPE_VALUE)
	 */
	public String getReceiverType()
	{
		return getNullSaveAttributeStringValue(RECEIVER_TYPE_ATTRIBUTE_NAME);
	}
	/**
	 * ������ ����������
	 * @return �� ���� � ���������/�� ����� � ��������/�� ���� � ������ �����
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
	 * ����� ������ � ������� ����� ����� �������������.
	 * ������������ ��� ���������� �������� ���.
	 * @return ����� ����������
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
	 * ���������� ���� ����������.
	 * @param receiverBank ���� ����������
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

	//��������� ������ ��� ������ ������������� �� ����� ����
	private void storeDepoPaymentInfo() throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!AuthModule.getAuthModule().implies(new ServicePermission("DepoDebtPayment")))
				throw new AccessControlException("��� ������� � ��������");
			String recNumber = getDepoDebtRecNumber();
			DepoAccountLink depoLink = externalResourceService.findLinkById(DepoAccountLink.class, Long.parseLong(getDepoLinkId()));
			DepoAccountService depoAccountService = GateSingleton.getFactory().service(DepoAccountService.class);
			DepoAccount depoAccount = depoLink.getDepoAccount();
			if (MockHelper.isMockObject(depoAccount))
				throw new DocumentException("�� ������ ���� ����, ����� " + depoLink.getAccountNumber());
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
				throw new DocumentException("�� ������� ������������� � ������� �����" + recNumber + ", ����� ����� ���� " + depoAccount.getAccountNumber());

			DepoDebtItemInfo depoDebtItemInfo = depoAccountService.getDepoDebtItemInfo(depoAccount, depoDebtItem).getResult(depoDebtItem);
			if (depoDebtItemInfo == null)
				throw new DocumentException("�� ������� ����������� ���������� � ������������� � ������� �����" + recNumber + ", ����� ����� ���� " + depoAccount.getAccountNumber());

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

			if (depoDebtItem.getRecNumber().endsWith("V"))//���� �� ���������� ������
			{
				String text = "������ ����� � %s �� %s �� ������ �� ����� ���� � %s";
				setGround(String.format(text, depoDebtItem.getRecNumber(),
											  String.format("%1$td.%1$tm.%1$tY", depoDebtItem.getRecDate()),
											  depoAccount.getAccountNumber()));
			}
			else //������� ����
			{
				String text = "������ ����� � %s �� %s �� ������ �� ����� ���� � %s, � �.�. ��� %s ������";
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
			throw new DocumentException("�� ������ ��������� �����");

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

	// ��������� ������ �����
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

			//��� �������� � ����� ���� �������� ����� �� ����, ��� �������� � �����
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//��������� ���� � ����� �������� ����� ����������
				setChargeOffCardExpireDate(getChargeOffCardExpireDate(chargeOffResourceType, messageCollector));
			}
			if (destinationResourceType == ResourceType.CARD || destinationResourceType == ResourceType.EXTERNAL_CARD)
			{
				if (isTransferByOurContactToOtherCard)
					saveCardByOurContactToOtherCard();

				//��������� ���� � ����� �������� ����� ����������
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
			// ������ ��� �������� "�� ����� ���������" ��� "������� �� ����� �� ������ ���������� ��������"
			boolean isExternalCard = VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType) ||
					MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType) || isTransferByOurContactToOtherCard;
			boolean isOurCard = OUR_CARD_TRANSFER_TYPE_VALUE.equals(receiverSubType);

			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
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

			// ��� �������� "�� ����� ���������" ��� "������� �� ����� �� ������ ���������� ��������" ��� "�� ����� � ������ �����"
			if (!StringHelper.isEmpty(getReceiverType()))
			{
				// ���� ������� "�� ����� �� ������ ���������� ��������", "������� ������� ��������� �� ���������� �� ��"
				if (isTransferByPhone || isTransferBySocialId || isTransferByOurContact)
				{
					Card card;
					if (isTransferByPhone)
					{
						String mobileNumber = getMobileNumber();
						// ���� ������ ���, �������� ��� �������� �������, �� ������ ������ ������
						if (StringHelper.isEmpty(mobileNumber))
							return;

						//������ �� ������ ��������. ���� ����� ����� mApi
						if (getCreationType() == CreationType.mobile)
						{
							//������� ����������� ������ ���� ������� �� �� �������� �����
							if (!isContactFromAddressBook(mobileNumber) && !userCounterService.increment(documentOwner.getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
								throw new PhoneLimitExceededException("$5$�������� ������������ ����� �������� �� ������ ��������. ����������� ������� �� ������ �����.");
						}
						card = getCardByPhone(getMobileNumber());
						if (getCreationType() == CreationType.mobile)
							updateUserAddressBook(card, mobileNumber);
					}
					else if (isTransferByOurContact)
					{
						card = getCardByOurContact();
						//������ �� ������ ��������. ���� ����� ����� mApi
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
								throw new DocumentLogicException("�������� �� ���������. �� ������� ����� ���������� �� ���������� ������ ��������.");
							throw new DocumentLogicException("�� ������� �������� ����� �������� �� ���������� ������ ��������");
						}

						ActivePerson owner = documentOwner.getPerson();
						if(!new CheckCurConditionByCardNumValidator().validateCurConditionByCardNumber(card.getNumber(), owner))
							throw new DocumentLogicException("���������� ������ �������� �� ������ ����� ������ �� ���� �������� ����� (�����).");

						PaymentAbilityERL paymentAbilityERL = getChargeOffResourceLink();
						if(paymentAbilityERL != null && card.getNumber().equals(paymentAbilityERL.getNumber()))
							throw new DocumentLogicException("���� �������� � ���� ���������� ������ ���� ��������.");

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
					// ���� ������� ��� ���� �� ������ �������� �� �� ��������� ��� ����������, ���������� ���� � UpdateReceiverInfoByPhoneHandler
					// � ������ �������� CHG041044
					//fillReceiverInfoByCardOwner(card);
					if (isTransferBySocialId)
						fillReceiverInfoByCardOwner(card);
				}

				// ������� "�� ����� ���������"
				else if (isOurCard)
				{
					// ���� ����� ���(�������� ��� �������� �������) �� �������
					if(StringHelper.isEmpty(getReceiverCard()))
						return;

					fillReceiverInfoByCardOwner(receiveDestinationCard(messageCollector));
				}

				// ������� "�� ����� � ������ �����"
				else if(isExternalCard)
				{
					// ���� ����� ���(�������� ��� �������� �������) �� �������
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
			// ��� ������������� ���� ������, ������� ����������� ������� � ���� �����
			log.warn(e);
		}
		catch (DocumentLogicException e)
		{
			if(updateState != InnerUpdateState.INIT)
				throw e;
			// ��� ������������� ���� ������, ������� ����������� ������� � ���� �����
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

		//������������� ������� �� ����� ���������� �� ������ �����
		if ( StringHelper.isNotEmpty(externalCardNumber) )
		{
			//����������� ����� �������� �����, � ���� ���������� �������
			if (!isSBRFCardIssuerBank(externalCardNumber))
			{
				if (externalCardNumber.startsWith("4") || externalCardNumber.startsWith("5") || externalCardNumber.startsWith("6"))
					//�� ����� VISA ������� ����� �� ������ ����������� ����� �������� �� �������� ����� (��) ����
					receiverSubType = RurPayment.VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;
				else if (externalCardNumber.startsWith("5") || externalCardNumber.startsWith("6"))
					//�� ����� Mastercard ������� ����� �� ������ ����������� ����� �������� �� �������� ����� (��) ����
					receiverSubType = RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;
				else
					//�� ����� ������� ����� �� ������ ����������� ����� �������� �� �������� ����� (��) ����
					receiverSubType = RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE;
			}
		}
		//���� �������� �� �������� �� ��
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
	 * ���������� ������ ��� �������� �����.
	 *
	 * @param mobileNumber ����� ���������� ��������.
	 */
	private void updateUserAddressBook(Card card, String mobileNumber) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (card == null)
				return;

			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
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
	 * ���� ��� �������� � �������� ����� �� ��������� � ���, ��������� �� WAY.
	 *
	 * @return ������, ���� �������� ����.
	 */
	public NotEqualsFIOException getNotEqualFIO()
	{
		return notEqualFIO;
	}

	/**
	 * @return true -- ������� �� ����� ������� ����� �� ����� ���� �������� ��������� � ��������
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
	 * �������� ����� �� ������ ��������
	 * @param  phone - ����� ��������
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
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		try
		{
			Office office = null;
			if (ApplicationUtil.isMobileApi())
				office = documentOwner.getPerson().asClient().getOffice();
			else
			{
				if (link == null)
					throw new IllegalStateException("�� ����� �������� ��������");
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
	 * �������� ����� �� �������������� ��������
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
	 * ���������� � ������ ����������� ����� �������� �� ��
	 * @return Card
	 */
	private void saveCardByOurContactToOtherCard() throws DocumentException, DocumentLogicException
	{
		Long contactId = getContactId();
		if (contactId == null)
			throw new DocumentException("�� ����� ������������� ��������");

		Contact tmp = findContact(contactId);
		String cardNumber = tmp.getCardNumber();
		if (StringHelper.isEmpty(cardNumber))
			throw new DocumentLogicException("������� ������� ���������� ��� ������� ������ ����� ����� �������");

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
				throw new DocumentException("������� � id=" + contactId + " �� ������ � �������� ����� ������� = " + getOwner().getPerson());

			currentContact = result;
			return currentContact;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * �������� ����� �� ������� ��������
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
				throw new IllegalStateException("�� ����� �������� ��������");
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
	 * �������� ������ ���� ��� �������� �.
	 * @param client ������.
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
	 * ���������� ���������� � ���������� �� ��� �����
	 * @param card �����, �� ������� ����� ���������� ����
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

			//������ ��� ���� ������� �� �������.
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
			log.error("�������� ��� ��������� ��� ������������ ����������", ex);
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
	 * �������� ��������� �� �������� �����. ���� � ����������� ��������� �� ��������� �������� ����,
	 *  � � �������� ���������, ��������� ����������.
	 * @param template ������, �� ������ �������� ��� ������ ��������
	 * @return true - �������� ���� � ���������� ����������, false - �������� ���� ������
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
	 * ���������� ���� "���������� �������"
	 * @param template ������, �� ������ �������� ��� ������ ��������
	 * @return ��������� ���������
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
	 * ��������� ������
	 * @return ��/���
	 */
	public boolean isTaxPayment()
	{
		return Boolean.parseBoolean(getNullSaveAttributeStringValue(TAX_PAYMENT_ATTRIBUTE_NAME));
	}

	/**
	 * ��� ���
	 * Domain: KBK
	 *
	 * @return ���
	 */
	public String getTaxKBK()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_KBK_ATTRIBUTE_NAME);
	}

	/**
	 * ��� �����
	 * Domain: OKATO
	 *
	 * @return �����
	 */
	public String getTaxOKATO()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_OKATO_ATTRIBUTE_NAME);
	}

	/**
	 * ���� ���������� ���������
	 * Domain: Date
	 *
	 * @return ����
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
	 * ����� ���������� ���������
	 * Domain: Text
	 *
	 * @return �����
	 */
	public String getTaxDocumentNumber()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_DOCUMENT_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * ��������� ������
	 * Domain: TaxPeriod
	 *
	 * @return ������
	 */
	public String getTaxPeriod()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PERIOD);
	}

	/**
	 * ��������� ���������� �������
	 * Domain: Text
	 *
	 * @return ���������
	 */
	public String getTaxGround()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_GROUND_ATTRIBUTE_NAME);
	}

	/**
	 * ��� ���������� �������
	 * Domain: Text
	 *
	 * @return ���
	 */
	public String getTaxPaymentType()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_TYPE);
	}

	/**
	 * ������� ����������� �����.�������
	 * Domain: Text
	 *
	 * @return ���
	 */
	public String getTaxPaymentStatus()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_STATUS);
	}

		/**
	 * �������� ������ ���������� ����������� �������
	 * @return - postal code
	 */
	public String getPayerPostalCode()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_POSTAL_CODE);
	}

	/**
	 * ����� ���������� ����������� �������
	 * @return - district
	 */
	public String getPayerDistrict()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_DISTRICT);
	}

	/**
	 * ����� ���������� ����������� �������
	 * @return - city
	 */
	public String getPayerCity()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_CITY);
	}

	/**
	 * ����� ���������� ����������� �������
	 * @return - street
	 */
	public String getPayerStreet()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_STREET);
	}

	/**
	 * ��� ���������� ����������� �������
	 * @return - house
	 */
	public String getPayerHouse()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_HOUSE);
	}

	/**
	 * ������ ���� ����������� �������
	 * @return - building
	 */
	public String getPayerBuilding()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_BUILDING);
	}

	/**
	 * ����� �������� ����������� �������
	 * @return - flat
	 */
	public String getPayerFlat()
	{
		return getNullSaveAttributeStringValue(TAX_PAYMENT_PAYER_FLAT);
	}

	/**
	 * @return true - ������ ��������������
	 */
	public boolean isFullPayment()
	{
		String fullPayment = getNullSaveAttributeStringValue(TAX_PAYMENT_FULL_PAYMENT_PARAMETER);
		return Boolean.parseBoolean(StringHelper.isEmpty(fullPayment) ? "true" : fullPayment); //���� fullPayment is null (�.�. ���������� �� �� ����), ��-��. �������������� ������
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
	 * ������� ������ �� ���� ����
	 * ��������: ������ ������������ �� �������� ��������� �������,
	 * �.�. ����� ������������� ��� ������ ���������� (� ������ ������ ������������ null)
	 * @return ������ ��� null, ���� ���� ����� ���� �� �����
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
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			return externalResourceService.findInSystemLinkById(documentOwner.getLogin(), DepoAccountLink.class, Long.parseLong(linkId));
		}
		catch (BusinessException e)
		{
			throw new DocumentException("���� ��� ��������� �����-��-�������� ����� ����", e);
		}
	}

	/**
	 * ���������� ������ �� ����������� ��������� ���������� � ����������
	 * @param flag ������
	 */
	public void setRestrictReceiverInfoByPhone(Boolean flag)
	{
		setNullSaveAttributeBooleanValue(RESTRICT_PERSON_IFO_BY_PHONE, flag);
	}

	/**
	 * @return true - ��������� ����������� �� ���������� � ����������
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
				throw new FormException("�� ������ ����� ���������");

			if (receiverCard.startsWith("4"))
				return ReceiverCardType.VISA;

			if (receiverCard.startsWith("5") || receiverCard.startsWith("6"))
				return ReceiverCardType.MASTERCARD;
		}
		return null;
	}

	/**
	 * ���������� ����� ��� ���-��������� ���������� ��������, ���� �� ����� �� ����� ��������������
	 * @return ����� ��� ���-��������� ��� ������ ������
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
	 * @return ��� ��������� ��������
	 */
	public String getFromResourceLink()
	{
		return getNullSaveAttributeStringValue(FROM_RESOURCE_LINK);
	}

	/**
	 * @return ��� ��������� ��������
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
	 * ����������� �������� ����������� ����� �� �������� ������� � ����������
	 * @param payment �������� �����������.
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
	 * @return ����� �������� ���������� (�������� �� ��)
	 */
	public String getContactPhone() throws DocumentException
	{
		Long contactId = getContactId();
		if (contactId == null)
			throw new DocumentException("�� ����� ������������� ��������");

		return findContact(contactId).getPhone();
	}

	//����� ����� ����������
	public String getReceiverCardNumber()
	{
		return getNullSaveAttributeStringValue(RECEIVER_CARD);
	}
	//����� �������� ����������
	public String getReceiverPhoneNumber()
	{
		return getNullSaveAttributeStringValue(MOBILE_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * ���������� �������� �� �������� ������-���������
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
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
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
	 * �������� �� �������� �������� �� ������.������
	 * @return true - ������ �� ������.������
	 */
	public boolean isServiceProviderPayment()
	{
		return YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(getReceiverSubType()) || OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(getReceiverSubType())|| BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(getReceiverSubType());
	}

	/**
	 * �������� ������ � ���������� �� ��.
	 * � ������� ���������� ������������ ��� ������ ������.������
	 * @return ������ � ���������� ������ ������
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
			throw new FormException("�� ������ ������");

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
	 * ���������� ���������� ������������� ���������� �����
	 * @param multiBlockReceiverPointCode - ���������� ������������� ���������� �����
	 */
	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	/**
	 * @return ����������� ������������ ���������� �����
	 */
	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	/**
	 * ���������� ������������� ����������
	 * @param receiverInternalId ������������� ����������
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
			//���� ������-���� �������
			if(StringHelper.isNotEmpty(getFundResponseId()))
			{
				fundResponse = senderResponseService.getByExternalId(getFundResponseId());
				if (fundResponse == null)
					throw new DocumentException("�� ���������� �������������� ����������������� ������ �� ������� ������. �������� ����������.");
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
	 * ��������, ����� �������� � ������ ������������� ��� ���.
	 * @return ��, ���� ����� ��� �������� � ������ �������������. ��� � ��������� �������
	 */
	public boolean isFundPayment()
	{
		return getNullSaveAttributeBooleanValue(IS_FUND_PAYMENT_ATTRIBUTE_NAME);
	}

	/**
	 * ��������� ����� "����� �������� � ������ ������������"
	 * @param isFundPayment ����, ���������������, �������� �� ����� � ������ �������������
	 */
	public void setFundPayment(Boolean isFundPayment)
	{
		setNullSaveAttributeBooleanValue(IS_FUND_PAYMENT_ATTRIBUTE_NAME, isFundPayment);
	}

	/**
	 * ��������� ����������� �������� ����� � ���� ������
	 * @param extendedFieldsAsString �������� �����
	 */
	protected void setExtendedFieldsAsString(String extendedFieldsAsString) throws DocumentException
	{
		this.extendedFieldsAsString = extendedFieldsAsString;
	}

	/**
	 * @return �������� ����������� ����� ����� ������
	 */
	protected String getExtendedFieldsAsString() throws DocumentException
	{
		return extendedFieldsAsString;
	}

	/**
	 * @return �������� ����������� ����� ����� DOM
	 */
	public Document getExtendedFieldsAsDOM() throws DocumentException
	{
		return ExtendedFieldsHelper.deserializeToDOM(extendedFieldsAsString);
	}

	/**
	 * �������� �������� ���� �� ���������
	 * @param field ����, ��� �������� ����� �������� ��������. �� ����� ���� null
	 * @return �������
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
		//������� ����� �������� � DestinationAmount
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
	 * ��������� ���� �� � �������� ����� ���� ������� � ����� ������� ��������
	 * @param phoneNumber - ����� ��������
	 * @return true - ���� ����� �������
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
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			return TemplateHelper.addCounter(new ClientImpl(documentOwner.getPerson()), getDefaultTemplateName(metadata), new DefaultNamingStrategy());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ���� �������� ����������
 	 * @return ���� �������� ����������
	 */
	public Calendar getReceiverBirthday()
	{
		return getNullSaveAttributeCalendarValue(RECEIVER_BIRTHDAY);
	}

	/**
	 * ��������� ���� �������� ����������
	 * @param birthday ���� ��������
	 */
	public void setReceiverBirthday(Calendar birthday)
	{
		 setAttributeValue(ExtendedAttribute.CALENDAR_TYPE, RECEIVER_BIRTHDAY, birthday);
	}

	/**
	 * ��������� ����� ��������� ����������
	 * @return ����� ���������
	 */
	public String getReceiverDocSeries()
	{
		return getNullSaveAttributeStringValue(RECEIVER_DOCUMENT_SERIES);
	}

	/**
	 * ��������� ����� ������� �������
	 * @param series ����� ���������
	 */
	public void setReceiverDocSeries(String series)
	{
		setNullSaveAttributeStringValue(RECEIVER_DOCUMENT_SERIES, series);
	}

	/**
	 * ��������� ������ ��������� ����������
	 * @return ����� ���������
	 */
	public String getReceiverDocNumber()
	{
		return getNullSaveAttributeStringValue(RECEIVER_DOCUMENT_NUMBER);
	}

	/**
	 * ��������� ������ ��������� ����������
	 * @param number  ����� ���������
	 */
	public void setReceiverDocNumber(String number)
	{
		setNullSaveAttributeStringValue(RECEIVER_DOCUMENT_NUMBER, number);
	}
}
