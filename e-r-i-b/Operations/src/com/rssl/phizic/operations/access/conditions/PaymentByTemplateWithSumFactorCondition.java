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
	private boolean useInvert; //для стратегий card,cap задаем false. Если sms то true;

	public PaymentByTemplateWithSumFactorCondition(boolean useInvert)
	{
		this.useInvert = useInvert;
	}

	/**
	 * Если в платеже не превышена сумма в N раз(для шаблонов) то true. Если превышена, то возращаем useInvert = Для чека и cap - false, для СМС-true.
	 * Также учитывается настройка подтверждения всех операций по шаблону СМС-паролем, задаваемая в АРМ сотрудника для ТБ
	 * @param object - платеж для проверки
	 * @return true/useInvert
	 */
	public boolean checkCondition(ConfirmableObject object)
	{
		try
		{
			//проверяем является ли платеж платежом в адрес поставщиков мобильной связи по своему номеру телефона.
			//если нет превышения лимита по группе риска то такой платеж не требует доп подтверждения.
			if (object instanceof BusinessDocument && BillingPaymentHelper.isSelfMobileNumberPayment((BusinessDocument)object)
					&& !LimitHelper.needAdditionalConfirm((BusinessDocument) object))
				return true;

			if(object instanceof AbstractPaymentDocument)
			{
				//если превысили сумму шаблона в N раз то подтверждение только по СМС
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


