package com.rssl.phizicgate.wsgate.services.documents;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_Stub;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author niculichev
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentServiceWrapper extends JAXRPCClientSideServiceBase<AutoPaymentService_Stub> implements AutoPaymentService
{
	public AutoPaymentServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/AutoPaymentServiceImpl";
		AutoPaymentServiceImpl_Impl service = new AutoPaymentServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((AutoPaymentService_Stub) service.getAutoPaymentServicePort(), url);
	}

	public List<AutoPayment> getClientsAutoPayments(List<Card> cards) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.documents.generated.Card> cardList =
					new ArrayList<com.rssl.phizicgate.wsgate.services.documents.generated.Card>();
			BeanHelper.copyPropertiesWithDifferentTypes(cardList, cards, TypesCorrelation.getTypes());

			List<AutoPayment> autoPayments = new ArrayList<AutoPayment>();
			BeanHelper.copyPropertiesWithDifferentTypes(autoPayments, getStub().getClientsAutoPayments(cardList), TypesCorrelation.getTypes());
			return autoPayments;
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

	public GroupResult<String, AutoPayment> getAutoPayment(String... externalId)
	{
		try
		{
			GroupResult<String, AutoPayment> result = new GroupResult<String, AutoPayment>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, getStub().getAutoPayment(externalId), TypesCorrelation.getTypes());
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(externalId, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(externalId, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(externalId, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(externalId, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(externalId, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(externalId, new WSGateException(ex));
			}
			if(ex.getMessage().contains(GateTimeOutException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(externalId, new GateTimeOutException(ex));
			}
			if(ex.getMessage().contains(OfflineExternalServiceException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(externalId, new OfflineExternalServiceException(ex));
			}
			if(ex.getMessage().contains(InactiveExternalServiceException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(externalId, new InactiveExternalServiceException(ex));
			}
			return GroupResultHelper.getOneErrorResult(externalId, new GateException(ex));
		}
		catch (Exception e)
		{
			return GroupResultHelper.getOneErrorResult(externalId, new GateException(e));
		}
	}

	public AutoPaymentStatus checkPaymentPossibilityExecution(String externalId) throws GateException, GateLogicException
	{
		try
		{
			String status = getStub().checkPaymentPossibilityExecution(externalId);
			if (StringHelper.isEmpty(status))
				return null;

			return AutoPaymentStatus.fromValue(Long.parseLong(status));

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

	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment autoPaymentImpl = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment();
			BeanHelper.copyPropertiesWithDifferentTypes(autoPaymentImpl, autoPayment, TypesCorrelation.getTypes());

			List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
			BeanHelper.copyPropertiesWithDifferentTypes(scheduleItems, getStub().getSheduleReport2(autoPaymentImpl, fromDate, toDate), TypesCorrelation.getTypes());
			return scheduleItems;
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

	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment autoPaymentImpl = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment();
			BeanHelper.copyPropertiesWithDifferentTypes(autoPaymentImpl, autoPayment, TypesCorrelation.getTypes());

			List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
			BeanHelper.copyPropertiesWithDifferentTypes(scheduleItems, getStub().getSheduleReport(autoPaymentImpl), TypesCorrelation.getTypes());
			return scheduleItems;
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

	/**
	 * Получение разрешенных типов автоплатежей
	 * @param cardNumber номер карты
	 * @param requisite лицевой счет
	 * @param routeCode код сервиса
	 * @return список разрешенных типов
	 */
	public List<ExecutionEventType> getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws GateException, GateLogicException
	{
		try
		{
			List<ExecutionEventType> executionEventTypes = new ArrayList<ExecutionEventType>();
			for(Object type: getStub().getAllowedAutoPaymentTypes(cardNumber, requisite, routeCode))
				executionEventTypes.add(ExecutionEventType.valueOf((String) type));

			return executionEventTypes;
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
