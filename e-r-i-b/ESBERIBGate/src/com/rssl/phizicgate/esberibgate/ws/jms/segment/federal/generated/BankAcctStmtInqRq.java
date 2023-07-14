
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ ��� ���������� IAS - ��������� ������� �� ���
 * 
 * <p>Java class for BankAcctStmtInqRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctStmtInqRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}BankAcctRec"/>
 *         &lt;element ref="{}StmtType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctStmtInqRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "bankAcctRec",
    "stmtType"
})
@XmlRootElement(name = "BankAcctStmtInqRq")
public class BankAcctStmtInqRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "BankAcctRec", required = true)
    protected BankAcctRecType bankAcctRec;
    @XmlElement(name = "StmtType", required = true)
    protected StmtTypeType stmtType;

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
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoType }
     *     
     */
    public BankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoType }
     *     
     */
    public void setBankInfo(BankInfoType value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the bankAcctRec property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctRecType }
     *     
     */
    public BankAcctRecType getBankAcctRec() {
        return bankAcctRec;
    }

    /**
     * Sets the value of the bankAcctRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctRecType }
     *     
     */
    public void setBankAcctRec(BankAcctRecType value) {
        this.bankAcctRec = value;
    }

    /**
     * Gets the value of the stmtType property.
     * 
     * @return
     *     possible object is
     *     {@link StmtTypeType }
     *     
     */
    public StmtTypeType getStmtType() {
        return stmtType;
    }

    /**
     * Sets the value of the stmtType property.
     * 
     * @param value
     *     allowed object is
     *     {@link StmtTypeType }
     *     
     */
    public void setStmtType(StmtTypeType value) {
        this.stmtType = value;
    }

}
