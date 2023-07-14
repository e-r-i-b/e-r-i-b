
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for CCAcctStmtRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CCAcctStmtRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EffDate" type="{}DateTime"/>
 *         &lt;element name="DiscDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="Number" type="{}String" minOccurs="0"/>
 *         &lt;element name="Code" type="{}String" minOccurs="0"/>
 *         &lt;element name="CorAccountNumber" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}StmtSummAmt" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="IsDebit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="TrnType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/choice>
 *         &lt;element name="TrnDest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrnSrc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrnDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrigCurAmt">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}CurAmt"/>
 *                   &lt;element ref="{}AcctCur"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OperationAmt" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}CurAmt"/>
 *                   &lt;element ref="{}AcctCur"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RemaindAmt" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}CurAmt"/>
 *                   &lt;element ref="{}AcctCur"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CCAcctStmtRec_Type", propOrder = {
    "effDate",
    "discDate",
    "number",
    "code",
    "corAccountNumber",
    "stmtSummAmt",
    "trnType",
    "isDebit",
    "trnDest",
    "trnSrc",
    "trnDesc",
    "origCurAmt",
    "operationAmt",
    "remaindAmt"
})
@XmlRootElement(name = "CCAcctStmtRec")
public class CCAcctStmtRec {

    @XmlElement(name = "EffDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar effDate;
    @XmlElement(name = "DiscDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar discDate;
    @XmlElement(name = "Number")
    protected String number;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "CorAccountNumber")
    protected String corAccountNumber;
    @XmlElement(name = "StmtSummAmt")
    protected StmtSummAmt stmtSummAmt;
    @XmlElement(name = "TrnType")
    protected Boolean trnType;
    @XmlElement(name = "IsDebit")
    protected Boolean isDebit;
    @XmlElement(name = "TrnDest")
    protected String trnDest;
    @XmlElement(name = "TrnSrc")
    protected String trnSrc;
    @XmlElement(name = "TrnDesc")
    protected String trnDesc;
    @XmlElement(name = "OrigCurAmt", required = true)
    protected CCAcctStmtRec.OrigCurAmt origCurAmt;
    @XmlElement(name = "OperationAmt")
    protected CCAcctStmtRec.OperationAmt operationAmt;
    @XmlElement(name = "RemaindAmt")
    protected CCAcctStmtRec.RemaindAmt remaindAmt;

    /**
     * Gets the value of the effDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEffDate() {
        return effDate;
    }

    /**
     * Sets the value of the effDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDate(Calendar value) {
        this.effDate = value;
    }

    /**
     * Gets the value of the discDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDiscDate() {
        return discDate;
    }

    /**
     * Sets the value of the discDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscDate(Calendar value) {
        this.discDate = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the corAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorAccountNumber() {
        return corAccountNumber;
    }

    /**
     * Sets the value of the corAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorAccountNumber(String value) {
        this.corAccountNumber = value;
    }

    /**
     * Gets the value of the stmtSummAmt property.
     * 
     * @return
     *     possible object is
     *     {@link StmtSummAmt }
     *     
     */
    public StmtSummAmt getStmtSummAmt() {
        return stmtSummAmt;
    }

    /**
     * Sets the value of the stmtSummAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link StmtSummAmt }
     *     
     */
    public void setStmtSummAmt(StmtSummAmt value) {
        this.stmtSummAmt = value;
    }

    /**
     * Gets the value of the trnType property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getTrnType() {
        return trnType;
    }

    /**
     * Sets the value of the trnType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTrnType(Boolean value) {
        this.trnType = value;
    }

    /**
     * Gets the value of the isDebit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsDebit() {
        return isDebit;
    }

    /**
     * Sets the value of the isDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDebit(Boolean value) {
        this.isDebit = value;
    }

    /**
     * Gets the value of the trnDest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrnDest() {
        return trnDest;
    }

    /**
     * Sets the value of the trnDest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrnDest(String value) {
        this.trnDest = value;
    }

    /**
     * Gets the value of the trnSrc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrnSrc() {
        return trnSrc;
    }

    /**
     * Sets the value of the trnSrc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrnSrc(String value) {
        this.trnSrc = value;
    }

    /**
     * Gets the value of the trnDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrnDesc() {
        return trnDesc;
    }

    /**
     * Sets the value of the trnDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrnDesc(String value) {
        this.trnDesc = value;
    }

    /**
     * Gets the value of the origCurAmt property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctStmtRec.OrigCurAmt }
     *     
     */
    public CCAcctStmtRec.OrigCurAmt getOrigCurAmt() {
        return origCurAmt;
    }

    /**
     * Sets the value of the origCurAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctStmtRec.OrigCurAmt }
     *     
     */
    public void setOrigCurAmt(CCAcctStmtRec.OrigCurAmt value) {
        this.origCurAmt = value;
    }

    /**
     * Gets the value of the operationAmt property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctStmtRec.OperationAmt }
     *     
     */
    public CCAcctStmtRec.OperationAmt getOperationAmt() {
        return operationAmt;
    }

    /**
     * Sets the value of the operationAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctStmtRec.OperationAmt }
     *     
     */
    public void setOperationAmt(CCAcctStmtRec.OperationAmt value) {
        this.operationAmt = value;
    }

    /**
     * Gets the value of the remaindAmt property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctStmtRec.RemaindAmt }
     *     
     */
    public CCAcctStmtRec.RemaindAmt getRemaindAmt() {
        return remaindAmt;
    }

    /**
     * Sets the value of the remaindAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctStmtRec.RemaindAmt }
     *     
     */
    public void setRemaindAmt(CCAcctStmtRec.RemaindAmt value) {
        this.remaindAmt = value;
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
     *         &lt;element ref="{}CurAmt"/>
     *         &lt;element ref="{}AcctCur"/>
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
        "curAmt",
        "acctCur"
    })
    public static class OperationAmt {

        @XmlElement(name = "CurAmt", required = true)
        protected BigDecimal curAmt;
        @XmlElement(name = "AcctCur", required = true)
        protected String acctCur;

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
         * Gets the value of the acctCur property.
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
     *         &lt;element ref="{}CurAmt"/>
     *         &lt;element ref="{}AcctCur"/>
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
        "curAmt",
        "acctCur"
    })
    public static class OrigCurAmt {

        @XmlElement(name = "CurAmt", required = true)
        protected BigDecimal curAmt;
        @XmlElement(name = "AcctCur", required = true)
        protected String acctCur;

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
         * Gets the value of the acctCur property.
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
     *         &lt;element ref="{}CurAmt"/>
     *         &lt;element ref="{}AcctCur"/>
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
        "curAmt",
        "acctCur"
    })
    public static class RemaindAmt {

        @XmlElement(name = "CurAmt", required = true)
        protected BigDecimal curAmt;
        @XmlElement(name = "AcctCur", required = true)
        protected String acctCur;

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
         * Gets the value of the acctCur property.
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

}
