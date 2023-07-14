package com.rssl.phizic.test.webgate.uec;

import com.rssl.phizic.test.webgate.uec.generated.*;

import java.rmi.RemoteException;
import java.util.Random;

/**
 * @author Erkin
 * @ created 17.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class UECServiceImpl implements UECService
{
	private final Random random = new Random();

	///////////////////////////////////////////////////////////////////////////

	public StatNotRsType UECDocStatNotRq(StatNotRqType request) throws RemoteException
	{
		StatNotRsType response = new StatNotRsType();
		response.setRqUID(request.getRqUID());
		response.setRqTm(request.getRqTm());

		StatNotRqDocumentType[] rqDocs = request.getDocuments();
		ResultType[] rsDocs = new ResultType[rqDocs.length];
		int i = 0;
		for (StatNotRqDocumentType rqDoc : rqDocs)
		{
			int statusCodeRand = random.nextInt(10);
			
			StatusCodeType statusCode;
			String statusDescr;
			if (statusCodeRand < 8) {
				statusCode = StatusCodeType.value1;
				statusDescr = "Принят на исполнение (в обработке)";
			}
			else {
				statusCode = StatusCodeType.value2;
				statusDescr = "Внутренняя ошибка системы";
			}

			StatusType status = new StatusType(statusCode, statusDescr);
			rsDocs[i++] = new ResultType(rqDoc.getDocUID(), status);
		}

		response.setDocuments(rsDocs);

		return response;
	}
}
