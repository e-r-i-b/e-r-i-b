package com.rssl.phizic.common.types.shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Erkin
 * @ created 24.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������� �������������� � ��������-����������
 */
public class ShopConstants
{
	/**
	 * �������� ��� ���� "������������ ���� �����, � ������� ��� ������� ��������", ���� ����� ���������� �� �������
	 */
	public static final String UNKNOWN_CHARGE_OFF_CARD_TYPE = "unknown";

	/**
	 * @return ������������ ������ ����-�������
	 */
	public static DateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}
}
