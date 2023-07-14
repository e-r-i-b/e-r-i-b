
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Channel_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Channel_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="IB"/>
 *     &lt;enumeration value="VSP"/>
 *     &lt;enumeration value="US"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Channel_Type")
@XmlEnum
public enum ChannelType {


    /**
     * Интернет-банк
     * 
     */
    IB,

    /**
     * ВСП (филиал)
     * 
     */
    VSP,

    /**
     * Устройство самообслуживания
     * 
     */
    US;

    public String value() {
        return name();
    }

    public static ChannelType fromValue(String v) {
        return valueOf(v);
    }

}
