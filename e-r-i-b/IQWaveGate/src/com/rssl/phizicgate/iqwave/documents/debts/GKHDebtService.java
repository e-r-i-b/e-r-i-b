package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.iqwave.documents.RequestHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
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
 *      BC                  75
 * ������ �������������:
 * �������	         ���	        �����������	                        ���������
 * <Route>	        RouteCode       ��� ������� (��������)                  [1]
 * <DebitCard>	    CardInf         ���������� �� ����� ��������	        [1]
 * <RecIdentifier>	Requisite       ��������, ���������������� �����������	[1]
 * <Period>	        PayPeriod       ������ ���/���	                        [1]
 *
 *  ����� � �������������:
 * �������	                    ���	            �����������	                                                             ���������
 * <SummCanEdit>	            Bool        ������ ����� �������� ����� �������.                                        [1]
 *                                  (������ ����� �false�, ������ ����� ������).
 * <PayDebtsList>	 ������ ��������������. ������ ������ ��������, ��� � ������� ��� ������� �������������.	        [0..n]
 *      <DebtsRoute>	        RouteCode   ��� ������� (��������)	                                                    [1]
 *  	<DebtsNumber>	        Long        ����� �������������	                                                        [1]
 *      <DebtsPeriod>	        PayPeriod   ������ �������������	                                                    [1]
 *  	<DebtsCurrCode>	        IsoCode     ������ �������������	                                                    [1]
 *      <DebtsSumma>	        DebSumType  ����� �������������, ������ ���� �������������, ��������� �� �����������.	[1]
 *  	<DebtsComission>	    Money       ����� ��������, ������ ���� �������������	                                [1]
 *  	<DebtsName>	            C-50        �������� �������������	                                                    [1]
 *  	<DebtsCases>    �������� ������	                                                                                [0�2]
 *  		<CaseNumber>	    Long        ����� ���� ������������ E-pay ��� ���� ����� �������� ������.	            [1]
 *  		<CaseDesc>	        C-50        �������� �������� ������	                                                [1]
 *          <CaseSumma>	        DebSumType  ����� ������������� �������� ������, ������ ���� �������������,
 *                                          ��������� �� �����������.	                                                [1]
 *           <CaseComission>    Money����� �������� �������� ������, ������ ���� �������������	                        [1]
 *
 * ����������: ���� <DebtsCases> �� �������� �������� ���������,
 * ����� ������������� � �������� ������� �� ����� <DebtsSumma> � <DebtsComission>.
 * ���� <DebtsCases> �������� 1 ��� 2 �������� ������,
 * ����� ������������� � �������� ������� �� ����� <CaseSumma> � <CaseComission>.
 */

public class GKHDebtService extends AbstractIQWaveDebtService implements DebtService
{
	public GKHDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient,List<Field> keyFields) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_DEBTS_GKH_REQUEST);
		DebtsHelper.fillDebtRequest(message, recipient, keyFields);
		//� ����� ����� ���������� �������� ������ ���/���
		Field period = BillingPaymentHelper.getFieldById(keyFields, Constants.PERIOD_FIELD_NAME);
		RequestHelper.appendPeriod(message, Constants.PERIOD_FIELD_NAME, (String) period.getValue());

		Document response = serviceFacade.sendOnlineMessage(message, null);
		return DebtsHelper.parseGKHDebts(response);
	}
}