package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.common.types.Application;

import java.util.Map;

/**
 * @author osminin
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Общий экшен для переводов внешнему получателю
 */
public class EditExternalPaymentAction extends EditPaymentAction
{
	private static final String OLD_TEMPLATE_ERROR_MESSAGE = "По этому шаблону Вы можете совершать платежи только с Ваших счетов. Для оплаты с банковских карт, пожалуйста, создайте новый шаблон.";
	private static final String ACCOUNT_TEMPLATE_ERROR_MESSAGE = "По этому шаблону Вы можете совершать платежи только с Ваших карт.";
	private static final String EXTERNAL_ACCOUNT_PAYMENT_ERROR_MESSAGE = "Уважаемый клиент, операция возможна только с банковской карты.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.changeConditions");
		map.remove("afterAccountOpening");
		return map;
	}
	/**
	 * Сохранить возможные ошибки
	 * @param document документ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void saveErrors(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (TemplateHelper.isByOldCPFLTemplate(document))
		{
			saveMessage(currentRequest(), OLD_TEMPLATE_ERROR_MESSAGE);
		}

		if (TemplateHelper.isBy40TBTemplateFromAccount(document))
		{
			saveError(currentRequest(), ACCOUNT_TEMPLATE_ERROR_MESSAGE);
		}

		if (ApplicationUtil.isNotMobileApi())
			showDisallowExternalAccountPaymentMessage(document);
	}

	/**
	 * Отображает сообщение о недоступности внешних платежей со счета, если это актуально.
	 * @param document документ.
	 */
	protected void showDisallowExternalAccountPaymentMessage(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (document != null && DocumentHelper.isPaymentDisallowedFromAccount(document))
			{
				saveMessage(currentRequest(), DocumentHelper.getDisallowedExternalAccountMessage(document));
			}
			else
			{
				ApplicationConfig config = ApplicationConfig.getIt();

				if (config.getApplicationInfo().getApplication() != Application.PhizIA && !DocumentHelper.isExternalJurAccountPaymentsAllowed())
					saveMessage(currentRequest(), EXTERNAL_ACCOUNT_PAYMENT_ERROR_MESSAGE);
			}
		}
		catch(DocumentException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
