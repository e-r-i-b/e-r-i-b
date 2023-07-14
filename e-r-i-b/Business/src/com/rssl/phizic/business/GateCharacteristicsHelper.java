package com.rssl.phizic.business;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author: Pakhomova
 * @created: 29.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateCharacteristicsHelper
{

	/**
	 * @param office офис
	 * @return поддерживает ли шлюз календари (ритейл)
	 */
	public static boolean isCalendarAvaliable(Office office) throws BusinessException, BusinessLogicException
	{
		try
		{
			GateInfoService service = GateSingleton.getFactory().service(GateInfoService.class);
			return service.isCalendarAvailable(office);
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
}
