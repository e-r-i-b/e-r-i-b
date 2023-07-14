package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizic.gate.dictionaries.ContactMember;

/**
 * @author Kosyakova
 * @ created 09.01.2007
 * @ $Author$
 * @ $Revision$
 */
@IncludeTest(configurations = "russlav")
public class ReceiverServiceTest extends BusinessTestCaseBase
{
    public void testReceiverService () throws Exception
    {
        ReceiverService service = new ReceiverService();

        final ContactReceiver receiver = new ContactReceiver();

        receiver.setFirstName("Семен");
        receiver.setPatrName("Семенович");
        receiver.setSurName("Семенов");
        receiver.setAddInfo("info");
	    ContactMember bank = new ContactMember();
	    bank.setCode("MRSB");
	    bank.setName("Тестовый");
	    bank.setPhone("123");
	    receiver.setBank(bank);

        service.add(receiver);

        ContactReceiver byId = service.findById(receiver.getId());
        assertNotNull(byId);
        service.remove(byId);
    }
}