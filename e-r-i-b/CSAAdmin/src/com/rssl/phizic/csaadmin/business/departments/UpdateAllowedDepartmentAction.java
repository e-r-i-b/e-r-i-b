package com.rssl.phizic.csaadmin.business.departments;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * эекшен обновлени€ доступа к подразделени€м
 */

class UpdateAllowedDepartmentAction implements HibernateAction<Void>
{
	private static final String REMOVE_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.remove";
    private static final String CHECK_TB_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.checkTb";
    private static final String CHECK_OSB_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.checkOsb";
    private static final String CHECK_VSP_QUERY_NAME = "com.rssl.phizic.csaadmin.business.departments.queries.checkVsp";
	private static final String KEY_DELIMITER = "|";

	private final Login login;
	private final List<Department> addDepartments;
	private final List<Department> deleteDepartments;

	UpdateAllowedDepartmentAction(Login login, List<Department> addDepartments, List<Department> deleteDepartments)
	{
		this.login = login;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.addDepartments = addDepartments;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.deleteDepartments = deleteDepartments;
	}

	public Void run(Session session) throws Exception
	{
		addAllowed(session, addDepartments, login);
		removeAllowed(session, deleteDepartments, login);
        session.flush();
		checkValid(session, login);
		updateLogin(session,login);
		return null;
	}

	private void addAllowed(Session session, List<Department> source, final Login login) throws AdminException, AdminLogicException
	{
		if (CollectionUtils.isEmpty(source))
			return;
		for (Department department : source)
			session.save(new AllowedDepartment(login, department));
	}

	private List<String> getAllowedDepartments(List<Department> allowedDepartments)
	{
		List<String> result = new ArrayList<String>(allowedDepartments.size());
		for (Department allowedDepartment : allowedDepartments)
			result.add(allowedDepartment.getTb().concat(KEY_DELIMITER).concat(allowedDepartment.getOsb()).concat(KEY_DELIMITER).concat(allowedDepartment.getVsp()));
		return result;
	}

	private void removeAllowed(Session session, List<Department> source, final Login login) throws AdminException
	{
		if (CollectionUtils.isEmpty(source))
			return;

		Query query = session.getNamedQuery(REMOVE_QUERY_NAME);
		query.setParameter("login", login);
		query.setParameterList("allowedDepartments", getAllowedDepartments(source));
		query.executeUpdate();
	}

    private void check(final Login login, Session session, String queryName) throws AdminLogicException
    {
        Query query = session.getNamedQuery(queryName);
        query.setParameter("loginId",login.getId());
        Integer count = (Integer)query.uniqueResult();
        if(count!=null && count!=0)
            throw new AdminLogicException("¬ы не можете добавить доступ к дочернему подразделению, если уже доступно родительское.");
    }

    /**
     * ѕровер€ет неизбыточность списка доступных департаментов
     * @param login - логин сотрудника
     * @throws AdminLogicException список избыточен
     */
	private void checkValid(Session session, Login login) throws AdminLogicException
	{
        check(login,session,CHECK_TB_QUERY_NAME);
        check(login,session,CHECK_OSB_QUERY_NAME);
        check(login,session,CHECK_VSP_QUERY_NAME);
	}

	private void updateLogin(Session session, Login login)
	{
		login.setLastUpdateDate(Calendar.getInstance());
		session.update(login);
	}
}
