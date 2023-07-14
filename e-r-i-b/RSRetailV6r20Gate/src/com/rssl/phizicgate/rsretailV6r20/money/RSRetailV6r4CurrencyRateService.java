package com.rssl.phizicgate.rsretailV6r20.money;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.common.types.TarifPlanCodeType;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
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

public class RSRetailV6r4CurrencyRateService extends AbstractService implements CurrencyRateService
{
	public RSRetailV6r4CurrencyRateService(GateFactory factory)
	{
		super(factory);
	}

	/**
	 *
	 * @param saleSum сумма продажи (банк продает)
	 * @param saleCurrency  - валюта продажи
	 * @param buySum - сумма покупки (банк покупает)
	 * @param buyCurrency - валюта покупки
	 * @return - сообщение для отправки в ритейл
	 * @throws GateException
	 */
	private GateMessage createMessage(BigDecimal saleSum, Currency saleCurrency, BigDecimal buySum, Currency buyCurrency) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getCurrencyRate_q");
		String date = String.format("%1$te.%1$tm.%1$tY", Calendar.getInstance());
		message.addParameter("date",         date);

		if (buySum != null){
			message.addParameter("buySum",  buySum.toString());
		}
		if (buyCurrency != null){
			//message.addParameter("buyCurrency", ("RUB".equals(buyCurrency.getCode()) ? "RUR" : buyCurrency.getCode()));
			message.addParameter("buyCurrency", buyCurrency.getExternalId());
		}
		if (saleCurrency != null){
			//message.addParameter("saleCurrency", ("RUB".equals(saleCurrency.getCode()) ? "RUR" : saleCurrency.getCode()));
			message.addParameter("saleCurrency", saleCurrency.getExternalId());
		}

		if (saleSum != null){
			message.addParameter("saleSum",      saleSum.toString());
		}
		return message;
	}

	private GateMessage createMessageCB(Currency saleCurrency, BigDecimal amount, Currency buyCurrency) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getRateCB_q");
		String date = String.format("%1$te.%1$tm.%1$tY", Calendar.getInstance());
		message.addParameter("date",         date);

		if (amount != null){
			message.addParameter("buySum", amount.toString());
		}
		if (buyCurrency != null){
			message.addParameter("buyCurrency", buyCurrency.getExternalId());
		}
		if (saleCurrency != null){
			message.addParameter("saleCurrency", saleCurrency.getExternalId());
		}
		return message;
	}

	/**
	 *
	 * @param document документ
	 * @return  значение суммы (tag - "sum"), возвращенное макросом в xml-файле
	 * @throws GateException
	 */
	private BigDecimal getDocumentSum(Document document) throws GateException
	{
		Element sumElem = null;
		try
		{
			sumElem = XmlHelper.selectSingleNode(document.getDocumentElement(), "sum");
			String sumStr = sumElem.getTextContent();
		    return NumericUtil.parseBigDecimal(sumStr);
		}
		catch (ParseException e)
		{
			return(new BigDecimal(-1));
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
		Document document = null;

		if(type.equals(CurrencyRateType.BUY_REMOTE))
		{
		    /*банк покупает валюту from за валюту to*/
			/*макрос вернет число в валюте from, которые банк отдаст, покупая 1 to*/
			message = createMessage(null, to, BigDecimal.ONE, from);

		}
		else if (type.equals(CurrencyRateType.SALE_REMOTE))
		{
			/*макрос вернет количество денег в валюте to, которые возьмет, продавая единицу в валюте from*/
			message = createMessage(BigDecimal.ONE, from, null, to);
		}
		else
		{   //курс ЦБ
			message = createMessageCB(from, BigDecimal.ONE, to);
		}
		try
		{
			document = service.sendOnlineMessage(message, null);
			return new CurrencyRate(from, BigDecimal.ONE, to, getDocumentSum(document), type, tarifPlanCodeType);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Money from, Currency to, Office office, TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		// клиент продает валюту = банк покупает.
		GateMessage message = createMessage(null, to, from.getDecimal(), from.getCurrency());

		Document document = null;
		try
		{
			document = service.sendOnlineMessage(message, null);
			// Тип курса ставим null, потому что в данном случае он не имеет значения.
			return new CurrencyRate(from.getCurrency(), from.getDecimal(), to, getDocumentSum(document), null, tarifPlanCodeType);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Currency from, Money to, Office office, TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		// клиент всегда продает валюту from = банк покупает валюту from.
		GateMessage message = createMessage(to.getDecimal(), to.getCurrency(), null, from);

		Document document = null;
		try
		{
			document = service.sendOnlineMessage(message, null);
			// Тип курса ставим null, потому что в данном случае он не имеет значения.
			return new CurrencyRate(from, getDocumentSum(document), to.getCurrency(), to.getDecimal(), null, tarifPlanCodeType);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

}
