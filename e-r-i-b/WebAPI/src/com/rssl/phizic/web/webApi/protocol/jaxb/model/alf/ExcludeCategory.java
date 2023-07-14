package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Исключаемые категории операций
 *
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlEnum
public enum ExcludeCategory
{
	/**
	 * Перевод между своими счетами
	 */
	@XmlEnumValue(value = "TransferBetweenАccounts")
	TRANSFER_BETWEEN_АCCOUNTS("TransferBetweenАccounts"),

	/**
	 * Перевод на вклад
	 */
	@XmlEnumValue(value = "TransferToDeposit")
	TRANSFER_TO_DEPOSIT("TransferToDeposit"),

	/**
	 * Перевод с карты
	 */
	@XmlEnumValue(value = "TransferFromCard")
	TRANSFER_FROM_CARD("TransferFromCard");

	private String value;

	ExcludeCategory(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return this.value;
	}

	/**
	 * @param categories список категорий
	 * @return Массив исключаемых категорий
	 */
	public static String[] toArray(List<ExcludeCategory> categories)
	{
		if (CollectionUtils.isNotEmpty(categories))
		{
			String[] values = new String[categories.size()];

			int i = 0;
			for (ExcludeCategory category : categories)
			{
				values[i++] = category.toValue();
			}

			return values;
		}

		return new String[0];
	}
}
