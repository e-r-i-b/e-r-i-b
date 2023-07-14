package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Kosyakov
 * @ created 12.12.2005
 * @ $Author: balovtsev $
 * @ $Revision: 67844 $
 */
public class GetPaymentListOperation extends OperationBase
{
	private Long loginId;

    public void initialize() throws BusinessException, BusinessLogicException
    {
        PersonDataProvider provider=PersonContext.getPersonDataProvider();
        ActivePerson person=provider.getPersonData().getPerson();

        loginId=person.getLogin().getId();
    }

    public Long getLoginId ()
    {
        return loginId;
    }
}
