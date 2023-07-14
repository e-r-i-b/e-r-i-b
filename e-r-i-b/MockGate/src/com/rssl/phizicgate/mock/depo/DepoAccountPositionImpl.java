package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.depo.DepoAccountDivision;

import java.util.List;

/**
 * @author mihaylov
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountPositionImpl implements DepoAccountPosition
{
	private List<DepoAccountDivision> depoAccountDivisions;

	public DepoAccountPositionImpl(List<DepoAccountDivision> depoAccountDivision)
	{
		this.depoAccountDivisions = depoAccountDivision;
	}

	public List<DepoAccountDivision> getDepoAccountDivisions()
	{
		return depoAccountDivisions;
	}
}
