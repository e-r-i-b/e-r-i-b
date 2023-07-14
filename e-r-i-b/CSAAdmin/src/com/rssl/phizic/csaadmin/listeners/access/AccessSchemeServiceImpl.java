package com.rssl.phizic.csaadmin.listeners.access;

import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.csaadmin.business.access.AccessSchemeService;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.employee.Employee;
import com.rssl.phizic.csaadmin.business.employee.EmployeeService;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.listeners.GateMessageHelper;
import com.rssl.phizic.csaadmin.listeners.generated.AccessSchemeListFilterParametersType;
import com.rssl.phizic.csaadmin.listeners.generated.AccessSchemeType;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */

public class AccessSchemeServiceImpl
{
	private static final EmployeeService employeeService = new EmployeeService();
	private static final AccessSchemeService service = new AccessSchemeService();

	private void update(AccessScheme scheme, AccessSchemeType source)
	{
		scheme.setName(source.getName());
		scheme.setCategory(source.getCategory());
		scheme.setCAAdminScheme(source.isCAAdminScheme());
		scheme.setVSPEmployeeScheme(source.isVSPEmployeeScheme());
		scheme.setMailManagement(source.isMailManagement());
	}

	private AccessSchemeType toGate(AccessScheme scheme)
	{
		return GateMessageHelper.toGate(scheme);
	}

	private AccessSchemeType[] toGate(List<AccessScheme> schemes)
	{
		AccessSchemeType[] accessSchemeTypes = new AccessSchemeType[schemes.size()];
		int i = 0;
		for (AccessScheme accessScheme : schemes)
			accessSchemeTypes[i++] = toGate(accessScheme);
		return accessSchemeTypes;
	}

	/**
	 * @param parameters параметры фильтрации
	 * @param initiatorLogin логин сотрудника
	 * @return список схем прав
	 * @throws AdminException
	 */
	public AccessSchemeType[] getList(AccessSchemeListFilterParametersType parameters, Login initiatorLogin) throws AdminException
	{
		Employee employee = employeeService.findByLogin(initiatorLogin);
		boolean isCaSchemeEnable = employee == null || (employee != null && employee.isCAAdmin());
		List<AccessScheme> schemeList = service.getList(employee != null && employee.isVSPEmployee(), isCaSchemeEnable ,parameters.getCategories());
		return toGate(schemeList);
	}

	/**
	 * @param id идентификатор
	 * @return список схем прав
	 * @throws AdminException
	 */
	public AccessSchemeType getById(long id) throws AdminException
	{
		AccessScheme scheme = service.findById(id);
		return toGate(scheme);
	}

	/**
	 * сохранить схему
	 * @param scheme сохраняемая схема
	 * @return сохраненная схема
	 * @throws AdminException
	 */
	public AccessSchemeType save(AccessSchemeType scheme) throws AdminException
	{
		AccessScheme savingScheme;
		if (scheme.getExternalId() == null)
			savingScheme = new AccessScheme();
		else
			savingScheme = service.findById(scheme.getExternalId());

		update(savingScheme, scheme);
		savingScheme = service.save(savingScheme);
		return toGate(savingScheme);
	}

	/**
	 * удалить схему
	 * @param schema схема
	 * @throws AdminException
	 */
	public void delete(AccessSchemeType schema) throws AdminException, AdminLogicException
	{
		try
		{
			AccessScheme scheme = service.findById(schema.getExternalId());
			service.delete(scheme);
		}
		catch (ConstraintViolationException e)
		{
			throw new AdminLogicException("Невозможно удалить схему прав.", e);
		}
	}
}
