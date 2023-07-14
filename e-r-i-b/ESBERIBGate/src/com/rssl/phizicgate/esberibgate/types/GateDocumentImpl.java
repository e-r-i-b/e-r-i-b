package com.rssl.phizicgate.esberibgate.types;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author khudyakov
 * @ created 04.09.14
 * @ $Author$
 * @ $Revision$
 */
public class GateDocumentImpl implements CardPaymentSystemPayment, CardsTransfer
{
	private Long id;
	private String documentNumber;
	private Calendar clientCreationDate;
	private Calendar clientOperationDate;
	private Calendar additionalOperationDate;
	private Calendar admissionDate;
	private Office office;
	private Class<? extends GateDocument> type;
	private FormType formType;
	private Money commission;
	private CommissionOptions commissionOptions;
	private Long internalOwnerId;
	private String externalOwnerId;
	private EmployeeInfo createdEmployeeInfo;
	private EmployeeInfo confirmedEmployeeInfo;
	private CreationType clientCreationChannel;
	private CreationType clientOperationChannel;
	private CreationType additionalOperationChannel;
	private boolean template;
	private List<WriteDownOperation> writeDownOperations;
	private String nextState;
	private String chargeOffCard;
	private Currency chargeOffCurrency;
	private String chargeOffCardAccount;
	private Calendar chargeOffCardExpireDate;
	private String chargeOffCardDescription;
	private String authorizeCode;
	private Calendar authorizeDate;
	private String billingCode;
	private String billingClientId;
	private Service service;
	private List<Field> extendedFields;
	private String receiverTransitAccount;
	private ResidentBank receiverTransitBank;
	private String receiverPhone;
	private String receiverNameForBill;
	private boolean notVisibleBankDetails;
	private Code receiverOfficeCode;
	private String idFromPaymentSystem;
	private String salesCheck;
	private String receiverPointCode;
	private String multiBlockReceiverPointCode;
	private Long receiverInternalId;
	private String receiverName;
	private String receiverKPP;
	private String receiverINN;
	private String receiverAccount;
	private String receiverCard;
	private Currency destinationCurrency;
	private Calendar receiverCardExpireDate;
	private ReceiverCardType receiverCardType;
	private ResidentBank receiverBank;
	private Money chargeOffAmount;
	private Money destinationAmount;
	private InputSumType inputSumType;
	private CurrencyRate debetSaleRate;
	private CurrencyRate debetBuyRate;
	private CurrencyRate creditSaleRate;
	private CurrencyRate creditBuyRate;
	private BigDecimal convertionRate;
	private String operationCode;
	private String ground;
	private String payerName;
	private String externalId;
	private State state;
	private Calendar executionDate;
	private String mbOperCode;
	private Long sendNodeNumber;
	private String operationUID;
	private String registerNumber;
	private String registerString;
	private LongOfferPayDay longOfferPayDay;
	private String tariffPlanESB;
	private ExternalSystemIntegrationMode integrationMode;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public Calendar getClientCreationDate()
	{
		return clientCreationDate;
	}

	public void setClientCreationDate(Calendar clientCreationDate)
	{
		this.clientCreationDate = clientCreationDate;
	}

	public Calendar getClientOperationDate()
	{
		return clientOperationDate;
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{
		this.clientOperationDate = clientOperationDate;
	}

	public Calendar getAdditionalOperationDate()
	{
		return additionalOperationDate;
	}

	public void setAdditionalOperationDate(Calendar additionalOperationDate)
	{
		this.additionalOperationDate = additionalOperationDate;
	}

	public Calendar getAdmissionDate()
	{
		return admissionDate;
	}

	public void setAdmissionDate(Calendar admissionDate)
	{
		this.admissionDate = admissionDate;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public Class<? extends GateDocument> getType()
	{
		return type;
	}

	public void setType(Class<? extends GateDocument> type)
	{
		this.type = type;
	}

	public FormType getFormType()
	{
		return formType;
	}

	public void setFormType(FormType formType)
	{
		this.formType = formType;
	}

	public Money getCommission()
	{
		return commission;
	}

	public void setCommission(Money commission)
	{
		this.commission = commission;
	}

	public CommissionOptions getCommissionOptions()
	{
		return commissionOptions;
	}

	public void setCommissionOptions(CommissionOptions commissionOptions)
	{
		this.commissionOptions = commissionOptions;
	}

	public Long getInternalOwnerId()
	{
		return internalOwnerId;
	}

	public void setInternalOwnerId(Long internalOwnerId)
	{
		this.internalOwnerId = internalOwnerId;
	}

	public String getExternalOwnerId()
	{
		return externalOwnerId;
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		this.externalOwnerId = externalOwnerId;
	}

	public EmployeeInfo getCreatedEmployeeInfo()
	{
		return createdEmployeeInfo;
	}

	public void setCreatedEmployeeInfo(EmployeeInfo createdEmployeeInfo)
	{
		this.createdEmployeeInfo = createdEmployeeInfo;
	}

	public EmployeeInfo getConfirmedEmployeeInfo()
	{
		return confirmedEmployeeInfo;
	}

	public boolean isEinvoicing()
	{
		return false;
	}

	public void setConfirmedEmployeeInfo(EmployeeInfo confirmedEmployeeInfo)
	{
		this.confirmedEmployeeInfo = confirmedEmployeeInfo;
	}

	public CreationType getClientCreationChannel()
	{
		return clientCreationChannel;
	}

	public void setClientCreationChannel(CreationType clientCreationChannel)
	{
		this.clientCreationChannel = clientCreationChannel;
	}

	public CreationType getClientOperationChannel()
	{
		return clientOperationChannel;
	}

	public void setClientOperationChannel(CreationType clientOperationChannel)
	{
		this.clientOperationChannel = clientOperationChannel;
	}

	public CreationType getAdditionalOperationChannel()
	{
		return additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(CreationType additionalOperationChannel)
	{
		this.additionalOperationChannel = additionalOperationChannel;
	}

	public boolean isTemplate()
	{
		return template;
	}

	public void setTemplate(boolean template)
	{
		this.template = template;
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		return writeDownOperations;
	}

	public void setWriteDownOperations(List<WriteDownOperation> writeDownOperations)
	{
		this.writeDownOperations = writeDownOperations;
	}

	public String getNextState()
	{
		return nextState;
	}

	public void setNextState(String nextState)
	{
		this.nextState = nextState;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Currency getChargeOffCurrency()
	{
		return chargeOffCurrency;
	}

	public void setChargeOffCurrency(Currency chargeOffCurrency)
	{
		this.chargeOffCurrency = chargeOffCurrency;
	}

	public String getChargeOffCardAccount()
	{
		return chargeOffCardAccount;
	}

	public void setChargeOffCardAccount(String chargeOffCardAccount)
	{
		this.chargeOffCardAccount = chargeOffCardAccount;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return chargeOffCardExpireDate;
	}

	public void setChargeOffCardExpireDate(Calendar chargeOffCardExpireDate)
	{
		this.chargeOffCardExpireDate = chargeOffCardExpireDate;
	}

	public String getChargeOffCardDescription()
	{
		return chargeOffCardDescription;
	}

	public void setChargeOffCardDescription(String chargeOffCardDescription)
	{
		this.chargeOffCardDescription = chargeOffCardDescription;
	}

	public String getAuthorizeCode()
	{
		return authorizeCode;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		this.authorizeCode = authorizeCode;
	}

	public Calendar getAuthorizeDate()
	{
		return authorizeDate;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		this.authorizeDate = authorizeDate;
	}

	public String getBillingCode()
	{
		return billingCode;
	}

	public void setBillingCode(String billingCode)
	{
		this.billingCode = billingCode;
	}

	public String getBillingClientId()
	{
		return billingClientId;
	}

	public void setBillingClientId(String billingClientId)
	{
		this.billingClientId = billingClientId;
	}

	public Service getService()
	{
		return service;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public List<Field> getExtendedFields()
	{
		return extendedFields;
	}

	public void setExtendedFields(List<Field> extendedFields)
	{
		this.extendedFields = extendedFields;
	}

	public String getReceiverTransitAccount()
	{
		return receiverTransitAccount;
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		this.receiverTransitAccount = receiverTransitAccount;
	}

	public ResidentBank getReceiverTransitBank()
	{
		return receiverTransitBank;
	}

	public void setReceiverTransitBank(ResidentBank receiverTransitBank)
	{
		this.receiverTransitBank = receiverTransitBank;
	}

	public String getReceiverPhone()
	{
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone)
	{
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverNameForBill()
	{
		return receiverNameForBill;
	}

	public void setReceiverNameForBill(String receiverNameForBill)
	{
		this.receiverNameForBill = receiverNameForBill;
	}

	public boolean isNotVisibleBankDetails()
	{
		return notVisibleBankDetails;
	}

	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
		this.notVisibleBankDetails = notVisibleBankDetails;
	}

	public Code getReceiverOfficeCode()
	{
		return receiverOfficeCode;
	}

	public void setReceiverOfficeCode(Code receiverOfficeCode)
	{
		this.receiverOfficeCode = receiverOfficeCode;
	}

	public String getIdFromPaymentSystem()
	{
		return idFromPaymentSystem;
	}

	public void setIdFromPaymentSystem(String idFromPaymentSystem)
	{
		this.idFromPaymentSystem = idFromPaymentSystem;
	}

	public String getSalesCheck()
	{
		return salesCheck;
	}

	public void setSalesCheck(String salesCheck)
	{
		this.salesCheck = salesCheck;
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public String getReceiverKPP()
	{
		return receiverKPP;
	}

	public void setReceiverKPP(String receiverKPP)
	{
		this.receiverKPP = receiverKPP;
	}

	public String getReceiverINN()
	{
		return receiverINN;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public Currency getDestinationCurrency()
	{
		return destinationCurrency;
	}

	public void setDestinationCurrency(Currency destinationCurrency)
	{
		this.destinationCurrency = destinationCurrency;
	}

	public Calendar getReceiverCardExpireDate()
	{
		return receiverCardExpireDate;
	}

	public void setReceiverCardExpireDate(Calendar receiverCardExpireDate)
	{
		this.receiverCardExpireDate = receiverCardExpireDate;
	}

	public ReceiverCardType getReceiverCardType()
	{
		return receiverCardType;
	}

	public void setReceiverCardType(ReceiverCardType receiverCardType)
	{
		this.receiverCardType = receiverCardType;
	}

	public ResidentBank getReceiverBank()
	{
		return receiverBank;
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
		this.receiverBank = receiverBank;
	}

	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	public void setChargeOffAmount(Money chargeOffAmount)
	{
		this.chargeOffAmount = chargeOffAmount;
	}

	public Money getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(Money destinationAmount)
	{
		this.destinationAmount = destinationAmount;
	}

	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
		this.inputSumType = inputSumType;
	}

	public CurrencyRate getDebetSaleRate()
	{
		return debetSaleRate;
	}

	public void setDebetSaleRate(CurrencyRate debetSaleRate)
	{
		this.debetSaleRate = debetSaleRate;
	}

	public CurrencyRate getDebetBuyRate()
	{
		return debetBuyRate;
	}

	public void setDebetBuyRate(CurrencyRate debetBuyRate)
	{
		this.debetBuyRate = debetBuyRate;
	}

	public CurrencyRate getCreditSaleRate()
	{
		return creditSaleRate;
	}

	public void setCreditSaleRate(CurrencyRate creditSaleRate)
	{
		this.creditSaleRate = creditSaleRate;
	}

	public CurrencyRate getCreditBuyRate()
	{
		return creditBuyRate;
	}

	public void setCreditBuyRate(CurrencyRate creditBuyRate)
	{
		this.creditBuyRate = creditBuyRate;
	}

	public BigDecimal getConvertionRate()
	{
		return convertionRate;
	}

	public void setConvertionRate(BigDecimal convertionRate)
	{
		this.convertionRate = convertionRate;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public String getPayerName()
	{
		return payerName;
	}

	public void setPayerName(String payerName)
	{
		this.payerName = payerName;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public String getMbOperCode()
	{
		return mbOperCode;
	}

	public void setMbOperCode(String mbOperCode)
	{
		this.mbOperCode = mbOperCode;
	}

	public Long getSendNodeNumber()
	{
		return sendNodeNumber;
	}

	public void setSendNodeNumber(Long sendNodeNumber)
	{
		this.sendNodeNumber = sendNodeNumber;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public String getRegisterNumber()
	{
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber)
	{
		this.registerNumber = registerNumber;
	}

	public String getRegisterString()
	{
		return registerString;
	}

	public void setRegisterString(String registerString)
	{
		this.registerString = registerString;
	}

	public LongOfferPayDay getLongOfferPayDay()
	{
		return longOfferPayDay;
	}

	public void setLongOfferPayDay(LongOfferPayDay longOfferPayDay)
	{
		this.longOfferPayDay = longOfferPayDay;
	}

	public String getTariffPlanESB()
	{
		return tariffPlanESB;
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{
		this.tariffPlanESB = tariffPlanESB;
	}


	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		return integrationMode;
	}

	/**
	 * задать режим интеграции
	 * @param integrationMode режим
	 */
	public void setIntegrationMode(ExternalSystemIntegrationMode integrationMode)
	{
		this.integrationMode = integrationMode;
	}

	/**
	 * задать режим интеграции
	 * @param integrationMode режим
	 */
	public void setIntegrationMode(String integrationMode)
	{
		if (StringHelper.isEmpty(integrationMode))
			return;

		setIntegrationMode(ExternalSystemIntegrationMode.valueOf(integrationMode));
	}
}
