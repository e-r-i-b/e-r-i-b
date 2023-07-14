package com.rssl.phizic.business.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.log.LogService;

/**
 * @author Dorzhinov
 * @ created 03.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class LogServiceImpl implements LogService
{
	private GateFactory factory;
	private static DepartmentService departmentService = new DepartmentService();

	public LogServiceImpl(GateFactory factory)
	{
		this.factory = factory;
	}

	public GateFactory getFactory()
	{
		return factory;
	}

	public Long getDepartmentIdByCode(String tb, String osb, String vsp) throws GateException, GateLogicException
	{
		Department department = getDepartment(tb, osb, vsp);
		return department == null ? null : department.getId();
	}

	public String getDepartmentNameByCode(String tb, String osb, String vsp) throws GateException, GateLogicException
	{
		Department department = getDepartment(tb, osb, vsp);
		return department == null ? null : department.getName();
	}

	private Department getDepartment(String tb, String osb, String vsp) throws GateException
	{
		try
		{
			Code code = new ExtendedCodeImpl(tb, osb, vsp);
			return departmentService.findByCode(code);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
