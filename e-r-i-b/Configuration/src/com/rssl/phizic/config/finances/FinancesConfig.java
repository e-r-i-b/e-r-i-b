package com.rssl.phizic.config.finances;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lepihina
 * @ created 10.04.14
 * $Author$
 * $Revision$
 */
public class FinancesConfig extends Config
{
	private static final String DELIMITER = ",";
	private static final String TARGET_COUNT_PROPERTY = "com.rssl.iccs.settings.alf.targets.count";
	private static final String TARGET_IMAGE_MAX_SIZE = "com.rssl.iccs.settings.alf.targets.image.size.max";
	private static final String CATEGORY_COUNT_PROPERTY = "com.rssl.iccs.settings.alf.categories.count";
	private static final String FINANCES_CLIENT_CATEGORIES_COLORS = "settings.alf.client.categories.colors";
	private static final String ALF_TRANSFERS_CATEGORIES = "alf.transfers.categories";
	private static final String ALF_OPERATIONS_LINKING_MODE = "alf.operations.linking.enabled";

	private Integer maxClientTarget;
	private Integer �lientTargetImageMaxSize;
	private Integer maxClientCategories;
	private String colors;
	private String[] alfTransfersCategories;
	private Boolean operationLinkingEnabled;

	/**
	 * @param reader - �����
	 */
	public FinancesConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		maxClientTarget = getIntProperty(TARGET_COUNT_PROPERTY);
		�lientTargetImageMaxSize = getIntProperty(TARGET_IMAGE_MAX_SIZE);
		maxClientCategories = getIntProperty(CATEGORY_COUNT_PROPERTY);
		colors = getProperty(FINANCES_CLIENT_CATEGORIES_COLORS);
		alfTransfersCategories = getProperty(ALF_TRANSFERS_CATEGORIES).split(DELIMITER);
		operationLinkingEnabled = getBoolProperty(ALF_OPERATIONS_LINKING_MODE);
	}

	/**
	 * @return ����������� ��������� ���������� �����
	 */
	public Integer getMaxClientTarget()
	{
		return maxClientTarget;
	}

	/**
	 * @return ������������ ��������� ���������� ��������� ��� �������.
	 */
	public Integer getMaxClientCategories()
	{
		return maxClientCategories;
	}

	/**
	 * @return ������ ������, ��������� ��� ���������� ���������
	 */
	public List<String> getColorsList()
	{
		if (StringHelper.isEmpty(colors))
			return Collections.emptyList();

		return Arrays.asList(colors.split(DELIMITER));
	}

	/**
	 * @return ������������ ������ ���������� �������� ��� ����
	 */
	public Integer get�lientTargetImageMaxSize()
	{
		return �lientTargetImageMaxSize;
	}

	public String[] getAlfTransfersCategories()
	{
		return alfTransfersCategories;
	}

	/**
	 * @return ���������� �� ��������� �������� �� ������_��������� � ���������� � CARD_OPERATIONS
	 */
	public Boolean getOperationLinkingEnabled()
	{
		return operationLinkingEnabled;
	}
}
