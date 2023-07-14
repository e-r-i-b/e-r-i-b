package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.*;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * источник обновления записей справочника ПФП
 */
public class PFPDictionaryReplicaSource implements ReplicaSource<PFPDictionaryRecordWrapper>
{
	private List<PFPDictionaryRecordWrapper> dictionary;

	/**
	 * @param dictionary записи справочника
	 */
	public PFPDictionaryReplicaSource(Collection<PFPDictionaryRecordWrapper> dictionary)
	{
		this.dictionary = new ArrayList<PFPDictionaryRecordWrapper>(dictionary);
		Collections.sort(this.dictionary, new SynchKeyComparator());
	}

	public void initialize(GateFactory factory){}

	public Iterator<PFPDictionaryRecordWrapper> iterator() throws GateException, GateLogicException
	{
		return dictionary.iterator();
	}

	public void close(){}
}
