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
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на получение списка клиентов, ожидающих миграцию
 */

public class GetTemporaryNodeClientsInformationRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "getTemporaryNodeClientsInformationRq";
	private static final String MAX_RESULTS_TAG = "maxResults";
	private static final String FIRST_RESULT_TAG = "firstResult";

	private final String fio;
	private final String document;
	private final Calendar birthday;
	private final CreationType creationType;
	private final String agreementNumber;
	private final List<String> tbList;
	private final Long nodeId;
	private final int maxResults;
	private final int firstResult;

	/**
	 * конструктор
	 * @param fio фио клиента
	 * @param document ДУЛ клиента
	 * @param birthday ДР клиента
	 * @param creationType тип договора
	 * @param agreementNumber номер договора
	 * @param tbList список ТБ в которых нужно искать
	 * @param nodeId идентификатор блока
	 * @param maxResults максимальное количество клиентов
	 * @param firstResult смещение выборки
	 */
	public GetTemporaryNodeClientsInformationRequestData(String fio, String document, Calendar birthday, CreationType creationType, String agreementNumber, List<String> tbList, Long nodeId, int maxResults, int firstResult)
	{
		this.fio = fio;
		this.document = document;
		this.birthday = birthday;
		this.creationType = creationType;
		this.agreementNumber = agreementNumber;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.tbList = tbList;
		this.nodeId = nodeId;
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
		if (birthday != null)
			root.appendChild(createTag(request, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(birthday)));
		Element departmentsElement = request.createElement(RequestConstants.TB_LIST_TAG);
		for (String tb : tbList)
			departmentsElement.appendChild(createTag(request, RequestConstants.TB_TAG, tb));
		root.appendChild(departmentsElement);
		root.appendChild(createTag(request, RequestConstants.NODE_ID_TAG, String.valueOf(nodeId)));
		root.appendChild(createTag(request, MAX_RESULTS_TAG, String.valueOf(maxResults)));
		root.appendChild(createTag(request, FIRST_RESULT_TAG, String.valueOf(firstResult)));
		return request;
	}
}
