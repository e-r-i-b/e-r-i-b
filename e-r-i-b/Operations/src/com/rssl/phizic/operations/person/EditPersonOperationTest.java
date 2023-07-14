package com.rssl.phizic.operations.person;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.persons.xmlserialize.CODRequestHelper;
import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: Kosyakova
 * Date: 11.09.2006
 * Time: 15:20:35
 * To change this template use File | Settings | File Templates.
 */
public class EditPersonOperationTest extends BusinessTestCaseBase
{
    public void test() throws Exception {
      ActivePerson testPerson = PersonServiceTest.getTestPerson();
      EditPersonOperation operation = new EditPersonOperation();
      operation.setPerson(testPerson);
      Document document = CODRequestHelper.buildClientUpdateRequest(testPerson, null);
      assertNotNull(document);      
    }
}
