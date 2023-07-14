package com.rssl.phizic.wsgate.clients;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.exceptions.WSGateInvalidTargerException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.UpdatePersonService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.InvalidTargetException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.clients.generated.CancelationCallBackImpl;
import com.rssl.phizic.wsgate.clients.generated.ClientState;
import com.rssl.phizic.wsgate.clients.generated.Money;
import com.rssl.phizic.wsgate.clients.generated.UpdatePersonService_PortType;
import com.rssl.phizic.wsgate.types.CancelationCallBackType;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 08.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class UpdatePersonService_Server_Impl implements UpdatePersonService_PortType
{
	public void updateState2(String clientId, ClientState clientState_2) throws RemoteException
	{
		try
		{
			UpdatePersonService service = GateSingleton.getFactory().service(UpdatePersonService.class);
			com.rssl.phizic.gate.clients.ClientState clientState = new com.rssl.phizic.gate.clients.ClientState();
			BeanHelper.copyPropertiesWithDifferentTypes(clientState, clientState_2,
					PersonServiceTypesCorrelation.toGateTypes);
			service.updateState(clientId, clientState);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(InvalidTargetException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(WSGateInvalidTargerException.MESSAGE_PREFIX + e.getMessage()+ WSGateInvalidTargerException.MESSAGE_SUFFIX, e);
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

	public void lockOnUnlock(String clientId, Calendar date_2, Boolean boolean_3, Money money_4) throws RemoteException
	{
		try
		{
			UpdatePersonService service = GateSingleton.getFactory().service(UpdatePersonService.class);

			com.rssl.phizic.common.types.Money money = null;
			if (money_4 != null)
			{   //если unlock
				money = new com.rssl.phizic.common.types.Money();
				BeanHelper.copyPropertiesWithDifferentTypes(money, money_4, PersonServiceTypesCorrelation.toGateTypes);
			}

			service.lockOrUnlock(clientId, date_2 == null ? null : date_2.getTime(), boolean_3, money);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(InvalidTargetException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(WSGateInvalidTargerException.MESSAGE_PREFIX + e.getMessage()+ WSGateInvalidTargerException.MESSAGE_SUFFIX, e);
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

	public void updateState(CancelationCallBackImpl callback_1, ClientState clientState_2) throws RemoteException
	{
		try
		{
			UpdatePersonService service = GateSingleton.getFactory().service(UpdatePersonService.class);
			CancelationCallBackType callback = new CancelationCallBackType();

			BeanHelper.copyPropertiesWithDifferentTypes(callback, callback_1,
					PersonServiceTypesCorrelation.toGateTypes);

			com.rssl.phizic.gate.clients.ClientState clientState = new com.rssl.phizic.gate.clients.ClientState();
			BeanHelper.copyPropertiesWithDifferentTypes(clientState, clientState_2,
					PersonServiceTypesCorrelation.toGateTypes);

			service.updateState(callback, clientState);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(InvalidTargetException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(WSGateInvalidTargerException.MESSAGE_PREFIX + e.getMessage()+ WSGateInvalidTargerException.MESSAGE_SUFFIX, e);
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
