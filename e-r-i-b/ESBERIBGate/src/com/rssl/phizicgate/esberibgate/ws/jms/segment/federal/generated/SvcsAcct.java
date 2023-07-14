
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * —писок длительных поручений
 * 
 * <p>Java class for SvcsAcct_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SvcsAcct_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId"/>
 *         &lt;element name="SvcAcctId">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SvcAcctNum" type="{}Long"/>
 *                   &lt;element ref="{}BankInfo"/>
 *                   &lt;element name="SvcType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DtStart" type="{}Date"/>
 *         &lt;element name="DtEnd" type="{}Date"/>
 *         &lt;element name="CurAmt" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element ref="{}CardNum" minOccurs="0"/>
 *         &lt;element ref="{}AcctId" minOccurs="0"/>
 *         &lt;element name="PmtKind" type="{}PmtKind_Type"/>
 *         &lt;element name="RcptKind" type="{}RcptKind_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SvcsAcct_Type", propOrder = {
    "systemId",
    "svcAcctId",
    "dtStart",
    "dtEnd",
    "curAmt",
    "cardNum",
    "acctId",
    "pmtKind",
    "rcptKind"
})
@XmlRootElement(name = "SvcsAcct")
public class SvcsAcct {

    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "SvcAcctId", required = true)
    protected SvcsAcct.SvcAcctId svcAcctId;
    @XmlElement(name = "DtStart", required = true)
    protected String dtStart;
    @XmlElement(name = "DtEnd", required = true)
    protected String dtEnd;
    @XmlElement(name = "CurAmt", required = true)
    protected BigDecimal curAmt;
    @XmlElement(name = "CardNum")
    protected String cardNum;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "PmtKind", required = true)
    protected String pmtKind;
    @XmlElement(name = "RcptKind", required = true)
    protected String rcptKind;

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
     * Gets the value of the svcAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link SvcsAcct.SvcAcctId }
     *     
     */
    public SvcsAcct.SvcAcctId getSvcAcctId() {
        return svcAcctId;
    }

    /**
     * Sets the value of the svcAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcsAcct.SvcAcctId }
     *     
     */
    public void setSvcAcctId(SvcsAcct.SvcAcctId value) {
        this.svcAcctId = value;
    }

    /**
     * Gets the value of the dtStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtStart() {
        return dtStart;
    }

    /**
     * Sets the value of the dtStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtStart(String value) {
        this.dtStart = value;
    }

    /**
     * Gets the value of the dtEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtEnd() {
        return dtEnd;
    }

    /**
     * Sets the value of the dtEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtEnd(String value) {
        this.dtEnd = value;
    }

    /**
     * Gets the value of the curAmt property.
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
     * Ќомер карты, с которой выполнитс€ списание
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Sets the value of the cardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNum(String value) {
        this.cardNum = value;
    }

    /**
     * Ќомер счета, с которого выполнитс€ списание
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctId(String value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the pmtKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmtKind() {
        return pmtKind;
    }

    /**
     * Sets the value of the pmtKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmtKind(String value) {
        this.pmtKind = value;
    }

    /**
     * Gets the value of the rcptKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRcptKind() {
        return rcptKind;
    }

    /**
     * Sets the value of the rcptKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRcptKind(String value) {
        this.rcptKind = value;
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
     *         &lt;element name="SvcAcctNum" type="{}Long"/>
     *         &lt;element ref="{}BankInfo"/>
     *         &lt;element name="SvcType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "svcAcctNum",
        "bankInfo",
        "svcType"
    })
    public static class SvcAcctId {

        @XmlElement(name = "SvcAcctNum")
        protected long svcAcctNum;
        @XmlElement(name = "BankInfo", required = true)
        protected BankInfoType bankInfo;
        @XmlElement(name = "SvcType")
        protected boolean svcType;

        /**
         * Gets the value of the svcAcctNum property.
         * 
         */
        public long getSvcAcctNum() {
            return svcAcctNum;
        }

        /**
         * Sets the value of the svcAcctNum property.
         * 
         */
        public void setSvcAcctNum(long value) {
            this.svcAcctNum = value;
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
         * Gets the value of the svcType property.
         * 
         */
        public boolean isSvcType() {
            return svcType;
        }

        /**
         * Sets the value of the svcType property.
         * 
         */
        public void setSvcType(boolean value) {
            this.svcType = value;
        }

    }

}
