
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IMAOperCurType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IMAOperCurType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="CS_MONEY"/>
 *     &lt;enumeration value="CS_METAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IMAOperCurType_Type")
@XmlEnum
public enum IMAOperCurTypeType {


    /**
     * Денежные средства
     * 
     */
    CS_MONEY,

    /**
     * Слиток
     * 
     */
    CS_METAL;

    public String value() {
        return name();
    }

    public static IMAOperCurTypeType fromValue(String v) {
        return valueOf(v);
    }

}
