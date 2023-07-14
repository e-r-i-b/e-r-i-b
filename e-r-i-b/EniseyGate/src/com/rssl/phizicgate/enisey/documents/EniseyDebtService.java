package com.rssl.phizicgate.enisey.documents;

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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 12.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class EniseyDebtService extends AbstractService implements DebtService
{
	public EniseyDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		List<Debt> debts = new ArrayList<Debt>();

		EniseyRequestHelper requestHepler = new EniseyRequestHelper(getFactory());
		String recipientSynchKey = (String) recipient.getSynchKey();
		Document result = requestHepler.sendDebtAndValuesRequest(recipientSynchKey, recipient.getService().getCode(), fields);

		Currency currency = getFactory().service(CurrencyService.class).getNationalCurrency();
		String balance = XmlHelper.getSimpleElementValue(result.getDocumentElement(), "Debt");

		DebtImpl debt = new DebtImpl();
		debt.setDescription("Задолженность");
		debt.setCode(recipientSynchKey);
		debt.setFixed(false);

		List<DebtRow> debtRows = new ArrayList<DebtRow>();
		DebtRowImpl debtRow = new DebtRowImpl();
		debtRow.setDescription("");
		debtRow.setCode(recipientSynchKey);
		debtRow.setCommission(new Money(BigDecimal.valueOf(0), currency));
		debtRow.setFine(new Money(BigDecimal.valueOf(0), currency));
		try
		{
			debtRow.setDebt(new Money(NumericUtil.parseBigDecimal(balance), currency));
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}

		debtRows.add(debtRow);
		debt.setRows(debtRows);
		debts.add(debt);

		return debts;
	}
}
