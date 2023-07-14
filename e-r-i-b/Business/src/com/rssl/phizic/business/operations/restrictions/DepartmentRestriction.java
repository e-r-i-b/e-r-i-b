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
	* �������� ����������� ������ � �������������� �� ��� ��������
	* @param departmentId id ������������ �������������
	*/
	boolean accept(Long departmentId) throws BusinessException;
}
