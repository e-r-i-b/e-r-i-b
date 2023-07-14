package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.AddressImpl;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 06.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClientImpl implements Client, Routable
{
	private String id;
	private Long internalId;
	private String shortName;
	private String fullName;
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDay;
	private String birthPlace;
	private String documentType;
	private String docTypeName;
	private String docNumber;
	private String docSeries;
	private Calendar docIssueDate;
	private String docIssueBy;
	private String registrationAddress;
	private String residenceAddress;
	private String email;
	private String homePhone;
	private String jobPhone;
	private String mobilePhone;
	private String mobileOperator;
	private String sex;
	private String INN;
	private boolean isResident;
	private AddressImpl legalAddress;
	private AddressImpl realAddress;
	private List<ClientDocument> documents;
	private String citizenship;
	private Long internalOwnerId;
	private String agreementNumber;
	private Office office;
	private Calendar insertionDate;
	private Calendar cancellationDate;
	private ClientState state;
	private boolean isUDBO;
	private SegmentCodeType segmentCodeType;
	private String tarifPlanCodeType;
	private Calendar tarifPlanConnectionDate;
	private String managerId;
	private String managerTB;
	private String managerOSB;
	private String managerVSP;
	private String gender;

	public ClientImpl(){
		
	}

	public ClientImpl(ClientImpl client)
	{
		id = client.getId();
	}

    public ClientImpl(Person person) throws BusinessException
    {
	    id = person.getClientId();
	    internalId = person.getId();
	    if(person.getLogin() != null)
	    {
		    internalOwnerId = person.getLogin().getId();
	    }
	    fullName = person.getFullName();
	    firstName = person.getFirstName();
	    surName = person.getSurName();
	    patrName = person.getPatrName();
	    birthDay = person.getBirthDay();
	    birthPlace = person.getBirthPlace();
		registrationAddress = person.getRegistrationAddress()!=null?person.getRegistrationAddress().toString():null;
		residenceAddress = person.getRegistrationAddress()!=null?person.getRegistrationAddress().toString():null;
		email = person.getEmail();
		homePhone = person.getHomePhone();
		jobPhone = person.getJobPhone();
		mobilePhone = person.getMobilePhone();
	    mobileOperator = person.getMobileOperator();
		sex = person.getGender();
		INN = person.getInn();
	    citizenship = person.getCitizenship();
		isResident = BooleanUtils.isTrue(person.getIsResident());
	    if(person.getRegistrationAddress() != null)
	    {
		    legalAddress = new AddressImpl(person.getRegistrationAddress(), person);
	    }
	    if(person.getResidenceAddress() != null)
	    {
		    realAddress = new AddressImpl(person.getResidenceAddress(), person);
	    }
	    if(person.getPersonDocuments()!=null)
	    {
			documents = new ArrayList<ClientDocument>();
		    for (PersonDocument personDocument : person.getPersonDocuments())
		    {
			    ClientDocumentImpl document = new ClientDocumentImpl();
			    document.setDocIssueBy(personDocument.getDocumentIssueBy());
			    document.setDocIssueDate(personDocument.getDocumentIssueDate());
			    document.setDocIssueByCode(personDocument.getDocumentIssueByCode());
			    document.setDocTimeUpDate(personDocument.getDocumentTimeUpDate());
			    document.setDocNumber(personDocument.getDocumentNumber());
			    document.setDocSeries(personDocument.getDocumentSeries());
			    document.setDocTypeName(personDocument.getDocumentName());
			    document.setDocumentType(ClientDocumentType.valueOf(personDocument.getDocumentType().toString()));
			    document.setDocIdentify(personDocument.isDocumentIdentify());
			    documents.add(document);
		    }
	    }
	    agreementNumber = person.getAgreementNumber();

		DepartmentService service = new DepartmentService();
		office = service.findById(person.getDepartmentId());
	    insertionDate = person.getServiceInsertionDate();
		cancellationDate = person.getProlongationRejectionDate();
		state = ClientState.getStateByCode(person.getStatus());
	    isUDBO = person.getCreationType().equals(CreationType.UDBO);
	    segmentCodeType = person.getSegmentCodeType();
	    managerId = person.getManagerId();
	    tarifPlanConnectionDate = person.getTarifPlanConnectionDate();
	    tarifPlanCodeType = person.getTarifPlanCodeType();
	    managerTB = person.getManagerTB();
	    managerOSB = person.getManagerOSB();
	    managerVSP = person.getManagerVSP();
	    gender = person.getGender();
    }

	public ClientImpl(String firstName, String secondName, String surName)
	{
		ActivePerson person = new ActivePerson();

		person.setFirstName(firstName);
		person.setPatrName(secondName);
		person.setSurName(surName);

	    this.fullName = person.getFullName();
		this.firstName = person.getFirstName();
	    this.surName = person.getSurName();
	    this.patrName = person.getPatrName();
		segmentCodeType = SegmentCodeType.NOTEXISTS;
	}
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	public String getDocIssueBy()
	{
		return docIssueBy;
	}

	public void setDocIssueBy(String docIssueBy)
	{
		this.docIssueBy = docIssueBy;
	}

	public Calendar getDocIssueDate()
	{
		return docIssueDate;
	}

	public void setDocIssueDate(Calendar docIssueDate)
	{
		this.docIssueDate = docIssueDate;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public String getDocSeries()
	{
		return docSeries;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
	}

	public List<? extends ClientDocument> getDocuments()
	{
		return documents;
	}

	public void setDocuments(List<ClientDocument> documents)
	{
		this.documents = documents;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getId()
	{
		return id;
	}

	public String getDisplayId()
	{
		return getId();
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Long getInternalId()
	{
		return internalId;
	}

	public void setInternalId(Long internalId)
	{
		this.internalId = internalId;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public boolean isResident()
	{
		return isResident;
	}

	public void setResident(boolean resident)
	{
		isResident = resident;
	}

	public String getJobPhone()
	{
		return jobPhone;
	}

	public void setJobPhone(String jobPhone)
	{
		this.jobPhone = jobPhone;
	}

	public Address getLegalAddress()
	{
		return legalAddress;
	}

	public void setLegalAddress(AddressImpl legalAddress)
	{
		this.legalAddress = legalAddress;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getMobileOperator()
	{
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator)
	{
		this.mobileOperator = mobileOperator;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Address getRealAddress()
	{
		return realAddress;
	}

	public void setRealAddress(AddressImpl realAddress)
	{
		this.realAddress = realAddress;
	}

	public String getRegistrationAddress()
	{
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress)
	{
		this.registrationAddress = registrationAddress;
	}

	public String getResidenceAddress()
	{
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress)
	{
		this.residenceAddress = residenceAddress;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getCitizenship()
	{
		return citizenship;
	}

	public Long getInternalOwnerId()
	{
		return internalOwnerId;
	}

	public void setInternalOwnerId(Long internalOwnerId)
	{
		this.internalOwnerId = internalOwnerId;
	}

	public void setCitizenship(String citizenship)
	{
		this.citizenship = citizenship;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public boolean isUDBO()
	{
		return isUDBO;
	}

	public SegmentCodeType getSegmentCodeType()
	{
		return segmentCodeType;
	}

	public void setSegmentCodeType(SegmentCodeType segmentCodeType)
	{
		this.segmentCodeType = segmentCodeType;
	}

	public String getTarifPlanCodeType()
	{
		return tarifPlanCodeType;
	}

	public void setTarifPlanCodeType(String tarifPlanCodeType)
	{
		this.tarifPlanCodeType = tarifPlanCodeType;
	}

	public Calendar getTarifPlanConnectionDate()
	{
		return tarifPlanConnectionDate;
	}

	public void setTarifPlanConnectionDate(Calendar tarifPlanConnectionDate)
	{
		this.tarifPlanConnectionDate = tarifPlanConnectionDate;
	}

	public String getManagerId()
	{
		return managerId;
	}

	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	public Calendar getInsertionDate()
	{
		return insertionDate;
	}

	public void setInsertionDate(Calendar insertionDate)
	{
		this.insertionDate = insertionDate;
	}

	public Calendar getCancellationDate()
	{
		return cancellationDate;
	}

	public void setCancellationDate(Calendar cancellationDate)
	{
		this.cancellationDate = cancellationDate;
	}

	public ClientState getState()
	{
		return state;
	}

	public void setState(ClientState state)
	{
		this.state = state;
	}

	public void setUDBO(boolean UDBO)
	{
		isUDBO = UDBO;
	}

	public void storeRouteInfo(String info)
	{
		setId(IDHelper.storeRouteInfo(getId(), info));
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo(getId());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo(id);
		setId(IDHelper.restoreOriginalId(id));

		return info;
	}

	public String getManagerTB()
	{
		return managerTB;
	}

	public void setManagerTB(String managerTB)
	{
		this.managerTB = managerTB;
	}

	public String getManagerOSB()
	{
		return managerOSB;
	}

	public void setManagerOSB(String managerOSB)
	{
		this.managerOSB = managerOSB;
	}

	public String getManagerVSP()
	{
		return managerVSP;
	}

	public void setManagerVSP(String managerVSP)
	{
		this.managerVSP = managerVSP;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}
}
