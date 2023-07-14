package com.rssl.phizic.web.client.payments.forms;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.payment.EditJurPaymentOperation;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentAction;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentForm;
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
 * @author lukina
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class JurPaymentRecipientAction extends EditJurPaymentAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditJurPaymentForm frm = (EditJurPaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		OperationContext.setCurrentOperUID(frm.getOperationUID());

		EditJurPaymentOperation operation = createEditOperation(request, frm, messageCollector);
		Map<String, Object> map = frm.getFields();
		map.put(EditJurPaymentForm.FROM_RESOURCE_FIELD, frm.getFromResource());//��������� ��������, �� �������� � ��������� �������� �����
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(map), EditJurPaymentForm.EDIT_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.setReceiverAccount((String) result.get(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD));
			operation.setReceiverBIC((String) result.get(EditJurPaymentForm.RECEIVER_BIC_FIELD));
			operation.setReceiverINN((String) result.get(EditJurPaymentForm.RECEIVER_INN_FIELD));
			operation.setChargeOffResource((PaymentAbilityERL) result.get(EditJurPaymentForm.FROM_RESOURCE_FIELD));
			//������������� ��� ������� �������� � ����� � ��������, ����� ������ nullPointer �� �������� isBilling. BUG032174
			if (frm.getTemplate() != null && frm.getTemplate().compareTo(0L) > 0)
			{
				AbstractPaymentDocument doc = (AbstractPaymentDocument) operation.getDocument();
				com.rssl.phizic.business.documents.ResourceType resType = operation.getChargeOffResource().getResourceType();
				doc.setChargeOffResourceType(resType.getResourceLinkClass().getName());
			}
			try
			{
				//���� ������ ������� ������������� ���������� - ������ ������ � ��� �����
				String externalProviderId = (String) result.get(EditJurPaymentForm.EXTERNAL_PROVIDER_KEY_FIELD);
				if (externalProviderId != null)
				{
					return doPrepareExternalProviderPayment(operation, externalProviderId, frm, request);
				}

				//������������ �� ������ - ���� ����������� � ����� ��
				List<Object[]> providers = operation.findRecipients();
				if (providers.size() == 1)
				{
					Object[] prov = providers.get(0);
					//���� ��������� ���� � �� �� ������� ������������-���������� ����������� ���� � �������
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

				//� �� ������ ����������� ��� - ��������� �������� ������ ����� �������� � ����
				if (operation.isCardsTransfer())
				{
					frm.setNextURL(doPrepareExternalProviderPayment(operation, null, frm, request).getPath());
					return mapping.findForward(FORWARD_SHOW_FORM);
				}

				//������� �� ����� - ���� � �������� �� ���������
				List<Recipient> defaultBillingRecipients = operation.findDefaultBillingRecipients();

				if (defaultBillingRecipients.size() == 1)
				{
					//���� ������ 1 ���������� - ��������� �� ����� �� ����� ������
					frm.setNextURL(doPrepareExternalProviderPayment(operation, (String) defaultBillingRecipients.get(0).getSynchKey(), frm, request).getPath());
					return mapping.findForward(FORWARD_SHOW_FORM);
				}

				if (defaultBillingRecipients.size() > 1)
				{
					//�� ���� �� ����� ���������� �� ��� ������
					frm.setExternalProviders(defaultBillingRecipients);
					frm.setField(EditJurPaymentForm.EXTERNAL_PROVIDER_KEY_FIELD, operation.getReceiverCodePoint());
					updateFormAdditionalData(frm, operation);
					return mapping.findForward(FORWARD_SHOW_FORM);//���������� �����
				}
				//���������� ����� �� ������� ��������� �� ����� ����� ������ �����
				frm.setNextURL(doPrepareJurPayment(operation,frm).getPath());
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
			saveErrorsInForm(frm, request, processor.getErrors());
		}

		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);//���� �� ���������.
	}

	protected void saveErrorsInForm(EditJurPaymentForm frm,HttpServletRequest request, ActionMessages errors)
	{
		saveErrors(request, errors); // ��������� ������ ���������

		List<String> validationErrors = ActionMessagesManager.getFormValidationError(
				ActionMessagesManager.COMMON_BUNDLE, ActionMessagesManager.ERROR);
		String errorText = StringUtils.join(validationErrors,'|');
		frm.setField("error",errorText);

		if (StringHelper.isEmpty(errorText))
		{
			Map<String, String> validationMessages = ActionMessagesManager.getFieldsValidationError(
					ActionMessagesManager.COMMON_BUNDLE, ActionMessagesManager.ERROR);
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
		return new ActionForward("");//� ������ ������ � nextUrl ������ ������ �� ����. ��� ��� �� �������� �� ���������� ��������
	}

}

