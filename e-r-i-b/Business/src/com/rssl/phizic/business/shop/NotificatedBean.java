package com.rssl.phizic.business.shop;

import java.util.Date;

/**
 * ��� � ������ ����������
 * @author gladishev
 * @ created 19.06.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class NotificatedBean
{
	private OrderStatus notificationStatus;         //������ ����������
	private Date notificationTime;                  //����� �������� �����������(����������)
	private Long notificationCount;                 // ���������� ������������ �����������
	private String notificationStatusDescription;   // �������� �������

	public OrderStatus getNotificationStatus()
	{
		return notificationStatus;
	}

	public void setNotificationStatus(OrderStatus notificationStatus)
	{
		this.notificationStatus = notificationStatus;
	}

	public Date getNotificationTime()
	{
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime)
	{
		this.notificationTime = notificationTime;
	}

	public Long getNotificationCount()
	{
		return notificationCount;
	}

	public void setNotificationCount(Long notificationCount)
	{
		this.notificationCount = notificationCount;
	}

	public void incrementNotificationCount()
	{
		this.notificationCount++;
	}

	public String getNotificationStatusDescription()
	{
		return notificationStatusDescription;
	}

	public void setNotificationStatusDescription(String notificationStatusDescription)
	{
		this.notificationStatusDescription = notificationStatusDescription;
	}
}
