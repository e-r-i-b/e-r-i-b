
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantGroupCode_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApplicantGroupCode_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;maxLength value="2"/>
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="A1"/>
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="B1"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="E"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApplicantGroupCode_Type")
@XmlEnum
public enum ApplicantGroupCodeType {

    A("A"),
    @XmlEnumValue("A1")
    A_1("A1"),
    B("B"),
    @XmlEnumValue("B1")
    B_1("B1"),
    C("C"),
    D("D"),
    E("E");
    private final String value;

    ApplicantGroupCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApplicantGroupCodeType fromValue(String v) {
        for (ApplicantGroupCodeType c: ApplicantGroupCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
