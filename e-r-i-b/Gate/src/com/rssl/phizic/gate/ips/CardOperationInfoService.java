package com.rssl.phizic.gate.ips;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;

/**
 * @author Erkin
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * —ервис дл€ получени€ справочной информации по карточной операции
 */
public interface CardOperationInfoService extends Service
{
	/**
	 * ѕолучить тип операций по коду
	 * @param operationTypeCodes - коды типов операций
	 * @return типы операций
	 */
	GroupResult<Long, CardOperationType> getOperationTypes(Long... operationTypeCodes);
}
