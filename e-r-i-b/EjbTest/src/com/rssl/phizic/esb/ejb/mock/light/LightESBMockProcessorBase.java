package com.rssl.phizic.esb.ejb.mock.light;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.ESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.esberibgate.types.CurrencyImpl;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadResponseType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый заглушечный процессор сегмента "легкой" шины
 */

public abstract class LightESBMockProcessorBase<Rq, Rs> extends ESBMockProcessorBase<Rq, Rs>
{
	private static final String OK_STATUS_CODE = "0";

	protected LightESBMockProcessorBase(Module module)
	{
		super(module);
	}

	protected final String getDate(Calendar date)
	{
		return RequestHelper.getDate(date);
	}

	protected final String getMoney(Money money)
	{
		return RequestHelper.getMoney(money);
	}

	protected final Currency parseCurrency(String currency)
	{
		CurrencyImpl result = new CurrencyImpl();
		result.setCode(currency);
		return result;
	}

	protected final String getAuthCode()
	{
		return getRandomString(6);
	}

	protected final boolean isOkStatus(HeadResponseType.Error status)
	{
		return status.getErrCode().equals(OK_STATUS_CODE);
	}

	protected final HeadResponseType.Error getOkStatus()
	{
		return getStatusInstance(OK_STATUS_CODE, null);
	}

	protected final HeadResponseType.Error getErrorStatus()
	{
		return getErrorStatus("-10", "Описание ошибки, готовое для отображения пользователю.");
	}

	protected final HeadResponseType.Error getErrorStatus(String code, String text)
	{
		return getStatusInstance(code, text);
	}

	protected final HeadResponseType.Error getStatusInstance(String statusCode, String statusDesc)
	{
		HeadResponseType.Error status = new HeadResponseType.Error();
		status.setErrCode(statusCode);
		status.setErrMes(statusDesc);
		return status;
	}

	protected HeadResponseType getHeader(HeadRequestType requestHeader, String fromAbonent, String targetSystem, HeadResponseType.Error error)
	{
		Calendar currentDate = Calendar.getInstance();

		HeadResponseType header = new HeadResponseType();

		header.setMessUID(getMessUID(currentDate, fromAbonent));
		header.setOperUID(getOperUID(requestHeader));

		header.setMessType(requestHeader.getMessType());
		header.setVersion(requestHeader.getVersion());
		header.setTargetSystem(targetSystem);

		header.setError(error);

		header.setParentId(getParentId(requestHeader.getMessUID()));

		return header;
	}

	protected void send(ESBMessage<Rq> xmlRequest, Rs message, String service)
	{
		super.send(xmlRequest, message, ESBSegment.light, service);
	}

	private HeadResponseType.MessUID getMessUID(Calendar date, String fromAbonent)
	{
		HeadResponseType.MessUID messUID = new HeadResponseType.MessUID();
		messUID.setMessageId(new RandomGUID().getStringValue());
		messUID.setMessageDate(getDate(date));
		messUID.setFromAbonent(fromAbonent);
		return messUID;
	}

	private HeadResponseType.OperUID getOperUID(HeadRequestType requestHeader)
	{
		HeadRequestType.OperUID source = requestHeader.getOperUID();

		HeadResponseType.OperUID operUID = new HeadResponseType.OperUID();
		operUID.setOperId(source.getOperId());
		operUID.setSTAN(source.getSTAN());
		operUID.setLTDT(source.getLTDT());
		operUID.setRRN(source.getRRN());
		return operUID;
	}

	private HeadResponseType.ParentId getParentId(HeadRequestType.MessUID messUID)
	{
		HeadResponseType.ParentId parentId = new HeadResponseType.ParentId();
		parentId.setMessageId(messUID.getMessageId());
		parentId.setMessageDate(messUID.getMessageDate());
		parentId.setFromAbonent(messUID.getFromAbonent());
		return parentId;
	}
}
