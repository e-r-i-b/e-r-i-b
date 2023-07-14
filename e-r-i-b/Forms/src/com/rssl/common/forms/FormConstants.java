package com.rssl.common.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 13.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class FormConstants
{
	/**
	 * Соответствие формы категории.
	 */
	public static final Map<String, String> formNameToCategory = new HashMap<String, String>();

	static
	{
		formNameToCategory.put("InternalPayment",              "TRANSFER");
		formNameToCategory.put("RurPayment",                   "TRANSFER");
		formNameToCategory.put("ConvertCurrencyPayment",       "TRANSFER");
		formNameToCategory.put("JurPayment",                   "TRANSFER");
		formNameToCategory.put("BlockingCardClaim",            "TRANSFER");
		formNameToCategory.put("VirtualCardClaim",             "TRANSFER");
		formNameToCategory.put("IMAPayment",                   "TRANSFER");
		formNameToCategory.put("CreateMoneyBoxPayment",        "TRANSFER");
		formNameToCategory.put("ReIssueCardClaim",             "DEPOSITS_AND_LOANS");
		formNameToCategory.put("AccountOpeningClaim",          "DEPOSITS_AND_LOANS");
		formNameToCategory.put("AccountClosingPayment",        "DEPOSITS_AND_LOANS");
		formNameToCategory.put("LossPassbookApplication",      "DEPOSITS_AND_LOANS");
		formNameToCategory.put("LoanPayment",                  "DEPOSITS_AND_LOANS");
		formNameToCategory.put("LoanOffer",                    "DEPOSITS_AND_LOANS");
		formNameToCategory.put("LoanProduct",                  "DEPOSITS_AND_LOANS");
		formNameToCategory.put("LoanCardOffer",                "DEPOSITS_AND_LOANS");
		formNameToCategory.put("LoanCardProduct",              "DEPOSITS_AND_LOANS");
		formNameToCategory.put("PFRStatementClaim",            "PFR");
		formNameToCategory.put("FNSPayment",                   "PFR_ONLINE");
		formNameToCategory.put("RurPayJurSB",                  "SERVICE_PAYMENT");
		formNameToCategory.put("ExternalProviderPayment",      "SERVICE_PAYMENT");
		formNameToCategory.put("CreateAutoPaymentPayment",     "SERVICE_PAYMENT");
		formNameToCategory.put("EditAutoPaymentPayment",       "SERVICE_PAYMENT");
		formNameToCategory.put("RefuseAutoPaymentPayment",     "SERVICE_PAYMENT");
		formNameToCategory.put("RefuseLongOffer",              "SERVICE_PAYMENT");
		formNameToCategory.put("RemoteConnectionUDBOClaim",    "SERVICE_PAYMENT");
		formNameToCategory.put("AccountOpeningClaimWithClose", "DEPOSITS_AND_LOANS");
	}

	/**
	 * Такое кодовое обозначение имеет форма оплаты услуг (платёж поставщику)
	 */
	public static final String SERVICE_PAYMENT_FORM = "RurPayJurSB";

	/**
	 * Такое кодовое обозначение имеет форма перевода "между моими счетами"
	 */
	public static final String INTERNAL_PAYMENT_FORM = "InternalPayment";

	/**
	 * Такое кодовое обозначение имеет форма "перевести деньги"
	 */
	public static final String RUR_PAYMENT_FORM = "RurPayment";

	/**
	 * Такое кодовое обозначение имеет форма "перевод организации"
	 */
	public static final String JUR_PAYMENT_FORM = "JurPayment";

	/**
	 * Такое кодовое обозначение имеет форма "оплата налогов"
	 */
	public static final String TAX_PAYMENT_FORM = "TaxPayment";

	/**
	 * Такое кодовое обозначение имеет форма "обмен валют"
	 */
	public static final String CONVERT_CURRENCY_PAYMENT_FORM = "ConvertCurrencyPayment";

	/**
	 * Такое кодовое обозначение имеет форма "погашение кредита"
	 */
	public static final String LOAN_PAYMENT_FORM = "LoanPayment";

	/**
	 *  Такое кодовое обозначение имеет форма перевода "оплата с внешней ссылки"
	 *  форма "оплата с внешней ссылки"
	 */
	public static final String EXTERNAL_PROVIDER_PAYMENT_FORM = "ExternalProviderPayment";

	/**
	 *  форма "оплата налогов с сайта ФНС"
	 */
	public static final String FNS_PAYMENT_FORM = "FNSPayment";

	/**
	 * форма "бронирование авиабилетов"
	 */
	public static final String AIRLINE_RESERVATION_PAYMENT_FORM = "AirlineReservationPayment";
	/**
	 * Такое кодовое обозначение имеет форма "создание автоплатежа"
	 */
	public static final String CREATE_AUTOPAYMENT_FORM = "CreateAutoPaymentPayment";

	/**
	 * форма "редактирование автоплатежа"
	 */
	public static final String EDIT_AUTOPAYMENT_FORM = "EditAutoPaymentPayment";

	/**
	 * форма "отмены автоплатежа"
	 */
	public static final String REFUSE_AUTOPAYMENT_FORM = "RefuseAutoPaymentPayment";

	/**
	 * Такое кодовое обозначение имеет форма "заявка на получение выписки из Пенсионного Фонда РФ"
	 */
	public static final String PFR_STATEMENT_CLAIM = "PFRStatementClaim";

	/**
	 * Такое кодовое обозначение имеет форма "заявка на закрытие вклада"
	 */
	public static final String ACCOUNT_CLOSING_PAYMENT_FORM = "AccountClosingPayment";

	/**
	 * Такое кодовое обозначение имеет форма "заявка на открытие вклада"
	 */
	public static final String ACCOUNT_OPENING_CLAIM_FORM = "AccountOpeningClaim";


	/**
	 * Такое кодовое обозначение имеет форма "Покупка/продажа металла"
	 */
	public static final String IMA_PAYMENT_FORM = "IMAPayment";
	
	/**
	 * Такое кодовое обозначение имеет форма "поручение на перевод/прием перевода ценных бумаг"
	 */
	public static final String SECURIRIES_TRANSFER_CLAIM_FORM = "SecuritiesTransferClaim";

	/**
	 * Такое кодовое обозначение имеет форма "заявка на регистрацию ценной бумаги"
	 */
	public static final String SECURIRIES_REGISTRATION_CLAIM_FORM = "SecurityRegistrationClaim";

	/**
	 * Такое кодовое обозначение имеет форма "Отзыв документа"
	 */
	public static final String RECALL_DEPOSITARY_CLAIM_FORM = "RecallDepositaryClaim";

	/**
	 * Такое кодовое обозначение имеет форма "Отзыв документа"
	 */
	public static final String RECALL_PAYMENT_FORM = "RecallPayment";

	/**
	 * Такое кодовое обозначение имеет форма "Поручение на получение анкетных данных"
	 */
	public static final String DEPOSITOR_FORM_CLAIM_FORM = "DepositorFormClaim";

	/**
	 *      Заявка на предодобренный кредит
	 */
	public static final String LOAN_OFFER_FORM = "LoanOffer";

	/**
	 *      Заявка на оформление кредитного продукта
	 */
	public static final String LOAN_PRODUCT_FORM = "LoanProduct";

	/**
	 *      Заявка на предодобренную кредитную карту
	 */
	public static final String LOAN_CARD_OFFER_FORM = "LoanCardOffer";

	/**
	 *      Заявка на оформление кредитной карты
	 */
	public static final String LOAN_CARD_PRODUCT_FORM = "LoanCardProduct";

	/**
	 *  Заявка на блокировку карты
	 */
	public static final String BLOCKING_CARD_CLAIM = "BlockingCardClaim";

	/**
	 * Заявка на перевыпуск карты.
	 */
	public static final String REISSUE_CARD_CLAIM = "ReIssueCardClaim";

	/**
	 *  Заявка об утере сберегательной книжки
	 */
	public static final String LOSS_PASSBOOK_APPLICATION = "LossPassbookApplication";

	/**
	 *   Такое кодовое обозначение имеет форма "заявка на закрытие вклада (перевод средств на новый вклад)"
	 */
	public static final String ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM = "AccountOpeningClaimWithClose";

	/**
	 *  Заявка на виртуальную карту
	 */
	public static final String VIRTUAL_CARD_CLAIM = "VirtualCardClaim";

	/**
	 *  Заявка на подкючение бонусной программы
	 */
	public static final String LOYALTY_PROGRAM_REGISTRATION_CLAIM = "LoyaltyProgramRegistrationClaim";
	
	/**
	 *  Заявка на открытие ОМС
	 */
	public static final String IMA_OPENING_CLAIM = "IMAOpeningClaim";	

	/**
	 *  Заявка на редактирование шинного автоплатежа(автоподписки)
	 */
	public static final String EDIT_AUTOSUBSCRIPTION_PAYMENT = "EditAutoSubscriptionPayment";

	/**
	 *  Заявка на закрытие шинного автоплатежа(автоподписки)
	 */
	public static final String REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM = "CloseAutoSubscriptionPayment";

	/**
	 *  Заявка на приостановку шинного автоплатежа(автоподписки)
	 */
	public static final String DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM = "DelayAutoSubscriptionPayment";

	/**
	 *  Заявка на возобновление шинного автоплатежа(автоподписки)
	 */
	public static final String RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM = "RecoveryAutoSubscriptionPayment";

	public static final String REFUSE_LONGOFFER_FORM = "RefuseLongOffer";
	/**
	 *  Заявка на удаленное подключение УДБО
	 */
	public static final String REMOTE_CONNECTION_UDBO_CLAIM_FORM = "RemoteConnectionUDBOClaim";

	public static final String CREATE_AUTOPAYMENT_DEFAULT_FORM = "CreateAutoPayment";

	public static final String EDIT_AUTOPAYMENT_DEFAULT_FORM = "EditAutoPayment";

	public static final String REFUSE_AUTOPAYMENT_DEFAULT_FORM = "RefuseAutoPayment";

	/**
	 * Возврат товара из интернет-магазина
	 */
	public static final String REFUND_GOODS_FORM = "RefundGoodsClaim";


	public static final String CANCEL_EARLY_LOAN_REPAYMENT_CLAIM = "CancelEarlyLoanRepaymentClaim";

	/**
	 * Отмена оплаты товара из интернет-магазина
	 */
	public static final String RECALL_ORDER_FORM = "RollbackOrderClaim";

	/*Заявка на изменениие порядка уплаты процентов по вкладу*/
	public static final String ACCOUNT_CHANGE_INTEREST_DESTINATION = "AccountChangeInterestDestinationClaim";

	/*Заявка на изменение неснижаемого остатка*/
	public static final String CHANGE_DEPOSIT_MINIMUM_BALANCE = "ChangeDepositMinimumBalanceClaim";

	public static final String PREAPPROVED_LOAN_CARD_CLAIM = "PreapprovedLoanCardClaim";

	/**
	 * Заявка на подписку получения инвойсов
	 */
	public static final String CREATE_INVOICE_SUBSCRIPTION_PAYMENT = "CreateInvoiceSubscriptionPayment";

	/**
	 * Создание заявки на редактирование подписки на инвойсы
	 */
	public static final String EDIT_INVOICE_SUBSCRIPTION_CLAIM = "EditInvoiceSubscriptionClaim";

	/**
	 * Создание заявки на удаление подписки на инвойсы
	 */
	public static final String CLOSE_INVOICE_SUBSCRIPTION_CLAIM = "CloseInvoiceSubscriptionClaim";

	/**
	 * Создание заявки на приостановку подписки на инвойсы
	 */
	public static final String DELAY_INVOICE_SUBSCRIPTION_CLAIM = "DelayInvoiceSubscriptionClaim";

	/**
	 * Создание заявки на приостановку подписки на инвойсы
	 */
	public static final String RECOVERY_INVOICE_SUBSCRIPTION_CLAIM = "RecoveryInvoiceSubscriptionClaim";

	/**
	 * Оплата задолженности
	 */
	public static final String INVOICE_PAYMENT_FORM = "InvoicePayment";

	/**
	 * Создание копилки.
	 */
	public static final String CREATE_MONEY_BOX_FORM = "CreateMoneyBoxPayment";

	/**
	 * Редактирование копилки.
	 */
	public static final String EDIT_MONEY_BOX_FORM = "EditMoneyBoxClaim";

	/**
	 * Приостановка копилки.
	 */
	public static final String REFUSE_MONEY_BOX_FORM = "RefuseMoneyBoxPayment";
	/**
	 * Закрытие копилки.
	 */
	public static final String CLOSE_MONEY_BOX_FORM = "CloseMoneyBoxPayment";
	/**
	 * Восстановление копилки.
	 */
	public static final String RECOVERY_MONEY_BOX_FORM = "RecoverMoneyBoxPayment";

	/**
	 *  Заявка на возобновление P2P автоплатежа
	 */
	public static final String RECOVERY_P2P_AUTO_TRANSFER_CLAIM_FORM = "RecoveryP2PAutoTransferClaim";

	/**
	 *  Заявка на приостановку P2P автоплатежа
	 */
	public static final String DELAY_P2P_AUTO_TRANSFER_CLAIM_FORM = "DelayP2PAutoTransferClaim";

	/**
	 *  Заявка на закрытие P2P автоплатежа
	 */
	public static final String CLOSE_P2P_AUTO_TRANSFER_CLAIM_FORM = "CloseP2PAutoTransferClaim";

	/**
	 *  Заявка на создание P2P автоплатежа
	 */
	public static final String CREATE_P2P_AUTO_TRANSFER_CLAIM_FORM = "CreateP2PAutoTransferClaim";

	/**
	 *  Заявка на редактирование P2P автоплатежа
	 */
	public static final String EDIT_P2P_AUTO_TRANSFER_CLAIM_FORM = "EditP2PAutoTransferClaim";

	/**
	 * Платеж за кредитный отчет
	 */
	public static final String CREDIT_REPORT_PAYMENT = "CreditReportPayment";

	/**
	 * Перевод частному лицу по АК
	 */
	public static final String NEW_RUR_PAYMENT = "NewRurPayment";

	/**
	 * Расширенная заявка на кредит
	 */
	public static final String EXTENDED_LOAN_CLAIM = "ExtendedLoanClaim";

	/**
	 * Заявка на кредитную карту
	 */
	public static final String LOAN_CARD_CLAIM = "LoanCardClaim";

	/**
	 * Заявка на выписку по карте на e-mail.
	 */
	public static final String REPORT_BY_CARD_CLAIM = "ReportByCardClaim";

	/**
	 * Изменение кредитного лимита карты
	 */
	public static final String CHANGE_CREDIT_LIMIT_CLAIM = "ChangeCreditLimitClaim";

	/**
	 * Заявка на досрочное погашение кредита
	 */
	public static final String EARLY_LOAN_REPAYMENT_CLAIM = "EarlyLoanRepaymentClaim";

}
