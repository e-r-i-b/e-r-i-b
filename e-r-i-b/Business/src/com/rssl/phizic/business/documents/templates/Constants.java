package com.rssl.phizic.business.documents.templates;

/**
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final int MAX_TEMPLATE_NAME_SIZE                              = 50;

	public static final String GATE_TEMPLATE_CACHE_NAME                         = "gate-template-cache-name";
	public static final String GATE_TEMPLATE_STORE_NAME                         = "gate-template-store-name-";

	public static final String EDIT_EVENT_ATTRIBUTE_NAME                        = "edit-event-enabled";

	public static final String TEMPLATE_DATA_STORE_KEY                          = "edit.template.operation.store.key";
	public static final String TEMPLATE_OPERATION_ERROR_MESSAGE                 = "По техническим причинам операция временно недоступна. Повторите попытку позже";
	public static final String OLD_TEMPLATE_ERROR_MESSAGE                       = "По этому шаблону Вы можете совершать платежи только с Ваших счетов. Для оплаты с банковских карт, пожалуйста, создайте новый шаблон.";
	public static final String ACCOUNT_TEMPLATE_ERROR_MESSAGE                   = "По этому шаблону Вы можете совершать платежи только с Ваших карт.";
	public static final String EXTERNAL_ACCOUNT_PAYMENT_ERROR_MESSAGE           = "Уважаемый клиент, операция возможна только с банковской карты.";
	public static final String EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE  = "Уважаемый клиент, операция возможна только с банковской карты. Пожалуйста, создайте новый шаблон.";
	public static final String DUPLICATE_TEMPLATE_NAME_ERROR_MESSAGE            = "Шаблон %s уже создан в системе. Вы можете его удалить или восстановить, позвонив в Контактный центр банка по телефону 8 (800) 555 5550.";
	public static final String NOT_FOUND_PROVIDER_ERROR_MESSAGE                 = "Поставщик услуг multiBlockReceiverPointCode  = %s в блоке %s не найден, операции по шаблону id = %s запрещены.";
	public static final String NOT_FOUND_TEMPLATE_ID_ERROR_MESSAGE              = "Для шаблона OperationUID = %s не задан Id.";
	public static final String NOT_FOUND_OPERATION_UID_ERROR_MESSAGE            = "Для шаблона name = %s не задан OperationUID.";

	public static final String SHORT_YEAR_FORMAT                                = "dd.MM.yy";
	public static final String DATE_FORMAT                                      = "dd.MM.yyyy";
	public static final String TIME_FORMAT                                      = "HH:mm:ss";

	public static final String OPERATION_DATE_ATTRIBUTE_NAME                    = "operationDate";
	public static final String OPERATION_STATE_ATTRIBUTE_NAME                   = "state";
	public static final String OPERATION_STATE_DESCRIPTION_ATTRIBUTE_NAME       = "state-description";
	public static final String CLIENT_TRANSACTION_ID                            = "client-transaction-id";


	//общие поля платежей
	public static final String CREATION_DATE_ATTRIBUTE_NAME                     = "creationDate";
	public static final String DOCUMENT_TIME_ATTRIBUTE_NAME                     = "documentTime";
	public static final String DOCUMENT_DATE_ATTRIBUTE_NAME                     = "documentDate";
	public static final String DOCUMENT_NUMBER_ATTRIBUTE_NAME                   = "documentNumber";
	public static final String OPERATION_CODE_ATTRIBUTE_NAME                    = "operationCode";
	public static final String GROUND_ATTRIBUTE_NAME                            = "ground";
	public static final String IS_CARD_TRANSFER_ATTRIBUTE_NAME                  = "isCardTransfer";
	public static final String IS_OUR_BANK_ATTRIBUTE_NAME                       = "isOurBank";
	public static final String IS_EXTERNAL_CARD_ATTRIBUTE_NAME                  = "externalCard";
	public static final String AUTHORIZE_CODE_ATTRIBUTE_NAME                    = "AUTHORIZE_CODE";
	public static final String AUTHORIZE_DATE_ATTRIBUTE_NAME                    = "AUTHORIZE_DATE";
	public static final String MB_OPER_CODE_ATTRIBUTE_NAME                      = "mbOperCode";
	public static final String NODE_NUMBER_ATTRIBUTE_NAME                       = "nodeNumber";
	public static final String TEMPORARY_NODE_ID_ATTRIBUTE_NAME                 = "temporaryNodeId";

	//поля ресурса списания/зачисления
	public static final String FROM_RESOURCE_ATTRIBUTE_NAME                     = "fromResource";
	public static final String TO_RESOURCE_ATTRIBUTE_NAME                       = "toResource";
	public static final String FROM_RESOURCE_TYPE_ATTRIBUTE_NAME                = "fromResourceType";
	public static final String TO_RESOURCE_TYPE_ATTRIBUTE_NAME                  = "toResourceType";
	public static final String FROM_RESOURCE_CURRENCY_ATTRIBUTE_NAME            = "fromResourceCurrency";
	public static final String TO_RESOURCE_CURRENCY_ATTRIBUTE_NAME              = "toResourceCurrency";

	//поля счетов списания/зачисления
	public static final String FROM_ACCOUNT_TYPE_ATTRIBUTE_NAME                 = "fromAccountType";
	public static final String TO_ACCOUNT_TYPE_ATTRIBUTE_NAME                   = "toAccountType";
	public static final String FROM_ACCOUNT_NAME_ATTRIBUTE_NAME                 = "fromAccountName";
	public static final String TO_ACCOUNT_NAME_ATTRIBUTE_NAME                   = "toAccountName";
	public static final String FROM_ACCOUNT_NUMBER_ATTRIBUTE_NAME               = "fromAccountNumber";
	public static final String TO_ACCOUNT_NUMBER_ATTRIBUTE_NAME                 = "toAccountNumber";
	public static final String FROM_ACCOUNT_SELECT_ATTRIBUTE_NAME               = "fromAccountSelect";
	public static final String TO_ACCOUNT_SELECT_ATTRIBUTE_NAME                 = "toAccountSelect";
	public static final String CHARGEOFF_CARD_ACCOUNT_ATTRIBUTE_NAME            = "chargeOffCardAccount";
	public static final String CHARGEOFF_CARD_EXPIRE_DATE_ATTRIBUTE_NAME        = "chargeOffCardExpireDate";

	//поля сумм
	public static final String AMOUNT_VALUE_ATTRIBUTE_NAME                      = "amount";
	public static final String CURRENCY_ATTRIBUTE_SUFFIX                        = "Currency";
	public static final String SELL_AMOUNT_VALUE_ATTRIBUTE_NAME                 = "sellAmount";
	public static final String BUY_AMOUNT_VALUE_ATTRIBUTE_NAME                  = "buyAmount";
	public static final String EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME                = "exactAmount";
	public static final String CONVERTION_RATE_ATTRIBUTE_NAME                   = "cource";

	//поля получатель платежа
	public static final String RECEIVER_TYPE_ATTRIBUTE_NAME                     = "receiverType";
	public static final String RECEIVER_SUB_TYPE_ATTRIBUTE_NAME                 = "receiverSubType";
	public static final String RECEIVER_ACCOUNT_ATTRIBUTE_NAME                  = "receiverAccount";
	public static final String RECEIVER_TRANSIT_ACCOUNT_ATTRIBUTE_NAME          = "transitReceiverAccount";
	public static final String RECEIVER_ADDRESS_ATTRIBUTE_NAME                  = "receiverAddress";
	public static final String INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME     = "bank";
	public static final String RECEIVER_BANK_NAME_ATTRIBUTE_NAME                = "receiverBankName";
	public static final String RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME         = "receiverCorAccount";
	public static final String RECEIVER_BANK_BIK_ATTRIBUTE_NAME                 = "receiverBIC";
	public static final String RECEIVER_TRANSIT_BANK_NAME_ATTRIBUTE_NAME        = "transitBank";
	public static final String RECEIVER_TRANSIT_BANK_COR_ACCOUNT_ATTRIBUTE_NAME = "transitReceiverCorAccount";
	public static final String RECEIVER_TRANSIT_BANK_BIK_ATTRIBUTE_NAME         = "transitReceiverBIC";
	public static final String RECEIVER_INN_ATTRIBUTE_NAME                      = "receiverINN";
	public static final String RECEIVER_KPP_ATTRIBUTE_NAME                      = "receiverKPP";
	public static final String RECEIVER_ACCOUNT_INTERNAL_ATTRIBUTE_NAME         = "receiverAccountInternal";
	public static final String RECEIVER_SURNAME_ATTRIBUTE_NAME                  = "receiverSurname";
	public static final String RECEIVER_NAME_ATTRIBUTE_NAME                     = "receiverName";
	public static final String RECEIVER_FIRST_NAME_ATTRIBUTE_NAME               = "receiverFirstName";
	public static final String RECEIVER_PATR_NAME_ATTRIBUTE_NAME                = "receiverPatrName";
	public static final String RECEIVER_EXTERNAL_PHONE_NUMBER_ATTRIBUTE_NAME    = "externalPhoneNumber";
	public static final String RECEIVER_PHONE_NUMBER_ATTRIBUTE_NAME             = "phoneNumber";
	public static final String RECEIVER_NAME_ON_BILL_ATTRIBUTE_NAME             = "nameOnBill";
	public static final String RECEIVER_DESCRIPTION_ATTRIBUTE_NAME              = "receiverDescription";
	public static final String RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME            = "receiverOfficeRegion";
	public static final String RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME            = "receiverOfficeBranch";
	public static final String RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME            = "receiverOfficeOffice";
	public static final String JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE                = "jur";
	public static final String PHIZ_RECEIVER_TYPE_ATTRIBUTE_VALUE               = "ph";
	public static final String OUR_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME        = "ourCard";
	public static final String OUR_CONTACT_TRANSFER_TYPE_VALUE                  = "ourContact";
	public static final String RECEIVER_CONTACT                                 = "externalContactId";
	public static final String OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME       = "ourPhone";
	public static final String OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE    = "ourContactToOtherCard";
	public static final String VISA_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME       = "visaExternalCard";
	public static final String MASTERCARD_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME = "masterCardExternalCard";
	public static final String OUR_ACCOUNT_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME     = "ourAccount";
	public static final String EXTERNAL_ACCOUNT_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME= "externalAccount";
	public static final String RESTRICT_RECEIVER_INFO_ATTRIBUTE_NAME            = "restrict-receiver-info";
	public static final String IS_OUR_BANK_CARD_ATTRIBUTE_NAME                  = "isOurBankCard";
	public static final String YANDEX_EXIST_ATTRIBUTE_NAME                      = "yandexExist";
	public static final String YANDEX_MESSAGE_SHOW_ATTRIBUTE_NAME               = "yandexMessageShow";

	public static final String ID_FORM_PAYMENT_SYSTE_PAYMENT_ATTRIBUTE_NAME     = "receiverDescription";
	public static final String AUTO_SUBSCRIPTION_ATTRIBUTE_NAME                 = "autoSubName";
	public static final String AUTO_SUBSCRIPTION_START_DATE_ATTRIBUTE_NAME      = "longOfferStartDate";
	public static final String AUTO_SUBSCRIPTION_EVENT_TYPE_ATTRIBUTE_NAME      = "longOfferEventType";
	public static final String MESSAGE_TO_RECEIVER_ATTRIBUTE_NAME               = "messageToReceiver";
	public static final String IS_NEW_FORM_ATTRIBUTE_NAME                       = "isNewForm";
	//информация о поставщике
	public static final String PROVIDER_BILLING_CODE_ATTRIBUTE_NAME             = "billingCode";
	public static final String PROVIDER_BILLING_CLIENT_ID_ATTRIBUTE_NAME        = "billingClientId";
	public static final String PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME          = "bankDetails";
	public static final String PROVIDER_EXTERNAL_ID_ATTRIBUTE_NAME              = "receiverId";
	public static final String PROVIDER_MULTI_BLOCK_POINT_CODE_ATTRIBUTE_NAME   = "multiBlockReceiverId";
	public static final String PROVIDER_INTERNAL_ID_ATTRIBUTE_NAME              = "recipient";
	public static final String EXTERNAL_PROVIDER_KEY_ATTRIBUTE_NAME             = "externalReceiverId";
	public static final String PROVIDER_CODE_SERVICE_ATTRIBUTE_NAME             = "codeService";
	public static final String PROVIDER_NAME_SERVICE_ATTRIBUTE_NAME             = "nameService";

	//погашение кредита
	public static final String LOAN_ATTRIBUTE_NAME                              = "loan";
	public static final String LOAN_TYPE_ATTRIBUTE_NAME                         = "loanType";
	public static final String LOAN_NAME_ATTRIBUTE_NAME                         = "loanName";
	public static final String LOAN_AMOUNT_ATTRIBUTE_NAME                       = "loanAmount";
	public static final String LOAN_CURRENCY_ATTRIBUTE_NAME                     = "loanCurrency";
	public static final String LOAN_ACCOUNT_NUMBER_ATTRIBUTE_NAME               = "loanAccountNumber";
	public static final String LOAN_EXTERNAL_ID_ATTRIBUTE_NAME                  = "loanExternalId";
	public static final String LOAN_AGREEMENT_NUMBER_ATTRIBUTE_NAME             = "agreementNumber";
	public static final String LOAN_OFFICE_ATTRIBUTE_NAME                       = "office";
	public static final String LOAN_LINK_ID_ATTRIBUTE_NAME                      = "loanLinkId";

	//перевод ценных бумаг
	public static final String SECURITY_TRANSFER_OP_TYPE_ATTRIBUTE_NAME         = "operationType";
	public static final String SECURITY_TRANSFER_OP_SUB_TYPE_ATTRIBUTE_NAME     = "operationSubType";
	public static final String SECURITY_TRANSFER_OP_DESC_ATTRIBUTE_NAME         = "operationDesc";
	public static final String SECURITY_TRANSFER_DIVISION_TYPE_ATTRIBUTE_NAME   = "divisionType";
	public static final String SECURITY_TRANSFER_DIVISION_NUM_ATTRIBUTE_NAME    = "divisionNumber";
	public static final String SECURITY_TRANSFER_INSIDE_CODE_ATTRIBUTE_NAME     = "insideCode";
	public static final String SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME  = "securityCount";
	public static final String SECURITY_TRANSFER_SECURITY_NAME_ATTRIBUTE_NAME   = "securityName";
	public static final String SECURITY_TRANSFER_OP_REASON_ATTRIBUTE_NAME       = "operationReason";
	public static final String SECURITY_TRANSFER_CORR_DEPOSITARY_ATTRIBUTE_NAME = "corrDepositary";
	public static final String SECURITY_TRANSFER_CORDEPO_ACCOUNT_ATTRIBUTE_NAME = "corrDepoAccount";
	public static final String SECURITY_TRANSFER_CORDEPO_OWNER_ATTRIBUTE_NAME   = "corrDepoAccountOwner";
	public static final String SECURITY_TRANSFER_ADD_INFO_ATTRIBUTE_NAME        = "additionalInfo";
	public static final String SECURITY_TRANSFER_DELIVERY_TYPE_ATTRIBUTE_NAME   = "deliveryType";
	public static final String SECURITY_TRANSFER_REG_NUMBER_ATTRIBUTE_NAME      = "registrationNumber";
	public static final String SECURITY_TRANSFER_DEPO_EX_ID_ATTRIBUTE_NAME      = "depoExternalId";
	public static final String SECURITY_TRANSFER_DEPO_ACC_NUMBER_ATTRIBUTE_NAME = "depoAccount";
}
