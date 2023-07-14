package com.rssl.phizic.business.longoffer.mock;

import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 24.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockLongOffer implements LongOffer, MockObject
{
	protected  static String EMPTY_STRING = "";

	private String externalId = EMPTY_STRING;
	private String number = null;
 	private Office office = null;
	private Calendar startDate = null;
	private Calendar endDate = null;
	private Class<? extends GateDocument> type = null;
	private ExecutionEventType executionEventType = null;
	private SumType sumType = null;
	private Money amount = null;
	private BigDecimal percent = null;
	private Long payDay = null;
	private Long priority = null;
	private String receiverName;
	private String friendlyName;
	private Money floorLimit;
	private String cardNumber;
	private String accountNumber;
	private Money totalAmountLimit;
	private TotalAmountPeriod totalAmountPeriod;

	public MockLongOffer()
	{
	}

	public MockLongOffer(String externalId)
	{
		this.externalId = externalId;
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

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
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
}
