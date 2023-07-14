package com.rssl.phizic.business.dictionaries.offices;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * @author niculichev
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BackRefOfficeGateServiceImpl extends AbstractService implements BackRefOfficeGateService
{
	private static final DepartmentService departmentService = new DepartmentService();

	public BackRefOfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		try
		{
			return departmentService.findBySynchKey(id);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public Office getOfficeByCode(Code code) throws GateException, GateLogicException
	{
		try
		{
			return departmentService.findByCode(code);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
