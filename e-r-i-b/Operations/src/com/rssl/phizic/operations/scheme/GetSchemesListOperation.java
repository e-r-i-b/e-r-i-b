package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.gate.schemes.AccessSchemeService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * User: Evgrafov
 * Date: 03.10.2005
 * Time: 15:15:06
 */
public class GetSchemesListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final String[] AVAILABLE_CATEGORIES = new String[]{AccessCategory.CATEGORY_ADMIN, AccessCategory.CATEGORY_EMPLOYEE, AccessCategory.CATEGORY_CLIENT};
	/**
	 * @return Список схем прав
	 * @throws BusinessException
	 */
	public List<? extends AccessScheme> getAccessSchemes() throws BusinessException, BusinessLogicException
	{
		try
		{
			return GateSingleton.getFactory().service(AccessSchemeService.class).getList(AVAILABLE_CATEGORIES);
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
