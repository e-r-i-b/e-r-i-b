package com.rssl.phizic.security.certification;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Evgrafov
 * @ created 22.12.2006
 * @ $Author: emakarov $
 * @ $Revision: 6373 $
 */

public class CertOwn
{	
	private Long id;
	private CommonLogin owner;
	private Certificate certificate;
	private boolean active;
	private long    version;
	private String status;
	private Calendar startDate;//дата начала действия сертификата в системе
	private Calendar endDate;//дата окончания действия сертификата в системе (берется из Агавы)

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Certificate getCertificate()
	{
		return certificate;
	}

	public void setCertificate(Certificate certificate)
	{
		this.certificate = certificate;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CommonLogin getOwner()
	{
		return owner;
	}

	public void setOwner(CommonLogin owner)
	{
		this.owner = owner;
	}

	public long getVersion()
	{
		return version;
	}

	public void setVersion(long version)
	{
		this.version = version;
	}
}