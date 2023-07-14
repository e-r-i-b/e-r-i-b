
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransDirection_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransDirection_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TCC_OWN"/>
 *     &lt;enumeration value="TCC_P2P"/>
 *     &lt;enumeration value="TCC_P2P_FOREIGN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransDirection_Type")
@XmlEnum
public enum TransDirectionType {


    /**
     * перевод между своими картами
     * 
     */
    TCC_OWN("TCC_OWN"),

    /**
     * перевод стороннему лицу на карту СБ РФ внутри одного ТБ
     * 
     */
    @XmlEnumValue("TCC_P2P")
    TCC_P_2_P("TCC_P2P"),

    /**
     * перевод стороннему лицу на карту СБ РФ в другой ТБ
     * 
     */
    @XmlEnumValue("TCC_P2P_FOREIGN")
    TCC_P_2_P_FOREIGN("TCC_P2P_FOREIGN");
    private final String value;

    TransDirectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransDirectionType fromValue(String v) {
        for (TransDirectionType c: TransDirectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
