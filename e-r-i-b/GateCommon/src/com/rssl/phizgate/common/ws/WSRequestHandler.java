package com.rssl.phizgate.common.ws;

import com.Ostermiller.util.Base64;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;
import static com.rssl.phizgate.common.filters.GateContextFilter.*;

/** ’ендлер, добавл€ющий идентификароры в запрос гейту
 * @author akrenev
 * @ created 20.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class WSRequestHandler extends GenericHandler
{
	public static final String PERSON_TB_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_TB";
	public static final String INITIATOR_APPLICATION_KEY = LogThreadContext.class.getName().toUpperCase() + "INITIATOR_APPLICATION";

	private HandlerInfo handlerInfo;

	public void init(HandlerInfo info)
	{
		handlerInfo = info;
	}

	public boolean handleRequest(MessageContext messageContext)
	{
		SOAPMessageContext smc = (SOAPMessageContext) messageContext;
		SOAPMessage message = smc.getMessage();
		String currentOperUID = OperationContext.getCurrentOperUID();
		String sessionId = LogThreadContext.getSessionId();
		String loginId = String.valueOf(LogThreadContext.getLoginId());
		String surname = LogThreadContext.getSurName();
		String firstname = LogThreadContext.getFirstName();
		String partname = LogThreadContext.getPatrName();
		String docSeries = LogThreadContext.getSeries();
		String docNumber = LogThreadContext.getNumber();
		String tb = LogThreadContext.getDepartmentRegion();
		String application = LogThreadContext.getInitiatorApplication() != null ? LogThreadContext.getInitiatorApplication().name() : null;
		String localeId = MultiLocaleContext.getLocaleId();
		String birthDate = LogThreadContext.getBirthday() != null ? DateHelper.toXMLDateFormat(LogThreadContext.getBirthday().getTime()) : null;
		String guestCode = String.valueOf(LogThreadContext.getGuestCode());
		String guestPhoneNumber = LogThreadContext.getGuestPhoneNumber();
		String guestLogin = LogThreadContext.getGuestLogin();
		if (!StringHelper.isEmpty(currentOperUID))
			message.getMimeHeaders().addHeader(OPERATION_CONTEXT_KEY, currentOperUID);
		if (!StringHelper.isEmpty(sessionId))
			message.getMimeHeaders().addHeader(SESSION_ID_KEY, sessionId);
		if (!StringHelper.isEmpty(loginId) && !loginId.equals("null"))
			message.getMimeHeaders().addHeader(LOGIN_ID_KEY, loginId);
		if (!StringHelper.isEmpty(surname))
			message.getMimeHeaders().addHeader(PERSON_SURNAME_KEY, Base64.encode(surname));
		if (!StringHelper.isEmpty(firstname))
			message.getMimeHeaders().addHeader(PERSON_FIRSTNAME_KEY, Base64.encode(firstname));
		if (!StringHelper.isEmpty(partname))
			message.getMimeHeaders().addHeader(PERSON_PATRNAME_KEY, Base64.encode(partname));
		if (!StringHelper.isEmpty(docSeries))
			message.getMimeHeaders().addHeader(PERSON_SERIES_KEY, Base64.encode(docSeries));
		if (!StringHelper.isEmpty(docNumber))
			message.getMimeHeaders().addHeader(PERSON_NUMBER_KEY, Base64.encode(docNumber));
		if (!StringHelper.isEmpty(tb))
			message.getMimeHeaders().addHeader(PERSON_TB_KEY, Base64.encode(tb));
		if (!StringHelper.isEmpty(application))
			message.getMimeHeaders().addHeader(INITIATOR_APPLICATION_KEY, Base64.encode(application));
		if (!StringHelper.isEmpty(localeId))
			message.getMimeHeaders().addHeader(MultiLocaleContext.LOCALE_KEY, Base64.encode(localeId));
		if (!StringHelper.isEmpty(birthDate))
			message.getMimeHeaders().addHeader(PERSON_BIRTHDAY, birthDate);
		if (StringHelper.isNotEmpty(guestCode) && !guestCode.equals("null"))
			message.getMimeHeaders().addHeader(GUEST_CODE, guestCode);
		if (StringHelper.isNotEmpty(guestPhoneNumber))
			message.getMimeHeaders().addHeader(GUEST_PHONE, guestPhoneNumber);
		if(StringHelper.isNotEmpty(guestLogin))
			message.getMimeHeaders().addHeader(GUEST_LOGIN, guestLogin);
		return true;
	}

	public QName[] getHeaders()
	{
		return handlerInfo.getHeaders();
	}
}
