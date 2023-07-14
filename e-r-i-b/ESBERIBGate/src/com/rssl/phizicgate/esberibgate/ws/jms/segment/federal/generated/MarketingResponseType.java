
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MarketingResponse_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarketingResponse_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SourceCode" type="{}MarResSourceCode_Type" minOccurs="0"/>
 *         &lt;element name="Description" type="{}MarResDescription_Type" minOccurs="0"/>
 *         &lt;element name="Type" type="{}MarResType_Type" minOccurs="0"/>
 *         &lt;element name="Method" type="{}MarResMethod_Type"/>
 *         &lt;element name="Result" type="{}MarResResult_Type"/>
 *         &lt;element name="DetailResult" type="{}MarResDetailResult_Type"/>
 *         &lt;element name="PhoneNumber" type="{}MarResPhoneNumberInt_Type" minOccurs="0"/>
 *         &lt;element name="CampaingMemberId" type="{}MarResCampaingMemberId_Type"/>
 *         &lt;element name="ResponseDateTime" type="{}DateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarketingResponse_Type", propOrder = {
    "sourceCode",
    "description",
    "type",
    "method",
    "result",
    "detailResult",
    "phoneNumber",
    "campaingMemberId",
    "responseDateTime"
})
public class MarketingResponseType {

    @XmlElement(name = "SourceCode")
    protected String sourceCode;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "Method", required = true)
    protected String method;
    @XmlElement(name = "Result", required = true)
    protected String result;
    @XmlElement(name = "DetailResult", required = true)
    protected String detailResult;
    @XmlElement(name = "PhoneNumber")
    protected BigInteger phoneNumber;
    @XmlElement(name = "CampaingMemberId", required = true)
    protected String campaingMemberId;
    @XmlElement(name = "ResponseDateTime")
    protected String responseDateTime;

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
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the detailResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailResult() {
        return detailResult;
    }

    /**
     * Sets the value of the detailResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailResult(String value) {
        this.detailResult = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPhoneNumber(BigInteger value) {
        this.phoneNumber = value;
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
