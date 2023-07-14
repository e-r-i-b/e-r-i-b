
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Инфомрация по продуктам компании
 * 
 * <p>Java class for InternalProduct_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternalProduct_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductId" type="{}ProductId_Type" minOccurs="0"/>
 *         &lt;element name="ProductName" type="{}ProductName_Type" minOccurs="0"/>
 *         &lt;element name="ProductType" type="{}ProductType_Type" minOccurs="0"/>
 *         &lt;element name="TargetProductType" type="{}TargetProductType_Type" minOccurs="0"/>
 *         &lt;element name="TargetProduct" type="{}TargetProduct_Type" minOccurs="0"/>
 *         &lt;element name="TargetProductSub" type="{}TargetProductSub_Type" minOccurs="0"/>
 *         &lt;element name="TSMConture" type="{}TSMConture_Type" minOccurs="0"/>
 *         &lt;element name="Priority" type="{}Priority_Type" minOccurs="0"/>
 *         &lt;element name="PrimaryFlag" type="{}PrimaryFlag_Type" minOccurs="0"/>
 *         &lt;element name="MainProductId" type="{}MainProductId_Type" minOccurs="0"/>
 *         &lt;element name="ExpDate" type="{}DateTime" minOccurs="0"/>
 *         &lt;element name="CurCode" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element ref="{}ProposalParameters" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternalProduct_Type", propOrder = {
    "productId",
    "productName",
    "productType",
    "targetProductType",
    "targetProduct",
    "targetProductSub",
    "tsmConture",
    "priority",
    "primaryFlag",
    "mainProductId",
    "expDate",
    "curCode",
    "proposalParameters"
})
@XmlRootElement(name = "InternalProduct")
public class InternalProduct {

    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "ProductName")
    protected String productName;
    @XmlElement(name = "ProductType")
    protected String productType;
    @XmlElement(name = "TargetProductType")
    protected String targetProductType;
    @XmlElement(name = "TargetProduct")
    protected String targetProduct;
    @XmlElement(name = "TargetProductSub")
    protected String targetProductSub;
    @XmlElement(name = "TSMConture")
    protected TSMContureType tsmConture;
    @XmlElement(name = "Priority")
    protected String priority;
    @XmlElement(name = "PrimaryFlag")
    protected String primaryFlag;
    @XmlElement(name = "MainProductId")
    protected String mainProductId;
    @XmlElement(name = "ExpDate")
    protected String expDate;
    @XmlElement(name = "CurCode")
    protected String curCode;
    @XmlElement(name = "ProposalParameters")
    protected ProposalParameters proposalParameters;

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
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
     * Gets the value of the targetProductType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetProductType() {
        return targetProductType;
    }

    /**
     * Sets the value of the targetProductType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetProductType(String value) {
        this.targetProductType = value;
    }

    /**
     * Gets the value of the targetProduct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetProduct() {
        return targetProduct;
    }

    /**
     * Sets the value of the targetProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetProduct(String value) {
        this.targetProduct = value;
    }

    /**
     * Gets the value of the targetProductSub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetProductSub() {
        return targetProductSub;
    }

    /**
     * Sets the value of the targetProductSub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetProductSub(String value) {
        this.targetProductSub = value;
    }

    /**
     * Gets the value of the tsmConture property.
     * 
     * @return
     *     possible object is
     *     {@link TSMContureType }
     *     
     */
    public TSMContureType getTSMConture() {
        return tsmConture;
    }

    /**
     * Sets the value of the tsmConture property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSMContureType }
     *     
     */
    public void setTSMConture(TSMContureType value) {
        this.tsmConture = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriority(String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the primaryFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryFlag() {
        return primaryFlag;
    }

    /**
     * Sets the value of the primaryFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryFlag(String value) {
        this.primaryFlag = value;
    }

    /**
     * Gets the value of the mainProductId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainProductId() {
        return mainProductId;
    }

    /**
     * Sets the value of the mainProductId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainProductId(String value) {
        this.mainProductId = value;
    }

    /**
     * Gets the value of the expDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * Sets the value of the expDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDate(String value) {
        this.expDate = value;
    }

    /**
     * Gets the value of the curCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurCode() {
        return curCode;
    }

    /**
     * Sets the value of the curCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurCode(String value) {
        this.curCode = value;
    }

    /**
     * Gets the value of the proposalParameters property.
     * 
     * @return
     *     possible object is
     *     {@link ProposalParameters }
     *     
     */
    public ProposalParameters getProposalParameters() {
        return proposalParameters;
    }

    /**
     * Sets the value of the proposalParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProposalParameters }
     *     
     */
    public void setProposalParameters(ProposalParameters value) {
        this.proposalParameters = value;
    }

}
