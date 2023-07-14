package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.RequireAdditionConfirmLimitException;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * Процессор, проверяющий попадание суммы документа в лимит, бросает RequireAdditionConfirmException
 *
 * @author khudyakov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */
public class ByTemplateLimitProcessor implements LimitProcessor
{
	public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		//проверяем только для mAPI (условия нужно проверять только для 5 версии и старше)
		if (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00))
		{
			return;
		}

		//проверяем только платежи по шаблону
		if (!document.isByTemplate())
		{
			return;
		}

		TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
		//проверяем только оплату по подтвержденным по смс шаблонам
		if (!StateCode.WAIT_CONFIRM_TEMPLATE.name().equals(template.getState().getCode()))
		{
			return;
		}

		//увеличение суммы более чем в N раз при проведении операции по шаблону должно требовать подтверждение операции через КЦ
		if (DocumentHelper.checkPaymentByTemplateSum(document, template))
		{
			throw new RequireAdditionConfirmLimitException(Constants.EXCEEDED_AMOUNT_BY_TEMPLATE_PAYMENT_MESSAGE, RestrictionType.MAX_AMOUNT_BY_TEMPLATE, null);
		}
	}
}
