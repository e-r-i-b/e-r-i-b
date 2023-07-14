package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.depo.DepoAccountDivision;
import com.rssl.phizic.gate.depo.DepoAccountSecurity;

import java.util.List;

/**
 * @author mihaylov
 * @ created 03.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountDivisionImpl implements DepoAccountDivision
{
	private String divisionType;
	private String divisionNumber;
	private List<DepoAccountSecurity> depoAccountSecurities;

	public String getDivisionType()
	{
		return divisionType;
	}

	public void setDivisionType(String divisionType)
	{
		this.divisionType = divisionType;
	}

	public String getDivisionNumber()
	{
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber)
	{
		this.divisionNumber = divisionNumber;
	}

	public List<DepoAccountSecurity> getDepoAccountSecurities()
	{
		return depoAccountSecurities;
	}

	public void setDepoAccountSecurities(List<DepoAccountSecurity> depoAccountSecurities)
	{
		this.depoAccountSecurities = depoAccountSecurities;
	}
}
