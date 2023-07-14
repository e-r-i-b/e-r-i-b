package com.rssl.phizic.rapida;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import sun.security.action.GetPropertyAction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.AccessController;
import java.util.List;

/**
 * @author Krenev
 * @ created 29.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class RapidaURLBuilder
{
	private static final RapidaConfig config = new RapidaConfig();

	private static final String PAYMENT_ID_PREFIX = config.getPaymentIDPrefix();
	private static final String REQUEST_PARAMETER_DELIM = "&";
	private static final String PARAMETERES_DELIM = ";";
	private static final String PARAMETERS_VALUES_DELIM = "%20";
	private static final String TERMINAL_TYPE = "04";
	private static final Object TERMINAL_ID = config.getTerminalID();

	private static String encoding;
	static
	{
		encoding = (String) AccessController.doPrivileged(
				new GetPropertyAction("file.encoding")
		);
	}

	/**
	 * Посторить запрос проверки правильности платежа.
	 * @param payment платеж
	 * @return строка запроса
	 * @throws DocumentException
	 */
	public String buildPaymentRequest(AccountPaymentSystemPayment payment) throws DocumentException
	{
		return "function=payment&" + generateRequest(payment);
	}

	/**
	 * Посторить запрос на исполнение платежа
	 * @param payment платеж
	 * @return строка запроса
	 * @throws DocumentException
	 */
	public String buildCheckPaymentRequest(AccountPaymentSystemPayment payment) throws DocumentException
	{
		return "function=check&" + generateRequest(payment);
	}

	private String generateRequest(AccountPaymentSystemPayment payment) throws DocumentException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PaymExtId=").append(generatePaymentId(payment)).append(REQUEST_PARAMETER_DELIM);
		sb.append("PaymSubjTp=").append(payment.getReceiverPointCode()).append(REQUEST_PARAMETER_DELIM);
		sb.append("Amount=").append(payment.getChargeOffAmount().getAsCents()).append(REQUEST_PARAMETER_DELIM);
		sb.append("Params=").append(generateParams(payment.getExtendedFields())).append(REQUEST_PARAMETER_DELIM);
		sb.append("TermType=").append(TERMINAL_TYPE).append(REQUEST_PARAMETER_DELIM);
		sb.append("TermID=").append(TERMINAL_ID);
		return sb.toString();
	}

	private String generatePaymentId(AccountPaymentSystemPayment payment) {
/* уникальный номер приложения + "_" + идентификатор платежа в ИКФЛ + "_" + текущее время в мс.*/
		StringBuilder sb = new StringBuilder();
		sb.append(PAYMENT_ID_PREFIX);
		sb.append("_").append(payment.getId());
		sb.append("_").append(DateHelper.getCurrentDate().getTimeInMillis());
		return sb.toString();
	}

	private String generateParams(List<Field> extendedFields)
	{
/* необходимо, чтобы необязательные параметры шли в конце */
		StringBuilder sb = new StringBuilder();
		StringBuilder sbMandatory = new StringBuilder();
		int i = extendedFields.size() - 1;
		for (Field field : extendedFields)
		{
			Object value = field.getValue();
			try
			{
			   if (field.isRequired())
			   {
			      sbMandatory.append(field.getExternalId()).append(PARAMETERS_VALUES_DELIM).append(URLEncoder.encode(StringHelper.getEmptyIfNull(value), encoding));
				  if (i > 0)
				     sbMandatory.append(PARAMETERES_DELIM);
			   }
			   else
			   {
			        sb.append(field.getExternalId()).append(PARAMETERS_VALUES_DELIM).append(URLEncoder.encode(StringHelper.getEmptyIfNull(value), encoding));
				   if (i > 0)
				      sb.append(PARAMETERES_DELIM);
			   }
			}
			catch (UnsupportedEncodingException e)
		    {
			// check encoding in setEncoding
		    }

			i--;
		}

		if (sb.length() > 0)
		{
           sbMandatory.append(PARAMETERES_DELIM).append(sb);
		}

		return sbMandatory.toString();
	}
}
