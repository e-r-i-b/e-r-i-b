
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangeStatusAction_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChangeStatusAction_Type">
 *   &lt;restriction base="{}String">
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
     * Приостановка подписки
     * 
     */
    PAUSE,

    /**
     * Возобновление подписки
     * 
     */
    UNPAUSE,

    /**
     * Закрытие подписки
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
