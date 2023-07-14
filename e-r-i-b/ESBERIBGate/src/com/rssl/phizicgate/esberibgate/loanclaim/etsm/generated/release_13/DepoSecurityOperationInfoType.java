
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Параметры операции с ценной бумагой (ДЕПО).
 * 
 * <p>Java class for DepoSecurityOperationInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoSecurityOperationInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DocumentNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="SecurityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SecurityNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Issuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Depositary" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InsideCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Operations" type="{}DepoSecurityOperationList_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoSecurityOperationInfo_Type", propOrder = {
    "documentDate",
    "documentNumber",
    "securityName",
    "securityNumber",
    "issuer",
    "depositary",
    "insideCode",
    "operations"
})
public class DepoSecurityOperationInfoType {

    @XmlElement(name = "DocumentDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar documentDate;
    @XmlElement(name = "DocumentNumber")
    protected long documentNumber;
    @XmlElement(name = "SecurityName", required = true)
    protected String securityName;
    @XmlElement(name = "SecurityNumber", required = true)
    protected String securityNumber;
    @XmlElement(name = "Issuer", required = true)
    protected String issuer;
    @XmlElement(name = "Depositary", required = true)
    protected String depositary;
    @XmlElement(name = "InsideCode")
    protected String insideCode;
    @XmlElement(name = "Operations", required = true)
    protected List<DepoSecurityOperationListType> operations;

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDocumentDate() {
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
    public void setDocumentDate(Calendar value) {
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
     * Gets the value of the securityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityName() {
        return securityName;
    }

    /**
     * Sets the value of the securityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityName(String value) {
        this.securityName = value;
    }

    /**
     * Gets the value of the securityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityNumber() {
        return securityNumber;
    }

    /**
     * Sets the value of the securityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityNumber(String value) {
        this.securityNumber = value;
    }

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuer(String value) {
        this.issuer = value;
    }

    /**
     * Gets the value of the depositary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositary() {
        return depositary;
    }

    /**
     * Sets the value of the depositary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositary(String value) {
        this.depositary = value;
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
     * Gets the value of the operations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepoSecurityOperationListType }
     * 
     * 
     */
    public List<DepoSecurityOperationListType> getOperations() {
        if (operations == null) {
            operations = new ArrayList<DepoSecurityOperationListType>();
        }
        return this.operations;
    }

}
