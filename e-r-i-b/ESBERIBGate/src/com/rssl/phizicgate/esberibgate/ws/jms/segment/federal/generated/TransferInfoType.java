
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Параметры операции (ДЕПО).
 * 
 * <p>Java class for TransferInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransferInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentDate" type="{}Date"/>
 *         &lt;element name="DocumentNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="OperType" type="{}DepoOperType_Type"/>
 *         &lt;element name="OperationSubType" type="{}DepoOperationSubType_Type"/>
 *         &lt;element name="OperationDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DivisionNumber" type="{}DivisionNumber_Type"/>
 *         &lt;element name="InsideCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SecurityCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="OperationReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DetailOperationReason" type="{}DepoDetailOperationReason_Type" minOccurs="0"/>
 *         &lt;element name="TransferRcpInfo" type="{}TransferRcpInfo_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferInfo_Type", propOrder = {
    "documentDate",
    "documentNumber",
    "operType",
    "operationSubType",
    "operationDesc",
    "divisionNumber",
    "insideCode",
    "securityCount",
    "operationReason",
    "detailOperationReason",
    "transferRcpInfo"
})
public class TransferInfoType {

    @XmlElement(name = "DocumentDate", required = true)
    protected String documentDate;
    @XmlElement(name = "DocumentNumber")
    protected long documentNumber;
    @XmlElement(name = "OperType", required = true)
    protected String operType;
    @XmlElement(name = "OperationSubType", required = true)
    protected DepoOperationSubTypeType operationSubType;
    @XmlElement(name = "OperationDesc")
    protected String operationDesc;
    @XmlElement(name = "DivisionNumber", required = true)
    protected DivisionNumberType divisionNumber;
    @XmlElement(name = "InsideCode", required = true)
    protected String insideCode;
    @XmlElement(name = "SecurityCount")
    protected long securityCount;
    @XmlElement(name = "OperationReason")
    protected String operationReason;
    @XmlElement(name = "DetailOperationReason")
    protected DepoDetailOperationReasonType detailOperationReason;
    @XmlElement(name = "TransferRcpInfo", required = true)
    protected TransferRcpInfoType transferRcpInfo;

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentDate(String value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     */
    public long getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     */
    public void setDocumentNumber(long value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the operType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperType() {
        return operType;
    }

    /**
     * Sets the value of the operType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperType(String value) {
        this.operType = value;
    }

    /**
     * Gets the value of the operationSubType property.
     * 
     * @return
     *     possible object is
     *     {@link DepoOperationSubTypeType }
     *     
     */
    public DepoOperationSubTypeType getOperationSubType() {
        return operationSubType;
    }

    /**
     * Sets the value of the operationSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoOperationSubTypeType }
     *     
     */
    public void setOperationSubType(DepoOperationSubTypeType value) {
        this.operationSubType = value;
    }

    /**
     * Gets the value of the operationDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationDesc() {
        return operationDesc;
    }

    /**
     * Sets the value of the operationDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationDesc(String value) {
        this.operationDesc = value;
    }

    /**
     * Gets the value of the divisionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link DivisionNumberType }
     *     
     */
    public DivisionNumberType getDivisionNumber() {
        return divisionNumber;
    }

    /**
     * Sets the value of the divisionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link DivisionNumberType }
     *     
     */
    public void setDivisionNumber(DivisionNumberType value) {
        this.divisionNumber = value;
    }

    /**
     * Gets the value of the insideCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsideCode() {
        return insideCode;
    }

    /**
     * Sets the value of the insideCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsideCode(String value) {
        this.insideCode = value;
    }

    /**
     * Gets the value of the securityCount property.
     * 
     */
    public long getSecurityCount() {
        return securityCount;
    }

    /**
     * Sets the value of the securityCount property.
     * 
     */
    public void setSecurityCount(long value) {
        this.securityCount = value;
    }

    /**
     * Gets the value of the operationReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationReason() {
        return operationReason;
    }

    /**
     * Sets the value of the operationReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationReason(String value) {
        this.operationReason = value;
    }

    /**
     * Gets the value of the detailOperationReason property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDetailOperationReasonType }
     *     
     */
    public DepoDetailOperationReasonType getDetailOperationReason() {
        return detailOperationReason;
    }

    /**
     * Sets the value of the detailOperationReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDetailOperationReasonType }
     *     
     */
    public void setDetailOperationReason(DepoDetailOperationReasonType value) {
        this.detailOperationReason = value;
    }

    /**
     * Gets the value of the transferRcpInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TransferRcpInfoType }
     *     
     */
    public TransferRcpInfoType getTransferRcpInfo() {
        return transferRcpInfo;
    }

    /**
     * Sets the value of the transferRcpInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferRcpInfoType }
     *     
     */
    public void setTransferRcpInfo(TransferRcpInfoType value) {
        this.transferRcpInfo = value;
    }

}
