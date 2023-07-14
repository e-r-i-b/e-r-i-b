package com.rssl.phizic.wsgate.statistics.exception;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.statistics.exception.generated.ExternalExceptionInfo;

import java.rmi.RemoteException;
import java.util.Collections;

/**
 * @author akrenev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * сервис сбора статистики по исключениям
 */

public class ExceptionStatisticServiceImpl implements com.rssl.phizic.wsgate.statistics.exception.generated.ExceptionStatisticService
{
	/**
	 * добавить информацию об исключении
	 * @param gateInfo описание ошибки
	 * @return сообщение клиенту
	 */
	public String addException(ExternalExceptionInfo gateInfo) throws RemoteException
	{
		try
		{
			ExceptionStatisticService backService =	GateSingleton.getFactory().service(ExceptionStatisticService.class);
			ExternalExceptionInfoImpl info = new ExternalExceptionInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(info, gateInfo, Collections.<Class, Class>emptyMap());
			return backService.addException(info);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
