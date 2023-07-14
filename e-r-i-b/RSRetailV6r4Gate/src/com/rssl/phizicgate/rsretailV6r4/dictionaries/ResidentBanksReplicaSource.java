package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.BanksGateService;

/**
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:43:22
 */
@PublicDefaultCreatable
public class ResidentBanksReplicaSource extends NamedQueryReplicaSource
{
    public ResidentBanksReplicaSource()
    {
        super("GetResidentBanks");
    }

	public void initialize(GateFactory factory)
	{
	}
}
