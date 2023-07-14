package com.rssl.phizic.business.extendedattributes;

import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roshka
 * @ created 08.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedAttribute
{
	public static final String BOOLEAN_TYPE = "boolean";
	public static final String DATE_TYPE = "date";
	public static final String DATE_TIME_TYPE = "dateTime";
	public static final String DECIMAL_TYPE = "decimal";
	public static final String INTEGER_TYPE = "int";
	public static final String LONG_TYPE = "long";
	public static final String STRING_TYPE = "string";
	public static final String CALENDAR_TYPE = "сalendar";

	private Long id;
    private String name;
	private boolean isChanged;
	private String type;
	private Object internalValue;
	private Calendar dateCreated;
	private static final BigDecimalParser parser = new BigDecimalParser();
	private static final Map<String, Class> attributeTypesMap = new HashMap<String, Class>();

	//Типы атрибутов
	static
	{
		attributeTypesMap.put(BOOLEAN_TYPE, Boolean.class);
		attributeTypesMap.put(DATE_TYPE, Date.class);
		attributeTypesMap.put(DATE_TIME_TYPE.toLowerCase(), Date.class);
		attributeTypesMap.put(CALENDAR_TYPE, Calendar.class);
		attributeTypesMap.put(DECIMAL_TYPE, BigDecimal.class);
		attributeTypesMap.put("bigdecimal", BigDecimal.class);
		attributeTypesMap.put("integer", Integer.class);
		attributeTypesMap.put(INTEGER_TYPE, Integer.class);
		attributeTypesMap.put(LONG_TYPE, Long.class);
		attributeTypesMap.put(STRING_TYPE, String.class);
	}
	/**
	 * 4hibernate only
	 */
	protected ExtendedAttribute() {}

	/**
	 * @param name - имя атрибута
	 */
	public ExtendedAttribute(String type, String name)
	{
		if (attributeTypesMap.get(type.toLowerCase()) == null)
			throw new RuntimeException("Unknown attribute type - " + type);
		this.type = type;
		this.name = name;
	}

	public ExtendedAttribute(Long id, String name, String type, Object internalValue)
	{
		this(type, name);
		this.id            = id;
		this.internalValue = internalValue;
	}

	public boolean getIsChanged()
	{
		return isChanged;
	}

	public void setIsChanged(boolean changed)
	{
		isChanged = changed;
	}

	/**
     * 4hibernate
     */
    @SuppressWarnings({"UNUSED_SYMBOL"})
    private Long getId()
    {
        return id;
    }

    /**
     * 4hibernate
     */
    @SuppressWarnings({"UNUSED_SYMBOL"})
    private void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
	    if (internalValue == null)
		    return type;

	    if (internalValue instanceof Boolean)
		    return BOOLEAN_TYPE;
	    else if (internalValue instanceof Date)
		    return DATE_TYPE;
	    else if (internalValue instanceof Calendar)
		    return DATE_TIME_TYPE;
	    else if (internalValue instanceof BigDecimal)
		    return DECIMAL_TYPE;
	    else if (internalValue instanceof Integer)
		    return INTEGER_TYPE;
	    else if (internalValue instanceof Long)
		    return LONG_TYPE;
	    else if (internalValue instanceof String)
		    return STRING_TYPE;
	    else
		    return null;
    };

    private void setType(String type)
    {
	    this.type = type;
    }

    public void setValue(Object value)
    {
	    this.internalValue = value;
    }

	public void setValueByType(String type, String str)
	{
		this.type = type;
		this.internalValue = parse(type, str);
	}

    public Object getValue()
    {
        return internalValue;
    }

    public String getStringValue()
    {
        return format(internalValue);
    }

    public void setStringValue(String str)
    {
        internalValue = parse(type, str);
    }

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	/**
	 * @return never null (BUG086463: Просмотр истории операций и документа порождает апдейты хвостов.)
	 */
	private String format(Object value)
    {
	    if (value == null)
		    return "";

	    if ( value instanceof Boolean ||
			 value instanceof BigDecimal ||
             value instanceof Integer ||
             value instanceof Long )
            return value.toString();
        else if (value instanceof Date)
            return DateHelper.toXMLDateFormat((Date) value);
        else if (value instanceof Calendar)
            return XMLDatatypeHelper.formatDateTime((Calendar) value);
	    else if (value instanceof String)
            return (String) value;
        else
            return "";
    };

	/**
	 * @return never null (BUG086463: Просмотр истории операций и документа порождает апдейты хвостов.)
	 */
    private Object parse(String type, String strValue)
    {
	    try
        {
		    if (StringHelper.isEmpty(type))
			    return "";

	        //Тип объекта по строковому названию
	        Class clazz = attributeTypesMap.get(type.toLowerCase());
	        if (clazz == null)
		        return "";
		    if (clazz.equals(Boolean.class))
			    return Boolean.parseBoolean(strValue);
	        else if (clazz.equals(Date.class))
			    return StringHelper.isEmpty(strValue) ? null : DateHelper.fromXMlDateToDate(strValue);
	        else if (clazz.equals(Calendar.class))
			    return StringHelper.isEmpty(strValue) ? null : XMLDatatypeHelper.parseDateTime(strValue);
	        else if (clazz.equals(BigDecimal.class))
			    return parser.parse(strValue);
	        else if (clazz.equals(Integer.class))
			    return StringHelper.isEmpty(strValue) ? null : Integer.parseInt(strValue);
	        else if (clazz.equals(Long.class))
			    return StringHelper.isEmpty(strValue) ? null : Long.parseLong(strValue);
	        else //иначе расцениваем как строку
			    return strValue;
	        }
	    catch (ParseException e)
	    {
		    throw new RuntimeException(e);
	    }
    };

	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
}
