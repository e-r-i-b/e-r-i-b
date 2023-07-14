package com.rssl.phizic.business.ermb.sms.config;

import com.rssl.phizic.business.ermb.sms.parser.Dictionary;
import com.rssl.phizic.business.ermb.sms.parser.PreprocessorFilter;
import com.rssl.phizic.business.ermb.sms.parser.ReplacementPreprocessorFilter;
import com.rssl.phizic.business.ermb.sms.parser.SendSmsPreprocessorFilter;
import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.*;

/**
 * @author Erkin
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���-������ ����
 */
public class SmsConfig extends BeanConfigBase<SmsConfigBean>
{
	public static final String CODENAME = "ErmbSmsConfig";

	private static final int SMS_MAX_LENGTH = 1024;

	private Collection<PreprocessorFilter> preprocessorFilters;

	private Collection<CommandDefinition> commandDefinitions;
	
	private Collection<CommandDefinition> enabledCommandDefinitions;

	private int dublicateExpireInterval;

	private int incompletePaymentsExpireInterval;

	///////////////////////////////////////////////////////////////////////////

	public SmsConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected String getCodename()
	{
		return CODENAME;
	}

	protected Class<SmsConfigBean> getConfigDataClass()
	{
		return SmsConfigBean.class;
	}

	/**
	 * ���������� ��� ������� �������������
	 * @return ������ ��������
	 */
	public Collection<PreprocessorFilter> getPreprocessorFilters()
	{
		return preprocessorFilters;
	}

	/**
	 * ���������� ��� ���-�������
	 * @return �������� �������-���������
	 */
	public Collection<CommandDefinition> getAllCommandDefinitions()
	{
		return commandDefinitions;
	}

	/**
	 * @return ��������� ������ ������ (� ������� ��������)
	 */
	public Set<String> getAllCommandAliases()
	{
		Set<String> smsCommands = new HashSet<String>();
		for (CommandDefinition command : getAllCommandDefinitions())
		{
			for (String smsCommand : command.getKeywords().getWords())
			{
				smsCommands.add(smsCommand.toUpperCase());
			}
		}
		return smsCommands;
	}

	/**
	 * ���������� ��������� ��� ������������� ���-�������
	 * @return �������� �������-��������� � ���������� ������� "enabled"
	 */
	public Collection<CommandDefinition> getEnabledCommandDefinitions()
	{
		return enabledCommandDefinitions;
	}

	/**
	 * �������� �������������� �������� ���-��������
	 * @return ���������� ����� ���������
	 */
	public int getDublicateExpireInterval()
	{
		return dublicateExpireInterval;
	}

	/**
	 * ���������� ����-��� ��� �������� ������������� ��������
	 * @return �������� � ��������
	 */
	public int getIncompletePaymentsExpireInterval()
	{
		return incompletePaymentsExpireInterval;
	}

	/**
	 * ���������� ����������� ���������� ����� ���
	 * @return ����������� ���������� ����� ������ ���
	 */
	public int getSmsMaxLength()
	{
		return SMS_MAX_LENGTH;
	}

	protected void doRefresh() throws ConfigurationException
	{
		// 1. ������ ������ ������� �� ���� ��� �� �����
		SmsConfigBean configBean = getConfigData();

		// 2. ������ ������ ���� ��������������� ��������
		preprocessorFilters = buildPreprocessorFilters(configBean);

		// 2. ������ ������ ���� ������
		commandDefinitions = buildCommandDefinitions(configBean);

		// 3. ������ ������ ��������� ������
		enabledCommandDefinitions = new ArrayList<CommandDefinition>(commandDefinitions.size());
		for (CommandDefinition commandDefinition : commandDefinitions) {
			if (commandDefinition.isEnabled())
				enabledCommandDefinitions.add(commandDefinition);
		}

		// 4. �������� �������������� �������� ���-��������
		dublicateExpireInterval = configBean.getDublicateFilter().getValue();

		//5. ����-��� �������� ������������� ��������
		incompletePaymentsExpireInterval = configBean.getIncompletePaymentsRemover().getValue();
	}

	private List<PreprocessorFilter> buildPreprocessorFilters(SmsConfigBean configBean)
	{
		PreprocessorBean preprocessor = configBean.getPreprocessor();

		if(preprocessor != null)
		{
			List<ReplacementFilterBean> replacementFilterBeans = preprocessor.getReplacementFilters();
			List<SendSmsFilterBean> sendSmsFilterBeans = preprocessor.getSendSmsFilters();

			List<PreprocessorFilter> filters = new ArrayList<PreprocessorFilter>(replacementFilterBeans.size() + sendSmsFilterBeans.size());

			for(ReplacementFilterBean bean : replacementFilterBeans)
			{
				ReplacementPreprocessorFilter filter = new ReplacementPreprocessorFilter();
				filter.setSearchStrings(bean.getSearchStrings());
				filter.setReplacement(bean.getReplacement());
				filters.add(filter);
			}

			for(SendSmsFilterBean bean : sendSmsFilterBeans)
			{
				SendSmsPreprocessorFilter filter = new SendSmsPreprocessorFilter();
				filter.setSearchStrings(bean.getSearchStrings());
				filter.setSmsText(bean.getSmsText());
				filters.add(filter);
			}

			return filters;
		}

		return Collections.emptyList();
	}

	private List<CommandDefinition> buildCommandDefinitions(SmsConfigBean configBean)
	{
		List<CommandDefinitionBean> beans = configBean.getCommands();
		List<CommandDefinition> definitions = new ArrayList<CommandDefinition>(beans.size());
		for (CommandDefinitionBean bean : beans)
		{
			String codename = bean.getCodename();
			CommandDefinition definition = CommandDefinition.fromCodename(codename);
			if (definition == null) {
				log.error("�� ������� ��������� ���-������� � ������������� " + codename);
				continue;
			}

			definition.setEnabled(bean.isEnabled());
			definition.setKeywords(new Dictionary(bean.getKeywords()));
			definition.setParseOrder(bean.getParseOrder());
			definitions.add(definition);
		}
		return definitions;
	}
}
