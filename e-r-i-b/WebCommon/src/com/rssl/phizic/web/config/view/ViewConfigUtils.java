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
	 * ѕреобразовать строку в ActionType (надо дл€ JAXB)
	 * @param value строка
	 * @return enum
	 */
	public static ActionType parseActionType(String value)
	{
		return ActionType.valueOf(value);
	}

	/**
	 * ѕреобразовать ActionType в строку (надо дл€ JAXB)
	 * @param value enum
	 * @return строка
	 */
	public static String printActionType(ActionType value)
	{
		return value.toString();
	}
}