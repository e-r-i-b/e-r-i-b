
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangeStatusAction_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChangeStatusAction_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PAUSE"/>
 *     &lt;enumeration value="UNPAUSE"/>
 *     &lt;enumeration value="CLOSE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChangeStatusAction_Type")
@XmlEnum
public enum ChangeStatusActionType {


    /**
     * ������������ ��������
     * 
     */
    PAUSE,

    /**
     * ������������� ��������
     * 
     */
    UNPAUSE,

    /**
     * �������� ��������
     * 
     */
    CLOSE;

    public String value() {
        return name();
    }

    public static ChangeStatusActionType fromValue(String v) {
        return valueOf(v);
    }

}
