package com.rssl.phizic.messaging.mail.messagemaking.push;

import javax.mail.Address;
import com.rssl.phizic.messaging.mail.messagemaking.AddressBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;

import javax.mail.internet.AddressException;

/**
 * Билдер адресов - заглушка
 * @ author gorshkov
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */
public class NoneAddressBuilder implements AddressBuilder
{
	public Address build(ContactInfo contactInfo) throws AddressException
	{
		return null;
	}
}
