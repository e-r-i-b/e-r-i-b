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
	 * ������������ ����� ���������.
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
	 * ����� ������� ����������� ����� ����� ������ ����� (����� ����������)
	 */
	public static final String SERVICE_PAYMENT_FORM = "RurPayJurSB";

	/**
	 * ����� ������� ����������� ����� ����� �������� "����� ����� �������"
	 */
	public static final String INTERNAL_PAYMENT_FORM = "InternalPayment";

	/**
	 * ����� ������� ����������� ����� ����� "��������� ������"
	 */
	public static final String RUR_PAYMENT_FORM = "RurPayment";

	/**
	 * ����� ������� ����������� ����� ����� "������� �����������"
	 */
	public static final String JUR_PAYMENT_FORM = "JurPayment";

	/**
	 * ����� ������� ����������� ����� ����� "������ �������"
	 */
	public static final String TAX_PAYMENT_FORM = "TaxPayment";

	/**
	 * ����� ������� ����������� ����� ����� "����� �����"
	 */
	public static final String CONVERT_CURRENCY_PAYMENT_FORM = "ConvertCurrencyPayment";

	/**
	 * ����� ������� ����������� ����� ����� "��������� �������"
	 */
	public static final String LOAN_PAYMENT_FORM = "LoanPayment";

	/**
	 *  ����� ������� ����������� ����� ����� �������� "������ � ������� ������"
	 *  ����� "������ � ������� ������"
	 */
	public static final String EXTERNAL_PROVIDER_PAYMENT_FORM = "ExternalProviderPayment";

	/**
	 *  ����� "������ ������� � ����� ���"
	 */
	public static final String FNS_PAYMENT_FORM = "FNSPayment";

	/**
	 * ����� "������������ �����������"
	 */
	public static final String AIRLINE_RESERVATION_PAYMENT_FORM = "AirlineReservationPayment";
	/**
	 * ����� ������� ����������� ����� ����� "�������� �����������"
	 */
	public static final String CREATE_AUTOPAYMENT_FORM = "CreateAutoPaymentPayment";

	/**
	 * ����� "�������������� �����������"
	 */
	public static final String EDIT_AUTOPAYMENT_FORM = "EditAutoPaymentPayment";

	/**
	 * ����� "������ �����������"
	 */
	public static final String REFUSE_AUTOPAYMENT_FORM = "RefuseAutoPaymentPayment";

	/**
	 * ����� ������� ����������� ����� ����� "������ �� ��������� ������� �� ����������� ����� ��"
	 */
	public static final String PFR_STATEMENT_CLAIM = "PFRStatementClaim";

	/**
	 * ����� ������� ����������� ����� ����� "������ �� �������� ������"
	 */
	public static final String ACCOUNT_CLOSING_PAYMENT_FORM = "AccountClosingPayment";

	/**
	 * ����� ������� ����������� ����� ����� "������ �� �������� ������"
	 */
	public static final String ACCOUNT_OPENING_CLAIM_FORM = "AccountOpeningClaim";


	/**
	 * ����� ������� ����������� ����� ����� "�������/������� �������"
	 */
	public static final String IMA_PAYMENT_FORM = "IMAPayment";
	
	/**
	 * ����� ������� ����������� ����� ����� "��������� �� �������/����� �������� ������ �����"
	 */
	public static final String SECURIRIES_TRANSFER_CLAIM_FORM = "SecuritiesTransferClaim";

	/**
	 * ����� ������� ����������� ����� ����� "������ �� ����������� ������ ������"
	 */
	public static final String SECURIRIES_REGISTRATION_CLAIM_FORM = "SecurityRegistrationClaim";

	/**
	 * ����� ������� ����������� ����� ����� "����� ���������"
	 */
	public static final String RECALL_DEPOSITARY_CLAIM_FORM = "RecallDepositaryClaim";

	/**
	 * ����� ������� ����������� ����� ����� "����� ���������"
	 */
	public static final String RECALL_PAYMENT_FORM = "RecallPayment";

	/**
	 * ����� ������� ����������� ����� ����� "��������� �� ��������� �������� ������"
	 */
	public static final String DEPOSITOR_FORM_CLAIM_FORM = "DepositorFormClaim";

	/**
	 *      ������ �� �������������� ������
	 */
	public static final String LOAN_OFFER_FORM = "LoanOffer";

	/**
	 *      ������ �� ���������� ���������� ��������
	 */
	public static final String LOAN_PRODUCT_FORM = "LoanProduct";

	/**
	 *      ������ �� �������������� ��������� �����
	 */
	public static final String LOAN_CARD_OFFER_FORM = "LoanCardOffer";

	/**
	 *      ������ �� ���������� ��������� �����
	 */
	public static final String LOAN_CARD_PRODUCT_FORM = "LoanCardProduct";

	/**
	 *  ������ �� ���������� �����
	 */
	public static final String BLOCKING_CARD_CLAIM = "BlockingCardClaim";

	/**
	 * ������ �� ���������� �����.
	 */
	public static final String REISSUE_CARD_CLAIM = "ReIssueCardClaim";

	/**
	 *  ������ �� ����� �������������� ������
	 */
	public static final String LOSS_PASSBOOK_APPLICATION = "LossPassbookApplication";

	/**
	 *   ����� ������� ����������� ����� ����� "������ �� �������� ������ (������� ������� �� ����� �����)"
	 */
	public static final String ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM = "AccountOpeningClaimWithClose";

	/**
	 *  ������ �� ����������� �����
	 */
	public static final String VIRTUAL_CARD_CLAIM = "VirtualCardClaim";

	/**
	 *  ������ �� ���������� �������� ���������
	 */
	public static final String LOYALTY_PROGRAM_REGISTRATION_CLAIM = "LoyaltyProgramRegistrationClaim";
	
	/**
	 *  ������ �� �������� ���
	 */
	public static final String IMA_OPENING_CLAIM = "IMAOpeningClaim";	

	/**
	 *  ������ �� �������������� ������� �����������(������������)
	 */
	public static final String EDIT_AUTOSUBSCRIPTION_PAYMENT = "EditAutoSubscriptionPayment";

	/**
	 *  ������ �� �������� ������� �����������(������������)
	 */
	public static final String REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM = "CloseAutoSubscriptionPayment";

	/**
	 *  ������ �� ������������ ������� �����������(������������)
	 */
	public static final String DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM = "DelayAutoSubscriptionPayment";

	/**
	 *  ������ �� ������������� ������� �����������(������������)
	 */
	public static final String RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM = "RecoveryAutoSubscriptionPayment";

	public static final String REFUSE_LONGOFFER_FORM = "RefuseLongOffer";
	/**
	 *  ������ �� ��������� ����������� ����
	 */
	public static final String REMOTE_CONNECTION_UDBO_CLAIM_FORM = "RemoteConnectionUDBOClaim";

	public static final String CREATE_AUTOPAYMENT_DEFAULT_FORM = "CreateAutoPayment";

	public static final String EDIT_AUTOPAYMENT_DEFAULT_FORM = "EditAutoPayment";

	public static final String REFUSE_AUTOPAYMENT_DEFAULT_FORM = "RefuseAutoPayment";

	/**
	 * ������� ������ �� ��������-��������
	 */
	public static final String REFUND_GOODS_FORM = "RefundGoodsClaim";


	public static final String CANCEL_EARLY_LOAN_REPAYMENT_CLAIM = "CancelEarlyLoanRepaymentClaim";

	/**
	 * ������ ������ ������ �� ��������-��������
	 */
	public static final String RECALL_ORDER_FORM = "RollbackOrderClaim";

	/*������ �� ���������� ������� ������ ��������� �� ������*/
	public static final String ACCOUNT_CHANGE_INTEREST_DESTINATION = "AccountChangeInterestDestinationClaim";

	/*������ �� ��������� ������������ �������*/
	public static final String CHANGE_DEPOSIT_MINIMUM_BALANCE = "ChangeDepositMinimumBalanceClaim";

	public static final String PREAPPROVED_LOAN_CARD_CLAIM = "PreapprovedLoanCardClaim";

	/**
	 * ������ �� �������� ��������� ��������
	 */
	public static final String CREATE_INVOICE_SUBSCRIPTION_PAYMENT = "CreateInvoiceSubscriptionPayment";

	/**
	 * �������� ������ �� �������������� �������� �� �������
	 */
	public static final String EDIT_INVOICE_SUBSCRIPTION_CLAIM = "EditInvoiceSubscriptionClaim";

	/**
	 * �������� ������ �� �������� �������� �� �������
	 */
	public static final String CLOSE_INVOICE_SUBSCRIPTION_CLAIM = "CloseInvoiceSubscriptionClaim";

	/**
	 * �������� ������ �� ������������ �������� �� �������
	 */
	public static final String DELAY_INVOICE_SUBSCRIPTION_CLAIM = "DelayInvoiceSubscriptionClaim";

	/**
	 * �������� ������ �� ������������ �������� �� �������
	 */
	public static final String RECOVERY_INVOICE_SUBSCRIPTION_CLAIM = "RecoveryInvoiceSubscriptionClaim";

	/**
	 * ������ �������������
	 */
	public static final String INVOICE_PAYMENT_FORM = "InvoicePayment";

	/**
	 * �������� �������.
	 */
	public static final String CREATE_MONEY_BOX_FORM = "CreateMoneyBoxPayment";

	/**
	 * �������������� �������.
	 */
	public static final String EDIT_MONEY_BOX_FORM = "EditMoneyBoxClaim";

	/**
	 * ������������ �������.
	 */
	public static final String REFUSE_MONEY_BOX_FORM = "RefuseMoneyBoxPayment";
	/**
	 * �������� �������.
	 */
	public static final String CLOSE_MONEY_BOX_FORM = "CloseMoneyBoxPayment";
	/**
	 * �������������� �������.
	 */
	public static final String RECOVERY_MONEY_BOX_FORM = "RecoverMoneyBoxPayment";

	/**
	 *  ������ �� ������������� P2P �����������
	 */
	public static final String RECOVERY_P2P_AUTO_TRANSFER_CLAIM_FORM = "RecoveryP2PAutoTransferClaim";

	/**
	 *  ������ �� ������������ P2P �����������
	 */
	public static final String DELAY_P2P_AUTO_TRANSFER_CLAIM_FORM = "DelayP2PAutoTransferClaim";

	/**
	 *  ������ �� �������� P2P �����������
	 */
	public static final String CLOSE_P2P_AUTO_TRANSFER_CLAIM_FORM = "CloseP2PAutoTransferClaim";

	/**
	 *  ������ �� �������� P2P �����������
	 */
	public static final String CREATE_P2P_AUTO_TRANSFER_CLAIM_FORM = "CreateP2PAutoTransferClaim";

	/**
	 *  ������ �� �������������� P2P �����������
	 */
	public static final String EDIT_P2P_AUTO_TRANSFER_CLAIM_FORM = "EditP2PAutoTransferClaim";

	/**
	 * ������ �� ��������� �����
	 */
	public static final String CREDIT_REPORT_PAYMENT = "CreditReportPayment";

	/**
	 * ������� �������� ���� �� ��
	 */
	public static final String NEW_RUR_PAYMENT = "NewRurPayment";

	/**
	 * ����������� ������ �� ������
	 */
	public static final String EXTENDED_LOAN_CLAIM = "ExtendedLoanClaim";

	/**
	 * ������ �� ��������� �����
	 */
	public static final String LOAN_CARD_CLAIM = "LoanCardClaim";

	/**
	 * ������ �� ������� �� ����� �� e-mail.
	 */
	public static final String REPORT_BY_CARD_CLAIM = "ReportByCardClaim";

	/**
	 * ��������� ���������� ������ �����
	 */
	public static final String CHANGE_CREDIT_LIMIT_CLAIM = "ChangeCreditLimitClaim";

	/**
	 * ������ �� ��������� ��������� �������
	 */
	public static final String EARLY_LOAN_REPAYMENT_CLAIM = "EarlyLoanRepaymentClaim";

}
