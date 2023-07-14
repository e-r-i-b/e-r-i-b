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
 * Клиент
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
		//не используется в ЕСУШ
		return null;
	}

	public Long getInternalId()
	{
		//не используется в ЕСУШ
		return null;
	}

	public Long getInternalOwnerId()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getDisplayId()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getShortName()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getFullName()
	{
		//не используется в ЕСУШ
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
		//не используется в ЕСУШ
		return null;
	}

	public String getEmail()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getHomePhone()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getJobPhone()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getMobilePhone()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getMobileOperator()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getSex()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getINN()
	{
		//не используется в ЕСУШ
		return null;
	}

	public boolean isResident()
	{
		//не используется в ЕСУШ
		return false;
	}

	public Address getLegalAddress()
	{
		//не используется в ЕСУШ
		return null;
	}

	public Address getRealAddress()
	{
		//не используется в ЕСУШ
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
		//не используется в ЕСУШ
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
		//не используется в ЕСУШ
		return null;
	}

	public Calendar getInsertionDate()
	{
		//не используется в ЕСУШ
		return null;
	}

	public Calendar getCancellationDate()
	{
		//не используется в ЕСУШ
		return null;
	}

	public ClientState getState()
	{
		//не используется в ЕСУШ
		return null;
	}

	public boolean isUDBO()
	{
		//не используется в ЕСУШ
		return false;
	}

	public SegmentCodeType getSegmentCodeType()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getTarifPlanCodeType()
	{
		//не используется в ЕСУШ
		return null;
	}

	public Calendar getTarifPlanConnectionDate()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getManagerId()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getManagerTB()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getManagerOSB()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getManagerVSP()
	{
		//не используется в ЕСУШ
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
