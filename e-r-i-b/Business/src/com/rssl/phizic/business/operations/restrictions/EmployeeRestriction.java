package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.dataaccess.query.FilterRestriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;

/**
 * ����������� �� ������ � ������������
 * @author egorova
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */
public interface EmployeeRestriction extends Restriction
{
	/**
	 * �������� ����������� ������ � �����������
	 * @param employee ��������� ��� ��������
	 */
	public boolean accept(Employee employee) throws BusinessException;

}
