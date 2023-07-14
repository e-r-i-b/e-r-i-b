package com.rssl.phizicgate.rsV55.commission;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AccountNotFoundException;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsV55.bankroll.AccountService;
import com.rssl.phizicgate.rsV55.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV55.bankroll.BankrollServiceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractCommissionCalculator implements CommissionCalculator
{
	private AccountService accountService;
	private WebBankServiceFacade webBankServiceFacade;
	private Map<String, ?> parameters = new HashMap<String, Object>();

	protected AccountService getAccountService()
	{
		if (accountService == null)
		{
			accountService = new AccountService();
		}
		return accountService;
	}

	protected WebBankServiceFacade getWebBankServiceFacade()
	{
		if (webBankServiceFacade == null)
		{
			webBankServiceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		}
		return webBankServiceFacade;
	}

	/**
	 * @param document xml-ответ, пришедши из ритейла
	 * @return сумму из документа.
	 */
	protected BigDecimal getDocumentCommission(Document document) throws GateException
	{
		Element sumElem = null;
		try
		{
			sumElem = XmlHelper.selectSingleNode(document.getDocumentElement(), "commission");
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

	protected String getCurrency(AbstractTransfer payment)
	{
		String currencyCode = payment.getChargeOffAmount().getCurrency().getCode();
		return "RUB".equals(currencyCode) ? "RUR" : currencyCode;
	}

	protected Long getFnCash(AbstractAccountTransfer payment) throws GateException, AccountNotFoundException
	{
		return getAccount(payment.getChargeOffAccount(), payment.getClientInfo().getExternalOwnerId()).getBranch();
	}

	protected AccountImpl getAccount(String accountNumber, String clientId) throws GateException
	{
		BankrollServiceImpl bankrollService = (BankrollServiceImpl)GateSingleton.getFactory().service(BankrollService.class);
		AccountImpl account = bankrollService.getClientAccountByNumber(accountNumber, clientId);
		if(account == null)
			throw new GateException("Не найдена информация по счету:"+ accountNumber);

		return account;
	}

	protected String getDate(GateDocument doc)
	{
		return String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(doc.getClientCreationDate()));
	}

	public void setParameters(Map<String, ?> params)
	{
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}
}
