package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.depo.DepoAccountSecurity;
import com.rssl.phizic.gate.depo.DepoAccountSecurityStorageMethod;
import com.rssl.phizic.gate.depo.SecurityMarker;

import java.util.List;

/**
 * @author mihaylov
 * @ created 20.08.2010
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

	public DepoAccountSecurityImpl(List<SecurityMarker> securityMarkers, String insideCode, Long remainder, DepoAccountSecurityStorageMethod storageMethod, String name, Money nominal,String registrationNumber)
	{
		this.securityMarkers = securityMarkers;
		this.insideCode = insideCode;
		this.remainder = remainder;
		this.storageMethod = storageMethod;
		this.name = name;
		this.nominal = nominal;
		this.registrationNumber = registrationNumber;
	}

	public List<SecurityMarker> getSecurityMarkers()
	{
		return securityMarkers;
	}

	public String getInsideCode()
	{
		return insideCode;
	}

	public Long getRemainder()
	{
		return remainder;
	}

	public DepoAccountSecurityStorageMethod getStorageMethod()
	{
		return storageMethod;
	}

	public String getName()
	{
		return name;
	}

	public Money getNominal()
	{
		return nominal;
	}

	public String getRegistrationNumber()
	{
		return registrationNumber;
	}
}
