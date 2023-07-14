<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/service/nodes">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fields" value="${form.fields}"/>
    <c:set var="availableEdit" value="${phiz:impliesOperation('EditNodesAvailabilityInfoOperation', 'EditNodesAvailabilityInfoManagement')}"/>
    <c:set var="availableKick" value="${phiz:impliesOperation('KickClientsOperation', 'KickClientsManagement')}"/>

    <tiles:insert definition="nodesAvailabilityInfo">
        <tiles:put name="pageTitle"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.title"/></tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="nodesAvailabilityInfo"/>

                <tiles:put name="buttons">
                    <c:if test="${availableKick}">
                        <script type="text/javascript">
                            function validateKick()
                            {
                                checkIfOneItem("id");
                                return checkSelection("id", '<bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.select.any.message"/>') &&
                                        checkOneSelection("id", '<bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.select.one.message"/>')

                            }
                        </script>
                    </c:if>
                    <tiles:insert definition="clientButton" flush="false" operation="EditNodesAvailabilityInfoOperation">
                        <tiles:put name="commandTextKey"    value="form.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="form.button.cancel.help"/>
                        <tiles:put name="bundle"            value="nodesAvailabilityInfoBundle"/>
                        <tiles:put name="action"            value="/service/nodes"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditNodesAvailabilityInfoOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="form.button.save"/>
                        <tiles:put name="commandHelpKey"     value="form.button.save.help"/>
                        <tiles:put name="bundle"             value="nodesAvailabilityInfoBundle"/>
                        <tiles:put name="isDefault"          value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="KickClientsOperation">
                        <tiles:put name="commandKey"         value="button.kick"/>
                        <tiles:put name="commandTextKey"     value="form.button.kick"/>
                        <tiles:put name="commandHelpKey"     value="form.button.kick.help"/>
                        <tiles:put name="bundle"             value="nodesAvailabilityInfoBundle"/>
                        <tiles:put name="isDefault"          value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="validateKick();"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="head">
                    <th class="selector">&nbsp;</th>
                    <th class="nodeName"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.name"/></th>
                    <th class="nodeExistingUsersAllowed"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.allowed.users.existing"/></th>
                    <th class="nodeNewUsersAllowed"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.allowed.users.new"/></th>
                    <th class="nodeTemporaryUsersAllowed"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.allowed.users.temporary"/></th>
                    <th class="nodeTransferUsersAllowed"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.allowed.users.transfer"/></th>
                    <th class="nodeAdminAvailable"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.admin.available"/></th>
                    <th class="nodeGuestAvailable"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.title.guest.available"/></th>
                </tiles:put>

                <tiles:put name="data">
                    <c:forEach items="${form.nodeIds}" var="id">
                        <tr>
                            <td class="selector">
                                <html:checkbox property="id" value="${id}" disabled="${not availableKick}"/>
                            </td>
                            <td class="nodeName">
                                <html:hidden property="nodeIds" value="${id}"/>
                                <html:hidden property="fields(name_${id})"/>
                                <c:set var="nameNodeFieldName" value="name_${id}"/>
                                <c:out value="${form.fields[nameNodeFieldName]}"/>
                            </td>
                            <td class="nodeExistingUsersAllowed">
                                <html:checkbox property="fields(existingUsersAllowed_${id})" disabled="${not availableEdit}"/>
                            </td>
                            <td class="nodeNewUsersAllowed">
                                <html:checkbox property="fields(newUsersAllowed_${id})" disabled="${not availableEdit}"/>
                            </td>
                            <td class="nodeTemporaryUsersAllowed">
                                <html:checkbox property="fields(temporaryUsersAllowed_${id})" disabled="${not availableEdit}"/>
                            </td>
                            <td class="nodeTransferUsersAllowed">
                                <html:checkbox property="fields(usersTransferAllowed_${id})" disabled="${not availableEdit}"/>
                            </td>
                            <td class="nodeAdminAvailable">
                                <html:checkbox property="fields(adminAvailable_${id})" disabled="${not availableEdit}"/>
                            </td>
                            <td class="nodeGuestAvailable">
                                <html:checkbox property="fields(guestAvailable_${id})" disabled="${not availableEdit}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tiles:put>
                <tiles:put name="emptyMessage"><bean:message bundle="nodesAvailabilityInfoBundle" key="form.table.empty"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>