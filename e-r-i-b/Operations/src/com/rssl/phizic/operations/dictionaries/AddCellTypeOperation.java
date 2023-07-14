package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class AddCellTypeOperation extends OperationBase
{
	private static final SimpleService simpleService = new SimpleService();

	private CellType cellType = new CellType();

	public CellType getNewCellType()
	{
		return cellType;
	}

	public void save() throws BusinessException
	{
		simpleService.add(cellType);
	}
}
