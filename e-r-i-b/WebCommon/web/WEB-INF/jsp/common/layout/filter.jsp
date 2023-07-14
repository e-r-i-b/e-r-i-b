<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute name="filter" ignore="true" scope="request"/>
<c:if test="${filter  != ''}">
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <script type="text/javascript">
        var disabled = false;
        var actionFilterURL = "${phiz:calculateActionURL(pageContext,'/private/async/filter')}";

        function needCleaning(elem)
        {
            return ((elem.tagName == "INPUT" && elem.type != "checkbox") || elem.tagName == "SELECT")&&
                    elem.name.indexOf('filter(') >=0 && elem.className.indexOf("notCleaningField") == -1;
        }

        function clearFilterParams(data)
        {
            var frm = document.forms.item(0);
            for (var i = frm.elements.length-1; i >= 0; i--)
            {
                var elem = frm.elements.item(i);
                if (needCleaning(elem))
                {
                    elem.value = "";
                }
            }

            var params =  data.split(";");
            for (var j=params.length-1; j >=0; j--)
            {
                var filterElement= params[j].split("=");
                if (filterElement.length != 2) {
                    continue;
                }
                var element = getElement("filter("+trim(filterElement[0])+")");
                var value = trim(filterElement[1]);
                if (element != null)
                {
                    if (element.type != 'checkbox')
                    {
                        element.value = value;
                    } else
                    {
                        element.checked = (value == 'true' || value == 'on');
                    }
                }
            }
            return false;
        }

        function clearFilter()
        {
            ajaxQuery("url=<%=request.getAttribute("FormAction").toString()%>", actionFilterURL, clearFilterParams, null, true);
        }

        function fastSearch()
        {
            $(".fastSearch").toggle().children('input').attr("disabled", !disabled);

            $("#fastSearchLink").text(
                    $("#fastSearchLink").text() == '<bean:message bundle="commonBundle" key="button.advancedSearch"/>'
                            ? '<bean:message bundle="commonBundle" key="button.fastSearch"/>'
                            : '<bean:message bundle="commonBundle" key="button.advancedSearch"/>'
            );
            $("input[name=filter(isFastSearch)]").val(!disabled);
            disabled = !disabled;
        }

    </script>
    <!--основная таблица-->
    <div class="relative">
        <div class="filterLink relative">
            <a id="showFilterButton" class="showFilterButton relative" href="#" onClick="switchFilter(event);validCountMMIns();"
               title="Поиск">
                Поиск
            </a>
        </div>

        <div id="filterFields" style="display:none;">
            <div class="triangle"></div>
            <div class="filter">

                <div class="MaxSize fltrBg">
                    <!--внешняя таблица стиля фильтра-->
                    <table cellspacing="0" cellpadding="0" class="MaxSize fltrInfArea"
                           onkeypress="onEnterKey(event);">
                        <tr>
                            <!-- фильтр -->
                            <tiles:insert attribute="filter"/>
                        </tr>
                    </table>
                    <!--сообщение об ошибке-->
                    <div class="filterErrorMessage" id="filterErrorMessage"></div>
                </div>
                <c:if test="${empty bundleName or bundleName==''}">
                    <c:set var="bundleName" value="commonBundle"/>
                </c:if>
                <!--стандартные кнопки -->
                <div align="right" class="fltrBgBtm">
                    <div class="quickSearchButton float">
                        <!-- Кнопка быстрого поиска-->
                        <c:if test="${not empty fastSearchFilter && fastSearchFilter == 'true'}">
                            <html:link href="#" onclick="fastSearch();" styleId="fastSearchLink" styleClass="fastSearchLink blueGrayLink">
                                <bean:message bundle="commonBundle" key="button.fastSearch"/>
                            </html:link>
                            <input type="hidden"  name="filter(isFastSearch)" value="${!fastSearchFilter}"/>
                        </c:if>
                    </div>
                    <div class="otherButtonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.clear"/>
                            <tiles:put name="commandHelpKey" value="button.clear.help"/>
                            <tiles:put name="bundle" value="${bundleName}"/>
                            <tiles:put name="onclick" value="clearFilter();"/>
                            <tiles:put name="viewType" value="blueGrayLink"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.filter"/>
                            <tiles:put name="commandHelpKey" value="button.filter.help"/>
                            <tiles:put name="bundle" value="${bundleName}"/>
                            <tiles:put name="isDefault" value="true"/>
                        </tiles:insert>
                        <!--дополнительные кнопки-->
                        <c:if test="${additionalFilterButtons  != ''}">
                            <tiles:insert attribute="additionalFilterButtons"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

</c:if>