
package com.rssl.phizic.synchronization.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Продукты клиента, по которым должны отправляться оповещения.
 *                 Отсутствие тэга принимается равнозначным пустому тэгу.
 *                 (Из списка clientResources)
 *             
 * 
 * <p>Java class for InformResourcesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InformResourcesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resource" type="{}ResourceIDType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformResourcesType", propOrder = {
    "resources"
})
public class InformResourcesType {

    @XmlElement(name = "resource")
    protected List<ResourceIDType> resources;

    /**
     * Gets the value of the resources property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resources property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResources().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceIDType }
     * 
     * 
     */
    public List<ResourceIDType> getResources() {
        if (resources == null) {
            resources = new ArrayList<ResourceIDType>();
        }
        return this.resources;
    }

}
