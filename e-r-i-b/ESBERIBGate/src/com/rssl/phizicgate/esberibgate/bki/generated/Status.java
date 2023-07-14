
package com.rssl.phizicgate.esberibgate.bki.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for status.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="status">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NotVerified"/>
 *     &lt;enumeration value="Valid"/>
 *     &lt;enumeration value="NotValid"/>
 *     &lt;enumeration value="Found"/>
 *     &lt;enumeration value="NotFound"/>
 *     &lt;enumeration value="Mismatch"/>
 *     &lt;enumeration value="PartialMismatch"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "status")
@XmlEnum
public enum Status {

    @XmlEnumValue("NotVerified")
    NOT_VERIFIED("NotVerified"),
    @XmlEnumValue("Valid")
    VALID("Valid"),
    @XmlEnumValue("NotValid")
    NOT_VALID("NotValid"),
    @XmlEnumValue("Found")
    FOUND("Found"),
    @XmlEnumValue("NotFound")
    NOT_FOUND("NotFound"),
    @XmlEnumValue("Mismatch")
    MISMATCH("Mismatch"),
    @XmlEnumValue("PartialMismatch")
    PARTIAL_MISMATCH("PartialMismatch");
    private final String value;

    Status(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Status fromValue(String v) {
        for (Status c: Status.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
