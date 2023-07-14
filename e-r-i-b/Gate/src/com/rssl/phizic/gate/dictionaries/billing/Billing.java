package com.rssl.phizic.gate.dictionaries.billing;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public interface Billing extends DictionaryRecord, Serializable
{
	/**
	 * @return название биллинга
	 * */
	String getName();
}
