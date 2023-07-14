
package com.rssl.phizgate.basket.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Дополнительный реквизит
 * 
 * <p>Java class for Requisite_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Requisite_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NameVisible" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NameBS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NumberPrecision" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="IsRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsSum" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsKey" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsEditable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsVisible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsForBill" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IncludeInSMS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SaveInTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RequisiteTypes" type="{}RequisiteTypes_Type" minOccurs="0"/>
 *         &lt;element name="HideInConfirmation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AttributeLength" type="{}AttributeLength_Type" minOccurs="0"/>
 *         &lt;element name="Validators" type="{}Validators_Type" minOccurs="0"/>
 *         &lt;element name="Menu" type="{}Menu_Type" minOccurs="0"/>
 *         &lt;element name="EnteredData" type="{}EnteredData_Type" minOccurs="0"/>
 *         &lt;element name="DefaultValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Requisite_Type", propOrder = {
    "nameVisible",
    "nameBS",
    "description",
    "comment",
    "type",
    "numberPrecision",
    "isRequired",
    "isSum",
    "isKey",
    "isEditable",
    "isVisible",
    "isForBill",
    "includeInSMS",
    "saveInTemplate",
    "requisiteTypes",
    "hideInConfirmation",
    "attributeLength",
    "validators",
    "menu",
    "enteredData",
    "defaultValue",
    "error"
})
public class RequisiteType {

    @XmlElement(name = "NameVisible", required = true)
    protected String nameVisible;
    @XmlElement(name = "NameBS", required = true)
    protected String nameBS;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "NumberPrecision")
    protected BigInteger numberPrecision;
    @XmlElement(name = "IsRequired")
    protected Boolean isRequired;
    @XmlElement(name = "IsSum")
    protected Boolean isSum;
    @XmlElement(name = "IsKey")
    protected Boolean isKey;
    @XmlElement(name = "IsEditable")
    protected Boolean isEditable;
    @XmlElement(name = "IsVisible")
    protected Boolean isVisible;
    @XmlElement(name = "IsForBill")
    protected Boolean isForBill;
    @XmlElement(name = "IncludeInSMS")
    protected Boolean includeInSMS;
    @XmlElement(name = "SaveInTemplate")
    protected Boolean saveInTemplate;
    @XmlElement(name = "RequisiteTypes")
    protected RequisiteTypesType requisiteTypes;
    @XmlElement(name = "HideInConfirmation")
    protected Boolean hideInConfirmation;
    @XmlElement(name = "AttributeLength")
    protected AttributeLengthType attributeLength;
    @XmlElement(name = "Validators")
    protected ValidatorsType validators;
    @XmlElement(name = "Menu")
    protected MenuType menu;
    @XmlElement(name = "EnteredData")
    protected EnteredDataType enteredData;
    @XmlElement(name = "DefaultValue")
    protected String defaultValue;
    @XmlElement(name = "Error")
    protected String error;

    /**
     * Gets the value of the nameVisible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameVisible() {
        return nameVisible;
    }

    /**
     * Sets the value of the nameVisible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameVisible(String value) {
        this.nameVisible = value;
    }

    /**
     * Gets the value of the nameBS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameBS() {
        return nameBS;
    }

    /**
     * Sets the value of the nameBS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameBS(String value) {
        this.nameBS = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the numberPrecision property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberPrecision() {
        return numberPrecision;
    }

    /**
     * Sets the value of the numberPrecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberPrecision(BigInteger value) {
        this.numberPrecision = value;
    }

    /**
     * Gets the value of the isRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsRequired() {
        return isRequired;
    }

    /**
     * Sets the value of the isRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRequired(Boolean value) {
        this.isRequired = value;
    }

    /**
     * Gets the value of the isSum property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsSum() {
        return isSum;
    }

    /**
     * Sets the value of the isSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSum(Boolean value) {
        this.isSum = value;
    }

    /**
     * Gets the value of the isKey property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsKey() {
        return isKey;
    }

    /**
     * Sets the value of the isKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsKey(Boolean value) {
        this.isKey = value;
    }

    /**
     * Gets the value of the isEditable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsEditable() {
        return isEditable;
    }

    /**
     * Sets the value of the isEditable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEditable(Boolean value) {
        this.isEditable = value;
    }

    /**
     * Gets the value of the isVisible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * Sets the value of the isVisible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVisible(Boolean value) {
        this.isVisible = value;
    }

    /**
     * Gets the value of the isForBill property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsForBill() {
        return isForBill;
    }

    /**
     * Sets the value of the isForBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsForBill(Boolean value) {
        this.isForBill = value;
    }

    /**
     * Gets the value of the includeInSMS property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIncludeInSMS() {
        return includeInSMS;
    }

    /**
     * Sets the value of the includeInSMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeInSMS(Boolean value) {
        this.includeInSMS = value;
    }

    /**
     * Gets the value of the saveInTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getSaveInTemplate() {
        return saveInTemplate;
    }

    /**
     * Sets the value of the saveInTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSaveInTemplate(Boolean value) {
        this.saveInTemplate = value;
    }

    /**
     * Gets the value of the requisiteTypes property.
     * 
     * @return
     *     possible object is
     *     {@link RequisiteTypesType }
     *     
     */
    public RequisiteTypesType getRequisiteTypes() {
        return requisiteTypes;
    }

    /**
     * Sets the value of the requisiteTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequisiteTypesType }
     *     
     */
    public void setRequisiteTypes(RequisiteTypesType value) {
        this.requisiteTypes = value;
    }

    /**
     * Gets the value of the hideInConfirmation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getHideInConfirmation() {
        return hideInConfirmation;
    }

    /**
     * Sets the value of the hideInConfirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHideInConfirmation(Boolean value) {
        this.hideInConfirmation = value;
    }

    /**
     * Gets the value of the attributeLength property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeLengthType }
     *     
     */
    public AttributeLengthType getAttributeLength() {
        return attributeLength;
    }

    /**
     * Sets the value of the attributeLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeLengthType }
     *     
     */
    public void setAttributeLength(AttributeLengthType value) {
        this.attributeLength = value;
    }

    /**
     * Gets the value of the validators property.
     * 
     * @return
     *     possible object is
     *     {@link ValidatorsType }
     *     
     */
    public ValidatorsType getValidators() {
        return validators;
    }

    /**
     * Sets the value of the validators property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidatorsType }
     *     
     */
    public void setValidators(ValidatorsType value) {
        this.validators = value;
    }

    /**
     * Gets the value of the menu property.
     * 
     * @return
     *     possible object is
     *     {@link MenuType }
     *     
     */
    public MenuType getMenu() {
        return menu;
    }

    /**
     * Sets the value of the menu property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuType }
     *     
     */
    public void setMenu(MenuType value) {
        this.menu = value;
    }

    /**
     * Gets the value of the enteredData property.
     * 
     * @return
     *     possible object is
     *     {@link EnteredDataType }
     *     
     */
    public EnteredDataType getEnteredData() {
        return enteredData;
    }

    /**
     * Sets the value of the enteredData property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnteredDataType }
     *     
     */
    public void setEnteredData(EnteredDataType value) {
        this.enteredData = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

}
