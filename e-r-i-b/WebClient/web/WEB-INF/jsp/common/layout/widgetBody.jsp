<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--@elvariable id="globalUrl" type="java.lang.String"--%>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<tiles:insert definition="roundBorder" flush="false">
    <tiles:put name="color" value="${borderColor}"/>
    <tiles:put name="title">
        <span panel="title">
            <img button="roll" src="${globalImagePath}/1x1.gif" alt="" border="0" />
            <span label="title">${title}</span>
        </span>
    </tiles:put>

    <tiles:put name="control">
           <c:if test="${sizeable == 'true'}">
                <img button="size" src="${globalImagePath}/1x1.gif" alt="" border="0" class="widgetButtonResize"/>
            </c:if>

            <c:if test="${editable == 'true'}">
                <img button="edit" src="${globalImagePath}/1x1.gif" alt="" border="0" class="widgetButtonSettings"/>
            </c:if>

            <c:if test="${not empty linksControl}">
                <div panel="linksControl">${linksControl}</div>
            </c:if>

        <img button="close" src="${globalImagePath}/1x1.gif" alt="" border="0" class="widgetButtonClose"/>
    </tiles:put>

    <tiles:put name="data">
        <div class="clientPane">
            <%-- Панелька для отображения --%>
            <div panel="view" class="panelView" style="display:none;">
                ${viewPanel}
                <div class="clear"></div>
            </div>

            <%-- Панелька для настроек --%>
            <div panel="edit" class="panelEdit" style="display:none;">
                <tiles:insert definition="scrollable-data" flush="false">
                    <tiles:put name="className" value="scroll-edit-widget"/>
                    <tiles:put name="data">
                        ${editPanel}
                    </tiles:put>
                </tiles:insert>

                <div class="hideWidgetsText"></div>

                <div align="right" class="buttons">

                    ${additionalSetting}

                    <span button="cancel">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="bundle" value="widgetBundle"/>
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="onclick" value=";"/>
                        </tiles:insert>
                    </span>
                    <span button="save">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="bundle" value="widgetBundle"/>
                            <tiles:put name="commandTextKey" value="button.apply"/>
                            <tiles:put name="commandHelpKey" value="button.apply"/>
                            <tiles:put name="viewType" value="buttonOrange"/>
                            <tiles:put name="onclick" value=";"/>
                        </tiles:insert>
                    </span>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </tiles:put>
</tiles:insert>
