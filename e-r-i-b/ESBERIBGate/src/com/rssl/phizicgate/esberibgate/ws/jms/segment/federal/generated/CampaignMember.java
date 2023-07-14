
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Данные по участнику
 * 
 * <p>Java class for CampaignMember_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CampaignMember_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonInfo" type="{}PersonInfoSec_Type" minOccurs="0"/>
 *         &lt;element name="CampaignMemberId" type="{}CampaignMemberId_Type" minOccurs="0"/>
 *         &lt;element name="OfferDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="CampaignName" type="{}CampaignName_Type" minOccurs="0"/>
 *         &lt;element name="CampaignId" type="{}CampaignId_Type" minOccurs="0"/>
 *         &lt;element name="SourceCode" type="{}SourceCode_Type" minOccurs="0"/>
 *         &lt;element name="ProdType" type="{}ProdType2_Type" minOccurs="0"/>
 *         &lt;element name="SourceName" type="{}SourceName_Type" minOccurs="0"/>
 *         &lt;element name="ProductASName" type="{}ProductASName_Type" minOccurs="0"/>
 *         &lt;element name="ProductASPriority" type="{}ProductASPriority_Type" minOccurs="0"/>
 *         &lt;element name="PersonalText" type="{}PersonalText_Type" minOccurs="0"/>
 *         &lt;element name="MediaType" type="{}MediaType_Type" minOccurs="0"/>
 *         &lt;element name="ClientId" type="{}ClientId_Type" minOccurs="0"/>
 *         &lt;element name="ClientSegment" type="{}ClientSegment_Type" minOccurs="0"/>
 *         &lt;element ref="{}CardInfo" minOccurs="0"/>
 *         &lt;element ref="{}PayrollAgree" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PrintingAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="BackgroundId" type="{}BackgroundId_Type" minOccurs="0"/>
 *         &lt;element name="SearchSpec" type="{}SearchSpec_Type" minOccurs="0"/>
 *         &lt;element ref="{}InternalProduct" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="TreatmentType" type="{}TreatmentType_Type" minOccurs="0"/>
 *         &lt;element name="TopUp" type="{}TopUp_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampaignMember_Type", propOrder = {
    "personInfo",
    "campaignMemberId",
    "offerDate",
    "campaignName",
    "campaignId",
    "sourceCode",
    "prodType",
    "sourceName",
    "productASName",
    "productASPriority",
    "personalText",
    "mediaType",
    "clientId",
    "clientSegment",
    "cardInfo",
    "payrollAgrees",
    "printingAllowed",
    "backgroundId",
    "searchSpec",
    "internalProducts",
    "bankInfo",
    "treatmentType",
    "topUp"
})
@XmlRootElement(name = "CampaignMember")
public class CampaignMember {

    @XmlElement(name = "PersonInfo")
    protected PersonInfoSecType personInfo;
    @XmlElement(name = "CampaignMemberId")
    protected String campaignMemberId;
    @XmlElement(name = "OfferDate")
    protected String offerDate;
    @XmlElement(name = "CampaignName")
    protected String campaignName;
    @XmlElement(name = "CampaignId")
    protected String campaignId;
    @XmlElement(name = "SourceCode")
    protected String sourceCode;
    @XmlElement(name = "ProdType")
    protected String prodType;
    @XmlElement(name = "SourceName")
    protected String sourceName;
    @XmlElement(name = "ProductASName")
    protected String productASName;
    @XmlElement(name = "ProductASPriority")
    protected BigInteger productASPriority;
    @XmlElement(name = "PersonalText")
    protected String personalText;
    @XmlElement(name = "MediaType")
    protected String mediaType;
    @XmlElement(name = "ClientId")
    protected String clientId;
    @XmlElement(name = "ClientSegment")
    protected String clientSegment;
    @XmlElement(name = "CardInfo")
    protected CardInfo cardInfo;
    @XmlElement(name = "PayrollAgree")
    protected List<PayrollAgree> payrollAgrees;
    @XmlElement(name = "PrintingAllowed")
    protected Boolean printingAllowed;
    @XmlElement(name = "BackgroundId")
    protected String backgroundId;
    @XmlElement(name = "SearchSpec")
    protected String searchSpec;
    @XmlElement(name = "InternalProduct")
    protected List<InternalProduct> internalProducts;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;
    @XmlElement(name = "TreatmentType")
    protected TreatmentTypeType treatmentType;
    @XmlElement(name = "TopUp")
    protected TopUpType topUp;

    /**
     * Gets the value of the personInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInfoSecType }
     *     
     */
    public PersonInfoSecType getPersonInfo() {
        return personInfo;
    }

    /**
     * Sets the value of the personInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInfoSecType }
     *     
     */
    public void setPersonInfo(PersonInfoSecType value) {
        this.personInfo = value;
    }

    /**
     * Gets the value of the campaignMemberId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignMemberId() {
        return campaignMemberId;
    }

    /**
     * Sets the value of the campaignMemberId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignMemberId(String value) {
        this.campaignMemberId = value;
    }

    /**
     * Gets the value of the offerDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferDate() {
        return offerDate;
    }

    /**
     * Sets the value of the offerDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferDate(String value) {
        this.offerDate = value;
    }

    /**
     * Gets the value of the campaignName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignName() {
        return campaignName;
    }

    /**
     * Sets the value of the campaignName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignName(String value) {
        this.campaignName = value;
    }

    /**
     * Gets the value of the campaignId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignId() {
        return campaignId;
    }

    /**
     * Sets the value of the campaignId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignId(String value) {
        this.campaignId = value;
    }

    /**
     * Gets the value of the sourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * Sets the value of the sourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceCode(String value) {
        this.sourceCode = value;
    }

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
    }

    /**
     * Gets the value of the sourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Sets the value of the sourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceName(String value) {
        this.sourceName = value;
    }

    /**
     * Gets the value of the productASName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductASName() {
        return productASName;
    }

    /**
     * Sets the value of the productASName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductASName(String value) {
        this.productASName = value;
    }

    /**
     * Gets the value of the productASPriority property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProductASPriority() {
        return productASPriority;
    }

    /**
     * Sets the value of the productASPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProductASPriority(BigInteger value) {
        this.productASPriority = value;
    }

    /**
     * Gets the value of the personalText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalText() {
        return personalText;
    }

    /**
     * Sets the value of the personalText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalText(String value) {
        this.personalText = value;
    }

    /**
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the clientSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSegment() {
        return clientSegment;
    }

    /**
     * Sets the value of the clientSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSegment(String value) {
        this.clientSegment = value;
    }

    /**
     * Gets the value of the cardInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CardInfo }
     *     
     */
    public CardInfo getCardInfo() {
        return cardInfo;
    }

    /**
     * Sets the value of the cardInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardInfo }
     *     
     */
    public void setCardInfo(CardInfo value) {
        this.cardInfo = value;
    }

    /**
     * Gets the value of the payrollAgrees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payrollAgrees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayrollAgrees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PayrollAgree }
     * 
     * 
     */
    public List<PayrollAgree> getPayrollAgrees() {
        if (payrollAgrees == null) {
            payrollAgrees = new ArrayList<PayrollAgree>();
        }
        return this.payrollAgrees;
    }

    /**
     * Gets the value of the printingAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getPrintingAllowed() {
        return printingAllowed;
    }

    /**
     * Sets the value of the printingAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrintingAllowed(Boolean value) {
        this.printingAllowed = value;
    }

    /**
     * Gets the value of the backgroundId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundId() {
        return backgroundId;
    }

    /**
     * Sets the value of the backgroundId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundId(String value) {
        this.backgroundId = value;
    }

    /**
     * Gets the value of the searchSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchSpec() {
        return searchSpec;
    }

    /**
     * Sets the value of the searchSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchSpec(String value) {
        this.searchSpec = value;
    }

    /**
     * Gets the value of the internalProducts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the internalProducts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInternalProducts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InternalProduct }
     * 
     * 
     */
    public List<InternalProduct> getInternalProducts() {
        if (internalProducts == null) {
            internalProducts = new ArrayList<InternalProduct>();
        }
        return this.internalProducts;
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
     * Gets the value of the treatmentType property.
     * 
     * @return
     *     possible object is
     *     {@link TreatmentTypeType }
     *     
     */
    public TreatmentTypeType getTreatmentType() {
        return treatmentType;
    }

    /**
     * Sets the value of the treatmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreatmentTypeType }
     *     
     */
    public void setTreatmentType(TreatmentTypeType value) {
        this.treatmentType = value;
    }

    /**
     * Gets the value of the topUp property.
     * 
     * @return
     *     possible object is
     *     {@link TopUpType }
     *     
     */
    public TopUpType getTopUp() {
        return topUp;
    }

    /**
     * Sets the value of the topUp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TopUpType }
     *     
     */
    public void setTopUp(TopUpType value) {
        this.topUp = value;
    }

}
