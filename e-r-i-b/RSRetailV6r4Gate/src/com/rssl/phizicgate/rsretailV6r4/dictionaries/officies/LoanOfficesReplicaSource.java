package com.rssl.phizicgate.rsretailV6r4.dictionaries.officies;

import com.rssl.phizicgate.rsretailV6r4.dictionaries.NamedQueryReplicaSource;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Krenev
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfficesReplicaSource extends NamedQueryReplicaSource
{
	public LoanOfficesReplicaSource()
	{
		super("com.rssl.phizicgate.rsretailV6r4.dictionaries.officies.GetLoanOfficies");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
