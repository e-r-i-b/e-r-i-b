package com.rssl.phizic.web.help;

import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 17.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * Генератор урла хелпа
 */

public class HelpIdGenerator
{
	private static final Map<String, String> HELP_SUFFIXES = new HashMap<String, String>();
	public static final String SOCIAL_NET_URL_SUFFIX = "/socialNet";

	static
	{
		String rurPaymentReceiverTypePrefix = RurPayment.PHIZ_RECEIVER_TYPE_VALUE + ".";

		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix,                                                             "/externalCard");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE,         "/externalCard");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE,   "/externalCard");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE,  "/externalCard");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE,                   "/ourBank");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE,                  "/ourBank");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE,                "/ourBank");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.OUR_ACCOUNT_TYPE_VALUE,                         "/ourBank");
		HELP_SUFFIXES.put(rurPaymentReceiverTypePrefix + RurPayment.SOCIAL_TRANSFER_TYPE_VALUE,                     "/ourBank");
	}

	/**
	 * генерирует суффикс урла хелпа по RurPayment
	 * @param payment платежка
	 * @return суффикс урла хелпа
	 */
	public static String getHelpIdSuffix(RurPayment payment)
	{
		if (payment == null)
			return StringUtils.EMPTY;

		return StringHelper.getEmptyIfNull(HELP_SUFFIXES.get(payment.getReceiverType() + "." + payment.getReceiverSubType()));
	}
}
