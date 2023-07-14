package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.operations.messaging.EditUserContactDataOperation;
import com.rssl.phizic.operations.person.AddPersonOperation;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.persons.EditPersonAction;
import com.rssl.phizic.web.persons.EditPersonForm;
import com.rssl.phizic.web.persons.PersonFormBuilder;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.struts.action.*;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFAddPersonAction extends EditPersonAction
{
	private static final String FORWARD_IMPORT_FAILURE = "ImportFailure";
	private static final PersonFormBuilder sbrfPersonFormBuilder = new SBRFPersonFormBuilder();

	protected PersonFormBuilder getFormBuilder()
	{
		return sbrfPersonFormBuilder;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		PersonFormBuilder formBuilder = getFormBuilder();
		Form editForm = formBuilder.getPartiallyFilledForm();
		ActionForward actionForward;

		try
		{
			//проверяем сначала на валидность pin, потому как save и savePINForm независимо друг от друга сохраняют результат.
			if (checkPINForm((EditPersonForm) form, request) &&
					save(form, editForm, request) &&
					savePINForm((EditPersonForm) form, request))
			{
				EditPersonForm frm = (EditPersonForm) form;
				actionForward = new ActionForward(mapping.findForward(FORWARD_SUCCESS));
				actionForward.setPath(actionForward.getPath() + "?person=" + frm.getPerson());
			}
			else
			{
				actionForward = mapping.findForward(FORWARD_FAILURE);
			}
			AddPersonOperation operation = createAddOperation(form);
			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Первоначальные данные", operation.getEntity()),
					new FormLogParametersReader("Данные введенные пользователем", editForm, ((EditPersonForm)form).getFields())
				));

			initForm(operation, form);
			return actionForward;
		}
		catch(BusinessLogicException be)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(be.getMessage(), false));
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_FAILURE);
		}
	}

	private boolean save(ActionForm form, Form editForm, HttpServletRequest request)
			throws Exception
	{
		EditPersonForm frm = (EditPersonForm) form;
		clearEmptyFields(frm, Boolean.parseBoolean((String) frm.getField("resident")));

		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldsSource, editForm);
		boolean result = false;

		//в том случае если пользователь не хочет получать sms, он не может выбрать формат, поэтому установим его сами
		if (!frm.getField("messageService").equals("sms"))
		{
			frm.setField("SMSFormat", TranslitMode.DEFAULT.toString());
		}

		final AddPersonOperation operation = createAddOperation(form);

		if (processor.process())
		{
			Map<String, Object> values = processor.getResult();
			ActivePerson person = operation.getPerson();
			updatePerson(person, values);

			final SetupNotificationOperation notificationOperation = createOperation(SetupNotificationOperation.class);
			notificationOperation.initialize(person, UserNotificationType.operationNotification);
			notificationOperation.setChannel((String) values.get("messageService"));
			final EditUserContactDataOperation contactDataOperation = createOperation(EditUserContactDataOperation.class);
			contactDataOperation.initialize(person.getLogin());
			contactDataOperation.setMobilePhone((String)values.get("mobilePhone"));
			contactDataOperation.setEmailAddress((String)values.get("email"));
			contactDataOperation.setTranslit(TranslitMode.valueOf((String)values.get("SMSFormat")));

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					operation.save();
					contactDataOperation.save();
					notificationOperation.saveNotification();
					return null;
				}
			});

			request.setAttribute("$$newId", operation.getPersonId());
			frm.setPerson(operation.getPersonId());
			result = true;
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		initForm(operation, frm);

		return result;
	}

	private AddPersonOperation createAddOperation(ActionForm form) throws BusinessException, BusinessLogicException
	{
		EditPersonForm frm = (EditPersonForm) form;
		AddPersonOperation operation = createOperation(AddPersonOperation.class);

		String clientId = (String) frm.getField("clientId");
		if (clientId == null || clientId.length() == 0)
		{
			clientId = null;
		}
		String agreementNumber = (String) frm.getField("agreementNumber");
		if (agreementNumber == null || agreementNumber.length() == 0)
		{
			agreementNumber = null;
		}

		operation.newInstance(clientId, agreementNumber);
		ActivePerson person = operation.getPerson();
		frm.setField("clientId", person.getClientId());
		frm.setField("agreementNumber", person.getAgreementNumber());
		String pinEnvelopeNumber = (String)frm.getField("pinEnvelopeNumber");
		if ((pinEnvelopeNumber == null || (pinEnvelopeNumber != null && pinEnvelopeNumber.length() == 0)) && !checkAccess("ViewLinkPINOperation"))
	    {
		    updatePIN2(frm, person);
	    }
		return operation;
	}

	private void initForm(AddPersonOperation operation, ActionForm form) throws Exception
	{
		EditPersonForm frm = (EditPersonForm) form;
		frm.setActivePerson(operation.getPerson());
		frm.setDepartment(operation.getDepartment());
		frm.setPersonDocuments(operation.getPerson().getPersonDocuments());
		frm.setDocumentTypes(operation.getDocumentTypes());
	}

	protected void updatePerson(Person person, Map<String, Object> result) throws Exception
	{
		person.setFirstName((String) result.get("firstName"));
		person.setSurName((String) result.get("surName"));
		person.setPatrName((String) result.get("patrName"));
		person.setBirthDay(DateHelper.toCalendar((Date) result.get("birthDay")));
		person.setBirthPlace((String) result.get("birthPlace"));

		updatePersonsDocuments(person, result);

		Address registrationAddress = new Address();

		registrationAddress.setPostalCode((String) result.get("registrationPostalCode"));
		registrationAddress.setProvince((String) result.get("registrationProvince"));
		registrationAddress.setDistrict((String) result.get("registrationDistrict"));
		registrationAddress.setCity((String) result.get("registrationCity"));
		registrationAddress.setStreet((String) result.get("registrationStreet"));
		registrationAddress.setHouse((String) result.get("registrationHouse"));
		registrationAddress.setBuilding((String) result.get("registrationBuilding"));
		registrationAddress.setFlat((String) result.get("registrationFlat"));

		person.setRegistrationAddress(registrationAddress);
		person.setHomePhone((String) result.get("homePhone"));
		person.setJobPhone((String) result.get("jobPhone"));

		Address residenceAddress = new Address();
        residenceAddress.setPostalCode((String) result.get("residencePostalCode"));
		residenceAddress.setProvince((String) result.get("residenceProvince"));
		residenceAddress.setDistrict((String) result.get("residenceDistrict"));
		residenceAddress.setCity((String) result.get("residenceCity"));
		residenceAddress.setStreet((String) result.get("residenceStreet"));
		residenceAddress.setHouse((String) result.get("residenceHouse"));
		residenceAddress.setBuilding((String) result.get("residenceBuilding"));
		residenceAddress.setFlat((String) result.get("residenceFlat"));
		residenceAddress.setUnparseableAddress((String) result.get("residenceAddress"));

		person.setResidenceAddress(residenceAddress);
		person.setMobileOperator((String) result.get("mobileOperator"));
		person.setAgreementNumber((String) result.get("agreementNumber"));
		person.setGender((String) result.get("gender"));
		person.setCitizenship((String) result.get("citizen"));
		UserDocumentService.get().resetUserDocument(person.getLogin(), DocumentType.INN, (String) result.get("inn"));
		person.setSecretWord((String) result.get("secretWord"));

		// В СБРФ дата начала договора это дата начала обслуживания
		Date serviceInsertionDate = (Date) result.get("serviceInsertionDate");
		Calendar serviceInsertionCalendar = null;

		if (serviceInsertionDate != null)
		{
			serviceInsertionCalendar = new GregorianCalendar();
			serviceInsertionCalendar.setTime(serviceInsertionDate);
		}

		person.setServiceInsertionDate(serviceInsertionCalendar);
		person.setAgreementDate(serviceInsertionCalendar);

		Date prolongationRejectionDate = (Date) result.get("prolongationRejectionDate");
		Calendar prolongationRejectionCalendar = null;

		if (prolongationRejectionDate != null)
		{
			prolongationRejectionCalendar = new GregorianCalendar();
			prolongationRejectionCalendar.setTime(prolongationRejectionDate);
		}

		person.setProlongationRejectionDate(prolongationRejectionCalendar);

		person.setContractCancellationCouse((String)result.get("contractCancellationCouse"));
		person.setIsResident((Boolean.parseBoolean((String)result.get("resident"))));
		String displayClientId = IDHelper.restoreOriginalId((String)result.get("clientId"));//TODO. что с этим делать?
		person.setDisplayClientId(displayClientId);

		UserPropertiesConfig.processUserSettingsWithoutPersonContext(person.getLogin(), new SettingsProcessor<Void>()
		{
			public Void onExecute(UserPropertiesConfig userProperties)
			{
				userProperties.setUseOfert(true);
				return null;
			}
		});
	}
}
