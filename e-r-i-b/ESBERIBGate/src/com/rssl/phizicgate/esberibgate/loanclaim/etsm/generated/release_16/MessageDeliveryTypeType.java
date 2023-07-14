
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessageDeliveryType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MessageDeliveryType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="I"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MessageDeliveryType_Type")
@XmlEnum
public enum MessageDeliveryTypeType {


    /**
     * e-mail
     * 
     */
    E,

    /**
     * no e-mail/mail
     * 
     */
    N,

    /**
     * sbrf e-mail
     * 
     */
    S,

    /**
     * mail
     * 
     */
    P,

    /**
     * e-mail/mail
     * 
     */
    I;

    public String value() {
        return name();
    }

    public static MessageDeliveryTypeType fromValue(String v) {
        return valueOf(v);
    }

}
