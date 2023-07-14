package com.rssl.phizicgate.iqwave.types;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 02.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentImpl implements AutoPayment
{
	private String cardNumber;
	private String accountNumber;
	private String codeService;
	private Money floorLimit;
	private String friendlyName;
	private AutoPaymentStatus reportStatus;
	private Calendar dateAccepted;
	private String externalId;
	private String number;
	private Office office;
	private Calendar startDate;
	private Calendar endDate;
	private Class<? extends GateDocument> type;
	private ExecutionEventType executionEventType;
	private SumType sumType;
	private Money amount;
	private BigDecimal percent;
	private Long payDay;
	private Long priority;
	private String requisite;
	private String receiverName;
	private Money totalAmountLimit;
	private TotalAmountPeriod totalAmountPeriod;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
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

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
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

	public Class<? extends GateDocument> getType()
	{
		return type;
	}

	public void setType(Class<? extends GateDocument> type)
	{
		this.type = type;
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

	public String getRequisite()
	{
		return requisite;
	}

	public void setRequisite(String requisite)
	{
		this.requisite = requisite;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public Calendar getDateAccepted()
	{
		return dateAccepted;
	}

	public void setDateAccepted(Calendar dateAccepted)
	{
		this.dateAccepted = dateAccepted;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
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
}
