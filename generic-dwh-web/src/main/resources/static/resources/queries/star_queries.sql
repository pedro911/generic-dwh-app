use tpch_star_1gb;
set @sf := 1;

#for SF1:
set @quantity_limit := 199;
set @max_customer := "Customer#000142346";

#for SF10:
#set @quantity_limit := 499;
#set @max_customer := "Customer#001371611";


select '#q1.1';
select c_r_name, d_year_number, sum(revenue), sum(product_cost), sum(profit), sum(selling_price) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_date d on d.date_pk = f.fk_orderdate
where c_r_name = "europe"
  and d.d_year_number = 1998;


select '#q2.1';
select c_mktsegment, d_month_number, d_year_number, sum(profit), sum(l_quantity) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_date d on d.date_pk = f.fk_orderdate
where c_mktsegment = "automobile"
  and d_month_number = 12
  and d_year_number = 1995;


select '#q2.2';
select c_mktsegment, d_month_number, d_year_number, sum(profit), sum(l_quantity) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_date d on d.date_pk = f.fk_orderdate
where (c_mktsegment = "automobile"
    or c_mktsegment = "furniture"
    or c_mktsegment = "machinery")
  and (d_month_number = 3
    or d_month_number = 6
    or d_month_number = 12)
  and d_year_number = 1994
group by c_mktsegment,d_month_number,d_year_number
having sum(l_quantity) >= 4000*@sf;


select '#q3.1';
select c_n_name, p_type, d_year_number, sum(product_cost),sum(profit),sum(selling_price) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where c_n_name = "brazil"
  and p_type = "small plated tin"
  and d_year_number = 1997;


select '#q3.2';
select c_n_name, p_type, d_year_number, sum(product_cost),sum(profit),sum(selling_price) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where p_type = "promo polished copper"
  and d_year_number between 1993 and 1996
group by c_n_name,p_type,d_year_number;


select '#q3.3';
select c_n_name, p_type, d_year_number, sum(product_cost), sum(profit), sum(selling_price) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where (c_n_name = "brazil"
    or c_n_name = "united states"
    or c_n_name = "japan")
  and (p_type = "large anodized nickel"
    or p_type = "large anodized steel"
    or p_type = "large anodized tin")
  and d_year_number = 1995
group by c_n_name,p_type,d_year_number
order by c_n_name,p_type,d_year_number;


select '#q4.1';
select o_clerk, p_mfgr, d_year_number, sum(revenue), sum(profit) from fact f
    inner join dim_clerk k on k.pk_clerk = f.fk_clerk
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where o_clerk = "clerk#000000015"
  and (p_mfgr = "manufacturer#2"
    or p_mfgr ="manufacturer#5")
  and d_year_number = 1993
group by p_mfgr;


select '#q5.1';
select c_r_name,p_mfgr,d_month_number,d_year_number, sum(revenue) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where p_mfgr = "manufacturer#4"
  and d_month_number = 11
  and d_year_number = 1992
group by c_r_name,p_mfgr,d_month_number,d_year_number;


select '#q5.2';
select c_r_name,p_mfgr,d_month_number,d_year_number, sum(revenue) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where (p_mfgr = "manufacturer#1"
    or p_mfgr = "manufacturer#3")
  and d_year_number >= 1996
group by c_r_name,p_mfgr,d_month_number,d_year_number;


select '#q6.1';
select c_mktsegment,p_type,s_name,d_year_number, sum(product_cost), sum(selling_price), sum(l_quantity) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_supplier s on s.pk_supplier = f.fk_supplier
    inner join dim_date d on d.date_pk = f.fk_orderdate
where c_mktsegment = "machinery"
  and s_name = "supplier#000000093"
  and d_year_number between 1995 and 1997
group by c_mktsegment,p_type,s_name,d_year_number
order by c_mktsegment,p_type,s_name,d_year_number;


select '#q7.1';
select c_name,p_brand,d_month_number,d_year_number, sum(l_quantity) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
group by c_name,p_brand,d_month_number,d_year_number
having sum(l_quantity) > @quantity_limit
order by c_name,p_brand,d_month_number,d_year_number;


select '#q7.2';
select c_name,p_brand,d_month_number,d_year_number, sum(l_quantity) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_date d on d.date_pk = f.fk_orderdate
where c_name = @max_customer
group by c_name,p_brand,d_month_number,d_year_number
order by c_name,p_brand,d_month_number,d_year_number;


select '#q8.1';
select o_clerk,c_r_name,c_mktsegment, sum(revenue),sum(product_cost),sum(profit),sum(selling_price),sum(l_quantity) from fact f
    inner join dim_clerk k on k.pk_clerk = f.fk_clerk
    inner join dim_customer c on c.pk_customer = f.fk_customer
where o_clerk = "clerk#000000535"
  and c_mktsegment = "furniture"
group by o_clerk,c_r_name,c_mktsegment
order by o_clerk,c_r_name,c_mktsegment;


select '#q9.1';
select c_n_name,c_mktsegment,s_name, sum(revenue), sum(product_cost), sum(selling_price) from fact f
    inner join dim_customer c on c.pk_customer = f.fk_customer
    inner join dim_supplier s on s.pk_supplier = f.fk_supplier
where s.s_name = "supplier#000000024"
  and (c.c_n_name = "canada"
    or c.c_n_name = "united states")
group by c_n_name,c_mktsegment,s_name
order by c_n_name,c_mktsegment,s_name;


select '#q10.1';
select o_clerk, p_brand,p_name, sum(profit),sum(l_quantity) from fact f
    inner join dim_product p on p.pk_part = f.fk_part
    inner join dim_clerk k on k.pk_clerk = f.fk_clerk
where p_brand = "brand#41"
group by o_clerk, p_brand, p_name
having sum(profit) < 0
order by o_clerk,p_brand,p_name;


select '#q10.2';
select p_brand, p_name, sum(profit), sum(l_quantity) from fact f
    inner join dim_product p on p.pk_part = f.fk_part
group by p_brand, p_name
having sum(profit) < 0
order by sum(profit);

