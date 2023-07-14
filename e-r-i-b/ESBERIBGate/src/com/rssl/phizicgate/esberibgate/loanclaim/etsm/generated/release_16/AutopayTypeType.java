
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AutopayType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AutopayType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="A"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AutopayType_Type")
@XmlEnum
public enum AutopayTypeType {


    /**
     * Регулярный автоплатеж
     * 
     */
    R,

    /**
     * Пороговый автоплатеж
     * 
     */
    P,

    /**
     * Автоплатеж по счету
     * 
     */
    A;

    public String value() {
        return name();
    }

    public static AutopayTypeType fromValue(String v) {
        return valueOf(v);
    }

}
