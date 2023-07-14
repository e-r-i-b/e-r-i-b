package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.chargeoff.ChargeOffLogService;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.persons.clients.UserImpl;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.gate.clients.User;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ermb.person.identity.PersonIdentityHistoryEditOperation;
import com.rssl.phizic.operations.groups.ModifyPersonGroupsOperation;
import com.rssl.phizic.operations.messaging.EditUserContactDataOperation;
import com.rssl.phizic.operations.person.*;
import com.rssl.phizic.operations.security.LinkPINOperation;
import com.rssl.phizic.operations.userprofile.EditPersonAvatarOperation;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.persons.formBuilders.PersonFormBuilderBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditPersonAction extends EditPersonActionBase
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ChargeOffLogService chargeoffService = new ChargeOffLogService();

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditPersonForm frm = (EditPersonForm) form;

		EditPersonOperation operation = createOperation("ViewPersonOperation");
        operation.setPersonId(frm.getPerson());

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return doSave(form, request, mapping);
    }

	/**
	 * Нажатие кнопки о том, что договор подписан.
	 * @param mapping мапинг
	 * @param form    форма
	 * @param request request
	 * @param response response
	 * @return  action forward
	 * @throws Exception
	 */
    public ActionForward agreementSigned(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    EditPersonForm      frm       = (EditPersonForm) form;

	    SignAgreementOperation signAgreementOperation = createOperation(SignAgreementOperation.class);

	    clearEmptyFields(frm, Boolean.parseBoolean((String) frm.getField("resident")));

        MapValuesSource fieldsSource  = new MapValuesSource(frm.getFields());
        FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldsSource, getFormBuilder().getFullForm());
	    //в том случае если пользователь не хочет получать sms, он не может выбрать формат, поэтому установим его сами
		if (!"sms".equals(frm.getField("messageService")))
			frm.setField("SMSFormat", TranslitMode.DEFAULT.toString());

		signAgreementOperation.setPersonId(frm.getPerson());

	    //анкета должны быть полностью заполнена
        boolean hasErrors = !processor.process();
        if ( !hasErrors)
        {
	        doSave(form, request, mapping);
	        updateEntity(signAgreementOperation.getPerson(), processor.getResult());
	        if(processClientRegistrationForm( frm, request ))
	        {

				signAgreementOperation.sign();
	        }
        }
        else
        {
            saveErrors(request, processor.getErrors());
        }

        updateFormPerson(signAgreementOperation, frm);

	    return mapping.findForward(FORWARD_START);
    }

	/**
	 * возвращаемся к редактированию клиента
	 * @param mapping мапинг
	 * @param form    форма
	 * @param request request
	 * @param response response
	 * @return  action forward
	 * @throws Exception
	 */
    public ActionForward returnToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    EditPersonForm      frm       = (EditPersonForm) form;

	    SignAgreementOperation signAgreementOperation = createOperation(SignAgreementOperation.class);

		signAgreementOperation.setPersonId(frm.getPerson());
		signAgreementOperation.returnToEdit();

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", signAgreementOperation.getEntity()));

	    return start(mapping, form, request, response);
    }


    protected boolean processClientRegistrationForm(EditPersonForm frm, HttpServletRequest request) throws Exception
	{
		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);

		FormProcessor<ActionMessages, ?> processor =
				createFormProcessor(fieldsSource,
						getFormBuilder().buildClientRegistrationForm(propertiesPaymentsConfig.getNeedAccount()));

		boolean result = processor.process();
		if (!result)
		{
			saveErrors(request, processor.getErrors());
		}
		return result;
	}

    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        EditPersonForm                   frm           = (EditPersonForm) form;
        MapValuesSource                  fieldsSource  = new MapValuesSource(frm.getFields());
        RemovePersonOperationBase      operation     = createOperation("RemovePersonOperation");
        FormProcessor<ActionMessages, ?> processor     =
                createFormProcessor(fieldsSource, getFormBuilder().buildClientCancelationForm(frm.getAskForDeleting()));

        operation.initialize(frm.getPerson());

        if ( processor.process() )
        {
            ActivePerson person = operation.getPerson();

            Date prolongationRejectionDate = (Date)processor.getResult().get("prolongationRejectionDate");
            person.setProlongationRejectionDate(DateHelper.toCalendar(prolongationRejectionDate));

            String contractCancellationCouse = (String) processor.getResult().get("contractCancellationCouse");
            person.setContractCancellationCouse(contractCancellationCouse);

	        // если вылетит Exception, то он должен словиться выше, откуда вызван remove()
			ModifyPersonGroupsOperation modifyPersonGroupsOperation = createOperation(ModifyPersonGroupsOperation.class);
			List<Long> ids = new ArrayList<Long>();
			ids.add(frm.getPerson());
			modifyPersonGroupsOperation.deletePersonsFromAll(ids);

            try
            {
	            addLogParameters(new BeanLogParemetersReader("Удаляемая сущность", operation.getEntity()));
	            operation.setCurrentUser(getCurrentUser());
	            operation.remove();
            }
            catch (InvalidPersonStateException ex)
            {
	            ActionMessages msgs = new ActionMessages();
	            msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.persons.cantDeleteActiveUser", ex.getPerson().getFullName()));
	            saveErrors(request, msgs);
				updateFormPerson(operation, frm);
				return mapping.findForward(FORWARD_START);
            }
	        catch (BusinessLogicException e)
	        {
		        ActionMessages msgs = new ActionMessages();
		        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		        saveErrors(request, msgs);
		        updateFormPerson(operation, frm);
		        return mapping.findForward(FORWARD_START);
	        }
        }
        else
        {
            saveErrors(request, processor.getErrors());
			updateFormPerson(operation, frm);
			return mapping.findForward(FORWARD_START);
        }

        return mapping.findForward(FORWARD_CLOSE);
    }

	/**
	 * удалить пользователя, информация о котором не была передана в ЦОД
	 * при этом не нужно делать проверок на заполненность анкеты
	 * @param mapping мапинг
	 * @param form    форма
	 * @param request request
	 * @param response response
	 * @return  action forward
	 * @throws Exception
	 */
	public ActionForward removeTemplatePerson(ActionMapping mapping,
	                                          ActionForm form, HttpServletRequest request,
	                                          HttpServletResponse response) throws Exception
	{
		EditPersonForm frm = (EditPersonForm) form;
		RemovePersonOperationBase operation = createOperation("RemovePersonOperation");

		operation.initialize(frm.getPerson());
		try
		{
			addLogParameters(new BeanLogParemetersReader("Удаляемая сущность", operation.getEntity()));

			operation.remove();
		}
		catch (InvalidPersonStateException ex)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					         new ActionMessage("com.rssl.phizic.web.persons.cantDeleteActiveUser",
							 ex.getPerson().getFullName()));
			saveErrors(request, msgs);

			updateFormPerson(operation, frm);
			return mapping.findForward(FORWARD_START);
		}
		return mapping.findForward(FORWARD_CLOSE);
	}


    protected ActionForward doSave(ActionForm form, HttpServletRequest request, ActionMapping mapping) throws Exception
    {
        EditPersonForm frm          = (EditPersonForm) form;
	    ActionForward actionForward = null;

	    if (!PermissionUtil.impliesOperation(EditPersonOperation.class.getSimpleName(), null))
	    {
		    EditIncognitoStateOperation editIncogintoOperation = createOperation(EditIncognitoStateOperation.class);
            editIncogintoOperation.setPersonId(frm.getPerson());
		    String personStatus = editIncogintoOperation.getPerson().getStatus();
		    if (personStatus.equals(Person.ACTIVE))
		    {
	            Form editForm = getFormBuilder().getFullForm();

	            clearEmptyFields(frm, Boolean.parseBoolean((String) frm.getField("resident")));

	            MapValuesSource                  fieldsSource = new MapValuesSource(frm.getFields());
	            FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource , editForm, getChangedFields(request));

	            if(!processor.process())
	            {
	                saveErrors(request, processor.getErrors());
	            }
			    else
	            {
		            frm.setFields(processor.getResult());
			        saveIncognito(frm);
	            }
		    }
		    updateForm(frm,  editIncogintoOperation.getPerson());
            updateFormAdditionalData(frm, editIncogintoOperation);
            return mapping.findForward(FORWARD_START);
	    }

	    EditPersonOperation editOperation = createOperation(EditPersonOperation.class);
		editOperation.setPersonId(frm.getPerson());
	    ActivePerson person = new ActivePerson(editOperation.getPerson());

	    if(checkPINForm(frm, request) && validateForm(frm, editOperation, request))
	    {
		    String personStatus = frm.getActivePerson().getStatus();

		    boolean mobileChecked  = false;
            boolean isPersonActive = personStatus.equals(Person.ACTIVE);

		    RegisterPersonChangesOperation operation = null;
		    if (PermissionUtil.impliesOperation(RegisterPersonChangesOperation.class.getSimpleName(), null))
		    {
			    operation = createOperation("RegisterPersonChangesOperation");
			    operation.setPersonId(frm.getPerson());

			    if (isPersonActive)
			    {
				    try
				    {
					    PersonCreateConfig flowConfig = ConfigFactory.getConfig(PersonCreateConfig.class);
					    if (!flowConfig.getRegisterChangesEnable())
					    {
						    ActivePerson frmPerson = new ActivePerson(frm.getActivePerson());
						    operation.setChangedPerson(frmPerson);

						    operation.checkMobileChanges();
					    }
					    mobileChecked = true;
				    }
				    catch (BusinessException e)
				    {
					    ActionMessages msgs = new ActionMessages();
					    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
					    saveMessages(request, msgs);

					    actionForward = mapping.findForward(FORWARD_START);
				    }
				    catch (BusinessLogicException e)
				    {
					    log.error("Ошибка при сохранении анкеты клиента. Не удалось отправить смс сообщение по номеру " + operation.getPerson().getMobilePhone(), e);
					    actionForward = mapping.findForward(FORWARD_START);

					    /*
					     * Если упали при отправке сообщения изменения сохранять все равно, поскольку может оказаться
					     * что номер сохраненный в базе некорректный, а отправка смс осуществляется именно на старый номер.
					     */
					    mobileChecked = true;
				    }
			    }
		    }

		    /*
		     *
		     * Если пользователь активен, тогда при сохранении необходимо чтобы сообщение было отослано, если же пользователь
		     * неактивен то сообщение может быть и не отослано.
		     *
		     */
		    if(mobileChecked || !isPersonActive)
		    {
				saveForm(frm);
				savePINForm(frm, request);
		    }

		    /*
		     * Обновляем содержимое
		     */
		    editOperation.setPersonId(frm.getPerson());


		    actionForward = actionForward == null ? mapping.findForward(FORWARD_SUCCESS) : actionForward;
	    }
	    else
        {
            actionForward = mapping.findForward(FORWARD_START);
        }

	    addLogParameters(new CompositeLogParametersReader(
				new BeanLogParemetersReader("Первоначальные данные", editOperation.getEntity()),
				new FormLogParametersReader("Данные введенные пользователем", getFormBuilder().getFullForm(), frm.getFields())
			));

	    updateSubscription(editOperation.getPerson(), frm.getFields());
	    updateForm(frm,  editOperation.getPerson());

	    updateFormAdditionalData(frm, editOperation);

	    PersonDocument curDoc = null;
	    for(PersonDocument doc: person.getPersonDocuments())
	    {
		    if (doc.getDocumentMain())
			    curDoc = doc;
	    }
	    if (curDoc != null)
	    {
		    if (!PermissionUtil.impliesOperation(RegisterPersonChangesOperation.class.getSimpleName(), null))
			    return actionForward;

		    PersonIdentityHistoryEditOperation historyOperation = createOperation(PersonIdentityHistoryEditOperation.class);
		    historyOperation.initialize(null,person.getId(),true);
		    historyOperation.setDocument(curDoc.getDocumentType().toValue(),curDoc.getDocumentNumber(),curDoc.getDocumentSeries());
		    PersonOldIdentity hIdentity = (PersonOldIdentity)historyOperation.getEntity();
		    hIdentity.setBirthDay(person.getBirthDay());
		    hIdentity.setFirstName(person.getFirstName());
		    hIdentity.setSurName(person.getSurName());
		    hIdentity.setPatrName(person.getPatrName());
		    hIdentity.setDateChange(DateHelper.getCurrentDate());
		    hIdentity.setEmployee(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin());
		    hIdentity.setRegion(PersonHelper.getPersonTb(person));
		    historyOperation.save();
	    }

	    return actionForward;
    }

	private User getCurrentUser()
	{
		try
		{
			Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
			UserImpl user = new UserImpl();
			user.fillFromEmployee(employee);
			return user;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при попытке определить данные текущего пользователя.", e);
			return null;
		}
	}

	protected boolean validateForm(EditPersonForm frm, EditPersonOperation editOperation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		ActivePerson person = editOperation.getPerson();
		Form editForm = chooseEditForm(person);

		clearEmptyFields(frm, Boolean.parseBoolean((String) frm.getField("resident")));

		MapValuesSource                  fieldsSource = new MapValuesSource(frm.getFields());
	    FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource , editForm, getChangedFields(request));

		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return false;
		}

		frm.setFields(processor.getResult());
		updateFormAdditionalData(frm, editOperation);
        return true;
	}

	protected void saveForm(EditPersonForm frm) throws BusinessException, BusinessLogicException
	{
	    //в том случае если польхователь не хочет получать sms, он не может выбрать формат, поэтому устан его сами
		String messageService = (String) frm.getField("messageService");
	    if (!("sms".equals(messageService) || messageService.indexOf("smsBanking") != -1) )
	    {
		    frm.setField("SMSFormat", TranslitMode.DEFAULT.toString());
	    }

		if (PermissionUtil.impliesOperation(EditPersonOperation.class.getSimpleName(), null))
			savePerson(frm);

		if (PermissionUtil.impliesService("ChangePersonIncognitoSettings"))
			saveIncognito(frm);
	}

	private void savePerson(EditPersonForm frm) throws BusinessException, BusinessLogicException
	{
		EditPersonOperation editOperation = createOperation(EditPersonOperation.class);
		editOperation.setPersonId(frm.getPerson());

		ActivePerson person = editOperation.getPerson();
		//todo криво, но проблемы с сесией хибернейт и коллекциями, необходимо перейти на перехватчики  делать вне сессии
		editOperation.switchToShadow();
		editOperation.createEvent();
		ActivePerson oldPerson = new ActivePerson(person);
		try
		{
			updateEntity(person, frm.getFields());
			editOperation.setOldPersonKey(oldPerson);
			editOperation.save();
			if (frm.getActivation())
			{
				editOperation.switchToDirect();
			}
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch(BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void saveIncognito(EditPersonForm frm)
	{
		try
		{
			EditIncognitoStateOperation editOperation = createOperation(EditIncognitoStateOperation.class);
			editOperation.initialize((IncognitoState) frm.getField(PersonFormBuilderBase.INCOGNITO_FIELD));
			editOperation.setPersonId(frm.getPerson());
			editOperation.save();
		}
		catch (Exception e)
		{
			phizLog.error("Ошибка изменения статуса приватности.", e);
			saveMessage(currentRequest(), "Ошибка изменения статуса приватности.");
		}
	}

	protected Form chooseEditForm(ActivePerson person)
	{
		if (person.getStatus().equals(Person.TEMPLATE) || person.getStatus().equals(Person.SIGN_AGREEMENT))
		{
			return getFormBuilder().getPartiallyFilledForm();
		}
		else
		{
			return getFormBuilder().getFullForm();
		}
	}

    protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, BusinessLogicException
	{
		try
		{
			super.updateFormAdditionalData(form, operation);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		EditPersonForm frm = (EditPersonForm) form;
		PersonExtendedInfoOperationBase op = (PersonExtendedInfoOperationBase) operation;

        frm.setActivePerson(op.getPerson());
	    frm.setDepartment(op.getDepartment());
		frm.setDocumentTypes(op.getDocumentTypes());
	    frm.setModified(PersonOperationMode.shadow.equals(op.getMode()));
	    if (op.getPerson() == null)
	    {
		    return;
	    }
	    Set <PersonDocument> personDocument = op.getPerson().getPersonDocuments();
	    if (personDocument!=null)
            frm.setPersonDocuments(personDocument);
		frm.setHasDebts(checkIfPpersonsHasDebts(op.getPerson()));

		//бред, но мы можем сюда попасть сразу после валидации полей, в этом случае не нужно тянуть признак
		if (!frm.getFields().containsKey(PersonFormBuilderBase.INCOGNITO_FIELD))
			frm.setField(PersonFormBuilderBase.INCOGNITO_FIELD, op.getClientIncognito());

	    // в зависимости от наличия доступа к операции LinkPINOperation либо проставлять id из пин-конверта либо просто генерить существующими алгоритмами
	    if (checkAccess("ViewLinkPINOperation"))
	    {
			LinkPINOperation linkPINOperation = createOperation("ViewLinkPINOperation");
			linkPINOperation.setPersonId(frm.getPerson());
			updatePinFieldForm(linkPINOperation, form);
	    }
	    else
	    {
		    updatePIN2(frm, op.getPerson());
	    }

		if (PermissionUtil.impliesOperation("ViewPersonAvatarOperation", "ViewPersonAvatar") || PermissionUtil.impliesOperation("EditPersonAvatarOperation", "EditPersonAvatar"))
		{
			EditPersonAvatarOperation editPersonAvatarOperation;
			if(PermissionUtil.impliesOperation("EditPersonAvatarOperation", "EditPersonAvatar"))
				editPersonAvatarOperation = createOperation("EditPersonAvatarOperation", "EditPersonAvatar");
			else
				editPersonAvatarOperation = createOperation("ViewPersonAvatarOperation", "ViewPersonAvatar");
			long loginId = op.getPerson().getLogin().getId();
			AvatarInfo info = editPersonAvatarOperation.getPersonAvatarInfo(AvatarType.AVATAR, loginId);
			frm.setHasAvatar(info.getImageInfo() != null);
			if (info.getImageInfo() != null)
				frm.setAvatarInfo(info);
		}
    }


    protected void updateFormPerson(PersonOperationBase operation, EditPersonForm form) throws BusinessException
    {
        form.setActivePerson(operation.getPerson());
        form.setDepartment(operation.getPersonDepartment());
	    Set <PersonDocument> personDocument = operation.getPerson().getPersonDocuments();
	    form.setDocumentTypes(operation.getDocumentTypes());
	    if (personDocument!=null)
            form.setPersonDocuments(personDocument);
		form.setHasDebts(checkIfPpersonsHasDebts(operation.getPerson()));
    }

	private void updateSubscription(ActivePerson person, Map<String, Object> fields) throws BusinessException, BusinessLogicException
	{
		if (!PermissionUtil.impliesOperation(EditUserContactDataOperation.class.getSimpleName(), null))
			return;
		EditUserContactDataOperation operation = createOperation(EditUserContactDataOperation.class);
		operation.initialize(person.getLogin());
		String mobilePhone = (String) fields.get("mobilePhone");
		TranslitMode smsFormat = TranslitMode.valueOf((String) fields.get("SMSFormat"));
		operation.setEmailAddress((String) fields.get("email"));
		operation.setMobilePhone(mobilePhone);
		operation.setTranslit(smsFormat);
		operation.checkMobileChanges(mobilePhone);//проверяем не изменился ли мобильный телефон.
		operation.save();

		if (!PermissionUtil.impliesOperation(SetupNotificationOperation.class.getSimpleName(), null))
			return;
		SetupNotificationOperation notificationOperation = createOperation(SetupNotificationOperation.class);
		notificationOperation.initialize(person, UserNotificationType.operationNotification);
		notificationOperation.setChannel((String) fields.get("messageService"));
		notificationOperation.saveNotification();
	}

	/**
	 *
	 * @return  true, если у пользователя есть задолженности
	 */
	public boolean checkIfPpersonsHasDebts(ActivePerson person) throws BusinessException
	{
		return !chargeoffService.getPersonsDebts(person.getLogin()).isEmpty();
	}
}
