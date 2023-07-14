package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * «аполн€ет ответ на запрос выхода клиента
 * @author Jatsky
 * @ created 08.05.14
 * @ $Author$
 * @ $Revision$
 */

public class LogoffResponseConstructor extends JAXBResponseConstructor<Request, SimpleResponse>
{
	@Override protected SimpleResponse makeResponse(Request request) throws Exception
	{
		Calendar start = GregorianCalendar.getInstance();
		ContextFillHelper.fillContextByLogin(PersonContext.getPersonDataProvider().getPersonData().getLogin());
		WebContext.getCurrentRequest().getSession(false).invalidate();
		Calendar end = GregorianCalendar.getInstance();
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.LOGOFF);
		reader.setKey(com.rssl.phizic.logging.Constants.LOGOFF_KEY);
		reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGOFF_DEFAULT_OPERATION_KEY);
		logWriter.writeSecurityOperation(reader, start, end);
		return new SimpleResponse();
	}
}
