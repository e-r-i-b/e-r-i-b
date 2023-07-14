package com.rssl.phizic.business.messaging.info;

/**
 * Подписка.
 *
 * @author Roshka
 * @ created 20.06.2006
 * @ $Author$
 * @ $Revision$
 */

public class SubscriptionImpl implements Subscription
{
	private Long id;
	private Long loginId; //id логина владельца подписки
	private UserNotificationType notificationType;  //тип оповещения
	private NotificationChannel channel; //выбранный клиентом канал для отправки оповещений
	private String tb;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public UserNotificationType getNotificationType()
	{
		return notificationType;
	}

	public void setNotificationType(UserNotificationType notificationType)
	{
		this.notificationType = notificationType;
	}

	public NotificationChannel getChannel()
	{
		return channel;
	}

	public void setChannel(NotificationChannel channel)
	{
		this.channel = channel;
	}

	public String getTB()
	{
		return tb;
	}

	public void setTB(String tb)
	{
		this.tb = tb;
	}
}