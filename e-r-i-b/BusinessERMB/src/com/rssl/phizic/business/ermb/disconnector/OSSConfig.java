package com.rssl.phizic.business.ermb.disconnector;

import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Gulov
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� �������� ���������� �������� �� ������� ����
 */
public class OSSConfig extends BeanConfigBase<OSSConfigBean>
{
	public static final String CODENAME = "ErmbOSSConfig";

	/**
	 * ���� ������������� ���������� � ���
	 */
	private boolean useIntegration;

	/**
	 * ������������ ���������� ����������� ������� ����� ����������� ��������
	 */
	private int maxRegistrations;

	/**
	 * ������������ ���������� ����������� ������� �� ���
	 */
	private int maxDisconnectors;

	/**
	 * ������������ ���������� ������� ��� ���������� ������� � ���
	 */
	private int maxDisconnectorsForUpdate;

	/**
	 * ������ ����� ������ ����������, ��� ������� ����� �������������� �������� ���
	 */
	private List<Integer> notifyingReasonCodes;

	/**
	 * �����������
	 */
	public OSSConfig(PropertyReader reader)
	{
		super(reader);
	}

	public boolean getUseIntegration()
	{
		return useIntegration;
	}

	public int getMaxRegistrations()
	{
		return maxRegistrations;
	}

	public int getMaxDisconnectors()
	{
		return maxDisconnectors;
	}

	public int getMaxDisconnectorsForUpdate()
	{
		return maxDisconnectorsForUpdate;
	}

	public List<Integer> getNotifyingReasonCodes()
	{
		if (CollectionUtils.isEmpty(notifyingReasonCodes))
			return Collections.emptyList();
		return Collections.unmodifiableList(notifyingReasonCodes);
	}

	@Override
	protected String getCodename()
	{
		return CODENAME;
	}

	@Override
	protected Class<OSSConfigBean> getConfigDataClass()
	{
		return OSSConfigBean.class;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		OSSConfigBean configBean = getConfigData();
		useIntegration = configBean.getUseIntegration();
		maxRegistrations = configBean.getNewClientRegistration().getMaxResults();
		maxDisconnectors = configBean.getDisconnectorPhone().getMaxResults();
		maxDisconnectorsForUpdate = configBean.getDisconnectorPhone().getMaxResultsForUpdate();
		notifyingReasonCodes = configBean.getDisconnectorPhone().getCodes();
	}
}
