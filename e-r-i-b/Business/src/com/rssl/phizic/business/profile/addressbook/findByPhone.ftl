--Опорный объект IDX_AB_LGID
--Предикаты доступа ("CONTACT"."LOGIN_ID"=TO_NUMBER(extra_loginId) AND "CONTACT"."PHONE"
--    LIKE extra_like_phone
--Кардинальность 300 (количество контактов для логина)
SELECT
    contact.id ID
FROM ADDRESS_BOOKS contact
WHERE
 contact.LOGIN_ID = :extra_loginId AND
 contact.PHONE like :extra_like_phone