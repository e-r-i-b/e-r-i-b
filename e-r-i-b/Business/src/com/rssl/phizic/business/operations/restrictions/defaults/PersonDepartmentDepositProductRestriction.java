package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.operations.restrictions.DepositProductRestriction;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import com.rssl.phizic.context.PersonContext;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */
@PublicDefaultCreatable
public class PersonDepartmentDepositProductRestriction implements DepositProductRestriction
{
	public static final DepositProductRestriction INSTANCE = new PersonDepartmentDepositProductRestriction();
	
	public boolean accept(DepositProduct card)
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if (person != null){
			return card.getDepartmentId().equals(person.getDepartmentId());
		}
		return true;
	}
}