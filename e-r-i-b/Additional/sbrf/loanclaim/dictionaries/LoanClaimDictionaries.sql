-- Заполнение справочников

insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('1', 'Руководитель высшего звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('2', 'Руководитель среднего звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('3', 'Руководитель начального звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('4', 'Владелец предприятия/ген.Директор/Главный бухгалтер', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('5', 'Высококвалифицированный специалист', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('6', 'Специалист', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('7', 'Военнослужащий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('8', 'Рабочий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('9', 'Служащий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('10', 'Судья', 70); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('11', 'Нотариус', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('12', 'Государственный гражданский служащий', 65); 

insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('1', 'Ученая степень / MBA', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('2', 'Второе высшее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('3', 'Высшее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('4', 'Незаконченное высшее', 1);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('5', 'Среднее специальное', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('6', 'Среднее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('7', 'Ниже среднего', 0);

insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('1', 'Мать', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('2', 'Отец', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('3', 'Брат', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('4', 'Сестра', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('5', 'Сын', 1); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('6', 'Дочь', 1);
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('7', 'Иное', 0); 

insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', 'Холост/не замужем', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', 'В разводе', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', 'Женат/замужем', 'REQUIRED'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', 'Вдовец/вдова', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', 'Гражданский брак', 'WITHOUT_PRENUP');

insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('005', 'Предприятие потребительской кооперации', 'Потреб.КООП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('002', 'Открытое акционерное общество', 'ОАО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('003', 'Закрытое акционерное общество', 'ЗАО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('006', 'Полное товарищество', 'Полное товарищество'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('007', 'Товарищество на вере', 'Товарищество на вере'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('008', 'Производственный кооператив', 'Произв. КООП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('009', 'Крестьянское (фермерское) хозяйство', 'КФХ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('001', 'Общество с ограниченной ответственностью', 'ООО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('012', 'Общество с дополнительной ответственностью', 'ОДО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('013', 'Унитарное предприятие на праве хозяйственного ведения', 'УПОнПХВ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('014', 'Унитарное предприятие на праве оперативного управления', 'УПОнПОУ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('015', 'Дочернее унитарное предприятие', 'ДУП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('016', 'Общественная (религиозная) организация (объединение)', 'ОРО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('017', 'Общественное движение', 'ОД'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('018', 'Фонд', 'ФОНД'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('019', 'Учреждение', 'УЧРЕЖДЕНИЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('020', 'Государственное корпорация', 'ГК'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('021', 'Орган общественной самодеятельности', 'ООС'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('022', 'Некоммерческое партнерство', 'НКП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('023', 'Автономная некоммерческая организация', 'АНО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('024', 'Объединение юридических лиц (ассоциация или союз)', 'ОЮЛ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('025', 'Ассоциация крестьянских (фермерских) хозяйств', 'АКФХ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('026', 'Территориальное общественное самоуправление', 'ТОС'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('027', 'Товарищество собственников жилья', 'ТСЖ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('028', 'Садовод., огород.или дачные некоммерческие товарищества', 'ТОВРИЩЕСТВА'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('029', 'Прочая некоммерческия организация', 'ПРОЧИЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('030', 'Финансово - промышленные группы', 'ФПГ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('031', 'Паевые инвестиционные фонды', 'ПИФ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('032', 'Простые товарищества', 'ПТ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('999', 'Представительства и филиалы', 'ПРЕДСТАВИТЕЛЬСТВА'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('034', 'Иные неюридические лица', 'ИНЫЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('033', 'Индивидуальные предприниматели (ПБЮЛ)', 'ИП');

insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('1', 'от 6 до 12 мес', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('2', 'от 1 года до 3 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('3', 'от 3 до 5 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('4', 'от 5 до 10 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('5', 'от 10 до 20 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('6', 'более 20 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('7', 'от 3 до 6 месяцев', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('8', 'менее 3 месяцев', 1);

insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('1', 'Финансы, банки, страхование', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('2', 'Консалтинговые услуги', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('3', 'Армия', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('4', 'Промышленность и машиностроение', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('5', 'Предприятия ТЭК', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('6', 'Металлургия', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('7', 'Оптовая / розничная торговля (уточнение)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('8', 'Услуги (уточнение)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('9', 'Транспорт', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('10', 'Охранная деятельность', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('11', 'Туризм', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('12', 'Образование', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('13', 'Медицина', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('14', 'Культура и искусство', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('15', 'Органы власти и управления', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('16', 'Социальная сфера', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('17', 'Информационные технологии / телекоммуникации', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('18', 'Строительство', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('19', 'Наука', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('20', 'Другие отрасли (уточнение)', 1);

insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('2', 'Аннуитетный'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('0', 'Дифференцированный'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('9', 'Иное'); 

insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('0', 'Ежемесячно'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('1', 'Ежеквартально'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('2', 'Индивидуальный график'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('3', 'Ежегодно'); 

insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('1', 'До 10'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('2', '11-30'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('3', '31-50'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('4', '51-100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('5', 'Более 100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('6', 'Затрудняюсь ответить'); 

insert into LOANCLAIM_REGION(CODE, NAME) values ('0001', 'Республика Адыгея '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0002', 'Республика Башкортостан '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0003', 'Республика Бурятия '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0004', 'Республика Алтай '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0005', 'Республика Дагестан '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0006', 'Ингушская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0007', 'Кабардино - Балкарская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0008', 'Республика Калмыкия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0009', 'Карачаево - Черкесская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0010', 'Республика Карелия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0011', 'Республика Коми'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0012', 'Республика Марий Эл'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0013', 'Республика Мордовия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0014', 'Республика Саха (Якутия)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0015', 'Республика Северная Осетия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0016', 'Республика Татарстан (Татарстан)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0017', 'Республика Тува '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0018', 'Удмуртская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0019', 'Республика Хакасия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0020', 'Чеченская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0021', 'Чувашская Республика- Чаваш Республики '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0022', 'Алтайский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0023', 'Краснодарский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0024', 'Красноярский край '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0025', 'Приморский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0026', 'Ставропольский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0027', 'Хабаровский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0028', 'Амурская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0029', 'Архангельская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0030', 'Астраханская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0031', 'Белгородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0032', 'Брянская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0033', 'Владимирская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0034', 'Волгоградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0035', 'Вологодская область '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0036', 'Воронежская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0037', 'Ивановская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0038', 'Иркутская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0039', 'Калининградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0040', 'Калужская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0041', 'Камчатская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0042', 'Кемеровская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0043', 'Кировская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0044', 'Костромская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0045', 'Курганская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0046', 'Курская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0047', 'Ленинградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0048', 'Липецкая область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0049', 'Магаданская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0050', 'Московская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0051', 'Мурманская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0052', 'Нижегородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0053', 'Новгородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0054', 'Новосибирская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0055', 'Омская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0056', 'Оренбургская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0057', 'Орловская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0058', 'Пензенская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0059', 'Пермская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0060', 'Псковская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0061', 'Ростовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0062', 'Рязанская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0063', 'Самарская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0064', 'Саратовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0065', 'Сахалинская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0066', 'Свердловская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0067', 'Смоленская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0068', 'Тамбовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0069', 'Тверская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0070', 'Томская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0071', 'Тульская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0072', 'Тюменская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0073', 'Ульяновская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0074', 'Челябинская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0075', 'край Забайкальский'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0076', 'Ярославская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0077', 'г. Москва'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0078', 'г. Санкт – Петербург'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0079', 'Еврейская автономная область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0080', 'Агинский Бурятский автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0081', 'Коми - Пермяцкий автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0082', 'Корякский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0083', 'Ненецкий автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0084', 'Таймырский (Долгано - Ненецкий) автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0085', 'Усть - Ордынский Бурятский автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0086', 'Ханты - Мансийский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0087', 'Чукотский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0088', 'Эвенкийский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0089', 'Ямало - Ненецкий автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('9901', 'город и космодром Байконур '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0099', 'Иное (только для адресов, расположенных вне Российской Федерации');

insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('0', 'Собственная квартира',1); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('1', 'У родственников',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('2', 'Соц. Найм',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('3', 'Аренда',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('4', 'Коммунальная квартира',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('5', 'Общежитие',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('6', 'Воинская часть',0);

insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('201', 'Р-н'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('230', 'Тер'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('202', 'У'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('204', 'Кожуун'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('205', 'АО'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('103', 'г'); 

insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('310', 'Волость'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('301', 'Г'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('305', 'Дп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('304', 'Кп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('311', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('302', 'пгт'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('303', 'рп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('307', 'с/а'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('309', 'с/о'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('306', 'с/с'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('312', 'тер'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('313', 'сумон'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('314', 'с/п'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('315', 'с/мо'); 

insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('1', 'Автокредит'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('2', 'Ипотечный'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('3', 'Потребительский'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('4', 'Кредитная карта'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('5', 'Поручительство'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('6', 'Другое'); 

insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('401', 'аал'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('402', 'аул'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('403', 'волость'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('404', 'высел'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('405', 'г'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('436', 'городок'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('406', 'д'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('407', 'дп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('408', 'ж/д будка'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('409', 'ж/д казарм'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('410', 'ж/д оп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('411', 'ж/д пост'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('412', 'ж/д разд'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('438', 'ж/д платф'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('413', 'ж/д ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('414', 'заимка'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('415', 'казарма'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('416', 'кп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('417', 'м'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('418', 'мкр'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('419', 'нп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('420', 'остров'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('421', 'п'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('426', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('422', 'п/р'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('423', 'п/ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('424', 'пгт'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('425', 'починок'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('427', 'промзона'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('428', 'рзд'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('429', 'рп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('430', 'с'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('431', 'сл'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('432', 'ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('433', 'ст-ца'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('437', 'тер'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('434', 'у'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('435', 'х'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('439', 'кв-л'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('440', 'арбан'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('441', 'снт'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('442', 'лпх'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('443', 'погост'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('444', 'кордон'); 

insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('1', 'Комната', 0); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('2', 'Квартира', 1); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('3', 'Дом', 0); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('4', 'Участок', 0); 

insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('529', 'Ул'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('532', 'аал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('501', 'Аллея'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('533', 'Аул'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('502', 'б-р'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('503', 'Въезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('534', 'Высел'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('535', 'Городок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('536', 'Д'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('504', 'Дор'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('537', 'ж/д будка'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('538', 'ж/д казарм'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('539', 'ж/д оп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('540', 'ж/д пост'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('541', 'ж/д рзд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('542', 'ж/д ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('559', 'ж/д платф'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('505', 'Жт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('506', 'Заезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('543', 'Казарма'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('507', 'кв-л'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('508', 'Км'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('509', 'Кольцо'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('510', 'Линия'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('544', 'М'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('545', 'Мкр'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('511', 'Наб'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('546', 'Нп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('512', 'Остров'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('548', 'П'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('549', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('550', 'п/р'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('551', 'п/ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('513', 'Парк'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('514', 'Пер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('515', 'Переезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('516', 'Пл'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('547', 'Платф'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('517', 'пл-ка'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('552', 'Полустанок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('553', 'Починок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('519', 'пр-кт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('518', 'Проезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('520', 'просек'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('521', 'Проселок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('522', 'Проулок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('554', 'Рзд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('555', 'С'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('523', 'Сад'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('524', 'Сквер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('556', 'Сл'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('557', 'Ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('525', 'Стр'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('526', 'Тер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('527', 'Тракт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('528', 'Туп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('530', 'уч-к'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('558', 'Х'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('531', 'Ш'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('560', 'Арбан'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('561', 'Спуск'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('562', 'Канал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('563', 'Гск'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('564', 'Снт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('565', 'Лпх'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('566', 'Проток'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('567', 'коса'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('568', 'вал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('569', 'ферма'); 

insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('1', 'Наземное ТС'); 
insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('2', 'Водное ТС'); 

-- Работа по трудовому договору
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('1', 'Срочный контракт', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('2', 'Без срока (постоянная занятость)', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('3', 'Частная практика (уточнить)', 0, 0, 1, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('4', 'Индивидуальный предприниматель', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('5', 'Агент на комиссионном договоре', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('6', 'Пенсионер', 0, 0, 0, 1); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('7', 'Исполнитель по гражданско-правовому договору', 1, 1, 0, 0); 

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('1',
             'На имеющийся вклад',
             1,
             0,
             'DEPOSIT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('2',
             'На новый вклад',
             1,
             1,
             'DEPOSIT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('3',
             'На имеющуюся карту',
             1,
             0,
             'CARD');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('4',
             'На новую карту',
             1,
             1,
             'CARD');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('5',
             'На имеющийся текущий счет',
             1,
             0,
             'CURRENT_ACCOUNT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('6',
             'На новый текущий счет',
             1,
             1,
             'CURRENT_ACCOUNT');