
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActionName_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActionName_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VariantInterestPayment"/>
 *     &lt;enumeration value="AlterMinRemainder"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActionName_Type")
@XmlEnum
public enum ActionNameType {


    /**
     * Изменения варианта уплаты процентов
     * 
     */
    @XmlEnumValue("VariantInterestPayment")
    VARIANT_INTEREST_PAYMENT("VariantInterestPayment"),

    /**
     * Изменения неснижаемого остатка
     * 
     */
    @XmlEnumValue("AlterMinRemainder")
    ALTER_MIN_REMAINDER("AlterMinRemainder");
    private final String value;

    ActionNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActionNameType fromValue(String v) {
        for (ActionNameType c: ActionNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
