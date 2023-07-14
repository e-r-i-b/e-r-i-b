//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:43 PM MSD 
//


package com.rssl.phizic.business.test.mobile6.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * �������� ������
 * 
 * <p>Java class for DocAccountOpeningClaim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocAccountOpeningClaim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="depositSubType" type="{}Field"/>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="depositName" type="{}Field"/>
 *         &lt;element name="openDate" type="{}Field"/>
 *         &lt;element name="closingDate" type="{}Field"/>
 *         &lt;element name="toResourceCurrency" type="{}Field"/>
 *         &lt;element name="needInitialFee" type="{}Field"/>
 *         &lt;element name="withMinimumBalance" type="{}Field"/>
 *         &lt;element name="minDepositBalance" type="{}Field" minOccurs="0"/>
 *         &lt;element name="fromResource" type="{}Field" minOccurs="0"/>
 *         &lt;element name="buyAmount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="course" type="{}Field" minOccurs="0"/>
 *         &lt;element name="sellAmount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="exactAmount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="interestRate" type="{}Field" minOccurs="0"/>
 *         &lt;element name="minAdditionalFee" type="{}Field" minOccurs="0"/>
 *         &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocAccountOpeningClaim", propOrder = {
    "depositSubType",
    "documentNumber",
    "documentDate",
    "depositName",
    "openDate",
    "closingDate",
    "toResourceCurrency",
    "needInitialFee",
    "withMinimumBalance",
    "minDepositBalance",
    "fromResource",
    "buyAmount",
    "course",
    "sellAmount",
    "exactAmount",
    "interestRate",
    "minAdditionalFee",
    "operationCode"
})
public class DocAccountOpeningClaimDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor depositSubType;
    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor depositName;
    @XmlElement(required = true)
    protected FieldDescriptor openDate;
    @XmlElement(required = true)
    protected FieldDescriptor closingDate;
    @XmlElement(required = true)
    protected FieldDescriptor toResourceCurrency;
    @XmlElement(required = true)
    protected FieldDescriptor needInitialFee;
    @XmlElement(required = true)
    protected FieldDescriptor withMinimumBalance;
    protected FieldDescriptor minDepositBalance;
    protected FieldDescriptor fromResource;
    protected FieldDescriptor buyAmount;
    protected FieldDescriptor course;
    protected FieldDescriptor sellAmount;
    protected FieldDescriptor exactAmount;
    protected FieldDescriptor interestRate;
    protected FieldDescriptor minAdditionalFee;
    protected FieldDescriptor operationCode;

    /**
     * Gets the value of the depositSubType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDepositSubType() {
        return depositSubType;
    }

    /**
     * Sets the value of the depositSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDepositSubType(FieldDescriptor value) {
        this.depositSubType = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentNumber(FieldDescriptor value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentDate(FieldDescriptor value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the depositName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDepositName() {
        return depositName;
    }

    /**
     * Sets the value of the depositName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDepositName(FieldDescriptor value) {
        this.depositName = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOpenDate(FieldDescriptor value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the closingDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the value of the closingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setClosingDate(FieldDescriptor value) {
        this.closingDate = value;
    }

    /**
     * Gets the value of the toResourceCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getToResourceCurrency() {
        return toResourceCurrency;
    }

    /**
     * Sets the value of the toResourceCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setToResourceCurrency(FieldDescriptor value) {
        this.toResourceCurrency = value;
    }

    /**
     * Gets the value of the needInitialFee property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getNeedInitialFee() {
        return needInitialFee;
    }

    /**
     * Sets the value of the needInitialFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setNeedInitialFee(FieldDescriptor value) {
        this.needInitialFee = value;
    }

    /**
     * Gets the value of the withMinimumBalance property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getWithMinimumBalance() {
        return withMinimumBalance;
    }

    /**
     * Sets the value of the withMinimumBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setWithMinimumBalance(FieldDescriptor value) {
        this.withMinimumBalance = value;
    }

    /**
     * Gets the value of the minDepositBalance property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getMinDepositBalance() {
        return minDepositBalance;
    }

    /**
     * Sets the value of the minDepositBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setMinDepositBalance(FieldDescriptor value) {
        this.minDepositBalance = value;
    }

    /**
     * Gets the value of the fromResource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getFromResource() {
        return fromResource;
    }

    /**
     * Sets the value of the fromResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setFromResource(FieldDescriptor value) {
        this.fromResource = value;
    }

    /**
     * Gets the value of the buyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getBuyAmount() {
        return buyAmount;
    }

    /**
     * Sets the value of the buyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setBuyAmount(FieldDescriptor value) {
        this.buyAmount = value;
    }

    /**
     * Gets the value of the course property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getCourse() {
        return course;
    }

    /**
     * Sets the value of the course property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setCourse(FieldDescriptor value) {
        this.course = value;
    }

    /**
     * Gets the value of the sellAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSellAmount() {
        return sellAmount;
    }

    /**
     * Sets the value of the sellAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSellAmount(FieldDescriptor value) {
        this.sellAmount = value;
    }

    /**
     * Gets the value of the exactAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getExactAmount() {
        return exactAmount;
    }

    /**
     * Sets the value of the exactAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setExactAmount(FieldDescriptor value) {
        this.exactAmount = value;
    }

    /**
     * Gets the value of the interestRate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the value of the interestRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setInterestRate(FieldDescriptor value) {
        this.interestRate = value;
    }

    /**
     * Gets the value of the minAdditionalFee property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getMinAdditionalFee() {
        return minAdditionalFee;
    }

    /**
     * Sets the value of the minAdditionalFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setMinAdditionalFee(FieldDescriptor value) {
        this.minAdditionalFee = value;
    }

    /**
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOperationCode(FieldDescriptor value) {
        this.operationCode = value;
    }

}
