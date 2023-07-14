package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author Erkin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от шаблонов МБК
 */

/**
 * Сервис для работы с шаблонами платежей во внешней системе
 */
@Deprecated
//todo CHG059738 удалить
//todo удалить после перехода на использование MobileBankGate в качестве отдельного приложения
public interface GatePaymentTemplateService extends Service
{	
	/**
	 * Возвращает список шаблонов по перечню карт
	 * @param cardNumbers - массив номеров карт, по которым нужно получить список шаблонов
	 * @return список шаблонов платежей
	 */
	GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers);

	/**
	 * Возвращает шаблон платежа по его ID во внешней системе
	 * @param externalId - ID платежа во внешней системе
	 * @return шаблон платежа
	 *  или null, если не найден
	 */
	GatePaymentTemplate getPaymentTemplate(String externalId) throws GateException, GateLogicException;
}
