package com.rssl.phizicgate.wsgate.services.clients;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.clients.generated.ClientServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_Stub;
import com.rssl.phizicgate.wsgate.services.types.ClientImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: Pakhomova
 * @created: 14.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class ClientServiceWrapper  extends JAXRPCClientSideServiceBase<ClientService_Stub> implements ClientService
{
	public ClientServiceWrapper(GateFactory factory) throws GateException
	{
		super(factory);
		String url = factory.config(WSGateConfig.class).getURL() + "/ClientServiceImpl";
		ClientServiceImpl_Impl service = new ClientServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((ClientService_Stub) service.getClientServicePort(), url);
	}

	public com.rssl.phizic.gate.clients.Client getClientById(String id) throws GateLogicException, GateException
	{
		try
		{
			String encoded = encodeData(id);
			com.rssl.phizicgate.wsgate.services.clients.generated.Client  generated =  getStub().getClientById(encoded);

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, generated, ClientServiceTypesCorrelation.toGateTypes);

			return client;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 *
	 * @param client описание, содержащее ограниченный надор данных
	 * @return полное описание клиента
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public com.rssl.phizic.gate.clients.Client fillFullInfo(com.rssl.phizic.gate.clients.Client client) throws GateLogicException, GateException
	{
		//todo whether this implementation is correct?
		if (client == null)
			throw new GateLogicException("Параметр client не может быть пустым");

		return getClientById(client.getId());
	}

	public List<com.rssl.phizic.gate.clients.Client> getClientsByTemplate(com.rssl.phizic.gate.clients.Client client, com.rssl.phizic.gate.dictionaries.officies.Office office, int firstResult, int maxResults) throws GateLogicException, GateException
    {
          try
          {
	          com.rssl.phizicgate.wsgate.services.clients.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.clients.generated.Client();

	          BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, ClientServiceTypesCorrelation.toGeneratedTypes);

	          com.rssl.phizicgate.wsgate.services.clients.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.clients.generated.Office();

	          BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, ClientServiceTypesCorrelation.toGeneratedTypes);

	          String encoded = encodeData(generatedOffice.getSynchKey());
	          generatedOffice.setSynchKey(encoded);

	          List<com.rssl.phizicgate.wsgate.services.clients.generated.Client> generatedClients = getStub().getClientsByTemplate(generatedClient, generatedOffice, firstResult, maxResults);

	          List<com.rssl.phizic.gate.clients.Client> clients = new ArrayList<com.rssl.phizic.gate.clients.Client>(generatedClients.size());

	          BeanHelper.copyPropertiesWithDifferentTypes(clients, generatedClients, ClientServiceTypesCorrelation.toGateTypes);

	          return clients;
          }
          catch (InvocationTargetException e)
          {
	          if (e.getTargetException() instanceof TemporalDocumentException)
	          {
		          throw new TemporalGateException(e);
	          }
	          throw new GateException(e);
          }
          catch (RemoteException ex)
          {
	          checkTimeoutException(ex);

	          if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
	          {
		          throw new WSTemporalGateException(ex);
	          }
	          if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
	          {
		          throw new WSGateLogicException(ex);
	          }
	          if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
	          {
		          throw new WSGateException(ex);
	          }
	          throw new GateException(ex);
          }
          catch (Exception e)
          {
	          throw new GateException(e);
          }
     }

	public com.rssl.phizic.gate.clients.Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.clients.generated.Client  generated =  getStub().getClientByCardNumber(rbTbBranchId, cardNumber);

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, generated, ClientServiceTypesCorrelation.toGateTypes);

			return client;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
