package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.depo.DepoAccountSecurity;
import com.rssl.phizic.gate.depo.DepoAccountSecurityStorageMethod;
import com.rssl.phizic.gate.depo.SecurityMarker;

import java.util.List;

/**
 * @author mihaylov
 * @ created 03.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountSecurityImpl implements DepoAccountSecurity
{
	private List<SecurityMarker> securityMarkers;
	private String insideCode;
	private Long remainder;
	private DepoAccountSecurityStorageMethod storageMethod;
	private String name;
	private Money nominal;
	private String registrationNumber;

	public List<SecurityMarker> getSecurityMarkers()
	{
		return securityMarkers;
	}

	public void setSecurityMarkers(List<SecurityMarker> securityMarkers)
	{
		this.securityMarkers = securityMarkers;
	}

	public String getInsideCode()
	{
		return insideCode;
	}

	public void setInsideCode(String insideCode)
	{
		this.insideCode = insideCode;
	}

	public Long getRemainder()
	{
		return remainder;
	}

	public void setRemainder(Long remainder)
	{
		this.remainder = remainder;
	}

	public DepoAccountSecurityStorageMethod getStorageMethod()
	{
		return storageMethod;
	}

	public void setStorageMethod(DepoAccountSecurityStorageMethod storageMethod)
	{
		this.storageMethod = storageMethod;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Money getNominal()
	{
		return nominal;
	}

	public void setNominal(Money nominal)
	{
		this.nominal = nominal;
	}

	public void setRegistrationNumber(String registrationNumber)
	{
		this.registrationNumber = registrationNumber;
	}

	public String getRegistrationNumber()
	{
		return registrationNumber;
	}
}
