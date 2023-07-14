package com.rssl.phizicgate.manager.services.routablePersistent.util;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;
import com.rssl.phizicgate.manager.services.persistent.documents.DocumentManagerHelper;

import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CalendarGateServiceImpl extends RoutablePersistentServiceBase<CalendarGateService> implements CalendarGateService
{
	public CalendarGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected CalendarGateService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(CalendarGateService.class);
	}

	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper = new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			return endService(helper.getRouteInfo()).getNextWorkDay(fromDate, document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper(document);

		document = helper.removeRouteInfo(document);
		try
		{
			return endService(helper.getRouteInfo()).isHoliday(date, document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}
}
