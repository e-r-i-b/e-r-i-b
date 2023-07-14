package com.rssl.phizic.business.shop;

import com.rssl.common.forms.PaymentFieldKeys;
import static com.rssl.common.forms.PaymentFieldKeys.*;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.shopclient.ShopInfoServiceWrapper;
import com.rssl.phizicgate.shopclient.generated.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @author Mescheryakova
 * @ created 23.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕолучает пришедшие по заказу данные из базы и заполн€ет ими пол€ дл€ формы-платежа
 */

public class ExternalPaymentDataServiceSources  implements FieldValuesSource
{
	protected FieldValuesSource source;

	protected static final ServiceProviderService providerService = new ServiceProviderService();
	protected static final BankDictionaryService bankService = new BankDictionaryService();

	protected static final String RECEIVER_ID = "receiverId";
	protected static final String RECEIVER_ACCOUNT = "receiverAccount";
	protected static final String RECEIVER_NAME_ORDER = "receiverNameOrder";
	protected static final String INN = "inn";
	protected static final String KPP = "kpp";
	protected static final String RECEIVER_BANK_CODE = "receiverBankCode";
	protected static final String RECEIVER_COR_ACCOUNT = "receiverCorAccount";
	protected static final String RECEIVER_BANK_NAME = "receiverBankName";
	protected static final String CURRENCY = "currency";
	protected static final String REC_IDENTIFIER = "RecIdentifier";
	protected static final String AMOUNT = "amount";
	protected static final String RECIPIENT = PaymentFieldKeys.PROVIDER_KEY;
	protected static final String CODE_SERVICE = "codeService";
	protected static final String NAME_SERVICE = "nameService";
	protected static final String RECEIVER_PHONE_NUMBER = "phoneNumber";
	protected static final String RECEIVER_NAME_ON_BILL = "nameOnBill";

	/**
	 * ∆урнал дл€ записи сообщений
	 */
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ¬озвращает денежную часть в виде строки
	 */
	protected String getDecimalFromMoney(Money money)
	{
		if (money != null)
		{
			BigDecimal amountDecimal = money.getDecimal();
			if (amountDecimal != null)
				return amountDecimal.toString();
		}
		return "";
	}

	/**
	 * ¬озвращает код валюты или пустую строку
	 */
	protected String getCurrencyFromMoney(Money money)
	{
		if (money != null)
		{
			Currency currency = money.getCurrency();
			if (currency != null)
				return currency.getCode();
		}
		return "";
	}

	public String getValue(String name)
	{
		return source.getValue(name);
	}

	public Map<String, String> getAllValues()
	{
		return source.getAllValues();
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return source.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
