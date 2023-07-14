package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.io.IOException;

/**
 * @author Gololobov
 * @ created 08.08.2011
 * @ $Author$
 * @ $Revision$
 */

interface RecordConstructor<T extends DictionaryRecordBase>
{
	T construct(CsvRecordIterator csvRecordIterator) throws IOException;
}
