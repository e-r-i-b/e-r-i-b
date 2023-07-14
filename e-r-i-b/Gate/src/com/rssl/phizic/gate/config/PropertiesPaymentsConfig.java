package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.payments.PaymentsConfig;

import java.util.*;

/**
 * @author Gainanov
 * @ created 14.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class PropertiesPaymentsConfig extends PaymentsConfig
{
	private Boolean needAccount;
	private Long operateNumber;
	private Long transferOtherBankOperation;
	private Long transferOtherAccountOperation;
	private Long transferConvertOperation;
	private Long openAccountOperation;
	private Long closeAccountOperation;
	private Long payAccountOperation;
	private Long appKind;
	private Long contactRetailId;
	private String banksListString;
	private ArrayList bankList = null;
	private String otherBankTransferSubopertaionJur;
	private String otherBankTransferSubopertaionFiz;
	private String corrBankTransferSuboperation;
	private String contactTransferSuboperationFiz;
	private String contactTransferSuboperationJur;
	private String taxPaymentSuboperation;
	private String ourBankBic;
	private String ourBankCorAcc;
	private String dateOfStopExternalAccountPayments;
	private Set<String> providerCodes;
	private String einvoicingUIDKey;
	private String einvoicingSumKey;
	private String einvoicingInvoiceIDKey;

	public PropertiesPaymentsConfig(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public Long getContactRetailId()
	{
		return contactRetailId;
	}

	public Long getApplicationKind()
	{
		return appKind;
	}

	public Boolean getNeedAccount()
	{
		return needAccount;
	}

	public Long getOperateNumber()
	{
		return operateNumber;
	}

	public Long getTransferOtherBankOperation()
	{
		return transferOtherBankOperation;
	}

	public Long getTransferOtherAccountOperation()
	{
		return transferOtherAccountOperation;
	}

	public Long getTransferConvertOperation()
	{
		return transferConvertOperation;
	}

	public Long getOpenAccountOperation()
	{
		return openAccountOperation;
	}

	public Long getCloseAccountOperation()
	{
		return closeAccountOperation;
	}

	public Long getPayAccountOperation()
	{
		return  payAccountOperation;
	}

	public ArrayList getBanksList()
	{
		if(bankList == null)
		{
			StringTokenizer st = new StringTokenizer(banksListString, ";", false);
			bankList = new ArrayList<String>();
			while (st.hasMoreTokens())
			{
				bankList.add(st.nextToken());
			}
		}
		return bankList;
	}

	public String getTransferOtherBankSuboperationJur()
	{
		return otherBankTransferSubopertaionJur;
	}

	public String getTransferOtherBankSuboperationFiz()
	{
		return otherBankTransferSubopertaionFiz;
	}

	public String getTransferKorrBankSuboperation()
	{
		return corrBankTransferSuboperation;
	}

	public String getContactTransferSuboperationFiz()
	{
		return contactTransferSuboperationFiz;
	}

	public String getContactTransferSuboperationJur()
	{
		return contactTransferSuboperationJur;
	}

	public String getTaxPaymentSuboperation()
	{
		return taxPaymentSuboperation;
	}

	public String getOurBankBic()
	{
		return ourBankBic;
	}

	public String getOurBankCorAcc()
	{
		return ourBankCorAcc;
	}

	public void doRefresh() throws ConfigurationException
	{
		needAccount = getBoolProperty(PaymentsConfig.NEED_ACCOUNT);
		operateNumber = getLongProperty(PaymentsConfig.OPERATE_NUMBER);
		transferOtherBankOperation = getLongProperty(PaymentsConfig.OPERATION_TRANSFER_OTHER_BANK);
		transferOtherAccountOperation = getLongProperty(PaymentsConfig.OPERATION_TRANSFER_OTHER_ACCOUNT);
		transferConvertOperation = getLongProperty(PaymentsConfig.OPERATION_TRANSFER_CONVERT);
		openAccountOperation = getLongProperty(PaymentsConfig.OPERATION_OPEN_ACCOUNT);
		closeAccountOperation = getLongProperty(PaymentsConfig.OPERATION_CLOSE_ACCOUNT);
		payAccountOperation = getLongProperty(PaymentsConfig.OPERATION_ACCOUNT_PAY);
		appKind = getLongProperty( PaymentsConfig.SYSTEM_APPLICATION_KIND );
		contactRetailId = getLongProperty( PaymentsConfig.CONTACT_RETAIL_ID );
		banksListString = getProperty(PaymentsConfig.BANKS_LIST);
		otherBankTransferSubopertaionJur = getProperty(PaymentsConfig.SUBOPERATION_TRANSFER_OTHER_BANK_JUR);
		otherBankTransferSubopertaionFiz = getProperty(PaymentsConfig.SUBOPERATION_TRANSFER_OTHER_BANK_FIZ);
		corrBankTransferSuboperation = getProperty(PaymentsConfig.SUBOPERATION_TRANSFER_KORR_BANK);
		contactTransferSuboperationFiz = getProperty(PaymentsConfig.SUBOPERATION_CONTACT_TRANSFER_FIZ);
		contactTransferSuboperationJur = getProperty(PaymentsConfig.SUBOPERATION_CONTACT_TRANSFER_JUR);
		taxPaymentSuboperation = getProperty(PaymentsConfig.SUBOPERATION_TAX_PAYMENT);
		ourBankBic = getProperty(PaymentsConfig.OUR_BANK_BIC);
		ourBankCorAcc = getProperty(PaymentsConfig.OUR_BANK_CORACC);
		dateOfStopExternalAccountPayments = getProperty(PaymentsConfig.DATE_OF_STOP_EXTERNAL_ACCOUNT_PAYMENT);

		String codesStr = getProperty(IQW_PROVIDER_CODE_ONLINE_PAYMENT_SETTING);
		providerCodes = new HashSet<String>(Arrays.asList(codesStr.split(",")));
		einvoicingInvoiceIDKey = getProperty(INVOICE_ID);
		einvoicingUIDKey = getProperty(UID);
		einvoicingSumKey = getProperty(SUM);
	}

	public String getDateOfStopExternalPayments()
	{
		return dateOfStopExternalAccountPayments;
	}

	@Override
	public Set<String> getProviderCodes()
	{
		return providerCodes;
	}

	@Override
	public String getEinvoicingUIDKey()
	{
		return einvoicingUIDKey;
	}

	@Override
	public String getEinvoicingSumKey()
	{
		return einvoicingSumKey;
	}

	@Override
	public String getEinvoicingInvoiceIDKey()
	{
		return einvoicingInvoiceIDKey;
	}
}

