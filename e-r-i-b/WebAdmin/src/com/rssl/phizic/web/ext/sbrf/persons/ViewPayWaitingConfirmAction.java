package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CommissionsHelper;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ConfirmPayWaitingConfirmOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewPayWaitingConfirmAction extends ViewDocumentAction
{
	private static final String DEFAULT_SECURITY_OFFICER_TEXT = "������ �������� �������� �� �������������� ������������� �� ������� ��������� �������������";
	private static final String INFO_MESSAGE = "�������� ��������, �������� ���� ���������� ��������� � ����� ";
	private static final LimitService limitService = new LimitService();
	private static final ProfileService profileService = new ProfileService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirm", "confirm");
		return map;
	}

	private void processFraudAttributes(ActionForm form)
	{
		ViewPayWaitingConfirmForm frm = (ViewPayWaitingConfirmForm)form;
		BusinessDocument document = frm.getDocument();

		if (document.isFraudReasonForAdditionalConfirm())
		{
			String text = document.getSecurityOfficerText();
			if (StringHelper.isEmpty(text))
			{
				text = DEFAULT_SECURITY_OFFICER_TEXT;
			}
			saveError(currentRequest(), new ActionMessage(text, false));
		}
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (checkAccess(ConfirmPayWaitingConfirmOperation.class))
		{
			saveOperation(request, createOperation(ConfirmPayWaitingConfirmOperation.class));
		}

		ActionForward forward = super.start(mapping, form, request, response);

		processFraudAttributes(form);

		return forward;
	}


	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException,BusinessLogicException
	{
		ViewDocumentOperation operation = createOperation("ViewPayWaitingConfirmOperation", "ConfirmPaymentByCallCenter");
		operation.initialize(new ExistingSource(frm.getId(), new NullDocumentValidator()));
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewPayWaitingConfirmForm frm = (ViewPayWaitingConfirmForm) form;
		ViewDocumentOperation op = (ViewDocumentOperation) operation;

		super.updateFormData(operation, form);
		frm.setActivePerson((ActivePerson) op.getOwner());
	}

	protected boolean isAutoPaymentAllowed(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		return false;
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ViewPayWaitingConfirmForm frm = (ViewPayWaitingConfirmForm) form;
		ConfirmPayWaitingConfirmOperation operation = getOperation(request);

		try
		{
			operation.initialize(new ExistingSource(frm.getId(), new NullDocumentValidator()));

			//��������� �������������� ����� �����.
			FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, getAdditionalForm(operation));
			if (!processor.process())
			{
				saveErrors(request, processor.getErrors());
				return start(mapping, form, request, response);
			}

			Map<String, Object> result = processor.getResult();
			ActionMessage msg = checkAdditionalData(operation, result);
			if (msg != null)
			{
				saveError(request, msg);
				return start(mapping, form, request, response);
			}

			operation.confirm();

			resetOperation(request);
			saveStateMachineEventMessages(request, operation, false);
			additionalConfirmAction(request, operation, result);
			addInfoMessage(request, operation);
			// ����� ������������� ��������� �� ��� �� �����
			return super.start(mapping, form, request, response);
		}
		catch (BusinessDocumentLimitException e)
		{
			String errorMessage = e.getMessage();
			String defaultMessage = "�������� ����� �� ���������� ��������, ���������� �������� ����������.";
			saveError(request, StringHelper.isEmpty(errorMessage) ? defaultMessage : errorMessage);
			return start(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return start(mapping, form, request, response);
		}
		catch (InactiveExternalSystemException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return start(mapping, form, request, response);
		}
		catch (PostConfirmCalcCommission ignore)
		{
			BusinessDocument document = operation.getDocument();
			new DbDocumentTarget().save(document);
			String commissionMessage = CommissionsHelper.getCommissionMessage(MoneyFunctions.getFormatAmount(((BusinessDocumentBase) document).getCommission()), ((BusinessDocumentBase) document).getTariffPlanESB(), operation.getCurrentPerson());
			saveMessage(request, commissionMessage);
			return start(mapping, form, request, response);
		}
	}

	private void addInfoMessage(HttpServletRequest request, ConfirmPayWaitingConfirmOperation operation) throws BusinessException, BusinessLogicException
	{
		State state = operation.getDocument().getState();
		// ���� �� ��������������� ����� �� ������� ���������
		if("DELAYED_DISPATCH".equals(state.getCode()))
		{
			saveMessage(request, INFO_MESSAGE + DateHelper.formatDateToStringWithPoint(operation.getNextWorkDay()));
		}
	}

	/**
	 * �������������� ��������� ����� �����.
	 */
	private ActionMessage checkAdditionalData(ConfirmPayWaitingConfirmOperation operation, Map<String, Object> result) throws BusinessException, BusinessLogicException
	{
		String reasonForAdditionalConfirm = operation.getDocument().getReasonForAdditionalConfirm();
		Boolean isIMSIChecked = (Boolean) result.get(ViewPayWaitingConfirmForm.IMSI_CHECK_FIELD);
		//���� �������� ����� �� ������������� ���������� ���������� �������� �� ����� IMSI.
		if (StringHelper.isNotEmpty(reasonForAdditionalConfirm) && reasonForAdditionalConfirm.equals(RestrictionType.IMSI.name()) && !isIMSIChecked)
		{
			return new ActionMessage(String.format("����������, �������� ����������� �������� IMSI ��� ���������� ������ �������� ������� � ���������� ������ � ���� �%s�.", getResourceMessage("personsBundle", "label.changePersonIMSIConfirm")), false);
		}

		boolean isCreateTemplate = BooleanUtils.toBoolean((Boolean) result.get(ViewPayWaitingConfirmForm.CREATE_TEMPLATE_FIELD));
		//� ������ ���� ������� �������� �������������� �������. ���������� �������� �������.
		if (!operation.getDocument().isByTemplate() && isCreateTemplate)
		{
			String templateName = (String) result.get(ViewPayWaitingConfirmForm.TEMPLATE_NAME_FIELD);
			TemplateDocument existingTemplate = TemplateDocumentService.getInstance().findByTemplateName(operation.getCurrentPerson().asClient(), templateName);
			if (existingTemplate != null)
			{
				return new ActionMessage(String.format("������ �%s� ��� ������ � �������. ���������� ������� ������ �������� �������.", templateName), false);
			}
		}
		return null;
	}

	/**
	 * �������������� �������� ����� �������������� ������� �����������.
	 */
	private void additionalConfirmAction(HttpServletRequest request, ConfirmPayWaitingConfirmOperation operation, Map<String, Object> result)
	{
		String documentState = operation.getDocument().getState().getCode();
		StringBuilder messageBuilder = new StringBuilder();
		boolean isConfirmTemplate = BooleanUtils.toBoolean((Boolean) result.get(ViewPayWaitingConfirmForm.CONFIRM_TEMPLATE_FIELD));
		boolean isCreateTemplate = BooleanUtils.toBoolean((Boolean) result.get(ViewPayWaitingConfirmForm.CREATE_TEMPLATE_FIELD));
		//��������� �������������� �������� �� ������������� ������ ���� �������� "���������".
		if ("EXECUTED".equals(documentState) || "DISPATCHED".equals(documentState))
		{
			messageBuilder.append("�������� ������� ���������");
			try
			{
				if(isConfirmTemplate || isCreateTemplate)
				{
					//�������� ����������� �������������� ����� ��� ��������� ����������
					List<Limit> limits = limitService.findLimits((Department) operation.getDocument().getDepartment(), LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS, ChannelType.INTERNET_CLIENT, null, Status.ACTIVE, operation.getCurrentPerson().getSecurityType());
					if (limits.size() > 1)
						throw new BusinessException("��� ������������� departmentId = " + ((Department) operation.getDocument().getDepartment()).getId() + " ������� ������ ������ ��������� ��������� ����������� ��������������� ������ ��� ��������� ����������");

					//���� ������������ ������ �� ������� � ������� ������������� ������� �� ��������� ������ � �������������, ���� ������ ��������(WAIT_CONFIRM_TEMPLATE).
					if (operation.getDocument().isByTemplate() && isConfirmTemplate)
					{
						operation.confirmTemplate();
						if (limits.size() > 0)
							messageBuilder.append(String.format(". ����� �� ������� �%s� �������� �� %s ������ � ����.", operation.getTemplate().getTemplateInfo().getName(), limits.get(0).getAmount().getDecimal()));
						else
							messageBuilder.append(". ������ �� �������� ������� �����������.");
					}
					//���� �������� ������� � ������� - �������� �������������� �������
					else if (isCreateTemplate)
					{
						String templateName = (String) result.get(ViewPayWaitingConfirmForm.TEMPLATE_NAME_FIELD);
						operation.createTemplate(templateName, operation.getDocument());
						if (limits.size() > 0)
							messageBuilder.append(String.format(", � �� ��� ������ ������������� ������ � ��������� �%s� � ��������� ������� �� �������� � ������� %s ������ � ����.", templateName, limits.get(0).getAmount().getDecimal()));
						else
							messageBuilder.append(String.format(", � �� ��� ������ ������������� ������ � ��������� �%s�.", templateName));
					}
				}
				else
					messageBuilder.append('.');
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
				if (isCreateTemplate)
					messageBuilder.append(". ������ �� �������� �� ������ �� ����������� ��������.");
				else if (isConfirmTemplate)
					messageBuilder.append(". ������ �� �������� �� ����������� �� ����������� ��������.");
			}
		}
		else
		{
			messageBuilder.append("�������� ������������, �� �� ��������� �� ����������� ��������.");
			if (isCreateTemplate)
				messageBuilder.append(" ������ �� �������� �� ������.");
			else if (isConfirmTemplate)
				messageBuilder.append(" ������ �� �������� �� �����������.");
		}

		saveMessage(request, messageBuilder.toString());
	}

	private Form getAdditionalForm(ConfirmPayWaitingConfirmOperation operation)
	{
		return operation.getDocument().isByTemplate() ? ViewPayWaitingConfirmForm.CONFIRM_TEMPLATE_FORM : ViewPayWaitingConfirmForm.CREATE_TEMPLATE_FORM;
	}

}
