<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/mobileApi/configurePlatform" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="MobilePlatformSettings"/>
        <tiles:put name="pageTitle" type="string">
            Настройки mAPI в разрезе платформ
        </tiles:put>
        <tiles:put name="data" type="string">

            <tiles:put name="menu" type="string">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add"/>
                    <tiles:put name="action" value="/mobileApi/configurePlatform/edit.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </tiles:put>

            <tiles:put name="filter" type="string">

               <tiles:insert definition="filterTextField" flush="false">
                   <tiles:put name="label" value="platform.Id"/>
                   <tiles:put name="bundle" value="configureBundle"/>
                   <tiles:put name="mandatory" value="false"/>
                   <tiles:put name="name" value="platformId"/>
                   <tiles:put name="size" value="30"/>
                   <tiles:put name="maxlength" value="10"/>
               </tiles:insert>

               <tiles:insert definition="filterTextField" flush="false">
                   <tiles:put name="label" value="platform.name"/>
                   <tiles:put name="bundle" value="configureBundle"/>
                   <tiles:put name="mandatory" value="false"/>
                   <tiles:put name="name" value="platformName"/>
                   <tiles:put name="size" value="100"/>
                   <tiles:put name="maxlength" value="100"/>
               </tiles:insert>

               <tiles:insert definition="filterEntryField" flush="false">
                   <tiles:put name="label"  value="platform.scheme"/>
                   <tiles:put name="bundle" value="configureBundle"/>
                   <tiles:put name="isFastSearch" value="true"/>
                   <tiles:put name="data">
                       <html:select property="filter(scheme)" styleClass="filterSelect">
                           <html:option value="">Все</html:option>
                           <html:option value="true">только Light</html:option>
                           <html:option value="false">Light + Pro</html:option>
                       </html:select>
                   </tiles:put>
               </tiles:insert>

            </tiles:put>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="name" value="Настройки мобильного API"/>
                <tiles:put name="description" value="Используйте форму для изменения настроек мобильного API"/>
                <tiles:put name="id" value="MobilePlatformConfig"/>

                <tiles:put name="grid">
                   <sl:collection id="device" model="list" property="data">
                       <sl:collectionParam id="selectType" value="checkbox"/>
                       <sl:collectionParam id="selectProperty" value="id"/>
                       <sl:collectionParam id="selectName" value="selectedIds"/>
                       <sl:collectionItem title="ID" property="platformId">
                          <sl:collectionItemParam id="action" value="/mobileApi/configurePlatform/edit.do?id=${device.id}"/>
                      </sl:collectionItem>

                       <sl:collectionItem title="Название" property="platformName">
                           <sl:collectionItemParam id="action" value="/mobileApi/configurePlatform/edit.do?id=${device.id}"/>
                       </sl:collectionItem>

                       <sl:collectionItem title="Версия Приложения" property="version"/>

                       <c:set var="scheme" value="${device.lightScheme}"/>
                       <sl:collectionItem title="Режим использования" property="lightScheme">
                           <sl:collectionItemParam id="value">
                               <c:choose>
                                   <c:when test="${scheme}">
                                       только Light
                                   </c:when>
                                   <c:otherwise>
                                       Light + Pro
                                   </c:otherwise>
                               </c:choose>
                           </sl:collectionItemParam>
                       </sl:collectionItem>
                       <sl:collectionItem title="Подтверждать одноразовым паролем">
                           <c:choose>
                               <c:when test="${device.passwordConfirm}">
                                   да
                               </c:when>
                               <c:otherwise>
                                   нет
                               </c:otherwise>
                           </c:choose>
                       </sl:collectionItem>
                   </sl:collection>

                </tiles:put>

                <tiles:put name="buttons">
                    <script type="text/javascript">
                        var addUrl = "${phiz:calculateActionURL(pageContext,'/mobileApi/configurePlatform/edit')}";
                        function doEdit()
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkOneSelection("selectedIds", 'Выберите одну платформу'))
                                return;
                            var synchKey = getRadioValue(document.getElementsByName("selectedIds"));
                            window.location = addUrl + "?id=" + encodeURIComponent(synchKey);
                        }

                        function doRemove()
                        {
                            if(!checkSelection('selectedIds', 'Выберите платформу'))
                                return;

                            var ids = document.getElementsByName('selectedIds');
                            var confirmText='';
                            var count = 0;
                            for (var i = 0; i < ids.length; ++i)
                            {
                                if (ids[i].checked)
                                    {
                                        var r = ids.item(i).parentNode.parentNode;
                                        var platformName  = trim(getElementTextContent(r.cells[2]));
                                        confirmText+= platformName+', ';
                                        count++;
                                    }
                            }

                           if(count == 1)
                           {
                                return confirm('Вы действительно хотите удалить платформу '+confirmText.substring(0, confirmText.length-2)+' из справочника?');
                           }
                           else
                           {
                                return confirm('Вы действительно хотите удалить платформы '+confirmText.substring(0, confirmText.length-2)+' из справочника?');
                           }
                        }
                    </script>
                    <tiles:insert definition="clientButton" flush="false" operation="MobilePlatformEditOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle" value="configureBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="MobilePlatformRemoveOperation">
                       <tiles:put name="commandKey" value="button.remove"/>
                       <tiles:put name="commandHelpKey" value="button.remove"/>
                       <tiles:put name="bundle" value="commonBundle"/>
                       <tiles:put name="validationFunction" value="doRemove();"/>
                   </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>