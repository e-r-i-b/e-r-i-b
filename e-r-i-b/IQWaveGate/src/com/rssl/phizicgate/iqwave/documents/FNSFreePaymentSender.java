package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gulov
 * @ created 08.12.2010
 * @ $Authors$
 * @ $Revision$
 */
public class FNSFreePaymentSender extends SimplePaymentSender
{
	// значение Shortname
	private static final String TAX_DOCUMENT_NUMBER = "taxesDocumentNumber";
	private static final String INN = "inn";
	private static final String KPP = "kpp";
	private static final String KBK = "kbk";
	private static final String OKATO = "okato";
	private static final String PAYER_INN = "payerinnpayments";
	private static final String STATUS_PAYER = "creatorstatus";
	private static final String PAYER_LAST_NAME = "payerLastName";
	private static final String PAYER_FIRST_NAME = "payerFirstName";
	private static final String PAYER_MIDDLE_NAME = "payerMiddleName";
	private static final String SPECIAL_CLIENT_NUMBER = "recipcod_spc";
	private static final String RECEIVER_NUMBER = Constants.RECEIVER_NUMBER_SHORT;
	private static final String RECEIVER_NAME = "recip_nameorg";
	private static final String RECEIVER_BIC = "recipbic";
	private static final String RECEIVER_ACCOUNT = "recipaccount";
	private static final String RECEIVER_BANK = Constants.RECEIVER_BANK_SHORT;

	// название параметров из xslt
	private static final String TAX_DOCUMENT_NUMBER_PAYMENT = Constants.REC_IDENTIFIER_FIELD_NAME;
	private static final String STATUS_PAYER_PAYMENT = "statusPayer";
	private static final String RECEIVAR_NAME_PAYMENT = "receiverNameOrder";
	private static final String RECEIVER_BIC_PAYMENT = "receiverBankCode";
	private static final String RECEIVER_ACCOUNT_PAYMENT = "receiverAccount";
	private static final String RECEIVER_BANK_PAYMENT = "receiverBankName";
	private static final String PAYER_INN_PAYMENT = "payerInn";

	// значение Fullname
	private static final String TAX_DOCUMENT_NUMBER_FULL = "Индекс документа";
	private static final String INN_FULL = "ИНН получателя";
	private static final String KPP_FULL = "КПП получателя";
	private static final String KBK_FULL = "Код бюджетной классификации";
	private static final String OKATO_FULL = "Код ОКАТО";
	private static final String PAYER_INN_FULL = "ИНН плательщика";
	private static final String STATUS_PAYER_FULL = "Статус плательщика(101)";
	private static final String PAYER_LAST_NAME_FULL = "Фамилия";
	private static final String PAYER_FIRST_NAME_FULL = "Имя";
	private static final String PAYER_MIDDLE_NAME_FULL = "Отчество";
	private static final String SPECIAL_CLIENT_NUMBER_FULL = "Код спецклиента";
	private static final String RECEIVER_NUMBER_FULL = "Номер организации";
	private static final String RECEIVER_NAME_FULL = "Наименование получателя";
	private static final String RECEIVER_BIC_FULL = "БИК получателя";
	private static final String RECEIVER_ACCOUNT_FULL = "Счет получателя";
	private static final String RECEIVER_BANK_FULL = "Наименование банка получателя";

	private static final String SPECIAL_CLIENT_NUMBER_VALUE = "76999";

	// соответствие Shortname и Fullname
	public static final Map<String, String> PARAMETERS = new HashMap<String, String>()
	{
		{
			put(TAX_DOCUMENT_NUMBER, TAX_DOCUMENT_NUMBER_FULL);
		    put(INN, INN_FULL);
		  	put(KPP, KPP_FULL);
			put(KBK, KBK_FULL);
			put(OKATO, OKATO_FULL);
			put(PAYER_INN, PAYER_INN_FULL);
			put(STATUS_PAYER, STATUS_PAYER_FULL);
			put(PAYER_LAST_NAME, PAYER_LAST_NAME_FULL);
			put(PAYER_FIRST_NAME, PAYER_FIRST_NAME_FULL);
			put(PAYER_MIDDLE_NAME, PAYER_MIDDLE_NAME_FULL);
			put(SPECIAL_CLIENT_NUMBER, SPECIAL_CLIENT_NUMBER_FULL);
			put(RECEIVER_NUMBER, RECEIVER_NUMBER_FULL);
			put(RECEIVER_NAME, RECEIVER_NAME_FULL);
			put(RECEIVER_BIC, RECEIVER_BIC_FULL);
			put(RECEIVER_ACCOUNT, RECEIVER_ACCOUNT_FULL);
			put(RECEIVER_BANK, RECEIVER_BANK_FULL);

		}
	};

	private static final Map<String, String> PARAMETERS_IQWAY_NO_STANDARD_BY_PAYMENT = buildParametersIQWAYPayment();
	private static final Map<String, String> PARAMETERS_IQWAY_NO_STANDARD = buildParametersIQWAY();

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public FNSFreePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	// соответствие Shortname и названия полей в платеже
	private static Map<String, String> buildParametersIQWAYPayment()
	{
		Map<String, String> buildParams = new HashMap<String,String>();
		buildParams.put(TAX_DOCUMENT_NUMBER, TAX_DOCUMENT_NUMBER_PAYMENT);
		buildParams.put(STATUS_PAYER, STATUS_PAYER_PAYMENT);
		buildParams.put(RECEIVER_NAME, RECEIVAR_NAME_PAYMENT);
		buildParams.put(RECEIVER_BIC, RECEIVER_BIC_PAYMENT);
		buildParams.put(RECEIVER_ACCOUNT, RECEIVER_ACCOUNT_PAYMENT);
		buildParams.put(RECEIVER_BANK, RECEIVER_BANK_PAYMENT);
		buildParams.put(PAYER_INN, PAYER_INN_PAYMENT);

		return buildParams;
	}

	// соответствие Shortname и подставляемых значений напрямую
	private static Map<String, String> buildParametersIQWAY()
	{
		Map<String, String> buildParams = new HashMap<String,String>();
		buildParams.put(RECEIVER_NUMBER, "");
		buildParams.put(SPECIAL_CLIENT_NUMBER, SPECIAL_CLIENT_NUMBER_VALUE);
		return buildParams;
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.FNS_FREE_PAYMENT_REQUEST, Constants.FNS_FREE_PAYMENT_RESPONSE);
	}

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		super.fillExecutionMessage(message, payment);	
		addParameters(message, payment);
	}

	protected void addParameters(GateMessage message, CardPaymentSystemPayment payment) throws GateException
	{
		Element root = message.addParameter(Constants.PARAMETERS_TAG, null);

		for (String key : PARAMETERS.keySet())
		{
			String shortName = key;
			String fullName = PARAMETERS.get(shortName);

			if (PARAMETERS_IQWAY_NO_STANDARD.containsKey(shortName))
			{
				addStringParameter(root, shortName, fullName, PARAMETERS_IQWAY_NO_STANDARD.get(shortName));
			}
			else
			{
				addStringParameter(root, shortName, fullName,
						RequestHelper.getStringFromExtendedFields(payment, PARAMETERS_IQWAY_NO_STANDARD_BY_PAYMENT.containsKey(shortName) ? PARAMETERS_IQWAY_NO_STANDARD_BY_PAYMENT.get(shortName) : shortName ));
			}
		}

	}

	private void addStringParameter(Element root, String shortName, String fullName, String tagValue)
	{
		Element node = XmlHelper.appendSimpleElement(root, Constants.PARAMETER_TAG, null);
		XmlHelper.appendSimpleElement(node, Constants.SHORT_NAME_TAG, shortName);
		XmlHelper.appendSimpleElement(node, Constants.FULL_NAME_TAG, fullName);
		XmlHelper.appendSimpleElement(node, Constants.TYPE_TAG, "C");
		XmlHelper.appendSimpleElement(node, Constants.VALUE_TAG, StringHelper.getEmptyIfNull(tagValue));
	}
}
