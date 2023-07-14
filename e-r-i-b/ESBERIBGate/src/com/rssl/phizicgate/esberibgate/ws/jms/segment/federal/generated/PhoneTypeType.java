
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhoneType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PhoneType_Type">
 *   &lt;restriction base="{}C">
 *     &lt;maxLength value="20"/>
 *     &lt;enumeration value="Mobile"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PhoneType_Type")
@XmlEnum
public enum PhoneTypeType {


    /**
     * Мобильный телефон
     * 
     */
    @XmlEnumValue("Mobile")
    MOBILE("Mobile");
    private final String value;

    PhoneTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PhoneTypeType fromValue(String v) {
        for (PhoneTypeType c: PhoneTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
