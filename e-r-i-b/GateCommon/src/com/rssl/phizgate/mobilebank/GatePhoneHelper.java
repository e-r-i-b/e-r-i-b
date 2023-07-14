package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class GatePhoneHelper
{
	/**
	 * ���������� ����� �������� � ������������� ����
	 * @param phoneNumber - ����� ��������
	 * @return ����� �������� � ������������� ����
	 */
	public static String hidePhoneNumber(String phoneNumber)
	{
		if (StringHelper.isEmpty(phoneNumber))
			return "empty";

		try
		{
			return PhoneNumberFormat.IKFL.translateAsHidden(phoneNumber);
		}
		catch (NumberFormatException ignored)
		{
			return phoneNumber;
		}
	}
}
