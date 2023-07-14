package com.rssl.phizic.web.gate.services.documents;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.gate.services.documents.generated.*;
import com.rssl.phizic.web.gate.services.types.AutoPaymentImpl;
import com.rssl.phizic.web.security.Constants;

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
public class AutoPaymentServiceImpl implements AutoPaymentService
{
	public GroupResult getAutoPayment(String... externalId) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService.class);

			com.rssl.phizic.common.types.transmiters.GroupResult<String, com.rssl.phizic.gate.longoffer.autopayment.AutoPayment> gateGroupResult =
						service.getAutoPayment(externalId);

			com.rssl.phizic.web.gate.services.documents.generated.GroupResult generatedGroupResult =
						new com.rssl.phizic.web.gate.services.documents.generated.GroupResult();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, TypesCorrelation.getTypes());
			return generatedGroupResult;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getClientsAutoPayments(List list_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService.class);

			List<com.rssl.phizic.gate.bankroll.Card> cards = new ArrayList<com.rssl.phizic.gate.bankroll.Card>(list_1.size());
            BeanHelper.copyPropertiesWithDifferentTypes(cards, list_1, TypesCorrelation.getTypes());

			List<com.rssl.phizic.gate.longoffer.autopayment.AutoPayment> gateAutoPayments = service.getClientsAutoPayments(cards);
			List<AutoPayment> generatedAutoPayments = new ArrayList<AutoPayment>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAutoPayments, gateAutoPayments, TypesCorrelation.getTypes());

			return generatedAutoPayments;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String checkPaymentPossibilityExecution(String externalId) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService.class);

			AutoPaymentStatus autoPaymentStatus = service.checkPaymentPossibilityExecution(externalId);
			if (autoPaymentStatus == null)
				return null;

			return autoPaymentStatus.toString();
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getSheduleReport2(AutoPayment generatedAutoPayment, Calendar fromDate, Calendar toDate) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService.class);

			com.rssl.phizic.gate.longoffer.autopayment.AutoPayment gateAutoPayment = new AutoPaymentImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAutoPayment, generatedAutoPayment, TypesCorrelation.getTypes());

			List<com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem> gateScheduleItems =
						service.getSheduleReport(gateAutoPayment, fromDate, toDate);

			List<ScheduleItem> generatedScheduleItems = new ArrayList<ScheduleItem>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedScheduleItems, gateScheduleItems, TypesCorrelation.getTypes());
			
			return generatedScheduleItems;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getSheduleReport(AutoPayment autoPayment_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService service =
					GateSingleton.getFactory().service(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService.class);

			com.rssl.phizic.gate.longoffer.autopayment.AutoPayment autoPayment = new AutoPaymentImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(autoPayment, autoPayment_1, TypesCorrelation.getTypes());

			List<com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem> scheduleItems = service.getSheduleReport(autoPayment);

			List<ScheduleItem> generatedScheduleItems = new ArrayList<ScheduleItem>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedScheduleItems, scheduleItems, TypesCorrelation.getTypes());
			return generatedScheduleItems;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService service =
					GateSingleton.getFactory().service(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService.class);

			List<String> result = new ArrayList<String>();
			for(ExecutionEventType type: service.getAllowedAutoPaymentTypes(cardNumber, requisite, routeCode))
				result.add(type.toString());

			return result;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public IKFLException __forGenerateIKFLException() throws RemoteException
	{
		return null;
	}

	public AutoPayment __forGenerateAutoPayment() throws RemoteException
	{
		return null;
	}

	public ScheduleItem __forGenerateScheduleItem() throws RemoteException
	{
		return null;
	}

	public Card __forGenerateCard() throws RemoteException
	{
		return null;
	}
}
