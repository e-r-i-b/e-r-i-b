package com.rssl.phizicgate.esberibgate.types.wrappers;

import com.rssl.phizicgate.esberibgate.ws.generated.MessageDeliveryType_Type;

/**
 * @author akrenev
 * @ created 24.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� �������� �� ������ �� �����
 */

public class MessageDeliveryTypeWrapper
{
	/**
	 * �������� ���� ������������� �� ������������ ������� �������
	 * @param value ������������ ������� �������
	 * @return ���� �������������
	 */
	public static boolean fromGate(MessageDeliveryType_Type value)
	{
		if (value == null)
			return false;
		return MessageDeliveryType_Type.E.equals(value);
	}
}
