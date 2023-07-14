package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;

/**
 * @author komarov
 * @ created 05.03.2014
 * @ $Author$
 * @ $Revision$
 */
public interface TbNumberRestriction extends Restriction
{
	/**
	 * �������� ����������� ������ � �������������� �� ��� ��������
	 * @param tb ����� ��
	 */
	boolean accept(String tb) throws BusinessException;

}
