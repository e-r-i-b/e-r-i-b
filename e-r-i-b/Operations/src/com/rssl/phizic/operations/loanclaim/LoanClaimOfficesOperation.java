package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.departments.ListOfficesOperation;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Операция выбора офиса получения кредита в кредитной заявке
 * @author Balovtsev
 * @since 04.02.14
 */
public class LoanClaimOfficesOperation extends ListOfficesOperation
{
	public List<Department> getExtendedLoanClaimOffices(final Map<String, Object> filter) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<Department>>()
		{
			public List<Department> run(Session session) throws Exception
			{
				Collection additionTBs = (Collection) filter.get("additionTBs");
				if (additionTBs == null || additionTBs.size() == 0)
					throw new IllegalArgumentException("Аргумент additionTBs должен быть не пуст!");

				Query query = session.getNamedQuery("com.rssl.phizic.business.departments.DepartmentService.getExtendedLoanClaimClientOffices")
						             .setParameterList("additionTBs", additionTBs)
									 .setParameter("officeInfo", filter.get("officeInfo"));

				return query.list();
			}
		});
	}
}
