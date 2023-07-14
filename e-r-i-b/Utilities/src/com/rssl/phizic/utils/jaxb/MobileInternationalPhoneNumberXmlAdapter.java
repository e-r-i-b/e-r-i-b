package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-Конвертация PhoneNumber в/из xsd:string
 * Реализация для номера мобильного телефона в международном формате без знака «+».
 * Например, 79165556677
 */
public class MobileInternationalPhoneNumberXmlAdapter extends AbstractPhoneNumberXmlAdapter
{
	/**
	 * ctor
	 */
	public MobileInternationalPhoneNumberXmlAdapter()
	{
		super(PhoneNumberFormat.MOBILE_INTERANTIONAL);
	}
}
