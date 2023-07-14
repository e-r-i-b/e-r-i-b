package com.rssl.phizicgate.rsV51.dictionaries;

import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.rsV51.test.RSRetaileGateTestCaselBase;
import com.rssl.phizicgate.rsV51.dictionaries.officies.OfficiesReplicaSource;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:45:31
 */
public class TestSinchronizeDictionaries extends RSRetaileGateTestCaselBase
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
                            "ReplicaSource ������ ���������� ������������������ � ������������� synchKey",
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
