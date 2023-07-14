package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-Конвертация PhoneNumber в/из xsd:string
 * Формат номера указывается наследниками
 */
public abstract class AbstractPhoneNumberXmlAdapter extends XmlAdapter<String, PhoneNumber>
{
	private final PhoneNumberFormat phoneNumberFormat;

	///////////////////////////////////////////////////////////////////////////

	protected AbstractPhoneNumberXmlAdapter(PhoneNumberFormat phoneNumberFormat)
	{
		this.phoneNumberFormat = phoneNumberFormat;
	}

	@Override
	public PhoneNumber unmarshal(String phoneNumberAsString)
	{
		return phoneNumberFormat.parse(phoneNumberAsString);
	}

	@Override
	public String marshal(PhoneNumber phoneNumber)
	{
		return phoneNumberFormat.format(phoneNumber);
	}
}
