
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
     * ��������� ������� � �����
     * 
     */
    TCC("TCC"),

    /**
     * ������� � ����� �� �����
     * 
     */
    TCD("TCD"),

    /**
     * ������� �� ������ �� �����
     * 
     */
    TDC("TDC"),

    /**
     * ������� �� ������ �� �����
     * 
     */
    TDD("TDD"),

    /**
     * ������ ������� � �����
     * 
     */
    TCP("TCP"),

    /**
     * �������� ����������� ��������� �� ������� �������� ������� � ������ ����������� ����
     * 
     */
    SDP("SDP"),

    /**
     * �������� ����������� ��������� �� ��������� ������� � ��������� �����
     * 
     */
    @XmlEnumValue("CreateForm190ForLoanRequest")
    CREATE_FORM_190_FOR_LOAN_REQUEST("CreateForm190ForLoanRequest"),

    /**
     * �������� ������ � ��������� �������� ������� �� ����� ������������ ������ �� ������ ���� �� ������
     * 
     */
    TDDC("TDDC"),

    /**
     * �������� ������ � ��������� �������� ������� �� ����� ������������ ������ �� ���� ���
     * 
     */
    TDCC("TDCC"),

    /**
     * ������� � ������ �� ��� (���� ����� ��� � TID, � �DD)
     * 
     */
    TDI("TDI"),

    /**
     * ������� � ��� �� ����� (���� ����� ��� � TDI, � �DD)
     * 
     */
    TID("TID"),

    /**
     * �������� ��� ��������� � ������
     * 
     */
    TDIO("TDIO"),

    /**
     * �������� ��� ��������� � �����
     * 
     */
    TCIO("TCIO"),

    /**
     * ������� � ����� �� ��� (������� �������)
     * 
     */
    TCI("TCI"),

    /**
     * ������� � ��� �� ����� (������� �������)
     * 
     */
    TIC("TIC"),

    /**
     * �������� ������ ��������� � ������� ������
     * 
     */
    TDDO("TDDO"),

    /**
     * �������� ������ ��������� � �����
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
