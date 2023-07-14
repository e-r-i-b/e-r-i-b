
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Channel_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Channel_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IB"/>
 *     &lt;enumeration value="VSP"/>
 *     &lt;enumeration value="US"/>
 *     &lt;enumeration value="SMSSender"/>
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
    IB("IB"),

    /**
     * ВСП (филиал)
     * 
     */
    VSP("VSP"),

    /**
     * Устройство самообслуживания
     * 
     */
    US("US"),

    /**
     * Автоматически
     * 
     */
    @XmlEnumValue("SMSSender")
    SMS_SENDER("SMSSender");
    private final String value;

    ChannelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChannelType fromValue(String v) {
        for (ChannelType c: ChannelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
