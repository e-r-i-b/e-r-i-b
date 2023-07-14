
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountStatusEnum_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountStatusEnum_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="Opened"/>
 *     &lt;enumeration value="Closed"/>
 *     &lt;enumeration value="Arrested"/>
 *     &lt;enumeration value="Lost-passbook"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountStatusEnum_Type")
@XmlEnum
public enum AccountStatusEnumType {


    /**
     * Открыт
     * 
     */
    @XmlEnumValue("Opened")
    OPENED("Opened"),

    /**
     * Закрыт
     * 
     */
    @XmlEnumValue("Closed")
    CLOSED("Closed"),

    /**
     * Арестован
     * 
     */
    @XmlEnumValue("Arrested")
    ARRESTED("Arrested"),

    /**
     * Утеряна сберкнижка
     * 
     */
    @XmlEnumValue("Lost-passbook")
    LOST_PASSBOOK("Lost-passbook");
    private final String value;

    AccountStatusEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccountStatusEnumType fromValue(String v) {
        for (AccountStatusEnumType c: AccountStatusEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
