package com.rssl.phizic.web.log;

import com.rssl.phizic.logging.operations.LogParametersReader;
import com.rssl.phizic.logging.operations.LogParemetersReaderBase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Ридер для записи параметров в журнал из мапа
 * @author shapin
 * @ created 21.04.15
 * @ $Author$
 * @ $Revision$
 */
public class MapLogParametersReader extends LogParemetersReaderBase implements LogParametersReader
{
	private Map<String, Object> parameters;

	public MapLogParametersReader(String description, Map<String, Object> parameters)
	{
		super(description);
		this.parameters = parameters;
	}

	public LinkedHashMap read() throws Exception
	{
		return new LinkedHashMap<String, Object>(parameters);
	}
}
