
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentStatusASAP_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentStatusASAP_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="New"/>
 *     &lt;enumeration value="Canceled"/>
 *     &lt;enumeration value="Done"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentStatusASAP_Type")
@XmlEnum
public enum PaymentStatusASAPType {


    /**
     * Новый
     * 
     */
    @XmlEnumValue("New")
    NEW("New"),

    /**
     * Отменён
     * 
     */
    @XmlEnumValue("Canceled")
    CANCELED("Canceled"),

    /**
     * Исполнен
     * 
     */
    @XmlEnumValue("Done")
    DONE("Done");
    private final String value;

    PaymentStatusASAPType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentStatusASAPType fromValue(String v) {
        for (PaymentStatusASAPType c: PaymentStatusASAPType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
