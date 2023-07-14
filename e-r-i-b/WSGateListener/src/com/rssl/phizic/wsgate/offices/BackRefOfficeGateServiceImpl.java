package com.rssl.phizic.wsgate.offices;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.wsgate.offices.generated.BackRefOfficeGateService;
import com.rssl.phizic.wsgate.offices.generated.Code;
import com.rssl.phizic.wsgate.offices.generated.Office;
import com.rssl.phizic.wsgate.types.CodeImpl;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Обратный сервис для получения офисов
 * @author niculichev
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BackRefOfficeGateServiceImpl implements BackRefOfficeGateService
{
	private static final DepartmentService departmentService = new DepartmentService();

	public Office getOfficeByCode(Code code_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.wsgate.types.CodeImpl code = new com.rssl.phizic.wsgate.types.CodeImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(code, code_1, TypesCorrelation.types);

			com.rssl.phizic.gate.dictionaries.officies.Office office = departmentService.findByCode(code);
			if(office == null)
				return null;

			com.rssl.phizic.wsgate.offices.generated.Office office1 = new com.rssl.phizic.wsgate.offices.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(office1, office, TypesCorrelation.types);

			return office1;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}

	}

	public Office getOfficeById(String externalId) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.dictionaries.officies.Office office = departmentService.findBySynchKey(externalId);
			if(office == null)
				return null;

			com.rssl.phizic.wsgate.offices.generated.Office office1 = new com.rssl.phizic.wsgate.offices.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(office1, office, TypesCorrelation.types);

			return office1;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
