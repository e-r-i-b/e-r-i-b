
package com.rssl.phizicgate.mdm.integration.mdm.generated;

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
 *     &lt;enumeration value="urn:sbrfsystems:99-erib"/>
 *     &lt;enumeration value="MDM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SPName_Type")
@XmlEnum
public enum SPNameType {


    /**
     * ¿— ≈–»¡
     * 
     */
    @XmlEnumValue("urn:sbrfsystems:99-erib")
    URN_SBRFSYSTEMS_99_ERIB("urn:sbrfsystems:99-erib"),

    /**
     * ¿— ÃƒÃ
     * 
     */
    MDM("MDM");
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
