package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 *
 * запрос на аутентификацию пользователя через АТМ
 */
public class FinishCreateATMSessionRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "finishCreateATMSessionRq";

	private String ouid;

	/**
	 * ctor
	 * @param ouid идентификатор операции
	 */
	public FinishCreateATMSessionRequestData(String ouid)
	{
		this.ouid = ouid;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.OUID_PARAM_NAME, ouid);

		return document;
	}
}
