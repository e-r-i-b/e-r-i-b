package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 @author Pankin
 @ created 16.09.2010
 @ $Author$
 @ $Revision$
 */
public class LongOfferImpl implements LongOffer
{
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
	private String receiverName;
	private String friendlyName;
	private Money floorLimit;
	private String cardNumber;
	private String accountNumber;
	private Money totalAmountLimit;
	private TotalAmountPeriod totalAmountPeriod;

	public String getExternalId()
	{
		return externalId;
	}

	public String getNumber()
	{
		return number;
	}

	public Office getOffice()
	{
		return office;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public Class<? extends GateDocument> getType()
	{
		return type;
	}

	public ExecutionEventType getExecutionEventType()
	{
		return executionEventType;
	}

	public SumType getSumType()
	{
		return sumType;
	}

	public Money getAmount()
	{
		return amount;
	}
	public Long getPayDay()
	{
		return payDay;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
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

	public void setExecutionEventType(ExecutionEventType executionEventType)
	{
		this.executionEventType = executionEventType;
	}

	public void setExecutionEventType(String executionEventType)
	{
		if(executionEventType == null || executionEventType.trim().length() == 0)
			return;
		this.executionEventType = Enum.valueOf(ExecutionEventType.class, executionEventType);
	}

	public void setSumType(SumType sumType)
	{
		this.sumType = sumType;
	}

	public void setSumType(String sumType)
	{
		if(sumType == null || sumType.trim().length() == 0)
			return;
		this.sumType = Enum.valueOf(SumType.class, sumType);
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public void setPayDay(Long payDay)
	{
		this.payDay = payDay;
	}

	public BigDecimal getPercent()
	{
		return percent;
	}

	public void setPercent(BigDecimal percent)
	{
		this.percent = percent;
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

	public void setTotalAmountPeriod(String totalAmountPeriod)
	{
		if(StringHelper.isEmpty(totalAmountPeriod))
			return;

		this.totalAmountPeriod = TotalAmountPeriod.valueOf(totalAmountPeriod);
	}
}
