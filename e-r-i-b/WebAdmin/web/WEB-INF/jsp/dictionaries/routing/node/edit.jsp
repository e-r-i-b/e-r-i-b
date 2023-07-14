<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/routing/node/edit">
    <c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="routingEdit">
        <tiles:put name="mainmenu" value="ExternalSystems"/>
        <tiles:put name="submenu" value="Node" type="string"/>

        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <html:hidden property="id"/>
                <tiles:put name="id" value="node"/>
                <tiles:put name="name"><bean:message bundle="nodeBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="nodeBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="nodeBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="nodeBundle" key="label.type"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(type)" styleId="field(type)" name="form" onchange="changeNodeType()">
                                <c:forEach var="type" items="${form.nodeTypes}">
                                    <html:option value="${type}">
                                        <c:out value="${type.description}"/>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="nodeBundle" key="label.URL"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(URL)" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <div id="prefix" >
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="nodeBundle" key="label.prefix"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(prefix)" size="30"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                    <script type="text/javascript">
                        function changeNodeType()
                        {
                            var elem = document.getElementById("field(type)");
                            if (elem != null)
                            {
                                switch (elem.value)
                                {
                                    case "SOFIA":
                                    {
                                        hideOrShow("prefix", false);
                                        return;
                                    }
                                    default:
                                    {
                                        hideOrShow("prefix", true);
                                        return;
                                    }
                                }
                            }
                        }

                        $(document).ready(function()
                        {
                            changeNodeType();
                        });
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditNodeOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="nodeBundle"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="ListNodesOperation">
                       <tiles:put name="commandTextKey" value="button.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                       <tiles:put name="bundle"         value="nodeBundle"/>
                       <tiles:put name="action"         value="/dictionaries/routing/node/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>

</html:form>