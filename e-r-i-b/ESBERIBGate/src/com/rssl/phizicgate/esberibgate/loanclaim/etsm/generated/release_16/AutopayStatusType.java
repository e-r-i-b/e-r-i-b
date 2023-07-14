
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AutopayStatus_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AutopayStatus_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="New"/>
 *     &lt;enumeration value="OnRegistration"/>
 *     &lt;enumeration value="Confirmed"/>
 *     &lt;enumeration value="Active"/>
 *     &lt;enumeration value="WaitForAccept"/>
 *     &lt;enumeration value="ErrorRegistration"/>
 *     &lt;enumeration value="Paused"/>
 *     &lt;enumeration value="Closed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AutopayStatus_Type")
@XmlEnum
public enum AutopayStatusType {


    /**
     * Новая
     * 
     */
    @XmlEnumValue("New")
    NEW("New"),

    /**
     * Подписка на регистрации у ПУ
     * 
     */
    @XmlEnumValue("OnRegistration")
    ON_REGISTRATION("OnRegistration"),

    /**
     * Прошла регистрацию у поставщика услуг
     * 
     */
    @XmlEnumValue("Confirmed")
    CONFIRMED("Confirmed"),

    /**
     * Активна и готова к исполнению
     * 
     */
    @XmlEnumValue("Active")
    ACTIVE("Active"),

    /**
     * Ожидание подтверждения клиентом
     * 
     */
    @XmlEnumValue("WaitForAccept")
    WAIT_FOR_ACCEPT("WaitForAccept"),

    /**
     * Не зарегистрирована
     * 
     */
    @XmlEnumValue("ErrorRegistration")
    ERROR_REGISTRATION("ErrorRegistration"),

    /**
     * Приостановлена
     * 
     */
    @XmlEnumValue("Paused")
    PAUSED("Paused"),

    /**
     * Закрыта
     * 
     */
    @XmlEnumValue("Closed")
    CLOSED("Closed");
    private final String value;

    AutopayStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutopayStatusType fromValue(String v) {
        for (AutopayStatusType c: AutopayStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
