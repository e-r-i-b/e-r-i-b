
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PermissType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PermissType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="View"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PermissType_Type")
@XmlEnum
public enum PermissTypeType {


    /**
     * ѕросмотр (при отсутствии этого права дл€ сущности, она не должна отображатьс€ в системе)
     * 
     */
    @XmlEnumValue("View")
    VIEW("View");
    private final String value;

    PermissTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PermissTypeType fromValue(String v) {
        for (PermissTypeType c: PermissTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
