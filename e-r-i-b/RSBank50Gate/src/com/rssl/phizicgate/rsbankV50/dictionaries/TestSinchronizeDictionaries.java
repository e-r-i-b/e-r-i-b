package com.rssl.phizicgate.rsbankV50.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizicgate.rsbankV50.junit.RSBankGateTestCaselBase;

import java.util.Iterator;

import junit.framework.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:45:31
 */
public class TestSinchronizeDictionaries extends RSBankGateTestCaselBase
{
    public TestSinchronizeDictionaries(String string) throws GateException
    {
        super(string);
    }

    public void testBanks() throws Exception
    {
        checkReplicaSource(new ForeginBanksReplicaSource());

        checkReplicaSource(new ResidentBanksReplicaSource());
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
                    Assert.assertTrue(
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
