package com.rssl.phizicgate.mock.payments;

import com.rssl.phizgate.common.payments.systems.recipients.DebtImpl;
import com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.DebtRow;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Krenev
 * @ created 15.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockDebtService extends AbstractService implements DebtService
{
	public MockDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		List<Debt> result = new ArrayList<Debt>();
		DebtImpl debt = new DebtImpl();
		debt.setCode("mockDebt");
		debt.setDescription("Debt from MockDebtService");
		debt.setFixed(true);
		Calendar lastPayDate = Calendar.getInstance();
		lastPayDate.set(2012, 1, 1);
		debt.setLastPayDate(lastPayDate);
		Calendar period = Calendar.getInstance();
		period.set(2012, 1, 1);
		debt.setPeriod(period);
		List<DebtRow> debtRows = new ArrayList<DebtRow>();

		DebtRowImpl debtRow = new DebtRowImpl();
		debtRow.setCode("mockDebtRow");
		debtRow.setDescription("DebtRow from MockDebtService");
		CurrencyImpl currency = new CurrencyImpl();
		currency.setCode("RUR");
		currency.setName("œ–»«Õ¿  –Œ——»…— Œ√Œ –”¡Àﬂ");
		currency.setNumber("810");
		currency.setExternalId("810");
		Money money = new Money(BigDecimal.valueOf(0.01),currency);
		debtRow.setCommission(money);
		debtRow.setDebt(money);
		debtRow.setFine(money);
		debtRows.add(debtRow);
		debtRow = new DebtRowImpl();
		debtRow.setCode("mockDebtRow2");
		debtRow.setDescription("DebtRow from MockDebtService2");
		currency = new CurrencyImpl();
		currency.setCode("RUR");
		currency.setName("œ–»«Õ¿  –Œ——»…— Œ√Œ –”¡Àﬂ");
		currency.setNumber("810");
		currency.setExternalId("810");
		money = new Money(BigDecimal.valueOf(0.02),currency);
		debtRow.setCommission(money);
		debtRow.setDebt(money);
		debtRow.setFine(money);
		debtRows.add(debtRow);

		debt.setRows(debtRows);
		result.add(debt);
		return result;
	}
}
