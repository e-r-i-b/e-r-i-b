
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperName_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperName_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TCC"/>
 *     &lt;enumeration value="TCD"/>
 *     &lt;enumeration value="TDC"/>
 *     &lt;enumeration value="TDD"/>
 *     &lt;enumeration value="TCP"/>
 *     &lt;enumeration value="SDP"/>
 *     &lt;enumeration value="CreateForm190ForLoanRequest"/>
 *     &lt;enumeration value="TDDC"/>
 *     &lt;enumeration value="TDCC"/>
 *     &lt;enumeration value="TDI"/>
 *     &lt;enumeration value="TID"/>
 *     &lt;enumeration value="TDIO"/>
 *     &lt;enumeration value="TCIO"/>
 *     &lt;enumeration value="TCI"/>
 *     &lt;enumeration value="TIC"/>
 *     &lt;enumeration value="TDDO"/>
 *     &lt;enumeration value="TCDO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperName_Type")
@XmlEnum
public enum OperNameType {


    /**
     * Погашение кредита с карты
     * 
     */
    TCC("TCC"),

    /**
     * Перевод с карты на вклад
     * 
     */
    TCD("TCD"),

    /**
     * Перевод со вклада на карту
     * 
     */
    TDC("TDC"),

    /**
     * Перевод со вклада на вклад
     * 
     */
    TDD("TDD"),

    /**
     * Оплата товаров и услуг
     * 
     */
    TCP("TCP"),

    /**
     * Создание длительного поручения на перевод денежных средств с вклада физическому лицу
     * 
     */
    SDP("SDP"),

    /**
     * Создание длительного поручения на погашение кредита с вкладного счета
     * 
     */
    @XmlEnumValue("CreateForm190ForLoanRequest")
    CREATE_FORM_190_FOR_LOAN_REQUEST("CreateForm190ForLoanRequest"),

    /**
     * Закрытие вклада с переводом денежных средств со счета закрываемого вклада на другой счет по вкладу
     * 
     */
    TDDC("TDDC"),

    /**
     * Закрытие вклада с переводом денежных средств со счета закрываемого вклада на счет МБК
     * 
     */
    TDCC("TDCC"),

    /**
     * Перевод с вклада на ОМС (тоже самое что и TID, и ТDD)
     * 
     */
    TDI("TDI"),

    /**
     * Перевод с ОМС на вклад (тоже самое что и TDI, и ТDD)
     * 
     */
    TID("TID"),

    /**
     * Открытие ОМС переводом с вклада
     * 
     */
    TDIO("TDIO"),

    /**
     * Открытие ОМС переводом с карты
     * 
     */
    TCIO("TCIO"),

    /**
     * Перевод с карты на ОМС (покупка металла)
     * 
     */
    TCI("TCI"),

    /**
     * Перевод с ОМС на карту (продажа металла)
     * 
     */
    TIC("TIC"),

    /**
     * Открытие вклада переводом с другого вклада
     * 
     */
    TDDO("TDDO"),

    /**
     * Открытие вклада переводом с карты
     * 
     */
    TCDO("TCDO");
    private final String value;

    OperNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperNameType fromValue(String v) {
        for (OperNameType c: OperNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
