package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.Calendar;
import java.util.List;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class ClientBase implements Client
{
	Client delegate;

	public ClientBase(Client delegate)
	{
		this.delegate = delegate;
	}

	public String getDisplayId()
	{
		return delegate.getDisplayId();
	}

	public Long getInternalOwnerId()
	{
		return delegate.getInternalOwnerId();
	}

	public String getShortName()
	{
		return delegate.getShortName();
	}

	public String getFullName()
	{
		return delegate.getFullName();
	}

	public String getFirstName()
	{
		return delegate.getFirstName();
	}

	public String getSurName()
	{
		return delegate.getSurName();
	}

	public String getPatrName()
	{
		return delegate.getPatrName();
	}

	public Calendar getBirthDay()
	{
		return delegate.getBirthDay();
	}

	public String getBirthPlace()
	{
		return delegate.getBirthPlace();
	}

	public String getEmail()
	{
		return delegate.getEmail();
	}

	public String getHomePhone()
	{
		return delegate.getHomePhone();
	}

	public String getJobPhone()
	{
		return delegate.getJobPhone();
	}

	public String getMobilePhone()
	{
		return delegate.getMobilePhone();
	}

	public String getMobileOperator()
	{
		return delegate.getMobileOperator();
	}

	public String getSex()
	{
		return delegate.getSex();
	}

	public String getINN()
	{
		return delegate.getINN();
	}

	public boolean isResident()
	{
		return delegate.isResident();
	}

	public Address getLegalAddress()
	{
		return delegate.getLegalAddress();
	}

	public Address getRealAddress()
	{
		return delegate.getRealAddress();
	}

	public List<? extends ClientDocument> getDocuments()
	{
		return delegate.getDocuments();
	}

	public String getCitizenship()
	{
		return delegate.getCitizenship();
	}

	public Office getOffice()
	{
		return delegate.getOffice();
	}

	public String getAgreementNumber()
	{
		return delegate.getAgreementNumber();
	}

	public Calendar getInsertionDate()
	{
		return delegate.getInsertionDate();
	}

	public Calendar getCancellationDate()
	{
		return delegate.getCancellationDate();
	}

	public ClientState getState()
	{
		return delegate.getState();
	}

	public boolean isUDBO()
	{
		return delegate.isUDBO();
	}

	public SegmentCodeType getSegmentCodeType()
	{
		return delegate.getSegmentCodeType();
	}

	public String getTarifPlanCodeType()
	{
		return delegate.getTarifPlanCodeType();
	}

	public Calendar getTarifPlanConnectionDate()
	{
		return delegate.getTarifPlanConnectionDate();
	}

	public String getManagerId()
	{
		return delegate.getManagerId();
	}

	public String getManagerTB()
	{
		return delegate.getManagerTB();
	}

	public String getManagerOSB()
	{
		return delegate.getManagerOSB();
	}

	public String getManagerVSP()
	{
		return delegate.getManagerVSP();
	}

	public String getGender()
	{
		return delegate.getGender();
	}
}
