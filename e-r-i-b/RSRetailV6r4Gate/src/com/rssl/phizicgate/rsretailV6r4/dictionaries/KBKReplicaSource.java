package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.KBKGateService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;

import java.util.Iterator;

import org.hibernate.Session;

/**
 * @author Kosyakova
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class KBKReplicaSource extends NamedQueryReplicaSource
{
	public KBKReplicaSource()
	{
		super("GetKBK");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
