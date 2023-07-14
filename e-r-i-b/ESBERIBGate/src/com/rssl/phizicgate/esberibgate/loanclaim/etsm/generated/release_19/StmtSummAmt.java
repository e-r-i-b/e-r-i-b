
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StmtSummAmt_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StmtSummAmt_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}StmtSummType"/>
 *         &lt;element ref="{}CurAmt"/>
 *         &lt;choice>
 *           &lt;element name="CurAmtCur" type="{}AcctCur_Type"/>
 *           &lt;element name="AcctCur" type="{}AcctCur_Type"/>
 *         &lt;/choice>
 *         &lt;element name="ContrAccount" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StmtSummAmt_Type", propOrder = {
    "stmtSummType",
    "curAmt",
    "acctCur",
    "curAmtCur",
    "contrAccount"
})
@XmlRootElement(name = "StmtSummAmt")
public class StmtSummAmt {

    @XmlElement(name = "StmtSummType", required = true)
    protected String stmtSummType;
    @XmlElement(name = "CurAmt", required = true)
    protected BigDecimal curAmt;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "CurAmtCur")
    protected String curAmtCur;
    @XmlElement(name = "ContrAccount")
    protected String contrAccount;

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

    /**
     * Gets the value of the curAmtCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurAmtCur() {
        return curAmtCur;
    }

    /**
     * Sets the value of the curAmtCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurAmtCur(String value) {
        this.curAmtCur = value;
    }

    /**
     * Gets the value of the contrAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContrAccount() {
        return contrAccount;
    }

    /**
     * Sets the value of the contrAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContrAccount(String value) {
        this.contrAccount = value;
    }

}
