package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$  ]
 * Данные инициализации сендера запроса GetResolution
 */
public class GetResolutionInitializationData implements InitializationData
{
	private String clientTransactionId;

	/**
	 * @param clientTransactionId - идентификатор фрод-транзакции
	 */
	public GetResolutionInitializationData(String clientTransactionId)
	{
		this.clientTransactionId = clientTransactionId;
	}

	/**
	 * @return тип взаимодействия с системой фрод-мониторинга
	 */
	public InteractionType getInteractionType()
	{
		return InteractionType.SYNC;
	}

	/**
	 * @return Стадия проверки
	 */
	public PhaseType getPhaseType()
	{
		return PhaseType.CONTINUOUS_INTERACTION;
	}

	public boolean isIMSI()
	{
		return false;
	}

	/**
	 * @return - идентификатор фрод-транзакции
	 */
	public String getClientTransactionId()
	{
		return clientTransactionId;
	}
}
