
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TSMConture_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TSMConture_Type">
 *   &lt;restriction base="{}C">
 *     &lt;maxLength value="1"/>
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="E"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TSMConture_Type")
@XmlEnum
public enum TSMContureType {

    R,
    E;

    public String value() {
        return name();
    }

    public static TSMContureType fromValue(String v) {
        return valueOf(v);
    }

}
