package com.rssl.phizic.gate.commission;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author vagin
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 * Сервис проверки необходимости запроса расширенных данных комисии в платежах(настройки в АРМ сотрудника).
 */
public interface BackRefCommissionTBSettingService extends Service
{
	/**
	 * Поддерживается ли отображение для данного платежа.
	 * @param payment - платеж.
	 * @return true/false - пддерживается/не поддерживаетя
	 * @throws GateException
	 */
	public boolean isCalcCommissionSupport(GateDocument payment) throws GateException;

	/**
	 * Поддерживается ли отображение для данного платежа.
	 * @param payment - платеж.
	 * @param office - подразделение
	 * @return true/false - пддерживается/не поддерживаетя
	 * @throws GateException
	 */
	public boolean isCalcCommissionSupport(Office office, GateDocument payment) throws GateException;
}
