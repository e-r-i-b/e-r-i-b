
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExeEventCodeASAP_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExeEventCodeASAP_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ONCE_IN_WEEK"/>
 *     &lt;enumeration value="ONCE_IN_MONTH"/>
 *     &lt;enumeration value="ONCE_IN_QUARTER"/>
 *     &lt;enumeration value="ON_REMAIND"/>
 *     &lt;enumeration value="ONCE_IN_YEAR"/>
 *     &lt;enumeration value="BY_ANY_RECEIPT"/>
 *     &lt;enumeration value="BY_DEBIT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExeEventCodeASAP_Type")
@XmlEnum
public enum ExeEventCodeASAPType {


    /**
     * Раз в неделю
     * 
     */
    ONCE_IN_WEEK,

    /**
     * Раз в месяц
     * 
     */
    ONCE_IN_MONTH,

    /**
     * Раз в квартал
     * 
     */
    ONCE_IN_QUARTER,

    /**
     * По остатку на счете
     * 
     */
    ON_REMAIND,
    ONCE_IN_YEAR,
    BY_ANY_RECEIPT,
    BY_DEBIT;

    public String value() {
        return name();
    }

    public static ExeEventCodeASAPType fromValue(String v) {
        return valueOf(v);
    }

}
