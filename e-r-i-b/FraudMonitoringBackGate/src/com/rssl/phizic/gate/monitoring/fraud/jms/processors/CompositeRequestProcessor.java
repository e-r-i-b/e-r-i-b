package com.rssl.phizic.gate.monitoring.fraud.jms.processors;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.rssl.phizic.gate.monitoring.fraud.Constants.ROLLBACK_ERROR_MESSAGE;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_GATE;

/**
 * Композитный обработчик запроса от ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CompositeRequestProcessor implements RequestProcessor
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_GATE);

	private List<RequestProcessor> processors = new ArrayList<RequestProcessor>();
	private List<RequestProcessor> exhaustProcessors = new ArrayList<RequestProcessor>();

	public CompositeRequestProcessor(RequestProcessor ... processors)
	{
		if (ArrayUtils.isNotEmpty(processors))
		{
			Collections.addAll(this.processors, processors);
		}
	}

	public boolean isEnabled()
	{
		throw new UnsupportedOperationException();
	}

	public void process() throws Exception
	{
		try
		{
			for (RequestProcessor processor : processors)
			{
				if (processor.isEnabled())
				{
					processor.process();
					exhaustProcessors.add(processor);
				}
			}
		}
		catch (Exception e)
		{
			rollback();
			throw e;
		}
	}

	public void rollback()
	{
		for (RequestProcessor processor : exhaustProcessors)
		{
			try
			{
				processor.rollback();
			}
			catch (Exception e)
			{
				log.error(String.format(ROLLBACK_ERROR_MESSAGE, processor.getClass().getName()), e);
			}
		}
	}
}
