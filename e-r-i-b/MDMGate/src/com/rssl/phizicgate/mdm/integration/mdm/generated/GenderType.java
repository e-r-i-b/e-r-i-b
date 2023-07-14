
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Gender_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Gender_Type">
 *   &lt;restriction base="{}C">
 *     &lt;maxLength value="20"/>
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="U"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Gender_Type")
@XmlEnum
public enum GenderType {


    /**
     * мужской
     * 
     */
    M,

    /**
     * женский
     * 
     */
    F,

    /**
     * не определен
     * 
     */
    U;

    public String value() {
        return name();
    }

    public static GenderType fromValue(String v) {
        return valueOf(v);
    }

}
