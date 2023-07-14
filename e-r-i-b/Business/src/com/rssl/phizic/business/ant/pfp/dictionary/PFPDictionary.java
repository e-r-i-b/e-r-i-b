package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ant.pfp.dictionary.actions.PFPDictionaryConfig;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.util.Collection;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������������� ������ ������� ������������� ����������� ���
 */
public interface PFPDictionary<DR extends DictionaryRecord> 
{
	/**
	 * �������� ���������� ������ �� ���� �����������
	 * @param dictionaryConfig ��� �����������
	 * @return ���������� ������
	 */
	public Collection<DR> getDictionary(PFPDictionaryConfig dictionaryConfig) throws BusinessException;
}
