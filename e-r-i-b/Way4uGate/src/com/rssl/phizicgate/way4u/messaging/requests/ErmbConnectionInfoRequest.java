package com.rssl.phizicgate.way4u.messaging.requests;

import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XMLWriter;
import com.rssl.phizicgate.way4u.messaging.Way4uRequest;

/**
 * ЕРМБ: Запрос на подключение или отключение
 * @author Rtischeva
 * @ created 11.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbConnectionInfoRequest extends Way4uRequest
{
	private UserInfo userInfo;
	private boolean ermbConnected;

	/**
	 * Конструктор запроса
	 * @param userInfo - инфо о пользователе (фио, дул, др)
	 * @param ermbConnected - подключен ли к ермб
	 */
	public ErmbConnectionInfoRequest(UserInfo userInfo, boolean ermbConnected)
	{
		this.userInfo = userInfo;
		this.ermbConnected = ermbConnected;
		init("Information", "WAY4Appl", "CSA", "Client", "Inquiry", false);
	}

	@Override
	protected void buildObjectForData(XMLWriter writer)
	{
		writer.startElement("ClientIDT");
			writer.writeTextElement("CustomIDT", buildCustomIDTData());
			writer.writeTextElement("CustomCode", "MOBILEBANKING");
		writer.endElement();
	}

	private String buildCustomIDTData()
	{
		return String.format("FNAME=%s;LNAME=%s;MNAME=%s;BDATE=%s;DUL=%s;ERM=%d",
				userInfo.getFirstname(),
				userInfo.getSurname(),
				userInfo.getPatrname(),
				DateHelper.formatDateToString(userInfo.getBirthdate()),
				userInfo.getPassport(),
				ermbConnected ? 1 : 0
		);
	}

	@Override
	protected void buildFilter(XMLWriter writer)
	{
	   return;
	}
}
