package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.autosubscriptions.EditExternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.autosubscriptions.EditInternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.longoffer.EditCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeEditCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vagin
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 * ������� ��������� ������ ����������� � ������� ����.
 * �������� ������������ �������������� �����������.
    � ����  ���� �������� �����  ��� �������� �������������� �����������:
    <����� �������><����� �����><�������>
    ���:
    �	����� ������� � ����� ���� = 1
    �	����� ����� � ����� ����� ����, ���������� 2 �������
    �	������� � ������� ���������� �������� �������� ������������ 9 ��������. ������ � ����� 1.

 */
public class SetAutopayNumberHandler extends BusinessDocumentHandlerBase
{
	private static final CounterService counterService = new CounterService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!(document instanceof AutoSubscriptionClaim))
			return;
		AutoSubscriptionClaim claim = (AutoSubscriptionClaim)document;
		//���� � ��������� ��� ������ ����� ����������� - ��������� ����� ����.
		if(StringHelper.isNotEmpty(claim.getAutopayNumber()))
			return;
		//���� ����� �� ���������� � ������� � ��� ������ �� ��������������-������ ������ ������ �������� ��� ������ �����������. ���������� ��� ������.
		if(isEditClaim(claim))
			return;

		//���������� � ������������� ����� � ������ �� ��������.
		try
		{
			Long next = counterService.getNext(Counter.createSimpleCounter("AUTOPAY_NUMBER"));
			StringBuilder sb = new StringBuilder();
			Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			sb.append("1").append(StringHelper.appendLeadingZeros(nodeNumber.toString(), 2)).append("1").append(StringHelper.appendLeadingZeros(next.toString(), 8));
			claim.setAutopayNumber(sb.toString());
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	private boolean isEditClaim(AutoSubscriptionClaim claim)
	{
		Class type = claim.getType();
		return type.equals(EditCardToAccountLongOffer.class)
				|| type.equals(EditInternalP2PAutoTransfer.class)
				|| type.equals(EditExternalP2PAutoTransfer.class)
				|| type.equals(EditCardPaymentSystemPaymentLongOffer.class)
				|| type.equals(EmployeeEditCardPaymentSystemPaymentLongOffer.class);
	}
}
