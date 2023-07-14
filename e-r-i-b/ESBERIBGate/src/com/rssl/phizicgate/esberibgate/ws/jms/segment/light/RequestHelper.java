package com.rssl.phizicgate.esberibgate.ws.jms.segment.light;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.RrnGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SignType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер сборки сообщений в шину
 */

public final class RequestHelper
{
	private static final String RSTYLE = "RSTYLE";
	private static final String LTDT_FORMAT = "yyyyMMddHHmmss";
	private static final String VERSION = "2";

	private RequestHelper(){}

	/**
	 * преобразовать тип "дата" к формату шины
	 * @param date "дата"
	 * @return отформатированные данные
	 */
	public static String getDate(Calendar date)
	{
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}

	/**
	 * преобразовать тип "деньги" к формату шины
	 * @param money "деньги"
	 * @return отформатированные данные
	 */
	public static String getMoney(Money money)
	{
		return money.getDecimal().toString();
	}

	/**
	 * преобразовать тип "деньги" к формату шины
	 * @param money "деньги"
	 * @return отформатированные данные
	 */
	public static String getCurrency(Money money)
	{
		return money.getCurrency().getCode();
	}

	/**
	 * собрать хеадер для сообщения
	 * @param messageType тип сообщения
	 * @return хеаденр
	 * @throws GateException
	 */
	public static HeadRequestType getHeader(String messageType) throws GateException
	{
		Calendar currentDate = Calendar.getInstance();

		HeadRequestType header = new HeadRequestType();

		header.setMessUID(getMessUID(currentDate));
		header.setOperUID(getOperUID(currentDate));

		header.setMessType(messageType);
		header.setVersion(VERSION);

		return header;
	}

	/**
	 * собрать структуру электронной подписи для сообщения
	 * @return структура электронной подписи
	 * @throws GateException
	 */
	public static SignType getSign() throws GateException
	{
		return null;
	}

	private static HeadRequestType.MessUID getMessUID(Calendar date)
	{
		HeadRequestType.MessUID messUID = new HeadRequestType.MessUID();
		messUID.setMessageId(getUUID());
		messUID.setMessageDate(getDate(date));
		messUID.setFromAbonent(RSTYLE);
		return messUID;
	}

	private static HeadRequestType.OperUID getOperUID(Calendar date) throws GateException
	{
		HeadRequestType.OperUID operUID = new HeadRequestType.OperUID();
		RrnGenerator.RrnInfo info = RrnGenerator.generateLightESB(date);
		operUID.setOperId(getUUID());
		operUID.setSTAN(info.stan);
		operUID.setLTDT(getLTDT(date));
		operUID.setRRN(info.rrn);
		return operUID;
	}

	private static String getUUID()
	{
		return new RandomGUID().getStringValue();
	}

	private static String getLTDT(Calendar currentDate)
	{
		return DateHelper.formatDateToStringOnPattern(currentDate, LTDT_FORMAT);
	}
}
