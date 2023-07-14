package com.rssl.phizic.utils.json;

import com.google.gson.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.PhoneNumber;

import java.lang.reflect.Type;

/**
 * @author  Moshenko
 * @ created 05.02.2014
 * @ $Author$
 * @ $Revision$
 * Сериализует  валюту
 */

public class CurrencyTypeAdapter implements JsonSerializer<Currency>
{
	public JsonElement serialize(Currency currencyr, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(currencyr.getCode());
	}
}
