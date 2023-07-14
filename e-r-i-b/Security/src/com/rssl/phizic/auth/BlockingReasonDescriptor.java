package com.rssl.phizic.auth;

/**
 * @author Egorova
 * @ created 25.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class BlockingReasonDescriptor
{
//TODO: вытаскивать соответствующие описание причин из properties
	public static String getReasonText(BlockingReasonType reasonType)
	{
		switch (reasonType)
		{
			case longInactivity:
			case employee:      return "Заблокировано администратором";
			case system:        return "Системная блокировка";
			case wrongLogons:   return "Неправильный ввод пароля";

			default: return "Причина блокировки неизвестна";
		}

	}
}
