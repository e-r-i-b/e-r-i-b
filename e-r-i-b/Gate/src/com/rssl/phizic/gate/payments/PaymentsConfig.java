package com.rssl.phizic.gate.payments;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Gainanov
 * @ created 14.03.2007
 * @ $Author$
 * @ $Revision$
 * @deprecated �� ������������� ������ �����
 */
//TODO ������ �� ���������� �����
//TODO ������ ��� ���� ������� ������
//todo ����� ������, ������������, ��� ������������ � ��������� � �����. �������
//ENH009631: ������ PaymentsConfig.	
@Deprecated
public abstract class PaymentsConfig extends Config
{
	public static final String NEED_ACCOUNT = "com.rssl.iccs.need.account";
	public static final String OPERATE_NUMBER = "com.rssl.iccs.operate.person.number";
	public static final String OPERATION_TRANSFER_OTHER_BANK = "com.rssl.iccs.operation.tranfer.other.bank";
	public static final String OPERATION_TRANSFER_OTHER_ACCOUNT = "com.rssl.iccs.operation.tranfer.other.account";
	public static final String OPERATION_TRANSFER_CONVERT = "com.rssl.iccs.operation.tranfer.convert";
	public static final String OPERATION_OPEN_ACCOUNT = "com.rssl.iccs.operation.open.account";
	public static final String OPERATION_ACCOUNT_PAY = "com.rssl.iccs.operation.account.pay";
	public static final String OPERATION_CLOSE_ACCOUNT = "com.rssl.iccs.operation.close.account";
	public static final String SYSTEM_APPLICATION_KIND = "com.rssl.iccs.system.aplication.kind";
	public static final String CONTACT_RETAIL_ID = "com.rssl.iccs.contact.reatail.id";
	public static final String OPERATION_MUNICIPAL_PAYMENT = "com.rssl.iccs.operation.municipal.payment";
	public static final String MUNICIPAL_PAYMENT_RECEIVER = "com.rssl.iccs.municipal.payment.receiver";
	public static final String BANKS_LIST = "com.rssl.iccs.banks.list";
	public static final String SUBOPERATION_TRANSFER_OTHER_BANK_JUR = "com.rssl.iccs.suboperation.transfer.other.bank.jur";
	public static final String SUBOPERATION_TRANSFER_OTHER_BANK_FIZ = "com.rssl.iccs.suboperation.transfer.other.bank.fiz";
	public static final String SUBOPERATION_TRANSFER_KORR_BANK = "com.rssl.iccs.suboperation.transfer.korr.bank";
	public static final String SUBOPERATION_CONTACT_TRANSFER_FIZ = "com.rssl.iccs.suboperation.contact.transfer.fiz";
	public static final String SUBOPERATION_CONTACT_TRANSFER_JUR = "com.rssl.iccs.suboperation.contact.transfer.jur";
	public static final String SUBOPERATION_TAX_PAYMENT = "com.rssl.iccs.suboperation.tax.payment";
	public static final String DATE_OF_STOP_EXTERNAL_ACCOUNT_PAYMENT = "com.rssl.phizic.payments.dateOfStopAccountPayment";
	protected static final String IQW_PROVIDER_CODE_ONLINE_PAYMENT_SETTING = "com.rssl.iccs.payment.online.iqw.provider.codes";
	public static final String INVOICE_ID = "com.rssl.iccs.payment.einvoicing.invoiceidkey";
	public static final String UID = "com.rssl.iccs.payment.einvoicing.eribuidkey";
	public static final String SUM = "com.rssl.iccs.payment.einvoicing.sumkey";

	//������� ��������, ���� ����� ������
	public static final String OUR_BANK_BIC = "com.rssl.iccs.our.bank.bic";
	public static final String OUR_BANK_CORACC = "com.rssl.iccs.our.bank.coracc";

	protected PaymentsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ������������� ��������� ������� ������� � retail
	 * @return
	 */
	public abstract Long getContactRetailId();

	/**
	 * ��� ����������
	 * @return
	 */
	public abstract Long getApplicationKind();
	/**
	 * true ���� ���������� ��������� ���� ��� �������� ����� ����� IKFL
	 * @return
	 */
	public abstract Boolean getNeedAccount();

	/**
	 * ����� �������������, ��� ������� ���� �������� �� �������� ��������
	 * @return
	 */
	public abstract Long getOperateNumber();

	/**
	 * ����� �������� �������� ����� �� ���� � ������ ����
 	 * @return
	 */
	public abstract Long getTransferOtherBankOperation();

	/**
	 * ����� �������� �������� ����� �� ������ ����
	 * @return
	 */
	public abstract Long getTransferOtherAccountOperation();

	/**
	 * ����� �������� �������� ����� �� ���� � ������ ������
	 * @return
	 */
	public abstract Long getTransferConvertOperation();

	/**
	 * ����� �������� �������� �����
	 * @return
	 */
	public abstract Long getOpenAccountOperation();

	/**
	 * ����� �������� �������� �����
	 * @return
	 */
	public abstract Long getCloseAccountOperation();

	/**
	 * ����� �������� ��������
	 * @return
	 */
	public abstract Long getPayAccountOperation();

	/**
	 * TODO ��� ��� �� �����?
	 * @return ������ ���������������� ��������������� ������ �����
	 */
	public abstract ArrayList getBanksList();

	/**
	 *
	 * @return ��� ����������� �������� ����� � �� ���� �� �����
	 */
	public abstract String getTransferOtherBankSuboperationJur();

	/**
	 *
 	 * @return ��� ����������� �������� ����� � �� ���� ��� �����
	 */
	public abstract String getTransferOtherBankSuboperationFiz();

	/**
	 *
	 * @return ��� ����������� �������� ����� � ����-���������������� ������������� ������
	 */
	public abstract String getTransferKorrBankSuboperation();

	/**
	 * 
	 * @return ��� ����������� ������� �� �������� ��� ����
	 */
	public abstract String getContactTransferSuboperationFiz();

	/**
	 *
	 * @return ��� ����������� ������� �� �������� �� ����
	 */
	public abstract String getContactTransferSuboperationJur();

	/**
	 *
	 * @return  ��� ����������� ���������� �������
	 */
	public abstract String getTaxPaymentSuboperation();

	/**
	 * �������� ��� ������ �����. �������� ��������, �� ��������� ���������� ������ ����� �� ���-�����
	 * @return
	 */
	public abstract String getOurBankBic();
	
	/**
	 * �������� �������� ������ �����. �������� ��������, �� ��������� ���������� ������ ����� �� ���-�����
	 * @return
	 */
	public abstract String getOurBankCorAcc();

	/**
	 * ���������� ���� ���������� ������� �������� �� �����.
	 * @return ���� � ������.
	 */
	public abstract String getDateOfStopExternalPayments();


	public abstract Set<String> getProviderCodes();

	public abstract String getEinvoicingUIDKey();

	public abstract String getEinvoicingSumKey();

	public abstract String getEinvoicingInvoiceIDKey();
}
