package com.rssl.phizic.messaging.mail.messagemaking;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author Roshka
 * @ created 08.06.2006
 * @ $Author: gladishev $
 * @ $Revision: 61544 $
 */

public interface MessageBuilder
{
	<T> T create(ContactInfo contactInfo, Object bean) throws MessagingException;

	public Long getPriority();

	public void setPriority(Long priority);
}
