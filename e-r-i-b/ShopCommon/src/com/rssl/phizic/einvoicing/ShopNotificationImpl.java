package com.rssl.phizic.einvoicing;

import com.rssl.phizic.gate.einvoicing.NotificationStatus;
import com.rssl.phizic.gate.einvoicing.ShopNotification;
import com.rssl.phizic.gate.einvoicing.ShopOrder;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopNotificationImpl implements ShopNotification, Serializable
{
	private String uuid;
	private String externalId;
	private String utrrno;
	private String state;
	private String receiverCode;
	private NotificationStatus notifStatus;
	private Calendar notifTime;
	private Long notifCount = 0L;
	private String notifStatusDescription;
	private Calendar date = Calendar.getInstance();

	/**
	 * ctor
	 */
	public ShopNotificationImpl()
	{}

	/**
	 * ctor
	 * @param order - заказ
	 */
	public ShopNotificationImpl(ShopOrder order)
	{
		uuid = order.getUuid();
		externalId = order.getExternalId();
		utrrno = order.getUtrrno();
		state = order.getState().name();
		receiverCode = order.getReceiverCode();
		notifStatus = NotificationStatus.CREATED;
	}

	/**
	 * ctor
	 * @param recall - отмена/возврат
	 */
	public ShopNotificationImpl(ShopRecallImpl recall)
	{
		uuid = recall.getOrderUuid();
		externalId = recall.getExternalId();
		utrrno = recall.getUtrrno();
		state = recall.getState().name();
		receiverCode = recall.getReceiverCode();
		notifStatus = NotificationStatus.CREATED;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public Long getNotifCount()
	{
		return notifCount;
	}

	public void setNotifCount(Long notifCount)
	{
		this.notifCount = notifCount;
	}

	public NotificationStatus getNotifStatus()
	{
		return notifStatus;
	}

	public void setNotifStatus(NotificationStatus notifStatus)
	{
		this.notifStatus = notifStatus;
	}

	public String getNotifStatusDescription()
	{
		return notifStatusDescription;
	}

	public String getReceiverCode()
	{
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode)
	{
		this.receiverCode = receiverCode;
	}

	public void setNotifStatusDescription(String notifStatusDescription)
	{
		this.notifStatusDescription = notifStatusDescription;
	}

	public Calendar getNotifTime()
	{
		return notifTime;
	}

	public void setNotifTime(Calendar notifTime)
	{
		this.notifTime = notifTime;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
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

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	@Override
	public int hashCode()
	{
		return (getUuid() + "$$" + getExternalId() + "$$" + getState()).hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		return hashCode() == o.hashCode();
	}
}
