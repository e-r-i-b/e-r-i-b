
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BonusProgram_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BonusProgram_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="BPT_AE"/>
 *     &lt;enumeration value="BPT_PG"/>
 *     &lt;enumeration value="BPT_GM"/>
 *     &lt;enumeration value="BPT_OB"/>
 *     &lt;enumeration value="BPT_MT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BonusProgram_Type")
@XmlEnum
public enum BonusProgramType {


    /**
     * Аэрофлот-бонус
     * 
     */
    BPT_AE,

    /**
     * Подари Жизнь
     * 
     */
    BPT_PG,

    /**
     * Золотая Маска
     * 
     */
    BPT_GM,

    /**
     * Олимпийская
     * 
     */
    BPT_OB,

    /**
     * МТС
     * 
     */
    BPT_MT;

    public String value() {
        return name();
    }

    public static BonusProgramType fromValue(String v) {
        return valueOf(v);
    }

}
