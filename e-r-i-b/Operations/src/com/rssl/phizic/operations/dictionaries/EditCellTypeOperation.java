package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class EditCellTypeOperation extends OperationBase
{
	private static final SimpleService simpleService = new SimpleService();

	private CellType cellType;

	public void initialize(Long id) throws BusinessException
	{
		cellType = simpleService.findById(CellType.class, id);

		if ( cellType == null )
			throw new BusinessException("Сейфовая ячейка не найдена: id=" + id);
	}

	public CellType getCellType()
	{
		return cellType;
	}

	public void save() throws BusinessException
	{
		simpleService.update(cellType);
	}
}
