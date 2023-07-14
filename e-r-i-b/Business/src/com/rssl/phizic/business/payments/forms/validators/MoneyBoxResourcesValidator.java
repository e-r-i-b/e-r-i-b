package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.utils.StringHelper;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 * Валидатор проверки что кара и счет открыты в одном ТБ.
 */
public class MoneyBoxResourcesValidator extends MultiFieldsValidatorBase
{
	private static final String ACCOUNT_FIELD_NAME = "toResource";
	private static final String CARD_FIELD_NAME = "fromResource";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Object toResource = retrieveFieldValue(ACCOUNT_FIELD_NAME, values);
			Object fromResource = retrieveFieldValue(CARD_FIELD_NAME, values);
			if (fromResource instanceof CardLink && toResource instanceof AccountLink)
			{
				CardLink cardLink = (CardLink) fromResource;
				AccountLink accountLink = (AccountLink) toResource;
				String cardTB = StringHelper.removeLeadingZeros(cardLink.getOffice().getCode().getFields().get("region"));
				String accountTB = StringHelper.removeLeadingZeros(accountLink.getOfficeTB());

				List<String> tbMoscow = ConfigFactory.getConfig(BusinessSettingsConfig.class).getMoscowTB();
				if (tbMoscow.contains(cardTB) && tbMoscow.contains(accountTB))
					return true;
				if (cardTB != null && accountTB != null)
					return cardTB.equals(accountTB);
				return false;
			}
			return false;
		}
		catch (Exception e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
