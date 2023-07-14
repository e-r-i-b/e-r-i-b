package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;

/**
 * @author Omeliyanchuk
 * @ created 31.01.2008
 * @ $Author$
 * @ $Revision$
 */

public abstract class PersonDocumentSourceBase
{
	protected static final DepartmentService departmentService = new DepartmentService();

	protected Department getDepartment() throws DocumentException
	{
		try
		{
			if (isAnonymous())
			{
				for (Department department : departmentService.getAll())
				{
					if (department.getMain())
					{
						return department; //все анонимы идут в главный офис по версии ИКФЛ
					}
				}
				return departmentService.getAll().get(0);//TODO не забыть записать в лог
			}
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			PersonData personData = provider.getPersonData();
			return departmentService.findById(personData.getPerson().getDepartmentId());
		}
		catch(BusinessException ex)
		{
			throw new DocumentException("Не найдено подразделение пользователя", ex);
		}
	}

	protected boolean isAnonymous()
	{
		return AuthModule.getAuthModule().getPrincipal().getAccessType().equals(AccessType.anonymous);
	}
}
