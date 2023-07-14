package com.rssl.phizicgate.asbc.documents;

import com.rssl.phizgate.common.payments.systems.recipients.DebtImpl;
import com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Krenev
 * @ created 01.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCDebtService extends AbstractService implements DebtService
{
	public ASBCDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		List<Debt> debts = new ArrayList<Debt>();

		ASBCRequestHelper requestHepler = new ASBCRequestHelper(getFactory());

		String recipientSynchKey = (String) recipient.getSynchKey();
		if (keyFields == null || keyFields.size() != 1)
		{
			throw new GateException("Должно быть ровно одно идентифицирующее поле");
		}

		Document result = requestHepler.makeFindCreditsRequest(recipientSynchKey, keyFields.get(0));
		DebtImpl debt = new DebtImpl();
		debt.setDescription("Задолженность");
		debt.setCode(recipientSynchKey);
		debt.setFixed(false);
		List<DebtRow> debtRows = new ArrayList<DebtRow>();
		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(result.getDocumentElement(), "//result/result_element");
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);

				DebtRowImpl debtRow = new DebtRowImpl();

				debtRow.setDescription(XmlHelper.getSimpleElementValue(node, "infoPu"));

				String balance = XmlHelper.getSimpleElementValue(node, "balance");
				debtRow.setCode(XmlHelper.getSimpleElementValue(node, "creditId"));

				Currency currency = getFactory().service(CurrencyService.class).getNationalCurrency();
				debtRow.setDebt(new Money(NumericUtil.parseBigDecimal(balance).divide(new BigDecimal(100)), currency));
				debtRow.setCommission(new Money(BigDecimal.valueOf(0), currency));
				debtRow.setFine(new Money(BigDecimal.valueOf(0), currency));

				debtRows.add(debtRow);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}

		debt.setRows(debtRows);
		debts.add(debt);

		return debts;
	}
}
