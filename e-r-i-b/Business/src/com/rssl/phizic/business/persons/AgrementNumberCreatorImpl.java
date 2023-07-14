package com.rssl.phizic.business.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;

/**
 * @author Omeliyanchuk
 * @ created 19.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class AgrementNumberCreatorImpl implements AgrementNumberCreator
{
	private static final CounterService counterService = new CounterService();

	public void init(Department department)
	{
	}

	public String getNextAgreementNumber() throws BusinessException
	{
		try
		{
			Long next = counterService.getNext(Counters.AGREEMENT_NUMBER);
			return next.toString();
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}
}
