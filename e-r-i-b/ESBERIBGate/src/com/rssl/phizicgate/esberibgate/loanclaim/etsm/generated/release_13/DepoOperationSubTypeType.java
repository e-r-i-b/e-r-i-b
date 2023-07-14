
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DepoOperationSubType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DepoOperationSubType_Type">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="INTERNAL_TRANSFER"/>
 *     &lt;enumeration value="LIST_TRANSFER"/>
 *     &lt;enumeration value="EXTERNAL_TRANSFER"/>
 *     &lt;enumeration value="INTERNAL_RECEPTION"/>
 *     &lt;enumeration value="LIST_RECEPTION"/>
 *     &lt;enumeration value="EXTERNAL_RECEPTION"/>
 *     &lt;enumeration value="INTERNAL_ACCOUNT_TRANSFER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DepoOperationSubType_Type")
@XmlEnum
public enum DepoOperationSubTypeType {


    /**
     * ������� �� ���� ���� ������ ����������� (���� ��� ��������: 231)
     * 
     */
    INTERNAL_TRANSFER,

    /**
     * ������� �� ���� � ������� (���� ��� ��������: 231)
     * 
     */
    LIST_TRANSFER,

    /**
     * ������� �� ���� � ������ ����������� (���� ��� ��������: 231)
     * 
     */
    EXTERNAL_TRANSFER,

    /**
     * ����� �������� �� ����� ���� ������ ����������� (���� ��� ��������: 240)
     * 
     */
    INTERNAL_RECEPTION,

    /**
     * ����� �������� �� ����� � ������� (���� ��� ��������: 240)
     * 
     */
    LIST_RECEPTION,

    /**
     * ����� �������� �� ����� � ������ ����������� (���� ��� ��������: 240)
     * 
     */
    EXTERNAL_RECEPTION,

    /**
     * ������� ����� ��������� ����� ���� (���� ��� ��������: 220)
     * 
     */
    INTERNAL_ACCOUNT_TRANSFER;

    public String value() {
        return name();
    }

    public static DepoOperationSubTypeType fromValue(String v) {
        return valueOf(v);
    }

}
