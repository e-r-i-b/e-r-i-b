package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * CHG027203
 * Валидатор проверяющий является ли счет зачисления счетом клиента
 * Так же идет проверка на то, является ли перевод в валюте рубли-рубли 
 * @author niculichev
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class TransferToOwnAccountValidator extends MultiFieldsValidatorBase
{
	private static final String VALUTA_TO_RUB = "Переводить валюту отличную от рублей можно только на свои валютные счета (карты).";
	private static final String RUB_TO_VALUTA = "Переводить валюту отличную от рублей можно только на свои валютные счета (карты).";
	private static final String VALUTA_TO_VALUTA = "На данной странице Вы можете выполнить операцию только в рублях. Пожалуйста, укажите другой счет списания и счет зачисления";
	private static final String ERRORMESSAGE = "Вы пытаетесь перевести деньги между своими счетами. Пожалуйста, перейдите на страницу, щелкнув по ссылке " +
			"<a href = \"/PhizIC/private/payments/payment.do?category=TRANSFER&form=InternalPayment&fromResource=%s&toResource=%s&exactAmount=%s&%s\">" +
			"«Перевод между своими счетами и картами»</a>";
	private static final String ERRORMESSAGE_MOBILE = "Вы пытаетесь перевести деньги между своими счетами. Для этого выберите операцию «Перевод между своими счетами и картами».";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String FROMRESOURCE = "fromResource";
	private static final String RECEIVERACCOUNT = "receiverAccount";
	private static final String ISOURBANK = "isOurBank";
	private static final String EXACT_AMOUNT_FIELD = "exactAmountField";
	private static final String SELL_AMOUNT = "sellAmount";
	private static final String BUY_AMOUNT = "buyAmount";

	private static ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		ExternalResourceLink fromResource = (ExternalResourceLink) retrieveFieldValue(FROMRESOURCE, values);
		String receiverAccount = (String) retrieveFieldValue(RECEIVERACCOUNT, values);
		Boolean isOurBank = (Boolean) retrieveFieldValue(ISOURBANK, values);
		String exactAmount = (String) retrieveFieldValue(EXACT_AMOUNT_FIELD, values);
		BigDecimal sellAmount = (BigDecimal) retrieveFieldValue(SELL_AMOUNT, values);
		BigDecimal buyAmount = (BigDecimal) retrieveFieldValue(BUY_AMOUNT, values);

		return validate(fromResource, receiverAccount, isOurBank, exactAmount, sellAmount, buyAmount);
	}

	/**
	 * Проверка введенных данных
	 * @param fromResource - ресурс списания
	 * @param receiverAccount - счет зачисления
	 * @param isOurBank - признак перевода внутри банка
	 * @param exactAmount - тип суммы зачисления
	 * @param sellAmount - сумма
	 * @param buyAmount - сумма
	 * @return результат проверки
	 */
	public boolean validate(ExternalResourceLink fromResource, String receiverAccount,  Boolean isOurBank,
	                        String exactAmount, BigDecimal sellAmount, BigDecimal buyAmount)  throws TemporalDocumentException
	{
		if(StringHelper.isEmpty(receiverAccount) || fromResource == null)
			return true;

		// Если это Сбербанк, то смотрим принадлежит ли счет зачисления данному клиенту
		if(isOurBank != null && isOurBank)
		{
			try
			{
				ExternalResourceService service = new ExternalResourceService();
				ExternalResourceLink receiverLink = service.findInSystemLinkByNumber(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(),
						ResourceType.CARD, receiverAccount);
				if(receiverLink == null)
					receiverLink = service.findInSystemLinkByNumber(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(),
						ResourceType.ACCOUNT, receiverAccount);						


				// если принадлежит то выводим сообщение с ссылкой на перевод между своими счетами с заполненными данными
				if(receiverLink != null)
				{
					String amountStr = "destination-field-exact".equals(exactAmount) ? "buyAmount=" + buyAmount  : "sellAmount=" + sellAmount;
					if (ApplicationUtil.isApi())
						setMessage(ERRORMESSAGE_MOBILE);
					else
					{
						setMessage(String.format(ERRORMESSAGE, fromResource.getCode(), receiverLink.getCode(), exactAmount, amountStr));
					}
					return false;
				}
			}
			catch (BusinessException e)
			{
				throw new TemporalDocumentException(e);
			}
		}

		// для перевода с карты следущие проверки не распространяются
		if(fromResource instanceof CardLink)
			return true;

		//Если  данный перевод не является переводом между счетами клиента, то он  возможен только в валюте «рубли- рубли»
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			Currency fromResourceCurrency = fromResource.getCurrency();
			Currency nationalCurrency = currencyService.getNationalCurrency();
			Currency toResourceCurrency = currencyService.findByNumericCode(receiverAccount.substring(5, 8));
			// если перевод валюта - валюта
			if(!nationalCurrency.compare(fromResourceCurrency) && !nationalCurrency.compare(toResourceCurrency))
			{
				setMessage(VALUTA_TO_VALUTA);
				return false;
			}
			// если перевод RUB - валюта
			else if(!nationalCurrency.compare(fromResourceCurrency))
			{
				setMessage(VALUTA_TO_RUB);
				return false;
			}
			// если перевод валюта - RUB
			else if(!nationalCurrency.compare(toResourceCurrency))
			{
				setMessage(RUB_TO_VALUTA);
				return false;
			}
			
			return true;
		}
		catch (IKFLException e)
		{
			String mess = "Ошибка при получении валюты";
			log.error(mess, e);
			throw new TemporalDocumentException(mess, e);
		}
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String value)
	{
		message.set(value);
	}
}
