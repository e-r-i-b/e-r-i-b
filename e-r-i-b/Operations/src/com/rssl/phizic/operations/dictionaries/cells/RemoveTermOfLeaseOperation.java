package com.rssl.phizic.operations.dictionaries.cells;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class RemoveTermOfLeaseOperation extends OperationBase implements RemoveEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private TermOfLease termOfLease;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		termOfLease = simpleService.findById(TermOfLease.class, id);
		if (termOfLease== null){
			throw new BusinessLogicException("Скрок аренды с id" + id + " не найден");
		}
	}


	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
	{
		simpleService.remove(termOfLease);
	}

	public Object getEntity()
	{
		return termOfLease;
	}
}
