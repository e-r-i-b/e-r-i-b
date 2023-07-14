
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LevelSale_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LevelSale_Type">
 *   &lt;restriction base="{}C">
 *     &lt;maxLength value="20"/>
 *     &lt;enumeration value="ZKSB"/>
 *     &lt;enumeration value="SKSB"/>
 *     &lt;enumeration value="LKSB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LevelSale_Type")
@XmlEnum
public enum LevelSaleType {


    /**
     * зарплатные карты
     * 
     */
    ZKSB,

    /**
     * соц карты
     * 
     */
    SKSB,

    /**
     * личные карты
     * 
     */
    LKSB;

    public String value() {
        return name();
    }

    public static LevelSaleType fromValue(String v) {
        return valueOf(v);
    }

}
