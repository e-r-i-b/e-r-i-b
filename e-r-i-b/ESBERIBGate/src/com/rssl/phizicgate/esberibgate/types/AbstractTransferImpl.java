package com.rssl.phizicgate.esberibgate.types;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 23.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AbstractTransferImpl extends LongOfferImpl implements AbstractTransfer
{
	private Money chargeOffAmount;
	private Money destinationAmount;
	private CurrencyRate debetSaleRate;
	private CurrencyRate debetBuyRate;
	private CurrencyRate creditSaleRate;
	private CurrencyRate creditBuyRate;
	private String externalId;
	private State state;
	private Calendar executionDate;
	private Calendar clientCreationDate;
	private Calendar clientOperationDate;
	private Calendar additionalOperationDate;
	private Long id;
	private Office office;
	private Class<? extends GateDocument> type;
	private FormType formType;
	private Money commission;
	private CommissionOptions commissionOptions;
	private String documentNumber;
	private Calendar admissionDate;
	private boolean template;
	private InputSumType inputSumType;
	private String operationCode;
	private String ground;
	private EmployeeInfo createdEmployeeInfo;
	private EmployeeInfo confirmedEmployeeInfo;
	private CreationType clientCreationChannel;
	private CreationType clientOperationChannel;
	private CreationType additionalOperationChannel;
	private String chargeOffCardAccount;
	private Long receiverInternalId;
	private String mbOperCode;
	private Long sendNodeNumber;
	private Long internalOwnerId;
	private String externalOwnerId;
	private String payerName;
	private List<WriteDownOperation> writeDownOperations = new ArrayList<WriteDownOperation>();
	private LongOfferPayDay longOfferPayDay;
	private String tariffPlanESB;
	private ExternalSystemIntegrationMode integrationMode;

	public AbstractTransferImpl()
	{
	}

	public AbstractTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
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

	public BigDecimal getConvertionRate()
	{
		return null;
	}

	public void setCreditBuyRate(CurrencyRate creditBuyRate)
	{
		this.creditBuyRate = creditBuyRate;
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

	public Money getCommission()
	{
		return commission;
	}

	public void setCommission(Money commission)
	{
		this.commission = commission;
	}

	public boolean isTemplate()
	{
		return template;
	}

	public void setTemplate(boolean template)
	{
		this.template = template;
	}

	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
		this.inputSumType = inputSumType;
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

	public String getChargeOffCardAccount()
	{
		return chargeOffCardAccount;
	}

	public void setChargeOffCardAccount(String chargeOffCardAccount)
	{
		this.chargeOffCardAccount = chargeOffCardAccount;
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
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
		throw new UnsupportedOperationException();
	}

	public void setNextState(String nextState)
	{
		throw new UnsupportedOperationException();
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

	public String getPayerName()
	{
		return payerName;
	}

	public void setPayerName(String payerName)
	{
		this.payerName = payerName;
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
