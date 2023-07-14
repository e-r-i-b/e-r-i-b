package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveDebtService extends AbstractService implements DebtService
{
	private static final String MGTS_RECEIVER_CODE = "13";//код получателя МГТС
	private static final String GKH_RECEIVER_CODE = "10";//код получателя ЖКХ
	private static final String FNS_RECEIVER_CODE = "83";//код получателя ФНС
	private static final String ROSTELECOM_RECEIVER_CODE = "125";   //код получателя Ростелеком

	private Map<String, DebtService> delegates = new HashMap<String, DebtService>();

	public IQWaveDebtService(GateFactory factory)
	{
		super(factory);
		delegates.put(MGTS_RECEIVER_CODE, new MGTSDebtService(factory));
		delegates.put(GKH_RECEIVER_CODE, new GKHDebtService(factory));
		delegates.put(FNS_RECEIVER_CODE, new FNSDebtService(factory));
		delegates.put(ROSTELECOM_RECEIVER_CODE, new RostelecomDebtService(factory));
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		String recipientId = (String) recipient.getSynchKey();
		DebtService debtService = delegates.get(recipientId);
		if (debtService == null)
		{
//Поставщик не поддерживет задолженность
			return new ArrayList<Debt>();
		}
		return debtService.getDebts(recipient, fields);
	}
}
