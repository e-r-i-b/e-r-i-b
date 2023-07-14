<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<html:form action="/card/product/kind/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="cardsBundle"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.product.kind.choose" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="CardProducts"/>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.product.kind.name"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="kindName"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength" value="255"/>
                <tiles:put name="isDefault" value="Наименование"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <bean:message key="description.product.kind.choose" bundle="${bundle}"/>
            <script type="text/javascript">
                var listCardProduct =
                {
                    items: {},
                    
                    hasSelectedIds: function ()
                    {
                        var ids = document.getElementsByName("selectedIds");
                        for (var i = 0; i < ids.length; i++)
                            if (ids[i].checked)
                                return true;
                        return false;
                    },

                    saveConfirm: function()
                    {
                        if (this.hasSelectedIds())
                            if (confirm('Добавить отмеченные виды вкладов?'))
                                if (!this.addAll())
                                    return false;
                        return true;
                    },

                    addAll: function ()
                    {
                        var ids = document.getElementsByName("selectedIds");
                        var flag = true;
                        var result = {};

                        for (var i = 0; i < ids.length; i++)
                        {
                            if (ids[i].checked)
                            {
                                var kindId = ids[i].value;
                                if (jQuery.inArray(this.items[kindId].currency, window.opener.editCardProduct.currencies) != -1)
                                {
                                    alert("В рамках одного вида можно загрузить только один подвид с одним видом валюты");
                                    return false;
                                }
                                result['id'] = kindId;
                                result['productId'] = this.items[kindId].productId;
                                result['productSubId'] = this.items[kindId].productSubId;
                                result['name'] = this.items[kindId].name;
                                result['currency'] = this.items[kindId].currency;
                                result['stopOpenDeposit'] = this.items[kindId].stopOpenDeposit;
                                flag = false;
                                window.opener.editCardProduct.addKindRow(result);
                            }
                        }
                        if (flag)
                        {
                            alert("Выберите один или несколько видов вкладов и нажмите на кнопку «Выбрать»");
                            return false;
                        }
                        return true;
                    },

                    setSelectedKind : function ()
                    {
                        checkIfOneItem("selectedIds");
                        if (this.addAll())
                            window.close();
                        return null;
                    }
                };

                $(document).ready(function()
                {
                    // для пагинации в таблице
                    $.each($("table.tblInf #pagination a"),
                            function()
                            {
                                var oldClick = this.onclick;
                                this.onclick = function(event)
                                {
                                    if (!listCardProduct.saveConfirm())
                                        return false;
                                    oldClick(event);
                                };
                            }
                            );

                    // для изменения количества отображаемых записей в таблице
                    $.each($("table.tblInf #pagination select"),
                            function()
                            {
                                var oldChange = this.onchange;
                                this.onchange = function(event)
                                {
                                    if (!listCardProduct.saveConfirm())
                                        return false;
                                    oldChange(event);
                                };
                            }
                            );

                    // для кнопки применить в фильтре
                    $.each($("span.buttDiv.defaultBtn table"),
                            function()
                            {
                                var oldClick = this.onclick;
                                this.onclick = function(event)
                                {
                                    if (!listCardProduct.saveConfirm())
                                        return false;
                                    oldClick(event);
                                };
                            }
                            );
                });
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="AllowedDepartmentListTable"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.choose"/>
                        <tiles:put name="commandHelpKey" value="button.choose"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="listCardProduct.setSelectedKind(event)"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="javascript:window.close()"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:put name="grid">
                        <sl:collection id="kindOfProduct" model="list" property="data" bundle="cardsBundle">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>

                            <sl:collectionItem title="label.card.product.table.kind.product.id" property="productId"/>
                            <sl:collectionItem title="label.card.product.table.kind.product.sub.id" property="productSubId"/>
                            <sl:collectionItem title="label.card.product.table.kind.product.sub.name" property="name"/>
                            <sl:collectionItem title="label.card.product.table.kind.product.currency">
                                <c:out value="${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)}"/>
                            </sl:collectionItem>
                            <script type="text/javascript">
                                <c:set var="code" value="${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)}"/>
                                listCardProduct.items["${kindOfProduct.id}"] = {
                                    productId: "${kindOfProduct.productId}",
                                    productSubId: "${kindOfProduct.productSubId}",
                                    name: "${phiz:escapeForJS(kindOfProduct.name, true)}",
                                    currencyISO: "${kindOfProduct.currency.code}",
                                    currency: "${code}",
                                    stopOpenDeposit: "${kindOfProduct.stopOpenDeposit.timeInMillis}"
                                };
                            </script>
                        </sl:collection>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message key="label.product.kind.empty" bundle="${bundle}"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>