package com.rssl.phizic.business.informers;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * ���������� ������� � �����-���� �������
 *
 * @author khudyakov
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentStateInformer
{
	/**
	 * �������������
	 * @return ������ ���������
	 */
	List<String> inform() throws BusinessException;

	/**
	 * @return true - ���������� �������
	 */
	boolean isActive();
}
