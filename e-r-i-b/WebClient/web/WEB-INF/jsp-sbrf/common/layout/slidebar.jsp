<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%--
    Выдвижная панель настроек виджетов/страницы в шапке
    (слайд-бар)
--%>

<script type="text/javascript">
    function openConfirmResetWin(){
        win.open('confirmResetWin');
    }

    function resetSettings(){
        win.close('confirmResetWin');
        ($("[button='reset']"))[0].click();
    }

    function selectHelp()
    {
        <c:set var="helpURL" value="${phiz:calculateActionURL(pageContext, '/help.do?id=/private/widget')}"/>
        openHelp("${helpURL}");
    }
</script>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<tiles:importAttribute/>
<div data-dojo-type="widget.SlideBar" class="sbrf SlideBar hidden" align="center">

    <%-- Шапка --%>
    <div class="head" align="center">
        <div class="title">Дизайн страниц <span class="bold"><bean:message bundle="commonBundle" key="application.title"/></span></div>
        <div class="image"><img src="${image}/bullet.gif" alt=""/></div>
    </div>

    <%-- Тело --%>
    <div class="body" align="center">
        <div class="content" >
            <div data-dojo-type="widget.TabContainerEx" id="tabContainer" class="WidgetCatalog" tabPosition="left-h" layoutAlign="client">
                <c:set var="dojoContentPaneProps">
                    loadingMessage:'Идет загрузка ...',
                    errorMessage:'Ошибка при загрузке'
                </c:set>

                <%-- 1. Вкладка с каталогом виджетов --%>
                <div data-dojo-type="dojox.layout.ContentPane" data-dojo-props="${dojoContentPaneProps}"
                     title="Каталог виджетов" href="${phiz:calculateActionURL(pageContext,"/private/async/widgetCatalog.do")}?haveMainContainer=${haveWidgetMainContainer}"></div>

                 <%--2. Вкладка с форматом страницы (положение бокового меню) --%>
                <div data-dojo-type="dojox.layout.ContentPane" data-dojo-props="${dojoContentPaneProps}"
                     title="Формат страниц" href="${phiz:calculateActionURL(pageContext,"/private/async/header/format.do")}"></div>

                <%-- 3. Вкладка с выбором скина --%>
                <div id="skinSelector" data-dojo-type="dojox.layout.ContentPane" data-dojo-props="${dojoContentPaneProps}"
                     title="Настройка стиля" href="${phiz:calculateActionURL(pageContext,"/private/async/header/skins.do")}"></div>

                <%-- 4. Помощь --%>
                <div id="widgetTabHelp" data-dojo-type="dojox.layout.ContentPane" select="selectHelp()" title="Помощь"></div>
            </div>
        </div>
    </div>

    <div class="buttonSlidebarContainer">
        <div class="buttonBlock">
            <div class="description">
                Вы можете нужные блоки поменять местами, а лишние &ndash; удалить.<br/>
                <img src="${image}/settings.png" alt=""/> Также можно настроить блоки, отмеченные шестеренкой.
            </div>
            <div class="buttons">
                <span button="cancel">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="viewType" value="buttonLightGray"/>
                        <tiles:put name="onclick" value=";"/>
                    </tiles:insert>
                </span>
                <span button="save">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="viewType" value="buttonOrange"/>
                        <tiles:put name="onclick" value=";"/>
                    </tiles:insert>
                </span>
                <span button="reset"></span>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.clear"/>
                        <tiles:put name="commandHelpKey" value="button.clear"/>
                        <tiles:put name="bundle" value="widgetBundle"/>
                        <tiles:put name="imageUrl" value="${image}/refresh.png"/>
                        <tiles:put name="viewType" value="linkWithImg"/>
                        <tiles:put name="onclick" value="openConfirmResetWin();"/>
                    </tiles:insert>

            </div>
            <div class="clear"></div>
        </div>
    </div>

    <%-- Рычаг слайд-бара --%>
    <div class="lever" button="lever">
        <div class="label">НАСТРОИТЬ</div>
    </div>
</div>
<div class="clear"></div>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirmResetWin"/>
    <tiles:put name="data">
        Вы действительно хотите применить настройки по умолчанию?
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel"/>
                <tiles:put name="bundle" value="widgetBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('confirmResetWin'); return false;"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.next"/>
                <tiles:put name="commandHelpKey" value="button.next"/>
                <tiles:put name="bundle" value="widgetBundle"/>
                <tiles:put name="onclick" value="resetSettings();"/>
            </tiles:insert>
        </div>
    </tiles:put>
 </tiles:insert>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="errorManyIdenticWidgets"/>
    <tiles:put name="data">
        <span class="many-same-widget-text"></span>
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel"/>
                <tiles:put name="bundle" value="widgetBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('errorManyIdenticWidgets'); return false;"/>
            </tiles:insert>
        </div>
    </tiles:put>
 </tiles:insert>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="notSavedChangedWidgets"/>
    <tiles:put name="data">
        Вы не применили настройки виджетов:
        <span class="notSavedWidgetNames"></span>.
        Продолжите редактирование.
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.continue"/>
                <tiles:put name="commandHelpKey" value="button.continue"/>
                <tiles:put name="bundle" value="widgetBundle"/>
                <tiles:put name="viewType" value="buttonLightGray"/>
                <tiles:put name="onclick" value="win.close('notSavedChangedWidgets'); return false;"/>
            </tiles:insert>
        </div>
    </tiles:put>
 </tiles:insert>
