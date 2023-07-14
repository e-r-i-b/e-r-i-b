package com.rssl.phizic.operations.access;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.List;

/**
 * @author vagin
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 * �������� �� ��������� ���� �������� ����� �������������.
 */
public class PostConfirmStrategyCondition extends NoStrategyCondition
{
	public boolean checkCondition(ConfirmableObject object)
	{
		if (object instanceof BusinessDocumentBase)
		{
			List<WriteDownOperation> writeDownOperations = ((BusinessDocumentBase) object).getWriteDownOperations();
			if (writeDownOperations != null && !writeDownOperations.isEmpty())
            {
	            //��� �������� ������ ���� ������ 1 ��� �������������, ����� ������ ����������� ���� �������� ���������.
                if (((BusinessDocumentBase) object).getFormName().equals(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM))
                    return ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountClosingPayment();
                else
                //��� �������������� ���������� � ���������� �� ��� ��������� ��� ������������� ������������ ��� ���.
                    return false;
            }
		}
		return true;
	}
}
