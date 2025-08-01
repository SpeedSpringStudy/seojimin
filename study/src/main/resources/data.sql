INSERT INTO category (id, name, color, image_url, description) VALUES
(1, '교환권', '#6c95d1', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/coupon.png', '다양한 상품 교환이 가능한 쿠폰입니다.'),
(2, '상품권', '#f5a623', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/voucher.png', '쇼핑이나 외식에 사용할 수 있는 상품권입니다.'),
(3, '뷰티', '#ff99cc', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/beauty.png', '화장품, 스킨케어 등 뷰티 관련 상품입니다.'),
(4, '패션', '#cc6699', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/fashion.png', '의류, 액세서리 등 패션 아이템입니다.'),
(5, '식품', '#ffcc66', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/food.png', '간식, 음료 등 다양한 먹거리입니다.'),
(6, '리빙/도서', '#66cccc', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/living_book.png', '생활용품과 도서 관련 아이템입니다.'),
(7, '레저/스포츠', '#6699ff', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/leisure_sports.png', '취미나 운동 관련 선물입니다.'),
(8, '아티스트/캐릭터', '#9966cc', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/artist_character.png', '캐릭터 상품 및 아티스트 콜라보 굿즈입니다.'),
(9, '유아동/반려', '#ff9999', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/baby_pet.png', '아이와 반려동물을 위한 선물입니다.'),
(10, '디지털/가전', '#6666ff', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/digital.png', '전자기기 및 디지털 용품입니다.'),
(11, '카카오프렌즈', '#fedd00', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/kakao_friends.png', '카카오프렌즈 캐릭터 관련 상품입니다.'),
(12, '트렌드 선물', '#00c896', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/trend.png', '요즘 인기 있는 트렌디한 선물입니다.'),
(13, '백화점', '#999999', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/department.png', '고급 백화점 상품입니다.');

INSERT INTO product (id, name, price, category) VALUES
(1, 'GS25 교환권 5천원권', 5000, 1),
(2, '컬쳐랜드 상품권 1만원권', 10000, 2),
(3, '올리브영 립스틱 세트', 18000, 3),
(4, '무신사 티셔츠 기프트', 25000, 4),
(5, '스타벅스 아메리카노 Tall', 4500, 5),
(6, '리빙박스 3종 세트', 12900, 6),
(7, '나이키 스포츠 타월', 16000, 7),
(8, '카카오프렌즈 무드등', 22000, 11),
(9, '아이 깨끗해 유아 핸드솝', 6900, 9),
(10, '애플 에어팟 프로 2세대', 359000, 10);