package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.clients.list.ClientNodeState;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.gate.clients.stoplist.ClientStopListState;
import com.rssl.phizic.logging.extendedLogging.ClientExtendedLoggingEntry;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
public class EditPersonForm extends EditFormBase
{
	public static final Form EXTENDED_LOGGING_FORM = createExtendedLoggingForm();
	public static final String TERMLESS_FIELD   = "termlessExtendedLogging";
	public static final String TO_DATE_FIELD    = "extendedLoggingEndDate";

	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy HH:mm";

	private Person             activePerson;
    private Long               personId;
    private String             password;
	private Department         department;
	private ClientStopListState stopListState;
	private Boolean            stopListRiskAccepted=false;
	private Boolean            activation=false;
	private Boolean            modified=false;
	private Boolean            askForDeleting = true; //удалять ли пользователя, когда он имеет задолженность
	private Boolean            hasDebts;
	private Set<PersonDocument> personDocuments;
	private String blockReason;
	private String blockStartDate;
	private String blockEndDate;
	private List<String> documentTypes;
	private ClientExtendedLoggingEntry extendedLoggingEntry; //запись расширенного логирования для клиента
	private AvatarInfo avatarInfo;
	private Boolean hasAvatar;
	private ClientNodeState clientNodeState;
	private String mdmId;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;                      
	}

	public Boolean getActivation()
	{
		return activation;
	}

	public void setActivation(Boolean activation)
	{
		this.activation = activation;
	}

	public Boolean getStopListRiskAccepted()
	{
		return stopListRiskAccepted;
	}

	public void setStopListRiskAccepted(Boolean stopListRiskAccepted)
	{
		this.stopListRiskAccepted = stopListRiskAccepted;
	}

	public ClientStopListState getStopListState()
	{
		return stopListState;
	}

	public void setStopListState(ClientStopListState stopListState)
	{
		this.stopListState = stopListState;
	}

	public Long getPerson()
    {
        return personId;
    }

    public void setPerson(Long personId)
    {
        this.personId = personId;
    }

	public Long getEditedPerson()
	{
		return personId;
	}

    public Person getActivePerson() {
        return activePerson;
    }

    public void setActivePerson(Person activePerson) {
        this.activePerson = activePerson;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword ( String password )
    {
        this.password = password;
    }

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public Set<PersonDocument> getPersonDocuments()
	{
		return personDocuments;
	}

	public void setPersonDocuments(Set<PersonDocument> personDocuments)
	{
		this.personDocuments = personDocuments;
	}

	public String getBlockReason()
	{
		return blockReason;
	}

	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

	public String getBlockStartDate()
	{
		return blockStartDate;
	}

	public void setBlockStartDate(String blockStartDate)
	{
		this.blockStartDate = blockStartDate;
	}

	public String getBlockEndDate()
	{
		return blockEndDate;
	}

	public void setBlockEndDate(String blockEndDate)
	{
		this.blockEndDate = blockEndDate;
	}

	/**
	 *
	 * @return true, если пользователя уже предупредили о долгах клиента, и пользователь дал согласие на удаление
	 */
	public Boolean getAskForDeleting()
	{
		return askForDeleting;
	}

	public void setAskForDeleting(Boolean askForDeleting)
	{
		this.askForDeleting = askForDeleting;
	}

	public Boolean getHasDebts()
	{
		return hasDebts;
	}

	public void setHasDebts(Boolean hasDebts)
	{
		this.hasDebts = hasDebts;
	}

	public Long getLoginId()
	{
		Person person = getActivePerson();
		if (person == null)
			return null;
		return getActivePerson().getLogin().getId();
	}

	public List<String> getDocumentTypes()
	{
		return documentTypes;
	}

	public void setDocumentTypes(List<String> documentTypes)
	{
		this.documentTypes = documentTypes;
	}

	public ClientExtendedLoggingEntry getExtendedLoggingEntry()
	{
		return extendedLoggingEntry;
	}

	public void setExtendedLoggingEntry(ClientExtendedLoggingEntry extendedLoggingEntry)
	{
		this.extendedLoggingEntry = extendedLoggingEntry;
	}

	public AvatarInfo getAvatarInfo()
	{
		return avatarInfo;
	}

	public void setAvatarInfo(AvatarInfo avatarInfo)
	{
		this.avatarInfo = avatarInfo;
	}

	public Boolean getHasAvatar()
	{
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar)
	{
		this.hasAvatar = hasAvatar;
	}

	/**
	 * @return идентификатор в мдм
	 */
	public String getMdmId()
	{
		return mdmId;
	}

	/**
	 * @param mdmId идентификатор в мдм
	 */
	public void setMdmId(String mdmId)
	{
		this.mdmId = mdmId;
	}

	private static Form createExtendedLoggingForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		// termlessExtendedLogging field
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TERMLESS_FIELD);
		fieldBuilder.setDescription("Логирование бессрочно");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//extendedLoggingEndDate field
		RhinoExpression endDateCheckEnableExpr = new RhinoExpression("form." + TERMLESS_FIELD + " == false");
		DateFieldValidator datefieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ ЧЧ:ММ");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TO_DATE_FIELD);
		fieldBuilder.setDescription("Дата окончания логирования");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), datefieldValidator);
		fieldBuilder.setParser(new DateParser(DATESTAMP_FORMAT));
		fieldBuilder.setEnabledExpression(endDateCheckEnableExpr);
		formBuilder.addField(fieldBuilder.build());

		//form validators
		DateTimeCompareValidator endDateTimeValidator = new DateTimeCompareValidator();
		endDateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		endDateTimeValidator.setParameter(DateTimeCompareValidator.CUR_DATE, "to");
		endDateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, TO_DATE_FIELD);
		endDateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		endDateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "");
		endDateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		endDateTimeValidator.setMessage("Дата и время окончания логирования не может быть меньше текущей даты и времени");
		endDateTimeValidator.setEnabledExpression(endDateCheckEnableExpr);

		formBuilder.addFormValidators(endDateTimeValidator);

		return formBuilder.build();
	}

	/**
	 * задать состояние клиента относительно блоков
	 * @param clientNodeState состояние
	 */
	public void setClientNodeState(ClientNodeState clientNodeState)
	{
		this.clientNodeState = clientNodeState;
	}

	/**
	 * @return состояние клиента относительно блоков
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public ClientNodeState getClientNodeState()
	{
		return clientNodeState;
	}
}
