package com.rssl.phizic.operations.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.operations.dictionaries.GetMultiDepartmentOfficesOperation;
import com.rssl.phizicgate.manager.GateManager;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Kosyakov
 * @ created 07.08.2006
 * @ $Author: khudyakov $
 * @ $Revision: 30464 $
 */
public class GetSBRFOfficesOperation extends GetMultiDepartmentOfficesOperation
{
	public List<Office> getOffices(Office template, int firstResult, int listLimit) throws BusinessException, BusinessLogicException
	{
		try
		{
			//при получении uuid адаптера делаем проверку на активность внешней системы
			String uuid = ExternalSystemHelper.getCode(adapter.getUUID());

			OfficeGateService service = GateManager.getInstance().getService(uuid, OfficeGateService.class);
			List<Office> offices = service.getAll(template, firstResult, listLimit);

			List<Office> adapters = new ArrayList<Office>();
			for(Office office : offices)
			{
				adapters.add(new SBRFOfficeAdapter(office));
			}

			return adapters;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
