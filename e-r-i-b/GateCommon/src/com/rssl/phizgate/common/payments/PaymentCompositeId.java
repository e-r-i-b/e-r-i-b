package com.rssl.phizgate.common.payments;

import com.rssl.phizic.utils.StringHelper;

/**
 * Класс для формирования композитного ключа
 * @author niculichev
 * @ created 20.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class PaymentCompositeId
{
	private static final String DELEMITER = "@";
	private String messageId;
	private String messageDate;

	public PaymentCompositeId(String externalId)
	{
		this(externalId, 2);
	}

	public PaymentCompositeId(String externalId, int splitLimit)
	{
		String[] components = externalId.split(DELEMITER, splitLimit);
		messageId = components[0];
		if(components.length > 1)
			messageDate = components[1];
	}

	public PaymentCompositeId(String messageId, String messageDate)
	{
		this.messageId = messageId;
		this.messageDate = messageDate;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public String getMessageDate()
	{
		return messageDate;
	}

	public void setMessageDate(String messageDate)
	{
		this.messageDate = messageDate;
	}

	public String toString()
	{
		return StringHelper.getEmptyIfNull(messageId)
				+ DELEMITER
				+ StringHelper.getEmptyIfNull(messageDate);
	}

}
