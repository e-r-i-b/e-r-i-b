package com.rssl.phizic.business.ermb.disconnector;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;

/**
 * Отключает телефоны от ЕРМБ
 * @author Puzikov
 * @ created 13.04.15
 * @ $Author$
 * @ $Revision$
 */

public interface ErmbPhoneDisconnector
{
	/**
	 * Отключить телефон от ЕРМБ
	 * @param phoneNumber номер телефона
	 * @param reason причина отключения
	 */
	void disconnect(String phoneNumber, PhoneDisconnectionReason reason) throws BusinessException, BusinessLogicException;
}
