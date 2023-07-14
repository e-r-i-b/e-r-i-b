package com.rssl.phizicgate.wsgate.services.utils.calendar;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_Stub;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class CalendarGateServiceWrapper extends JAXRPCClientSideServiceBase<CalendarGateService_Stub> implements CalendarGateService
{
	public CalendarGateServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/CalendarGateServiceImpl";

		CalendarGateServiceImpl_Impl service = new CalendarGateServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((CalendarGateService_Stub) service.getCalendarGateServicePort(), url);
	}

	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.utils.calendar.generated.GateDocument generatedDocument = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedDocument, document, TypesCorrelation.types);

			return getStub().getNextWorkDay(fromDate, generatedDocument);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.utils.calendar.generated.GateDocument generatedDocument = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedDocument, document, TypesCorrelation.types);
			
			return getStub().isHoliday(date, generatedDocument);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
