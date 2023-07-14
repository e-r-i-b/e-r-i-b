package com.rssl.phizic.business.ermb.migration.list.config;

import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Gulov
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг для отчета клиентских менеджеров в миграции.
 * Хранятся инструкции для менеджеров по каждому сегменту
 */
public class ErmbInstructionMigrationConfig extends BeanConfigBase<ErmbInstructionMigrationConfigBean>
{
	private static final String CODENAME = "ErmbInstructionMigrationConfig";

	/**
	 * Инструкции по сегментам
	 */
	private Map<Segment, String> instructions;

	public ErmbInstructionMigrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	public Map<Segment, String> getInstructions()
	{
		return Collections.unmodifiableMap(instructions);
	}

	@Override
	protected String getCodename()
	{
		return CODENAME;
	}

	@Override
	protected Class<ErmbInstructionMigrationConfigBean> getConfigDataClass()
	{
		return ErmbInstructionMigrationConfigBean.class;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		ErmbInstructionMigrationConfigBean configData = getConfigData();

		instructions = new EnumMap<Segment, String>(Segment.class);
		instructions.put(Segment.SEGMENT_1, configData.getInstruction1());
		instructions.put(Segment.SEGMENT_1_1, configData.getInstruction1_1());
		instructions.put(Segment.SEGMENT_1_2, configData.getInstruction1_2());
		instructions.put(Segment.SEGMENT_2_1, configData.getInstruction2_1());
		instructions.put(Segment.SEGMENT_2_2, configData.getInstruction2_2());
		instructions.put(Segment.SEGMENT_2_2_1, configData.getInstruction2_2_1());
		instructions.put(Segment.SEGMENT_3_1, configData.getInstruction3_1());
		instructions.put(Segment.SEGMENT_3_2_1, configData.getInstruction3_2_1());
		instructions.put(Segment.SEGMENT_3_2_2, configData.getInstruction3_2_2());
		instructions.put(Segment.SEGMENT_3_2_3, configData.getInstruction3_2_3());
		instructions.put(Segment.SEGMENT_4, configData.getInstruction4());
		instructions.put(Segment.SEGMENT_5_1, configData.getInstruction5_1());
		instructions.put(Segment.SEGMENT_5_2, configData.getInstruction5_2());
		instructions.put(Segment.SEGMENT_5_3, configData.getInstruction5_3());
		instructions.put(Segment.SEGMENT_5_4, configData.getInstruction5_4());
		instructions.put(Segment.SEGMENT_5_5, configData.getInstruction5_5());
	}
}
