package com.rssl.phizic.csaadmin.listeners.mail.comparators;

import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.gate.mail.OutcomeMailListEntity;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор исходящих писем.
 */
public class OutcomeMailListEntityComparator extends MultiNodeEntityComparator<OutcomeMailListEntity>
{
	@Override
	protected int compare(OutcomeMailListEntity entity1, OutcomeMailListEntity entity2, OrderParameter orderParameter)
	{
		int compareResult = 0;
		String compareParameter = orderParameter.getValue();
		if("mNum".equals(compareParameter))
			compareResult = compare(entity1.getNumber(),entity2.getNumber());
		else if("mCreationDate".equals(compareParameter))
			compareResult = compare(entity1.getCreationDate(),entity2.getCreationDate());
		else if("mailStateDescription".equals(compareParameter))
			compareResult = compare(entity1.getStateDescription(),entity2.getStateDescription());
		else if("mailTypeDescription".equals(compareParameter))
			compareResult = compare(entity1.getTypeDescription(),entity2.getTypeDescription());
		else if("msDescription".equals(compareParameter))
			compareResult = compare(entity1.getTheme(),entity2.getTheme());
		else if("mSubject".equals(compareParameter))
			compareResult = compare(entity1.getSubject(),entity2.getSubject());
		else if("User_FIO".equals(compareParameter))
			compareResult = compare(entity1.getRecipientFIO(),entity2.getRecipientFIO());
		else if("Employee_FIO".equals(compareParameter))
			compareResult = compare(entity1.getEmployeeFIO(),entity2.getEmployeeFIO());
		else if("lUserId".equals(compareParameter))
			compareResult = compare(entity1.getEmployeeUserId(),entity2.getEmployeeUserId());
		else if("mResponseMethodDescription".equals(compareParameter))
			compareResult = compare(entity1.getResponseMethod(),entity2.getResponseMethod());
		else
			throw new IllegalArgumentException("Некорректный параметр сортировки");

		return compareResult * orderParameter.getDirection().getSign();
	}
}
