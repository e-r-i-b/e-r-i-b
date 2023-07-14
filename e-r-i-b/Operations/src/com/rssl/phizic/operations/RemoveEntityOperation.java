package com.rssl.phizic.operations;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Krenev
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
public interface RemoveEntityOperation<E, R extends Restriction>  extends Operation<R>
{
	/**
	 * ������������� ��������
	 * @param id ������������� �������� ��� ��������.
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException;

	/**
	 *  ������� ��������.
	 */
	public void remove() throws BusinessException, BusinessLogicException;

	/**
	 * @return ���������/��������� ��������.
	 */
	public E getEntity();
}
