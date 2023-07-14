package com.rssl.phizic.business.dictionaries;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 25.08.2006
 * Time: 16:08:49
 * To change this template use File | Settings | File Templates.
 */

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;


import java.util.Iterator;

import junit.framework.TestCase;

public class ReplicaSourcesTest  extends TestCase
{
	public void testBanks() throws Exception
	{
		checkReplicaSource(new ResidentBankReplicaSourceSAX());
	}

	private void checkReplicaSource(ReplicaSource replicaSource) throws Exception
	{
		try
		{
			Iterator it = replicaSource.iterator();

			DictionaryRecord curr;
			DictionaryRecord prev=null;

			while (it.hasNext())
			{
				curr = (DictionaryRecord) it.next();
				curr.toString();
				prev = curr;
			}

		}
		finally
		{
			replicaSource.close();
		}
	}
}
