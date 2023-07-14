
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreatmentType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TreatmentType_Type">
 *   &lt;restriction base="{}C">
 *     &lt;maxLength value="30"/>
 *     &lt;enumeration value="SBOL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TreatmentType_Type")
@XmlEnum
public enum TreatmentTypeType {

    SBOL;

    public String value() {
        return name();
    }

    public static TreatmentTypeType fromValue(String v) {
        return valueOf(v);
    }

}
