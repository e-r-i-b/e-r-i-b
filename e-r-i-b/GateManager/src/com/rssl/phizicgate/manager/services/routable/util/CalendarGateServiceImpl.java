package com.rssl.phizicgate.manager.services.routable.util;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class CalendarGateServiceImpl extends RoutableServiceBase implements CalendarGateService
{
	private CalendarGateService businessDelegate;

	public CalendarGateServiceImpl(GateFactory factory)
	{
		super(factory);
		businessDelegate = (CalendarGateService) getDelegate(CalendarGateService.class.getName() + BUSINESS_DELEGATE_KEY);
	}

	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		return getDelegate(document).getNextWorkDay(fromDate, document);
	}

	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException
	{
		return getDelegate(document).isHoliday(date, document);
	}

	/**
	 * Получение делегата сервиса календаря по документу
	 * @param document документ
	 * @return делегат
	 * @throws GateException
	 */
	private CalendarGateService getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		String officeId = document.getOffice().getSynchKey().toString();
		boolean calendarAvailable = false;
		GateFactory documentGateDelegate = null; //конкретная фабрика гейта, которая обрабатывает документ.
		if (IDHelper.isRetailOfficeId(officeId))
		{
			documentGateDelegate = getDelegateFactory(officeId);
			//есть ли в гейте свой календарь....
			calendarAvailable = documentGateDelegate.service(GateInfoService.class).isCalendarAvailable(document.getOffice());
		}

		//есть ли в гейте свой календарь....
		if (calendarAvailable)
		{
			//есть - используем его
			return documentGateDelegate.service(CalendarGateService.class);
		}
		//нет - юзаем бизнесовый
		return businessDelegate;
	}
}
