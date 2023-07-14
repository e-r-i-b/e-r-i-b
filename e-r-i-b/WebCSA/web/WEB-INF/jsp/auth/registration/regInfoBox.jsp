<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="moveOutHeadInfo" flush="false">
    <tiles:put name="note" type="string" value="����������� � �������� ������"/>
    <tiles:put name="data" type="string">
        <div class="promo_head">
            <div class="promo_head-inner">
                <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_bg.png" alt="" class="promo_pic" />
                <div class="promo_about">����������� � �������� ������</div>
                <div class="promo_title">������ <br/>�&nbsp;���������� ������ ������������</div>
            </div>
        </div>
        <i class="promo_divider"></i>
        <div class="promo_items">
            <div class="promo_items-inner">
                <div class="promo_item">
                    <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_1.png" alt="" class="promo_img"/>
                    <h3 class="promo_text">���������� ������ <br/>�� �������� <br/>��������</h3>
                    <div class="promo_description">�� 7.5% �� ������ ������� � ������ � ���������� �������������� ���������.</div>
                </div>
                <div class="promo_item">
                    <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_2.png" alt="" class="promo_img"/>
                    <h3 class="promo_text">����������� <br/>������ � ������� <br/>���������</h3>
                    <div class="promo_description">� ��� ��� �������� �� ��� ����������� ������ � ������������ �������</div>
                </div>
                <div class="promo_item">
                    <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_3.png" alt="" class="promo_img"/>
                    <h3 class="promo_text">���������� <br/>���������� ����� <br/>��������</h3>
                    <div class="promo_description">��������� ������� �������� �������� � �������������� ���������� ��������.</div>
                </div>
            </div>
            <div class="promo_buttons">
                <a onclick="return utils.slide('#AboutRegistration')" class="b-btn btn_yellow" href="#AboutRegistration">
                    <span class="btn_text">������������������</span>
                    <i class="btn_crn"></i>
                </a>
            </div>
        </div>
    </tiles:put>
    <tiles:put name="dashOff" type="string" value="�������� ���� ����"/>
    <tiles:put name="dashOn" type="string" value="������ ��� ��� ������?"/>
</tiles:insert>

