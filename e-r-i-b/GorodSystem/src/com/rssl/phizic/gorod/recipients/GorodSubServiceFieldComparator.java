package com.rssl.phizic.gorod.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gorod.messaging.Constants;

import java.util.Comparator;

/**
 * @author osminin
 * @ created 14.06.2011
 * @ $Author$
 * @ $Revision$
 * Компаратор для упорядочивания полей подуслуг по идентификатору подуслуги, поля не подуслуг складываются вверх списка
 * упорядочиваемые между друг другом по номеру
 */
public class GorodSubServiceFieldComparator implements Comparator
{
	private static final int SS_ID_LENGTH = 14;

	public int compare(Object o1, Object o2)
	{
		//Получаем идентификаторы полей
		String externalId1 = ((CommonField) o1).getExternalId();
		String externalId2 = ((CommonField) o2).getExternalId();
		if (externalId1.startsWith(Constants.SS_PREFIX) && externalId2.startsWith(Constants.SS_PREFIX))
		{
			//получаем идентификаторы подуслуг из идентификаторов полей
			String[] array1 = externalId1.split(Constants.SS_DELIMITER);
			String[] array2 = externalId2.split(Constants.SS_DELIMITER);
			int num1 = Integer.decode(array1[0].substring(SS_ID_LENGTH, array1[0].length()));
			int num2 = Integer.decode(array2[0].substring(SS_ID_LENGTH, array2[0].length()));
			return num1 - num2;
		}
		else if (externalId1.startsWith(Constants.SS_PREFIX))
			return -1;
		else if (externalId2.startsWith(Constants.SS_PREFIX))
			return 1;

		return ((CommonField) o1).getNum() - ((CommonField) o2).getNum();
	}
}
