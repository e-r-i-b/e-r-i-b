package com.rssl.phizic.common.types;

/**
 * User: moshenko
 * Date: 04.10.12
 * Time: 10:40
 */
public enum Day {

	SUNDAY("SUN"),
	MONDAY("MON"),
	TUESDAY("TUE"),
	WEDNESDAY("WED"),
	THURSDAY("THU"),
	FRIDAY("FRI"),
	SATURDAY("SAT");

	private String value;

	Day(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

    public String toFullName()
    {
        if( value.equals(SUNDAY.value)) return "Sunday";
        if( value.equals(MONDAY.value)) return "Monday";
        if( value.equals(TUESDAY.value)) return "Tuesday";
        if( value.equals(WEDNESDAY.value)) return "Wednesday";
        if( value.equals(THURSDAY.value)) return "Thursday";
        if( value.equals(FRIDAY.value)) return "Friday";
        if( value.equals(SATURDAY.value)) return "Saturday";
        throw new IllegalArgumentException("Неизвестный тип  [" + value + "]");
    }

	public static Day fromValue(String value)
	{
		if( value.equals(SUNDAY.value)) return SUNDAY;
		if( value.equals(MONDAY.value)) return MONDAY;
		if( value.equals(TUESDAY.value)) return TUESDAY;
		if( value.equals(WEDNESDAY.value)) return WEDNESDAY;
		if( value.equals(THURSDAY.value)) return THURSDAY;
		if( value.equals(FRIDAY.value)) return FRIDAY;
		if( value.equals(SATURDAY.value)) return SATURDAY;
		throw new IllegalArgumentException("Неизвестный тип  [" + value + "]");
	}

	public static Day valueFromFullName(String value)
	{
		if( value.equals("Sunday")) return SUNDAY;
		if( value.equals("Monday")) return MONDAY;
		if( value.equals("Tuesday")) return TUESDAY;
		if( value.equals("Wednesday")) return WEDNESDAY;
		if( value.equals("Thursday")) return THURSDAY;
		if( value.equals("Friday")) return FRIDAY;
		if( value.equals("Saturday")) return SATURDAY;
		throw new IllegalArgumentException("Неизвестный тип  [" + value + "]");
	}

}
