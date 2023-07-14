
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SPName_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SPName_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BP_ES"/>
 *     &lt;enumeration value="BP_VSP"/>
 *     &lt;enumeration value="BP_ERIB"/>
 *     &lt;enumeration value="BP_COD"/>
 *     &lt;enumeration value="BP_ESK"/>
 *     &lt;enumeration value="BP_IASK"/>
 *     &lt;enumeration value="BP_WAY"/>
 *     &lt;enumeration value="BP_BS"/>
 *     &lt;enumeration value="BP_INFOBANK"/>
 *     &lt;enumeration value="urn:sbrfsystems:99-autopay"/>
 *     &lt;enumeration value="urn:sbrfsystems:99-crm"/>
 *     &lt;enumeration value="urn:sbrfsystem:99-way"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SPName_Type")
@XmlEnum
public enum SPNameType {


    /**
     * Устройство самообслуживания (СмартВиста)
     * 
     */
    BP_ES("BP_ES"),

    /**
     * АС ВСП
     * 
     */
    BP_VSP("BP_VSP"),

    /**
     * АС ЕРИБ для Базового продукта
     * 
     */
    BP_ERIB("BP_ERIB"),

    /**
     * АС ЦОД
     * 
     */
    BP_COD("BP_COD"),

    /**
     * АС ЭСК
     * 
     */
    BP_ESK("BP_ESK"),

    /**
     * АС ИАСК
     * 
     */
    BP_IASK("BP_IASK"),

    /**
     * АС WAY4U
     * 
     */
    BP_WAY("BP_WAY"),

    /**
     * АС БС
     * 
     */
    BP_BS("BP_BS"),
    BP_INFOBANK("BP_INFOBANK"),
    @XmlEnumValue("urn:sbrfsystems:99-autopay")
    URN_SBRFSYSTEMS_99_AUTOPAY("urn:sbrfsystems:99-autopay"),
    @XmlEnumValue("urn:sbrfsystems:99-crm")
    URN_SBRFSYSTEMS_99_CRM("urn:sbrfsystems:99-crm"),
    @XmlEnumValue("urn:sbrfsystem:99-way")
    URN_SBRFSYSTEM_99_WAY("urn:sbrfsystem:99-way");
    private final String value;

    SPNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SPNameType fromValue(String v) {
        for (SPNameType c: SPNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
