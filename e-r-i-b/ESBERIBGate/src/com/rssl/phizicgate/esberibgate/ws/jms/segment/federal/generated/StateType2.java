
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StateType">
 *   &lt;restriction base="{}C">
 *     &lt;length value="10"/>
 *     &lt;enumeration value="DISPATCHED"/>
 *     &lt;enumeration value="SUCCESSED"/>
 *     &lt;enumeration value="EXECUTED"/>
 *     &lt;enumeration value="REFUSED"/>
 *     &lt;enumeration value="RECALLED"/>
 *     &lt;enumeration value="CANCELED"/>
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StateType")
@XmlEnum
public enum StateType2 {

    DISPATCHED,
    SUCCESSED,
    EXECUTED,
    REFUSED,
    RECALLED,
    CANCELED,
    ERROR;

    public String value() {
        return name();
    }

    public static StateType2 fromValue(String v) {
        return valueOf(v);
    }

}
