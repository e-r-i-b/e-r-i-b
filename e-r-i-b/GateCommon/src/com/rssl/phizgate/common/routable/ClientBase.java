package com.rssl.phizgate.common.routable;

import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Calendar;
import java.util.List;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class ClientBase implements Client, Routable
{
	protected String id;
	protected Long internalId;
	protected String shortName;
	protected String fullName;
	protected String firstName;
	protected String surName;
	protected String patrName;
	protected Calendar birthDay;
	protected String gender;
	protected String birthPlace;
	protected String email;
	protected String homePhone;
	protected String jobPhone;
	protected String mobilePhone;
	protected String mobileOperator;
	protected String INN;
    protected Address legalAddress;
    protected Address realAddress;
	protected List<? extends ClientDocument> documents;
	protected String citizenship;
	protected Long internalOwnerId;
	private String agreementNumber;
	private Office office;
	private Calendar insertionDate;
	private Calendar cancellationDate;
	private ClientState state;	
	private boolean isUDBO;
	protected SegmentCodeType segmentCodeType;
	protected String tarifPlanCodeType;
	protected Calendar tarifPlanConnectionDate;
	protected String managerId;
	private String managerTB;
	private String managerOSB;
	private String managerVSP;

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

	public String getId()
	{
		return id;
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

	public Long getInternalOwnerId()
	{
		return internalOwnerId;
	}

	public void setInternalOwnerId(Long internalOwnerId)
	{
		this.internalOwnerId = internalOwnerId;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getSex()
	{
		return gender;
	}

	public void setSex(String gender)
	{
		this.gender = gender;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getJobPhone()
	{
		return jobPhone;
	}

	public void setJobPhone(String jobPhone)
	{
		this.jobPhone = jobPhone;
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

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public Address getLegalAddress()
	{
		return legalAddress;
	}

	public void setLegalAddress(Address legalAddress)
	{
		this.legalAddress = legalAddress;
	}

	public Address getRealAddress()
	{
		return realAddress;
	}

	public void setRealAddress(Address realAddress)
	{
		this.realAddress = realAddress;
	}

	public List<? extends ClientDocument> getDocuments()
	{
		return documents;
	}

	public void setDocuments(List<? extends ClientDocument> documents)
	{
		this.documents = documents;
	}

	public String getCitizenship()
	{
		return citizenship;
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

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
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

	public boolean isUDBO()
	{
		return isUDBO;
	}

	public void setUDBO(boolean UDBO)
	{
		isUDBO = UDBO;
	}

	public SegmentCodeType getSegmentCodeType()
	{
		return segmentCodeType;
	}

	public void setSegmentCodeType(SegmentCodeType segmentCodeType)
	{
		this.segmentCodeType = segmentCodeType;
	}

	public void setSegmentCodeType(String segmentCodeType)
	{
		this.segmentCodeType = SegmentCodeType.valueOf(segmentCodeType);
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

	public String getManagerTB()
	{
		return managerTB;
	}

	public String getManagerOSB()
	{
		return managerOSB;
	}

	public String getManagerVSP()
	{
		return managerVSP;
	}

	public void setManagerTB(String managerTB)
	{
		this.managerTB = managerTB;
	}

	public void setManagerOSB(String managerOSB)
	{
		this.managerOSB = managerOSB;
	}

	public void setManagerVSP(String managerVSP)
	{
		this.managerVSP = managerVSP;
	}
}
