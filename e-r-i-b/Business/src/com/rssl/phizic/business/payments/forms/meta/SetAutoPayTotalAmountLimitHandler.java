package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.lang.BooleanUtils;

/**
 * ��������� � ���������� �������� ������������ ����� �� ������������ � ����������� ������
 * @author niculichev
 * @ created 18.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class SetAutoPayTotalAmountLimitHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof AutoPaymentBase))
			throw new DocumentException("������������ ��� ��������� " + document.getClass());

		AutoPaymentBase payment = (AutoPaymentBase) document;
		// ������ ��� ���������� �����������
		if(payment.getExecutionEventType() != ExecutionEventType.REDUSE_OF_BALANCE)
			return;

		// ���� ������������ ����� �� ��������������, �� ������ �� ������
		if(!BooleanUtils.isTrue(payment.isSupportTotalAmount()))
			return;

		// ���� ������ ������ �� ����, ��������� �������� �� ����������
		if(payment.getClientTotalAmountLimit() == null)
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

			BillingServiceProvider provider = (BillingServiceProvider) payment.getServiceProvider();
			ThresholdAutoPayScheme scheme = provider.getThresholdAutoPayScheme();

			if(scheme.getTotalMaxSum() != null)
			{
				try
				{
					payment.setTotalAmountLimit(new Money(scheme.getTotalMaxSum(), currencyService.getNationalCurrency()));
				}
				catch (GateException e)
				{
					throw new DocumentException(e);
				}
			}
		}
		// ����� ��, ��� ���� ������
		else
		{
			payment.setTotalAmountLimit(payment.getClientTotalAmountLimit());
		}
	}
}
