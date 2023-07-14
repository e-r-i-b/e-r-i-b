
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о переводе
 * 
 * <p>Java class for PayInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}CardAcctId" minOccurs="0"/>
 *         &lt;element name="AcctId" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}SystemId"/>
 *                   &lt;element name="AcctIdFrom" type="{}AcctIdType"/>
 *                   &lt;element ref="{}BankInfo"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}DepoAcctId"/>
 *         &lt;element ref="{}DeptRec"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayInfo_Type", propOrder = {
    "cardAcctId",
    "acctId",
    "depoAcctId",
    "deptRec"
})
@XmlRootElement(name = "PayInfo")
public class PayInfo {

    @XmlElement(name = "CardAcctId")
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "AcctId")
    protected PayInfo.AcctId acctId;
    @XmlElement(name = "DepoAcctId", required = true)
    protected DepoAcctId depoAcctId;
    @XmlElement(name = "DeptRec", required = true)
    protected DeptRec deptRec;

    /**
     * Gets the value of the cardAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getCardAcctId() {
        return cardAcctId;
    }

    /**
     * Sets the value of the cardAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setCardAcctId(CardAcctIdType value) {
        this.cardAcctId = value;
    }

    /**
     * Gets the value of the acctId property.
     * 
     * @return
     *     possible object is
     *     {@link PayInfo.AcctId }
     *     
     */
    public PayInfo.AcctId getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayInfo.AcctId }
     *     
     */
    public void setAcctId(PayInfo.AcctId value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the depoAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAcctId }
     *     
     */
    public DepoAcctId getDepoAcctId() {
        return depoAcctId;
    }

    /**
     * Sets the value of the depoAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAcctId }
     *     
     */
    public void setDepoAcctId(DepoAcctId value) {
        this.depoAcctId = value;
    }

    /**
     * Gets the value of the deptRec property.
     * 
     * @return
     *     possible object is
     *     {@link DeptRec }
     *     
     */
    public DeptRec getDeptRec() {
        return deptRec;
    }

    /**
     * Sets the value of the deptRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeptRec }
     *     
     */
    public void setDeptRec(DeptRec value) {
        this.deptRec = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}SystemId"/>
     *         &lt;element name="AcctIdFrom" type="{}AcctIdType"/>
     *         &lt;element ref="{}BankInfo"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "systemId",
        "acctIdFrom",
        "bankInfo"
    })
    public static class AcctId {

        @XmlElement(name = "SystemId", required = true)
        protected String systemId;
        @XmlElement(name = "AcctIdFrom", required = true)
        protected String acctIdFrom;
        @XmlElement(name = "BankInfo", required = true)
        protected BankInfo bankInfo;

        /**
         * Gets the value of the systemId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSystemId() {
            return systemId;
        }

        /**
         * Sets the value of the systemId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSystemId(String value) {
            this.systemId = value;
        }

        /**
         * Gets the value of the acctIdFrom property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAcctIdFrom() {
            return acctIdFrom;
        }

        /**
         * Sets the value of the acctIdFrom property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAcctIdFrom(String value) {
            this.acctIdFrom = value;
        }

        /**
         * Gets the value of the bankInfo property.
         * 
         * @return
         *     possible object is
         *     {@link BankInfo }
         *     
         */
        public BankInfo getBankInfo() {
            return bankInfo;
        }

        /**
         * Sets the value of the bankInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link BankInfo }
         *     
         */
        public void setBankInfo(BankInfo value) {
            this.bankInfo = value;
        }

    }

}
