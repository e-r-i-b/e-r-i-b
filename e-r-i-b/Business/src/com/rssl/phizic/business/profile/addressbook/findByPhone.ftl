--������� ������ IDX_AB_LGID
--��������� ������� ("CONTACT"."LOGIN_ID"=TO_NUMBER(extra_loginId) AND "CONTACT"."PHONE"
--    LIKE extra_like_phone
--�������������� 300 (���������� ��������� ��� ������)
SELECT
    contact.id ID
FROM ADDRESS_BOOKS contact
WHERE
 contact.LOGIN_ID = :extra_loginId AND
 contact.PHONE like :extra_like_phone