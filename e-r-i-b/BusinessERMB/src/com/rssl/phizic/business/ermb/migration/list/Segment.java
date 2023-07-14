package com.rssl.phizic.business.ermb.migration.list;

/**
 * Группа клиентов относительно алгоритма миграции.
 * Один клиент может принадлежать одновременно к разным сегментам.
 * @author Puzikov
 * @ created 05.12.13
 * @ $Author$
 * @ $Revision$
 */

public enum Segment
{

	SEGMENT_1("1"),
	SEGMENT_1_1("1_1"),
	SEGMENT_1_2("1_2"),
	SEGMENT_2_1("2_1"),
	SEGMENT_2_2("2_2"),
	SEGMENT_2_2_1("2_2_1"),
	SEGMENT_3_1("3_1"),
	SEGMENT_3_2_1("3_2_1"),
	SEGMENT_3_2_2("3_2_2"),
	SEGMENT_3_2_3("3_2_3"),
	SEGMENT_4("4"),
	SEGMENT_5_1("5_1"),
	SEGMENT_5_2("5_2"),
	SEGMENT_5_3("5_3"),
	SEGMENT_5_4("5_4"),
	SEGMENT_5_5("5_5");

	private final String value;

	Segment(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public static Segment fromValue(String value)
	{
		for (Segment segment : values())
			if (value.equals(segment.value))
				return segment;

		throw new IllegalArgumentException("Неизвестный тип сегмента[" + value + "]");
	}
}
