package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.csaadmin.business.access.AccessSchemeService;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;


/**
 * @author akrenev
 * @ created 18.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция назначения схемы прав
 */

public class AssignAccessSchemeOperation extends EmployeeOperationBase
{
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();
	private AccessScheme scheme;

	/**
	 * инициализация операции существующим сотрудником и назначаемой схемой прав
	 * @param externalId редактируемый сотрудник
	 * @param schemeId идентификатор схемы прав
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public void initialize(Long externalId, Long schemeId) throws AdminException, AdminLogicException
	{
		initialize(externalId);
		scheme = accessSchemeService.findById(schemeId);
	}

	@Override
	protected void process() throws AdminException, AdminLogicException
	{
		Login login = getEmployee().getLogin();
		login.setAccessScheme(scheme);
		checkAdminCount();
		updateAllowedDepartments();
		updateLogin();
	}

	protected String getConstraintViolationExceptionMessage(String constraintMessage)
	{
		return "Невозможно задать схему прав.";
	}
	/**
	 * @return назначенная схема прав
	 */
	public AccessScheme getScheme()
	{
		return scheme;
	}
}
