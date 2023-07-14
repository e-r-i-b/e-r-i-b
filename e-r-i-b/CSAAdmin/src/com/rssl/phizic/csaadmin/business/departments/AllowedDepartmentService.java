package com.rssl.phizic.csaadmin.business.departments;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с доступными подразделениями
 */

public class AllowedDepartmentService extends SimpleService<AllowedDepartment>
{
	private static final String LIST_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.list";
	private static final String REMOVE_ALL_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.removeAll";
	private static final String IS_ALL_TB_ACCESS_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.isAllTBAccess";

	@Override
	protected Class<AllowedDepartment> getEntityClass()
	{
		return AllowedDepartment.class;
	}

	/**
	 * поиск доступных подразделений по логину
	 * @param login логин
	 * @return список доступных подразделений
	 * @throws AdminException
	 */
	public List<Department> getAllowedByLogin(Login login) throws AdminException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getExecutor(), LIST_QUERY_NAME);
			query.setParameter("loginId", login.getId());
			return query.executeListInternal();
		}
		catch (DataAccessException e)
		{
			throw new AdminException(e);
		}
	}

	/**
	 * сохраниение доступных подразделений
	 * @param addDepartments новые департаменты
	 * @param deleteDepartments удаляемые департаменты
	 * @param login логин сотрудника, для которого ищем подразделения
	 * @throws AdminException
	 */
	public void updateAllowed(List<Department> addDepartments, List<Department> deleteDepartments, Login login) throws AdminException, AdminLogicException
	{
		try
		{
			execute(new UpdateAllowedDepartmentAction(login, addDepartments, deleteDepartments));
		}
		catch (ConstraintViolationException ignore)
		{
			throw new AdminLogicException("Невозможно обновить доступ к подразделениям.");
		}
	}

	/**
	 * для destinationLogin открыть доступ к подразделениям, доступным sourceLogin
	 * @param destinationLogin логин которому открываем доступ
	 * @param sourceLogin логин с которого копируем доступ
	 */
	public void merge(final Login destinationLogin, final Login sourceLogin) throws AdminLogicException, AdminException
	{
		updateAllowed(getAllowedByLogin(sourceLogin), Collections.<Department>emptyList(), destinationLogin);
	}

	/**
	 * удалить все доступные подразделения
	 * @param login логин для которого удаляем
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public void deleteAllAllowed(final Login login) throws AdminException, AdminLogicException
	{
		try
		{
		    execute(new HibernateAction<Void>()
		    {
			    public Void run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery(REMOVE_ALL_QUERY_NAME);
				    query.setParameter("login", login);
				    query.executeUpdate();
				    return null;
			    }
		    });
		}
		catch (ConstraintViolationException ignore)
		{
			throw new AdminLogicException("Невозможно обновить доступ к подразделениям.");
		}
	}

	/**
	 * @param loginId идентификатор логина сотрудника
	 * @return true - у сотрудника есть доступ ко всем ТВ
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public boolean isAllTbAccess(final Long loginId) throws AdminException, AdminLogicException
	{
		return execute(new HibernateAction<Boolean>()
	    {
		    public Boolean run(Session session) throws Exception
		    {
			    Query query = session.getNamedQuery(IS_ALL_TB_ACCESS_QUERY_NAME);
			    query.setParameter("loginId", loginId);
			    return ((Long)query.uniqueResult() > 0);
		    }
	    });
	}
}
