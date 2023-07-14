package com.rssl.phizic.business.dictionaries.currencies;

import junit.framework.TestCase;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 18.09.2006
 * Time: 10:26:56
 * To change this template use File | Settings | File Templates.
 */
public class CurrencyReplicaDestinitionsTest  extends TestCase
{
	public void testBanks() throws Exception
	{
		checkReplicaSource(new CurrencyXmlSourceSAX());
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
