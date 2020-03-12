use tpch_snow_1gb;
set @sf := 1;

#for SF1:
set @quantity_limit := 199;
set @max_customer := "Customer#000142346";

#for SF10:
#set @quantity_limit := 499;
#set @max_customer := "Customer#001371611";


select '#q1.1';
SET @start_time := sysdate(2);

select r_name,d_year_number, sum(revenue), sum(product_cost), sum(profit), sum(selling_price) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join region r on r.r_regionkey = n.n_regionkey
inner join dim_date d on d.date_pk = f.fk_orderdate
where r_name = "europe"
  and d.d_year_number = 1998;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q2.1';
SET @start_time := sysdate(2);

select market_segment,d_year_number,d_month_number, sum(profit), sum(l_quantity) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join market_segment m on m.pk_market_segment = c.market_segment_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where market_segment = "automobile"
  and d_month_number = 12
  and d_year_number = 1995;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q2.2';
SET @start_time := sysdate(2);

select market_segment,d_year_number,d_month_number, sum(profit), sum(l_quantity) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join market_segment m on m.pk_market_segment = c.market_segment_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where (market_segment = "automobile"
    or market_segment = "furniture"
    or market_segment = "machinery")
  and (d_month_number = 3
    or d_month_number = 6
    or d_month_number = 12)
  and d_year_number = 1994
group by market_segment,d_year_number,d_month_number
having sum(l_quantity) >= 4000*@sf;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q3.1';
SET @start_time := sysdate(2);

select n_name, product_type, d_year_number, sum(product_cost), sum(profit), sum(selling_price) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join dim_product p on p.pk_part = f.fk_part
inner join product_type pt on pt.pk_product_type = p.product_type_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where n_name = "brazil"
  and product_type = "small plated tin"
  and d_year_number = 1997;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q3.2';
SET @start_time := sysdate(2);

select n_name, product_type, d_year_number, sum(product_cost), sum(profit), sum(selling_price) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join dim_product p on p.pk_part = f.fk_part
inner join product_type pt on pt.pk_product_type = p.product_type_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where product_type = "promo polished copper"
  and d_year_number between 1993 and 1996
group by n_name, product_type, d_year_number;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q3.3';
SET @start_time := sysdate(2);

select n_name, product_type, d_year_number, sum(product_cost), sum(profit), sum(selling_price) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join dim_product p on p.pk_part = f.fk_part
inner join product_type pt on pt.pk_product_type = p.product_type_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where (n_name = "brazil"
    or n_name = "united states"
    or n_name = "japan")
  and (product_type = "large anodized nickel"
    or product_type = "large anodized steel"
    or product_type = "large anodized tin")
  and d_year_number = 1995
group by n_name, product_type, d_year_number
order by n_name, product_type, d_year_number;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q4.1';
SET @start_time := sysdate(2);

select o_clerk,manufacturer_group,d_year_number, sum(revenue),sum(profit) from fact f
inner join dim_clerk k on k.pk_clerk = f.fk_clerk
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
inner join manufacturer_group mg on mg.pk_manufacturer_group = pb.manufacturer_group_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where o_clerk = "clerk#000000015"
  and (manufacturer_group = "manufacturer#2"
    or manufacturer_group = "manufacturer#5")
  and d_year_number = 1993
group by manufacturer_group;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q5.1';
SET @start_time := sysdate(2);

select r_name,manufacturer_group,d_year_number,d_month_number, sum(revenue) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join region r on r.r_regionkey = n.n_regionkey
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
inner join manufacturer_group mg on mg.pk_manufacturer_group = pb.manufacturer_group_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where manufacturer_group = "manufacturer#4"
  and d_month_number = 11
  and d_year_number = 1992
group by r_name,manufacturer_group,d_year_number,d_month_number;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q5.2';
SET @start_time := sysdate(2);

select r_name,manufacturer_group,d_year_number,d_month_number, sum(revenue) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join region r on r.r_regionkey = n.n_regionkey
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
inner join manufacturer_group mg on mg.pk_manufacturer_group = pb.manufacturer_group_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where (manufacturer_group = "manufacturer#1"
    or manufacturer_group = "manufacturer#3")
  and d_year_number >= 1996
group by r_name,manufacturer_group,d_year_number,d_month_number;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q6.1';
SET @start_time := sysdate(2);

select market_segment,product_type,s_name,d_year_number, sum(product_cost),sum(selling_price),sum(l_quantity) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join market_segment m on m.pk_market_segment = c.market_segment_id
inner join dim_product p on p.pk_part = f.fk_part
inner join product_type pt on pt.pk_product_type = p.product_type_id
inner join dim_supplier s on s.pk_supplier = f.fk_supplier
inner join dim_date d on d.date_pk = f.fk_orderdate
where market_segment = "machinery"
  and s_name = "supplier#000000093"
  and d_year_number between 1995 and 1997
group by market_segment,product_type,s_name,d_year_number
order by market_segment,product_type,s_name,d_year_number;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q7.1';
SET @start_time := sysdate(2);

select c_name,product_brand,d_year_number,d_month_number, sum(l_quantity) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
inner join dim_date d on d.date_pk = f.fk_orderdate
group by c_name,product_brand,d_year_number,d_month_number
having sum(l_quantity) > @quantity_limit
order by sum(l_quantity) desc;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q7.2';
SET @start_time := sysdate(2);

select c_name,product_brand,d_year_number,d_month_number, sum(l_quantity) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
inner join dim_date d on d.date_pk = f.fk_orderdate
where c_name = @max_customer
group by product_brand,d_year_number,d_month_number
order by sum(l_quantity) desc;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q8.1';
SET @start_time := sysdate(2);

select o_clerk,r_name,market_segment, sum(revenue),sum(product_cost),sum(profit),sum(selling_price),sum(l_quantity) from fact f
inner join dim_clerk k on k.pk_clerk = f.fk_clerk
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join region r on r.r_regionkey = n.n_regionkey
inner join market_segment m on m.pk_market_segment = c.market_segment_id
where o_clerk = "clerk#000000535"
  and market_segment = "furniture"
group by o_clerk,r_name,market_segment
order by o_clerk,r_name,market_segment;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q9.1';
SET @start_time := sysdate(2);

select n_name,market_segment,s_name, sum(revenue),sum(product_cost),sum(selling_price) from fact f
inner join dim_customer c on c.pk_customer = f.fk_customer
inner join nation n on n.n_nationkey = c.c_nationkey
inner join market_segment m on m.pk_market_segment = c.market_segment_id
inner join dim_supplier s on s.pk_supplier = f.fk_supplier
where s.s_name = "supplier#000000024"
  and (n_name = "canada"
    or n_name = "united states")
group by n_name,market_segment,s_name
order by n_name,market_segment,s_name;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q10.1';
SET @start_time := sysdate(2);

select o_clerk,product_brand,p_name, sum(profit),sum(l_quantity) from fact f
inner join dim_clerk k on k.pk_clerk = f.fk_clerk
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
where product_brand = "brand#41"
group by o_clerk,product_brand,p_name
having sum(profit) < 0
order by o_clerk,product_brand,p_name;

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select '#q10.2';
SET @start_time := sysdate(2);

select product_brand, p_name, sum(profit),sum(l_quantity) from fact f
inner join dim_product p on p.pk_part = f.fk_part
inner join product_brand pb on pb.pk_product_brand = p.product_brand_id
group by product_brand,p_name
having sum(profit) < 0
order by sum(profit);

SET @end_time := sysdate(2);
SELECT TIMEDIFF(@end_time, @start_time) AS 'query_time';


select 'end :)';