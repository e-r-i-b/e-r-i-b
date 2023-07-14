
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AcctType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AcctType_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Deposit"/>
 *     &lt;enumeration value="IMA"/>
 *     &lt;enumeration value="Card"/>
 *     &lt;enumeration value="Credit"/>
 *     &lt;enumeration value="LongOrd"/>
 *     &lt;enumeration value="DepoAcc"/>
 *     &lt;enumeration value="CardWay"/>
 *     &lt;enumeration value="Securities"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AcctType_Type")
@XmlEnum
public enum AcctTypeType {


    /**
     * ������
     * 
     */
    @XmlEnumValue("Deposit")
    DEPOSIT("Deposit"),

    /**
     * ���
     * 
     */
    IMA("IMA"),

    /**
     * 
     * 						����������� �����
     * 					
     * 
     */
    @XmlEnumValue("Card")
    CARD("Card"),

    /**
     * �������
     * 
     */
    @XmlEnumValue("Credit")
    CREDIT("Credit"),

    /**
     * 
     * 						���������� ���������
     * 					
     * 
     */
    @XmlEnumValue("LongOrd")
    LONG_ORD("LongOrd"),

    /**
     * C���� ����
     * 
     */
    @XmlEnumValue("DepoAcc")
    DEPO_ACC("DepoAcc"),
    @XmlEnumValue("CardWay")
    CARD_WAY("CardWay"),
    @XmlEnumValue("Securities")
    SECURITIES("Securities");
    private final String value;

    AcctTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AcctTypeType fromValue(String v) {
        for (AcctTypeType c: AcctTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
