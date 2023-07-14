
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for State_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="State_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NO_CONTRACT"/>
 *     &lt;enumeration value="ACTIVE_CONTRACT"/>
 *     &lt;enumeration value="CLOSED_CONTRACT"/>
 *     &lt;enumeration value="NOT_SIGNED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "State_Type")
@XmlEnum
public enum StateType {


    /**
     * Договор не заключен
     * 
     */
    NO_CONTRACT,

    /**
     * Договор заключен
     * 
     */
    ACTIVE_CONTRACT,

    /**
     * Закрыт договор
     * 
     */
    CLOSED_CONTRACT,

    /**
     * Неверефицирован
     * 
     */
    NOT_SIGNED;

    public String value() {
        return name();
    }

    public static StateType fromValue(String v) {
        return valueOf(v);
    }

}
