package com.rssl.phizic.config.ips;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.rssl.phizic.config.ips.IPSConstants.*;

/**
 * @author Erkin
 * @ created 04.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class IPSConfig extends Config
{
	private static final String DELIMITER = ",";
	private static final String CLAIMS_BATCH_SIZE = "claims.batch.size";
	private static final String OPERATIONS_BATCH_SIZE = "operations.batch.size";
	private static final String CLAIM_EXECUTION_MAX_TIME = "claim.execution.max.time";
	private static final String CB_CURRENCY_RATE_OFFICE_TB = "cb.currency.rate.office.tb";
	private static final String DB_MAX_PARAMS = "database.max.params";
	public static final String DATA_SOURCE_NAME = "datasource.name";
	public static final String LINKING_MAX_DATE_DIFF = "linking.max.date.diff";
	public static final String LINKING_OPERATIONS_MCC_CODES = "linking.operations.mccCodes";
	public static final String LOADING_PACK_SIZE_IN_MONTHS = "loading.pack.size.in.months";

	private  Integer claimsBatchSize;
	private  Integer operationsBatchSize;
	private  Integer claimExecutionMaxTime;
	private  String  cbCurrencyOfficeRegion;
	private  Integer databaseMaxParams;
	private  Integer claimAutoUpdateFrequency;

	private  Integer cardOperationMaxTime;
	private  Integer connectionTimeout;
	private  Integer claimExecutionAttemptMaxNum;
	private  Boolean logCardOperationCategoryOn;
	private  Boolean logFilterOutcomePeriodOn;
	private  String  cardToCardMCCCodesList;
	private  Long internalOperationsMaxDiffTime;
	private Integer loadingPackSizeInMonths;

	private  Integer maxPauseInUsingFinances;
	private  Float minProbabilityOfUsingFinances;
	private  Integer minCountOfUsingFinances;
	private  Integer maxClientsToUpdateClaims;
	private  int maxDateDiffForLinking;
	private  String linkingOperationsMccCodes;

	private String dataSourceName;

	private int cardOperationLifetime;

	public IPSConfig(PropertyReader reader)
	{
		super(reader);
	}


	public void doRefresh() throws ConfigurationException
	{
		try
		{
			claimsBatchSize = getIntProperty(CLAIMS_BATCH_SIZE);
			operationsBatchSize = getIntProperty(OPERATIONS_BATCH_SIZE);
			claimExecutionMaxTime = getIntProperty(CLAIM_EXECUTION_MAX_TIME);
			databaseMaxParams = getIntProperty(DB_MAX_PARAMS);
			String tb  = getProperty(CB_CURRENCY_RATE_OFFICE_TB);
			if (StringHelper.isEmpty(tb))
				throw new ConfigurationException("�� ����� ������� ��� ��������� ��-������ �����");
			cbCurrencyOfficeRegion = tb;
			internalOperationsMaxDiffTime = getLongProperty(INTERNAL_OPERATIONS_MAX_TIME);
			cardToCardMCCCodesList = getProperty(CARD_TO_CARD_OPERATIONS_MCC_CODES);
			loadingPackSizeInMonths = getIntProperty(LOADING_PACK_SIZE_IN_MONTHS);

			cardOperationMaxTime = getIntProperty(CARD_OPERATION_MAX_TIME);
			connectionTimeout = getIntProperty(CONNECTION_TIMEOUT);
			claimExecutionAttemptMaxNum = getIntProperty(CLAIM_EXECUTION_ATTEMPT_MAX_NUM);
			logCardOperationCategoryOn = getBoolProperty(LOG_CARD_OPERATION_CATEGORY);
			logFilterOutcomePeriodOn = getBoolProperty(LOG_FILTER_OUTCOME_PERIOD);

			maxPauseInUsingFinances = getIntProperty(MAX_PAUSE_IN_USING_FINANCES);
			minProbabilityOfUsingFinances = getFloatProperty(MIN_PROBABILITY_OF_USING_FINANCES);
			minCountOfUsingFinances = getIntProperty(MIN_COUNT_OF_USING_FINANCES);
			maxClientsToUpdateClaims = getIntProperty(MAX_CLIENTS_TO_UPDATE_CLAIMS);

			claimAutoUpdateFrequency = getIntProperty(CLAIM_AUTO_UPDATE_FREQUENCY);
			maxDateDiffForLinking = getIntProperty(LINKING_MAX_DATE_DIFF);
			linkingOperationsMccCodes = getProperty(LINKING_OPERATIONS_MCC_CODES);

			dataSourceName = getProperty(DATA_SOURCE_NAME);

			cardOperationLifetime = getIntProperty(CARD_OPERATION_LIFETIME);
		}
		catch (NumberFormatException e)
		{
			throw new ConfigurationException("������ �������� �������� ���", e);
		}
	}


	/**
	 * @return ������������ ����� ������, �������������� �� ���� ������ job-�
	 */
	public int getClaimsBatchSize()
	{
		return claimsBatchSize;
	}

	/**
	 * ������ ������ ��������
	 * @return ������������ ���������� �������� � ������
	 */
	public int getOperationsBatchSize()
	{
		return operationsBatchSize;
	}

	/**
	 * @return ��������� ���������� ����� ������� ������ ������ � ���� ���������� ����������,
	 * ��� ���� ����� ���� ������� ������ � ������ "������� ���������"
	 */
	public int getClaimAutoUpdateFrequency()
	{
		return claimAutoUpdateFrequency;
	}

	/**
	 * ������������ ������� ��������� ��������
	 * ���������� ������ ��������� ��� ������������ ��������
	 * @return ������������ ������� ��������� �������� � ����
	 */
	public int getCardOperationMaxTime()
	{
		return cardOperationMaxTime;
	}

	/**
	 * ����������� ���������� ����� ���������� ������
	 * ������, ������������� ������ ����� ��������, ��������� "�����������" �� ����������
	 * @return ����������� ���������� ����� ���������� ������ � ��������
	 */
	public int getClaimExecutionMaxTime()
	{
		return claimExecutionMaxTime;
	}

	/**
	 * ���������� ��, � ������� ����� �������� ����� ����� ��
	 * @return ��� �� � ������� ��
	 */
	public String getCbCurrencyOfficeRegion()
	{
		return cbCurrencyOfficeRegion;
	}

	private List<Long> convertStringToMCCCodeList(String mccCodes)
	{
		if (StringHelper.isEmpty(mccCodes))
			return Collections.emptyList();

		List<String> mccCodesStr = Arrays.asList(mccCodes.split(DELIMITER));
		List<Long> codes = new ArrayList<Long>(mccCodesStr.size());
		for(String mccCode : mccCodesStr)
			codes.add(Long.valueOf(mccCode.trim()));
		return codes;
	}

	/**
	 * @return ������ mcc-�����, �� ������� �������� �������� �����-�����
	 */
	public List<Long> getCardToCardMCCCodesList()
	{
		return convertStringToMCCCodeList(cardToCardMCCCodesList);
	}

	/**
	 * @return ������ mcc-�����, �� ������� �������� �������� �����-�����
	 */
	public String getCardToCardMCCCodes()
	{
		return cardToCardMCCCodesList;
	}

	/**
	 * @return ���������� ����� ����������� � ��������, ��� ���� ����� �������� ����� ��������� ��������� ����� ������ �������
	 */
	public Long getInternalOperationsMaxDiffTime()
	{
		return internalOperationsMaxDiffTime;
	}

	/**
	 * @return ����� �� ���������� ������� � ��� (� ��������)
	 */
	public int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	/**
	 * @return ������������ ���������� ������� ��������� ���� ������
	 */
	public int getClaimExecutionAttemptMaxNum()
	{
		return claimExecutionAttemptMaxNum;
	}

	/**
	 * @return �������� �� ����������� true - ��������
	 */
	public boolean isLogCardOperationCategoryOn()
	{
		return logCardOperationCategoryOn;
	}

	/**
	 * @return �������� �� ����������� true - ��������
	 */
	public boolean isLogFilterOutcomePeriodOn()
	{
		return logFilterOutcomePeriodOn;
	}

	/**
	 * ���������� ������������ ���������� ����������, ������������ ���� ������
	 * @return ������������ ���������� ����������
	 */
	public int getDatabaseMaxParams()
	{
		return databaseMaxParams;
	}

	/**
	 * @return ������������ ������� (� ����) � ������������� ���
	 */
	public int getMaxPauseInUsingFinances()
	{
		return maxPauseInUsingFinances;
	}

	/**
	 * @return ����������� �������� ����������� ������������� ��� ��� � 3 ���
	 */
	public float getMinProbabilityOfUsingFinances()
	{
		return minProbabilityOfUsingFinances;
	}

	/**
	 * @return ����������� ���������� ������������� ���
	 */
	public int getMinCountOfUsingFinances()
	{
		return minCountOfUsingFinances;
	}

	/**
	 * @return ������������ ���������� ������� ������������ ��� ��������, �������������� �� ���� ������
	 */
	public int getMaxClientsToUpdateClaims()
	{
		return maxClientsToUpdateClaims;
	}

	public String getDataSourceName()
	{
		return dataSourceName;
	}

	/**
	 * @return ����������� ���� ���������� ���������� ��������� � �������� BUSSINES_DOCUNENTS, CARD_OPERATION
	 */
	public int getMaxDateDiffForLinking()
	{
		return maxDateDiffForLinking;
	}

	/**
	 * @return ������ MCC-����� ��� ������� ���������� ����������� �������� ��� ������
	 */
	public List<Long> getLinkingOperationsMccCodeList()
	{
		return convertStringToMCCCodeList(linkingOperationsMccCodes);
	}

	/**
	 * @return ������ ����� � ����������, ������������ �� ��, ��� ��������� �������� ��������� �� ���
	 */
	public Integer getLoadingPackSizeInMonths()
	{
		return loadingPackSizeInMonths;
	}

	/**
	 * @return ����� ����� ��������
	 */
	public int getCardOperationLifetime()
	{
		return cardOperationLifetime;
	}
}
