package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 * @ author Rtischeva
 * @ created 30.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateWidgetAccessor extends GenericWidgetAccessor
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		return PermissionUtil.impliesServiceRigid("CurrenciesRateInfo");
	}

	protected boolean checkData(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Long departmentId ;
		if (personData == null)
			departmentId = null;
	    else
		{
			Person person = personData.getPerson();
			departmentId = person.getDepartmentId();
		}

		DepartmentService departmentService = new DepartmentService();
		Department department = departmentService.findById(departmentId);
		if (department == null)
				return false;
		try
		{
			GateInfoService service = GateSingleton.getFactory().service(GateInfoService.class);
			return service.isCurrencyRateAvailable(department);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения возможности предоставить информацию о курсах валют", e);
			return false;
		}
	}
}
