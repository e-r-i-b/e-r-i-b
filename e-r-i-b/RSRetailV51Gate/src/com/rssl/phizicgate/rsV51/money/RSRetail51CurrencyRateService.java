package com.rssl.phizicgate.rsV51.money;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.common.types.TarifPlanCodeType;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 * @author Roshka
 * @ created 18.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSRetail51CurrencyRateService extends AbstractService implements CurrencyRateService
{
	public RSRetail51CurrencyRateService(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @param saleSum ����� ������� (���� �������)
	 * @param saleCurrency  - ������ �������
	 * @param buySum - ����� ������� (���� ��������)
	 * @param buyCurrency - ������ �������
	 * @return - ��������� ��� �������� � ������
	 * @throws GateException
	 */
	private GateMessage createMessage(BigDecimal saleSum, Currency saleCurrency, BigDecimal buySum, Currency buyCurrency) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getRateCurrency_q");
		String date = String.format("%1$te.%1$tm.%1$tY", Calendar.getInstance());
		String time = String.format("%1$tk:%1$tM", Calendar.getInstance());
		message.addParameter("date",         date);
		message.addParameter("time",         time);

		if (buySum != null){
			message.addParameter("buySum",  buySum.toString());
		}
		if (buyCurrency != null){
			message.addParameter("buyCurrency", buyCurrency.getExternalId());
		}
		if (saleCurrency != null){
			message.addParameter("saleCurrency", saleCurrency.getExternalId());
		}

		if (saleSum != null){
			message.addParameter("saleSum",      saleSum.toString());
		}
		return message;
	}

	/**
	 *
	 * @param document ��������, ��������� �� �������
	 * @param tagName ��� ����, �������� �������� ���� �������
	 * @return  ��������, ��������� � ��������� � ���� tagName
	 * @throws GateException
	 */
	private BigDecimal getDocumentSum(Document document, String tagName) throws GateException
	{
		try
		{
			Element sumElem = XmlHelper.selectSingleNode(document.getDocumentElement(), tagName);
			String sumStr = sumElem.getTextContent();
		    return NumericUtil.parseBigDecimal(sumStr);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage message = null;

		if(type.equals(CurrencyRateType.BUY_REMOTE))
		{
			/*���� �������� 1 � ������ from �� X � ������ to*/
			message = createMessage(null, to, BigDecimal.ONE, from);
		}
		else
		{
			message = createMessage(BigDecimal.ONE, from, null, to);
		}
		try
		{
			Document document = service.sendOnlineMessage(message, null);
			if (type.equals(CurrencyRateType.CB))
			{
				//���� ��
				return new CurrencyRate(from, BigDecimal.ONE, to, getDocumentSum(document, "CBrate"), type, tarifPlanCodeType);
			}
			else
			{
				return new CurrencyRate(from, BigDecimal.ONE, to, getDocumentSum(document, "rate"), type, tarifPlanCodeType);
			}
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Money from, Currency to, Office office, TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		// ������ ������ ������� ������ from = ���� �������� ������ from.
		GateMessage message = createMessage(null, to, from.getDecimal(), from.getCurrency());
		try
		{
			Document document = service.sendOnlineMessage(message, null);
			// ��� ����� ������ null, ������ ��� � ������ ������ �� �� ����� ��������.
			return new CurrencyRate(from.getCurrency(), from.getDecimal(), to, getDocumentSum(document, "sum"), null, tarifPlanCodeType);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Currency from, Money to, Office office, TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		// ������ ������ ������� ������ from = ���� �������� ������ from.
		GateMessage message = createMessage(to.getDecimal(), to.getCurrency(), null, from);

		try
		{
			Document document = service.sendOnlineMessage(message, null);
			// ��� ����� ������ null, ������ ��� � ������ ������ �� �� ����� ��������.
			return new CurrencyRate(from, getDocumentSum(document, "sum"), to.getCurrency(), to.getDecimal(), null, tarifPlanCodeType);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}
}