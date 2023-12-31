
package com.rssl.phizic.synchronization.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ResourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="account"/>
 *     &lt;enumeration value="card"/>
 *     &lt;enumeration value="loan"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ResourceType")
@XmlEnum
public enum ResourceType {

    @XmlEnumValue("account")
    ACCOUNT("account"),
    @XmlEnumValue("card")
    CARD("card"),
    @XmlEnumValue("loan")
    LOAN("loan");
    private final String value;

    ResourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResourceType fromValue(String v) {
        for (ResourceType c: ResourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
