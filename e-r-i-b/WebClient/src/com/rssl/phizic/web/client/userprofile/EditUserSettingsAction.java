package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.operations.messaging.EditUserContactDataOperation;
import com.rssl.phizic.operations.userprofile.EditIdentifierBasketOperation;
import com.rssl.phizic.operations.userprofile.EditUserSettingsOperation;
import com.rssl.phizic.operations.userprofile.UploadAvatarOperation;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;
import org.hibernate.Session;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 21.05.2010
 * @ $Authors$
 * @ $Revision$
 */
public class EditUserSettingsAction extends EditUserProfileActionBase
{
	private static final String FORWARD_SUCCESS = "Success";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.confirm", "confirm");
		map.put("button.backToEdit", "backToEdit");
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.confirmCap", "changeToCap");
		map.put("button.nextStage", "createSaveActionForward");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUserSettingsForm frm = (EditUserSettingsForm)form;
		EditUserSettingsOperation operation = null;
		if(checkAccess(EditUserSettingsOperation.class))
			operation = createOperation(EditUserSettingsOperation.class);
		else
			operation = createOperation("ViewUserSettingsOperation");
		operation.initialize();

		updateForm(frm, operation);
		if (PersonHelper.availableNewProfile())
		{
			updateFormNewProfile(frm, operation);
			return mapping.findForward(FORWARD_START_NEW);
		}
		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormAdditionalData(EditUserSettingsForm frm, EditUserSettingsOperation operation) throws Exception
	{
		EditUserContactDataOperation contactDataOperation = null;
		if(checkAccess(EditUserContactDataOperation.class))
			contactDataOperation = createOperation(EditUserContactDataOperation.class);
		else
			contactDataOperation = createOperation("ViewUserContactDataOperation");
		contactDataOperation.initialize(operation.getUser().getLogin());
		frm.setField("email", contactDataOperation.getEmailAddress());
		MailFormat mailFormat = contactDataOperation.getMailFormat();
		if (mailFormat == null)
		{
			frm.setField("mailFormat", MailFormat.PLAIN_TEXT.name());
		} else
		{
			frm.setField("mailFormat", mailFormat);
		}
		Region region = operation.getProfileRegion();
		if (region == null || region.getId() == null)
		{
			frm.setField("regionId", "-1");
			frm.setField("regionName", "Все регионы");
		}
		else
		{
			frm.setField("regionId", region.getId());
			frm.setField("regionName", region.getName());
		}
	}
	//обновить форму данными, не подлежащими изменению
	private void updateFormStaticData(EditUserSettingsForm frm, EditUserSettingsOperation operation) throws BusinessException
	{
		ActivePerson activePerson = operation.getUser();
		frm.setClientUDBO(activePerson.getCreationType().equals(CreationType.UDBO));
		frm.setField("fio", PersonHelper.getFormattedPersonName(
				activePerson.getFirstName(), activePerson.getSurName(), activePerson.getPatrName()));
		try
		{
			frm.setField("mobilePhone", PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhones()));
		} catch (InactiveExternalSystemException ignored){}//игнорируем ошибку, просто не отображаем номер телефона
	}

	protected void updateForm(EditUserSettingsForm frm, EditUserSettingsOperation operation) throws Exception
	{
		updateFormStaticData(frm, operation);
		ActivePerson activePerson = operation.getUser();
		frm.setField("homePhone", activePerson.getHomePhone());
		frm.setField("jobPhone", activePerson.getJobPhone());
		frm.setField("SNILS", activePerson.getSNILS());
		frm.setHasAvatar(UploadAvatarOperation.hasAvatar());
		frm.setIdent(operation.getAllowedTypes());

		updateFormAdditionalData(frm, operation);
	}

	private void updateFormNewProfile(EditUserSettingsForm frm, EditUserSettingsOperation operation) throws BusinessException
	{
		frm.setPersonDocumentList(PersonHelper.getDocumentForProfile(operation.getUser().getPersonDocuments()));
		frm.setLinks(operation.getLinks());

		try
		{
			EditIdentifierBasketOperation editIdentifierBasketOperation = createOperation("EditIdentifierBasketOperation");
			editIdentifierBasketOperation.initialize();
			ProfileConfig profileConfig = ConfigFactory.getConfig(ProfileConfig.class);
			frm.setAccessDL(profileConfig.isAccessUserDocumentDL());
			frm.setAccessRC(profileConfig.isAccessUserDocumentRC());
			frm.setAccessINN(profileConfig.isAccessUserDocumentINN());
			frm.setUserDocumentList(editIdentifierBasketOperation.getUserDocumentList());
		}
		catch (AccessControlException ignore){}
	}

	public ActionForward createSaveActionForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены", false), null);
		return getCurrentMapping().findForward(FORWARD_SUCCESS);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUserSettingsForm frm = (EditUserSettingsForm) form;
		final EditUserSettingsOperation operation = createOperation(EditUserSettingsOperation.class);
		operation.initialize();

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, EditUserSettingsForm.EDIT_FORM);
		if (!formProcessor.process())
		{
			updateFormStaticData(frm, operation);
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		Map<String, Object> results = formProcessor.getResult();
		operation.setUnsavedData(results);

		//Старое значение региона из профиля
		Region region = operation.getProfileRegion();
		Long oldRegionId = region != null ? region.getId() : -1;
		String oldMailFormatName = null;
		if (operation.getUser().getMailFormat() != null)
		{
			EditUserContactDataOperation op = createOperation(EditUserContactDataOperation.class);
			op.initialize(operation.getUser().getLogin());
			oldMailFormatName = op.getMailFormat().name();
		}
		Boolean unobligatoryFieldsChanged = checkChangesNotRequiringConfirmation(results, oldRegionId, oldMailFormatName);

		if(checkChanges(results, operation))
		{
			operation.resetConfirmStrategy();
			ConfirmationManager.sendRequest(operation);
            frm.setConfirmStrategy(operation.getConfirmStrategy());
            saveOperation(request,operation);
			updateFormStaticData(frm, operation);
			frm.setField("confirmableObject", operation.getConfirmableObject());

			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Обратите внимание, если Вы перейдете на другую страницу системы без " +
																						"подтверждения изменения настроек SMS-паролем, " +
																						"то Ваши изменения не будут сохранены в системе.", false));
			saveMessages(request, message);
			return mapping.findForward(FORWARD_START);
		}
		else if (!unobligatoryFieldsChanged)
		{
			updateFormStaticData(frm, operation);
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не внесли никаких изменений.", false));
			saveErrors(request, message);
			return mapping.findForward(FORWARD_START);
		}
		else
		{
			final EditUserContactDataOperation op = createOperation(EditUserContactDataOperation.class);
			op.initialize(operation.getUser().getLogin());
			op.setMailFormat(MailFormat.valueOf((String) results.get("mailFormat")));
			// Сохраняем все, что было изменено, но не требует подтверждения (Регион).

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					operation.save();
					op.save();
					return null;
				}
			});
			return createSaveActionForward(mapping, form, request, response);
		}
	}

	public ActionForward backToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUserSettingsForm frm = (EditUserSettingsForm) form;
		EditUserSettingsOperation operation = createOperation(EditUserSettingsOperation.class);
		operation.initialize();

		frm.setField("unsavedData", true);
		updateFormStaticData(frm, operation);
		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		EditUserSettingsForm frm = (EditUserSettingsForm) form;
		EditUserSettingsOperation operation = createOperation(EditUserSettingsOperation.class);
		operation.initialize();

		operation.setUnsavedData(frm.getFields());

		frm.setField("confirmableObject", operation.getConfirmableObject());

		if (CollectionUtils.isEmpty(frm.getFields().entrySet()))
		{
			updateFormAdditionalData(frm, operation);
		}

		ConfirmationManager.sendRequest(operation);
        frm.setConfirmStrategy(operation.getConfirmStrategy());
		operation.getRequest().setPreConfirm(true);
		MailFormat mailFormat = StringHelper.isNotEmpty((String) frm.getField("mailFormat")) ? MailFormat.valueOf((String)frm.getField("mailFormat")) : MailFormat.PLAIN_TEXT;
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(ConfirmStrategyType.sms, login, operation.getConfirmableObject()));
			operation.getRequest().setPreConfirm(true);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			frm.setMailFormat(mailFormat);
			Map<String, Object> chengedFields = operation.getUser().getChangedFields(frm.getFields());
			frm.setChengedFields(chengedFields);
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
			frm.setMailFormat(mailFormat);
			Map<String, Object> chengedFields = operation.getUser().getChangedFields(frm.getFields());
			frm.setChengedFields(chengedFields);
		}

		return mapping.findForward(FORWARD_START);
	}

    public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		EditUserSettingsForm frm = (EditUserSettingsForm) form;
		EditUserSettingsOperation operation =  getOperation(request);
		operation.setUnsavedData(frm.getFields());
		frm.setField("confirmableObject", operation.getConfirmableObject());
		updateFormAdditionalData(frm, operation);
        operation.setUserStrategyType(ConfirmStrategyType.cap);
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
		updateForm(frm, operation);
		confirmRequest.addMessage(ConfirmHelper.getPreConfirmCapString());
		return mapping.findForward(FORWARD_START);
	}


	public ActionForward confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
	    EditUserSettingsForm frm = (EditUserSettingsForm) form;
		EditUserSettingsOperation operation = getOperation(request);

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		MailFormat format = StringHelper.isNotEmpty((String) frm.getField("mailFormat")) ? MailFormat.valueOf((String)frm.getField("mailFormat")) : MailFormat.PLAIN_TEXT;
		frm.setMailFormat(format);
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, EditUserSettingsForm.EDIT_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			frm.setField("confirmableObject", operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}

		Map<String,Object> unsavedData = formProcessor.getResult();
		operation.setUnsavedData(unsavedData);

		if (CollectionUtils.isEmpty(unsavedData.entrySet()))
		{
			updateFormAdditionalData(frm, operation);
		}

		try
	    {
		    List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		    Map<String, Object> chengedFields = operation.getUser().getChangedFields(frm.getFields());
			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				frm.setField("confirmableObject", operation.getConfirmableObject());
				frm.setChengedFields(chengedFields);
				return mapping.findForward(FORWARD_START);
			}
			else
			{
				operation.validateConfirm();
				//запомнить оригинальные данные чтоб откатить person если происходит ошибка при отправке в mdm
				operation.rememberOriginalPerson();
				operation.updatePerson();
			    saveAndSendInTransaction(operation);
				// Обновляем персональные данные для рассылки оповещений
				String email = (String)unsavedData.get("email");
				String mobilePhone = (String)unsavedData.get("mobilePhone");
				TranslitMode smsFormat = operation.getUser().getSMSFormat();
				MailFormat mailFormat = MailFormat.valueOf((String)unsavedData.get("mailFormat"));
				updatePersonalSubscriptionData(operation.getUser().getLogin(), email, mobilePhone, smsFormat, mailFormat);
				operation.sendSmsNotification(chengedFields);
				return createSaveActionForward(mapping, form, request, response);
			}
		}
	   	catch (SecurityLogicException e) // ошибка подтверждения
        {
	        operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
	        frm.setField("confirmableObject", operation.getConfirmableObject());
	        Map<String, Object> chengedFields = operation.getUser().getChangedFields(frm.getFields());
			frm.setChengedFields(chengedFields);
			return mapping.findForward(FORWARD_START);
        }
	    catch (SecurityException e) //упал сервис
	    {
		    frm.setField("confirmableObject", operation.getConfirmableObject());
		    ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Сервис временно недоступен, попробуйте позже", false));
			saveMessages(request, message);
		    return mapping.findForward(FORWARD_START);
	    }
		catch (InactiveExternalSystemException e)
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveMessages(request, message);

			//откатываем обратно person, так как при start() он не синхранизируется с нашей базой
			operation.setUnsavedData(operation.getOriginalData());
			operation.updatePerson();

			return mapping.findForward(FORWARD_START);
		}
	}

	//чтоб в случае ошибки при отправке в mdm изменения не сохранились в базе
	private void saveAndSendInTransaction(final EditUserSettingsOperation operation) throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				operation.save();
				return null;
			}
		});
	}


	private void updatePersonalSubscriptionData(CommonLogin login, String email, String mobilePhone, TranslitMode smsFormat, MailFormat mailFormat) throws BusinessException, BusinessLogicException
	{
		EditUserContactDataOperation operation = createOperation(EditUserContactDataOperation.class);
		operation.initialize(login);
		operation.setEmailAddress(email);
		operation.setMobilePhone(mobilePhone);
		operation.setTranslit(smsFormat);
		operation.setMailFormat(mailFormat);
		operation.save();
	}

	private boolean checkChanges(Map<String, Object> fields, EditUserSettingsOperation operation) throws BusinessException, BusinessLogicException
	{		
		ActivePerson activePerson = operation.getUser();

		return !activePerson.getChangedFields(fields).isEmpty();
	}

	/**
	 * проверяем на изменение поля, не требующие подтверждения по СМС
	 */
	private boolean checkChangesNotRequiringConfirmation(Map<String, Object> results, Long oldRegionId, String oldMailFormatName )
	{
		return !oldRegionId.equals(results.get("regionId")) || !results.get("mailFormat").equals(oldMailFormatName);
	}
}
