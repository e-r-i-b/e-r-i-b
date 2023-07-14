package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * @author Evgrafov
 * @ created 05.04.2007
 * @ $Author: egorovaav $
 * @ $Revision: 28943 $
 */

public class Retail51DepositSource implements EntityListSource
{
	public Source getSource(Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		DepositProductService depProdService = GateSingleton.getFactory().service(DepositProductService.class);
		try
		{
			return depProdService.getDepositsInfo(getDepartment(params));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	private Department getDepartment(Map<String, String> params) throws BusinessException
	{
		String departmentId = params.get("departmentId");
		if (StringUtils.isEmpty(departmentId))
		{
			throw new BusinessException("Не указан ID департамента!");
		}

		DepartmentService departmentService = new DepartmentService();
		Department department = departmentService.findById(Long.valueOf(departmentId));
		return department;
	}
}