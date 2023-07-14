package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.documents.payments.CurrencyPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.business.documents.templates.Constants.NODE_NUMBER_ATTRIBUTE_NAME;

/**
 * @author Krenev
 * @ created 23.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountClosingTransferDocumentAdapter implements ClientAccountsTransfer, AbstractRUSPayment, SWIFTPayment
{
	private AccountClosingClaim accountClosingClaim;
	public static final String TRANSFER_TYPE_ATTRIBUTE_NAME = "transferType";
	public static final String TRANSFER_TYPE_INTERNAL = "internal";
	public static final String TRANSFER_TYPE_RUSPAYMENT = "ruspayment";
	public static final String TRANSFER_TYPE_SWIFTPAYMENT = "swiftpayment";

	public AccountClosingTransferDocumentAdapter(AccountClosingClaim accountClosingClaim)
	{
		this.accountClosingClaim = accountClosingClaim;
	}

	public Long getId()
	{
		return accountClosingClaim.getId();
	}

	public Calendar getClientCreationDate()
	{
		return accountClosingClaim.getClientCreationDate();
	}

	public Calendar getClientOperationDate()
	{
		return accountClosingClaim.getClientOperationDate();
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{
		accountClosingClaim.setClientOperationDate(clientOperationDate);
	}

	public Calendar getAdditionalOperationDate()
	{
		return accountClosingClaim.getAdditionalOperationDate();
	}

	public Long getInternalOwnerId() throws GateException
	{
		return accountClosingClaim.getInternalOwnerId();
	}

	public String getExternalOwnerId()
	{
		return accountClosingClaim.getExternalOwnerId();
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		throw new UnsupportedOperationException();
	}

	public Office getOffice()
	{
		return accountClosingClaim.getOffice();
	}

	public void setOffice(Office office)
	{
		throw new UnsupportedOperationException();
	}

	public Class<? extends GateDocument> getType()
	{
		String transferType = getAttributeStringValue(TRANSFER_TYPE_ATTRIBUTE_NAME);
		if (TRANSFER_TYPE_INTERNAL.equals(transferType)){
			return ClientAccountsTransfer.class;
		}
		if (TRANSFER_TYPE_RUSPAYMENT.equals(transferType)){
			return AbstractRUSPayment.class;
		}
		if (TRANSFER_TYPE_SWIFTPAYMENT.equals(transferType)){
			return SWIFTPayment.class;
		}
		throw new UnsupportedOperationException("неподдерживаемый тип перевода "+transferType);
	}

	public FormType getFormType()
	{
		return accountClosingClaim.getFormType();
	}

	public Money getCommission()
	{
		return accountClosingClaim.getCommission();
	}

	public void setCommission(Money commission)
	{
		throw new UnsupportedOperationException();
	}

	public CommissionOptions getCommissionOptions()
	{
		return accountClosingClaim.getCommissionOptions();
	}

	public EmployeeInfo getCreatedEmployeeInfo() throws GateException
	{
		return accountClosingClaim.getCreatedEmployeeInfo();
	}

	public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
	{
		return accountClosingClaim.getConfirmedEmployeeInfo();
	}

	public CreationType getClientCreationChannel()
	{
		return accountClosingClaim.getClientCreationChannel();
	}

	public CreationType getClientOperationChannel()
	{
		return accountClosingClaim.getClientOperationChannel();
	}

	public CreationType getAdditionalOperationChannel()
	{
		return accountClosingClaim.getAdditionalOperationChannel();
	}

	public String getDocumentNumber()
	{
		return accountClosingClaim.getDocumentNumber();
	}

	public Calendar getAdmissionDate()
	{
		return accountClosingClaim.getAdmissionDate();
	}

	public boolean isTemplate()
	{
		return accountClosingClaim.isTemplate();
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		return accountClosingClaim.getWriteDownOperations();
	}

	public void setWriteDownOperations(List<WriteDownOperation> list)
	{
		accountClosingClaim.setWriteDownOperations(list);
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{
		throw new UnsupportedOperationException();
	}

	public String getNextState()
	{
		throw new UnsupportedOperationException();
	}

	public void setNextState(String nextState)
	{
		throw new UnsupportedOperationException();
	}

	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		return accountClosingClaim.getIntegrationMode();
	}

	public String getChargeOffAccount()
	{
		return accountClosingClaim.getClosedAccount();
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return accountClosingClaim.getChargeOffCurrency();
	}

	public Money getChargeOffAmount()
	{
		return accountClosingClaim.getChargeOffAmount();
	}

	public void setChargeOffAmount(Money amount)
	{
		//TODO
	}

	public void setChargeOffDate(Calendar chargeOffDate)
	{
		//TODO
	}

	public String getGround()
	{
		return getAttributeStringValue(AbstractPaymentDocument.GROUND_ATTRIBUTE_NAME);
	}

	public void setGround(String ground)
	{
		accountClosingClaim.setNullSaveAttributeStringValue(AbstractPaymentDocument.GROUND_ATTRIBUTE_NAME, ground);
	}

	public String getPayerName()
	{
		return accountClosingClaim.getPayerName();
	}

	public String getReceiverName()
	{
		return getAttributeStringValue(AbstractAccountsTransfer.RECEIVER_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverName(String receiverName)
	{
		accountClosingClaim.setNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_NAME_ATTRIBUTE_NAME, receiverName);
	}

	public String getReceiverSurName()
	{
		return getAttributeStringValue(AbstractAccountsTransfer.RECEIVER_SUR_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverFirstName()
	{
		return getAttributeStringValue(AbstractAccountsTransfer.RECEIVER_FIRST_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverPatrName()
	{
		return getAttributeStringValue(AbstractAccountsTransfer.RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverAccount()
	{
		return getAttributeStringValue(AbstractAccountsTransfer.RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
	}

	public Currency getDestinationCurrency() throws GateException
	{
		String currencyCode = getAttributeStringValue(AbstractAccountsTransfer.DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(currencyCode))
		{
			return null;
		}
		return accountClosingClaim.findCurrencyByISOCode(currencyCode);
	}

	public ResidentBank getReceiverBank()
	{
		ResidentBank result = new ResidentBank();
		result.setAccount(getReceiverCorAccount());
		result.setBIC(getReceiverBIC());
		result.setName(getReceiverBankName());
		return result;
	}

	public String getReceiverBIC()
	{
		return getAttributeStringValue(RurPayment.RECEIVER_BIC_ATTRIBUTE_NAME);
	}

	public String getReceiverCorAccount()
	{
		return getAttributeStringValue(RurPayment.RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME);
	}

	public String getReceiverINN()
	{
		return getAttributeStringValue(RurPayment.RECEIVER_INN_ATTRIBUTE_NAME);
	}

	public String getReceiverKPP()
	{
		return getAttributeStringValue(RurPayment.RECEIVER_KPP_ATTRIBUTE_NAME);
	}

	public String getReceiverBankName()
	{
		return getAttributeStringValue(RurPayment.RECEIVER_BANK_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverAlias()
	{
		return getAttributeStringValue(RurPayment.RECEIVER_ALIAS_ATTRIBUTE_NAME);
	}

	public String getReceiverCountryCode()
	{
		return getAttributeStringValue(CurrencyPayment.RECEIVER_COUNTRY_CODE_ATTRIBUTE_NAME);
	}

	public String getReceiverSWIFT()
	{
		return getAttributeStringValue(CurrencyPayment.RECEIVER_BANK_SWIFT_ATTRIBUTE_NAME);
	}

	public SWIFTPaymentConditions getConditions()
	{
		String conditions = getAttributeStringValue(CurrencyPayment.PAYMENT_CONDITIONS_ATTRIBUTE_NAME);
		if (conditions == null || conditions.trim().length() == 0)
		{
			return null;
		}
		return SWIFTPaymentConditions.valueOf(conditions);
	}

	private String getAttributeStringValue(String name)
	{
		return accountClosingClaim.getNullSaveAttributeStringValue(name);
	}

	private Long getAttributeLongValue(String name)
	{
		return accountClosingClaim.getNullSaveAttributeLongValue(name);
	}

	private void setAttributeLongValue(String name, Long value)
	{
		accountClosingClaim.setNullSaveAttributeLongValue(name, value);
	}

	public String getExternalId()
	{
		return accountClosingClaim.getExternalId();
	}

	public void setExternalId(String externalId)
	{
		throw new UnsupportedOperationException();
	}

	public State getState()
	{
		return accountClosingClaim.getState();
	}

	public Calendar getExecutionDate()
	{
		return accountClosingClaim.getExecutionDate();
	}

	public void setExecutionDate(Calendar executionDate)
	{
		throw new UnsupportedOperationException();
	}

	public void setState(State state)
	{
		throw new UnsupportedOperationException();
	}

	public Money getDestinationAmount()
	{
		//TODO
		return null;
	}

	public void setDestinationAmount(Money amount)
	{
		//TODO
	}

	public InputSumType getInputSumType()
	{
		return accountClosingClaim.getInputSumType();
	}

	public CurrencyRate getDebetSaleRate()
	{
		//TODO
		return null;
	}

	public CurrencyRate getDebetBuyRate()
	{
		//TODO
		return null;
	}

	public CurrencyRate getCreditSaleRate()
	{
		//TODO
		return null;
	}

	public CurrencyRate getCreditBuyRate()
	{
		//TODO
		return null;
	}

	public BigDecimal getConvertionRate()
	{
		return null;
	}

	public String getOperationCode()
	{
		return accountClosingClaim.getOperationCode();
	}

	public String getMbOperCode()
	{
		throw new UnsupportedOperationException();
	}

	public void setMbOperCode(String mbOperCode)
	{
		throw new UnsupportedOperationException();
	}

	public Long getSendNodeNumber()
	{
		return getAttributeLongValue(NODE_NUMBER_ATTRIBUTE_NAME);
	}

	public void setSendNodeNumber(Long nodeNumber)
	{
		setAttributeLongValue(NODE_NUMBER_ATTRIBUTE_NAME, nodeNumber);
	}
}
