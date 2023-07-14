
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountStateAction_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountStateAction_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="STATE_SAVE_BOOK_LOST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountStateAction_Type")
@XmlEnum
public enum AccountStateActionType {


    /**
     * Признак утери сберкнижки по счету
     * 
     */
    STATE_SAVE_BOOK_LOST;

    public String value() {
        return name();
    }

    public static AccountStateActionType fromValue(String v) {
        return valueOf(v);
    }

}
