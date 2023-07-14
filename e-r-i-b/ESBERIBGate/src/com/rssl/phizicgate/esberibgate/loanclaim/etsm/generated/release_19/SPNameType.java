
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SPName_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SPName_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BP_ES"/>
 *     &lt;enumeration value="BP_VSP"/>
 *     &lt;enumeration value="BP_ERIB"/>
 *     &lt;enumeration value="BP_COD"/>
 *     &lt;enumeration value="BP_ESK"/>
 *     &lt;enumeration value="BP_IASK"/>
 *     &lt;enumeration value="BP_WAY"/>
 *     &lt;enumeration value="BP_BS"/>
 *     &lt;enumeration value="CRM"/>
 *     &lt;enumeration value="ETSM_Rtl"/>
 *     &lt;enumeration value="urn:sbrfsystems:99-autopay"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SPName_Type")
@XmlEnum
public enum SPNameType {


    /**
     * ���������� ���������������� (����������)
     * 
     */
    BP_ES("BP_ES"),

    /**
     * �� ���
     * 
     */
    BP_VSP("BP_VSP"),

    /**
     * �� ���� ��� �������� ��������
     * 
     */
    BP_ERIB("BP_ERIB"),

    /**
     * �� ���
     * 
     */
    BP_COD("BP_COD"),

    /**
     * �� ���
     * 
     */
    BP_ESK("BP_ESK"),

    /**
     * �� ����
     * 
     */
    BP_IASK("BP_IASK"),

    /**
     * �� WAY4U
     * 
     */
    BP_WAY("BP_WAY"),

    /**
     * �� ��
     * 
     */
    BP_BS("BP_BS"),

    /**
     * �� CRM
     * 
     */
    CRM("CRM"),

    /**
     * ��������� ������ ETSM
     * 
     */
    @XmlEnumValue("ETSM_Rtl")
    ETSM_RTL("ETSM_Rtl"),
    @XmlEnumValue("urn:sbrfsystems:99-autopay")
    URN_SBRFSYSTEMS_99_AUTOPAY("urn:sbrfsystems:99-autopay");
    private final String value;

    SPNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SPNameType fromValue(String v) {
        for (SPNameType c: SPNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
