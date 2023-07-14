//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.06 at 04:05:33 PM MSK 
//


package com.rssl.phizic.business.test.mobile8.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentState">
 *   &lt;restriction base="{}string">
 *     &lt;enumeration value="INITIAL"/>
 *     &lt;enumeration value="DRAFT"/>
 *     &lt;enumeration value="SAVED"/>
 *     &lt;enumeration value="EXECUTED"/>
 *     &lt;enumeration value="REFUSED"/>
 *     &lt;enumeration value="RECALLED"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="DELAYED_DISPATCH"/>
 *     &lt;enumeration value="DISPATCHED"/>
 *     &lt;enumeration value="INITIAL"/>
 *     &lt;enumeration value="WAIT_CONFIRM"/>
 *     &lt;enumeration value="UNKNOW"/>
 *     &lt;enumeration value="SUCCESSED"/>
 *     &lt;enumeration value="PARTLY_EXECUTED"/>
 *     &lt;enumeration value="DRAFTTEMPLATE"/>
 *     &lt;enumeration value="SAVED_TEMPLATE"/>
 *     &lt;enumeration value="TEMPLATE"/>
 *     &lt;enumeration value="WAIT_CONFIRM_TEMPLATE"/>
 *     &lt;enumeration value="DELETED"/>
 *     &lt;enumeration value="STATEMENT_READY"/>
 *     &lt;enumeration value="ADOPTED"/>
 *     &lt;enumeration value="INITIAL_LONG_OFFER"/>
 *     &lt;enumeration value="OFFLINE_SAVED"/>
 *     &lt;enumeration value="OFFLINE_DELAYED"/>
 *     &lt;enumeration value="BILLING_CONFIRM_TIMEOUT"/>
 *     &lt;enumeration value="BILLING_GATE_CONFIRM_TIMEOUT"/>
 *     &lt;enumeration value="ABS_RECALL_TIMEOUT"/>
 *     &lt;enumeration value="ABS_GATE_RECALL_TIMEOUT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentState")
@XmlEnum
public enum PaymentStateDescriptor {

    INITIAL,
    DRAFT,
    SAVED,
    EXECUTED,
    REFUSED,
    RECALLED,
    ERROR,
    DELAYED_DISPATCH,
    DISPATCHED,
    WAIT_CONFIRM,
    UNKNOW,
    SUCCESSED,
    PARTLY_EXECUTED,
    DRAFTTEMPLATE,
    SAVED_TEMPLATE,
    TEMPLATE,
    WAIT_CONFIRM_TEMPLATE,
    DELETED,
    STATEMENT_READY,
    ADOPTED,
    INITIAL_LONG_OFFER,
    OFFLINE_SAVED,
    OFFLINE_DELAYED,
    BILLING_CONFIRM_TIMEOUT,
    BILLING_GATE_CONFIRM_TIMEOUT,
    ABS_RECALL_TIMEOUT,
    ABS_GATE_RECALL_TIMEOUT;

    public String value() {
        return name();
    }

    public static PaymentStateDescriptor fromValue(String v) {
        return valueOf(v);
    }

}
