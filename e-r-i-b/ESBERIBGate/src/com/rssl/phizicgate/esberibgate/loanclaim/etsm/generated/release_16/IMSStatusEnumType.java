
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

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
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="Opened"/>
 *     &lt;enumeration value="Closed"/>
 *     &lt;enumeration value="Lost-passbook"/>
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
    LOST_PASSBOOK("Lost-passbook");
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
