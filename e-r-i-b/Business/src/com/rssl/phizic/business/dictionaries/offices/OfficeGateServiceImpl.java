package com.rssl.phizic.business.dictionaries.offices;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 13.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class OfficeGateServiceImpl extends AbstractService implements OfficeGateService
{
	private static DepartmentService departmentService = new DepartmentService();

	public OfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		try
		{
			return  departmentService.findBySynchKey(id);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{
			List<Office> offices = new ArrayList<Office>();
			offices = HibernateExecutor.getInstance().execute(new HibernateAction<List<Office>>(){
				public List<Office> run(Session session)
				{
					CriteriaImpl criteria =  new CriteriaImpl(Department.class.getName(), (SessionImplementor) session);
					criteria.setFirstResult(firstResult);
					criteria.setMaxResults(maxResults);
					return criteria.list();
				}
			});
			return offices;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(final Office template, final int firstResult, final int listLimit) throws GateException, GateLogicException
	{
		try
		{			
			List<Office> offices = new ArrayList<Office>();
			offices = HibernateExecutor.getInstance().execute(new HibernateAction<List<Office>>(){
				public List<Office> run(Session session) throws Exception
				{
					Map<String, String> fields = template.getCode().getFields();
					DetachedCriteria criteria = departmentService.getFindByBankInfoCriteria(fields);
					return criteria.getExecutableCriteria(session).setFirstResult(firstResult).setMaxResults(listLimit).list();
				}
			});
			return offices;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
