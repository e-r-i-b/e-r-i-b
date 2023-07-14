package com.rssl.phizic.operations.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * Операция прочтения списка доступных для перевыпуска карт ВСП.
 *
 * @author bogdanov
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class GetAllowedReissueCardOfficesOperation extends ListOfficesOperation
{
	/**
	 * Инициализация операции
	 */
	public void initialize(String filter) throws BusinessException, BusinessLogicException
	{
		offices = departmentService.getAllowedCreditCardOffices(filter);
	}
}
