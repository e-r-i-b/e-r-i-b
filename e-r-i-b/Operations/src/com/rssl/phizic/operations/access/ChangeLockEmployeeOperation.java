package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.LoginBlockWrapper;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.operations.employees.EditEmployeeOperation;
import com.rssl.phizic.utils.DateHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Kidyaev
 * @ created 20.01.2006
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLockEmployeeOperation extends EditEmployeeOperation
{
	private List<LoginBlock> blocks;

	/**
	 * ���������� ������ ����������
	 * @param reason �������
	 * @param blockedFrom ������ ����������
	 * @param blockedUntil ��������� ����������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void lock(String reason, Date blockedFrom, Date blockedUntil) throws BusinessLogicException, BusinessException
	{
		Employee lockerEmployee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		try
		{
			LoginBlockWrapper block = new LoginBlockWrapper();
			block.setBlockedFrom(DateHelper.toCalendar(blockedFrom));
			block.setBlockedUntil(DateHelper.toCalendar(blockedUntil));
			block.setReasonDescription(reason);
			block.setReasonType(BlockingReasonType.employee.name());
			getService().lock(getEntity().getLogin(), block);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������������� ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void unlock() throws BusinessException, BusinessLogicException
	{
		try
		{
			blocks = Collections.emptyList();
			getService().unlock(getEntity().getLogin());
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

	/**
	 * @return ������ ����������
	 */
	public List<LoginBlock> getBlocks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return blocks;
	}
}
