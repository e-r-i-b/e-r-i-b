package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.SmsDocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.EditSmsTemplateOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.MobileBankGetServiceInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.GetPaymentServiceInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.GetServiceProviderFieldsOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ServicePaymentInfoSource;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.ProviderFormEditor;
import com.rssl.phizic.web.util.ActionForwardHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 13.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
public class CreateSmsTemplateAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_SAVE = "Save";

	///////////////////////////////////////////////////////////////////////////

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.search", "search");
		map.put("button.save", "save");
		return map;
	}

	private GetPaymentServiceInfoOperation createPaymentInfoOperation(SmsTemplateForm frm)
			throws BusinessLogicException, BusinessException
	{
		MobileBankGetServiceInfoOperation operation = createOperation("GetMobileBankServiceInfoOperation");
		if(frm.getSelectedTemplateId() == null)
		{
			addLogParameters(
				new SimpleLogParametersReader(
						"�������� ������� ��� ����������",
						"id ���������� �����",
						frm.getRecipient()
				));
			operation.initialize(
					frm.getService(),
					frm.getPhoneCode(),
					frm.getCardCode(),
					frm.getRecipient(),
					frm.getFields()
			);
		}
		else
		{
			addLogParameters(
				new SimpleLogParametersReader(
						"�������� �� ������� ������",
						"id �������",
						frm.getSelectedTemplateId()
				));
			operation.initialize(
					frm.getSelectedTemplateId(),
					frm.getPhoneCode(),
					frm.getCardCode()
			);
		}
		return operation;
	}

	protected GetServiceProviderFieldsOperation createGetProviderFieldsOperation(Long providerId)
			throws BusinessLogicException, BusinessException
	{
		GetServiceProviderFieldsOperation operation =
				createOperation("GetServiceProviderFieldsOperation", "RurPayJurSB");
		operation.initialize(providerId);
		operation.setMobilebank(true);
		return operation;
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		GetPaymentServiceInfoOperation operation = createPaymentInfoOperation(frm);
		return doStart(mapping, frm, operation);
	}

	private ActionForward doStart(ActionMapping mapping, SmsTemplateForm frm, GetPaymentServiceInfoOperation operation)
			throws BusinessException, BusinessLogicException
	{
		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(SmsTemplateForm frm, GetPaymentServiceInfoOperation operation)
			throws BusinessException, BusinessLogicException
	{
		ProviderFormEditor formEditor = new ProviderFormEditor(operation);
		ServicePaymentInfoSource source = ((MobileBankGetServiceInfoOperation)operation).getPaymentInfoSource();
		formEditor.setProviderId(source.getProviderId());
		formEditor.setKeyFields(new HashMap(source.getKeyFields()));
		formEditor.updateForm(frm);
	}

	/**
	 * ������������ ����� �� ��������� ������ �����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		GetPaymentServiceInfoOperation operation = createPaymentInfoOperation(frm);
		ActionForward forward = doStart(mapping, frm, operation);

		if (forward != null && forward.getRedirect()) {
			// ��������� � ������ ��� �������� � ��� �����
			forward = MobilebankActionForwardHelper.appendRegistrationParams(
					forward, frm.getPhoneCode(), frm.getCardCode());
		}

		return forward;
	}

	/**
	 * ������������ ����� ������ "��������� SMS-������"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward save(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		GetPaymentServiceInfoOperation infoOperation = createPaymentInfoOperation(frm);
		try
		{
			Long providerId = frm.getRecipient();
			GetServiceProviderFieldsOperation operation = createGetProviderFieldsOperation(providerId);

			List<FieldDescription> fields = operation.getKeyFieldDescriptions();
			FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());

			Form editForm = SmsTemplateForm.getForm(fields);
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm, SmsDocumentValidationStrategy.getInstance());
			if (!processor.process())
			{
				saveErrors(request, processor.getErrors());
				return doStart(mapping, frm, infoOperation);
			}

			updateForm(frm, infoOperation);

			return doSave(mapping, frm);
		}
		catch (BusinessLogicException ex)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(request, errors);
			return doStart(mapping, frm, infoOperation);
		}
	}

	private ActionForward doSave(ActionMapping mapping, ActionForm frm)
			throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm form = (SmsTemplateForm) frm;

		// 1. �������� ����������
		String phoneCode = form.getPhoneCode();
		if (StringHelper.isEmpty(phoneCode))
			throw new BusinessException("�� ������ ��� ������ ��������");

		String cardCode = form.getCardCode();
		if (StringHelper.isEmpty(cardCode))
			throw new BusinessException("�� ������ ��� ������ �����");

		Long providerId = form.getRecipient();
		if (providerId == null)
			throw new BusinessException("�� ������ ID ���������� �����");

		FieldDescription keyField = getProviderKeyField(form);
		if (keyField == null)
			throw new BusinessException("�� ������� �������� ���� ���������� �����");

		// �������� ��������� ����, ������� ��� ������������
		String keyFieldValue = (String)form.getField(keyField.getExternalId());
		if (StringHelper.isEmpty(keyFieldValue))
			throw new BusinessException("�� ������� �������� ��������� ���� ���������� �����");

		try
		{
			// 2. ���������� ������� ������������� (���������� ��� ��)
			EditSmsTemplateOperation operation = createOperation("CreateMobileBankSmsTemplateOperation");
			operation.initializeNewAdditiveUpdate(phoneCode, cardCode, form.getRecipient(), keyFieldValue);
			if (operation.getNewSmsCommands().size() > 1)
				throw new BusinessLogicException("������ ��� ������ ������� ���������� ��� ����������.");
			
			operation.saveUpdate();
			addLogParameters(new SimpleLogParametersReader("����������� ������ ���-�������","id �������",operation.getUpdateId()));
			ActionForward forward = mapping.findForward(FORWARD_SAVE);
			forward = ActionForwardHelper.appendParam(
					forward, "updateId", operation.getUpdateId().toString());
			forward = MobilebankActionForwardHelper.appendRegistrationParams(
					forward, form.getPhoneCode(), form.getCardCode());
			return forward;
		}
		catch (SecurityLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	private FieldDescription getProviderKeyField(SmsTemplateForm form)
			throws BusinessException, BusinessLogicException
	{
		log.debug("����������� ��������� ���� ��� ���������� " + form.getRecipient());

		GetServiceProviderFieldsOperation operation = createGetProviderFieldsOperation(form.getRecipient());
		List<FieldDescription> keyFields = operation.getKeyFieldDescriptions();

		if (CollectionUtils.isEmpty(keyFields))
			throw new BusinessException("�� ������� �������� ���� " +
					"��� ���������� ����� ���������� �����. " +
					"ID ����������: " + form.getRecipient());

		if (keyFields.size() > 1)
			throw new BusinessException("��� ���������� ����� ���������� ����� " +
					"������� ����� ������ ��������� ����. " +
					"���������, ����� �� ��� ������������ ��� ������ � ��������� SMS-��������. " +
					"ID ����������: " + form.getRecipient());

		FieldDescription keyField = keyFields.iterator().next();
		log.debug("�������� ���� ����������: " +
				"ID = " + keyField.getId() + "; " +
				"Name = " + keyField.getName()  + "; " +
				"ExternalID = " + keyField.getExternalId() + ";"
		);
		return keyField;
	}
}
