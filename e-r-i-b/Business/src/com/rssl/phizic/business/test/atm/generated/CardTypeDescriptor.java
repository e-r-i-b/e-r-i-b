//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.26 at 01:33:00 PM MSD 
//


package com.rssl.phizic.business.test.atm.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CardType">
 *   &lt;restriction base="{}string">
 *     &lt;enumeration value="debit"/>
 *     &lt;enumeration value="credit"/>
 *     &lt;enumeration value="overdraft"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CardType")
@XmlEnum
public enum CardTypeDescriptor {

    @XmlEnumValue("debit")
    DEBIT("debit"),
    @XmlEnumValue("credit")
    CREDIT("credit"),
    @XmlEnumValue("overdraft")
    OVERDRAFT("overdraft");
    private final String value;

    CardTypeDescriptor(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CardTypeDescriptor fromValue(String v) {
        for (CardTypeDescriptor c: CardTypeDescriptor.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
