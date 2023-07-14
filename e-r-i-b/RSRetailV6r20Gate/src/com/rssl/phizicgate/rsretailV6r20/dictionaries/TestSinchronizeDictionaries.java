package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;
import com.rssl.phizicgate.rsretailV6r20.dictionaries.officies.OfficiesReplicaSource;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:45:31
 */
public class TestSinchronizeDictionaries extends RSRetailV6r20GateTestCaseBase
{
    public TestSinchronizeDictionaries(String string) throws GateException
    {
        super(string);
    }

    public void testBanks() throws Exception
    {
        checkReplicaSource( new ForeginBanksReplicaSource() );

        checkReplicaSource( new ResidentBanksReplicaSource() );

	    checkReplicaSource( new OfficiesReplicaSource() );
    }

    private void checkReplicaSource(ReplicaSource replicaSource) throws Exception
    {
        try
        {
            Iterator it = replicaSource.iterator();
            DictionaryRecord prev = null;
            DictionaryRecord curr = null;

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
