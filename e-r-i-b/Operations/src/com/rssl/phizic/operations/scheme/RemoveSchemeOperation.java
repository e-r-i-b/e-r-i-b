package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.schemes.AccessSchemeService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.util.Map;

/**
 * User: Evgrafov
 * Date: 03.10.2005
 * Time: 16:47:17
 */
public class RemoveSchemeOperation extends OperationBase implements RemoveEntityOperation
{
	private SharedAccessScheme scheme;
	private Map<String,String> categories;

	/**
	 * задать категории для схем
	 * @param categories категории для схем
	 */
	public void setCategories(Map<String,String> categories)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.categories = categories;
	}

	private AccessSchemeService getService()
	{
		return GateSingleton.getFactory().service(AccessSchemeService.class);
	}

	public void initialize(Long schemeId) throws BusinessException, BusinessLogicException
	{
		try
		{
			scheme = (SharedAccessScheme) getService().getById(schemeId);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		if (scheme == null)
			throw new BusinessException("Схема доступа id: " + schemeId + " не найдена");
	}

	/**
	 * Удалить схему
	 * @throws BusinessLogicException невозможно удалить схему - на нее есть внешние ссылки, либо эта схема
	 * недоступна сотруднику
	 */
	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
	{
		if (!scheme.canDelete())
			throw new BusinessLogicException("com.rssl.phizic.web.schemes.removeLinks");

		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		if (scheme.isCAAdminScheme() && !employee.isCAAdmin())
			throw new BusinessLogicException("com.rssl.phizic.web.schemes.remove.ca.scheme");

		try
		{
			getService().delete(scheme);
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

	public SharedAccessScheme getEntity()
	{
		return scheme;
	}
}
