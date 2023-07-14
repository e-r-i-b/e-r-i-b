package com.rssl.phizic.business.dictionaries.pfp.common;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����� ������� ������������ ���
 */

public abstract class PFPDictionaryRecord extends MultiBlockDictionaryRecordBase implements DictionaryRecord
{
	private Long id;

	/**
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ������
	 * @param id ������������� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ������ � ������������ ������ ��������
	 * ����� ��� ����������
	 * @param id ������������� ��������
	 */
	@SuppressWarnings({"NoopMethodInAbstractClass"})
	public void setImageId(Long id){}

	/**
	 * ����� ��� ����������
	 * @return ������������� �������� ������
	 */
	public Long getImageId()
	{
		return null;
	}

	/**
	 * ��������� ������ ��� ����������
	 * @return ���� ������
	 */
	public abstract Comparable getSynchKey();
}
