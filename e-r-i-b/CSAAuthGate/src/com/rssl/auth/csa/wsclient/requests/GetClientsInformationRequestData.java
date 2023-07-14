package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на получение списка клиентов
 */

public class GetClientsInformationRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "getClientsInformationRq";
	private static final String MAX_RESULTS_TAG = "maxResults";
	private static final String FIRST_RESULT_TAG = "firstResult";

	private final String fio;
	private final String document;
	private final Calendar birthday;
	private final String login;
	private final CreationType creationType;
	private final String agreementNumber;
	private final String phoneNumber;
	private final List<String> tbList;
	private final int maxResults;
	private final int firstResult;

	/**
	 * конструктор
	 * @param fio фио клиента
	 * @param document ДУЛ клиента
	 * @param birthday ДР клиента
	 * @param login логин клиента
	 * @param creationType тип договора
	 * @param agreementNumber номер договора
	 * @param phoneNumber номер телефона
	 * @param tbList список ТБ в которых нужно искать
	 * @param maxResults максимальное количество клиентов
	 * @param firstResult смещение выборки
	 */
	public GetClientsInformationRequestData(String fio, String document, Calendar birthday, String login,
	                                        CreationType creationType, String agreementNumber, String phoneNumber,
	                                        List<String> tbList, int maxResults, int firstResult)
	{
		this.fio = fio;
		this.document = document;
		this.birthday = birthday;
		this.creationType = creationType;
		this.agreementNumber = agreementNumber;
		this.phoneNumber = phoneNumber;
		this.login = login;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.tbList = tbList;
		this.maxResults = maxResults;
		this.firstResult = firstResult;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.FIO_TAG, fio));
		root.appendChild(createTag(request, RequestConstants.PASSPORT_PARAM_NAME, this.document));
		root.appendChild(createTag(request, RequestConstants.CREATION_TYPE_TAG, StringHelper.getNullIfNull(this.creationType)));
		root.appendChild(createTag(request, RequestConstants.AGREEMENT_NUMBER_TAG, this.agreementNumber));
		root.appendChild(createTag(request, RequestConstants.PHONE_NUMBER_PARAM_NAME, this.phoneNumber));
		root.appendChild(createTag(request, RequestConstants.LOGIN_PARAM_NAME, login));
		if (birthday != null)
			root.appendChild(createTag(request, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(birthday)));
		Element departmentsElement = request.createElement(RequestConstants.TB_LIST_TAG);
		for (String tb : tbList)
			departmentsElement.appendChild(createTag(request, RequestConstants.TB_TAG, tb));
		root.appendChild(departmentsElement);
		root.appendChild(createTag(request, MAX_RESULTS_TAG, String.valueOf(maxResults)));
		root.appendChild(createTag(request, FIRST_RESULT_TAG, String.valueOf(firstResult)));
		return request;
	}
}
