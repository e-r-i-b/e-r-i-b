package com.rssl.phizic.business.dictionaries.offices.loans;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfficeServiceTest extends BusinessTestCaseBase
{
	private static String TEST_OFFICE_NAME = "TEST OFFICE NAME";
	private static BigDecimal TEST_OFFICE_SYNK_KEY = BigDecimal.TEN;

	private final LoanOfficeService service = new LoanOfficeService();;


	public void testLoanOfficeService() throws Exception
	{
		List<LoanOffice> beforeLoanOffices = service.getAll();
		assertNotNull(beforeLoanOffices);

		LoanOffice office = createTestOffice();
		office = service.add(office);
		List<LoanOffice> afterLoanOffices = service.getAll();
		assertNotNull(afterLoanOffices);
		assertEquals(beforeLoanOffices.size()+1, afterLoanOffices.size());

		LoanOffice byId = service.findSynchKey(office.getSynchKey());
		assertNotNull(byId);
		assertEquals(byId.getName(),office.getName());

		service.remove(office);
		afterLoanOffices = service.getAll();
		assertEquals(beforeLoanOffices.size(), afterLoanOffices.size());
	}

	private LoanOffice createTestOffice()
	{
		LoanOffice office = new LoanOffice();
		office.setName(TEST_OFFICE_NAME);
		office.setSynchKey(TEST_OFFICE_SYNK_KEY);
		return office;
	}
}
