package com.rssl.phizic.gate.dictionaries;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author lepihina
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface CsvReplicaSource extends ReplicaSource
{
	void setReader(CsvReader reader) throws GateException;
}
