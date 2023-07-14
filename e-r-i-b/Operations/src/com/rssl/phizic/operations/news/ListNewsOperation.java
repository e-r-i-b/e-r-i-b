package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.business.news.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.synchronization.ListConsiderMultiBlockOperation;
import com.rssl.phizic.utils.ClientConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: Zhuravleva
 * Date: 27.11.2006
 * Time: 12:36:02
 */
public class ListNewsOperation extends ListConsiderMultiBlockOperation
{
	private static final BusinessPropertyService businessPropertyService = new BusinessPropertyService();

	/**
	 * @return ������������ ����� �������� �� ��������
	 * @throws BusinessException
	 */
	public int getMaxDataLength() throws BusinessException
	{
		return ConfigFactory.getConfig(ClientConfig.class).getNewsCount();
	}

	/**
	 * ����� �������� ��� ��������
	 * @param news - ������ ��������
	 * @return - ���, ������� ��� ������ ��������� � ���� �������� ������ � ���� � ����� ����� ����� ���� ��� ���<id ������� ������� ����������� ��������, ��������>
	 */
	public Map<Long, NewsDistribution> findNewsDistributions(List<News> news) throws BusinessException
	{
       return new NewsDistributionsSelector(news, Constants.DB_CSA.equals(getInstanceName()) ? NewsDistributionType.LOGIN_PAGE : NewsDistributionType.MAIN_PAGE);
	}

	@Override
	public Query createQuery(String name)
	{
		return MultiLocaleQueryHelper.getOperationQuery(this,name,getInstanceName());
	}
}
