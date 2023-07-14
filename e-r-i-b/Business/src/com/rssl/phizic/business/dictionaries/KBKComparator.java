package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.KBKRecord;

import java.util.Comparator;

/**
 * @author Kosyakova
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class KBKComparator implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        KBKRecord kbk1 = (KBKRecord) o1;
        KBKRecord kbk2 = (KBKRecord) o2;

        if(!kbk1.getSynchKey().equals(kbk2.getSynchKey()))
            return -1;

        if (!kbk1.getCode().equals(kbk2.getCode()))
            return -1;

        if (!kbk1.getNote().equals(kbk2.getNote()))
            return -1;
        return 0;
    }
}