
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserCategoryType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserCategoryType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Employee"/>
 *     &lt;enumeration value="TechnicalUser"/>
 *     &lt;enumeration value="Client"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserCategoryType")
@XmlEnum
public enum UserCategoryType {

    @XmlEnumValue("Employee")
    EMPLOYEE("Employee"),
    @XmlEnumValue("TechnicalUser")
    TECHNICAL_USER("TechnicalUser"),
    @XmlEnumValue("Client")
    CLIENT("Client");
    private final String value;

    UserCategoryType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserCategoryType fromValue(String v) {
        for (UserCategoryType c: UserCategoryType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
