
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SummaKindCodeASAP_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SummaKindCodeASAP_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="FIXED_SUMMA_IN_RECIP_CURR"/>
 *     &lt;enumeration value="BY_BILLING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SummaKindCodeASAP_Type")
@XmlEnum
public enum SummaKindCodeASAPType {


    /**
     * Фиксированная сумма
     * 
     */
    FIXED_SUMMA_IN_RECIP_CURR,

    /**
     * По выставленному счету
     * 
     */
    BY_BILLING;

    public String value() {
        return name();
    }

    public static SummaKindCodeASAPType fromValue(String v) {
        return valueOf(v);
    }

}
