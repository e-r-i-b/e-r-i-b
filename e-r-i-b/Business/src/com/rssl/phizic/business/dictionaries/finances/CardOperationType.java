package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Erkin
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class CardOperationType extends DictionaryRecordBase implements com.rssl.phizic.gate.ips.CardOperationType
{
	private long code;
	private boolean cash;

	public long getCode()
	{
		return code;
	}

	/**
	 * @param code - ���
	 */
	public void setCode(Long code)
	{
		this.code = code;
	}

	/**
	 * @return true - �������� � ���������
	 */
	public boolean isCash()
	{
		return cash;
	}

	/**
	 * @param cash - true - �������� � ���������
	 */
	public void setCash(boolean cash)
	{
		this.cash = cash;
	}

	public Comparable getSynchKey()
	{
		return code;
	}
}
