package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ������� ��������
 */

public abstract class OperationBase
{
	private Login initiator;

	protected String getInstanceName()
	{
		return "CSAAdmin";
	}

	/**
	 * ������ ��������� ��������
	 * @param initiator ����� ��������� ��������
	 */
	public void setInitiator(Login initiator)
	{
		this.initiator = initiator;
	}

	/**
	 * @return ����� ��������� ��������
	 */
	protected Login getInitiator()
	{
		return initiator;
	}

	/**
	 * ��������� ��������
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public abstract void execute() throws AdminException, AdminLogicException;

	protected <E> E executeAtomic(HibernateAction<E> action) throws AdminException, AdminLogicException
	{
		try
		{
			return HibernateExecutor.getInstance(getInstanceName()).execute(action);
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (AdminLogicException e)
		{
			throw e;
		}
		catch (AdminException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}
}
