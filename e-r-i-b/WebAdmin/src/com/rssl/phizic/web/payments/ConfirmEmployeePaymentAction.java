package com.rssl.phizic.web.payments;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.EmployeeConfirmFormPaymentOperation;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmEmployeePaymentAction extends ConfirmDocumentAction
{
	protected static final String FORWARD_SHOW_CONFIRM = "ShowConfirm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirm", "preConfirm");
		map.put("button.edit", "edit");
		map.put("button.cancel", "cancelConfirm");
		map.put("button.acceptConfirm", "confirm");

		return map;
	}

	protected ConfirmFormPaymentOperation getConfirmOperation(HttpServletRequest request, ConfirmPaymentByFormForm frm) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(frm.getId(), new ChecksOwnersPaymentValidator());
		ConfirmFormPaymentOperation operation = createConfirmOperation(source);
		operation.initialize(source);
		operation.setStrategyType();
		operation.setConfirmStrategyTypeForDocument(operation.getStrategyType());

		return operation;
	}

	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source) throws BusinessException, BusinessLogicException
	{
		return createOperation(EmployeeConfirmFormPaymentOperation.class, getServiceName(source));
	}

	/**
	 * Возвращаем имя формы
	 * @param source источник
	 * @return имя формы
	 */
	protected String getServiceName(DocumentSource source)
	{
		String serviceName = DocumentHelper.getEmployeeServiceName(source);
		return serviceName != null ? serviceName : super.getServiceName(source);
	}

	protected void updateForm(ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm form) throws BusinessException, BusinessLogicException
	{
		super.updateForm(operation, form);

		ConfirmEmployeePaymentByFormForm frm = (ConfirmEmployeePaymentByFormForm) form;

		//при инициализации операции контекст уже проверен
		frm.setActivePerson(PersonContext.getPersonDataProvider().getPersonData().getPerson());
		frm.setConfirmStrategyType(operation.getStrategyType());
	}

	public ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmStrategyType currentType = operation.getStrategyType();
		if (currentType == ConfirmStrategyType.none)
		{
			return confirm(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		updateForm(operation, frm);
		return mapping.findForward(FORWARD_SHOW_CONFIRM);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmEmployeePaymentByFormForm frm = (ConfirmEmployeePaymentByFormForm) form;
		ConfirmFormPaymentOperation operation = getOperation(request);
		clearConfirmErrors(request, operation.getRequest());
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, getCompositeFieldValueSource(request, operation));
			if (!errors.isEmpty())
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				return start(mapping, form, request, response);
			}

			frm.setMetadataPath(operation.getMetadataPath());
			frm.setMetadata(operation.getMetadata());
			frm.setConfirmStrategyType(operation.getStrategyType());
			return doConfirm(mapping, operation, frm, request, response);
		}
		catch (TemporalBusinessException ignore)
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", "message.operation.not.available"), false), null);
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}

	/**
	 * Добавляем необходимые поля для конвертиварония.
	 * @param request реквест.
	 * @param operation операция.
	 * @return список полей.
	 * @throws BusinessException
	 */
	private FieldValuesSource getCompositeFieldValueSource(HttpServletRequest request, ConfirmFormPaymentOperation operation) throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		BusinessDocument payment = operation.getDocument();
		map.put(Constants.CONFIRM_PLASTIC_FORM_NAME_FIELD, payment.getFormName());
		map.put(Constants.CONFIRM_PLASTIC_EMPLOYEE_LOGIN_FIELD, new SimpleService().findById(CommonLogin.class, payment.getCreatedEmployeeLoginId()).getUserId());

		MapValuesSource mapValuesSource = new MapValuesSource(map);

		return new CompositeFieldValuesSource(new RequestValuesSource(request), mapValuesSource);
	}

	protected ActionForward doConfirm(ActionMapping mapping, ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			operation.confirm();
			resetOperation(request);

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
			return createNextStageDocumentForward(operation, false);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);

			return forwardShow(mapping, operation, frm, request, false);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			saveConfirmErrors(Collections.singletonList(e.getMessage()), request, operation.getRequest());
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(currentRequest(), msgs);
			return forwardShow(mapping, operation, frm, request, false);
		}
		catch (TemporalBusinessException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(getResourceMessage("paymentsBundle", "message.operation.not.available"), false));

			operation.getDocument().incrementCountError();
			operation.save();

			return forwardShow(mapping, operation, frm, request, false);
		}
		catch (RedirectBusinessLogicException ex)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false), null);

			return createNextStageDocumentForward(operation, false);
		}
		catch (IndicateFormFieldsException e)
		{
			// Ошибки и сообщения, для которых должны быть подсвечены поля
			ActionMessages actionErrors = new ActionMessages();
			boolean isEmptyFieldMessages = true;
			for (Map.Entry<String, String> entry : e.getFieldMessages().entrySet())
			{
				actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getKey(), new ActionMessage(entry.getValue(), false)));
				if (!StringHelper.isEmpty(entry.getValue()) && isEmptyFieldMessages)
				{
					isEmptyFieldMessages = false;
				}
			}
			if (e.isError())
			{
				saveErrors(request, actionErrors);
			}
			else
			{
				// Если ни для одного из полей нет сообщения с описанием проблемы, добавляем общее сообщение в
				// блоке информационных сообщений
				if (isEmptyFieldMessages)
				{
					actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				}
				saveMessages(request, actionErrors);
			}
			return forwardShow(mapping, operation, frm, request, false);
		}
		catch (BusinessLogicException e) // ошибка логики
		{
			saveError(request, e);

			return forwardShow(mapping, operation, frm, request, false);
		}
		catch (SecurityException e) //упал сервис
		{
			log.error(e.getMessage(), e);
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Сервис временно недоступен, попробуйте позже", false), null);

			operation.getDocument().incrementCountError();
			operation.save();

			return forwardShow(mapping, operation, frm, request, false);
		}
		finally
		{
			saveStateMachineEventMessages(request, operation, false);
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmEmployeePaymentByFormForm frm = (ConfirmEmployeePaymentByFormForm) form;

		ConfirmFormPaymentOperation operation = getOperation(request);
		operation.edit();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		//при инициализации операции контекст уже проверен
		frm.setActivePerson(PersonContext.getPersonDataProvider().getPersonData().getPerson());
		frm.setConfirmStrategyType(ConfirmStrategyType.none);
		frm.setMetadataPath(operation.getMetadataPath());

		return createNextStageDocumentForward(operation, false);
	}

	@Override
	protected void addDocumentLimitsInfoMessages(ConfirmFormPaymentOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		// в АРМ сотрудника лимиты не проверяем, следовательно, сообщений нет
	}
}
