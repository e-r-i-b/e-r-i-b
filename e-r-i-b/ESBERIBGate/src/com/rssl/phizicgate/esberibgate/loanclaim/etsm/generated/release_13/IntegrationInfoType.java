
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Идентификаторы клиента в ИС <IntegrationInfo>
 * 
 * <p>Java class for IntegrationInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntegrationInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IntegrationId" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ISCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ISCustId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "IntegrationInfo_Type", propOrder = {
    "integrationIds"
})
public class IntegrationInfoType {

    @XmlElement(name = "IntegrationId", required = true)
    protected List<IntegrationInfoType.IntegrationId> integrationIds;

    /**
     * Gets the value of the integrationIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integrationIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntegrationIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntegrationInfoType.IntegrationId }
     * 
     * 
     */
    public List<IntegrationInfoType.IntegrationId> getIntegrationIds() {
        if (integrationIds == null) {
            integrationIds = new ArrayList<IntegrationInfoType.IntegrationId>();
        }
        return this.integrationIds;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ISCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ISCustId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isCode",
        "isCustId"
    })
    public static class IntegrationId {

        @XmlElement(name = "ISCode", required = true)
        protected String isCode;
        @XmlElement(name = "ISCustId", required = true)
        protected String isCustId;

        /**
         * Gets the value of the isCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getISCode() {
            return isCode;
        }

        /**
         * Sets the value of the isCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setISCode(String value) {
            this.isCode = value;
        }

        /**
         * Gets the value of the isCustId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getISCustId() {
            return isCustId;
        }

        /**
         * Sets the value of the isCustId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setISCustId(String value) {
            this.isCustId = value;
        }

    }

}
