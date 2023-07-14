package com.rssl.phizicgate.rsV51.demand;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.rsV51.test.RSRetaileGateTestCaselBase;

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class ClaimIdTest extends  RSRetaileGateTestCaselBase
{
	public ClaimIdTest(String string) throws GateException
	{
		super(string);
	}
	public void test(){
		ClaimId claimId1 = new ClaimId();
		claimId1.setApplicationKey("SDSADSADASDSADSAD");
		claimId1.setApplicationKind(123L);

		ClaimId claimId2 = ClaimId.valueOf(claimId1.toString());
		assertEquals(claimId1,claimId2);
	}
}
