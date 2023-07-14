package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;

import java.util.List;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ServiceProviderFetcher
{
	/**
	 * ������� ���������� �����
	 * @param source ������
	 */
	void collect(Map<String, Object> source);

	/**
	 * @return ��������� �����
	 */
	BillingServiceProvider getProvider();
	/**
	 *
	 * @return ������ ���������� ���������� �����
	 */
	List<String> getErrors();

	/**
	 * ������������� �� ��������� �����
	 * @return ��/���
	 */
	boolean isReplicated();

	/**
	 * @return  ��������� � ������ ����������� �����
	 */
	ServiceProviderForReplicationWrapper getWrapper();
}
