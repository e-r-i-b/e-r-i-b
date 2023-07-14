package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Проверка, что сумма источника(счета, карты) не меньше, чем проверяемая
 * Проверка работает с допущением что сумма на счете и проверяемая сумма в одной валюте
 * @author krenev
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class UserResourceAmountValidator extends AmountValidatorBase
{
	public static final String FIELD_RESOURCE = "resource";

	protected BigDecimal getCheckedAmount(Map values) throws TemporalDocumentException
	{
		ExternalResourceLink resourceLink = (ExternalResourceLink) retrieveFieldValue(FIELD_RESOURCE, values);
	    if (resourceLink == null)
	        return null;

	    if (resourceLink instanceof AccountLink){
		    return getAccountAmount((AccountLink)resourceLink);
	    }
		else if (resourceLink instanceof CardLink)
	    {
		    return getCardAmount((CardLink)resourceLink);
	    }
		return null;
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		ExternalResourceLink resourceLink = (ExternalResourceLink) retrieveFieldValue(FIELD_RESOURCE, values);
		if (isStoredResource(resourceLink))
			return true;

		if (super.validate(values))
			return true;

		// Если текстовку не задали, нужно её сгенерировать
		if (StringHelper.isEmpty(getMessage()))
			setMessage(buildMessage(values));
		return false;
	}

	protected String buildMessage(Map values) throws TemporalDocumentException
	{
		ExternalResourceLink link = (ExternalResourceLink) retrieveFieldValue(FIELD_RESOURCE, values);

		if (ApplicationUtil.isErmbSms())
		{
			ErmbProductLink ermbLink = (ErmbProductLink) link;
			//todo Здесь ФИО не заполнено. Исполнитель - Доржинов П.
			String firstName = (String) values.get("receiverFirstName");
			String surName = (String) values.get("receiverSurName");
			String patrName = (String) values.get("receiverPatrName");
			return String.format("Перевод на сумму %s %s с карты %s на карту получателя %s не выполнен.%nНедостаточно средств.",
					values.get("sellAmount"), values.get("sellAmountCurrency"),
					StringUtils.defaultIfEmpty(ermbLink.getErmbSmsAlias(), ermbLink.getSmsResponseAlias()),
					PersonHelper.getFormattedPersonName(firstName, surName, patrName));
		}
		else
		{
			ResourceType resourceType = link.getResourceType();
			StringBuilder builder = new StringBuilder();
			builder.append( "Вы ввели сумму, превышающую остаток средств на " );
			builder.append( resourceType == ResourceType.CARD ? "Вашей карте. " : "Вашем счете. " );
			builder.append( "Пожалуйста, введите другую сумму." );
			return builder.toString();
		}
	}
}
