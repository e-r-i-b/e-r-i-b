package com.rssl.phizic.einvoicing;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.RecallState;
import com.rssl.phizic.gate.einvoicing.RecallType;
import com.rssl.phizic.gate.einvoicing.ShopRecall;

import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopRecallImpl implements ShopRecall
{
	private String uuid;
	private String orderUuid;
	private String externalId;
	private String utrrno;
	private RecallState state;
	private String receiverCode;
	private Calendar date;
	private RecallType type;
	private Money amount;

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public String getReceiverCode()
	{
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode)
	{
		this.receiverCode = receiverCode;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getOrderUuid()
	{
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid)
	{
		this.orderUuid = orderUuid;
	}

	public RecallState getState()
	{
		return state;
	}

	public void setState(RecallState state)
	{
		this.state = state;
	}

	public String getUtrrno()
	{
		return utrrno;
	}

	public void setUtrrno(String utrrno)
	{
		this.utrrno = utrrno;
	}

	public RecallType getType()
	{
		return type;
	}

	public void setType(RecallType type)
	{
		this.type = type;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}
}
