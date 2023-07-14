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
 * @author hudyakov
 * @ created 12.08.2010
 * @ $Author$
 * @ $Revision$
 *
 * ������ ����������
 *
 * ������ �������������:
 * �������	            ���         �����������                         ���������
 *	<Route> 	        RouteCode   ��� ������� (��������)	                [1]
 *	<DebitCard>	        CardInf     ���������� �� ����� ��������	        [1]
 *	<RecIdentifier>	    Requisite   ��������, ���������������� �����������	[1]
 *
 * ����� �������������:
 * �������	            ���	        �����������	                        ���������
 *	<SummCanEdit>       Bool        ������ ����� �������� ����� �������. (������ ����� �false�, ������ ����� ������).	    [1]
 *	<PayDebtsList>		            ������ ��������������. ������ ������ ��������, ��� � ������� ��� ������� �������������.	[0..n]
 *	    <DebtsRoute>	RouteCode   ��� ������� (��������)	                [1]
 *	    <DebtsNumber>	Long        ����� �������������	                    [1]
 *	    <DebtsPeriod>	PayPeriod   ������ �������������	                [1]
 *	    <DebtsCurrCode>	IsoCode     ������ �������������	                [1]
 *	    <DebtsSumma>	DebSumType  ����� �������������, ������ ���� �������������, ��������� �� �����������.	            [1]
 *	    <DebtsName>	    C-50        �������� �������������	                [1]
 *
 */
public class RostelecomDebtService extends AbstractIQWaveDebtService
{
	public RostelecomDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_DEBTS_ROSTELECOM_REQUEST);
		DebtsHelper.fillDebtRequest(message, recipient, fields);
		Document response = serviceFacade.sendOnlineMessage(message, null);
		return DebtsHelper.parseRostelecomDebts(response);
	}
}
