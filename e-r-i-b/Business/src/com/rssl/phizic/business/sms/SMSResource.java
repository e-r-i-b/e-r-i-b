package com.rssl.phizic.business.sms;

import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * @author  Balovtsev
 * @version 15.03.13 14:27
 *
 * @see SMSConfirmationResource
 * @see SMSRefusingResource
 * @see SMSInformingResource
 */
public abstract class SMSResource extends MessageResource
{
	private ChannelType channel;
	private Boolean     custom;
	private Long        priority; //Приоритет отправки сообщения

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public ChannelType getChannel()
	{
		return channel;
	}

	public void setChannel(ChannelType channel)
	{
		this.channel = channel;
	}

	public void setCustom(Boolean custom)
	{
		this.custom = custom;
	}

	public Boolean getCustom()
	{
		return custom;
	}
}
