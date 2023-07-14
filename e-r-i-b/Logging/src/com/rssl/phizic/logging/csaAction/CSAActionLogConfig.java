package com.rssl.phizic.logging.csaAction;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.writers.CSAActionLogWriter;

/**
 * @author vagin
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 */
public abstract class CSAActionLogConfig extends Config
{
	public static final String MAIN_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.CSAActionLogWriter";
	public static final String DB_INSTANCE_NAME = "com.rssl.phizic.logging.writers.CSAActionLogWriter.dbInstanceName";

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	protected CSAActionLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return основной writer
	 */
	public abstract CSAActionLogWriter getWriter();

	/**
	 * @return инстанснейм для DBWriter
	 */
	public abstract String getDbWriterInstanceName();
}
