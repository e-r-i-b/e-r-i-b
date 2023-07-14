package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 14:41:42
 */
public interface ReplicaDestination<DR extends DictionaryRecord> extends ReplicaSource
{
    void add   (DR newValue) throws GateException;
    void remove(DR oldValue) throws GateException;
    void update(DR oldValue, DR newValue) throws GateException;
	List<String> getErrors();
}
