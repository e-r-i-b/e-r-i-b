package com.rssl.phizic.gate.owners.client;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientState;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.office.ExtendedCodeImpl;
import com.rssl.phizic.gate.office.ExtendedOfficeImpl;
import com.rssl.phizic.gate.owners.person.Profile;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * ������
 *
 * @author khudyakov
 * @ created 02.03.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientImpl implements ExtendedClient
{
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDay;
	private List<ClientDocument> documents;
	private Office office;


	public ClientImpl()
	{
	}

	public ClientImpl(Profile profile)
	{
		firstName = profile.getFirstName();
		surName   = profile.getSurName();
		patrName  = profile.getPatrName();
		birthDay  = profile.getBirthDay();
		documents = Collections.singletonList(ClientHelper.getWayDocument(profile.getPassport()));
		office = new ExtendedOfficeImpl(new ExtendedCodeImpl(profile.getTb(), null, null));
	}

	public String getId()
	{
		//�� ������������ � ����
		return null;
	}

	public Long getInternalId()
	{
		//�� ������������ � ����
		return null;
	}

	public Long getInternalOwnerId()
	{
		//�� ������������ � ����
		return null;
	}

	public String getDisplayId()
	{
		//�� ������������ � ����
		return null;
	}

	public String getShortName()
	{
		//�� ������������ � ����
		return null;
	}

	public String getFullName()
	{
		//�� ������������ � ����
		return null;
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

	public String getBirthPlace()
	{
		//�� ������������ � ����
		return null;
	}

	public String getEmail()
	{
		//�� ������������ � ����
		return null;
	}

	public String getHomePhone()
	{
		//�� ������������ � ����
		return null;
	}

	public String getJobPhone()
	{
		//�� ������������ � ����
		return null;
	}

	public String getMobilePhone()
	{
		//�� ������������ � ����
		return null;
	}

	public String getMobileOperator()
	{
		//�� ������������ � ����
		return null;
	}

	public String getSex()
	{
		//�� ������������ � ����
		return null;
	}

	public String getINN()
	{
		//�� ������������ � ����
		return null;
	}

	public boolean isResident()
	{
		//�� ������������ � ����
		return false;
	}

	public Address getLegalAddress()
	{
		//�� ������������ � ����
		return null;
	}

	public Address getRealAddress()
	{
		//�� ������������ � ����
		return null;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public List<ClientDocument> getDocuments()
	{
		return documents;
	}

	public String getCitizenship()
	{
		//�� ������������ � ����
		return null;
	}

	public void setDocuments(List<ClientDocument> documents)
	{
		this.documents = documents;
	}

	public Office getOffice()
	{
		return office;
	}

	public String getAgreementNumber()
	{
		//�� ������������ � ����
		return null;
	}

	public Calendar getInsertionDate()
	{
		//�� ������������ � ����
		return null;
	}

	public Calendar getCancellationDate()
	{
		//�� ������������ � ����
		return null;
	}

	public ClientState getState()
	{
		//�� ������������ � ����
		return null;
	}

	public boolean isUDBO()
	{
		//�� ������������ � ����
		return false;
	}

	public SegmentCodeType getSegmentCodeType()
	{
		//�� ������������ � ����
		return null;
	}

	public String getTarifPlanCodeType()
	{
		//�� ������������ � ����
		return null;
	}

	public Calendar getTarifPlanConnectionDate()
	{
		//�� ������������ � ����
		return null;
	}

	public String getManagerId()
	{
		//�� ������������ � ����
		return null;
	}

	public String getManagerTB()
	{
		//�� ������������ � ����
		return null;
	}

	public String getManagerOSB()
	{
		//�� ������������ � ����
		return null;
	}

	public String getManagerVSP()
	{
		//�� ������������ � ����
		return null;
	}

	public String getGender()
	{
		return null;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public Profile asCurrentPerson() throws Exception
	{
		return new Profile(this);
	}

	public Profile asAbstractPerson() throws Exception
	{
		String passport = ClientHelper.getClientWayDocument(documents).getDocSeries();
		String tb = StringHelper.addLeadingZeros(new ExtendedCodeGateImpl(office.getCode()).getRegion(), 2);

		return new Profile(firstName, surName, patrName, birthDay, passport, tb);
	}
}
