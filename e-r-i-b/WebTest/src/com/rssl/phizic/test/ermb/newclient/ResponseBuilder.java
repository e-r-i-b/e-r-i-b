package com.rssl.phizic.test.ermb.newclient;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.newclient.message.generated.ClientToAddType;
import com.rssl.phizic.business.ermb.newclient.message.generated.Request;
import com.rssl.phizic.business.ermb.newclient.message.generated.ClientResultSType;
import com.rssl.phizic.business.ermb.newclient.message.generated.ClientResultType;
import com.rssl.phizic.business.ermb.newclient.message.generated.Response;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.ermb.newclient.accepted.ErmbAcceptedRegistrationService;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.lang.math.RandomUtils;

import java.util.List;

/**
 * @author Gulov
 * @ created 20.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Построитель ответа ОСС о добавлении новых клиентов
 */
class ResponseBuilder
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final long[] OPERATION_RESULTS = new long[]{0, 1205241111, 1205242222, 1205242229};
	private static final int OPERATION_RESULTS_COUNT = OPERATION_RESULTS.length;
	private static final long[] CLIENT_RESULTS = new long[]{0, 1205230001, 1205230002, 1205230003, 1205230004, 1205230005, 1205238888};
	private static final int CLIENT_RESULTS_COUNT = CLIENT_RESULTS.length;
	private static final ErmbAcceptedRegistrationService service = new ErmbAcceptedRegistrationService();

	private final Request request;

	public ResponseBuilder(Request request)
	{
		this.request = request;
	}

	Response build()
	{
		Response result = new Response();
		long operationResult = buildOperationResult();
		result.setOperationResult(operationResult);
		if (operationResult != 0)
		{
			buildErrorDescription(result);
			return result;
		}
		result.setClientResultS(buildClientResults());
		return result;
	}

	private ClientResultSType buildClientResults()
	{
		ClientResultSType result = new ClientResultSType();
		List<ClientResultType> results = result.getClientResult();
		for (ClientToAddType incoming : request.getClientSToAdd().getClientToAdd())
		{
			ClientResultType output = new ClientResultType();
			output.setMSISDN(incoming.getMSISDN());
			long clientResult = buildClientResult();
			output.setResult(clientResult);
			if (clientResult == 0)
				addAccepted(incoming, output);
			results.add(output);
		}
		return result;
	}

	private long buildOperationResult()
	{
		return generateResult(OPERATION_RESULTS, OPERATION_RESULTS_COUNT - 1);
	}

	private long buildClientResult()
	{
		return generateResult(CLIENT_RESULTS, CLIENT_RESULTS_COUNT - 1);
	}

	private long generateResult(long[] results, int count)
	{
		int i = RandomUtils.nextInt(count);
		return results[i % 2 == 0 ? 0 : i];
	}

	private void buildErrorDescription(final Response response)
	{
		if (response.getOperationResult() != 0)
			response.setErrorDescription("Ошибка");
	}

	private void addAccepted(final ClientToAddType incoming, ClientResultType output)
	{
		try
		{
			service.add(incoming.getMSISDN(), XMLDatatypeHelper.parseDateTime(incoming.getChangedAt().toXMLFormat()));
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при добавлении записи в таблицу принятых подкючений ОСС", e);
			output.setResult(CLIENT_RESULTS[6]);
		}
	}
}
