package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.depo.DepoAccountDivision;

import java.util.List;

/**
 * @author mihaylov
 * @ created 03.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountPositionImpl implements DepoAccountPosition
{
	private List<DepoAccountDivision> depoAccountDivisions;

	public List<DepoAccountDivision> getDepoAccountDivisions()
	{
		return depoAccountDivisions;
	}

	public void setDepoAccountDivisions(List<DepoAccountDivision> depoAccountDivisions)
	{
		this.depoAccountDivisions = depoAccountDivisions;
	}
}
