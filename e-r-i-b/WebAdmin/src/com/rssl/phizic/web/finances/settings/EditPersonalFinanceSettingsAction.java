package com.rssl.phizic.web.finances.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.finances.settings.EditPersonalFinanceSettingsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.PropertySerializer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author komarov
 * @ created 23.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditPersonalFinanceSettingsAction extends EditPropertiesActionBase<EditPersonalFinanceSettingsOperation>
{
	private static final Map<Class, PropertySerializer> extSerializers;

	static
	{
		extSerializers = new HashMap<Class, PropertySerializer>(serializers);
		extSerializers.put(Date.class, new PropertySerializer()
		{
			public String serialise(Object data)
			{
				return DateHelper.dateToString("%1$tH:%1$tM", (Date) data);
			}
		});
	}

	@Override
	protected EditPersonalFinanceSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditPersonalFinanceSettingsOperation.class);
	}

	@Override
	protected PropertySerializer getSerializer(Class aClass)
	{
		return extSerializers.get(aClass);
	}
}
