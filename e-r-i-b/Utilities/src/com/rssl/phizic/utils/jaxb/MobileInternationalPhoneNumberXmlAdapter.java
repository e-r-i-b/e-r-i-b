package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-����������� PhoneNumber �/�� xsd:string
 * ���������� ��� ������ ���������� �������� � ������������� ������� ��� ����� �+�.
 * ��������, 79165556677
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
