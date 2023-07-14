package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.operations.access.NullConfirmStrategySource;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author osminin
 * @ created 31.07.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������������� �������� ������ �� �������� ��� ���������
 */
public class ConfirmInvoiceSubscriptionClaimOperation extends ConfirmFormPaymentOperation
{
    public ConfirmStrategySource getStrategy()
   	{
   		//� mAPI ��������� ��� �������������
   		if (ApplicationUtil.isMobileApi())
   			return new NullConfirmStrategySource();
   		return super.getStrategy();
   	}
}
