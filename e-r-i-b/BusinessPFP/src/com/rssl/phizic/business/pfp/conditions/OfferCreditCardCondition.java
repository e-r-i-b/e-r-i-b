package com.rssl.phizic.business.pfp.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.person.Person;

/**
 * ѕредложить кредитную карту клиенту
 * @author komarov
 * @ created 08.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class OfferCreditCardCondition extends PfpConditionBase
{
	private static final PersonService service = new PersonService();

	public boolean accepted(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		Person person = service.findByLogin(profile.getOwner());
		return SegmentHelper.getSegmentCodeType(person.getSegmentCodeType()) != SegmentCodeType.VIP;
	}
}
