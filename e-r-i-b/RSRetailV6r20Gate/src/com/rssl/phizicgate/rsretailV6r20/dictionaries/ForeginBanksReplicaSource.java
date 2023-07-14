package com.rssl.phizicgate.rsretailV6r20.dictionaries;

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
public class ForeginBanksReplicaSource extends NamedQueryReplicaSource
{
    public ForeginBanksReplicaSource()
    {
        super("GetForeignBanks");
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
