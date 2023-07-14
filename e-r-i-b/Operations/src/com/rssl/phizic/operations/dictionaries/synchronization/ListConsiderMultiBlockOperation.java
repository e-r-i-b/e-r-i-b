package com.rssl.phizic.operations.dictionaries.synchronization;

import com.rssl.phizic.business.operations.restrictions.Restriction;

/**
 * @author komarov
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * consider - ���������
 * ������� �������� � ������������ ������ ��� ������ ���������������
 */
public class ListConsiderMultiBlockOperation <T extends Restriction> extends ListDictionaryEntityOperationBase <T>
{
	private boolean considerMultiBlock = false;

	@Override
	protected String getInstanceName()
	{
		if(considerMultiBlock)
			return super.getInstanceName();
		return null;
	}

	/**
	 * ��������� ���������������
	 */
	public void considerMultiBlock()
	{
		considerMultiBlock = true;
	}
}
