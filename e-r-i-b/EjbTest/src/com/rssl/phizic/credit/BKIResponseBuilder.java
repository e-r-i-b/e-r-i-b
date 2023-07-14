package com.rssl.phizic.credit;

import com.rssl.phizic.gate.bki.BKIRequestType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * Заглушечный построитель ответов из БКИ
 * @author Puzikov
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */

class BKIResponseBuilder
{
	private static final String CHECK_CREDIT_HISTORY_RESPONSE_FILE = "com/rssl/phizic/credit/bki/CheckCreditHistory_Response.xml";

	private static final String GET_CREDIT_HISTORY_RESPONSE_FILE_DEFAULT = "com/rssl/phizic/credit/bki/GetCreditHistory_Response.xml";

	private static final String GET_CREDIT_HISTORY_RESPONSE_FILE_TEMPLATE = "com/rssl/phizic/credit/bki/%s.xml";

	private final BKIMessageSerializer serializer = new BKIMessageSerializer();

	String makeResponse(EnquiryRequestERIB request) throws Exception
	{
		BKIRequestType requestType = BKIRequestType.fromCode(request.getRequestType());

		EnquiryResponseERIB response = createMockResponse(requestType, request);
		response.setRqUID(request.getRqUID());
		response.setRqTm(Calendar.getInstance());
		response.setOperUID(request.getOperUID());

		return serializer.marshallBKIResponse(response);
	}

	private EnquiryResponseERIB createMockResponse(BKIRequestType requestType, EnquiryRequestERIB request) throws Exception
	{
		switch (requestType)
		{
			case BKICheckCreditHistory:
				Random random = new Random();
				String checkResponseXML = ResourceHelper.loadResourceAsString(CHECK_CREDIT_HISTORY_RESPONSE_FILE, "UTF-8");
				EnquiryResponseERIB checkResponse = serializer.unmarshallBKIResponse(checkResponseXML);
				// Каждый 6 клиент без кредитной истории
				checkResponse.getConsumers().getSS().get(0).getSummary().setCAISRecordsOwner(new Long(random.nextInt(6)));
				return checkResponse;

			case BKIGetCreditHistory:
				String fileName = request.getConsumers().get(0).getPlaceOfBirth();
				String responseFile;
				if (StringHelper.isNotEmpty(fileName))
					responseFile = String.format(GET_CREDIT_HISTORY_RESPONSE_FILE_TEMPLATE, fileName);
				else
					responseFile = GET_CREDIT_HISTORY_RESPONSE_FILE_DEFAULT;
				String getReportResponseXML;
				try
				{
					getReportResponseXML = ResourceHelper.loadResourceAsString(responseFile, "UTF-8");
				} catch (IOException e)
				{
					getReportResponseXML = ResourceHelper.loadResourceAsString(GET_CREDIT_HISTORY_RESPONSE_FILE_DEFAULT, "UTF-8");
				}
				return serializer.unmarshallBKIResponse(getReportResponseXML);

			default:
				throw new UnsupportedOperationException("Неожиданный тип запроса в БКИ: " + requestType);
		}
	}
}
