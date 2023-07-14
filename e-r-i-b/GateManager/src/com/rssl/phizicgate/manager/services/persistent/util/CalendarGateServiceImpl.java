package com.rssl.phizicgate.manager.services.persistent.util;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import com.rssl.phizicgate.manager.services.persistent.documents.DocumentManagerHelper;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CalendarGateServiceImpl extends PersistentServiceBase<CalendarGateService> implements CalendarGateService
{
	public CalendarGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			return delegate.getNextWorkDay(fromDate, document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}

	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException
	{
		DocumentManagerHelper helper= new DocumentManagerHelper();

		document = helper.removeRouteInfo(document);
		try
		{
			return delegate.isHoliday(date, document);
		}
		finally
		{
			document = helper.storeRouteInfo(document);
		}
	}
}
