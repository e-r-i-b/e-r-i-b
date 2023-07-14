package com.rssl.phizgate.common.basket;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


/**
 * @author osminin
 * @ created 13.05.14
 * @ $Author$
 * @ $Revision$
 *
 * хелпер для сериализации десериализации реквизитов сущностей функционала "корзина платежей"
 */
public class RequisitesHelper
{
	/**
	 * Сериализация списка полей в строку
	 * @param fields список полей
	 * @return строка с описанием полей
	 */
	public static String serialize(List<? extends Field> fields) throws Exception
	{
		if (fields == null)
		{
			return null;
		}

		RequisitesSerializer serializer = new RequisitesSerializer(fields);
		return serializer.serialize();
	}

	/**
	 * десериализовать строку в список полей
	 * @param data строка с данными о полях, полученная методом serialize
	 * @return десериализованная строка.
	 */
	public static List<Field> deserialize(String data) throws DocumentException
	{
		if (StringHelper.isEmpty(data))
		{
			return Collections.EMPTY_LIST;
		}
		RequisitesSAXSource requisitesSAXSource = new RequisitesSAXSource(data);
		return requisitesSAXSource.getSource();
	}

	/**
	 * Получить сумму из реквизитов
	 * @param data реквизиты в виде xml
	 * @return значение главной суммы
	 * @throws DocumentException
	 */
	public static BigDecimal getAmount(String data) throws DocumentException
	{
		List<Field> fields = deserialize(data);
		if(CollectionUtils.isEmpty(fields))
			return null;

		for(Field field : fields)
		{
			if(field.isMainSum())
				return new BigDecimal((String) field.getValue());
		}

		return null;
	}
}
