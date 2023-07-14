package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.Response;
import com.rssl.phizic.common.types.ermb.ErmbStatus;

/**
 * Информация по состоянию услуги ЕРМБ
 * @author Puzikov
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */

public interface ErmbInfo extends Response
{
	/**
	 * @return состояние услуги
	 */
	public ErmbStatus getStatus();

	/**
	 * @return активный телефон подключения
	 */
	public String getActivePhone();
}
