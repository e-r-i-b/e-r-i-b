package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthStatusType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.csa.IncognitoService;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.account.*;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.operations.userprofile.*;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class SetupSecurityAction extends EditUserProfileActionBase
{
	private static final String FORWARD_LOGOFF = "Logoff";
	private static final String FORWARD_LIST = "List";
	protected static final String FORWARD_SUCCESS = "Success";
	protected static final String FORWARD_NEW_SHOW = "NewShow";
	private static final String PRECONFIRM_MESSAGE = ".settings.security.message";
	private static final String INFO_MESSAGE = "В настоящее время изменение настройки видимости по %s недоступно. Повторите попытку позднее";
	private static final String EMPTY_CHANGE_MESSAGE = "Вы не внесли никаких изменений в настройки видимости продуктов.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("button.save", "save");
		keys.put("button.saveConfirmSettings", "saveConfirmSettings");
		keys.put("button.password.new", "newPassword");
		keys.put("button.backToEdit", "backToEdit");
		keys.put("button.confirm", "confirm");
		keys.put("button.confirmSMS", "changeToSMS");
		keys.put("button.confirmPush", "changeToPush");
		keys.put("button.confirmCap", "changeToCap");
		keys.put("button.nextStage", "doNextStage");
		keys.put("button.saveLogin", "saveLogin");
		keys.put("button.savePassword", "savePassword");
		keys.put("button.saveIPasPassword", "saveIPasPassword");
		keys.put("button.saveIncognitoSetting", "saveIncognitoSetting");

		keys.put("button.saveShowInSystem", "saveShowInSystem");
		keys.put("button.saveShowInES", "saveShowInES");
		keys.put("button.saveShowInMobile", "saveShowInMobile");
		keys.put("button.saveShowInSocial", "saveShowInSocial");
		keys.put("button.saveShowInErmb", "saveShowInErmb");

		keys.put("button.preConfirmSystemSMS", "preConfirmSystemSMS"); //действие на получение смс-пароля для изменения настроек видимости в системе
		keys.put("button.preConfirmSystemPush", "preConfirmSystemPush"); //действие на получение push-пароля для изменения настроек видимости в системе
		keys.put("button.preConfirmSystemCap", "preConfirmSystemCAP"); //действие на получение cap-пароля для изменения настроек видимости в системе
		keys.put("button.preConfirmESSMS", "preConfirmESSMS"); //действие на получение смс-пароля для изменения настроек видимости в УС
		keys.put("button.preConfirmESPush", "preConfirmESPush"); //действие на получение push-пароля для изменения настроек видимости в УС
		keys.put("button.preConfirmESCap", "preConfirmESCAP"); //действие на получение cap-пароля для изменения настроек видимости в УС
		keys.put("button.preConfirmMobileSMS", "preConfirmMobileSMS"); //действие на получение смс-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmMobilePush", "preConfirmMobilePush"); //действие на получение push-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmMobileCap", "preConfirmMobileCAP"); //действие на получение cap-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmSocialSMS", "preConfirmSocialSMS"); //действие на получение смс-пароля для изменения настроек видимости в соц. приложении
		keys.put("button.preConfirmSocialPush", "preConfirmSocialPush"); //действие на получение push-пароля для изменения настроек видимости в соц. приложении
		keys.put("button.preConfirmSocialCap", "preConfirmSocialCAP"); //действие на получение cap-пароля для изменения настроек видимости в соц. приложении
		keys.put("button.preConfirmErmbSMS", "preConfirmErmbSMS"); //действие на получение смс-пароля для изменения настроек видимости в смс-канале (ЕРМБ)
		keys.put("button.preConfirmErmbPush", "preConfirmErmbPush"); //действие на получение push-пароля для изменения настроек видимости в смс-канале (ЕРМБ)
		keys.put("button.preConfirmErmbCap", "preConfirmErmbCAP"); //действие на получение cap-пароля для изменения настроек видимости в смс-канале (ЕРМБ)

		keys.put("button.confirmSystem", "confirmSystem"); //подтверждение  изменения настроек видимости в системе
		keys.put("button.confirmES", "confirmES");      //подтверждение  изменения настроек видимости в УС
		keys.put("button.confirmMobile", "confirmMobile");  //подтверждение  изменения настроек видимости в в iPhone/iPad
		keys.put("button.confirmSocial", "confirmSocial");  //подтверждение  изменения настроек видимости в соц. приложении
		keys.put("button.confirmErmb", "confirmErmb");  //подтверждение  изменения настроек видимости в смс-канале (ЕРМБ)

		keys.put("button.saveTemplates", "saveTemplates");
		keys.put("asyncTemplateTable", "showTemplateTable");
		return keys;
	}

	private void updateFormData(SetupSecurityForm form, SetupSecurityOperation operation) throws BusinessException
	{
		PersonBase person = operation.getPerson();
		if (!PersonHelper.availableNewProfile())
			form.setField("mobilePhone", PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhones()));
		form.setField("email", person.getEmail());

		form.setField("oneTimePassword", operation.isOneTimePassword());
		form.setField("confirmType", operation.getUserConfirmType());

		PasswordValidationConfig passwordConfig = ConfigFactory.getConfig(PasswordValidationConfig.class);
		form.setMinLoginLength(passwordConfig.getMinimunLoginLength());
	}

	protected void updateOperationData(ConfirmableOperationBase op, Map<String,Object> values)
	{
		SetupSecurityOperation operation = (SetupSecurityOperation) op;
		operation.setOneTimePassword((Boolean)values.get("oneTimePassword"));
		operation.setUserConfirmType((String)values.get("confirmType"));
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IncognitoService.updateIncognitoPhones();
		SetupProductsSystemViewOperation operation = null;
		if (PersonHelper.availableNewProfile())
		{
			//инициализация Настройка видимости продуктов. Нужна для нового дизайна.
			operation = createOperation(SetupProductsSystemViewOperation.class);
			operation.initialize();
		}

		return doShow(operation, mapping, form, request, response);
	}

	private ActionForward doShow(SetupProductsSystemViewOperation operation, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;

		SetupSecurityOperation operationSetup = createOperation(SetupSecurityOperation.class);
		operationSetup.initialize(AuthenticationContext.getContext());

		if (PermissionUtil.impliesOperation("IncognitoSettingOperation", "IncognitoSetting"))
		{
			IncognitoSettingOperation incognitoOperation = createOperation(IncognitoSettingOperation.class);
			incognitoOperation.initialize(AuthenticationContext.getContext().getCSA_SID());
			frm.setField("incognitoSetting", incognitoOperation.getClientIncognito());
		}

		updateFormData(frm, operationSetup);
		return newShow(operation, mapping, form, request, response);
	}

	private ActionForward nextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupProductsSystemViewOperation operation = null;
		if (PersonHelper.availableNewProfile())
		{
			//инициализация Настройка видимости продуктов. Нужна для нового дизайна.
			operation = createOperation(SetupProductsSystemViewOperation.class);
			operation.initialize();
		}

		return newShow(operation, mapping, form, request, response);
	}

	private ActionForward newShow(SetupProductsSystemViewOperation operation, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (!PersonHelper.availableNewProfile())
			return mapping.findForward(FORWARD_SHOW);

		SetupSecurityForm frm = (SetupSecurityForm)form;

		//инициализация Настройка видимости шаблонов. Нужна для нового дизайна.
		EditClientTemplatesShowSettingsOperation operationTemplates = createOperation(EditClientTemplatesShowSettingsOperation.class);

		operationTemplates.initialize(CreationType.internet);
		updateFormTemplates(frm, operationTemplates);

		//инициализация Настройка видимости продуктов. Нужна для нового дизайна.
		if (operation == null)
			return mapping.findForward(FORWARD_NEW_SHOW);
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setDepoAccounts(operation.getClientDepoAccounts());
		frm.setImAccounts(operation.getClientIMAccounts());
		frm.setSecurityAccounts(operation.getClientSecurityAccounts());
		frm.setPfrLink(operation.getClientPfrLink());
		frm.setNewProductsShowInSms(operation.isNewProductShowInSms());
		if (operation.getClientPfrLink() != null)
			frm.setPfrLinkSelected(operation.getClientPfrLink().getShowInSystem());
		frm.setSNILS(PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS());

		ArrayList<String> selectedIds = new ArrayList<String>();
		ArrayList<String> selectedMobileIds = new ArrayList<String>();
		ArrayList<String> selectedSocialIds = new ArrayList<String>();
		ArrayList<String> selectedATMIds = new ArrayList<String>();
		ArrayList<String> selectedSMSIds = new ArrayList<String>();     /** тут нужен селект для социального **/
		for (AccountLink accountLink : frm.getAccounts())
		{
			if (accountLink.getShowInSystem())
				selectedIds.add(accountLink.getId().toString());
			if (accountLink.getShowInMobile())
				selectedMobileIds.add(accountLink.getId().toString());
            //if (accountLink.getShowInSocial())
            //    selectedSocialIds.add(accountLink.getId().toString()); TODO
			if (accountLink.isShowInATM())
				selectedATMIds.add(accountLink.getId().toString());
			if (accountLink.getShowInSms())
				selectedSMSIds.add(accountLink.getId().toString());
		}
		frm.setSelectedAccountIds(selectedIds.toArray(new String[0]));
		frm.setSelectedAccountMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedAccountSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedAccountESIds(selectedATMIds.toArray(new String[0]));
		frm.setSelectedAccountSMSIds(selectedSMSIds.toArray(new String[0]));

		selectedIds.clear();
		selectedMobileIds.clear();
		selectedSocialIds.clear();
		selectedATMIds.clear();

		for (CardLink cardLink : frm.getCards())
		{
			String cardLinkId =  cardLink.getId().toString();
			if (cardLink.getShowInSystem())
				selectedIds.add(cardLinkId);
			if (cardLink.getShowInMobile())
				selectedMobileIds.add(cardLinkId);
			if (cardLink.getShowInSocial())
				selectedSocialIds.add(cardLinkId);
			if (cardLink.isShowInATM())
				selectedATMIds.add(cardLinkId);
			if (cardLink.getShowInSms())
				selectedSMSIds.add(cardLinkId);
		}
		frm.setSelectedCardIds(selectedIds.toArray(new String[0]));
		frm.setSelectedCardMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedCardSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedCardESIds(selectedATMIds.toArray(new String[0]));
		frm.setSelectedCardSMSIds(selectedSMSIds.toArray(new String[0]));

		selectedIds.clear();
		selectedMobileIds.clear();
		selectedSocialIds.clear();
		selectedATMIds.clear();
		for (LoanLink loanLink : frm.getLoans())
		{
			String loanLinkId = loanLink.getId().toString();
			if (loanLink.getShowInSystem())
				selectedIds.add(loanLinkId);
			if (loanLink.getShowInMobile())
				selectedMobileIds.add(loanLinkId);
			if (loanLink.getShowInSocial())
				selectedSocialIds.add(loanLinkId);
			if (loanLink.isShowInATM())
				selectedATMIds.add(loanLinkId);
			if (loanLink.getShowInSms())
				selectedSMSIds.add(loanLinkId);

		}
		frm.setSelectedLoanIds(selectedIds.toArray(new String[0]));
		frm.setSelectedLoanMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedLoanSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedLoanESIds(selectedATMIds.toArray(new String[0]));
		frm.setSelectedLoanSMSIds(selectedSMSIds.toArray(new String[0]));

		selectedIds.clear();
		for (DepoAccountLink depoAccountLink : frm.getDepoAccounts())
		{
			if (depoAccountLink.getShowInSystem())
				selectedIds.add(depoAccountLink.getId().toString());
		}
		frm.setSelectedDepoAccountIds(selectedIds.toArray(new String[0]));

		selectedIds.clear();
		for (SecurityAccountLink securityAccountLink : frm.getSecurityAccounts())
		{
			if (securityAccountLink.getShowInSystem())
				selectedIds.add(securityAccountLink.getId().toString());
		}
		frm.setSelectedSecurityAccountIds(selectedIds.toArray(new String[0]));

		selectedIds.clear();
		selectedMobileIds.clear();
		selectedSocialIds.clear();
		selectedATMIds.clear();
		for (IMAccountLink imAccountLink : frm.getImAccounts())
		{
			if (imAccountLink.getShowInSystem())
				selectedIds.add(imAccountLink.getId().toString());
			if (imAccountLink.getShowInMobile())
				selectedMobileIds.add(imAccountLink.getId().toString());
			if (imAccountLink.getShowInSocial())
				selectedSocialIds.add(imAccountLink.getId().toString());
			if (imAccountLink.isShowInATM())
				selectedATMIds.add(imAccountLink.getId().toString());
		}
		frm.setSelectedIMAccountIds(selectedIds.toArray(new String[0]));
		frm.setSelectedIMAccountMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedIMAccountSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedIMAccountESIds(selectedATMIds.toArray(new String[0]));

		return mapping.findForward(FORWARD_NEW_SHOW);
	}

	public ActionForward showTemplateTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		filter(mapping, form, request, response);
		return mapping.findForward(FORWARD_START);
	}

	private ActionForward saveProductsView(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, ConfirmableOperationBase operation, SetupSecurityForm frm) throws Exception
	{
		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Обратите внимание, если Вы перейдете на другую страницу системы без " +
																					"подтверждения изменения настроек SMS-паролем, " +
																					"то Ваши изменения не будут сохранены в системе.", false));
		saveMessages(request, message);

		return newShow(null, mapping, frm, request, response);
	}

	private ActionForward saveBase(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, ConfirmableOperationBase operation, SetupSecurityForm frm) throws Exception
	{
		try
		{
			operation.resetConfirmStrategy();
			ConfirmationManager.sendRequest(operation);
			saveOperation(request,operation);

			frm.setConfirmableObject(operation.getConfirmableObject());
			frm.setConfirmStrategy(operation.getConfirmStrategy());
			if (StringHelper.isEmpty((String)frm.getField("confirmType")))
				frm.setField("confirmType", "sms");
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}

		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Обратите внимание, если Вы перейдете на другую страницу системы без " +
																					"подтверждения изменения настроек SMS-паролем, " +
																					"то Ваши изменения не будут сохранены в системе.", false));
		saveMessages(request, message);

		return nextStage(mapping, frm, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		SetupSecurityOperation operation = createOperation(SetupSecurityOperation.class);
		operation.initialize(authContext);

		SetupSecurityForm frm = (SetupSecurityForm)form;

		PersonBase person = operation.getPerson();
		frm.setField("mobilePhone", PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhones()));
		frm.setField("email", person.getEmail());

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(valuesSource, checkPushAccess());
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return start(mapping, form, request, response);
		}
		if(checkChanges(formProcessor.getResult(), operation))
		{
			updateOperationData(operation, formProcessor.getResult());
			frm.setConfirmName("settings");
			return saveBase(mapping, request, response, operation, frm);
		}
		else
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не внесли никаких изменений в настройки подтверждений в системе.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}
	}

	public ActionForward saveConfirmSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		SetupSecurityOperation operation = createOperation(SetupSecurityOperation.class);
		operation.initialize(authContext);

		SetupSecurityForm frm = (SetupSecurityForm)form;

		PersonBase person = operation.getPerson();
		frm.setField("mobilePhone", PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhones()));
		frm.setField("email", person.getEmail());

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(valuesSource, checkPushAccess());
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return start(mapping, form, request, response);
		}
		if(checkChanges(formProcessor.getResult(), operation))
		{
			updateOperationData(operation, formProcessor.getResult());
			frm.setConfirmName("settingsConfirm");
			return saveBase(mapping, request, response, operation, frm);
		}
		else
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не внесли никаких изменений в настройки подтверждений в системе.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}
	}


	public ActionForward saveLogin( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.CHANGE_LOGIN_FORM);
		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}

		ChangeClientLoginOperation operation = createOperation(ChangeClientLoginOperation.class);
		try
		{
			operation.initialize(
					AuthenticationContext.getContext().getCSA_SID(),
					(String)frm.getField("newLogin"));

		}
		catch (BusinessLogicException e)
		{
			String message = e.getMessage();
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			saveErrors(request, errors);
			return start(mapping, form, request, response);
		}

		frm.setConfirmName("login");

		return saveBase(mapping, request, response, operation, frm);
	}

	public ActionForward savePassword( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.CHANGE_PASSWORD_FORM);
		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}

		ChangeClientPasswordOperation operation = createOperation(ChangeClientPasswordOperation.class);
		try
		{
			operation.initialize(
					AuthenticationContext.getContext().getCSA_SID(),
					(String)frm.getField("oldPassword"),
					(String)frm.getField("newPassword"));
		}
		catch (BusinessLogicException e)
		{
			String message = e.getMessage();
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			saveErrors(request, errors);
			if (operation.isNeedLogoff())
				return mapping.findForward(FORWARD_LOGOFF);
			return start(mapping, form, request, response);
		}
		frm.setConfirmName("password");

		return saveBase(mapping, request, response, operation, frm);
	}

	public ActionForward saveIPasPassword( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.GENERATE_IPAS_PASSWORD_FORM);
		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}

		GenerateIPasPasswordOperation operation = createOperation(GenerateIPasPasswordOperation.class);
		try
		{
			operation.initialize((String) processor.getResult().get("oldPassword"));
			operation.checkOldPassword();
		}
		catch (BusinessLogicException e)
		{
			if (operation.isNeedLogoff())
				return mapping.findForward(FORWARD_LOGOFF);
			saveError(request, e.getMessage());
			return start(mapping, form, request, response);
		}

		frm.setConfirmName("ipasPassword");

		return saveBase(mapping, request, response, operation, frm);
	}

	public ActionForward saveIncognitoSetting( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.CHANGE_INCOGNITO_SETTING_FORM);
		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}

		IncognitoSettingOperation operation = createOperation(IncognitoSettingOperation.class);
		operation.initialize(AuthenticationContext.getContext().getCSA_SID(), (Boolean) processor.getResult().get("incognitoSetting"));

		frm.setConfirmName("incognitoSetting");

		return saveBase(mapping, request, response, operation, frm);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
	    SetupSecurityForm frm = (SetupSecurityForm) form;
		ConfirmableOperationBase operation = getOperation(request);
	    updateForm(operation, form);
	    try
	    {
		    FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
			FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(valuesSource, checkPushAccess());
			if (!formProcessor.process())
			{
				saveErrors(request, formProcessor.getErrors());
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_SHOW);
			}

		    updateOperationData(operation, formProcessor.getResult());
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));

			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));				
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_SHOW);
			}
			else
			{
				operation.confirm();
				if(isAjax())
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(operation.getSuccessfulMessage(), false), null);
				else
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(operation.getSuccessfulMessage(), false));
					saveMessages(request, message);
				}
				return doNextStage(mapping, form, request, response);
			}
		}
	   	catch (InactiveExternalSystemException e) // есть технический перерыв.
        {
	        operation.getRequest().setErrorMessage(e.getMessage());
			frm.setConfirmableObject(operation.getConfirmableObject());
	        return mapping.findForward(FORWARD_SHOW);
        }
	   	catch (SecurityLogicException e) // ошибка подтверждения. не закрываем окно подтверждения. даем возможность ввести смс еще раз.
        {
	        operation.getRequest().setErrorMessage(e.getMessage());
			frm.setConfirmableObject(operation.getConfirmableObject());
	        return mapping.findForward(FORWARD_SHOW);
        }
	    catch (BusinessLogicException e) // ошибка подтверждения. выходим из окна подтверждения. выводим сообщение
        {
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
	        frm.setConfirmableObject(operation.getConfirmableObject());
			return doNextStage(mapping, form, request, response);
        }
	    catch (BusinessException e) //упал сервис. выходим из окна подтверждения. выводим сообщение
	    {
			saveSessionError(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Сервис временно недоступен, попробуйте позже",false), null);
			frm.setConfirmableObject(operation.getConfirmableObject());
			return doNextStage(mapping, form, request, response);
	    }
    }

	protected Form getForm()
	{
		return SetupSecurityForm.EDIT_FORM;
	}

	/**
	 * Используется для редиректа с окна подтверждения одноразовым паролем, открытым через ajax, на следующий шаг.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmableOperationBase operation = getOperation(request);
		SetupSecurityForm frm = (SetupSecurityForm)form;
		clearFields(operation, frm);
		return start(mapping, form, request, response);
	}

	public ActionForward backToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;
		frm.setField("unsavedData", true);
		clearFields((ConfirmableOperationBase)getOperation(request),frm);
		return start(mapping, frm, request, response);
	}

	public ActionForward newPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.GENERATE_PASSWORD_FORM);
		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}

		GeneratePasswordOperation operation = createOperation(GeneratePasswordOperation.class);
		operation.initialize();

		operation.setPassword((String)frm.getField("password"));

		try
		{
			operation.generate();

			//Показываем сообщение, если не упали
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вам успешно отправлен пароль", false));
			saveMessages(request, msgs);
			frm.setField("password","");
		}
		catch (BusinessException e)
		{
			log.error("Невозможно получить новый пароль", e);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("userprofileBundle", "error.change.password"), false));
			saveErrors(request, errors);
		}
		catch (LogicException e)
		{
			Throwable cause = e.getCause();
			if (cause instanceof AuthGateLogicException)
			{
				AuthStatusType errorCode = ((AuthGateLogicException) cause).getErrCode();
				String message;
				if (errorCode == AuthStatusType.ERR_BADPSW  || errorCode == AuthStatusType.ERR_AGAIN)
					message = getResourceMessage("userprofileBundle", "error.change.password.badpas");
				else
				{
					log.error("Невозможно получить новый пароль, вернулось сообщение с неподходящим статусом", e);
					message = getResourceMessage("userprofileBundle", "error.change.password");
				}
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
				saveErrors(request, errors);
			}
			else
			{
				saveError(request, SecurityMessages.translateException(e));
			}
		}

		return start(mapping, form, request, response);
	}

	protected void updateForm(ConfirmableOperationBase op, ActionForm form) throws BusinessException{}

	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return changeTo(mapping, form, request, ConfirmStrategyType.sms);
	}

	public ActionForward changeToPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return changeTo(mapping, form, request, ConfirmStrategyType.push);
	}

	private ActionForward changeTo(ActionMapping mapping, ActionForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		ConfirmableOperationBase operation = getOperation(request);
		SetupSecurityForm frm   = (SetupSecurityForm) form;
		operation.setUserStrategyType(confirmStrategy);

		updateForm(operation, form);

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(valuesSource, checkPushAccess());
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}
		updateOperationData(operation, formProcessor.getResult());
		frm.setConfirmableObject(operation.getConfirmableObject());

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();

		ConfirmationManager.sendRequest(operation);
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(confirmStrategy, login, operation.getConfirmableObject()));
			operation.getRequest().setPreConfirm(true);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			operation.getRequest().setPreConfirm(true);
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmableOperationBase operation = getOperation(request);
		SetupSecurityForm frm   = (SetupSecurityForm) form;
        operation.setUserStrategyType(ConfirmStrategyType.cap);
        updateForm(operation, form);

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, getForm());
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}
		updateOperationData(operation, formProcessor.getResult());
        frm.setConfirmableObject(operation.getConfirmableObject());
        ConfirmationManager.sendRequest(operation);
        frm.setConfirmStrategy(operation.getConfirmStrategy());
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(true);
		if (confirmRequest.getAdditionInfo() != null)
		{
			for (String str : confirmRequest.getAdditionInfo())
			{
				if (!StringHelper.isEmpty(str))
					confirmRequest.addMessage(str);
			}
		}

		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return changeToSMS(mapping, frm, request, response);
		}
		if (currentType == ConfirmStrategyType.push)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return changeToPush(mapping, frm, request, response);
		}
		confirmRequest.addMessage(ConfirmHelper.getPreConfirmCapString());
		return mapping.findForward(FORWARD_SHOW);
	}

	private boolean checkChanges(Map<String, Object> values, SetupSecurityOperation operation)
	{
		// СМС паролем подтверждается изменение:
		// - одноразового пароля при входе
		// - способа доставки
		// - предпочтительного способа подтверждения

		if (values.get("oneTimePassword").equals(operation.isOneTimePassword())
					&& values.get("confirmType").equals(operation.getUserConfirmType()))
		{
			return false;
		}
		return true;
	}

	/**
	 * Чистит значения полей при смене логина и пароля.
	 * @param operation - текущая операция
	 * @param frm - форма изменения настроек безопасности.
	 */
	private void clearFields(ConfirmableOperationBase operation, SetupSecurityForm frm)
	{
		if(operation instanceof ChangeClientLoginOperation)
		{
			frm.setField("newLogin","");
			frm.setField("newLoginReplay","");
		}
		else if(operation instanceof ChangeClientPasswordOperation)
		{
			frm.setField("newPassword","");
			frm.setField("newPasswordReplay","");
			frm.setField("oldPassword","");
			frm.setField("noMaskPassword","");
		}
		else if(operation instanceof GenerateIPasPasswordOperation)
			frm.setField("oldPassword","");
	}

	/**
	 * Создание формы в соотвктствии с доступом клиента к Push-уведомлениям
	 * @param valuesSource - значения формы
	 * @param clientProfilePush - доступность услуги
	 * @return текущий FormProcessor
	 */
	private FormProcessor<ActionMessages, ?> getFormProcessor(FieldValuesSource valuesSource, boolean clientProfilePush)
	{
		Form form = clientProfilePush ? SetupSecurityForm.EDIT_FORM_WITH_PUSH
	                 : SetupSecurityForm.EDIT_FORM;
		return createFormProcessor(valuesSource, form);
	}

	/**
	 * Проверка доступа клиента к услуге Push-уведомлений
	 * @return доступность услуги
	 */
	private boolean checkPushAccess()
	{
		return PermissionUtil.impliesService("ClientProfilePush");
	}

	//сохранить настройки видимости продуктов в системе
	public ActionForward saveShowInSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSystemViewOperation operation = createOperation(SetupProductsSystemViewOperation.class);
		operation.initialize();
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),    frm.getSelectedCardIds(),
										  frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(), frm.getSelectedSecurityAccountIds(),
										  frm.getPfrLinkSelected());
		operation.resetConfirmStrategy();
		updateFormDataAccountsSystemView(operation, frm);
		frm.setField("pageType", "system");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);
		frm.setConfirmName("settingsViewProductsInSystem");
		return saveProductsView(mapping, request, response, operation, frm);
	}

	//сохранить настройки видимости продуктов в устройствах самообслуживания
	public ActionForward saveShowInES(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsRCSViewOperation operation = createOperation(SetupProductsRCSViewOperation.class);
		operation.initialize();
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());

		operation.resetConfirmStrategy();
		updateFormDataAccountsSystemView(operation, frm);
		frm.setField("pageType", "ES");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);
		frm.setConfirmName("settingsViewProductsInES");
		return saveProductsView(mapping, request, response, operation, frm);
	}

	//сохранить настройки видимости продуктов в iPhone/iPad
	public ActionForward saveShowInMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsMobileViewOperation operation = createOperation(SetupProductsMobileViewOperation.class);
		operation.initialize();
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		operation.resetConfirmStrategy();
		updateFormDataAccountsSystemView(operation, frm);
		frm.setField("pageType", "mobile");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		//если клиент только отключает видимость, то сообщение не отображаем
		if (operation.getStrategyType() != ConfirmStrategyType.none)
			setMessage(request);

		frm.setConfirmName("settingsViewProductsInMobile");
		saveOperation(request,operation);
		return saveProductsView(mapping, request, response, operation, frm);
	}

    //сохранить настройки видимости продуктов в социальных приложениях
    public ActionForward saveShowInSocial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        SetupSecurityForm frm = (SetupSecurityForm) form;
        SetupProductsSocialViewOperation operation = createOperation(SetupProductsSocialViewOperation.class);
        operation.initialize();
        operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
        operation.resetConfirmStrategy();
        updateFormDataAccountsSystemView(operation, frm);
        frm.setField("pageType", "social");

        if (operation.noChanges())
        {
            ActionMessages message = new ActionMessages();
            message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
            saveErrors(request, message);
            return start(mapping, form, request, response);
        }

        ConfirmationManager.sendRequest(operation);
        //если клиент только отключает видимость, то сообщение не отображаем
        if (operation.getStrategyType() != ConfirmStrategyType.none)
            setMessage(request);

        frm.setConfirmName("settingsViewProductsInSocial");
        saveOperation(request,operation);
        return saveProductsView(mapping, request, response, operation, frm);
    }

	//сохранить настройки видимости продуктов в смс-канале
	public ActionForward saveShowInErmb(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSMSViewOperation operation = createOperation(SetupProductsSMSViewOperation.class);
		operation.initialize();
		operation.updateResourcesSettings(frm.getSelectedAccountSMSIds(), frm.getSelectedCardSMSIds(), frm.getSelectedLoanSMSIds(), frm.isNewProductsShowInSms());
		operation.resetConfirmStrategy();
		updateFormDataAccountsSystemView(operation, frm);
		frm.setField("pageType", "SMS");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);

		frm.setConfirmName("settingsViewProductsInErmb");
		return saveProductsView(mapping, request, response, operation, frm);
	}

	public ActionForward confirmSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;
		SetupProductsSystemViewOperation operation = getOperation(request);
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),    frm.getSelectedCardIds(),
										  frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(),frm.getSelectedSecurityAccountIds(),
										  frm.getPfrLinkSelected());
		updateFormDataAccountsSystemView(operation,frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmES(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;
		SetupProductsRCSViewOperation operation = getOperation(request);
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());
		updateFormDataAccountsSystemView(operation,frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;
		SetupProductsMobileViewOperation operation = getOperation(request);
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		updatePersonInfoData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmSocial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;
        SetupProductsSocialViewOperation operation = getOperation(request);
		operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
		updatePersonInfoData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmErmb(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm)form;
		SetupProductsSMSViewOperation operation = getOperation(request);
		updatePersonInfoData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward preConfirmSystemSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmSystem(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.sms, new CallBackHandlerSmsImpl());
	}

	public ActionForward preConfirmSystemPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmSystem(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.push, new CallBackHandlerPushImpl());
	}

	private ActionForward preConfirmSystem(ActionMapping mapping, SetupSecurityForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy, CallBackHandler callBackHandler) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSystemViewOperation operation = getOperation(request);
		operation.setUserStrategyType(confirmStrategy);
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),  frm.getSelectedCardIds(),
										  frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(), frm.getSelectedSecurityAccountIds(),
										  frm.getPfrLinkSelected());
		updateFormDataAccountsSystemView(operation,frm);
        frm.setField("pageType", "system");
		preConfirmAction(request, operation, confirmStrategy + PRECONFIRM_MESSAGE, confirmStrategy);
        return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmSystemCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSystemViewOperation operation = getOperation(request);
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),  frm.getSelectedCardIds(),
				frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(),frm.getSelectedSecurityAccountIds(),
				frm.getPfrLinkSelected());
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "system");
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmESSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmES(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmESPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmES(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmES(ActionMapping mapping, SetupSecurityForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsRCSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(confirmStrategy);
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "ES");
		preConfirmAction(request, operation, confirmStrategy + PRECONFIRM_MESSAGE, confirmStrategy);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmESCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsRCSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "ES");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmMobileSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmMobile(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmMobilePush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmMobile(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmMobile(ActionMapping mapping, SetupSecurityForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsMobileViewOperation operation = getOperation(request);
		operation.setUserStrategyType(confirmStrategy);
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "mobile");
		preConfirmAction(request, operation, confirmStrategy + PRECONFIRM_MESSAGE, confirmStrategy);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmMobileCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsMobileViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "mobile");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

    public ActionForward preConfirmSocialSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
		return preConfirmSocial(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.sms);
    }

    public ActionForward preConfirmSocialPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
		return preConfirmSocial(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.push);
    }

	private ActionForward preConfirmSocial(ActionMapping mapping, SetupSecurityForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSocialViewOperation operation = getOperation(request);
		operation.setUserStrategyType(confirmStrategy);
		operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "social");
		preConfirmAction(request, operation, confirmStrategy + PRECONFIRM_MESSAGE, confirmStrategy);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmSocialCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
        SetupSecurityForm frm = (SetupSecurityForm) form;
        SetupProductsSocialViewOperation operation = getOperation(request);
        operation.setUserStrategyType(ConfirmStrategyType.cap);
        operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
        updateFormDataAccountsSystemView(operation,frm);
        frm.setField("pageType", "social");
        ConfirmationManager.sendRequest(operation);
        operation.getRequest().setPreConfirm(true);
        operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
        addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
        return mapping.findForward(FORWARD_LIST);
    }

	public ActionForward preConfirmErmbSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmErmb(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmErmbPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmErmb(mapping, (SetupSecurityForm) form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmErmb(ActionMapping mapping, SetupSecurityForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSMSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(confirmStrategy);
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "SMS");
		preConfirmAction(request, operation, confirmStrategy + PRECONFIRM_MESSAGE, confirmStrategy);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmErmbCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		SetupProductsSMSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		updateFormDataAccountsSystemView(operation,frm);
		frm.setField("pageType", "SMS");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	private ActionForward confirmSettings(SetupProductsSystemViewOperation operation, ActionMapping mapping, SetupSecurityForm frm, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_LIST);
			}
			else
			{
				// Сохранение делается при успешной валидации в методе saveConfirm
				operation.confirm();
				operation.sendSmsNotification(false);
				if(isAjax())
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
					else
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()), null);
				else
				{
					ActionMessages actionMessages = new ActionMessages();
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, (new ActionMessage("Данные успешно сохранены.", false)));
					else
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()));
					saveMessages(request, actionMessages);
				}
				return doNextStage(mapping, frm, request, response);
			}
		}
		catch (BusinessLogicException ble)
		{
			saveError(request, SecurityMessages.translateException(ble));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return doNextStage(mapping, frm, request, response);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_LIST);
		}
		catch (SecurityException e) //упал сервис
		{
			operation.getRequest().setErrorMessage("Сервис временно недоступен, попробуйте позже");
			frm.setConfirmableObject(operation.getConfirmableObject());
			return doNextStage(mapping, frm, request, response);
		}
		catch (InactiveExternalSystemException e) // ошибка неактивна внешняя система
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return doNextStage(mapping, frm, request, response);
		}
	}

	private void preConfirmAction(HttpServletRequest request, SetupProductsSystemViewOperation operation, String messageKey, ConfirmStrategyType confirmStrategy) throws BusinessException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
			operation.getRequest().setPreConfirm(true);
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(confirmStrategy, operation.getLogin(), operation.getConfirmableObject()));
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}
		catch (BusinessLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}
	}

	private ActionMessage getInfoMessage(List<AccountLink> links)
	{
		StringBuilder linkNames = new StringBuilder();
		for (AccountLink link : links)
		{
			linkNames.append("\"" + link.getName() + " " + link.getNumber() + "\"" + ", ");
		}
		linkNames = linkNames.delete(linkNames.length()-1, linkNames.length());
		return new ActionMessage(String.format(INFO_MESSAGE, linkNames), false);
	}

	private void updateFormDataAccountsSystemView(SetupProductsSystemViewOperation operation, SetupSecurityForm frm)  throws BusinessException
	{
		updatePersonInfoData(operation, frm);
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
	}

	private void updatePersonInfoData(SetupProductsSystemViewOperation operation, SetupSecurityForm frm)  throws BusinessException
	{
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setDepoAccounts(operation.getClientDepoAccounts());
		frm.setImAccounts(operation.getClientIMAccounts());
		frm.setSecurityAccounts(operation.getClientSecurityAccounts());
		frm.setPfrLink(operation.getClientPfrLink());
		frm.setSNILS(PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS());
	}


	private void setMessage(HttpServletRequest request)
	{
		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Обратите внимание, если Вы перейдете на другую страницу системы без " +
																						"подтверждения изменения настроек SMS-паролем, " +
																						"то Ваши изменения не будут сохранены в системе.", false));
		saveMessages(request, message);
	}

	/**
	 * для работы пагинации на странице, тк фильтра нет
	 */
	private void filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		EditClientTemplatesShowSettingsOperation operation = createOperation(EditClientTemplatesShowSettingsOperation.class);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();

			operation.initialize(CreationType.valueOf((String) result.get(SetupSecurityForm.CHANNEL_TYPE_NAME_FIELD)));
			updateFormTemplates(frm, operation);
		}
		else
		{
			operation.initialize(CreationType.internet);
			saveErrors(request, processor.getErrors());
			updateFormTemplates(frm, operation);
		}
	}

	public ActionForward saveTemplates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupSecurityForm frm = (SetupSecurityForm) form;
		EditClientTemplatesShowSettingsOperation operation = createOperation(EditClientTemplatesShowSettingsOperation.class);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), SetupSecurityForm.FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String changedIds = (String) result.get(SetupSecurityForm.CHANGED_IDS_NAME_FIELD);

			operation.initialize(CreationType.valueOf((String) result.get(SetupSecurityForm.CHANNEL_TYPE_NAME_FIELD)), changedIds);
			operation.save();
			updateFormTemplates(frm, operation);
		}
		else
		{
			operation.initialize(CreationType.internet);
			saveErrors(request, processor.getErrors());
			updateFormTemplates(frm, operation);
		}

		return start(mapping, form, request, response);
	}

	private void updateFormTemplates(SetupSecurityForm frm, EditClientTemplatesShowSettingsOperation operation) throws BusinessException
	{
		frm.setTemplates(operation.getTemplates());
		frm.setField(SetupSecurityForm.CHANNEL_TYPE_NAME_FIELD, operation.getChannelType().name());
	}
}
