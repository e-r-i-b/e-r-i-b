package com.rssl.phizic.operations.card.delivery;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.utils.CardsConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author akrenev
 * @ created 13.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ќпераци€ редактировани€ управлени€ оповещением
 */

public class EditEmailDeliverySettingsOperation extends EditPropertiesOperation<Restriction>
{
	private static final Set<String> keys = new HashSet<String>(2);

	static
	{
		keys.add(CardsConfig.SHOW_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY);
		keys.add(CardsConfig.TEXT_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY);
	}

	@Override
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, keys);
	}
}
