package com.rssl.phizic.operations.config.writers;

import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.PropertyCategory;

import java.util.Map;

/**
 * Писатель настроек Фрод Мониторинга
 *
 * @author khudyakov
 * @ created 15.07.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringPropertiesWriter implements PropertiesWriter
{
	public void write(Map<String, String> properties)
	{
		DbPropertyService.updateProperties(properties, PropertyCategory.RSA, DbPropertyService.getDbInstance(PropertyCategory.CSABack.getValue()));
		DbPropertyService.updateProperties(properties, PropertyCategory.RSA, DbPropertyService.getDbInstance(PropertyCategory.Phizic.getValue()));
	}
}
