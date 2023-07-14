package com.rssl.phizic.business.xslt.lists.cache;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.xslt.lists.EntityListDefinition;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.util.List;
import java.util.Map;

/**
 * ���������� ����
 * @author gladishev
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public interface CachedEntityListSource extends EntityListSource
{
	/**
	 * @return ����������� �����������
	 */
	EntityListDefinition getListDefinition();

	/**
	 * @param params - ��������� ���������� �����������
	 * @return - ���� <����������� xml � ���� ������,
	 *                 ������ �������� �� ������� ������� ������ � ������-���� �� ��� ������� �����������>
	 * � ������ �������� ������ ���� ���������� ������� � ������ ������������ � ����������� ����������� (� lists.xml)
	 * � ���� calbackcache-dependences
	 */
	Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException;
}
