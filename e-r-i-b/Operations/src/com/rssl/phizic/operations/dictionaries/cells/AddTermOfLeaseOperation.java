package com.rssl.phizic.operations.dictionaries.cells;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class AddTermOfLeaseOperation extends OperationBase
{
	private static final SimpleService simpleService = new SimpleService();

	private TermOfLease termOfLease = new TermOfLease();

	public TermOfLease getTermOfLease()
	{
		return termOfLease;
	}

	public void save() throws BusinessException
	{
		simpleService.add(termOfLease);
	}
}
