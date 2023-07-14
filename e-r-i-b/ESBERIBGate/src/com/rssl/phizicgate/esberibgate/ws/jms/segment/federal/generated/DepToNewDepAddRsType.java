
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип ответа для интерфейса TDDO и TDO. Открытие вклада с переводом на него денежных средств с другого вклада (возможно с закрытием этого вклада) и открытие бессрочного вклада без первоначального взноса
 * 
 * <p>Java class for DepToNewDepAddRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepToNewDepAddRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element ref="{}AgreemtInfo" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtInfoClose" minOccurs="0"/>
 *         &lt;element ref="{}DstCurAmt" minOccurs="0"/>
 *         &lt;element ref="{}SrcCurAmt" minOccurs="0"/>
 *         &lt;element name="SrcLayoutInfo" type="{}SrcLayoutInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepToNewDepAddRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "agreemtInfo",
    "agreemtInfoClose",
    "dstCurAmt",
    "srcCurAmt",
    "srcLayoutInfo"
})
public class DepToNewDepAddRsType {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "AgreemtInfo")
    protected AgreemtInfoType agreemtInfo;
    @XmlElement(name = "AgreemtInfoClose")
    protected AgreemtInfoType agreemtInfoClose;
    @XmlElement(name = "DstCurAmt")
    protected BigDecimal dstCurAmt;
    @XmlElement(name = "SrcCurAmt")
    protected BigDecimal srcCurAmt;
    @XmlElement(name = "SrcLayoutInfo")
    protected SrcLayoutInfoType srcLayoutInfo;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(String value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the agreemtInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AgreemtInfoType }
     *     
     */
    public AgreemtInfoType getAgreemtInfo() {
        return agreemtInfo;
    }

    /**
     * Sets the value of the agreemtInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreemtInfoType }
     *     
     */
    public void setAgreemtInfo(AgreemtInfoType value) {
        this.agreemtInfo = value;
    }

    /**
     * Gets the value of the agreemtInfoClose property.
     * 
     * @return
     *     possible object is
     *     {@link AgreemtInfoType }
     *     
     */
    public AgreemtInfoType getAgreemtInfoClose() {
        return agreemtInfoClose;
    }

    /**
     * Sets the value of the agreemtInfoClose property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreemtInfoType }
     *     
     */
    public void setAgreemtInfoClose(AgreemtInfoType value) {
        this.agreemtInfoClose = value;
    }

    /**
     * Gets the value of the dstCurAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDstCurAmt() {
        return dstCurAmt;
    }

    /**
     * Sets the value of the dstCurAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDstCurAmt(BigDecimal value) {
        this.dstCurAmt = value;
    }

    /**
     * Gets the value of the srcCurAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSrcCurAmt() {
        return srcCurAmt;
    }

    /**
     * Sets the value of the srcCurAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSrcCurAmt(BigDecimal value) {
        this.srcCurAmt = value;
    }

    /**
     * Gets the value of the srcLayoutInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SrcLayoutInfoType }
     *     
     */
    public SrcLayoutInfoType getSrcLayoutInfo() {
        return srcLayoutInfo;
    }

    /**
     * Sets the value of the srcLayoutInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SrcLayoutInfoType }
     *     
     */
    public void setSrcLayoutInfo(SrcLayoutInfoType value) {
        this.srcLayoutInfo = value;
    }

}
