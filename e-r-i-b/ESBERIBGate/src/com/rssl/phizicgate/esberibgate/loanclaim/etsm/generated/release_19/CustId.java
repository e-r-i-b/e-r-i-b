
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Агрегат <CustId> используется для уникальной идентификации потребителя, пославшего запрос. Элемент <SPName> указывает на ППУ, который присвоил потребителю постоянный идентификатор.
 * 
 * <p>Java class for CustId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SPName" minOccurs="0"/>
 *         &lt;element ref="{}CustPermId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustId_Type", propOrder = {
    "spName",
    "custPermId"
})
@XmlRootElement(name = "CustId")
public class CustId {

    @XmlElement(name = "SPName")
    protected SPNameType spName;
    @XmlElement(name = "CustPermId", required = true)
    protected String custPermId;

    /**
     * Название ПУ, присвоившего <CustPermId>.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Постоянный идентификатор потребителя. Используется как ключ БД для уникальной идентификации потребителя. Не может быть изменен потребителем.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustPermId() {
        return custPermId;
    }

    /**
     * Sets the value of the custPermId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustPermId(String value) {
        this.custPermId = value;
    }

}
