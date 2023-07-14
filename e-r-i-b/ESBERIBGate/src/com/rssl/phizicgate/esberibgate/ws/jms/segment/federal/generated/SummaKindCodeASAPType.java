
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SummaKindCodeASAP_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SummaKindCodeASAP_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FIXED_SUMMA_IN_RECIP_CURR"/>
 *     &lt;enumeration value="BY_BILLING"/>
 *     &lt;enumeration value="PERCENT_BY_ANY_RECEIPT"/>
 *     &lt;enumeration value="PERCENT_BY_DEBIT"/>
 *     &lt;enumeration value="RUR_SUMMA"/>
 *     &lt;enumeration value="FIXED_SUMMA"/>
 *     &lt;enumeration value="BY_BILLING_BASKET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SummaKindCodeASAP_Type")
@XmlEnum
public enum SummaKindCodeASAPType {


    /**
     * ������������� �����
     * 
     */
    FIXED_SUMMA_IN_RECIP_CURR,

    /**
     * �� ������������� �����
     * 
     */
    BY_BILLING,

    /**
     * �������� �������� ������� �� ������ ���������� �� ���� ��������
     * 
     */
    PERCENT_BY_ANY_RECEIPT,

    /**
     * �������� �������� ������� �� ������ �������� �� �����
     * 
     */
    PERCENT_BY_DEBIT,

    /**
     * �������� �������� �����  � ������
     * 
     */
    RUR_SUMMA,

    /**
     * �������� �������� �����  � ������ ����� ��������
     * 
     */
    FIXED_SUMMA,
    BY_BILLING_BASKET;

    public String value() {
        return name();
    }

    public static SummaKindCodeASAPType fromValue(String v) {
        return valueOf(v);
    }

}
