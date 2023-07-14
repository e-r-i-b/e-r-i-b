package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.BankInfoType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author akrenev
 * @ created 15.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����� ������� �������� ��� ������� � ����� ����� jms
 */

public abstract class RequestBuilderBase<Rq, D> implements RequestBuilder<Rq, D>
{
	private final GateFactory factory;

	protected RequestBuilderBase(GateFactory factory)
	{
		this.factory = factory;
	}

	protected GateFactory getFactory()
	{
		return factory;
	}

	protected static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * ������������ RqUUID
	 * @return RqUUID
	 */
	protected static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * ������������ OperUUID
	 * @return OperUUID
	 */
	protected static String generateOUUID()
	{
		return OperationContext.getCurrentOperUID();
	}

	/**
	 * ������������ ���� � ����� �������� ���������
	 * @return RqTm
	 */
	protected static String generateRqTm()
	{
		GregorianCalendar messageDate = new GregorianCalendar();
		return getStringDateTime(messageDate);
	}

	/**
	 * �������� ������� �������� ��� ���������, ���������������
	 * xsd:date	xmlns:xsd=http://www.w3.org/2001/XMLSchema
	 * @param date ���� ��� ��������������
	 * @return xsd:date
	 */
	protected static String getStringDate(Calendar date)
	{
		if (date == null)
			return null;
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}

	protected static String getStringDate(Calendar date, String separator)
	{
		if (date == null)
			return null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy"+separator+"MM"+separator+"dd");
		return dateFormat.format(date.getTime());
	}

	protected BankInfoType makeBankInfo(String rbTbBranch)
	{
		BankInfoType info = new BankInfoType();
		info.setRbTbBrchId(rbTbBranch);
		return info;
	}
}
