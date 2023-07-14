
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportDeliveryType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportDeliveryType_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="C"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReportDeliveryType_Type")
@XmlEnum
public enum ReportDeliveryTypeType {


    /**
     * PDF
     * 
     */
    P,

    /**
     * HTML
     * 
     */
    H,
    C;

    public String value() {
        return name();
    }

    public static ReportDeliveryTypeType fromValue(String v) {
        return valueOf(v);
    }

}
