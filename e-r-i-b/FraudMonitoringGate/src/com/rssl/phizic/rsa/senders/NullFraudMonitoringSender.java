package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;

/**
 * Сендер заглушка во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public class NullFraudMonitoringSender implements FraudMonitoringSender
{
	public void initialize(InitializationData data) {}

	public InitializationData getInitializationData()
	{
		return new PhaseInitializationData(InteractionType.NONE, PhaseType.CONTINUOUS_INTERACTION);
	}

	public void send()
	{
		//ничего не отсылаем
	}

	public boolean isNull()
	{
		return true;
	}

	public String getRequestBody()
	{
		return null;
	}

	public void setSendingType(FraudMonitoringRequestSendingType sendingType){}

	public FraudMonitoringRequestSendingType getSendingType()
	{
		return null;
	}

}
