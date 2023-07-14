package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.limits.LimitHelper;

/**
 * @author vagin
 * @ created 27.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class PaymentByTemplateWithSumFactorCondition extends PaymentByTemplateCondition
{
	private boolean useInvert; //��� ��������� card,cap ������ false. ���� sms �� true;

	public PaymentByTemplateWithSumFactorCondition(boolean useInvert)
	{
		this.useInvert = useInvert;
	}

	/**
	 * ���� � ������� �� ��������� ����� � N ���(��� ��������) �� true. ���� ���������, �� ��������� useInvert = ��� ���� � cap - false, ��� ���-true.
	 * ����� ����������� ��������� ������������� ���� �������� �� ������� ���-�������, ���������� � ��� ���������� ��� ��
	 * @param object - ������ ��� ��������
	 * @return true/useInvert
	 */
	public boolean checkCondition(ConfirmableObject object)
	{
		try
		{
			//��������� �������� �� ������ �������� � ����� ����������� ��������� ����� �� ������ ������ ��������.
			//���� ��� ���������� ������ �� ������ ����� �� ����� ������ �� ������� ��� �������������.
			if (object instanceof BusinessDocument && BillingPaymentHelper.isSelfMobileNumberPayment((BusinessDocument)object)
					&& !LimitHelper.needAdditionalConfirm((BusinessDocument) object))
				return true;

			if(object instanceof AbstractPaymentDocument)
			{
				//���� ��������� ����� ������� � N ��� �� ������������� ������ �� ���
				AbstractPaymentDocument document = (AbstractPaymentDocument) object;
				if(checkConfirmSMSSetting(document) || document.getSumIncreasedOverLimit() )
					return useInvert;
			}
			return true;
		}
		catch(BusinessException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}
}


