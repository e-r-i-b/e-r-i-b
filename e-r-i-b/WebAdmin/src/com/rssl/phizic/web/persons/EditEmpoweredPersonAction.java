package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.access.EditEmpoweredAccessOperation;
import com.rssl.phizic.operations.person.*;
import com.rssl.phizic.operations.security.LinkPINOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditEmpoweredPersonAction extends EditPersonActionBase
{
	protected boolean saveForm(EditPersonForm form, HttpServletRequest request) throws Exception
	{
		EditEmpoweredPersonForm frm = (EditEmpoweredPersonForm) form;

		EditEmpoweredPersonOperation editOperation = (EditEmpoweredPersonOperation) createEditOperation(frm);
		EditEmpoweredResourcesOperation resourcesOperation = createResourcesOperation(editOperation, false);
		EditEmpoweredAccessOperation simpleAccessOperation = createAccessOperation(editOperation, false, AccessType.simple);
		EditEmpoweredAccessOperation secureAccessOperation = createAccessOperation(editOperation, false, AccessType.secure);

		ActivePerson person = editOperation.getEntity();
		Form editForm;
		if (person.getStatus().equals(Person.TEMPLATE))
		{
			editForm = getFormBuilder().getEmpoweredPartiallyFilledForm();
		}
		else
		{
			editForm = getFormBuilder().getEmpoweredFullForm();
		}

		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldsSource, editForm);

		boolean hasErrors = !processor.process();

		if (hasErrors)
		{
			saveErrors(request, processor.getErrors());
		}
		else
		{
			Map<String, Object> result = processor.getResult();
			addLogParameters(new BeanLogParemetersReader("Клиент", editOperation.getPerson()));
			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Первоначальные данные", editOperation.getEntity()),
					new FormLogParametersReader("Данные введенные пользователем", editForm, form.getFields())
				));

			updateEntity(person, result);
			switchToShadow(editOperation,person);

			editOperation.save();
			frm.setId(person.getId());
			//права доступа
			processAccessForm(frm.getSimpleAccess(), simpleAccessOperation);
			processAccessForm(frm.getSecureAccess(), secureAccessOperation);
			//счета
			List<String> selectedAccountNumbers = Arrays.asList(frm.getSelectedAccountLinks());
			resourcesOperation.setNewAccountLinks(selectedAccountNumbers);
			//карты
			List<String> selectedCardNumbers = Arrays.asList(frm.getSelectedCardLinks());
			resourcesOperation.setNewCardLinks(selectedCardNumbers);

			switchToShadow(resourcesOperation,person);
			resourcesOperation.save();
		}

		return !hasErrors;
	}

	/**
	 * обработка настроек прав доступа введеных пользователем
	 * @param subForm
	 * @param operation
	 * @throws BusinessException
	 */
	private void processAccessForm(AccessEmpoweredPersonSubForm subForm,EditEmpoweredAccessOperation operation) throws BusinessException
	{
		operation.setNewServices( Arrays.asList(subForm.getSelectedServices()) );
		operation.setCurrentAccessAllowed(subForm.isEnabled());

		Properties properties = new Properties();
		Set<Map.Entry<String,Object>> entries = subForm.getProperties().entrySet();
		for (Map.Entry<String, Object> entry : entries)
		{
			properties.put(entry.getKey(), (String) entry.getValue());
		}
		operation.setProperties(properties);
		switchToShadow(operation,operation.getEmpoweredPerson() );
		operation.save();
	}

	private void switchToShadow(PersonOperationBase operation, Person empoweredPerson) throws BusinessException
	{
		/**
		 * Только в случае активного представителя работаем с тенью, т.к. иначе есть проблемы с подключением
		 */
		if(Person.ACTIVE.equals(empoweredPerson.getStatus()))
		{
			operation.switchToShadow();
		}
		else
		{
			operation.setMode(PersonOperationMode.direct);
		}
	}

	private LinkPINOperation createLinkPINOperation(Long personId, boolean view) throws BusinessException, BusinessLogicException
	{
		LinkPINOperation linkPINOperation;
		if (view)
			linkPINOperation = createOperation("ViewLinkPINOperation");
		else
			linkPINOperation = createOperation(LinkPINOperation.class);

		linkPINOperation.setPersonId(personId);
		return linkPINOperation;
	}

	/**
	 * инициализации формы прав доступа из операции
	 * @param form форма для заполнения
	 * @param accessOperation
	 * @return
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private AccessEmpoweredPersonSubForm initAccessSubForm(AccessEmpoweredPersonSubForm form, EditEmpoweredAccessOperation accessOperation) throws BusinessException, BusinessLogicException
	{
		form.setEnabled(accessOperation.isCurrentAccessAllowed());
		form.setOperationsByServiceMap(accessOperation.getServiceOperationDescriptors());
		form.setPolicy(accessOperation.getAccessPolicy());
		form.setProperties((Map<String, Object>)accessOperation.getProperties().clone());
		form.setServices(accessOperation.getTrustingServices());

		List<Service> availableServices = accessOperation.getCurrentServices();
		Long[] temp = new Long[availableServices.size()];
		int i = 0;
		for (Service s : availableServices)
		{
			temp[i++] = s.getId();
		}
		form.setSelectedServices(temp);

		return form;
	}

	private void initForm(EditEmpoweredPersonForm frm, EditEmpoweredPersonOperation editOperation, EditEmpoweredResourcesOperation resourcesOperation,
	                      EditEmpoweredAccessOperation simpleAccessOperation, EditEmpoweredAccessOperation secureAccessOperation)
			throws BusinessException, BusinessLogicException
	{
		frm.setActivePerson(editOperation.getPerson());
		frm.setEmpoweredPerson(editOperation.getEntity());
		frm.setDepartment(editOperation.getPersonDepartment());
		frm.setPersonDocuments(editOperation.getEntity().getPersonDocuments());

		frm.setAllAccountLinks(resourcesOperation.getTrustingAccountLinks());
		frm.setAllCardLinks(resourcesOperation.getTrustingCardLinks());

		frm.setSimpleAccess(initAccessSubForm(frm.getSimpleAccess(), simpleAccessOperation) );
		frm.setSecureAccess( initAccessSubForm(frm.getSecureAccess(), secureAccessOperation) );
	}

	public void initForm(EditEmpoweredPersonForm frm) throws BusinessException, BusinessLogicException
	{
		EditEmpoweredPersonOperation editOperation = (EditEmpoweredPersonOperation) createEditOperation(frm);
		EditEmpoweredResourcesOperation resourcesOperation = createResourcesOperation(editOperation, false);
		EditEmpoweredAccessOperation simpleAccessOperation = createAccessOperation(editOperation, false, AccessType.simple);
		EditEmpoweredAccessOperation secureAccessOperation = createAccessOperation(editOperation, false, AccessType.secure);

		initForm(frm, editOperation,resourcesOperation, simpleAccessOperation, secureAccessOperation);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmpoweredPersonForm frm = (EditEmpoweredPersonForm) form;
		ActionForward forward = null;
		clearEmptyFields(frm, Boolean.parseBoolean((String) frm.getField("resident")));

		//проверяем сначала на валидность pin, потому как save и savePINForm независимо друг от друга сохраняют результат.
		if (!checkPINForm((EditPersonForm) form, request))
		{
			forward = mapping.findForward(FORWARD_START);
		}
		
		if (!saveForm(frm, request) || !savePINForm(frm, request))
		{
			forward = mapping.findForward(FORWARD_START);
		}
		initForm(frm);
		if (forward != null)
			return forward;
		return RedirectWithPersonIdAndSelfId(mapping, request, frm);
	}

	private static ActionForward RedirectWithPersonIdAndSelfId(ActionMapping mapping, HttpServletRequest request, EditEmpoweredPersonForm frm)
	{
		ActionForward actionForward = PersonUtils.redirectWithPersonId(mapping.findForward(FORWARD_SUCCESS), request);
		//TODO проверить корректность замены frm.gerEmpoweredPerson().getId() на frm.getId(). Т.к. в activate не установлен empoweredPerson
		actionForward.setPath(actionForward.getPath() + "&id=" + frm.getId());
		return actionForward;
	}

	protected boolean processEmpoweredRegistrationForm(EditEmpoweredPersonForm frm, HttpServletRequest request) throws Exception
	{
		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());

		FormProcessor<ActionMessages, ?> processor =
				createFormProcessor(fieldsSource, getFormBuilder().buildEmpoweredRegistrationForm());

		boolean result = processor.process();
		if (!result)
		{
			saveErrors(request, processor.getErrors());
		}
		return result;
	}

	//TODO Убрать: в jsp использовать clientButton
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception 
	{
		super.updateFormAdditionalData(form, operation);

		EditEmpoweredPersonForm frm = (EditEmpoweredPersonForm) form;

		EditEmpoweredPersonOperation editOperation = (EditEmpoweredPersonOperation) operation;

		EditEmpoweredAccessOperation simpleAccessOperation = createAccessOperation(editOperation, true, AccessType.simple);
		EditEmpoweredAccessOperation secureAccessOperation = createAccessOperation(editOperation, true, AccessType.secure);

		EditEmpoweredResourcesOperation resourcesOperation = createResourcesOperation(editOperation, frm.getId() != null);
		ActivePerson empoweredPerson = editOperation.getEntity();

		//if (empoweredPerson.getId() != null)
		// TODO подумать как обойти то, что при добавлении представителю не создается логина в базе
		//{
			// в зависимости от наличия доступа к операции LinkPINOperation либо проставлять id из пин-конверта либо просто генерить существующими алгоритмами
			if (checkAccess("ViewLinkPINOperation"))
			{
				LinkPINOperation linkPINOperation = createLinkPINOperation(empoweredPerson.getId(), true);
				updatePinFieldForm(linkPINOperation, frm);
			}
			else
			{
				updatePIN2(frm, editOperation.getEntity());
			}
		//}

		initForm(frm, editOperation, resourcesOperation, simpleAccessOperation, secureAccessOperation);

		//счета
		Collection<AccountLink> currentAccountLinks = resourcesOperation.getCurrentAccountLinks();
		int i = 0;
		String[] selectedAcconts = new String[currentAccountLinks.size()];

		for (AccountLink accountLink : currentAccountLinks)
		{
			selectedAcconts[i] = accountLink.getAccount().getNumber();
			i++;
		}

		frm.setSelectedAccountLinks(selectedAcconts);

		//карты
		Collection<CardLink> currentCardLinks = resourcesOperation.getCurrentCardLinks();
		i = 0;
		String[] selectedCards = new String[currentCardLinks.size()];

		for (CardLink cardLink : currentCardLinks)
		{
			selectedCards[i] = cardLink.getNumber();
			i++;
		}

		frm.setSelectedCardLinks(selectedCards);
	}

	private EditEmpoweredAccessOperation createAccessOperation(EditEmpoweredPersonOperation editOperation, boolean view, AccessType accessType) throws BusinessLogicException, BusinessException
	{
		EditEmpoweredAccessOperation operation;
		if(view)
			operation = createOperation("ViewEmpoweredAccessOperation");
		else
			operation = createOperation(EditEmpoweredAccessOperation.class);
		operation.initialize(editOperation.getEntity(), editOperation.getPerson(),accessType);
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditEmpoweredPersonOperation operation;
		if (frm.getId() != null)
			operation = createOperation("ViewEmpoweredPersonOperation");
		else
			operation = createOperation(EditEmpoweredPersonOperation.class);

		return  initializeEditOperation(operation, frm);
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditEmpoweredPersonOperation operation = createOperation(EditEmpoweredPersonOperation.class);

		return initializeEditOperation(operation, frm); 
	}

	private EditEntityOperation initializeEditOperation(EditEmpoweredPersonOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditEmpoweredPersonForm frm = (EditEmpoweredPersonForm) form;

		if (frm.getId() != null)
			operation.initialize(frm.getPerson(), frm.getId());
		else if (frm.getPerson() != null)
		{
			operation.initializeNew(frm.getPerson(), (String) frm.getField("clientId"));
			frm.setField("clientId", operation.getEntity().getClientId());
		}
		else
			throw new BusinessException("invalid url");
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return null;
	}

	private EditEmpoweredResourcesOperation createResourcesOperation(EditEmpoweredPersonOperation editOperation, boolean view) throws BusinessLogicException, BusinessException
	{
		EditEmpoweredResourcesOperation operation;
		if(view)
			operation = createOperation("ViewEmpoweredResourcesOperation");
		else
			operation = createOperation(EditEmpoweredResourcesOperation.class);

		operation.initialize(editOperation.getEntity(), editOperation.getPerson());

		return operation;
	}

	protected boolean save(EditPersonForm frm, Form editForm, HttpServletRequest request) throws Exception
	{
		EditPersonOperation operation = createOperation(EditPersonOperation.class);
		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldsSource, editForm);

		operation.setPersonId(frm.getPerson());

		boolean result = processor.process();
		if (result)
		{
			updateEntity(operation.getPerson(), processor.getResult());
			operation.save();
			result = true;
		}
		else
		{
			saveErrors(request, processor.getErrors());
			result = false;
		}

		updateFormPerson(operation.getPerson(), frm);

		return result;
	}

	protected void updateFormPerson(Person person, ActionForm form)
	{
		EditPersonForm frm = (EditPersonForm) form;
		frm.setActivePerson(person);
	}
}
