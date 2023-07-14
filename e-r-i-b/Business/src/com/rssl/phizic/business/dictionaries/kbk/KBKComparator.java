package com.rssl.phizic.business.dictionaries.kbk;

import java.util.Comparator;

/**
 * @author akrenev
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class KBKComparator implements Comparator
{
    public int compare(Object o1, Object o2)
    {
	    KBK kbk1 = (KBK) o1;
	    KBK kbk2 = (KBK) o2;

	    if (kbk1 == null && kbk2 == null)
	        return 0;

	    if (kbk1 == null)
	        return -1;

	    if (kbk2 == null)
	        return 1;

        return kbk1.getCode().compareTo(kbk2.getCode());
    }
}