package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;

/**
 * @author egorova
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */
public interface DepartmentRestriction extends Restriction
{
	/**
	* Проверка возможности работы с подразделением по его родителю
	* @param departmentId id родительское подразделение
	*/
	boolean accept(Long departmentId) throws BusinessException;
}
