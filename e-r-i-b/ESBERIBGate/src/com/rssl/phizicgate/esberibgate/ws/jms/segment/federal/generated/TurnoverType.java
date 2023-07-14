
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Turnover_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Turnover_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHARGE"/>
 *     &lt;enumeration value="RECEIPT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Turnover_Type")
@XmlEnum
public enum TurnoverType {


    /**
     * Списание
     * 
     */
    CHARGE,

    /**
     * Зачисление
     * 
     */
    RECEIPT;

    public String value() {
        return name();
    }

    public static TurnoverType fromValue(String v) {
        return valueOf(v);
    }

}
