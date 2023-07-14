
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceTarif_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceTarif_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STT_BANK_TARIF"/>
 *     &lt;enumeration value="STT_INDIVIDUAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ServiceTarif_Type")
@XmlEnum
public enum ServiceTarifType {


    /**
     * Тариф банка
     * 
     */
    STT_BANK_TARIF,

    /**
     * Индивидуальный
     * 
     */
    STT_INDIVIDUAL;

    public String value() {
        return name();
    }

    public static ServiceTarifType fromValue(String v) {
        return valueOf(v);
    }

}
