package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.PASSWORD_PARAM_NAME;

import com.rssl.phizic.context.RSAContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class FinishRestorePasswordRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "finishRestorePasswordRq";

	private String password;
	private String ouid;

	public FinishRestorePasswordRequestData(String ouid, String password)
	{
		this.ouid = ouid;
		this.password = password;
	}

	/**
	 * @return Имя запроса
	 */
	public String getName()
	{
		return REQUEST_NAME;
	}

	/**
	 * @return тело запроса ввиде строки
	 */
	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));
		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));

		return request;
	}
}
