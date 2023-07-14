package com.rssl.phizicgate.rsV55.dictionaries;

import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:43:22
 */
@PublicDefaultCreatable
public class ResidentBanksReplicaSource extends NamedQueryReplicaSource
{
    public ResidentBanksReplicaSource()
    {
        super("GetResidentBanks", true);
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
