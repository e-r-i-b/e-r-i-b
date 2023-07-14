package com.rssl.phizic.logging.operations;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.beanutils.BeanUtils;

import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @author Krenev
 * @ created 13.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class BeanLogParemetersReader extends LogParemetersReaderBase implements LogParametersReader
{
	private Object entity;

	public BeanLogParemetersReader(String description, Object entity)
	{
		super(description);

		this.entity = entity;
	}

	public LinkedHashMap read() throws Exception
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		if (entity == null){
			return map;
		}
		String prefix = entity.getClass().getName() + ".";
		Properties properties = descriptions.getProperties(prefix);
		map.put(entity.getClass().getName(), descriptions.getProperty(entity.getClass().getName()));
		for (Object key : properties.keySet())
		{
			String property = ((String) key).substring(prefix.length());
			String value = BeanUtils.getProperty(entity, property);
			if (StringHelper.isNotEmpty(value))
			{
				String name = descriptions.getProperty((String) key);
				map.put(name, value);
			}
		}
		return map;
	}
}
