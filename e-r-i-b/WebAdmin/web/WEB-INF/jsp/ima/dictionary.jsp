<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/ima/dictionary" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="dictionary">
        <tiles:put name="submenu" type="string" value="List"/>
        <tiles:put name="pageTitle" type="string">Список ОМС</tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="button.cancel"/>
                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                <tiles:put name="bundle"            value="commonBundle"/>
                <tiles:put name="onclick"           value="window.close();"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="depositsList"/>

                <script type="text/javascript">
                    function validateSelection()
                    {
                        checkIfOneItem("selectedId");
                        return (checkSelection("selectedId", 'Вы не выбрали ни одного ОМС') || checkOneSelection("selectedId", 'Вы можете выбрать только один ОМС'));
                    }

                    function sendIMAData()
                    {
                        if (!validateSelection())
                            return;

                        var checked = $('[name=selectedId]:checked');
                        var data = new Object();
                        data.id = checked.val();
                        data.type = $('#imaProductId'+checked.val()).val();
                        data.subType = $('#imaAdditionalProductId'+checked.val()).val();
                        data.name = $('#imaName'+checked.val()).text();

                        window.opener.setIMAInfo(data);
                        window.close();
                     }
                </script>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.choose"/>
                        <tiles:put name="commandHelpKey" value="button.choose"/>
                        <tiles:put name="bundle"         value="commonBundle"/>
                        <tiles:put name="onclick"        value="sendIMAData();"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="data">
                    <c:forEach items="${form.data}" var="product">
                        <tr>
                            <td>
                                <input type="radio" name="selectedId" value="${product.id}">
                                <input type="hidden" id="imaProductId${product.id}" value="${product.type}"/>
                                <input type="hidden" id="imaAdditionalProductId${product.id}" value="${product.subType}"/>
                                <span id="imaName${product.id}">${product.name}</span>
                            </td>
                        </tr>
                    </c:forEach>
                </tiles:put>

                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного ОМС!"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
