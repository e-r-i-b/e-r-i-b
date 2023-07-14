
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportOrderType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportOrderType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="IR"/>
 *     &lt;enumeration value="ER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReportOrderType_Type")
@XmlEnum
public enum ReportOrderTypeType {

    IR,
    ER;

    public String value() {
        return name();
    }

    public static ReportOrderTypeType fromValue(String v) {
        return valueOf(v);
    }

}
