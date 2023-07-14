package com.rssl.phizicgate.sbrf.ws.mock.offline;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;

import java.math.BigDecimal;

/**
 * @author Omeliyanchuk
 * @ created 07.05.2008
 * @ $Author$
 * @ $Revision$
 */

public abstract class CurrencyPaymentBaseOfflineHandler extends BaseOfflineHandler
{
	private static final String CURRENCY_DEMAND_CODE = "/currencyCode";
	private static final String CURRENCY_DEMAND_CREDIT = "/creditAccount";
	private static final String CURRENCY_DEMAND_DEBIT = "/debitAccount";

	protected abstract String getPathBase();

	protected String calculateCreditSum(Document request, String sum) throws GateException
	{
		Element root = request.getDocumentElement();
		try
		{
			String currCode = XmlHelper.selectSingleNode(root, getPathBase() + CURRENCY_DEMAND_CODE).getTextContent();
			String credit = XmlHelper.selectSingleNode(root, getPathBase() + CURRENCY_DEMAND_CREDIT).getTextContent();

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(credit));
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency currency = currencyService.findByAlphabeticCode(currCode);

			return convertSum(account,sum, currency,false);
		}
		catch(Exception ex)
		{
			throw new GateException(ex);
		}
	}

	protected String calculateDebitSum(Document request, String sum) throws GateException
	{
		Element root = request.getDocumentElement();
		try
		{
			String currCode = XmlHelper.selectSingleNode(root, getPathBase() + CURRENCY_DEMAND_CODE).getTextContent();
			String debit = XmlHelper.selectSingleNode(root, getPathBase() + CURRENCY_DEMAND_DEBIT).getTextContent();

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(debit));
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency currency = currencyService.findByAlphabeticCode(currCode);

			return convertSum(account ,sum, currency,true);
		}
		catch(Exception ex)
		{
			throw new GateException(ex);
		}
	}

	protected abstract String convertSum(Account account, String sum, Currency currency, boolean isDebet) throws GateException, GateLogicException;
}
