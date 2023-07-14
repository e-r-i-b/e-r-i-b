
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись о выписке
 * 
 * <p>Java class for BankAcctFullStmtRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctFullStmtRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EffDate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="Number" type="{}String"/>
 *         &lt;element name="Code" type="{}String"/>
 *         &lt;element name="DocumentNumber" type="{}String"/>
 *         &lt;element name="CorAcc" type="{}String"/>
 *         &lt;element ref="{}StmtSummType"/>
 *         &lt;element name="Amt" type="{}Decimal"/>
 *         &lt;element name="AmtPhiz" type="{}Decimal"/>
 *         &lt;element name="Balance" type="{}Decimal"/>
 *         &lt;element name="BalancePhiz" type="{}Decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctFullStmtRec_Type", propOrder = {
    "effDate",
    "number",
    "code",
    "documentNumber",
    "corAcc",
    "stmtSummType",
    "amt",
    "amtPhiz",
    "balance",
    "balancePhiz"
})
@XmlRootElement(name = "BankAcctFullStmtRec")
public class BankAcctFullStmtRec {

    @XmlElement(name = "EffDate", required = true)
    protected Object effDate;
    @XmlElement(name = "Number", required = true)
    protected String number;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "DocumentNumber", required = true)
    protected String documentNumber;
    @XmlElement(name = "CorAcc", required = true)
    protected String corAcc;
    @XmlElement(name = "StmtSummType", required = true)
    protected String stmtSummType;
    @XmlElement(name = "Amt", required = true)
    protected BigDecimal amt;
    @XmlElement(name = "AmtPhiz", required = true)
    protected BigDecimal amtPhiz;
    @XmlElement(name = "Balance", required = true)
    protected BigDecimal balance;
    @XmlElement(name = "BalancePhiz", required = true)
    protected BigDecimal balancePhiz;

    /**
     * Gets the value of the effDate property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getEffDate() {
        return effDate;
    }

    /**
     * Sets the value of the effDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setEffDate(Object value) {
        this.effDate = value;
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
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the corAcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorAcc() {
        return corAcc;
    }

    /**
     * Sets the value of the corAcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorAcc(String value) {
        this.corAcc = value;
    }

    /**
     * Gets the value of the stmtSummType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStmtSummType() {
        return stmtSummType;
    }

    /**
     * Sets the value of the stmtSummType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStmtSummType(String value) {
        this.stmtSummType = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmt(BigDecimal value) {
        this.amt = value;
    }

    /**
     * Gets the value of the amtPhiz property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmtPhiz() {
        return amtPhiz;
    }

    /**
     * Sets the value of the amtPhiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmtPhiz(BigDecimal value) {
        this.amtPhiz = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalance(BigDecimal value) {
        this.balance = value;
    }

    /**
     * Gets the value of the balancePhiz property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalancePhiz() {
        return balancePhiz;
    }

    /**
     * Sets the value of the balancePhiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalancePhiz(BigDecimal value) {
        this.balancePhiz = value;
    }

}
