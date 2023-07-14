package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.FilterRestriction;

/**
 * ����������� �� ������ � ������������ �����
 *
 * @author hudyakov
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public interface ServiceProvidersRestriction extends Restriction, FilterRestriction
{
	/**
	 * �������� ����������� ������ � ����������� �����
	 * @param provider ��������� �����
	 */
	boolean accept(ServiceProviderBase provider) throws BusinessException;
}

