package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.StringCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * �������� ������ BackRefBankInfoService
 *
 * @author khudyakov
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefBankInfoService extends Service
{
	/**
	 * ����� ���������� �� �����
	 * @param bic ��� �����
	 * @return ����
	 * @throws GateException
	 */
	@Cachable(keyResolver = StringCacheKeyComposer.class, linkable = false, name = "BackRefBankInfo.findByBIC")
	public ResidentBank findByBIC(String bic) throws GateException;
}
