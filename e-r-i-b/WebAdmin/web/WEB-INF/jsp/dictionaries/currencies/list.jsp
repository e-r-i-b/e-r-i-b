<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/currencies/list">
    <tiles:insert definition="dictionary">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="button.cancel"/>
                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                <tiles:put name="bundle"            value="commonBundle"/>
                <tiles:put name="onclick"           value="window.close();"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.code"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="name"   value="code"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.title"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="name"   value="name"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="currenciesList"/>
                <tiles:put name="text" value="Справочник валют"/>
                <c:set var="formdata" value="${form.data}"/>
                <tiles:put name="grid">
                    <tiles:importAttribute/>
                    <c:set var="globalImagePath" value="${globalUrl}/images"/>
                    <c:set var="imagePath" value="${skinUrl}/images"/>

                    <sl:collection id="listElement" model="list" property="data" bundle="commonBundle">
                        <sl:collectionParam id="selectType"     value="radio"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.code" property="code"/>
                        <sl:collectionItem title="label.title" property="name"/>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function selectCurrenciy()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", 'Укажите одну запись') ||
                                    (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
                                    return;


                                var ids = document.getElementsByName("selectedIds");
                                for (var i = 0; i < ids.length; i++)
                                {
                                    if (ids.item(i).checked)
                                    {
                                        var r = ids.item(i).parentNode.parentNode;
                                        var a = new Array(2);
                                        a['id'] = trim(ids.item(i).value);
                                        a['code'] = trim(getElementTextContent(r.cells[1]));
                                        a['name'] = trim(getElementTextContent(r.cells[2]));
                                        window.opener.setCurrency(a);
                                        window.close();
                                        return true;
                                    }
                                }
                                return false;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"    value="button.choose"/>
                            <tiles:put name="commandHelpKey"    value="button.choose"/>
                            <tiles:put name="bundle"            value="commonBundle"/>
                            <tiles:put name="onclick"           value="selectCurrenciy();"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage" value="Не найдено ни одной валюты!"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>