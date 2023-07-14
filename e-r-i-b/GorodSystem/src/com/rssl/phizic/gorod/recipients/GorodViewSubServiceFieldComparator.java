package com.rssl.phizic.gorod.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gorod.messaging.Constants;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author osminin
 * @ created 20.06.2011
 * @ $Author$
 * @ $Revision$
 * Компаратор для приведения допполей сложной услуги Города к виду:
 * 1. поле название подуслуги
 * 2. поле задолженности подуслуги
 * 3. поле суммы подуслуги
 * 4... в зависимости от собственного порядка остальных полей
 */
public class GorodViewSubServiceFieldComparator implements Comparator
{
	private static final int SUB_SERVICE_LENGTH = 11; //длина строки SubService- из идентификатора подуслуги

	public int compare(Object o1, Object o2)
	{
		//получаем имена групп полей
		String groupName1 = ((CommonField) o1).getGroupName();
		String groupName2 = ((CommonField) o2).getGroupName();

		//если поля не принадлежат группам, то упорядачиваем их по номеру
		if (StringHelper.isEmpty(groupName1) && StringHelper.isEmpty(groupName2))
			return ((CommonField) o1).getNum() - ((CommonField) o2).getNum();
		//поля без группы показываем выше полей подуслуг
		else if (StringHelper.isEmpty(groupName1))
			return -1;
		//поля без группы показываем выше полей подуслуг
		else if (StringHelper.isEmpty(groupName2))
			return 1;
		else if (groupName1.equals(groupName2))
			return getSSFieldNum(o1) - getSSFieldNum(o2);

		//поля разных подуслуг упорядочиваем по номеру подуслуг, который хранится в идентификаторе
		int subServiceId1 = Integer.decode(groupName1.substring(SUB_SERVICE_LENGTH, groupName1.length()));
		int subServiceId2 = Integer.decode(groupName2.substring(SUB_SERVICE_LENGTH, groupName2.length()));
		return subServiceId1 - subServiceId2;
	}

	/**
	 * получить номер поля подуслуги, устанавливается в зависимости от внешнего идентификатора,
	 * что бы всегда упорядочивалось в одинаковом порядке
	 * @param o объект
	 * @return порядковый номер поля
	 */
	private int getSSFieldNum(Object o)
	{
		//получаем идентификатор поля
		String externalId = ((CommonField) o).getExternalId();
		//первым выводится имя подуслуги
		if (externalId.startsWith(Constants.SS_NAME_PREFIX))
			return 0;
		//второй выводится задолженность
		else if (externalId.endsWith(Constants.DEBT_FIELD_NAME))
			return 1;
		//третьей выводится сумма
		else if (externalId.startsWith(Constants.SS_AMOUNT_PREFIX))
			return 2;
		//остальные поля выводятся ниже с учетом собственного порядкового номера
		return 3 + ((CommonField) o).getNum();
	}
}
