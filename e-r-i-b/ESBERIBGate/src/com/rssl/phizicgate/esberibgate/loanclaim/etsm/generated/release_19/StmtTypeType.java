
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StmtType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StmtType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="Short10"/>
 *     &lt;enumeration value="Full"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StmtType_Type")
@XmlEnum
public enum StmtTypeType {


    /**
     * Короткая выписка, 10 последних операций
     * 
     */
    @XmlEnumValue("Short10")
    SHORT_10("Short10"),

    /**
     * Полная выписка за период
     * 
     */
    @XmlEnumValue("Full")
    FULL("Full");
    private final String value;

    StmtTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StmtTypeType fromValue(String v) {
        for (StmtTypeType c: StmtTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
