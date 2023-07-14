
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WeekDay_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WeekDay_Type">
 *   &lt;restriction base="{}C">
 *     &lt;enumeration value="Monday"/>
 *     &lt;enumeration value="Tuesday"/>
 *     &lt;enumeration value="Wednesday"/>
 *     &lt;enumeration value="Thursday"/>
 *     &lt;enumeration value="Friday"/>
 *     &lt;enumeration value="Saturday"/>
 *     &lt;enumeration value="Sunday"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WeekDay_Type")
@XmlEnum
public enum WeekDayType {


    /**
     * Понедельник
     * 
     */
    @XmlEnumValue("Monday")
    MONDAY("Monday"),

    /**
     * Вторник
     * 
     */
    @XmlEnumValue("Tuesday")
    TUESDAY("Tuesday"),

    /**
     * Среда
     * 
     */
    @XmlEnumValue("Wednesday")
    WEDNESDAY("Wednesday"),

    /**
     * Четверг
     * 
     */
    @XmlEnumValue("Thursday")
    THURSDAY("Thursday"),

    /**
     * Пятница
     * 
     */
    @XmlEnumValue("Friday")
    FRIDAY("Friday"),

    /**
     * Суббота
     * 
     */
    @XmlEnumValue("Saturday")
    SATURDAY("Saturday"),

    /**
     * Воскресенье
     * 
     */
    @XmlEnumValue("Sunday")
    SUNDAY("Sunday");
    private final String value;

    WeekDayType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WeekDayType fromValue(String v) {
        for (WeekDayType c: WeekDayType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
