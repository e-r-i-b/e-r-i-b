package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.payments.forms.validators.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.person.*;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 05.10.2005 Time: 20:56:10 */
public class ListPersonResourcesAction extends ListActionBase
{
    protected Map<String, String> getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();

        map.put("button.importResources", "importResources");
        map.put("button.add.account", "addAccount");
        map.put("button.add.card", "addCard");
        map.put("button.save.payment.ability", "savePaymentAbilities");

        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetResourcesOperation operation = createOperation(GetResourcesOperation.class);
		operation.setPersonId(PersonUtils.getPersonId(currentRequest()));

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetResourcesOperation   op  = (GetResourcesOperation)   operation;
		addLogParameters(new BeanLogParemetersReader("Клиент", op.getPerson()));
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
        ListPersonResourcesForm frm = (ListPersonResourcesForm) form;
		GetResourcesOperation   op  = (GetResourcesOperation)   operation;

		ActivePerson person = op.getPerson();
		Department department = op.getPersonDepartment();

		frm.setActivePerson(person);
	    frm.setDepartment(department);

		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
        frm.setNeedAccount(propertiesPaymentsConfig.getNeedAccount());

	    List<AccountLink> resources = op.getAccounts();
        frm.setAccounts(resources);
	    if(frm.getNeedAccount())
	    {
			String[] paymentAbleAccountIds = this.getPaymentAbleAccountsIds(resources);
            frm.setSelectedPaymentAbleAccounts(paymentAbleAccountIds);
	    }
        frm.setCards(op.getCards());
		updateAdditionalResources(person, op, frm);
	    frm.setModified(PersonOperationMode.shadow.equals(op.getMode()));
    }

	protected void updateAdditionalResources(ActivePerson person, GetResourcesOperation op, ListPersonResourcesForm frm) throws BusinessException, BusinessLogicException
	{
		frm.setImAccounts(op.getIMAccounts());
	}

    public ActionForward importResources(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListPersonResourcesForm frm = (ListPersonResourcesForm) form;
        Long personId = PersonUtils.getPersonId(request);
	    PersonService personService = new PersonService();
	    Person person = personService.findById(personId);

	    AccountOpenOwnValidator validator = new AccountOpenOwnValidator();
	    validator.setParameter(AccountOpenOwnValidator.USE_PAREMETERS_INSTEAD_MAP_VALUES, "true");

        String[] selectedIds = frm.getSelectedResources();

        boolean isAccounts = frm.getResourcesType().equals("accounts");

	    AddResourcesOperation operation = null;
	    if (isAccounts)
	    {
		    operation = createOperation(AddAccountOperation.class);
		    validator.setParameter(AccountOpenOwnValidator.FIND_CARDS, "false");

	    }
	    else
	    {
		    operation = createOperation(AddCardOperation.class);
		    validator.setParameter(AccountOpenOwnValidator.FIND_CARDS, "true");
	    }

	    operation.setPersonId(personId);


        for (String selectedId : selectedIds)
        {
	        validator.setParameter("clientId", person.getClientId());
	        validator.setParameter("accountId", selectedId);

	        try
	        {
		       if (validator.validate(null))
		       {
			       operation.addResource(selectedId);
		       }
		       else
		       {
			       log.info("[Ошибка валидации(ListPersonResourcesAction.importResources)]: " + validator.getMessage() + ". " +
					       "MultiFieldsValidator: " + validator.getClass().getName() + ". ");
				   ActionMessages msgs = new ActionMessages();
		    	   msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(validator.getMessage(), false));
				   saveMessages(request, msgs);
			       return start(mapping, form, request, response);

		       }

		        addLogParameters(new SimpleLogParametersReader("ID добавляемой сущности", selectedId));
	        }
	        catch (BusinessLogicException e)
	        {
		    	ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveMessages(request, msgs);
	        }
        }

        String result = operation.save();
        if(result!=null)
        {
			ActionMessages msgs = new ActionMessages();
	    	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result, false));
			saveMessages(request, msgs);
        }
        frm.setSelectedResources(new String[0]);
	    stopLogParameters();
        return start(mapping, form, request, response);
    }

    public ActionForward addAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Long                             personId     = PersonUtils.getPersonId(request);
        ListPersonResourcesForm          frm          = (ListPersonResourcesForm) form;
        FieldValuesSource                fieldsSource = new RequestValuesSource(request);
        FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource, getNewAccountForm());

        if (processor.process())
        {
            // сохранение счета в БД
            String                newAccount = (String) processor.getResult().get("newAccount");
            AddAccountOperation operation  = createOperation(AddAccountOperation.class);

            operation.setPersonId(personId);
			try
			{
				operation.addResource(newAccount);
				addLogParameters(new BeanLogParemetersReader("Клиент", operation.getPerson()));
				addLogParameters(new CollectionLogParametersReader("Добавляемые счета", operation.getNewAccounts()));
			}
			catch(BusinessException ex)
			{
				ActionMessages msgs = new ActionMessages();
		    	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Такой счет уже добавлен в систему", false));
				saveMessages(request, msgs);
			}
	        catch(BusinessLogicException ex)
			{
				ActionMessages msgs = new ActionMessages();
		    	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
				saveMessages(request, msgs);
			}

	        operation.switchToShadow();
			String result = operation.save();
	        if(result!=null)
	        {
				ActionMessages msgs = new ActionMessages();
		    	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result, false));
				saveMessages(request, msgs);
	        }

            frm.setNewAccount("");
        }
        else
        {
            saveErrors(request, processor.getErrors());
        }
	    stopLogParameters();
        return start(mapping, form, request, response);
    }

    public ActionForward addCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Long                             personId     = PersonUtils.getPersonId(request);
        ListPersonResourcesForm          frm          = (ListPersonResourcesForm) form;
        FieldValuesSource                fieldsSource = new RequestValuesSource(request);
        FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource, getNewCardForm());

        if (processor.process())
        {
            // сохранение карты в БД
            String                newAccount = (String) processor.getResult().get("newCard");
            AddCardOperation operation  = createOperation(AddCardOperation.class);

            operation.setPersonId(personId);
			try
			{
				operation.addResource(newAccount);

				addLogParameters(new BeanLogParemetersReader("Клиент", operation.getPerson()));
				addLogParameters(new CollectionLogParametersReader("Добавленные карты", operation.getNewCards()));
			}
			catch(CardAlreadyExistsException ex)
			{
				ActionMessages msgs = new ActionMessages();
		    	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
				saveMessages(request, msgs);
			}
	        operation.switchToShadow();
			String result = operation.save();
	        if(result!=null)
	        {
				ActionMessages msgs = new ActionMessages();
		    	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result, false));
				saveMessages(request, msgs);
	        }


            frm.setNewCard("");
        }
        else
        {
            saveErrors(request, processor.getErrors());
        }
	    stopLogParameters();
        return start(mapping, form, request, response);
    }

    public ActionForward savePaymentAbilities(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Long                    personId        = PersonUtils.getPersonId(request);
        ListPersonResourcesForm frm             = (ListPersonResourcesForm) form;
        List<String>                    selectedIds     = Arrays.asList( frm.getSelectedPaymentAbleAccounts() );

        EditBankrollsOperation  editOperation   = createOperation(EditBankrollsOperation.class);
        editOperation.setPersonId(personId);

        GetResourcesOperation   operation       = createOperation(GetResourcesOperation.class);
        operation.setPersonId(personId);

        List<AccountLink>  resources   = operation.getAccounts();

	    try
	    {
		    if (selectedIds.size() > 3)
			{
				throw new BusinessLogicException("Вы можете выбрать не более трех счетов для оплаты");
			}

			for (AccountLink accountLink : resources)
			{
				String accountId = accountLink.getExternalId();
				editOperation.updateAccountPaymentAbility(accountLink, selectedIds.contains(accountId));
			}

		    addLogParameters(new BeanLogParemetersReader("Клиент", operation.getPerson()));
		    addLogParameters(new CollectionLogParametersReader("Счета для снятия платы", editOperation.getChangedAccounts()));

		    editOperation.validate();
			editOperation.switchToShadow();
			editOperation.update();
	    }
	    catch(BusinessLogicException ex)
	    {
		    ActionMessages messageContainer = new ActionMessages();
		    ActionMessage message = new ActionMessage(ex.getMessage(),false);
		    messageContainer.add(ActionMessages.GLOBAL_MESSAGE, message);
	        saveErrors(request, messageContainer);
	    }
	    stopLogParameters();
        return start(mapping, form, request, response);
    }

	/**
	 * Удаляет выделенные счета/карты клиента
	 * @param mapping    мапинг
	 * @param form       форма
	 * @param request    request
	 * @param response   response
	 * @return возвращает пользователя на нужную страницу
	 * @throws Exception
	 */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ListPersonResourcesForm frm = (ListPersonResourcesForm) form;
        String[] selectedAccounts = frm.getSelectedAccounts();
        String[] selectedCards = frm.getSelectedCards();

        if (selectedAccounts.length == 0 && selectedCards.length == 0)
        {
            addNoSelectedUserResourcesMessage(request);
        }
        else
        {
            Long personId = PersonUtils.getPersonId(request);
	        RemoveResourceOwnOperation operation;

	        if(checkAccess(RemoveResourceOwnOperation.class,"AccountManagement"))
	        {
				operation = createOperation(RemoveResourceOwnOperation.class,"AccountManagement");
				operation.setPersonId(personId);

				for (String selectedAccount : selectedAccounts)
				{
					operation.initialize(Long.decode(selectedAccount), AccountLink.class);
					operation.switchToShadow();
					operation.remove();

					addLogParameters(new BeanLogParemetersReader("Удаляемая сущность", operation.getExternalResourceLink()));
				}
	        }

	        if(checkAccess(RemoveResourceOwnOperation.class,"CardsManagement"))
	        {
		        operation = createOperation(RemoveResourceOwnOperation.class,"CardsManagement");
				for (String selectedCard : selectedCards)
				{
					operation.initialize(Long.decode(selectedCard), CardLink.class);
					operation.switchToShadow();
					operation.remove();

					addLogParameters(new BeanLogParemetersReader("Клиент", operation.getPerson()));
					addLogParameters(new BeanLogParemetersReader("Удаляемая сущность", operation.getExternalResourceLink()));
				}
	        }
        }
	    stopLogParameters();
        return start(mapping, form, request, response);
    }

    private void addNoSelectedUserResourcesMessage(HttpServletRequest request)
    {
        ActionMessages msg = new ActionMessages();
        msg.add("person", new ActionMessage("com.rssl.phizic.web.persons.noSelectedUserResources"));
        saveErrors(request, msg);
    }

    protected String[] getPaymentAbleAccountsIds(Collection<AccountLink> accountLinks)
    {
        List<AccountLink> links = new ArrayList<AccountLink>();

        for (Object object : accountLinks)
        {
            AccountLink accountLink = (AccountLink) object;

            if ( accountLink.getPaymentAbility() )
            {
				links.add( accountLink );
            }
        }

        String[] ids = new String[links.size()];
        int      i   = 0;

        for ( AccountLink account : links )
        {
            ids[i++] = account.getExternalId();
        }

        return ids;
    }

    private Form getNewAccountForm()
    {
        FormBuilder fb = new FormBuilder();

        FieldBuilder fieldBuilder;

        // Новый счет
        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Номер счета");
        fieldBuilder.setName("newAccount");
        fieldBuilder.setType("string");
	    fieldBuilder.addValidators(new RequiredFieldValidator());
	    fieldBuilder.addValidators(new RegexpFieldValidator("\\d{20}", "Номер счета должен состоять из 20 цифр"));
	    fieldBuilder.addValidators(new AccountCurrencyValidator("Валюта счета, который вы добавляете, отсутствует в справочнике валют. Обновите справочник и повторите попытку."));
	    fieldBuilder.addValidators(new RegexpFieldValidator("\\G423\\w*|\\G426\\w*", "Недопустимый номер счета клиента. В системе разрешена регистрация счетов, открытых только на балансовых счетах:423xx,426xx")); 
	    fieldBuilder.addValidators(new AccountIsUsedValidator());

	    FieldBuilder fieldBuilderCl = new FieldBuilder();
        fieldBuilderCl.setDescription("Идентификатор клиента");
        fieldBuilderCl.setName("clientId");
        fieldBuilderCl.setType("string");
	    fieldBuilderCl.addValidators(new RequiredFieldValidator());

	    AccountOpenOwnValidator openValidator = new AccountOpenOwnValidator();
        openValidator.setBinding(AccountOpenOwnValidator.FIELD_O1, "newAccount");
        openValidator.setBinding(AccountOpenOwnValidator.FIELD_O2, "clientId");
        openValidator.setMessage("Невозможно добавить закрытый счет");



	    SBRFAccountAdapterValidator accountAdapterValidator = new SBRFAccountAdapterValidator();
	    accountAdapterValidator.setBinding(SBRFAccountAdapterValidator.FIELD_O1, "newAccount");
        accountAdapterValidator.setBinding(SBRFAccountAdapterValidator.FIELD_O2, "clientId");

	    fb.setFormValidators( new MultiFieldsValidator[] {openValidator, accountAdapterValidator} );

        fb.addField(fieldBuilder.build());
	    fb.addField(fieldBuilderCl.build());

        return fb.build();
    }

    private Form getNewCardForm()
    {
        FormBuilder fb = new FormBuilder();

        FieldBuilder fieldBuilder;

        // Новая карта
        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Номер карты");
        fieldBuilder.setName("newCard");
        fieldBuilder.setType("string");
        fieldBuilder.setValidators(
                new RequiredFieldValidator(),
                new RegexpFieldValidator("\\d{15,16,18}", "Номер карты должен состоять из 15, 16 или 18 цифр"),
                new CardNumberChecksumFieldValidator()
        );

        fb.addField(fieldBuilder.build());

        return fb.build();
    }
}
