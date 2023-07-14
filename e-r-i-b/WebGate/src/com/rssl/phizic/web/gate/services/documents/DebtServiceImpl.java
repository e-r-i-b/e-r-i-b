package com.rssl.phizic.web.gate.services.documents;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.gate.services.documents.generated.*;
import com.rssl.phizic.web.security.Constants;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class DebtServiceImpl implements DebtService
{
	public List getDebts(RecipientImpl recipient, List fields) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.documents.DebtService debtService = GateSingleton.getFactory().service(com.rssl.phizic.gate.documents.DebtService.class);

			com.rssl.phizgate.common.routable.RecipientImpl recipientImpl = new com.rssl.phizgate.common.routable.RecipientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(recipientImpl, recipient, TypesCorrelation.getTypes());

			List<Field> list = new ArrayList<Field>();
			BeanHelper.copyPropertiesWithDifferentTypes(list, fields, TypesCorrelation.getTypes());

			List debts = new ArrayList();
			List<Debt> gateDebts = debtService.getDebts(recipientImpl, list);
			BeanHelper.copyPropertiesWithDifferentTypes(debts, gateDebts, TypesCorrelation.getTypes());
			return debts;
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

	public com.rssl.phizic.web.gate.services.documents.generated.Pair __forGeneratePair() throws RemoteException
	{
		return null;
	}

	public com.rssl.phizic.web.gate.services.documents.generated.Field __forGenerateField() throws RemoteException
	{
		return null;
	}

	public com.rssl.phizic.web.gate.services.documents.generated.ListValue __forGenerateListValue() throws RemoteException
	{
		return null;
	}

	public DebtRowImpl __forGenerateDebtRow() throws RemoteException
	{
		return null;
	}

	public DebtImpl __forGenerateDebt() throws RemoteException
	{
		return null;
	}
}
