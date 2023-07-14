package com.rssl.phizicgate.wsgate.services.documents;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.impl.documents.SystemWithdrawDocument;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.documents.generated.DocumentServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.documents.generated.DocumentService_Stub;
import com.rssl.phizicgate.wsgate.services.documents.generated.holders.GateDocumentHolder;
import com.rssl.phizicgate.wsgate.services.documents.types.StateUpdateInfoImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * @author egorova
 * @ created 29.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class DocumentServiceWrapper extends JAXRPCClientSideServiceBase<DocumentService_Stub> implements DocumentService, DocumentSender, CommissionCalculator, DocumentUpdater
{
	public DocumentServiceWrapper(GateFactory factory)
	{
		super(factory);
		String urlDocumentSender_PortType_Impl = getFactory().config(WSGateConfig.class).getURL() + "/DocumentServiceImpl";
		DocumentServiceImpl_Impl senderService = new DocumentServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(senderService);
		initStub((DocumentService_Stub) senderService.getDocumentServicePort(), urlDocumentSender_PortType_Impl);
	}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, transfer, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);
			getStub().calcCommission(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(transfer, holder.value, TypesCorrelation.getTypes());
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
			checkRemoteException(ex);

			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void setParameters(Map<String, ?> params){}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);
			getStub().send(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());

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
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				throw new GateWrapperSendTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex);

			if(ex.getMessage().contains(GateTimeOutException.MESSAGE_PREFIX))
				throw new GateSendTimeOutException(ex);

			checkRemoteException(ex);

			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);
			getStub().repeatSend(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());

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
			checkRemoteException(ex);

			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		return update(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);
			getStub().prepare(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());

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
			checkRemoteException(ex);

			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceDocument.getExternalOwnerId());
			serviceDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceDocument);
			getStub().rollback(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());

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
			checkRemoteException(ex);

			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);
			getStub().confirm(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());

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
			checkRemoteException(ex);
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);
			getStub().validate(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());
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
			checkRemoteException(ex);
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != WithdrawDocument.class)
			rollback(new SystemWithdrawDocument(document));
		else
			rollback((WithdrawDocument) document);
	}

	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument serviceGateDocument =
					new com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, document, TypesCorrelation.getTypes());
			String encoded = encodeData(serviceGateDocument.getExternalOwnerId());
			serviceGateDocument.setExternalOwnerId(encoded);

			GateDocumentHolder holder = new GateDocumentHolder(serviceGateDocument);

			com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo serviceStateUpdateInfo =
			getStub().update(holder);
			BeanHelper.copyPropertiesWithDifferentTypes(document, holder.value, TypesCorrelation.getTypes());
			StateUpdateInfoImpl stateUpdateInfo = new StateUpdateInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(stateUpdateInfo, serviceStateUpdateInfo, TypesCorrelation.getTypes());
			return stateUpdateInfo;
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
			checkRemoteException(ex);
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void checkRemoteException(RemoteException ex) throws GateException,GateLogicException
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
		if(ex.getMessage().contains(GateTimeOutException.MESSAGE_PREFIX))
		{
			throw new GateTimeOutException(ex);
		}
		if(ex.getMessage().contains(OfflineExternalServiceException.MESSAGE_PREFIX))
		{
			throw new OfflineExternalServiceException(ex);
		}
		if(ex.getMessage().contains(InactiveExternalServiceException.MESSAGE_PREFIX))
		{
			throw new InactiveExternalServiceException(ex);
		}
	}
}
