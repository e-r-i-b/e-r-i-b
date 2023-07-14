package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;

/**
 * Базовый класс сендеров во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class FraudMonitoringDataSenderBase<ID extends InitializationData, RQ extends GenericRequest> extends FraudMonitoringProviderSenderBase<ID, RQ>
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private ID initializationData;

	public void initialize(ID initializationData)
	{
		this.initializationData = initializationData;
	}

	public ID getInitializationData()
	{
		if (initializationData != null)
		{
			return initializationData;
		}

		//noinspection unchecked
		return (ID) new PhaseInitializationData(InteractionType.SYNC, PhaseType.CONTINUOUS_INTERACTION);
	}

	public boolean isNull()
	{
		return false;
	}

}
