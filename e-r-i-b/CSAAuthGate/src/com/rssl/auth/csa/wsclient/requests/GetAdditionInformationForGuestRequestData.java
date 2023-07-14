package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на получение дополнительной информации по гостю:
 *  <ol>
 *     <li>Подключён ли телефон к МБ</li>
 *     <li>Есть ли в ЦСА основная учётная запись</li>
 * </ol>
 * @author usachev
 * @ created 29.04.15
 * @ $Author$
 * @ $Revision$
 */
public class GetAdditionInformationForGuestRequestData extends RequestDataBase
{
	private static final String REQUEST_TYPE = "getGuestAdditionInformationRq";
	private String phone;

	/**
	 * Инициализация запроса в Back
	 * @param phone Номер телефона гостя, для которого требуется получить доп информацию
	 */
	public GetAdditionInformationForGuestRequestData(String phone)
	{
		this.phone = phone;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, CSAResponseConstants.PHONE_NUMBER_TAG, phone));
		return request;
	}

	public String getName()
	{
		return REQUEST_TYPE;
	}
}
