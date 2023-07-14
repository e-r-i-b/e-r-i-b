
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExeEventCodeASAP_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExeEventCodeASAP_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="ONCE_IN_WEEK"/>
 *     &lt;enumeration value="ONCE_IN_MONTH"/>
 *     &lt;enumeration value="ONCE_IN_QUARTER"/>
 *     &lt;enumeration value="ON_REMAIND"/>
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
    ON_REMAIND;

    public String value() {
        return name();
    }

    public static ExeEventCodeASAPType fromValue(String v) {
        return valueOf(v);
    }

}
