package com.rssl.phizic.operations.loans.offices;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeService;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanOfficeOperation extends OperationBase implements EditEntityOperation
{
	private static final LoanOfficeService service = new LoanOfficeService();

	LoanOffice office;

	public void initialize(String synchKey) throws BusinessException
	{
		office = service.findSynchKey(new BigDecimal(synchKey));

		if(office == null)
			throw new BusinessException("ќфис не найден, ID=" + synchKey);
	}
	public LoanOffice getEntity()
	{
		return office;
	}

	public void save() throws BusinessException
	{
		if(office.getSynchKey() == null)
			service.add(office);
		else
			service.update(office);
	}
}
