
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список значений для выбора клиентом
 * 
 * <p>Java class for Menu_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Menu_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MenuItem" type="{}String" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Menu_Type", propOrder = {
    "menuItems"
})
@XmlRootElement(name = "Menu")
public class Menu {

    @XmlElement(name = "MenuItem")
    protected List<String> menuItems;

    /**
     * Gets the value of the menuItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMenuItems() {
        if (menuItems == null) {
            menuItems = new ArrayList<String>();
        }
        return this.menuItems;
    }

}
