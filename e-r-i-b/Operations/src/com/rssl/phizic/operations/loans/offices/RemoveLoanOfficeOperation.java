package com.rssl.phizic.operations.loans.offices;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeService;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.business.operations.Transactional;


import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RemoveLoanOfficeOperation extends OperationBase implements RemoveEntityOperation
{
	private static final LoanOfficeService service = new LoanOfficeService();

	private LoanOffice office;

	/**
	 * Инициализация
	 * @param id ID офиса для удаления
	 */
	public void initialize(Long id) throws BusinessException
	{
		office = service.findSynchKey(new BigDecimal(id));

		if (office == null)
			throw new BusinessException("Кредитный офис не найден, ID=" + id);
	}

	/**
	 * Удалить офис
	 */
	@Transactional
	public void remove() throws BusinessException
	{
		service.remove(office);
	}

	public LoanOffice getEntity()
	{
		return office;
	}
}
