package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Iterator;

/**
 * 
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 14:40:47
 */
public interface ReplicaSource<DR extends DictionaryRecord>
{
	void initialize(GateFactory factory) throws GateException;

    Iterator<DR> iterator() throws GateException, GateLogicException;

    void close();
}
