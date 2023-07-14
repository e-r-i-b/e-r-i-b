<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/finances/settings/editCardOperationsLifetime" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="internalOperationSettings"/>
        <tiles:put name="submenu" type="string" value="EditCardOperationLifetime"/>
        <tiles:put name="replicateAccessOperation" value="EditCardOperationLifetimeOperation"/>
        <tiles:put name="pageName" type="string"><bean:message bundle="financesOptionsBundle" key="label.pageTitle.card.operation.lifetime"/></tiles:put>
        <tiles:put name="pageDescription" type="string"><bean:message bundle="financesOptionsBundle" key="label.description.card.operation.lifetime"/></tiles:put>
        <tiles:put name="data">
            <fieldset>
                <table>
                    <c:set var="fieldName" value="card.operation.lifetime"/>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="${fieldName}"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="financesOptionsBundle" key="label.card.operation.lifetime"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="1"/>
                        <tiles:put name="textMaxLength" value="40"/>
                        <tiles:put name="fieldType" value="custom"/>
                        <tiles:put name="customField">
                            <select id="lifetimeDays" onchange="fillLifeTime()">
                                <c:forEach var="d" begin="0" end="9">
                                    <option value="${d}">0${d}</option>
                                </c:forEach>
                                <c:forEach var="d" begin="10" end="40">
                                    <option value="${d}">${d}</option>
                                </c:forEach>
                            </select>

                            <select id="lifetimeHours" onchange="fillLifeTime()">
                                <c:forEach var="h" begin="0" end="9">
                                    <option value="${h}">0${h}</option>
                                </c:forEach>
                                <c:forEach var="h" begin="10" end="23">
                                    <option value="${h}">${h}</option>
                                </c:forEach>
                            </select>

                            <select id="lifetimeMinutes" onchange="fillLifeTime()">
                                <c:forEach var="m" begin="0" end="9">
                                    <option value="${m}">0${m}</option>
                                </c:forEach>
                                <c:forEach var="m" begin="10" end="59">
                                    <option value="${m}">${m}</option>
                                </c:forEach>
                            </select>

                            <html:hidden property="field(${fieldName})"/>
                        </tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                </table>
            </fieldset>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditCardOperationLifetimeOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditCardOperationLifetimeOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        doOnLoad(function ()
        {
            var sec = document.getElementsByName("field(${fieldName})")[0].value;
            var days = Math.floor(sec / 60 / 60 / 24);
            var hours1 = sec - days * 60 * 60 * 24;
            var hours = Math.floor(hours1 / 60 / 60);
            var minutes1 = hours1 - hours * 60 * 60;
            var minutes = Math.floor(minutes1 / 60);
            document.getElementById("lifetimeDays").options.selectedIndex = days;
            document.getElementById("lifetimeHours").options.selectedIndex = hours;
            document.getElementById("lifetimeMinutes").options.selectedIndex = minutes;
        });

        function fillLifeTime()
        {
            var days = parseInt(document.getElementById("lifetimeDays").value);
            var hours = parseInt(document.getElementById("lifetimeHours").value);
            var minutes = parseInt(document.getElementById("lifetimeMinutes").value);
            var time = days * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60;
            setElement("field(${fieldName})", time);
        }
    </script>
</html:form>