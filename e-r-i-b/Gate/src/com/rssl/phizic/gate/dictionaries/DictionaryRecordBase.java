package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.utils.BeanHelper;

/**
 * @author Kosyakova
 * @ created 04.10.2006
 * @ $Author$
 * @ $Revision$
 */
public abstract class DictionaryRecordBase implements DictionaryRecord
{
    public void updateFrom(DictionaryRecord that)
    {
        BeanHelper.copyProperties(this, that);
    }
}
