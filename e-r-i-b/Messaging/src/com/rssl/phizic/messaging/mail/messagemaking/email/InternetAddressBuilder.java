package com.rssl.phizic.messaging.mail.messagemaking.email;

import com.rssl.phizic.messaging.mail.messagemaking.AddressBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Evgrafov
 * @ created 03.07.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3813 $
 */

public class InternetAddressBuilder implements AddressBuilder
{
	public InternetAddress build(ContactInfo contactInfo) throws AddressException
	{
		return new InternetAddress(contactInfo.getEmailAddress());
	}
}