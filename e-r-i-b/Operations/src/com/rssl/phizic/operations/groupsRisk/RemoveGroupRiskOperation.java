package com.rssl.phizic.operations.groupsRisk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author basharin
 * @ created 01.08.2012
 * @ $Author$
 * @ $Revision$
 * удаление групп риска
 */

public class RemoveGroupRiskOperation extends RemoveDictionaryEntityOperationBase<GroupRisk, DepartmentRestriction>
{
	private static final String GROUP_RISK_HAVE_LIMITS = "Вы не можете удалить группу риска, у которой есть действующий лимит в следующих ТБ: %s. Удалите лимиты для выбранной группы и повторите операцию.";

	private static MultiInstanceGroupRiskService groupRiskService = new MultiInstanceGroupRiskService();
	private static LimitService limitService = new LimitService();
	private static DepartmentService departmentService = new DepartmentService();

	private GroupRisk groupRisk;

	public void initialize(Long id) throws BusinessException
	{
		groupRisk = groupRiskService.findById(id, getInstanceName());
		if (groupRisk == null)
		{
			throw new ResourceNotFoundBusinessException("Не найдена группа риска с указаным id", GroupRisk.class);
		}
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		// проверка что нет лимита в статусе действует
		List<Limit> limits = limitService.findLimits(null, LimitType.GROUP_RISK, null, groupRisk, Status.ACTIVE, null, getInstanceName());

		if (CollectionUtils.isEmpty(limits))
		{
			removeGroupRisk();
			return;
		}
		// формируем сообщение с перечислением ТБ, в которых есть лимиты по данной группе риска
		StringBuilder builder = new StringBuilder();
		List<String> tbs = new ArrayList<String>();
		for (Limit limit: limits)
		{
			if (!tbs.contains(limit.getTb()))
			{
				builder.append(departmentService.getDepartment(limit.getTb(), null, null, getInstanceName()).getName()).append(",");
				tbs.add(limit.getTb());
			}
		}
		builder.deleteCharAt(builder.length()-1);

		throw new BusinessLogicException(String.format(GROUP_RISK_HAVE_LIMITS, builder.toString()));
	}

	private void removeGroupRisk() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					// удаляем из поставщиков услуг эту группу риска
					removeGroupRiskFromServiceProvider(groupRisk.getId());

					// удаляем связи с операциями
					removeGroupRiskFromLimitPaymentLinks(groupRisk.getId());

					// удаляем группу риска
					groupRiskService.remove(groupRisk, getInstanceName());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void removeGroupRiskFromLimitPaymentLinks(final Long groupRiskId) throws BusinessException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		final Department tb = departmentService.getTB(employee.getDepartmentId());
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.groupsRisk.RemoveGroupRiskOperation.updateLimitPaymentsLinks");
					query.setParameter("tb", tb.getRegion());
					query.setParameter("groupRiskId", groupRiskId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void removeGroupRiskFromServiceProvider(final Long groupRiskId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
			        Query query = session.getNamedQuery("com.rssl.phizic.operations.groupsRisk.RemoveGroupRiskOperation.updateServiceProvider");
					query.setLong("groupRiskId", groupRiskId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public GroupRisk getEntity()
	{
		return groupRisk;
	}

	private boolean isNotMultiBlockMode()
	{
		return !ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode();
	}
}

