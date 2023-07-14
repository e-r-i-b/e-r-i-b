package com.rssl.phizic.jmx;

import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * ������ ��� ������-��������, ��� ������� ��������� ����������� ��������� � �������������� jmx
 @author Pankin
 @ created 15.04.2011
 @ $Author$
 @ $Revision$
 */
public class BusinessSettingsConfig extends Config implements BusinessSettingsConfigMBean
{
	//����� �������� ������������ ����������� ��-��������� (� �����)
	public static final String DEFAULT_EXTENDED_LOGGING_TIME = "com.rssl.business.default.extended.logging.time";

	//�������� ���� �������������� CAP ������.
	private static final String CAP_COMPATIBLE_CARD_NUMBERS = "CAP_compatible_card_numbers";

	// ���� ������� �� ��� �������
	private static final String TB_REPLACEMENTS_KEY = "com.rssl.iccs.specific.tb.replacements";

	// ������������ jms ��� ������� ����
	private static final String RATE_CACHE_CLEAR_USE_JMS = "com.rssl.iccs.rate.cache.clear.jms";

	// �������� �� ������������ ������ �������� ��� ���������� �����, �������� � ��
	private static final String CHANGE_ACCOUNT_OWNER_ENABLED = "com.rssl.iccs.change.account.owner.enabled";

	// ������������ ����� ������ ����������. � �� ���������� 50 ���������
	private static final String MAX_LENGTH_LOGINS = "com.rssl.iccs.logins.maxlength";

	// �������� ������� � ����������� � �������������� ������
	private static final String SPOOBK_TABLE_NAME = "com.rssl.phizic.web.configure.spoobk_table_name";
	//�������� ����� �� ���� ������
	private static final String SPOOBK_LINK_NAME = "com.rssl.phizic.web.configure.spoobk_link_name";
	//������ ��, ���������� ����� � ��� �� �� ������ ������
	private static final String SYNONYM_TB_OF_MOSCOW = "com.rssl.iccs.specific.tb.moscow.synonym";

	private String CAPCompatibleCardNumbers;
	//������ ���������� ���� �������������� CAP ������ (����������� � ������������ �����)
	//��������: 5469030010000100-5469030010299999
	//	        4294030010088888-4294030010099999
	private List<MinMax<String>> CAPCompatibleCardNumbersList;

	private String specificTBReplacements;
	private Map<String, String> tbReplacements;
	private List<String> moscowTB;
	private boolean clearRateCacheUseJMS;
	private boolean changeAccountOwnerEnabled;
	private int maxLengthLogins;
	private boolean spoobkEncoding;
	private int defaultExtendedLoggingTime;
	private String spoobkTableName;
	private String spoobkLinkName;



	public BusinessSettingsConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		CAPCompatibleCardNumbers = getProperty(CAP_COMPATIBLE_CARD_NUMBERS);
		CAPCompatibleCardNumbersList = refreshCardBuondPairList(CAPCompatibleCardNumbers);
		specificTBReplacements = getProperty(TB_REPLACEMENTS_KEY);
		tbReplacements = refreshTBReplacements(specificTBReplacements);
		clearRateCacheUseJMS = Boolean.parseBoolean(getProperty(RATE_CACHE_CLEAR_USE_JMS));
		changeAccountOwnerEnabled = Boolean.parseBoolean(getProperty(CHANGE_ACCOUNT_OWNER_ENABLED));
		maxLengthLogins = Integer.parseInt(getProperty(MAX_LENGTH_LOGINS));
		defaultExtendedLoggingTime = NumericUtil.parseInt(getProperty(DEFAULT_EXTENDED_LOGGING_TIME));
		spoobkEncoding = Boolean.parseBoolean(getProperty("com.rssl.iccs.spoobk.encoding.enabled"));
		spoobkTableName = getProperty(SPOOBK_TABLE_NAME);
		spoobkLinkName = getProperty(SPOOBK_LINK_NAME);
		moscowTB = refreshSynonymMoscowTB(getProperty(SYNONYM_TB_OF_MOSCOW));

	}

	public String getCAPCompatibleCardNumbers()
	{
		return CAPCompatibleCardNumbers;
	}

	public List<MinMax<String>> getCAPCompatibleCardNumbersList()
	{
		return Collections.unmodifiableList(CAPCompatibleCardNumbersList);
	}

	public String getSpecificTBReplacements()
	{
		return specificTBReplacements;
	}

	/**
	 * �������� ����� ��� ��������������� ��
	 * @return ����� � �������� ��� �������������
	 */
	public Map<String, String> getTBReplacementsMap()
	{
		return Collections.unmodifiableMap(tbReplacements);
	}

	public boolean getClearRateCacheUseJMS()
	{
		return this.clearRateCacheUseJMS;
	}

	public boolean isChangeAccountOwnerEnabled()
	{
		return this.changeAccountOwnerEnabled;
	}

	public int getMaxLengthLogins()
	{
		return this.maxLengthLogins;
	}

	private List<MinMax<String>> refreshCardBuondPairList(String property)
	{
		if (StringHelper.isEmpty(property))
			return Collections.emptyList();

		String[] cardBounds = property.split(",");
		List<MinMax<String>> cardBoundsPair = new ArrayList<MinMax<String>>();
		for (String cardBound: cardBounds)
		{
			String[] buondList = StringUtils.splitByWholeSeparator(cardBound,"-");
			MinMax<String> cardPair = new MinMax<String>(buondList[0],buondList[1]);
			cardBoundsPair.add(cardPair);
		}
		return cardBoundsPair;
	}

	private Map<String, String> refreshTBReplacements(String specificTBReplacements)
	{
		if (StringHelper.isEmpty(specificTBReplacements))
			return Collections.emptyMap();

		String[] replacements = specificTBReplacements.split(",");
		Map<String, String> replacementsMap = new HashMap<String, String>();
		for (String replacement : replacements)
		{
			String[] replacementElements = StringUtils.splitByWholeSeparator(replacement, ":");
			replacementsMap.put(replacementElements[0], replacementElements[1]);
		}
		return replacementsMap;
	}

	private List<String> refreshSynonymMoscowTB(String specificTB){
		if (StringHelper.isEmpty(specificTB))
			return Collections.emptyList();

		String[] synonym = specificTB.split(",");
		return Arrays.asList(synonym);
	}

	public boolean isSpoobkEncoding()
	{
		return spoobkEncoding;
	}

	/**
	 * @return ����� �������� ������������ ����������� ��-��������� (� �����)
	 */
	public int getDefaultExtendedLoggingTime()
	{
		return defaultExtendedLoggingTime;
	}

	/**
	 * �������� ������� � ����������� � �������������� ������
	 * @return spoobkTableName
	 */
	public String getSpoobkTableName()
	{
		return spoobkTableName;
	}

	/**
	 * �������� ����� �� ���� ������
	 * @return spoobkLinkName dbLink
	 */
	public String getSpoobkLinkName()
	{
		return spoobkLinkName;
	}

	/**
	 * ����� ���������� ������, ����������� � ������ ��
	 * @return  ���� �������, ���������� ���������
	 */
	public List<String> getMoscowTB()
	{
		return moscowTB;
	}
}

