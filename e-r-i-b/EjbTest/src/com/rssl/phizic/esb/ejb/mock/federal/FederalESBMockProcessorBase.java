package com.rssl.phizic.esb.ejb.mock.federal;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.ESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

/**
 * @author akrenev
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый заглушечный процессор федерального сегмента шины
 */

public abstract class FederalESBMockProcessorBase<Rq, Rs> extends ESBMockProcessorBase<Rq, Rs>
{
	private static final String SERVER_STATUS_DESC = "Текст с описанием ошибки для администратора (CHG030857).";

	protected FederalESBMockProcessorBase(Module module)
	{
		super(module);
	}

	protected final StatusType getOkStatus()
	{
		return getStatusInstance(0L, null, null, null);
	}

	protected final StatusType getErrorStatus()
	{
		return getErrorStatus(-10, "Описание ошибки, готовое для отображения пользователю.");
	}

	protected final StatusType getErrorStatus(long code, String text)
	{
		return getStatusInstance(code, text, null, SERVER_STATUS_DESC);
	}

	protected StatusType getStatusInstance(long statusCode, String statusDesc, String statusType, String serverStatusDesc)
	{
		StatusType status = new StatusType();
		status.setStatusCode(statusCode);
		status.setStatusDesc(statusDesc);
		status.setStatusType(statusType);
		status.setServerStatusDesc(serverStatusDesc);
		return status;
	}

	protected StatusType getStatusInstance(long statusCode, String statusDesc, String serverStatusDesc)
	{
		StatusType status = new StatusType();
		status.setStatusCode(statusCode);
		status.setStatusDesc(statusDesc);
		status.setServerStatusDesc(serverStatusDesc);
		return status;
	}

	protected void send(ESBMessage<Rq> xmlRequest, Rs message, String service)
	{
		super.send(xmlRequest, message, ESBSegment.federal, service);
	}
}
