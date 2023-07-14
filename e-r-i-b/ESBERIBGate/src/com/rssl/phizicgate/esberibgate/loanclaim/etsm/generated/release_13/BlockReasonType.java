
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BlockReasonType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BlockReasonType">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="LOST"/>
 *     &lt;enumeration value="STOLEN"/>
 *     &lt;enumeration value="HOLDER"/>
 *     &lt;enumeration value="ATM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BlockReasonType")
@XmlEnum
public enum BlockReasonType {


    /**
     * Карта потеряна
     * 
     */
    LOST,

    /**
     * Карта украдена
     * 
     */
    STOLEN,

    /**
     * По инициативе держателя
     * 
     */
    HOLDER,

    /**
     * Изъята банкоматом
     * 
     */
    ATM;

    public String value() {
        return name();
    }

    public static BlockReasonType fromValue(String v) {
        return valueOf(v);
    }

}
