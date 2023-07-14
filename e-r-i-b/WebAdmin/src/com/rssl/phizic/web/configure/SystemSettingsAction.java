package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.config.SetSystemSettingsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.PropertySerializer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 29.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class SystemSettingsAction extends EditPropertiesActionBase<SetSystemSettingsOperation>
{
	private static final Map<Class, PropertySerializer> extSerializers;

	static
	{
		extSerializers = new HashMap<Class, PropertySerializer>(serializers);
		extSerializers.put(Date.class, new PropertySerializer()
		{
			public String serialise(Object data)
			{
				return DateHelper.dateToString("%1$tH:%1$tM:%1$tS", (Date) data);
			}
		});
	}

	@Override
	protected SetSystemSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditSystemSettingsOperation");
	}

	@Override
	protected SetSystemSettingsOperation getViewOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewSystemSettingsOperation");
	}

	@Override
	protected PropertySerializer getSerializer(Class aClass)
	{
		return extSerializers.get(aClass);
	}
}
