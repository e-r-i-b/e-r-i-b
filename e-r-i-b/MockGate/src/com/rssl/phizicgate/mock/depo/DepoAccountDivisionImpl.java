package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.gate.depo.DepoAccountDivision;
import com.rssl.phizic.gate.depo.DepoAccountSecurity;

import java.util.List;

/**
 * @author mihaylov
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountDivisionImpl implements DepoAccountDivision
{
	private String divisionType;
	private String divisionNumber;
	private List<DepoAccountSecurity> depoAccountSecurities;

	public DepoAccountDivisionImpl(String divisionType, String divisionNumber, List<DepoAccountSecurity> depoAccountSecurities)
	{
		this.divisionType = divisionType;
		this.divisionNumber = divisionNumber;
		this.depoAccountSecurities = depoAccountSecurities;
	}

	public String getDivisionType()
	{
		return divisionType;
	}

	public String getDivisionNumber()
	{
		return divisionNumber;
	}

	public List<DepoAccountSecurity> getDepoAccountSecurities()
	{
		return depoAccountSecurities;
	}
}
