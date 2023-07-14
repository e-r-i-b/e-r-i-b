
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Связанный документ
 * 
 * <p>Java class for SecuritiesDocument_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesDocument_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocType">
 *           &lt;simpleType>
 *             &lt;restriction base="{}C">
 *               &lt;maxLength value="36"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DocNum">
 *           &lt;simpleType>
 *             &lt;restriction base="{}C">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DocDt" type="{}Date"/>
 *         &lt;element name="Annotation" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}C">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Status" type="{}Identifier" minOccurs="0"/>
 *         &lt;element name="Client" type="{}SecuritiesClient_Type" minOccurs="0"/>
 *         &lt;element name="Registrar" type="{}Owner_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesDocument_Type", propOrder = {
    "docType",
    "docNum",
    "docDt",
    "annotation",
    "status",
    "client",
    "registrar"
})
public class SecuritiesDocumentType {

    @XmlElement(name = "DocType", required = true)
    protected String docType;
    @XmlElement(name = "DocNum", required = true)
    protected String docNum;
    @XmlElement(name = "DocDt", required = true)
    protected String docDt;
    @XmlElement(name = "Annotation")
    protected String annotation;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Client")
    protected SecuritiesClientType client;
    @XmlElement(name = "Registrar")
    protected OwnerType registrar;

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the docNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Sets the value of the docNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNum(String value) {
        this.docNum = value;
    }

    /**
     * Gets the value of the docDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocDt() {
        return docDt;
    }

    /**
     * Sets the value of the docDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocDt(String value) {
        this.docDt = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
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
     * Gets the value of the client property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesClientType }
     *     
     */
    public SecuritiesClientType getClient() {
        return client;
    }

    /**
     * Sets the value of the client property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesClientType }
     *     
     */
    public void setClient(SecuritiesClientType value) {
        this.client = value;
    }

    /**
     * Gets the value of the registrar property.
     * 
     * @return
     *     possible object is
     *     {@link OwnerType }
     *     
     */
    public OwnerType getRegistrar() {
        return registrar;
    }

    /**
     * Sets the value of the registrar property.
     * 
     * @param value
     *     allowed object is
     *     {@link OwnerType }
     *     
     */
    public void setRegistrar(OwnerType value) {
        this.registrar = value;
    }

}
