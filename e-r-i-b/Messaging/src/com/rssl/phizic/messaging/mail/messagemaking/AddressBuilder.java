package com.rssl.phizic.messaging.mail.messagemaking;

import javax.mail.Address;
import javax.mail.internet.AddressException;

/**
 * @author Evgrafov
 * @ created 03.07.2006
 * @ $Author: kosyakov $
 * @ $Revision: 3224 $
 */

public interface AddressBuilder
{
	Address build(ContactInfo contactInfo) throws AddressException;
}
