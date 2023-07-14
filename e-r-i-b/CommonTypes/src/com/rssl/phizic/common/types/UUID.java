package com.rssl.phizic.common.types;

import com.rssl.phizic.common.types.annotation.Immutable;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Erkin
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Идентификатор
 * Шестнадцатеричное число с  длиной 32 символа.
 * Значение должно соответствовать регулярному выражению «[0-9a-fA-F]{32}».
 * Пример: 1234567890abcdef1234567890abcdef
 * При изменении длины поля нужно изменить поле и в БД, см. UUIDType
 */
@Immutable
public final class UUID implements Serializable
{
	public static final String FORMAT = "[0-9a-fA-F]{32}";

	private static final Pattern pattern = Pattern.compile(FORMAT);

	private final String value;

	///////////////////////////////////////////////////////////////////////////

	private UUID(String value)
	{
		this.value = value;
	}

	/**
	 * Создаёт UUID по строке
	 * @param value - строка с UUID
	 * @return UUID
	 */
	public static UUID fromValue(String value)
	{
		if (value == null || value.length()==0)
			throw new IllegalArgumentException("Не указан UUID");

		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches())
			throw new IllegalArgumentException("UUID " + value + " не соответствует формату " + FORMAT);

		return new UUID(value);
	}

	@Override
	public String toString()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UUID uuid = (UUID) o;

		return value.equals(uuid.value);
	}
}
