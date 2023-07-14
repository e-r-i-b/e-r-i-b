package com.rssl.phizic.web.client.payments.forms;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.documents.templates.EditJurPaymentTemplateOperation;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentTemplateAction;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentTemplateForm;
import com.rssl.phizic.web.struts.forms.ActionMessagesManager;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 16.09.2013
 * @ $Author$
 * @ $Revision$
 *
 *
 */
public class JurPaymentTemplateRecipientAction extends EditJurPaymentTemplateAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditJurPaymentTemplateForm frm = (EditJurPaymentTemplateForm) form;

		OperationContext.setCurrentOperUID(frm.getOperationUID());

		EditJurPaymentTemplateOperation operation = (EditJurPaymentTemplateOperation) createEditOperation(frm);
		Map<String, Object> map = frm.getFields();
		map.put(EditJurPaymentForm.FROM_RESOURCE_FIELD, frm.getFromResource());//Заполняем отдельно, тк хранится в отдельным свойстве формы
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(map), EditJurPaymentForm.EDIT_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.setReceiverAccount((String) result.get(Constants.RECEIVER_ACCOUNT_ATTRIBUTE_NAME));
			operation.setReceiverBIC((String) result.get(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME));
			operation.setReceiverINN((String) result.get(Constants.RECEIVER_INN_ATTRIBUTE_NAME));
			operation.setChargeOffResourceLink((PaymentAbilityERL) result.get(Constants.FROM_RESOURCE_ATTRIBUTE_NAME));
			operation.setExternalReceiverId((String) result.get(Constants.EXTERNAL_PROVIDER_KEY_ATTRIBUTE_NAME));

			try
			{
				//если пришел внешний идентификатор поставщика - делаем оплату в его адрес
				String externalProviderId = operation.getExternalReceiverId();
				if (externalProviderId != null)
				{
					return doPrepareExternalProviderPayment(frm, operation);
				}

				//идентифкатор не пришел - идем поставщиков в нашей БД
				List<Object[]> providers = operation.findRecipients();
				if (providers.size() == 1)
				{
					Object[] prov = providers.get(0);
					//если поставщик один и не из региона пользователя-отображаем всплывающее окно с выбором
					if (prov[11].equals("allRegion"))
					{
						frm.setServiceProviders(providers);
						return mapping.findForward(FORWARD_SHOW_FORM);
					}
					frm.setNextURL(doPrepareInternalProviderPayment(Long.valueOf((String)prov[6]), operation, frm).getPath());
					return mapping.findForward(FORWARD_SHOW_FORM);
				}
				if (providers.size() > 1)
				{
					frm.setProviderRegions(operation.getRegions());
					frm.setServiceProviders(providers);
					return mapping.findForward(FORWARD_SHOW_FORM);
				}

				//в БД нужных поставщиков нет - карточные переводы всегда через биллинги с карт
				if (operation.isCardsTransfer())
				{
					frm.setNextURL(doPrepareExternalProviderPayment(frm, operation).getPath());
					return mapping.findForward(FORWARD_SHOW_FORM);
				}

				//перевод со счета - ищем в биллинге по умолчанию
				List<Recipient> defaultBillingRecipients = operation.findDefaultBillingRecipients();


				if (defaultBillingRecipients.size() == 1)
				{
					//если найден 1 получатель - переходим на сразу на форму оплаты
					operation.setExternalReceiverId((String) defaultBillingRecipients.get(0).getSynchKey());
					frm.setNextURL(doPrepareExternalProviderPayment(frm, operation).getPath());
					return mapping.findForward(FORWARD_SHOW_FORM);
				}

				if (defaultBillingRecipients.size() > 1)
				{
					//на этой же форме отображаем их для выбора
					frm.setExternalProviders(defaultBillingRecipients);
					frm.setField(EditJurPaymentForm.EXTERNAL_PROVIDER_KEY_FIELD, operation.getReceiverCodePoint());
					updateFormAdditionalData(frm, operation);
					return mapping.findForward(FORWARD_SHOW_FORM);//отображаем форму
				}
				//поставщики нигде не найдены переходим на форму общей оплаты юрику
				frm.setNextURL(doPrepareJurPayment(frm, operation).getPath());
				return mapping.findForward(FORWARD_SHOW_FORM);
			}
			catch (InactiveExternalSystemException e)
			{
				frm.setField("inactiveESMessages", e.getMessage());
			}
			catch (BusinessLogicException e)
			{
				frm.setField("error", e.getMessage());
			}
		}
		else
		{
			saveErrorsInForm(frm, processor.getErrors());
		}

		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);//даем их исправить.
	}

	protected void saveErrorsInForm(EditJurPaymentTemplateForm frm, ActionMessages errors)
	{
		super.saveErrorsInForm(frm, errors);

		String errorText = StringUtils.join(ActionMessagesManager.getFormValidationError(ActionMessagesManager.COMMON_BUNDLE, ActionMessagesManager.ERROR), '|');
		frm.setField("error",errorText);

		if (StringHelper.isEmpty(errorText))
		{
			Map<String, String> validationMessages = ActionMessagesManager.getFieldsValidationError(ActionMessagesManager.COMMON_BUNDLE, ActionMessagesManager.ERROR);
			String messagesText = MapUtil.format(validationMessages, "=", "|");
			frm.setField("messages", messagesText);
		}
	}

	protected boolean isAjax()
	{
		return true;
	}

	protected ActionForward getForwardFormError()
	{
		//в случае ошибки в nextUrl ничего писать не надо. так как мы остаемся на предыдущей странице
		return new ActionForward("");
	}
}
