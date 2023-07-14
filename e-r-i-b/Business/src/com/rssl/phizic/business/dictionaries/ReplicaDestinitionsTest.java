package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:45:31
 */
public class ReplicaDestinitionsTest extends BusinessTestCaseBase
{

    public void testBanks() throws Exception
    {
        checkReplicaSource(new ResidentBanksReplicaDestination());
    }

    private void checkReplicaSource(ReplicaSource replicaSource) throws Exception
    {
        try
        {
            Iterator it = replicaSource.iterator();
            DictionaryRecord prev = null;
            DictionaryRecord curr;

            while (it.hasNext())
            {
                curr = (DictionaryRecord) it.next();
                curr.toString();

                if(prev != null)
                {
                    assertTrue(
                            "ReplicaSource должна возвращать последовательность с возрастающими synchKey",
                            prev.getSynchKey().compareTo(curr.getSynchKey()) < 0);
                }

                prev = curr;
            }
        }
        finally
        {
            replicaSource.close();
        }
    }
}
