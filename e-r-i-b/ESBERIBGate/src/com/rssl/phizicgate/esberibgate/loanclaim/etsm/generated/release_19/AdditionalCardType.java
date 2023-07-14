
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdditionalCard_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdditionalCard_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="Client2Client"/>
 *     &lt;enumeration value="Client2Other"/>
 *     &lt;enumeration value="Other2Client"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdditionalCard_Type")
@XmlEnum
public enum AdditionalCardType {


    /**
     * Карта, выпущенная на имя клиента и к его собственному счету
     * 
     */
    @XmlEnumValue("Client2Client")
    CLIENT_2_CLIENT("Client2Client"),

    /**
     * Карта, выпущенная к счету клиента на имя другого лица
     * 
     */
    @XmlEnumValue("Client2Other")
    CLIENT_2_OTHER("Client2Other"),

    /**
     * Карта на имя клиента, выпущенная к счету другого лица
     * 
     */
    @XmlEnumValue("Other2Client")
    OTHER_2_CLIENT("Other2Client");
    private final String value;

    AdditionalCardType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdditionalCardType fromValue(String v) {
        for (AdditionalCardType c: AdditionalCardType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
