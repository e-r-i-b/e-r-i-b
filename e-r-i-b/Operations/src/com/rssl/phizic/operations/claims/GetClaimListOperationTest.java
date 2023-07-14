package com.rssl.phizic.operations.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.DefaultClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ClientBusinessDocumentOwner;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.RusslavTestClientCreator;
import com.rssl.phizic.utils.test.annotation.IncludeTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 12.12.2006
 * @ $Author$
 * @ $Revision$
 */
@IncludeTest(configurations = "russlav")
public class GetClaimListOperationTest extends BusinessTestCaseBase
{
	private static final SimpleService simpleService = new SimpleService();

	private BusinessDocument claim;

	protected void setUp() throws Exception
	{
		super.setUp();
		this.initializeRsV51Gate();

		ActivePerson testPerson = RusslavTestClientCreator.getTestPerson();

		claim = new DefaultClaim();
		claim.setOwner(new ClientBusinessDocumentOwner(testPerson));

		simpleService.add(claim);
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();

		if (claim != null && claim.getId() != null)
			simpleService.remove(claim);
	}

	/**
	 * Получить список заявок
	 * @throws BusinessException
	 * @throws DataAccessException
	 */
	public void testGetClaimListOperation() throws BusinessException, DataAccessException
	{
		GetClaimListOperation operation = new GetClaimListOperation();
		Query query = operation.createQuery("list");
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("number", null);
		parameters.put("fromDate", null);
		parameters.put("toDate", null);
		parameters.put("type", "");
		parameters.put("state", "");

		query.setParameters(parameters);
		List documents = query.executeList();
		assertTrue(documents.size() > 0);
	}
}
