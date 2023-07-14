package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * @author tisov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 * интерфейс для данных для инициализации сендеров во фрод-мониторингы
 */
public interface InitializationData
{
	/**
	 * @return тип взаимодействия с системой ФМ
	 */
	InteractionType getInteractionType();

	/**
	 * @return стадия проверки во ФМ
	 */
	PhaseType getPhaseType();

	/**
	 * Выявлена ли смена сим карты
	 * @return true - да
	 */
	boolean isIMSI();
}
