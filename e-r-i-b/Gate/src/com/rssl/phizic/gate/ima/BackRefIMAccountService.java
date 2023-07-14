package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;

/**
 * Обратный сервис для работы с ОМС
 * @author Pankin
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public interface BackRefIMAccountService extends Service
{
	/**
	 * Поиск внешнего идентификатора ОМС по номеру
	 * @param loginId идентификатор логина владельца ОМС
	 * @param imAccountNumber номер ОМС
	 * @return Групповая информаци об идентификаторах ОМС <номер ОМС, идентификатор>
	 */
	GroupResult<String, String> findIMAccountExternalId(Long loginId, String... imAccountNumber);
}
