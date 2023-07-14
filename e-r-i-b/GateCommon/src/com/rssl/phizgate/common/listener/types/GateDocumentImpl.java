package com.rssl.phizgate.common.listener.types;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizgate.common.payments.systems.recipients.RecipientInfoImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.claims.AccountClosingClaim;
import com.rssl.phizic.gate.claims.DepositClosingClaim;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.claims.LoyaltyProgramRegistrationClaim;
import com.rssl.phizic.gate.cms.BlockReason;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.deposit.DepositOpeningClaim;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.gate.loans.QuestionnaireAnswer;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizic.gate.payments.longoffer.CardJurIntraBankTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.LoanTransferLongOffer;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;
import com.rssl.phizic.gate.payments.systems.contact.ContactPersonalPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author egorova
 * @ created 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateDocumentImpl implements GateDocument, SynchronizableDocument, EditDocument, WithdrawDocument, CardBlockingClaim, LoanOpeningClaim, AccountClosingClaim, LossPassbookApplicationClaim,
		AbstractRUSPayment, AccountPaymentSystemPayment, CardPaymentSystemPayment, AbstractJurTransfer, AbstractPhizTransfer,
		ChargeOffPayment, ClientAccountsTransfer, CardRUSTaxPayment, AccountRUSTaxPayment, RUSTaxPayment, ContactPersonalPayment, SWIFTPayment, UtilityPayment, DepositOpeningClaim,	DepositClosingClaim,
		AccountIntraBankPayment, InternalCardsTransfer, ExternalCardsTransferToOtherBank, ExternalCardsTransferToOurBank, AccountToCardTransfer, CardToAccountTransfer, CardJurIntraBankTransferLongOffer, LoanTransferLongOffer, AutoPayment, LoyaltyProgramRegistrationClaim, InvoiceAcceptPayment
{
	private Long id;
	private Calendar clientCreationDate;
	private Calendar clientOperationDate;
	private Calendar additionalOperationDate;
	private Office office;
	private FormType formType;
	private Class<? extends com.rssl.phizic.gate.documents.GateDocument> type;
	private com.rssl.phizic.common.types.Money commission;
	private CommissionOptions commissionOptions;
	private String documentNumber;
	private Calendar admissionDate;
	private RecipientInfo recipientInfo;
	private State state;
	private String externalId;
	private Calendar closingDate;
	private GateDocument transferPayment;
	private String closedAccount;
	private String chargeOffAccount;
	private Money chargeOffAmount;
	private Calendar chargeOffDate;
	private String ground;
	private String depositConditionsId;
	private DateSpan period;
	private String transferAccount;
	private Calendar visitDate;
	private boolean automaticRenewal;
	private String officeExternalId;
	private String account;
	private String destinationAccount;
	private Money destinationAmount;
	private String operationCode;
	private String receiverAccount;
	private String receiverPointCode;
	private String multiBlockReceiverPointCode;
	private Long receiverInternalId;
	private List<Field> extendedFields;
	private RecipientInfoImpl getRecipientInfo;
	private String receiverName;
	private String idFromPaymentSystem;
	private String receiverINN;
	private String receiverKPP;
	private String receiverAlias;
	private String transitAccount;
	private String receiverCountryCode;
	private String receiverSWIFT;
	private SWIFTPaymentConditions conditions;
	private String payerId;
	private String registerNumber;
	private String registerString;
	private String taxKBK;
	private String taxOKATO;
	private Calendar taxDocumentDate;
	private String taxDocumentNumber;
	private String taxPeriod;
	private String taxPaymentType;
	private String taxGround;
	private String taxPaymentStatus;
	private String withdrawExternalId;
	private Long withdrawInternalId;
	private Class<? extends GateDocument> withdrawType;
	private GateDocument editedDocument;
	private Money loanAmount;
	private Money selfAmount;
	private Money objectAmount;
	private DateSpan duration;
	private String conditionsId;
	private Iterator<QuestionnaireAnswer> questionnaireIterator;
	private Iterator<LoanOpeningClaim> guarantorClaimsIterator;
	private Money approvedAmount;
	private DateSpan approvedDuration;
	private String claimNumber;
	private String payerName;
	private String cardNumber;
	private BlockReason blockingReason;
	private String cardExternalId;
	private String depositAccount;
	private int accountAction;
	private String externalDepositId;
	private String receiverSurName;
	private String receiverFirstName;
	private String receiverPatrName;
	private Calendar receiverBornDate;
	private Calendar executionDate;
	private Service service;
	private String chargeOffCard;
	private Calendar chargeOffCardExpireDate;
	private String receiverCard;
	private Calendar receiverCardExpireDate;
	private String authorizeCode;
	private String salesCheck;
	private CurrencyRate debetSaleRate;
	private CurrencyRate debetBuyRate;
	private CurrencyRate creditSaleRate;
	private CurrencyRate creditBuyRate;
	private String number;
	private Calendar startDate;
	private Calendar endDate;
	private ExecutionEventType executionEventType;
	private SumType sumType;
	private Money amount;
	private BigDecimal percent;
	private Long payDay;
	private Long priority;
	private String loanExternalId;
	private String accountNumber;
	private String agreementNumber;
	private String idSpacing;
	private Calendar spacingDate;
	private Calendar authorizeDate;
	private boolean template;
	private String billingClientId;
	private java.lang.String codeService;
    private Money floorLimit;
    private java.lang.String friendlyName;
    private AutoPaymentStatus reportStatus;
	private String requisite;
	private Calendar dateAccepted;
	private InputSumType inputSumType;
	private String billingCode;
	private ResidentBank receiverBank;
	private ResidentBank receiverTransitBank;
	private String receiverBankName;
	private String receiverTransitAccount;
	private String receiverPhone;
	private String receiverNameForBill;
	private String chargeOffCardDescription;
	private boolean notVisibleBankDetails;
	private Code receiverOfficeCode;
	private ReceiverCardType receiverCardType;
	private Calendar cardExpireDate;
	private String phoneNumber;
	private String email;
	private BigDecimal convertionRate;
	private String operationUID;
	private boolean isConnectChargeOffResourceToMobileBank;
	private EmployeeInfo createdEmployeeInfo;
	private EmployeeInfo confirmedEmployeeInfo;
	private Money totalAmountLimit;
	private TotalAmountPeriod totalAmountPeriod;
	private CreationType clientCreationChannel;
	private CreationType clientOperationChannel;
	private CreationType additionalOperationChannel;
	private TemplateInfo templateInfo;
	private String chargeOffCardAccount;
	private WithdrawMode withdrawMode;
	private String mbOperCode;
	private Long sendNodeNumber;
	private List<WriteDownOperation> writeDownOperations = new ArrayList<WriteDownOperation>();
	private String nextState;
	private String autoSubscriptionId;
	private String autoInvoiceId;
	private Currency chargeOffCurrency;
	private Currency destinationCurrency;
	private Long internalOwnerId;
	private String externalOwnerId;
	private String tariffPlanESB;
	private ExternalSystemIntegrationMode integrationMode;

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return chargeOffCardExpireDate;
	}

	public void setChargeOffCardExpireDate(Calendar chargeOffCardExpireDate)
	{
		this.chargeOffCardExpireDate = chargeOffCardExpireDate;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public void setType(String type)
	{
		try
		{
			setType((Class<? extends GateDocument>) Class.forName(type));
		}
		catch (ClassNotFoundException e)
		{
		}
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public RecipientInfo getRecipientInfo()
	{
		return recipientInfo;
	}

	public void setRecipientInfo(RecipientInfo recipientInfo)
	{
		this.recipientInfo = recipientInfo;
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

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public Calendar getAdmissionDate()
	{
		return admissionDate;
	}

	public void setAdmissionDate(Calendar admissionDate)
	{
		this.admissionDate = admissionDate;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public Calendar getClosingDate()
	{
		return closingDate;
	}

	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}

	public GateDocument getTransferPayment()
	{
		return transferPayment;
	}

	public void setTransferPayment(GateDocument transferPayment)
	{
		this.transferPayment = transferPayment;
	}

	public String getClosedAccount()
	{
		return closedAccount;
	}

	public void setClosedAccount(String closedAccount)
	{
		this.closedAccount = closedAccount;
	}

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}

	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	public void setChargeOffAmount(Money chargeOffAmount)
	{
		this.chargeOffAmount = chargeOffAmount;
	}

	public Calendar getChargeOffDate()
	{
		return chargeOffDate;
	}

	public void setChargeOffDate(Calendar chargeOffDate)
	{
		this.chargeOffDate = chargeOffDate;
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public String getDepositConditionsId()
	{
		return depositConditionsId;
	}

	public void setDepositConditionsId(String depositConditionsId)
	{
		this.depositConditionsId = depositConditionsId;
	}

	public DateSpan getPeriod()
	{
		return period;
	}

	public void setPeriod(DateSpan period)
	{
		this.period = period;
	}

	public String getTransferAccount()
	{
		return transferAccount;
	}

	public void setTransferAccount(String transferAccount)
	{
		this.transferAccount = transferAccount;
	}

	public Calendar getVisitDate()
	{
		return visitDate;
	}

	public void setVisitDate(Calendar visitDate)
	{
		this.visitDate = visitDate;
	}

	public boolean isAutomaticRenewal()
	{
		return automaticRenewal;
	}

	public void setAutomaticRenewal(boolean automaticRenewal)
	{
		this.automaticRenewal = automaticRenewal;
	}

	public String getOfficeExternalId()
	{
		return officeExternalId;
	}

	public void setOfficeExternalId(String officeExternalId)
	{
		this.officeExternalId = officeExternalId;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getDestinationAccount()
	{
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount)
	{
		this.destinationAccount = destinationAccount;
	}

	public Money getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(Money destinationAmount)
	{
		this.destinationAmount = destinationAmount;
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

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	public List<Field> getExtendedFields()
	{
		return extendedFields;
	}

	public void setExtendedFields(List<Field> extendedFields)
	{
		this.extendedFields = extendedFields;
	}

	public RecipientInfoImpl getGetRecipientInfo()
	{
		return getRecipientInfo;
	}

	public void setGetRecipientInfo(RecipientInfoImpl getRecipientInfo)
	{
		this.getRecipientInfo = getRecipientInfo;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public String getIdFromPaymentSystem()
	{
		return idFromPaymentSystem;
	}

	public void setIdFromPaymentSystem(String idFromPaymentSystem)
	{
		this.idFromPaymentSystem = idFromPaymentSystem;
	}

	public String getReceiverINN()
	{
		return receiverINN;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public String getReceiverKPP()
	{
		return receiverKPP;
	}

	public void setReceiverKPP(String receiverKPP)
	{
		this.receiverKPP = receiverKPP;
	}

	public String getReceiverAlias()
	{
		return receiverAlias;
	}

	public void setReceiverAlias(String receiverAlias)
	{
		this.receiverAlias = receiverAlias;
	}

	public String getTransitAccount()
	{
		return transitAccount;
	}

	public void setTransitAccount(String transitAccount)
	{
		this.transitAccount = transitAccount;
	}

	public String getReceiverCountryCode()
	{
		return receiverCountryCode;
	}

	public void setReceiverCountryCode(String receiverCountryCode)
	{
		this.receiverCountryCode = receiverCountryCode;
	}

	public String getReceiverSWIFT()
	{
		return receiverSWIFT;
	}

	public void setReceiverSWIFT(String receiverSWIFT)
	{
		this.receiverSWIFT = receiverSWIFT;
	}

	public SWIFTPaymentConditions getConditions()
	{
		return conditions;
	}

	public void setConditions(SWIFTPaymentConditions conditions)
	{
		this.conditions = conditions;
	}

	public void setConditions(String conditions)
	{
		this.conditions = SWIFTPaymentConditions.valueOf(conditions);
	}

	public String getPayerId()
	{
		return payerId;
	}

	public void setPayerId(String payerId)
	{
		this.payerId = payerId;
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

	public String getTaxKBK()
	{
		return taxKBK;
	}

	public void setTaxKBK(String taxKBK)
	{
		this.taxKBK = taxKBK;
	}

	public String getTaxOKATO()
	{
		return taxOKATO;
	}

	public void setTaxOKATO(String taxOKATO)
	{
		this.taxOKATO = taxOKATO;
	}

	public Calendar getTaxDocumentDate()
	{
		return taxDocumentDate;
	}

	public void setTaxDocumentDate(Calendar taxDocumentDate)
	{
		this.taxDocumentDate = taxDocumentDate;
	}

	public String getTaxDocumentNumber()
	{
		return taxDocumentNumber;
	}

	public void setTaxDocumentNumber(String taxDocumentNumber)
	{
		this.taxDocumentNumber = taxDocumentNumber;
	}

	public String getTaxPeriod()
	{
		return taxPeriod;
	}

	public void setTaxPeriod(String taxPeriod)
	{
		this.taxPeriod = taxPeriod;
	}

	public String getTaxGround()
	{
		return taxGround;
	}

	public void setTaxGround(String taxGround)
	{
		this.taxGround = taxGround;
	}

	public String getTaxPaymentStatus()
	{
		return taxPaymentStatus;
	}

	public void setTaxPaymentStatus(String taxPaymentStatus)
	{
		this.taxPaymentStatus = taxPaymentStatus;
	}

	public String getWithdrawExternalId()
	{
		return withdrawExternalId;
	}

	public void setWithdrawExternalId(String withdrawExternalId)
	{
		this.withdrawExternalId = withdrawExternalId;
	}

	public Long getWithdrawInternalId()
	{
		return withdrawInternalId;
	}

	public void setWithdrawInternalId(Long withdrawInternalId)
	{
		this.withdrawInternalId = withdrawInternalId;
	}

	public Class<? extends GateDocument> getWithdrawType()
	{
		return withdrawType;
	}

	public WithdrawMode getWithdrawMode()
	{
		return withdrawMode;
	}

	public void setWithdrawMode(WithdrawMode withdrawMode)
	{
		this.withdrawMode = withdrawMode;
	}

	public void setWithdrawMode(String withdrawMode)
	{
		if(StringHelper.isEmpty(withdrawMode))
			return;

		this.withdrawMode = WithdrawMode.valueOf(withdrawMode);
	}

	public void setWithdrawType(Class<? extends GateDocument> withdrawType)
	{
		this.withdrawType = withdrawType;
	}

	public void setWithdrawType(String type)
	{
		try
		{
			setWithdrawType((Class<? extends GateDocument>) Class.forName(type));
		}
		catch (ClassNotFoundException e)
		{
		}
	}

	public GateDocument getEditedDocument()
	{
		return editedDocument;
	}

	public void setEditedDocument(GateDocument editedDocument)
	{
		this.editedDocument = editedDocument;
	}

	public Money getLoanAmount()
	{
		return loanAmount;
	}

	public void setLoanAmount(Money loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public Money getSelfAmount()
	{
		return selfAmount;
	}

	public void setSelfAmount(Money selfAmount)
	{
		this.selfAmount = selfAmount;
	}

	public Money getObjectAmount()
	{
		return objectAmount;
	}

	public void setObjectAmount(Money objectAmount)
	{
		this.objectAmount = objectAmount;
	}

	public DateSpan getDuration()
	{
		return duration;
	}

	public void setDuration(DateSpan duration)
	{
		this.duration = duration;
	}

	public String getConditionsId()
	{
		return conditionsId;
	}

	public void setConditionsId(String conditionsId)
	{
		this.conditionsId = conditionsId;
	}

	public Iterator<QuestionnaireAnswer> getQuestionnaireIterator()
	{
		return questionnaireIterator;
	}

	public void setQuestionnaireIterator(Iterator<QuestionnaireAnswer> questionnaireIterator)
	{
		this.questionnaireIterator = questionnaireIterator;
	}

	public Iterator<LoanOpeningClaim> getGuarantorClaimsIterator()
	{
		return guarantorClaimsIterator;
	}

	public void setGuarantorClaimsIterator(Iterator<LoanOpeningClaim> guarantorClaimsIterator)
	{
		this.guarantorClaimsIterator = guarantorClaimsIterator;
	}

	public Money getApprovedAmount()
	{
		return approvedAmount;
	}

	public void setApprovedAmount(Money approvedAmount)
	{
		this.approvedAmount = approvedAmount;
	}

	public DateSpan getApprovedDuration()
	{
		return approvedDuration;
	}

	public void setApprovedDuration(DateSpan approvedDuration)
	{
		this.approvedDuration = approvedDuration;
	}

	public String getClaimNumber()
	{
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber)
	{
		this.claimNumber = claimNumber;
	}

	public String getPayerName()
	{
		return payerName;
	}

	public void setPayerName(String payerName)
	{
		this.payerName = payerName;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public BlockReason getBlockingReason()
	{
		return blockingReason;
	}

	public void setBlockingReason(BlockReason blockingReason)
	{
		this.blockingReason = blockingReason;
	}

	public void setBlockingReason(String reason)
	{
		this.blockingReason = BlockReason.valueOf(reason);
	}

	public String getCardExternalId()
	{
		return cardExternalId;
	}

	public void setCardExternalId(String cardExternalId)
	{
		this.cardExternalId = cardExternalId;
	}

	public String getDepositAccount()
	{
		return depositAccount;
	}

	public void setDepositAccount(String depositAccount)
	{
		this.depositAccount = depositAccount;
	}

	public int getAccountAction()
	{
		return accountAction;
	}

	public void setAccountAction(int accountAction)
	{
		this.accountAction = accountAction;
	}

	public String getExternalDepositId()
	{
		return externalDepositId;
	}

	public void setExternalDepositId(String externalDepositId)
	{
		this.externalDepositId = externalDepositId;
	}

	public String getTaxPaymentType()
	{
		return this.taxPaymentType;
	}

	public void setTaxPaymentType(String type)
	{
		this.taxPaymentType = type;
	}

	public String getReceiverFirstName()
	{
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName)
	{
		this.receiverFirstName = receiverFirstName;
	}

	public Calendar getReceiverBornDate()
	{
		return receiverBornDate;
	}

	public void setReceiverBornDate(Calendar receiverBornDate)
	{
		this.receiverBornDate = receiverBornDate;
	}

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public Service getService()
	{
		return service;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public Calendar getReceiverCardExpireDate()
	{
		return receiverCardExpireDate;
	}

	public void setReceiverCardExpireDate(Calendar receiverCardExpireDate)
	{
		this.receiverCardExpireDate = receiverCardExpireDate;
	}

	public String getAuthorizeCode()
	{
		return authorizeCode;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		this.authorizeCode = authorizeCode;
	}

	public String getSalesCheck()
	{
		return salesCheck;
	}

	public void setSalesCheck(String salesCheck)
	{
		this.salesCheck = salesCheck;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public ExecutionEventType getExecutionEventType()
	{
		return executionEventType;
	}

	public void setExecutionEventType(ExecutionEventType executionEventType)
	{
		this.executionEventType = executionEventType;
	}

	public void setExecutionEventType(String executionEventType)
	{
		this.executionEventType = ExecutionEventType.valueOf(executionEventType);
	}

	public SumType getSumType()
	{
		return sumType;
	}

	public void setSumType(SumType sumType)
	{
		this.sumType = sumType;
	}

	public void setSumType(String sumType)
	{
		this.sumType = SumType.valueOf(sumType);
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public BigDecimal getPercent()
	{
		return percent;
	}

	public void setPercent(BigDecimal percent)
	{
		this.percent = percent;
	}

	public Long getPayDay()
	{
		return payDay;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPayDay(Long payDay)
	{
		this.payDay = payDay;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public String getLoanExternalId()
	{
		return loanExternalId;
	}

	public void setLoanExternalId(String loanExternalId)
	{
		this.loanExternalId = loanExternalId;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getIdSpacing()
	{
		return idSpacing;
	}

	public void setIdSpacing(String idSpacing)
	{
		this.idSpacing = idSpacing;
	}

	public Calendar getSpacingDate()
	{
		return spacingDate;
	}

	public void setSpacingDate(Calendar spacingDate)
	{
		this.spacingDate = spacingDate;
	}

	public Calendar getAuthorizeDate()
	{
		return authorizeDate;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		this.authorizeDate = authorizeDate;
	}

	public String getReceiverSurName()
	{
		return receiverSurName;
	}

	public void setReceiverSurName(String receiverSurName)
	{
		this.receiverSurName = receiverSurName;
	}

	public String getReceiverPatrName()
	{
		return receiverPatrName;
	}

	public void setReceiverPatrName(String receiverPatrName)
	{
		this.receiverPatrName = receiverPatrName;
	}

	public boolean isTemplate()
	{
		return template;
	}

	public void setTemplate(boolean template)
	{
		this.template = template;
	}

	public String getBillingClientId()
	{
		return billingClientId;
	}

	public void setBillingClientId(String billingClientId)
	{
		this.billingClientId = billingClientId;
	}

	public String getCodeService()
	{
		return codeService;
	}

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	public Money getFloorLimit()
	{
		return floorLimit;
	}

	public void setFloorLimit(Money floorLimit)
	{
		this.floorLimit = floorLimit;
	}

	public String getFriendlyName()
	{
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName)
	{
		this.friendlyName = friendlyName;
	}

	public AutoPaymentStatus getReportStatus()
	{
		return reportStatus;
	}

	public void setReportStatus(AutoPaymentStatus reportStatus)
	{
		this.reportStatus = reportStatus;
	}

	public void setReportStatus(String reportStatus)
	{
		if(reportStatus == null || reportStatus.trim().length() == 0)
			return;

		this.reportStatus = AutoPaymentStatus.valueOf(reportStatus);
	}

	public String getRequisite()
	{
		return requisite;
	}

	public void setRequisite(String requisite)
	{
		this.requisite = requisite;
	}

	public Calendar getDateAccepted()
	{
		return dateAccepted;
	}

	public void setDateAccepted(Calendar dateAccepted)
	{
		this.dateAccepted = dateAccepted;
	}

	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
		this.inputSumType = inputSumType;
	}

	public void setInputSumType(String inputSumType)
	{
		if(inputSumType == null || inputSumType.trim().length() == 0)
			return;

		this.inputSumType = Enum.valueOf(InputSumType.class, inputSumType);
	}

	public String getBillingCode()
	{
		return billingCode;
	}

	public void setBillingCode(String billingCode)
	{
		this.billingCode = billingCode;
	}

	public ResidentBank getReceiverBank()
	{
		return receiverBank;
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
		this.receiverBank = receiverBank;
	}

	public ResidentBank getReceiverTransitBank()
	{
		return receiverTransitBank;
	}

	public void setReceiverTransitBank(ResidentBank receiverTransitBank)
	{
		this.receiverTransitBank = receiverTransitBank;
	}

	public String getReceiverBankName()
	{
		return receiverBankName;
	}

	public void setReceiverBankName(String receiverBankName)
	{
		this.receiverBankName = receiverBankName;
	}

	public String getReceiverTransitAccount()
	{
		return receiverTransitAccount;
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		this.receiverTransitAccount = receiverTransitAccount;
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

	public String getChargeOffCardDescription()
	{
		return chargeOffCardDescription;
	}

	public void setChargeOffCardDescription(String chargeOffCardDescription)
	{
		this.chargeOffCardDescription = chargeOffCardDescription;
	}

	public ReceiverCardType getReceiverCardType()
	{
		return receiverCardType;
	}

	public void setReceiverCardType(ReceiverCardType receiverCardType)
	{
		this.receiverCardType = receiverCardType;
	}

	public void setReceiverCardType(String receiverCardType)
	{
		if(receiverCardType == null || receiverCardType.trim().length() == 0)
			return;

		this.receiverCardType = ReceiverCardType.valueOf(receiverCardType);
	}

	public Calendar getCardExpireDate()
	{
		return cardExpireDate;
	}

	public void setCardExpireDate(Calendar cardExpireDate)
	{
		this.cardExpireDate = cardExpireDate;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public BigDecimal getConvertionRate()
	{
		return convertionRate;
	}

	public void setConvertionRate(BigDecimal convertionRate)
	{
		this.convertionRate = convertionRate;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public boolean isConnectChargeOffResourceToMobileBank()
	{
		return isConnectChargeOffResourceToMobileBank;
	}

	public void setConnectChargeOffResourceToMobileBank(boolean isConnectChargeOffResourceToMobileBank)
	{
		this.isConnectChargeOffResourceToMobileBank = isConnectChargeOffResourceToMobileBank;
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

	public Money getTotalAmountLimit()
	{
		return totalAmountLimit;
	}

	public void setTotalAmountLimit(Money totalAmountLimit)
	{
		this.totalAmountLimit = totalAmountLimit;
	}

	public TotalAmountPeriod getTotalAmountPeriod()
	{
		return totalAmountPeriod;
	}

	public void setTotalAmountPeriod(TotalAmountPeriod totalAmountPeriod)
	{
		this.totalAmountPeriod = totalAmountPeriod;
	}

	public void setTotalAmountPeriod(String totalAmountPeriod)
	{
		if(StringHelper.isEmpty(totalAmountPeriod))
			return;

		this.totalAmountPeriod = TotalAmountPeriod.valueOf(totalAmountPeriod);
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

	public FormType getFormType()
	{
		return formType;
	}

	public void setFormType(FormType formType)
	{
		this.formType = formType;
	}

	public void setFormType(String formType)
	{
		this.formType = FormType.valueOf(formType);
	}

	public CreationType getClientCreationChannel()
	{
		return clientCreationChannel;
	}

	public void setClientCreationChannel(CreationType clientCreationChannel)
	{
		this.clientCreationChannel = clientCreationChannel;
	}

	public void setClientCreationChannel(String clientCreationChannel)
	{
		this.clientCreationChannel = CreationType.valueOf(clientCreationChannel);
	}

	public CreationType getClientOperationChannel()
	{
		return clientOperationChannel;
	}

	public void setClientOperationChannel(CreationType clientOperationChannel)
	{
		this.clientOperationChannel = clientOperationChannel;
	}

	public void setClientOperationChannel(String clientOperationChannel)
	{
		this.clientOperationChannel = CreationType.valueOf(clientOperationChannel);
	}

	public CreationType getAdditionalOperationChannel()
	{
		return additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(CreationType additionalOperationChannel)
	{
		this.additionalOperationChannel = additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(String additionalOperationChannel)
	{
		this.additionalOperationChannel = CreationType.valueOf(additionalOperationChannel);
	}

	public String getChargeOffCardAccount()
	{
		return chargeOffCardAccount;
	}

	public void setChargeOffCardAccount(String chargeOffCardAccount)
	{
		this.chargeOffCardAccount = chargeOffCardAccount;
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

	public String getAutoInvoiceId()
	{
		return autoInvoiceId;
	}

	public void setAutoInvoiceId(String autoInvoiceId)
	{
		this.autoInvoiceId = autoInvoiceId;
	}

	public String getAutoSubscriptionId()
	{
		return autoSubscriptionId;
	}

	public void setAutoSubscriptionId(String autoSubscriptionId)
	{
		this.autoSubscriptionId = autoSubscriptionId;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return chargeOffCurrency;
	}

	public void setChargeOffCurrency(Currency chargeOffCurrency)
	{
		this.chargeOffCurrency = chargeOffCurrency;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return destinationCurrency;
	}

	public void setDestinationCurrency(Currency destinationCurrency)
	{
		this.destinationCurrency = destinationCurrency;
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
