
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TINType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TINType_Type">
 *   &lt;restriction base="{}OpenEnum">
 *     &lt;enumeration value="KIO"/>
 *     &lt;enumeration value="INN"/>
 *     &lt;enumeration value="EIN"/>
 *     &lt;enumeration value="SSN"/>
 *     &lt;enumeration value="NRABusiness"/>
 *     &lt;enumeration value="NRAPersonal"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TINType_Type")
@XmlEnum
public enum TINTypeType {

    KIO("KIO"),
    INN("INN"),
    EIN("EIN"),
    SSN("SSN"),
    @XmlEnumValue("NRABusiness")
    NRA_BUSINESS("NRABusiness"),
    @XmlEnumValue("NRAPersonal")
    NRA_PERSONAL("NRAPersonal");
    private final String value;

    TINTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TINTypeType fromValue(String v) {
        for (TINTypeType c: TINTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
