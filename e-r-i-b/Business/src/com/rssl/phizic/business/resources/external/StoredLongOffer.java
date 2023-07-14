package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.business.util.MoneyUtil;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 12.02.13
 * Time: 17:05
 */
public class StoredLongOffer extends AbstractStoredResource<LongOffer, Void> implements LongOffer
{
	private Long                payDay;
	private Money               amount;
	private Calendar            endDate;
	private Calendar            startDate;
	private BigDecimal          percent;
	private ExecutionEventType  executionEventType;
	private Long                priority;
	private Money               floorLimit;
	private String              friendlyName;
	private String              typeInString;
	private SumType             sumType;

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setPayDay(Long payDay)
	{
		this.payDay = payDay;
	}

	public Long getPayDay()
	{
		return payDay;
	}

	public void setTypeInString(String typeInString)
	{
		this.typeInString = typeInString;
	}

	public String getTypeInString()
	{
		return typeInString;
	}

	public Class<? extends GateDocument> getType()
	{
		if (StringHelper.isEmpty(typeInString))
			return null;

		try
		{
			return ClassHelper.loadClass(typeInString);
		}
		catch (ClassNotFoundException e)
		{
			log.error("Ошибка во время загрузки класса: " + typeInString, e);
			return null;
		}
	}

	public void setExecutionEventType(ExecutionEventType executionEventType)
	{
		this.executionEventType = executionEventType;
	}

	public ExecutionEventType getExecutionEventType()
	{
		return executionEventType;
	}

	public void setPercent(BigDecimal percent)
	{
		this.percent = percent;
	}

	public BigDecimal getPercent()
	{
		return percent;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setFloorLimit(Money floorLimit)
	{
		this.floorLimit = floorLimit;
	}

	public Money getFloorLimit()
	{
		return floorLimit;
	}

	public void setSumType(SumType sumType)
	{
		this.sumType = sumType;
	}

	public SumType getSumType()
	{
		return sumType;
	}

	public void setFriendlyName(String friendlyName)
	{
		this.friendlyName = friendlyName;
	}

	public String getFriendlyName()
	{
		return friendlyName;
	}

	public String getNumber()
	{
		return getResourceLink().getNumber();
	}

	public String getCardNumber()
	{
		return ((LongOfferLink) getResourceLink()).getReceiverCard();
	}

	public String getAccountNumber()
	{
		return ((LongOfferLink) getResourceLink()).getReceiverAccount();
	}

	public String getReceiverName()
	{
		return ((LongOfferLink) getResourceLink()).getReceiverName();
	}

	public String getExternalId()
	{
		return getResourceLink().getExternalId();
	}

	@Override
	public void update(LongOffer longOffer)
	{
		this.amount             = longOffer.getAmount();
		this.endDate            = longOffer.getEndDate();
		this.startDate          = longOffer.getStartDate();
		this.executionEventType = longOffer.getExecutionEventType();
		this.payDay             = longOffer.getPayDay();
		this.percent            = longOffer.getPercent();
		this.priority           = longOffer.getPriority();
		this.floorLimit         = longOffer.getFloorLimit();
		this.friendlyName       = longOffer.getFriendlyName();
		this.sumType            = longOffer.getSumType();
		if (longOffer.getType() != null)
			this.typeInString = longOffer.getType().getName();

		try
		{
			if (longOffer.getOffice() != null)
				updateOffice(longOffer.getOffice());
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}

		setEntityUpdateTime( Calendar.getInstance() );
	}

	public boolean needUpdate(LongOffer longOffer)
	{
		try
		{
			if (longOffer.getType() == null || longOffer.getOffice() == null)
				return false;
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}

		if (!MoneyUtil.equalsNullIgnore(amount, longOffer.getAmount()))
			return true;

		if (DateHelper.nullSafeCompare(endDate, longOffer.getEndDate()) != 0)
			return true;

		if (DateHelper.nullSafeCompare(startDate, longOffer.getStartDate()) != 0)
			return true;

		if (executionEventType != longOffer.getExecutionEventType())
			return true;

		if (!NumericUtil.equalsNullIgnore(payDay, longOffer.getPayDay()))
			return true;

		if (!NumericUtil.equalsNullIgnore(percent, longOffer.getPercent()))
			return true;

		if (!NumericUtil.equalsNullIgnore(priority, longOffer.getPriority()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(floorLimit, longOffer.getFloorLimit()))
			return true;

		if (!StringHelper.equalsNullIgnore(friendlyName, longOffer.getFriendlyName()))
			return true;

		if (sumType != longOffer.getSumType())
			return true;

		return false;
	}

	public Money getTotalAmountLimit()
	{
		return null;
	}

	public TotalAmountPeriod getTotalAmountPeriod()
	{
		return null;
	}
}
