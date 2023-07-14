package com.rssl.phizic.web.persons;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.pin.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TarifPlanConfigService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.person.EditPersonOperation;
import com.rssl.phizic.operations.person.PersonExtendedInfoOperationBase;
import com.rssl.phizic.operations.security.LinkPINOperation;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.PersonInfoUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;

import java.math.BigInteger;
import java.security.AccessControlException;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 09.09.2005 Time: 19:01:07 */
@SuppressWarnings({"OverlyLongMethod"})
public abstract class PersonActionBase extends EditActionBase
{
	private static final int USER_ID_LENGTH = 8;
	private static final int TRY_ITERATIONS = 10;
	private static final String PIN_CREATION_ERROR_MESSAGE = "ќшибка генерации PIN";

	protected static final Log phizLog = PhizICLogFactory.getLog(LogModule.Web);
	private static final PINValueGenerator pinValueGenerator = new PINValueGenerator();
	private static final PINEnvelopeCreator pinEnvelopeCreator = new PINEnvelopeCreator();
	private static final PINService pinService = new PINService();
	private static final TarifPlanConfigService tarifPlanConfigService = new TarifPlanConfigService();
	private static final ServiceProviderService providerService = new ServiceProviderService();

	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.close", "cancel");
		map.put("button.import", "importClient");

		return map;
	}

	/** ќбновл€еет значени€ переданного Person из map */
	protected void updateEntity(Object entity, Map<String, Object> result)  throws Exception
	{
		Person person = (Person) entity;
		person.setFirstName((String) result.get("firstName"));
		person.setSurName((String) result.get("surName"));
		person.setPatrName((String) result.get("patrName"));
		person.setBirthDay(DateHelper.toCalendar((Date) result.get("birthDay")));
		person.setBirthPlace((String) result.get("birthPlace"));

		updatePersonsDocuments(person, result);

        Address registrationAddress = person.getRegistrationAddress();
        if (registrationAddress == null)
        {
            registrationAddress = new Address();
        }
		registrationAddress.setUnparseableAddress((String) result.get("registrationAddress"));
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
		String mobilePhone;
		try
		{
			//ѕытаемс€ преобразовать номер к дес€тизначному. ≈сли не получаетс€, записываем как есть.
			String phone = (String) result.get("mobilePhone");
			if (StringHelper.isEmpty(phone))
				mobilePhone = "";
			else
			{
				mobilePhone = PhoneNumberFormat.SIMPLE_NUMBER.translate(phone);
			}
		}
		catch (NumberFormatException ignored)
		{
			mobilePhone = (String) result.get("mobilePhone");
		}

	    Address residenceAddress = person.getResidenceAddress();
        if (residenceAddress == null)
        {
            residenceAddress = new Address();
        }
		residenceAddress.setUnparseableAddress((String) result.get("residenceAddress"));
		residenceAddress.setPostalCode((String) result.get("residencePostalCode"));
		residenceAddress.setProvince((String) result.get("residenceProvince"));
		residenceAddress.setDistrict((String) result.get("residenceDistrict"));
		residenceAddress.setCity((String) result.get("residenceCity"));
		residenceAddress.setStreet((String) result.get("residenceStreet"));
		residenceAddress.setHouse((String) result.get("residenceHouse"));
		residenceAddress.setBuilding((String) result.get("residenceBuilding"));
		residenceAddress.setFlat((String) result.get("residenceFlat"));
		person.setResidenceAddress(residenceAddress);

		person.setMobileOperator((String) result.get("mobileOperator"));
		person.setAgreementNumber((String) result.get("agreementNumber"));
		person.setGender((String) result.get("gender"));
		person.setCitizenship((String) result.get("citizen"));
		UserDocumentService.get().resetUserDocument(person.getLogin(), DocumentType.INN, (String) result.get("inn"));
		person.setSecretWord((String) result.get("secretWord"));
		// ¬ —Ѕ–‘ дата начала договора это дата начала обслуживани€
		Date serviceInsertionDate = (Date) result.get("serviceInsertionDate");
		person.setServiceInsertionDate(DateHelper.toCalendar(serviceInsertionDate));
		person.setAgreementDate(DateHelper.toCalendar(serviceInsertionDate));
		Date prolongationRejectionDate = (Date)result.get("prolongationRejectionDate");
        person.setProlongationRejectionDate(DateHelper.toCalendar(prolongationRejectionDate));
		person.setContractCancellationCouse((String)result.get("contractCancellationCouse"));
		person.setIsResident((Boolean.parseBoolean((String)result.get("resident"))));
		BigInteger departmentId = (BigInteger) result.get("departmentId");
		person.setDepartmentId(departmentId == null ? null : departmentId.longValue());
		if (departmentId==null){
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			person.setDepartmentId(employeeData.getEmployee().getDepartmentId());
		}
		//person.setLoanOfficeId(BigDecimal.valueOf(Long.parseLong((String)result.get("loanOfficeId"))));
	}

	private Set<PersonDocument> getPersonDocuments(Set<PersonDocument> documents, boolean identify)
	{
		Set<PersonDocument> personDocuments = new HashSet();
		for (PersonDocument pDocument: documents)
		{
			if (pDocument.isDocumentIdentify() == identify)
			{
				personDocuments.add(pDocument);
			}
		}
		return personDocuments;
	}

	private Set<PersonDocument> editDocument(Long documentId, Person person, boolean identify,  Map<String, Object> result, String prefix)
	{
		Set<PersonDocument> personDocuments = getPersonDocuments(person.getPersonDocuments(), identify);

		PersonDocumentType documentType = PersonDocumentType.valueOf((String) result.get(prefix + "Type"));
		boolean thisTypeOfDocumentAlreadyAdded = false;

		//добавл€ем новый документ и помечаем старые как неглавные
		if (documentId == null)
		{
			PersonDocument document = null;

			//проверим, что документ такого типа еще не существует у клиента
			// (id=null) не только тогда, когда мы добавл€ем новый документ, но и когда мы еще не сохранили старый введенный
			for (PersonDocument pdocument : personDocuments)
			{
				if (pdocument.getDocumentType().equals(documentType))
				{
					//мы нашли среди документов пользовател€ тот, что передан из формы - обновим его.
					updateDocumentParameters(pdocument, result, prefix, identify);
					document = pdocument;
					break;
				}
			}


			//если документ не найден - создадим новый, добавим его, сделав попутно главным.
			if (document == null )
			{
				document = new PersonDocumentImpl();
				updateDocumentParameters(document, result, prefix, identify);
				personDocuments.add(document);
			}

			if (!document.isEmpty())
			{
				MarkAllAsSecondary(personDocuments);
				document.setDocumentMain(true);
			}
		}
		//редактируем существующий документ и помечаем остальные как неглавные
		else
		{
			for(PersonDocument document : personDocuments)
			{
				if (document.getId().equals(documentId))
				{
					updateDocumentParameters(document, result, prefix, identify);
					if (document.isEmpty() && !document.getDocumentMain()) //стираем только пустой, не главный документ, иначе непон€тно кому ставить признак main
					{
						personDocuments.remove(document);
						continue;
					}
					document.setDocumentMain(true);
				}
				else
				{
					document.setDocumentMain(false);
				}
				personDocuments.add(document);
			}
		}
		return personDocuments;
	}

	/**
	 * просто помечает все документы пользовател€ как неосновные
	 * @param personDocuments
	 */
	private void MarkAllAsSecondary(Set<PersonDocument> personDocuments)
	{
		for(PersonDocument pdocument : personDocuments)
		{
			pdocument.setDocumentMain(false);
		}
	}

	protected void updatePersonsDocuments(Person person, Map<String, Object> result)
	{
		Set<PersonDocument> personDocuments = person.getPersonDocuments();

		//редактируем документ, удостовер€ющий личность
		Long documentId = (result.get("documentId").equals("null") || result.get("documentId").equals(""))?null:Long.valueOf((String) result.get("documentId"));
		personDocuments.addAll(editDocument(documentId, person, true, result, "document"));

		//редактируем документ, подтверждающий право на проживание на территоррии, если такой поддерживаетс€ конфигурацией, т.е. есть в jsp
		if (result.get("documentProveId")!=null)
		{
			Long documentProveId = (result.get("documentProveId").equals("null") || result.get("documentProveId").equals(""))?null:Long.valueOf((String) result.get("documentProveId"));
			personDocuments.addAll(editDocument(documentProveId, person, false, result, "documentProve"));
		}

		//ƒобавл€ем или редактируем миграционную карту
		addMigratoryCard(personDocuments,result);
		addPensionCertificate(personDocuments, result);
		person.setPersonDocuments(personDocuments);

	}

	private void updateDocumentParameters(PersonDocument document, Map<String, Object> result, String prefix, boolean identify)
	{
		document.setDocumentName((String)    result.get(prefix + "Name"));
		PersonDocumentType documentType = PersonDocumentType.valueOf((String) result.get(prefix + "Type"));
		document.setDocumentType(documentType);
		document.setDocumentNumber((String)  result.get(prefix + "Number"));
		document.setDocumentSeries((String)  result.get(prefix + "Series"));
		document.setDocumentIssueBy((String) result.get(prefix + "IssueBy"));
		document.setDocumentIssueByCode((String) result.get(prefix + "IssueByCode"));
		Calendar documentIssueDate = DateHelper.toCalendar((Date) result.get(prefix + "IssueDate"));
		document.setDocumentIssueDate(documentIssueDate);
		document.setDocumentIdentify(identify);
		document.setDocumentTimeUpDate(DateHelper.getCurrentDate());
	}

	private PersonDocument getPersonMigratoryCard(Set<PersonDocument> documents)
	{
		for (PersonDocument document: documents)
		{
			if (document.getDocumentType().equals(PersonDocumentType.MIGRATORY_CARD))
				return document;
		}
		PersonDocument personDocument = new PersonDocumentImpl();
		personDocument.setDocumentType(PersonDocumentType.MIGRATORY_CARD);
		return personDocument;
	}

	private PersonDocument getPersonPensionCertificate(Set<PersonDocument> documents)
	{
		for (PersonDocument document: documents)
		{
			if (document.getDocumentType().equals(PersonDocumentType.PENSION_CERTIFICATE))
				return document;
		}
		PersonDocument personDocument = new PersonDocumentImpl();
		personDocument.setDocumentType(PersonDocumentType.PENSION_CERTIFICATE);
		return personDocument;
	}

	private void addMigratoryCard(Set<PersonDocument> documents, Map<String, Object> result)
	{
		Object migratoryCardNumber = result.get("migratoryCardNumber");
		if (migratoryCardNumber == null || StringHelper.isEmpty( (String) migratoryCardNumber))
			return;
		PersonDocument personDocument = getPersonMigratoryCard(documents);
		personDocument.setDocumentNumber((String) migratoryCardNumber);
		Calendar documentFromDate = DateHelper.toCalendar((Date) result.get("migratoryCardFromDate"));
		personDocument.setDocumentIssueDate(documentFromDate);
		Calendar documentTimeUpDate = DateHelper.toCalendar((Date) result.get("migratoryCardTimeUpDate"));
		personDocument.setDocumentTimeUpDate(documentTimeUpDate);
		personDocument.setDocumentIdentify(false);
		documents.add(personDocument);
	}

	private void addPensionCertificate(Set<PersonDocument> documents, Map<String, Object> result)
	{
		Object pensionCertificate = result.get("pensionCertificate");
		if (pensionCertificate == null || StringHelper.isEmpty( (String) pensionCertificate))
			return;
		PersonDocument personDocument = getPersonPensionCertificate(documents);
		personDocument.setDocumentNumber((String) pensionCertificate);
		personDocument.setDocumentIdentify(false);
		documents.add(personDocument);
	}

	private void fillDocument(PersonDocument document, EditPersonForm frm, boolean isIdentify, String prefix)
	{
		if (!document.getDocumentMain() || (document.isDocumentIdentify() != isIdentify))
			return;
		frm.setField(prefix + "Id", document.getId());
		frm.setField(prefix + "Name", document.getDocumentName());
		frm.setField(prefix + "Type", document.getDocumentType());
		frm.setField(prefix + "Number", document.getDocumentNumber());
		frm.setField(prefix + "Series", document.getDocumentSeries());
		frm.setField(prefix + "IssueBy", document.getDocumentIssueBy());
		frm.setField(prefix + "IssueByCode", document.getDocumentIssueByCode());
		frm.setField(prefix + "IssueDate", DateHelper.toDate(document.getDocumentIssueDate()));
	}

	private void fillMigratoryCard(PersonDocument document, EditPersonForm frm)
	{
		if (!document.getDocumentType().equals(PersonDocumentType.MIGRATORY_CARD))
			return;
		frm.setField("migratoryCardNumber", document.getDocumentNumber());
		frm.setField("migratoryCardFromDate", DateHelper.toDate(document.getDocumentIssueDate()));
		frm.setField("migratoryCardTimeUpDate", DateHelper.toDate(document.getDocumentTimeUpDate()));
	}

	private void fillAddress(EditPersonForm frm, Address address, String prefix)
	{
		if (address == null){
			return;
		}
		frm.setField(prefix+"Address", address.getUnparseableAddress());
		frm.setField(prefix+"PostalCode", address.getPostalCode());
		frm.setField(prefix+"Province", address.getProvince());
		frm.setField(prefix+"District", address.getDistrict());
		frm.setField(prefix+"City", address.getCity());
		frm.setField(prefix+"Street", address.getStreet());
		frm.setField(prefix+"House", address.getHouse());
		frm.setField(prefix+"Building", address.getBuilding());
		frm.setField(prefix+"Flat", address.getFlat());
	}

	private void fillPensionCerificate(PersonDocument document, EditPersonForm frm)
	{
		if (!document.getDocumentType().equals(PersonDocumentType.PENSION_CERTIFICATE))
			return;
		frm.setField("pensionCertificate", document.getDocumentNumber());
	}

	protected void updateForm(EditFormBase form, Object entity) throws BusinessException
	{
		EditPersonForm frm = (EditPersonForm) form;
		Person person = (Person) entity;

		if (person == null)
		{
			return;
		}
		frm.setField("id", person.getId());
		frm.setField("loginId", person.getLogin().getId());
		frm.setField("displayClientId", person.getDisplayClientId());
		frm.setField("clientId", person.getClientId());
		frm.setField("firstName", person.getFirstName());
		frm.setField("surName", person.getSurName());
		frm.setField("patrName", person.getPatrName());
		frm.setField("login", person.getLogin().getUserId());
		frm.setField("birthDay", DateHelper.toDate(person.getBirthDay()));
		frm.setField("birthPlace", person.getBirthPlace());

		for (PersonDocument document : person.getPersonDocuments())
		{
			fillDocument(document,frm,true,"document");
			fillDocument(document,frm,false,"documentProve");
			fillMigratoryCard(document,frm);
			fillPensionCerificate(document, frm);
		}

		fillAddress(frm, person.getRegistrationAddress(), "registration");
		fillAddress(frm, person.getResidenceAddress(), "residence");

		frm.setField("residenceAddress", person.getResidenceAddress().getUnparseableAddress());
		frm.setField("email", person.getEmail());
		frm.setField("homePhone", person.getHomePhone());
		frm.setField("jobPhone", person.getJobPhone());

		ErmbProfileImpl ermbProfile = PersonInfoUtil.getErmbProfile(person.getId(), true);

		String mobilePhone;
		try
		{
			if (ermbProfile != null)
			{
				//ѕытаемс€ привести номер телефона к виду +7(xxx)xxxxxxx, если не получаетс€, подставл€ем как есть
				mobilePhone = PhoneNumberFormat.IKFL.translate(ermbProfile.getMainPhoneNumber());
			} else if (StringHelper.isEmpty(person.getMobilePhone()))
				mobilePhone = "";
				else
					mobilePhone = PhoneNumberFormat.IKFL.translate(person.getMobilePhone());
		}
		catch (NumberFormatException ignored)
		{
			if (ermbProfile != null)
				mobilePhone = ermbProfile.getMainPhoneNumber();
			else
				mobilePhone = person.getMobilePhone();
		}
		frm.setField("mobilePhone", mobilePhone);
		frm.setField("SMSFormat", person.getSMSFormat());
		if (StringHelper.isNotEmpty(mobilePhone))
		{
			try
			{
				ServiceProviderBase provider = providerService.getMobileOperatorByPhoneNumber(PhoneNumber.fromString(mobilePhone));
				if (provider == null)
					frm.setField("mobileOperator", "Ќеизвестно");
				else
					frm.setField("mobileOperator", provider.getName());
			}
			catch (NumberFormatException ex)
			{
				frm.setField("mobileOperator", "Ќеизвестно");
				phizLog.error(ex);
			}
		}

		frm.setField("agreementNumber", person.getAgreementNumber());
		frm.setField("gender", person.getGender());
		frm.setField("citizen", person.getCitizenship());
		frm.setField("inn", person.getInn());
		// ” ”ƒЅќ клиентов нет даты начала обслуживани€, она совпадает с датой договора
		frm.setField("serviceInsertionDate", person.getAgreementDate() == null ?
				DateHelper.toDate(person.getServiceInsertionDate()) : person.getAgreementDate().getTime());
		frm.setField("prolongationRejectionDate", DateHelper.toDate(person.getProlongationRejectionDate()));
        frm.setField("contractCancellationCouse", person.getContractCancellationCouse());
		frm.setField("secretWord", person.getSecretWord());
		frm.setField("resident", person.getIsResident());
		frm.setField("departmentId", person.getDepartmentId());
		frm.setField("SNILS", person.getSNILS());
		frm.setField("segmentCodeType",  person.getSegmentCodeType() != null ? person.getSegmentCodeType().getDescription() : null);
		if (person.getTarifPlanCodeType() != null)
		{
			TariffPlanConfig tariff = tarifPlanConfigService.getTarifPlanConfigByTarifPlanCodeType(person.getTarifPlanCodeType());
			frm.setField("tarifPlanCodeType", tariff != null ? tariff.getName() : null);
		}
		frm.setField("tarifPlanConnectionDate", person.getTarifPlanConnectionDate() != null ? DateHelper.toDate(person.getTarifPlanConnectionDate()) : null);
		frm.setField("managerId", person.getManagerId());
    }

	public void clearEmptyFields(EditPersonForm frm, boolean isResident)
	{
		if (isResident)
			clearNotResidentFields(frm);
		else
			clearEmptyDocument(frm);
	}

	private void clearNotResidentFields(EditPersonForm frm)
	{
		String prefix = "documentProve";
		frm.setField(prefix + "Id", null);
		frm.setField(prefix + "Type", null);
		frm.setField(prefix + "Number", null);
		frm.setField(prefix + "Series", null);
		frm.setField(prefix + "IssueBy", null);
		frm.setField(prefix + "IssueDate", null);

		frm.setField("migratoryCardNumber", null);
		frm.setField("migratoryCardFromDate", null);
		frm.setField("migratoryCardTimeUpDate", null);

		frm.setField("pensionCertificate", null);
	}

	//≈сли документ пустой, то и тип его передавать не правильно
	private void clearEmptyDocument(EditPersonForm frm)
	{
		String prefix = "documentProve";
		if (
				StringHelper.isEmpty((String)frm.getField(prefix + "Number"))
				&& StringHelper.isEmpty((String)frm.getField(prefix + "Series"))
			)
		{
			frm.setField(prefix + "Id", null);
			frm.setField(prefix + "Type", null);
		}
	}

	public void updatePinFieldForm(LinkPINOperation linkPINOperation, ActionForm form)
	{
		if( linkPINOperation.getPerson() != null )  // еще не сохран€ли
		{
			PINEnvelope pinEnvelope = linkPINOperation.getCurrentLinkedPINEnvelope();
			EditPersonForm frm = (EditPersonForm) form;
			String pinEnvelopeNumber = pinEnvelope == null ? (String)frm.getField("pinEnvelopeNumber") : pinEnvelope.getUserId();
			boolean needPINEnveloped = false;
			if (linkPINOperation.getPerson().getLogin().getPinEnvelopeId() == null)
			{
				needPINEnveloped = true;
			}

			frm.setField("pinEnvelopeNumber",    pinEnvelopeNumber);
			frm.setField("oldPinEnvelopeNumber", needPINEnveloped ? "" : pinEnvelopeNumber);
		}
	}

	private void updatePin(Long personId, String pin) throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		if( pin != null && pin.length() > 0 )
		{
			if (checkAccess("LinkPINOperation"))
			{
				LinkPINOperation linkPINOperation = createOperation(LinkPINOperation.class);
				linkPINOperation.setPersonId(personId);//
				linkPINOperation.setPin(pin);
				if( !linkPINOperation.isPINLinked() )
					linkPINOperation.link();
			}
			else
			{
				link(personId, pin);
			}
		}
	}

	private boolean needValidatePIN(String pin, String oldPin)
	{
		return !StringHelper.equals(pin, oldPin);
	}

	protected boolean savePINForm(EditPersonForm frm, HttpServletRequest request) throws Exception
	{
		String pin = (String) frm.getField("pinEnvelopeNumber");
		String oldPin = (String) frm.getField("oldPinEnvelopeNumber");

		if ( needValidatePIN(pin, oldPin) )
			return processPINForm(frm, request);

		return true;
	}
	//провер€ем новый pin если он введен на валидность.
	protected boolean checkPINForm(EditPersonForm frm, HttpServletRequest request)
			throws ParseException
	{
		String pin = (String) frm.getField("pinEnvelopeNumber");
		String oldPin = (String) frm.getField("oldPinEnvelopeNumber");

		if ( needValidatePIN(pin, oldPin) )
		{
			MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());

			FormProcessor<ActionMessages, ?> pinProcessor =
					createFormProcessor(fieldsSource, new PersonFormBuilder().buildPINForm());

			boolean result = pinProcessor.process();
			if (!result)
			{
				saveErrors(request, pinProcessor.getErrors());
			}
			return result;
		}
		return true;
	}
	protected boolean processPINForm(EditPersonForm frm, HttpServletRequest request) throws Exception
	{
		String pin = (String) frm.getField("pinEnvelopeNumber");

		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());

		FormProcessor<ActionMessages, ?> pinProcessor =
				createFormProcessor(fieldsSource, new PersonFormBuilder().buildPINForm());

		boolean result = pinProcessor.process();
		if (result)
		{
			updatePin(frm.getEditedPerson(), pin);
			frm.setField("oldPinEnvelopeNumber", pin);
		}
		else
		{
			saveErrors(request, pinProcessor.getErrors());
		}
		return result;
	}

	protected boolean activatePINForm(EditPersonForm frm, HttpServletRequest request) throws Exception
	{
		String pin = (String) frm.getField("pinEnvelopeNumber");
		String oldPin = (String) frm.getField("oldPinEnvelopeNumber");

		if ( oldPin == null || "".equals(oldPin) //pin не был задан
				|| needValidatePIN(pin, oldPin))
			return processPINForm(frm, request);
		return true;
	}


	protected PINEnvelope getPIN(Person person, Long departmentId) throws BusinessException
	{
		Login personLogin = person.getLogin();
		PINEnvelope pinEnvelope = null;
		if (personLogin != null)
		{
			pinEnvelope = pinService.findEnvelope(personLogin.getUserId());
		}

		if (pinEnvelope == null)
		{
			pinEnvelope = tryCreate(TRY_ITERATIONS, pinService.getLastRequestNumber()+1, departmentId);
		}

		if (pinEnvelope.getState().equals(PINEnvelope.STATE_NEW))
		{
			//
			pinEnvelope.setState(PINEnvelope.STATE_UPLOADED);
			pinEnvelope.setValue(pinEnvelope.getUserId());
			try
			{
				pinService.update(pinEnvelope);
			}
			catch(DuplicatePINException e)
			{
				// быть не может )))
				throw new BusinessException(e);
			}
		}

		return pinEnvelope;
	}

	private PINEnvelope tryCreate(int tryIterations, long requestNumber, long departmentId) throws BusinessException
	{
		try
		{
			String userId = pinValueGenerator.newUserId(USER_ID_LENGTH);
			return pinEnvelopeCreator.create(userId, requestNumber, departmentId);
		}
		catch (DuplicatePINException e)
		{
			if( tryIterations-- > 0 )
			{
				return tryCreate(tryIterations, requestNumber, departmentId);
			}
			else
			{
				throw new BusinessException(PIN_CREATION_ERROR_MESSAGE, e);
			}
		}
	}

	public void link(Long personId, String pin) throws BusinessException, SecurityLogicException, BusinessLogicException
	{
		EditPersonOperation editOperation;
		editOperation = createOperation(EditPersonOperation.class);
		editOperation.setPersonId(personId);

		final PINEnvelope envelope = pinService.findEnvelope(pin);

		SecurityService securityService = new SecurityService();
		try
		{
			securityService.linkPinEvenlope(editOperation.getPerson().getLogin(), envelope, editOperation.getInstanceName());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	public void updatePIN2(EditPersonForm frm, ActivePerson person) throws BusinessException
	{
		PINEnvelope pinEnvelope = getPIN(person, person.getDepartmentId());
		Login       personLogin = person.getLogin();

		frm.setField("pinEnvelopeNumber",    pinEnvelope.getUserId());
		frm.setField("oldPinEnvelopeNumber", personLogin == null ? null : personLogin.getUserId());
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		SetupNotificationOperation notificationOperation;
		try
		{
			notificationOperation = createOperation(SetupNotificationOperation.class);
		}
		catch (AccessControlException ignore)
		{
			notificationOperation = createOperation("ViewNotificationOperation");
		}

		notificationOperation.initialize(((PersonExtendedInfoOperationBase) operation).getPerson(), UserNotificationType.operationNotification);
		frm.setField("messageService", notificationOperation.getChannel(UserNotificationType.operationNotification));
	}
}
