
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип заявка, отправляемая из ЕРИБ в CRM
 * 
 * <p>Java class for ApplicationCRM_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationCRM_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Number" type="{}Number_Type"/>
 *         &lt;element name="Source" type="{}Source_Type"/>
 *         &lt;element name="Type" type="{}Type_Type"/>
 *         &lt;element name="CreatedByCCLogin" type="{}CreatedByLogin_Type" minOccurs="0"/>
 *         &lt;element name="CreatedByCCFullName" type="{}CreatedByFullName_Type" minOccurs="0"/>
 *         &lt;element name="LastName" type="{}LastName_Type"/>
 *         &lt;element name="FirstName" type="{}FirstName_Type"/>
 *         &lt;element name="MiddleName" type="{}MiddleName_Type" minOccurs="0"/>
 *         &lt;element name="BirthDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="PassportNum" type="{}PassportNum_Type"/>
 *         &lt;element name="WayCardId" type="{}WayCardId_Type" minOccurs="0"/>
 *         &lt;element name="CampaingMemberId" type="{}CampaingMemberId_Type" minOccurs="0"/>
 *         &lt;element name="WorkPhone" type="{}WorkPhone_Type" minOccurs="0"/>
 *         &lt;element name="CellPhone" type="{}CellPhone_Type"/>
 *         &lt;element name="AddPhone" type="{}AddPhone_Type" minOccurs="0"/>
 *         &lt;element name="ProductName" type="{}ProductName_Type" minOccurs="0"/>
 *         &lt;element name="TargetProductType" type="{}TargetProductType_Type" minOccurs="0"/>
 *         &lt;element name="TargetProduct" type="{}TargetProduct_Type" minOccurs="0"/>
 *         &lt;element name="ProdSubType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetProductSub" type="{}TargetProductSub_Type" minOccurs="0"/>
 *         &lt;element name="CreditCurrency" type="{}CreditCurrency_Type" minOccurs="0"/>
 *         &lt;element name="CreditAmount" type="{}CreditAmount_Type" minOccurs="0"/>
 *         &lt;element name="CreditPeriod" type="{}CreditPeriod_Type" minOccurs="0"/>
 *         &lt;element name="CreditRate" type="{}CreditRate_Type" minOccurs="0"/>
 *         &lt;element name="Comments" type="{}Comments_Type" minOccurs="0"/>
 *         &lt;element name="DivisionId" type="{}DivisionId_Type"/>
 *         &lt;element name="PlannedVisitDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="PlannedVisitTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TMCallback" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrefferedCallTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PreferredCallDate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="([0][1-9]|[1][0-2])[/]([0][1-9]|[1-2][0-9]|[3][0-1])[/]([0-9]){4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ResponseDateTime" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="([0][1-9]|[1][0-2])[/]([0][1-9]|[1-2][0-9]|[3][0-1])[/]([0-9]{4})\s([0-1][0-9]|[2][0-3])(:[0-5][0-9]){2}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlType(name = "ApplicationCRM_Type", propOrder = {
    "number",
    "source",
    "type",
    "createdByCCLogin",
    "createdByCCFullName",
    "lastName",
    "firstName",
    "middleName",
    "birthDate",
    "passportNum",
    "wayCardId",
    "campaingMemberId",
    "workPhone",
    "cellPhone",
    "addPhone",
    "productName",
    "targetProductType",
    "targetProduct",
    "prodSubType",
    "targetProductSub",
    "creditCurrency",
    "creditAmount",
    "creditPeriod",
    "creditRate",
    "comments",
    "divisionId",
    "plannedVisitDate",
    "plannedVisitTime",
    "tmCallback",
    "prefferedCallTime",
    "preferredCallDate",
    "responseDateTime"
})
public class ApplicationCRMType {

    @XmlElement(name = "Number", required = true)
    protected String number;
    @XmlElement(name = "Source", required = true)
    protected String source;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "CreatedByCCLogin")
    protected String createdByCCLogin;
    @XmlElement(name = "CreatedByCCFullName")
    protected String createdByCCFullName;
    @XmlElement(name = "LastName", required = true)
    protected String lastName;
    @XmlElement(name = "FirstName", required = true)
    protected String firstName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "BirthDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar birthDate;
    @XmlElement(name = "PassportNum", required = true)
    protected String passportNum;
    @XmlElement(name = "WayCardId")
    protected String wayCardId;
    @XmlElement(name = "CampaingMemberId")
    protected String campaingMemberId;
    @XmlElement(name = "WorkPhone")
    protected String workPhone;
    @XmlElement(name = "CellPhone", required = true)
    protected String cellPhone;
    @XmlElement(name = "AddPhone")
    protected String addPhone;
    @XmlElement(name = "ProductName")
    protected String productName;
    @XmlElement(name = "TargetProductType")
    protected String targetProductType;
    @XmlElement(name = "TargetProduct")
    protected String targetProduct;
    @XmlElement(name = "ProdSubType", required = true)
    protected String prodSubType;
    @XmlElement(name = "TargetProductSub")
    protected String targetProductSub;
    @XmlElement(name = "CreditCurrency")
    protected String creditCurrency;
    @XmlElement(name = "CreditAmount")
    protected Long creditAmount;
    @XmlElement(name = "CreditPeriod")
    protected Integer creditPeriod;
    @XmlElement(name = "CreditRate")
    protected BigDecimal creditRate;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "DivisionId", required = true)
    protected String divisionId;
    @XmlElement(name = "PlannedVisitDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar plannedVisitDate;
    @XmlElement(name = "PlannedVisitTime")
    protected Integer plannedVisitTime;
    @XmlElement(name = "TMCallback")
    protected String tmCallback;
    @XmlElement(name = "PrefferedCallTime")
    protected String prefferedCallTime;
    @XmlElement(name = "PreferredCallDate")
    protected String preferredCallDate;
    @XmlElement(name = "ResponseDateTime")
    protected String responseDateTime;

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
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
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
     * Gets the value of the createdByCCLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedByCCLogin() {
        return createdByCCLogin;
    }

    /**
     * Sets the value of the createdByCCLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedByCCLogin(String value) {
        this.createdByCCLogin = value;
    }

    /**
     * Gets the value of the createdByCCFullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedByCCFullName() {
        return createdByCCFullName;
    }

    /**
     * Sets the value of the createdByCCFullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedByCCFullName(String value) {
        this.createdByCCFullName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthDate(Calendar value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the passportNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportNum() {
        return passportNum;
    }

    /**
     * Sets the value of the passportNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportNum(String value) {
        this.passportNum = value;
    }

    /**
     * Gets the value of the wayCardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWayCardId() {
        return wayCardId;
    }

    /**
     * Sets the value of the wayCardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWayCardId(String value) {
        this.wayCardId = value;
    }

    /**
     * Gets the value of the campaingMemberId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaingMemberId() {
        return campaingMemberId;
    }

    /**
     * Sets the value of the campaingMemberId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaingMemberId(String value) {
        this.campaingMemberId = value;
    }

    /**
     * Gets the value of the workPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the value of the workPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkPhone(String value) {
        this.workPhone = value;
    }

    /**
     * Gets the value of the cellPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * Sets the value of the cellPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellPhone(String value) {
        this.cellPhone = value;
    }

    /**
     * Gets the value of the addPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddPhone() {
        return addPhone;
    }

    /**
     * Sets the value of the addPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddPhone(String value) {
        this.addPhone = value;
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
     * Gets the value of the prodSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdSubType() {
        return prodSubType;
    }

    /**
     * Sets the value of the prodSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdSubType(String value) {
        this.prodSubType = value;
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
     * Gets the value of the creditCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCurrency() {
        return creditCurrency;
    }

    /**
     * Sets the value of the creditCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCurrency(String value) {
        this.creditCurrency = value;
    }

    /**
     * Gets the value of the creditAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreditAmount() {
        return creditAmount;
    }

    /**
     * Sets the value of the creditAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreditAmount(Long value) {
        this.creditAmount = value;
    }

    /**
     * Gets the value of the creditPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    /**
     * Sets the value of the creditPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCreditPeriod(Integer value) {
        this.creditPeriod = value;
    }

    /**
     * Gets the value of the creditRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditRate() {
        return creditRate;
    }

    /**
     * Sets the value of the creditRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditRate(BigDecimal value) {
        this.creditRate = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the divisionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the value of the divisionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivisionId(String value) {
        this.divisionId = value;
    }

    /**
     * Gets the value of the plannedVisitDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPlannedVisitDate() {
        return plannedVisitDate;
    }

    /**
     * Sets the value of the plannedVisitDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlannedVisitDate(Calendar value) {
        this.plannedVisitDate = value;
    }

    /**
     * Gets the value of the plannedVisitTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPlannedVisitTime() {
        return plannedVisitTime;
    }

    /**
     * Sets the value of the plannedVisitTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPlannedVisitTime(Integer value) {
        this.plannedVisitTime = value;
    }

    /**
     * Gets the value of the tmCallback property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTMCallback() {
        return tmCallback;
    }

    /**
     * Sets the value of the tmCallback property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTMCallback(String value) {
        this.tmCallback = value;
    }

    /**
     * Gets the value of the prefferedCallTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefferedCallTime() {
        return prefferedCallTime;
    }

    /**
     * Sets the value of the prefferedCallTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefferedCallTime(String value) {
        this.prefferedCallTime = value;
    }

    /**
     * Gets the value of the preferredCallDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredCallDate() {
        return preferredCallDate;
    }

    /**
     * Sets the value of the preferredCallDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredCallDate(String value) {
        this.preferredCallDate = value;
    }

    /**
     * Gets the value of the responseDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseDateTime() {
        return responseDateTime;
    }

    /**
     * Sets the value of the responseDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseDateTime(String value) {
        this.responseDateTime = value;
    }

}
