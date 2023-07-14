
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportLangType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportLangType_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RUS"/>
 *     &lt;enumeration value="ENG"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReportLangType_Type")
@XmlEnum
public enum ReportLangTypeType {


    /**
     * русский
     * 
     */
    RUS,

    /**
     * английский
     * 
     */
    ENG;

    public String value() {
        return name();
    }

    public static ReportLangTypeType fromValue(String v) {
        return valueOf(v);
    }

}
