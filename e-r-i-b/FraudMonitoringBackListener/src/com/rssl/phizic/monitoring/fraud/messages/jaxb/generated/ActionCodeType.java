
package com.rssl.phizic.monitoring.fraud.messages.jaxb.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActionCode_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActionCode_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALLOW"/>
 *     &lt;enumeration value="DENY"/>
 *     &lt;enumeration value="REVIEW"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActionCode_Type")
@XmlEnum
public enum ActionCodeType {

    ALLOW,
    DENY,
    REVIEW;

    public String value() {
        return name();
    }

    public static ActionCodeType fromValue(String v) {
        return valueOf(v);
    }

}
