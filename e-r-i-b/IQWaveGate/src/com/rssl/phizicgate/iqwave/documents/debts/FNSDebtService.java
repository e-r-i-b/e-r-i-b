package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * ������� ������������� �� ������ ���� ���
 * ��� ������� (��������)
 * ���������� ���������	�������� ���������
 *      BK	                83
 * ������ �������������:
 * �������	         ���	        �����������	                  ���������
 * <Route>	        RouteCode      ��� ������� (��������)          [1]
 * <DebitCard>	    CardInf        ���������� �� ����� ��������	   [1]
 * <RecIdentifier>	Requisite      ����� ���������� ���������	   [1]
 *
 *  ����� � �������������:
 * �������	        ���	        �����������	                                    ���������
 *  <SummCanEdit>	Bool        ������ ����� �������� ����� �������                 [1]
 *                              (������ ����� �false�, �������� ����� ������).
 *  <DebtsRoute>	RouteCode   ��� ������� (��������)	                            [1]
 *  <DebtsCurrCode>	IsoCode     ������ �������������	                            [1]
 *  <DebtsSumma>	DebSumType  ����� �������������, ����� ���� ������������� ���   [1]
 *                              ������ ����, ��������� �� �����������.
 */

public class FNSDebtService extends AbstractIQWaveDebtService
{
	public FNSDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_DEBTS_FNS_REQUEST);
		DebtsHelper.fillDebtRequest(message,recipient, keyFields);
		Document response = serviceFacade.sendOnlineMessage(message, null);
		return DebtsHelper.parseMGTSFNSDebtList(response);
	}
}
