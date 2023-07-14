package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Evgrafov
 * @ created 14.09.2006
 * @ $Author: krenev_a $
 * @ $Revision: 55355 $
 */

public class ForceChangeEmployeePasswordOperation extends EditEmployeeOperation
{
	public void setNewPassword(String newPassword)
	{
		getEntity().getLogin().setPassword(newPassword);
	}

	/**
	 * изменить пароль сотрудника
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void change() throws BusinessException, BusinessLogicException
	{
		try
		{
			getService().changePassword(getEntity().getLogin());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}