package com.rssl.phizic.operations.csaadmin.node;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения контекста операции из предудущего блока
 */
public class LastNodeInfoOperation extends OperationBase
{
	private static final CSAAdminAuthService csaAdminAuthService = new CSAAdminAuthService();

	/**
	 * Сохранить контекст операции для работы с ним в следующем блоке
	 * @param operationContext - контекст операции
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void saveOperationContext(Map<String, Object> operationContext) throws BusinessException, BusinessLogicException
	{
		try
		{
			csaAdminAuthService.saveOperationContext(operationContext);
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

	/**
	 * @return контекст операции выполняемой в предудущем блоке
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Map<String, Object> getOperationContext() throws BusinessException, BusinessLogicException
	{
		try
		{
			return csaAdminAuthService.getOperationContext();
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
