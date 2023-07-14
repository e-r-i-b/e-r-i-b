package com.rssl.phizic.operations.csaadmin.node;

import com.rssl.phizic.business.BusinessException;

import java.util.Collections;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������������� ����� �����
 */
public class SelfChangeNodeOperation extends ChangeNodeOperation
{
	/**
	 * ������������� ��������
	 * @param nodeId - ������������� ����� � ������� ���������� �������
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.operations.csaadmin.node.NodeNotAvailableException -- ������� ���� �� ��������
	 */
	public void initialize(Long nodeId) throws BusinessException, NodeNotAvailableException
	{
		super.initialize(nodeId,null, Collections.<String,String>emptyMap());
	}

}
