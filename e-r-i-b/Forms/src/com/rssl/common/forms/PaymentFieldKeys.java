package com.rssl.common.forms;

/**
 * @author Erkin
 * @ created 08.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��� ����� (������), �������������� � ��������
 * ��� ���������� ����� ������ ���� ������������� �����
 * ��������� ���������������� ��� �
 *  WebCommon/src/com/rssl/phizic/web/resources.properties
 * � ������
 *  "������� � ����� �� �������� �����"
 */
public interface PaymentFieldKeys
{
	public static final String PAYMENT_ID_KEY = "id";

	public static final String TEMPLATE_ID_KEY = "templateId";

	public static final String SERVICE_KEY = "service";

	 //������� ������������� ���������� �����(���� synchKey)
	public static final String PROVIDER_EXTERNAL_KEY = "receiverId";

	//���������� ������������� ���������� �����(��������� ����)
	public static final String PROVIDER_KEY = "recipient";

	//������������ ����������
	public static final String RECEIVER_NAME = "receiverName";

	//�������� ����������
	public static final String RECEIVER_DESCRIPTION = "receiverDescription";

	//����� �������� ���������� ��� ��������� ��������
	public static final String RECEIVER_PHONE_NUMBER = "phoneNumber";

	//������������ ���������� �����, ��������� ��� ������.
	public static final String RECEIVER_NAME_ON_BILL = "nameOnBill";

	//��� ����������� ������ ����������
	public static final String RECEIVER_SERVICE_CODE = "codeService";
	//������������ ����������� ������ ����������
	public static final String RECEIVER_SERVICE_NAME = "nameService";

	//��� ����������� �������
	public static final String BILLING_CODE = "billingCode";

	public static final String FORM_NAME = "form";

	/**
	 * �������� �������� �������:
	 *  RurPayJurSB, InternalPayment, RurPayment, TaxPayment, ConvertCurrencyPayment, LoanPayment
	 */
	public static final String FROM_RESOURCE_KEY = "fromResource";

	/**
	 * ��� ��������� �������� �������: RurPayment
	 */
	public static final String FROM_RESOURCE_TYPE = "fromResourceType";

	/**
	 * ������� ���������� �������: InternalPayment, ConvertCurrencyPayment
	 */
	public static final String TO_RESOURCE_KEY = "toResource";

	/**
	 * ������� ���������� �������: RurPayment
	 */
	public static final String RECEIVER_ACCOUNT = "receiverAccount";

	/**
	 * ����� �������: RurPayJurSB, TaxPayment, LoanPayment
	 */
	public static final String AMOUNT = "amount";
	public static final String CURRENCY = "currency";

	/**
	 * ����� ��������: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String SELL_AMOUNT = "sellAmount";

	/**
	 * ����� ����������: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String BUY_AMOUNT = "buyAmount";

	/**
	 * ��� ����, � ������� ������ �������� �����: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String EXACT_AMOUNT_FIELD_NAME = "exactAmount";

	/**
	 * ������ ����������: RurPayment
	 */
	public static final String BUY_AMOUNT_CURRENCY = "buyAmountCurrency";

	public static final String DEBTCODE_KEY = "debtCode";

	/**
	 * ���� ���������: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String CONVERSION_RATE = "course";

	/**
	 * ����� ��������: RurPayJurSB
	 */
	public static final String COMMISSION = "commission";

	/**
	 * ����� ��������: TaxPayment
	 */
	public static final String COMISSION_AMOUNT = "commissionAmount";

	/**
	 * ���������� �������: RurPayJurSB, RurPayment, TaxPayment, LoanPayment
	 */
	public static final String GROUND = "ground";

	/**
	 * �������� ������: RurPayJurSB
	 */
	public static final String CREATION_SOURCE = "creationSource";

	public static final String ORDER_ID_KEY = "orderId";

	public static final String DOCUMENT_NUMBER_KEY = "documentNumber";

	public static final String TYPE = "type";
	/**
	 * ��� ���������
	 * �������� ������� ���, ����� ���� ����������� ����������� ��������� ��� ������ ������ �� ����
	 */
	public static final String SUBSERVICE_CODE = "r192025125";

	/**
	 * ��� ����� �����������
	 */
	public static final String RECEIVER_BIC = "receiverBIC";

	/**
	 * ������������ ����� �����������
	 */
	public static final String RECEIVER_BANK_NAME = "receiverBankName";

	/**
	 * ������� ����� �����������
	 */
	public static final String RECEIVER_COR_ACCOUNT = "receiverCorAccount";

	/**
	 * ��� �����������
	 */
	public static final String RECEIVER_INN = "receiverINN";

	/**
	 * KPP �����������
	 */
	public static final String RECEIVER_KPP = "receiverKPP";

	/**
	 * ��� �������� ��������
	 */
	public static final String OPERATION_CODE = "operationCode";

	/**
	 * ������ ���������� �������: RurPayment
	 */
	public static final String RECEIVER_SUB_TYPE = "receiverSubType";

	/**
	 * ��� ����: RurPayment
	 */
	public static final String IS_OUR_BANK = "isOurBank";

	/**
	 * �������� �� �������� ��������� � ����� �� �����: RurPayment
	 */
	public static final String IS_CARD_TRANSFER = "isCardTransfer";

	/**
	 * ����� ����� ����������: RurPayment
	 */
	public static final String EXTERNAL_CARD_NUMBER = "externalCardNumber";

	/**
	 * ����/����� ��������: RurPayment
	 */
	public static final String FROM_ACCOUNT_SELECT = "fromAccountSelect";

	/**
	 * ��������� �����: RurPayment
	 */
	public static final String EXTERNAL_PHONE_NUMBER = "externalPhoneNumber";

	/**
	 * ���� � ������� � ������������ ����������� (� ������� XML)
	 */
	public static final String AIRLINE_RESERVATION = "airlineReservation";

	/**
	 * ���� � ������� � �������, ���������� � ��������-������ (� ������� xml).
	 */
	public static final String ORDER_FIELDS = "orderFields";

	/**
	 * ���������� ����� � ������� � �������, ����������� � ��������-��������.
	 */
	public static final String ORDER_FIELDS_SIZE = "orderFieldsSize";

    /**
     * ���� � ����������� � ������� (� ������� XML)
     */
    public static final String TICKETS_INFO = "ticketsInfo";

	/**
	 * ������������� ����:
	 * LossPassbookApplication
	 */
	public static final String ACCOUNT_SELECT = "accountSelect";

	/**
	 * ������� �������� �����:
	 * LossPassbookApplication
	 */
	public static final String LOSS_PASSBOOK_APPLICATION_REASON = "closingAmountOrPassbookDuplicateRadio";

	/**
	 * ����� �������� ����� - ������ ������ ��������� ��� ����������� �� ������ ����:
	 * LossPassbookApplication
	 */
	public static final String ISSUE_MONEY_OR_TRANSFER_TO_ACCOUNT = "moneyOrTransferToAccountRadio";

	/**
	 * �����, ������� ���������:
	 * BlockingCardClaim
	 */
	public static final String BLOCKING_CARD = "card";

	/**
	 * ������� ���������� �����:
	 * BlockingCardClaim
	 */
	public static final String BLOCKING_CARD_REASON = "reason";

	/**
	 * ������ ����� ��������-������ (� ������� XML)
	 */
	public static final String INTERNET_ORDER_FIELDS = "internetOrderFields";

	/**
	 * ������������� ������� �����
	 */
	public static final String ACCOUNTING_ENTITY_ID = "accountingEntityId";

	/**
	 * ��� �����������
	 */
	public static final String AUTO_PAYMENT_TYPE  = "autoPaymentType";

	/**
	 * ��������� �����
	 */
	public static final String AUTO_PAYMENT_FLOOR_LIMIT = "autoPaymentFloorLimit";

	/**
	 * �����, ������� �������������:
	 * ReIssueCardClaim
	 */
	public static final String CARD_LINK_ISSUE = "cardLink";

	/**
	 * ������� ���������� ����� ��� �����������
	 */
	public static final String REASON_ISSUE = "reissueReason";

	/**
	 * ����� ��������� �����
	 */
	public static final String DESTINATION_OFFICE_REGION = "officeCodeRegion";
	public static final String DESTINATION_OFFICE_BRANCH = "officeCodeBranch";
	public static final String DESTINATION_OFFICE_OFFICE = "officeCodeOffice";
}
