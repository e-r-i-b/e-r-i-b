
package com.rssl.phizic.synchronization.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список  удалённых профилей
 * 
 * <p>Java class for DeleteProfilesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteProfilesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="profile" type="{}DeleteProfileType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteProfilesType", propOrder = {
    "profiles"
})
public class DeleteProfilesType {

    @XmlElement(name = "profile", required = true)
    protected List<DeleteProfileType> profiles;

    /**
     * Gets the value of the profiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeleteProfileType }
     * 
     * 
     */
    public List<DeleteProfileType> getProfiles() {
        if (profiles == null) {
            profiles = new ArrayList<DeleteProfileType>();
        }
        return this.profiles;
    }

}
