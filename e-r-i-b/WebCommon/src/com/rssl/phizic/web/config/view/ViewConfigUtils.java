package com.rssl.phizic.web.config.view;

/**
 * @author Evgrafov
 * @ created 10.08.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */

public class ViewConfigUtils
{
	/**
	 * ������������� ������ � ActionType (���� ��� JAXB)
	 * @param value ������
	 * @return enum
	 */
	public static ActionType parseActionType(String value)
	{
		return ActionType.valueOf(value);
	}

	/**
	 * ������������� ActionType � ������ (���� ��� JAXB)
	 * @param value enum
	 * @return ������
	 */
	public static String printActionType(ActionType value)
	{
		return value.toString();
	}
}