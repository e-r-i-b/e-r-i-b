package com.rssl.phizic.operations.dictionaries.contact;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.contact.ContactMember;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.List;

/**
 * @author Kosyakova
 * @ created 15.11.2006
 * @ $Author$
 * @ $Revision$
 */
@IncludeTest(configurations = "russlav")
public class GetMemberListOperationTest extends BusinessTestCaseBase
{
	private Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void testMemberListOperation() throws BusinessException, DataAccessException
	{
		GetMemberListOperation operation = new GetMemberListOperation();

		List<ContactMember> list = operation.createQuery("list").executeList();

		log.info("Записей в списке точек: " + list.size());
	}
}

