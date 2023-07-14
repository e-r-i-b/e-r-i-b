
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Погашаемый по Top-Up договор
 * 
 * <p>Java class for RepayLoan_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RepayLoan_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdSource" type="{}IdSource_Type"/>
 *         &lt;element name="IdContract" type="{}ContractNumber_Type"/>
 *         &lt;element name="LoanAccount_Number" type="{}AcctIdType"/>
 *         &lt;element name="AgreementNumber" type="{}AgreemtNum_Type"/>
 *         &lt;element name="StartDate" type="{}Date"/>
 *         &lt;element name="MaturityDate" type="{}Date"/>
 *         &lt;element name="TotalAmount" type="{}Amt_Type"/>
 *         &lt;element name="Currency" type="{}AcctCur_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepayLoan_Type", propOrder = {
    "idSource",
    "idContract",
    "loanAccountNumber",
    "agreementNumber",
    "startDate",
    "maturityDate",
    "totalAmount",
    "currency"
})
public class RepayLoanType {

    @XmlElement(name = "IdSource", required = true)
    protected String idSource;
    @XmlElement(name = "IdContract", required = true)
    protected String idContract;
    @XmlElement(name = "LoanAccount_Number", required = true)
    protected String loanAccountNumber;
    @XmlElement(name = "AgreementNumber", required = true)
    protected String agreementNumber;
    @XmlElement(name = "StartDate", required = true)
    protected String startDate;
    @XmlElement(name = "MaturityDate", required = true)
    protected String maturityDate;
    @XmlElement(name = "TotalAmount", required = true)
    protected BigDecimal totalAmount;
    @XmlElement(name = "Currency", required = true)
    protected String currency;

    /**
     * Gets the value of the idSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSource() {
        return idSource;
    }

    /**
     * Sets the value of the idSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSource(String value) {
        this.idSource = value;
    }

    /**
     * Gets the value of the idContract property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdContract() {
        return idContract;
    }

    /**
     * Sets the value of the idContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdContract(String value) {
        this.idContract = value;
    }

    /**
     * Gets the value of the loanAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    /**
     * Sets the value of the loanAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanAccountNumber(String value) {
        this.loanAccountNumber = value;
    }

    /**
     * Gets the value of the agreementNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementNumber() {
        return agreementNumber;
    }

    /**
     * Sets the value of the agreementNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementNumber(String value) {
        this.agreementNumber = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the maturityDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaturityDate() {
        return maturityDate;
    }

    /**
     * Sets the value of the maturityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaturityDate(String value) {
        this.maturityDate = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAmount(BigDecimal value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
