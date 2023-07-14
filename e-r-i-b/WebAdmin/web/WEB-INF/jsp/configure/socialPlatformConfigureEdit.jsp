<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/socialApi/configurePlatform/edit" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="SocialPlatformSettings"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="configureBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="/socialApi/configurePlatform.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <html:hidden property="id" name="frm"/>
                <tiles:put name="name" value="Редактирование платформы"/>
                <tiles:put name="description" value="Используйте форму для изменения настроек socialAPI в разрезе платформ."/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function()
                        {
                            var downloadFromSBRFRadio = document.getElementsByName("field(downloadFromSBRF)");
                            showSBRFDownload(getRadioValue(downloadFromSBRFRadio));
                            showQRName();
                        });
                        function showSBRFDownload(isShow)
                        {
                            if(isShow == 'true')
                            {
                                $('[name = "field(bankURL)"]').attr("disabled", false);
                                $('[name = "field(externalURL)"]').attr("disabled", true);
                                $('[name = "field(isQR)"]').attr("disabled", true);
                                showQRName();
                            }
                            else
                            {
                                $('[name = "field(bankURL)"]').attr("disabled", true);
                                $('[name = "field(externalURL)"]').attr("disabled", false);
                                $('[name = "field(isQR)"]').attr("disabled", false);
                                showQRName();
                            }
                        }

                        function showQRName()
                        {
                            if(ensureElementByName('field(isQR)').checked && (getRadioValue(document.getElementsByName("field(downloadFromSBRF)"))!='true'))
                            {
                                $('[name = "field(QRName)"]').attr("disabled", false);
                            }
                            else
                            {
                                $('[name = "field(QRName)"]').attr("disabled", true);
                            }
                        }
                    </script>
                    <html:hidden property="field(id)"/>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Название платформы
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(platformName)" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            ID платформы,присылаемый социальным приложением
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(platformId)" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <html:hidden property="field(social)" value="true" title="Социальная"/>

                    <html:hidden property="field(scheme)" value="false" title="Light + Pro"/>

                    <c:set var="imageData" value="${phiz:getImageById(frm.imageID)}"/>
                    <c:set var="imgSrc" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Иконка платформы
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false">
                                <tiles:put name="id"  value="Icon"/>
                                <tiles:put name="maxSize"  value="40"/>
                                <tiles:put name="preview" value="${true}"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${not empty imgSrc}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:img src="${imgSrc}"/>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Подтверждать одноразовым паролем
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(isPasswordConfirm)"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.show.SB.client.attribute.short" bundle="configureBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(isShowSbAttribute)"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend>Скачивание приложения</legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Отображать ссылку
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(downloadFromSBRF)" value="true" onclick="showSBRFDownload('true')"/>&nbsp;На сайт Сбербанка
                                <br/>
                                <html:radio property="field(downloadFromSBRF)" value="false" onclick="showSBRFDownload('false')"/>&nbsp;На сайт разработчика
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                URL-адрес на сайте банка
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(bankURL)" maxlength="100" size="50"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                URL-адрес на сайте разработчика
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(externalURL)" maxlength="100" size="50"/>
                                <div class="clear"></div>
                                <html:checkbox property="field(isQR)" onclick="showQRName()"/>&nbsp;Использовать QR-код
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Название файла с изображением QR-кода
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(QRName)" maxlength="100"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Отображать в разделе Социальные приложения
                            </tiles:put>
                            <tiles:put name="data">
                                <html:checkbox property="field(isShownInApps)"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="MobilePlatformEditOperation">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="configureBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="MobilePlatformEditOperation">
                        <tiles:put name="commandTextKey" value="button.clear"/>
                        <tiles:put name="commandHelpKey" value="button.clear.help"/>
                        <tiles:put name="bundle" value="configureBundle"/>
                        <tiles:put name="onclick">
                            javascript:resetForm(event);
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>