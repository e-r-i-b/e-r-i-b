package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.GuestPersonData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 14.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class PersonInfoSource extends CachedEntityListSourceBase
{
	private static final DepartmentService departmentService = new DepartmentService();
	private String ENTRY_TAG="person";

	public PersonInfoSource(EntityListDefinition definition)
	{
		super(definition);
	}

	private ActivePerson getCurrentPerson()
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		if (provider == null)
		{
			//в контексте сотрудника ничего не возвращаем
			return null;
		}
		PersonData personData = provider.getPersonData();
		if (personData == null)
		{
			return null;
		}
		return personData.getPerson();
	}

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
        ActivePerson curPerson = getCurrentPerson();

		EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

	    builder.openEntityTag(ENTRY_TAG);

		if (curPerson == null)
		{
			//анонимный клиент или админ.
			builder.closeEntityTag();
			builder.closeEntityListTag();
			return convertToReturnValue(builder.toString());
		}

		if (PersonHelper.isGuest())
		{
			GuestPersonData personData = (GuestPersonData) PersonContext.getPersonDataProvider().getPersonData();

			builder.appentField("firstName", curPerson.getFirstName() );
			builder.appentField("surName",   curPerson.getSurName() );
			builder.appentField("patrName",  curPerson.getPatrName() );
			builder.appentField("birthDay", nullSafeFormatDate(curPerson.getBirthDay()));
			builder.appentField("REGULAR_PASSPORT_RF-number", personData.getDocumentNumber());
			builder.appentField("REGULAR_PASSPORT_RF-series", personData.getDocumentSeries());

			GuestLogin guestLogin = (GuestLogin) personData.getLogin();
			if (guestLogin!=null && guestLogin.getAuthPhone()!=null)
				builder.appentField("mobilePhone", guestLogin.getAuthPhone());
			builder.closeEntityTag();
			builder.closeEntityListTag();
			return convertToReturnValue(builder.toString());
		}

		builder.appentField("id", curPerson.getId() == null ? null : curPerson.getId().toString() );
		builder.appentField("clientId", curPerson.getClientId() );
		builder.appentField("firstName", curPerson.getFirstName() );
		builder.appentField("surName", curPerson.getSurName() );
		builder.appentField("patrName", curPerson.getPatrName() );

		builder.appentField("birthDay", nullSafeFormatDate(curPerson.getBirthDay()));
		builder.appentField("birthPlace", curPerson.getBirthPlace() );

		builder.appentField("email", curPerson.getEmail() );
		builder.appentField("homePhone", curPerson.getHomePhone() );
		builder.appentField("jobPhone", curPerson.getJobPhone() );
		builder.appentField("mobilePhone", getSimplePhone(curPerson.getMobilePhone()));
		Collection<String> phones = MobileBankManager.getPhones(curPerson.getLogin());
		Iterator<String> iterator = phones.iterator();
		builder.appentField("additionalPhone1", getNextSimplePhone(iterator));
	    builder.appentField("additionalPhone2", getNextSimplePhone(iterator));
		builder.appentField("status", curPerson.getStatus() );
		builder.appentField("fullName", curPerson.getFullName() );
		TranslitMode smsFormat = curPerson.getSMSFormat();
		builder.appentField("SMSFormat", smsFormat != null ? smsFormat.toString() : null);
		builder.appentField("cardNumber", curPerson.getLogin().getLastLogonCardNumber());
		builder.appentField("ipAddress", curPerson.getLogin().getLastIpAddress());
	    builder.appentField("tbCode", curPerson.getLogin().getLastLogonCardDepartment());
	    builder.appentField("logonCardOSB", curPerson.getLogin().getLastLogonCardOSB());
	    builder.appentField("logonCardVSP", curPerson.getLogin().getLastLogonCardVSP());

	    appendAddressFields(builder, curPerson.getResidenceAddress(), "residenceAddress");
	    appendAddressFields(builder, curPerson.getRegistrationAddress(),"registrationAddress");

		builder.appentField("mobileOperator", curPerson.getMobileOperator() );
		builder.appentField("agreementNumber", curPerson.getAgreementNumber() );
        builder.appentField("isResident", nullSafeBooleanToString(curPerson.getIsResident()) );

	    builder.appentField("agreementDate", nullSafeFormatDate(curPerson.getAgreementDate()));
	    builder.appentField("serviceInsertionDate", nullSafeFormatDate(curPerson.getServiceInsertionDate()));

	    if(!StringHelper.isEmpty(curPerson.getGender()))
	    {
		    if (curPerson.getGender().equals("F"))
	          builder.appentField("gender", "Ж" );
	        else
		      builder.appentField("gender", "М" );
	    }

		builder.appentField("citizen", curPerson.getCitizenship() );

	    builder.appentField("prolongationRejectionDate", nullSafeFormatDate(curPerson.getProlongationRejectionDate()));

	    builder.appentField("discriminator", curPerson.getDiscriminator() );
		builder.appentField("contractCancellationCouse", curPerson.getContractCancellationCouse() );
	    builder.appentField("currentDepartmentId", Long.toString( curPerson.getDepartmentId() ) );
	    builder.appentField("inn", curPerson.getInn() );

		Department department = null;
		if (curPerson.getDepartmentId() != null)
		{
			department = departmentService.findById(curPerson.getDepartmentId());
		}

	    builder.appentField("region",                 department != null ? department.getCode().getFields().get("region") : null);
	    builder.appentField("office",                 department != null ? department.getCode().getFields().get("office") : null);
	    builder.appentField("branch",                 department != null ? department.getCode().getFields().get("branch") : null);

	    builder.appentField("tbName",                 department != null ? department.getName() : null);
		builder.appentField("departmentId",           department != null ? department.getId().toString() : null);
		builder.appentField("departmentPossibleLoan", department != null ? String.valueOf(department.isPossibleLoansOperation()) : "false");

	    appendDocumentsFields(builder, curPerson);

	    builder.closeEntityTag();
	    builder.closeEntityListTag();

        return convertToReturnValue(builder.toString(), curPerson);
    }

	private String getSimplePhone(String phone)
	{
		String result = StringUtils.EMPTY;
		if (StringHelper.isNotEmpty(phone))
		{
			result = PhoneNumberFormat.SIMPLE_NUMBER.translate(phone);
		}

		return result;
	}

	private String getNextSimplePhone(Iterator<String> iterator)
	{
		String result = StringUtils.EMPTY;
		try
		{
			if(!iterator.hasNext())
			{
				return result;
			}

			result = getSimplePhone(iterator.next());
		}
		catch (NumberFormatException e)
		{
			log.error(e.getMessage(), e);
		}

		return result;
	}

	private String nullSafeFormatDate(Date date)
	{
		if (date == null)
		{
			return null;
		}
		return nullSafeFormatDate(DateHelper.toCalendar(date));
	}

	private String nullSafeFormatDate(Calendar calendar)
	{
		if (calendar == null)
		{
			return null;
		}
		return XMLDatatypeHelper.formatDate(calendar);
	}

	private String nullSafeBooleanToString(Boolean aBoolean)
	{
		if (aBoolean == null)
		{
			return null;
		}
		return aBoolean.toString();
	}

	private void appendDocumentsFields(EntityListBuilder builder, ActivePerson curPerson)
	{
//TODO --------------------->оставлено для совместимости
		if (curPerson.getPersonDocuments() != null)
		{
			for (PersonDocument document : curPerson.getPersonDocuments())
			{
				builder.appentField("passportNumber", document.getDocumentNumber() );
				builder.appentField("passportSeries", document.getDocumentSeries() );
				builder.appentField("passportIssueBy", document.getDocumentIssueBy() );
				builder.appentField("passportIssueDate", nullSafeFormatDate (document.getDocumentIssueDate()) );
				builder.appentField("identityTypeName", document.getDocumentName() );

				PersonDocumentType documentType = document.getDocumentType();
				if (documentType != null)
				{
					builder.appentField("identityType",  documentType.toString() );
					builder.appentField(documentType.toString()+"-number", document.getDocumentNumber() );
					builder.appentField(documentType.toString()+"-series", document.getDocumentSeries() );
					builder.appentField(documentType.toString()+"-issue-by", document.getDocumentIssueBy() );
					builder.appentField(documentType.toString()+"-issue-by-code", document.getDocumentIssueByCode() );
					builder.appentField(documentType.toString()+"-issue-date", nullSafeFormatDate (document.getDocumentIssueDate()) );
				}
			}
		}
		builder.appentField("inn", curPerson.getInn());
//TODO <---------------------оставлено для совместимости
	}

	private void appendAddressFields(EntityListBuilder builder, Address address, String prefix)
	{
		if (address == null){
			return;
		}
		builder.appentField(prefix, address.toString());
		builder.appentField(prefix+"-postal-code", address.getPostalCode());
		builder.appentField(prefix+"-province", address.getProvince());
		builder.appentField(prefix+"-district", address.getDistrict());
		builder.appentField(prefix+"-city", address.getCity());
		builder.appentField(prefix+"-street", address.getStreet());
		builder.appentField(prefix+"-building", address.getBuilding());
		builder.appentField(prefix+"-house", address.getHouse());
		builder.appentField(prefix+"-flat", address.getFlat());
	}
}
