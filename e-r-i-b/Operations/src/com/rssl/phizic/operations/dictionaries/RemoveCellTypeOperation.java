package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class RemoveCellTypeOperation extends OperationBase implements RemoveEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();

	private CellType cellType;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		cellType = simpleService.findById(CellType.class, id);

		if (cellType == null)
			throw new BusinessLogicException("“ип €чейки с id " + id + " не найден!");
	}

	public CellType getEntity()
	{
		return cellType;
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		simpleService.remove(cellType);
	}
}
