package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author khudyakov
 * @ created 04.09.14
 * @ $Author$
 * @ $Revision$
 */
public class GateLongOfferImpl extends GateDocumentImpl implements AutoSubscriptionDetailInfo, AutoSubscription
{
	private Calendar nextPayDate;
	private AutoPayStatusType autoPayStatusType;
	private String number;
	private Calendar startDate;
	private Calendar endDate;
	private ExecutionEventType executionEventType;
	private SumType sumType;
	private Money amount;
	private BigDecimal percent;
	private Long payDay;
	private Long priority;
	private String friendlyName;
	private Money floorLimit;
	private Money totalAmountLimit;
	private TotalAmountPeriod totalAmountPeriod;
	private String cardNumber;
	private String accountNumber;
	private boolean connectChargeOffResourceToMobileBank;
	private AlwaysAutoPayScheme alwaysAutoPayScheme;
	private ThresholdAutoPayScheme thresholdAutoPayScheme;
	private InvoiceAutoPayScheme invoiceAutoPayScheme;
	private Boolean executionNow;
	private Calendar updateDate;
	private Calendar createDate;
	private boolean needConfirmation;
	private ChannelType channelType;
	private boolean withCommision;
	private Money maxSumWritePerMonth;
	private String reasonDescription;
	private String groupService;
	private String codeATM;
	private boolean sameTB;
	private String messageToRecipient;
	private String autopayNumber;
	private String receiverFirstName;
	private String receiverPatrName;
	private String receiverSurName;


	public Calendar getNextPayDate()
	{
		return nextPayDate;
	}

	public void setNextPayDate(Calendar nextPayDate)
	{
		this.nextPayDate = nextPayDate;
	}

	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoPayStatusType;
	}

	public void setAutoPayStatusType(AutoPayStatusType autoPayStatusType)
	{
		this.autoPayStatusType = autoPayStatusType;
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

	public SumType getSumType()
	{
		return sumType;
	}

	public void setSumType(SumType sumType)
	{
		this.sumType = sumType;
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

	public void setPayDay(Long payDay)
	{
		this.payDay = payDay;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public String getFriendlyName()
	{
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName)
	{
		this.friendlyName = friendlyName;
	}

	public Money getFloorLimit()
	{
		return floorLimit;
	}

	public void setFloorLimit(Money floorLimit)
	{
		this.floorLimit = floorLimit;
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

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public boolean isConnectChargeOffResourceToMobileBank()
	{
		return connectChargeOffResourceToMobileBank;
	}

	public void setConnectChargeOffResourceToMobileBank(boolean connectChargeOffResourceToMobileBank)
	{
		this.connectChargeOffResourceToMobileBank = connectChargeOffResourceToMobileBank;
	}

	public AlwaysAutoPayScheme getAlwaysAutoPayScheme()
	{
		return alwaysAutoPayScheme;
	}

	public void setAlwaysAutoPayScheme(AlwaysAutoPayScheme alwaysAutoPayScheme)
	{
		this.alwaysAutoPayScheme = alwaysAutoPayScheme;
	}

	public ThresholdAutoPayScheme getThresholdAutoPayScheme()
	{
		return thresholdAutoPayScheme;
	}

	public void setThresholdAutoPayScheme(ThresholdAutoPayScheme thresholdAutoPayScheme)
	{
		this.thresholdAutoPayScheme = thresholdAutoPayScheme;
	}

	public InvoiceAutoPayScheme getInvoiceAutoPayScheme()
	{
		return invoiceAutoPayScheme;
	}

	public void setInvoiceAutoPayScheme(InvoiceAutoPayScheme invoiceAutoPayScheme)
	{
		this.invoiceAutoPayScheme = invoiceAutoPayScheme;
	}

	public Boolean isExecutionNow()
	{
		return executionNow;
	}

	public void setExecutionNow(Boolean executionNow)
	{
		this.executionNow = executionNow;
	}

	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	public Calendar getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Calendar createDate)
	{
		this.createDate = createDate;
	}

	public boolean isNeedConfirmation()
	{
		return needConfirmation;
	}

	public void setNeedConfirmation(boolean needConfirmation)
	{
		this.needConfirmation = needConfirmation;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	public boolean isWithCommision()
	{
		return withCommision;
	}

	public void setWithCommision(boolean withCommision)
	{
		this.withCommision = withCommision;
	}

	public Money getMaxSumWritePerMonth()
	{
		return maxSumWritePerMonth;
	}

	public void setMaxSumWritePerMonth(Money maxSumWritePerMonth)
	{
		this.maxSumWritePerMonth = maxSumWritePerMonth;
	}

	public String getReasonDescription()
	{
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription)
	{
		this.reasonDescription = reasonDescription;
	}

	public String getGroupService()
	{
		return groupService;
	}

	public void setGroupService(String groupService)
	{
		this.groupService = groupService;
	}

	public String getCodeATM()
	{
		return codeATM;
	}

	public void setCodeATM(String codeATM)
	{
		this.codeATM = codeATM;
	}

	public boolean isSameTB()
	{
		return sameTB;
	}

	public void setSameTB(boolean sameTB)
	{
		this.sameTB = sameTB;
	}

	public String getMessageToRecipient()
	{
		return messageToRecipient;
	}

	public void setMessageToRecipient(String messageToRecipient)
	{
		this.messageToRecipient = messageToRecipient;
	}

	public String getAutopayNumber()
	{
		return autopayNumber;
	}

	public void setAutopayNumber(String autopayNumber)
	{
		this.autopayNumber = autopayNumber;
	}

	public String getReceiverFirstName()
	{
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName)
	{
		this.receiverFirstName = receiverFirstName;
	}

	public String getReceiverPatrName()
	{
		return receiverPatrName;
	}

	public void setReceiverPatrName(String receiverPatrName)
	{
		this.receiverPatrName = receiverPatrName;
	}

	public String getReceiverSurName()
	{
		return receiverSurName;
	}

	public void setReceiverSurName(String receiverSurName)
	{
		this.receiverSurName = receiverSurName;
	}

	public String getLongOfferExternalId()
	{
		return getExternalId();
	}
}
