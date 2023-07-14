
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация о конверсии при операциях с ОМС
 * 
 * <p>Java class for IMAOperConvInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IMAOperConvInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurType" type="{}IMAOperCurType_Type"/>
 *         &lt;element ref="{}CurAmt"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IMAOperConvInfo_Type", propOrder = {
    "curType",
    "curAmt",
    "acctCur"
})
public class IMAOperConvInfoType {

    @XmlElement(name = "CurType", required = true)
    protected IMAOperCurTypeType curType;
    @XmlElement(name = "CurAmt", required = true)
    protected BigDecimal curAmt;
    @XmlElement(name = "AcctCur")
    protected String acctCur;

    /**
     * Gets the value of the curType property.
     * 
     * @return
     *     possible object is
     *     {@link IMAOperCurTypeType }
     *     
     */
    public IMAOperCurTypeType getCurType() {
        return curType;
    }

    /**
     * Sets the value of the curType property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAOperCurTypeType }
     *     
     */
    public void setCurType(IMAOperCurTypeType value) {
        this.curType = value;
    }

    /**
     * Сумма операции в рублях
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt() {
        return curAmt;
    }

    /**
     * Sets the value of the curAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt(BigDecimal value) {
        this.curAmt = value;
    }

    /**
     * Валюта
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur() {
        return acctCur;
    }

    /**
     * Sets the value of the acctCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur(String value) {
        this.acctCur = value;
    }

}
