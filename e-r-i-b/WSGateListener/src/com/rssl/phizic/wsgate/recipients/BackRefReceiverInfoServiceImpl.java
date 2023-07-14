package com.rssl.phizic.wsgate.recipients;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.recipients.generated.BackRefReceiverInfoService;
import com.rssl.phizic.wsgate.recipients.generated.BusinessRecipientInfo;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefReceiverInfoServiceImpl implements BackRefReceiverInfoService
{
	public BusinessRecipientInfo getRecipientInfo(String pointCode, String serviceCode) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService infoService =
					GateSingleton.getFactory().service(com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService.class);

			com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo gateInfo =
					infoService.getRecipientInfo(pointCode, serviceCode);

			if (gateInfo == null)
				return null;

			BusinessRecipientInfo generatedInfo = new BusinessRecipientInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedInfo, gateInfo, TypesCorrelation.getTypes());

			return generatedInfo;
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
}
