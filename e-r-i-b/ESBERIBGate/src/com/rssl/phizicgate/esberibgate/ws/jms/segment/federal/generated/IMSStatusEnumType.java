
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IMSStatusEnum_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IMSStatusEnum_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Opened"/>
 *     &lt;enumeration value="Closed"/>
 *     &lt;enumeration value="Lost-passbook"/>
 *     &lt;enumeration value="Arrested"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IMSStatusEnum_Type")
@XmlEnum
public enum IMSStatusEnumType {


    /**
     * Открыт
     * 
     */
    @XmlEnumValue("Opened")
    OPENED("Opened"),

    /**
     * Закрыт
     * 
     */
    @XmlEnumValue("Closed")
    CLOSED("Closed"),

    /**
     * Утеряна сберкнижка
     * 
     */
    @XmlEnumValue("Lost-passbook")
    LOST_PASSBOOK("Lost-passbook"),

    /**
     * Арестован
     * 
     */
    @XmlEnumValue("Arrested")
    ARRESTED("Arrested");
    private final String value;

    IMSStatusEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IMSStatusEnumType fromValue(String v) {
        for (IMSStatusEnumType c: IMSStatusEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
