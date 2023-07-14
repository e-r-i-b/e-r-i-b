
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Информация о страховом/НПФ продукте
 * 
 * <p>Java class for InsuranceApp_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InsuranceApp_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Reference" type="{}String"/>
 *         &lt;element name="BusinessProcess" type="{}String" minOccurs="0"/>
 *         &lt;element name="ProductType" type="{}String" minOccurs="0"/>
 *         &lt;element name="Status" type="{}String" minOccurs="0"/>
 *         &lt;element name="Company" type="{}String" minOccurs="0"/>
 *         &lt;element name="Program" type="{}String" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="SNILS" type="{}String" minOccurs="0"/>
 *         &lt;element name="Amount" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="AmountCur" type="{}String" minOccurs="0"/>
 *         &lt;element name="PolicyDetails" type="{}PolicyDetails_Type" minOccurs="0"/>
 *         &lt;element name="Risks" type="{}String" minOccurs="0"/>
 *         &lt;element name="AdditionalInfo" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InsuranceApp_Type", propOrder = {
    "reference",
    "businessProcess",
    "productType",
    "status",
    "company",
    "program",
    "startDate",
    "endDate",
    "snils",
    "amount",
    "amountCur",
    "policyDetails",
    "risks",
    "additionalInfo"
})
@XmlRootElement(name = "InsuranceApp")
public class InsuranceApp {

    @XmlElement(name = "Reference", required = true)
    protected String reference;
    @XmlElement(name = "BusinessProcess")
    protected String businessProcess;
    @XmlElement(name = "ProductType")
    protected String productType;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Company")
    protected String company;
    @XmlElement(name = "Program")
    protected String program;
    @XmlElement(name = "StartDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar startDate;
    @XmlElement(name = "EndDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar endDate;
    @XmlElement(name = "SNILS")
    protected String snils;
    @XmlElement(name = "Amount")
    protected BigDecimal amount;
    @XmlElement(name = "AmountCur")
    protected String amountCur;
    @XmlElement(name = "PolicyDetails")
    protected PolicyDetailsType policyDetails;
    @XmlElement(name = "Risks")
    protected String risks;
    @XmlElement(name = "AdditionalInfo")
    protected String additionalInfo;

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the businessProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessProcess() {
        return businessProcess;
    }

    /**
     * Sets the value of the businessProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessProcess(String value) {
        this.businessProcess = value;
    }

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the company property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     * Gets the value of the program property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgram() {
        return program;
    }

    /**
     * Sets the value of the program property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgram(String value) {
        this.program = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDate() {
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
    public void setStartDate(Calendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(Calendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the snils property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSNILS() {
        return snils;
    }

    /**
     * Sets the value of the snils property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSNILS(String value) {
        this.snils = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the amountCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountCur() {
        return amountCur;
    }

    /**
     * Sets the value of the amountCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountCur(String value) {
        this.amountCur = value;
    }

    /**
     * Gets the value of the policyDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyDetailsType }
     *     
     */
    public PolicyDetailsType getPolicyDetails() {
        return policyDetails;
    }

    /**
     * Sets the value of the policyDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyDetailsType }
     *     
     */
    public void setPolicyDetails(PolicyDetailsType value) {
        this.policyDetails = value;
    }

    /**
     * Gets the value of the risks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRisks() {
        return risks;
    }

    /**
     * Sets the value of the risks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRisks(String value) {
        this.risks = value;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

}
