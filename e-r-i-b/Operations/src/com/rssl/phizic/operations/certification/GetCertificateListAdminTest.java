package com.rssl.phizic.operations.certification;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.security.certification.CertOwn;
import com.rssl.phizic.dataaccess.query.Query;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 26.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class GetCertificateListAdminTest extends BusinessTestCaseBase
{
	private static String TEST_STATUS = "E";

	public void testGetList() throws Exception
	{
		GetCertificateListAdminOperation operation = new GetCertificateListAdminOperation();
		Query query = operation.createQuery("list");
		query.setParameter("status",TEST_STATUS);

		List<CertOwn> list = query.executeList();
		int sz = list.size();
		for (CertOwn certOwn : list)
		{
			assertTrue( certOwn.getStatus().equals(TEST_STATUS));
		}
	}
}
