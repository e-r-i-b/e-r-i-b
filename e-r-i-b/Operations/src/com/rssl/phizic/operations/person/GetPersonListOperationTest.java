package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.MockEmployeeDataProvider;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 21.10.2005
 * Time: 12:59:57
 */
public class GetPersonListOperationTest extends BusinessTestCaseBase
{
    public void testList() throws BusinessException, DataAccessException
    {
	    EmployeeContext.setEmployeeDataProvider(new MockEmployeeDataProvider());

        GetPersonsListOperation personsListOperation = new GetPersonsListOperation();
        personsListOperation.setPerson(new ActivePerson());

        List personsExt = personsListOperation.createQuery("list").executeList();

	    assertNotNull(personsExt);

	    if(personsExt.size() == 0)
            return;

//        Object[] objects = (Object[]) personsExt.get(0);
//        ActivePerson person = (ActivePerson) objects[2];
//        Login login = person.getClientLogin();
//        assertNotNull(login);
    }
}
