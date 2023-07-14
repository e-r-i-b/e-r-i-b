package com.rssl.phizic.wsgate.clients.backrefservice;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.clients.backrefservice.generated.Client;
import com.rssl.phizic.wsgate.clients.backrefservice.generated.ClientDocument;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author akrenev
 * @ created 04.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class BackRefClientServiceImpl implements com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService
{
	private static final String SEPARATOR = "\\|";	

	public Client getClientById(Long clientId) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.clients.BackRefClientService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.clients.BackRefClientService.class);
			com.rssl.phizic.gate.clients.Client client = service.getClientById(clientId);
			if (client == null)
				return null;
			com.rssl.phizic.wsgate.clients.backrefservice.generated.Client generatedClient = new com.rssl.phizic.wsgate.clients.backrefservice.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, BackRefClientServiceTypesCorrelation.types);
			return generatedClient;
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

	public String getClientDepartmentCode(Long loginId) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.clients.BackRefClientService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.clients.BackRefClientService.class);
			String[] codeArray = service.getClientDepartmentCode(loginId).split(SEPARATOR);
			return ArrayUtils.isEmpty(codeArray) ? null : codeArray[0];
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
	}

	public String getClientCreationType(String clientId) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.clients.BackRefClientService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.clients.BackRefClientService.class);
			return service.getClientCreationType(clientId);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
	}

	public com.rssl.phizic.wsgate.clients.backrefservice.generated.Client getClientByFIOAndDoc(java.lang.String firstName, java.lang.String lastName, java.lang.String middleName, java.lang.String docSeries, java.lang.String docNumber, java.util.Calendar birthDate, String tb) throws java.rmi.RemoteException
	{
		try
		{
			com.rssl.phizic.gate.clients.BackRefClientService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.clients.BackRefClientService.class);
			com.rssl.phizic.gate.clients.Client client = service.getClientByFIOAndDoc(firstName, lastName, middleName, docSeries, docNumber, birthDate, tb);
			if (client == null)
				return null;
			com.rssl.phizic.wsgate.clients.backrefservice.generated.Client generatedClient = new com.rssl.phizic.wsgate.clients.backrefservice.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, BackRefClientServiceTypesCorrelation.types);
			return generatedClient;
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

	public void __forGenerateClientDocument(ClientDocument clientDocument) throws RemoteException
	{}
}
