package com.rssl.phizic.operations.moneyBox;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 15.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Отсортировка списка копилок по следующему порядку:
	1. Новый
	2. На регистрации
	3. Прошел регистрацию
	4. Ожидает подтверждения клиента
	5. Активный
	6. Приостановлен
	7. Не зарегистрирован
	8. Закрыт
    9. Ожидание активации
 */
public class EmployeeListMoneyBoxComparator implements Comparator<AutoSubscriptionLink>
{
	private static final Map<AutoPayStatusType, Integer> compareMap = new HashMap<AutoPayStatusType, Integer>(AutoPayStatusType.values().length);

	static
	{
		compareMap.put(AutoPayStatusType.New, 0);
		compareMap.put(AutoPayStatusType.OnRegistration, 1);
		compareMap.put(AutoPayStatusType.Confirmed, 2);
		compareMap.put(AutoPayStatusType.WaitForAccept, 3);
		compareMap.put(AutoPayStatusType.Active, 4);
		compareMap.put(AutoPayStatusType.Empty, 5);
		compareMap.put(AutoPayStatusType.Paused, 6);
		compareMap.put(AutoPayStatusType.ErrorRegistration, 7);
		compareMap.put(AutoPayStatusType.Closed, 8);
		compareMap.put(AutoPayStatusType.WaitingForActivation, 9);
	}

	public int compare(AutoSubscriptionLink first, AutoSubscriptionLink second)
	{
		AutoPayStatusType firstState = first.getAutoPayStatusType();
		AutoPayStatusType secondState = second.getAutoPayStatusType();

		Integer weight1 = compareMap.get(AutoPayStatusType.getEmptyIfNull(firstState));
		Integer weight2 = compareMap.get(AutoPayStatusType.getEmptyIfNull(secondState));

		Integer compareResult = weight1.compareTo(weight2);
		//Если копилки в одном статусе, сортируем по имени
		if (compareResult.equals(0))
		{
			return first.getFriendlyName().compareTo(second.getFriendlyName());
		}

		return compareResult;
	}
}
