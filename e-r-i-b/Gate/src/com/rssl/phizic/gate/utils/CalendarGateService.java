package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;

/**
 * @author Gainanov
 * @ created 02.02.2009
 * @ $Author$
 * @ $Revision$
 */
public interface CalendarGateService extends Service
{
	/**
	 * Получить следующий рабочий день (используется календарь внешней системы)
	 * @param fromDate дата, от которой считается следующий рабочий день
	 * @param document - документ
	 * @return дата следующего рабочего дня
	 */
	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException;

	/**
	 * Определить, является ли указанная дата выходным (нерабочим днем).
	 * Используется календарь из внешней системы
	 * @param date дата для проверки
	 * @param document - документ
	 * @return выодной день или нет
	 */
	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException;
}
