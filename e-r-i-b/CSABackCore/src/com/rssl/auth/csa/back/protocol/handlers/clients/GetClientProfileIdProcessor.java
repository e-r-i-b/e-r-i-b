package com.rssl.auth.csa.back.protocol.handlers.clients;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 14.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запросов на получениe идентификатора профиля по ФИО+ДУЛ+ДР+ТБ
 */
public class GetClientProfileIdProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE  = "getClientProfileIdRq";
	private static final String RESPONSE_TYPE = "getClientProfileIdRs";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Profile profile = getProfile(document.getDocumentElement());
		return getSuccessResponseBuilder().addProfileIdNullSafe(profile).end();
	}

	private Profile getProfile(Element element) throws Exception
	{
		CSAUserInfo info = fillUserInfo(element);
		return Profile.getByUserInfo(info, false);
	}

}
