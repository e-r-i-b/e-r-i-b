package com.rssl.phizicgate.rsretailV6r4.money;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
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
			message.addParameter("buyCurrency", ("RUB".equals(buyCurrency.getCode()) ? "RUR" : buyCurrency.getCode()));
		}
		if (saleCurrency != null){
			message.addParameter("saleCurrency", ("RUB".equals(saleCurrency.getCode()) ? "RUR" : saleCurrency.getCode()));
		}

		if (saleSum != null){
			message.addParameter("saleSum",      saleSum.toString());
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

	//признак необходимости инвертировать запрос. Если валюта From RUB
	// или в паре USD EUR from является USD
	private boolean needInvert(Currency from, Currency to)  throws GateException
	{
	   CurrencyHelper helper = new CurrencyHelper();
		return helper.isNational(from) || "USD".equals(from.getCode()) && "EUR".equals(to.getCode());
	}

	// метод возращающий гейтовый документ для получения курсов валюты
	private GateMessage getRateMessage(Currency from, Currency to, CurrencyRateType type) throws GateException
	{

		if (type.equals(CurrencyRateType.BUY_REMOTE))
		{
			/*банк покупает 1 в валюте from за X в валюте to*/
			return createMessage(null, to, BigDecimal.ONE, from);
		}

		/*банк продает 1 в валюте from за X в валюте to*/
		return createMessage(BigDecimal.ONE, from, null, to);
	}

	private GateMessage createMessageCB(Currency saleCurrency, BigDecimal amount, Currency buyCurrency) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getRateCB_q");
		String date = String.format("%1$te.%1$tm.%1$tY", Calendar.getInstance());
		message.addParameter("date",         date);

		if (amount != null){
			message.addParameter("buySum",  amount.toString());
		}
		if (buyCurrency != null){
			message.addParameter("buyCurrency", ("RUB".equals(buyCurrency.getCode()) ? "RUR" : buyCurrency.getCode()));
		}
		if (saleCurrency != null){
			message.addParameter("saleCurrency", ("RUB".equals(saleCurrency.getCode()) ? "RUR" : saleCurrency.getCode()));
		}
		return message;
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage message = null;
		Document document = null;
		boolean needInvert = false;

		if (type.equals(CurrencyRateType.BUY_REMOTE) || type.equals(CurrencyRateType.SALE_REMOTE))
		{
			needInvert = needInvert(from, to);
			if (needInvert)
				message = getRateMessage(to, from, CurrencyRateType.invert(type) );
			else
				message = getRateMessage(from, to, type);
		}
		else
		{   //курс ЦБ
			message = createMessageCB(from, BigDecimal.ONE, to);
		}

		try
		{
			document = service.sendOnlineMessage(message, null);
			BigDecimal sum = getDocumentSum(document); //результат запроса
			BigDecimal scaleFrom = BigDecimal.ONE;
			BigDecimal scaleTo = sum;
			// если есть необходимости инвертировать запрос
			// при RUB не должно быть 1
			if (needInvert)
			{
				scaleFrom = sum ;
				scaleTo = BigDecimal.ONE;
			}

			return new CurrencyRate(from, scaleFrom, to, scaleTo, type, tarifPlanCodeType);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Money from, Currency to, Office office, TarifPlanCodeType tarifPlanCodeType) throws GateException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		// клиент всегда продает валюту from = банк покупает валюту from.
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
