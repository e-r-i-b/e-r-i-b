package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * Изменение настроек оповещений клиента о исполненных операциях через MApi
 * @author basharin
 * @ created 09.10.14
 * @ $Author$
 * @ $Revision$
 */

public class MApiOperationSetupNotificationOperation extends SetupNotificationOperation
{
	public void doPreFraudControl() throws BusinessLogicException, BusinessException {}

	protected void doFraudControl() throws ProhibitionOperationFraudException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_ALERT_SETTINGS);
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new ProhibitionOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}
}
