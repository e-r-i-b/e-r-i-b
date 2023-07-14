package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.doc.FieldInitalValuesSource;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmRequest;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.ConfirmMobileBankRegistrationOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.EditMobileBankRegistrationOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.StartMobileBankRegistrationOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.ViewMobileBankRegistrationOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import static com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.ActionForwards.*;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 10.09.2012
 * @ $Author$
 * @ $Revision$
 */
class RegistrationWizard extends RegistrationWizardBase
{
	RegistrationWizard(OperationalActionBase action)
	{
		super(action);
	}

	protected StartMobileBankRegistrationOperation createStartOperation() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		StartMobileBankRegistrationOperation operation = createOperation(StartMobileBankRegistrationOperation.class);
		try
		{
			operation.initialize();
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	protected EditMobileBankRegistrationOperation createEditOperation() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		EditMobileBankRegistrationOperation operation = createOperation(EditMobileBankRegistrationOperation.class);
		try
		{
			operation.initializeNew();
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	protected ConfirmMobileBankRegistrationOperation createConfirmOperation(Long id) throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		ConfirmMobileBankRegistrationOperation operation = createOperation(ConfirmMobileBankRegistrationOperation.class);
		try
		{
			operation.initialize(id);
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	protected ViewMobileBankRegistrationOperation createViewOperation(Long id) throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		ViewMobileBankRegistrationOperation operation = createOperation(ViewMobileBankRegistrationOperation.class);
		try
		{
			operation.initialize(id);
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	ActionForward skipRegistration(ActionMapping mapping)
	{
		addLogParameters(new SimpleLogParametersReader("Подключение отклонено пользователем"));
		return mapping.findForward(FORWARD_SKIP);
	}

	ActionForward completeRegistration(ActionMapping mapping)
	{
		addLogParameters(new SimpleLogParametersReader("Подключение оформлено пользователем"));
		return mapping.findForward(FORWARD_COMPLETE);
	}

	///////////////////////////////////////////////////////////////////////////
	// Первый шаг процедуры подключения МБ: предложение подключиться, выбор тарифа

	ActionForward startRegistration(ActionMapping mapping, ActionForm form) throws BusinessException
	{
		StartRegistrationForm frm = (StartRegistrationForm) form;
		StartMobileBankRegistrationOperation operation = createStartOperation();

		updateStartForm(mapping, frm, operation);

		if (!frm.isCanRepeatClaim())
			addLogParameters(new SimpleLogParametersReader("Проверка возможности подключения", "подключение не доступно, т.к. с предыдущей попытки прошло недостаточно времени"));
		else if (!frm.isPhoneAvailable())
			addLogParameters(new SimpleLogParametersReader("Проверка возможности подключения", "подключение не доступно, т.к. в анкете не указан либо указан некорректно номер моб.т."));
		else if (!frm.isCardsAvailable())
			addLogParameters(new SimpleLogParametersReader("Проверка возможности подключения", "подключение не доступно, т.к. нет подходящих для подключения карт"));
		else addLogParameters(new SimpleLogParametersReader("Проверка возможности подключения", "подключение доступно"));

		return mapping.findForward(FORWARD_START);
	}

	private void updateStartForm(ActionMapping mapping, StartRegistrationForm form, StartMobileBankRegistrationOperation operation)
	{
		ActionForward next = mapping.findForward(FORWARD_NEXT);
		form.setCanRepeatClaim(operation.canRepeatClaim());
		form.setPreviousClaim(operation.getPreviousClaim());
		form.setPhoneAvailable(operation.isPhoneAvailable());
		form.setCardsAvailable(operation.isCardsAvailable());
		form.setNextActionPath(next.getPath());
	}

	///////////////////////////////////////////////////////////////////////////
	// Второй шаг процедуры подключения МБ: заполнение заявки

	ActionForward startRegistrationEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		EditRegistrationForm frm = (EditRegistrationForm) form;
		EditMobileBankRegistrationOperation operation = createEditOperation();

		Map<String, String> emptyMap = Collections.emptyMap();
		FieldValuesSource requestValuesSource = new RequestValuesSource(request);
		FieldValuesSource initialValuesSource = new FieldInitalValuesSource(getEditLogicForm(operation), emptyMap);
		FieldValuesSource valuesSource = new CompositeFieldValuesSource(requestValuesSource, initialValuesSource);

		updateEditForm(mapping, frm, operation, valuesSource);
		addLogParameters(new SimpleLogParametersReader("Параметры подключения", "тариф", frm.getField(EditRegistrationForm.FIELD_TARIFF)));

		return mapping.findForward(FORWARD_START);
	}

	ActionForward saveRegistrationEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		EditRegistrationForm frm = (EditRegistrationForm) form;
		EditMobileBankRegistrationOperation operation = createEditOperation();

		Form editLogicForm = getEditLogicForm(operation);
		addLogParameters(new FormLogParametersReader("Параметры подключения, выбранные пользователем", editLogicForm, frm.getFields()));
		
		// 1. Валидируем введённые данные
		FieldValuesSource validateValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(validateValuesSource, editLogicForm);
		if (!formProcessor.process())
		{
			ActionMessageHelper.saveErrors(request, formProcessor.getErrors());
			FieldValuesSource requestValuesSource = new RequestValuesSource(request);
			updateEditForm(mapping, frm, operation, requestValuesSource);
			return mapping.findForward(FORWARD_START);
		}

		// 2. Заполняем заявку
		Map<String, Object> fields = formProcessor.getResult();
		updateEditOperation(operation, fields);

		// 3. Сохраняем заявку
		operation.save();

		ActionForward forward = mapping.findForward(FORWARD_NEXT);
		//noinspection ReuseOfLocalVariable
		fields = new LinkedHashMap<String, Object>();
		fields.put("id", operation.getEntity().getId());
		if (StringHelper.isNotEmpty(frm.getReturnURL()))
			fields.put("returnURL", frm.getReturnURL());
		return redirect(forward, fields);
	}

	private void updateEditForm(ActionMapping mapping, EditRegistrationForm form, EditMobileBankRegistrationOperation operation, FieldValuesSource valuesSource) throws BusinessException
	{
		FormHelper.updateFormFields(form, valuesSource);

		form.setMaskedCards(operation.getMaskedCards());

		ActionForward back = mapping.findForward(FORWARD_BACK);
		if (StringHelper.isNotEmpty(form.getReturnURL()))
			back = redirect(back, "returnURL", form.getReturnURL());
		form.setBackActionPath(back.getPath());
	}

	private void updateEditOperation(EditMobileBankRegistrationOperation operation, Map<String, Object> fields)
	{
		operation.setTariff(MobileBankTariff.valueOf((String)fields.get(EditRegistrationForm.FIELD_TARIFF)));
		operation.setCard((String)fields.get(EditRegistrationForm.FIELD_CARD));
	}

	private Form getEditLogicForm(EditMobileBankRegistrationOperation operation)
	{
		return EditRegistrationForm.getEditForm(operation.getMaskedPhone(), operation.getMaskedCards());
	}

	///////////////////////////////////////////////////////////////////////////
	// Третий шаг процедуры подключения МБ: подтверждение заявки

	// Начало шага подтверждения
	ActionForward startRegistrationConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		ConfirmRegistrationForm frm = (ConfirmRegistrationForm) form;
		ConfirmMobileBankRegistrationOperation operation = createConfirmOperation(frm.getId());

		try
		{
			ConfirmationManager.sendRequest(operation);

			Exception warning = operation.getWarning();
			if (warning != null) {
				// если ошибки уже есть, предупреждения не показываем
				if (ActionMessageHelper.getErrors(request).isEmpty())
					ActionMessageHelper.saveError(request, warning);
			}
		}
		catch (BusinessLogicException e)
		{
            ActionMessageHelper.saveError(request, e);
		}

		updateConfirmForm(mapping, frm, operation);
		addLogParameters(new BeanLogParemetersReader("Параметры подключения", frm));

		return mapping.findForward(FORWARD_START);
	}

	// Пользователь нажал кнопку "Подтвердить", на выходе получил всплывающее окно для ввода пароля
	ActionForward preconfirmRegistration(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		ConfirmRegistrationForm frm = (ConfirmRegistrationForm) form;
		ConfirmMobileBankRegistrationOperation operation = createConfirmOperation(frm.getId());

		ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(operation.getConfirmableObject());
		operation.setConfirmRequest(confirmRequest);

		ConfirmHelper.clearConfirmErrors(confirmRequest);
		// Показываем popup-окно с предложением ввести пароль
		// даже если пароль не удалось сформировать и/или отправить
		confirmRequest.setPreConfirm(true); 
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm();

			List<String> messages = new ArrayList<String>();
			messages.add(ConfirmHelper.getPreConfirmString(preConfirmObject));
			String clientInfo = "";
			if (preConfirmObject != null)
				clientInfo = (String) preConfirmObject.getPreConfirmParam(SmsPasswordConfirmStrategy.CLIENT_SEND_MESSAGE_KEY);
			if (!StringHelper.isEmpty(clientInfo))
				messages.add(clientInfo);

			for(String message : messages)
				confirmRequest.addMessage(message);

			confirmRequest.setPreConfirm(true);
			if (confirmRequest instanceof SmsPasswordConfirmRequest)
					((SmsPasswordConfirmRequest)confirmRequest).setRequredNewPassword(false);
		}
		catch (SecurityException e)
		{
            ActionMessageHelper.saveError(request, e);
		}
		catch (SecurityLogicException e)
		{
			confirmRequest.resetMessages();
			ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(SecurityMessages.translateException(e)));
		}
		catch (BusinessLogicException e)
		{
			confirmRequest.resetMessages();
			ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(SecurityMessages.translateException(e)));
		}

		updateConfirmForm(mapping, frm, operation);
		addLogParameters(new BeanLogParemetersReader("Параметры подключения", frm));

		return mapping.findForward(FORWARD_START);
	}

	// Пользователь нажал кнопку "Подтвердить" во всплывающем окне с паролем
	ActionForward confirmRegistration(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		ConfirmRegistrationForm frm = (ConfirmRegistrationForm) form;
		ConfirmMobileBankRegistrationOperation operation = createConfirmOperation(frm.getId());

		// 2. Подтверждаем заявку
		ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(operation.getConfirmableObject());
		ConfirmHelper.clearConfirmErrors(confirmRequest);
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (errors.isEmpty())
			{
				operation.confirm();
			}
			else
			{
				operation.getRequest().setErrorMessage(errors.get(0));
			}

			ActionMessageHelper.saveSessionMessage(request, operation.formatGoodNewsMessage());
		}
		catch (BusinessException e)
		{
			ActionMessageHelper.saveSessionMessage(request, e.getMessage());
		}
		catch (SecurityException e)
		{
			ActionMessageHelper.saveSessionMessage(request, e.getMessage());
		}
		catch (SecurityLogicException e)
		{
			updateConfirmForm(mapping, frm, operation);
			operation.getRequest().setErrorMessage(e.getMessage());

			addLogParameters(new BeanLogParemetersReader("Параметры подключения", frm));
			return mapping.findForward(FORWARD_START);
		}
		catch (InactiveExternalSystemException e)
		{
			ActionMessageHelper.saveSessionMessage(request, e.getMessage());
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveSessionMessage(request, e.getMessage());
		}

		return mapping.findForward(FORWARD_NEXT);
	}

	private void updateConfirmForm(ActionMapping mapping, ConfirmRegistrationForm form, ConfirmMobileBankRegistrationOperation operation) throws BusinessException
	{
		form.setTariff(operation.getTariff());
		form.setMaskedPhone(operation.getMaskedPhone());
		form.setMaskedCard(operation.getMaskedCard());

		form.setConfirmRequest(operation.getRequest());

		ActionForward back = mapping.findForward(FORWARD_BACK);
		Map<String, Object> fields = new LinkedHashMap<String, Object>();
		fields.put(EditRegistrationForm.FIELD_TARIFF, operation.getTariff());
		fields.put(EditRegistrationForm.FIELD_PHONE, operation.getMaskedPhone());
		fields.put(EditRegistrationForm.FIELD_CARD, operation.getMaskedCard());
		if (StringHelper.isNotEmpty(form.getReturnURL()))
			fields.put("returnURL", form.getReturnURL());
		back = redirect(back, fields);
		form.setBackActionPath(back.getPath());

		ActionForward confirm = mapping.findForward(FORWARD_CONFIRM);
		form.setConfirmActionPath(confirm.getPath());

		ActionForward next = mapping.findForward(FORWARD_NEXT);
		//noinspection ReuseOfLocalVariable
		fields = new LinkedHashMap<String, Object>();
		fields.put("id", operation.getId());
		if (StringHelper.isNotEmpty(form.getReturnURL()))
			fields.put("returnURL", form.getReturnURL());
		next = redirect(next, fields);
		form.setNextActionPath(next.getPath());
	}

	///////////////////////////////////////////////////////////////////////////
	// Четвёртый шаг процедуры подключения МБ: просмотр статуса подключения

	ActionForward viewRegistrationStatus(ActionMapping mapping, ActionForm form) throws BusinessException
	{
		ViewRegistrationForm frm = (ViewRegistrationForm) form;
		ViewMobileBankRegistrationOperation operation = createViewOperation(frm.getId());

		updateViewForm(frm, operation);
		addLogParameters(new BeanLogParemetersReader("Параметры подключения", frm));

		return mapping.findForward(FORWARD_START);
	}

	private void updateViewForm(ViewRegistrationForm form, ViewMobileBankRegistrationOperation operation) throws BusinessException
	{
		form.setTariff(operation.getTariff());
		form.setMaskedPhone(operation.getMaskedPhone());
		form.setMaskedCard(operation.getMaskedCard());
	}
}
