use tpch_gdwh_1gb_dyn;
set @sf := 1;

#for SF1:
set @quantity_limit := 199;
set @max_customer := "Customer#000142346";

#for SF10:
#set @quantity_limit := 499;
#set @max_customer := "Customer#001371611";

select '#Q1.1';

select ro.name, revenue.value as 'revenue'  from reference_object ro
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
where ro.dimension_id = (select id from dimension where name = "Region, Year")
    and ro.name like '%europe%'
    and ro.name like '%1998%';


select '#Q2.1';

select ro.name, profit.value as 'profit', purchase_amount.value as 'purchase_amount' from reference_object ro
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Market Segment, Month")
    and ro.name like '%AUTOMOBILE%'
    and ro.name like '%199512%';


select '#Q2.2';

select ro.name, profit.value as 'profit', purchase_amount.value as 'purchase_amount' from reference_object ro
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Market Segment, Month")
    and ro.name like '%AUTOMOBILE%'
    and ro.name like '%199512%';


select '#Q3.1';

select ro.name, product_cost.value as 'product_cost', profit.value as 'profit', selling_prince.value as 'selling_price' from reference_object ro
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact selling_prince on selling_prince.reference_object_id = ro.id and selling_prince.ratio_id = 4
where ro.dimension_id = (select id from dimension where name = "Nation, Product Type, Year")
    and ro.name like '%BRAZIL%'
    and ro.name like '%SMALL PLATED TIN%'
    and ro.name like '%1997%';


select '#Q3.2';

select ro.name, product_cost.value as 'product_cost', profit.value as 'profit', selling_prince.value as 'selling_price' from reference_object ro
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact selling_prince on selling_prince.reference_object_id = ro.id and selling_prince.ratio_id = 4
where ro.dimension_id = (select id from dimension where name = "Nation, Product Type, Year")
    and ro.name like '%1995%'
    and (ro.name like '%LARGE ANODIZED NICKEL%' or ro.name like '%LARGE ANODIZED STEEL%' or ro.name like '%LARGE ANODIZED TIN%')
    and (ro.name like '%BRAZIL%' or ro.name like '%UNITED STATES%' or ro.name like '%JAPAN%');


select '#Q4.1';

select ro.name, revenue.value as 'revenue', profit.value as 'profit', from reference_object ro
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
where ro.dimension_id = (select id from dimension where name = "Clerk Name, Manufacturer Group, Year")
    and ro.name like '%Clerk#000000015%'
    and (ro.name like '%Manufacturer#2%' or ro.name like '%Manufacturer#5%');


select '#Q5.1';

select ro.name, revenue.value as 'revenue' from reference_object ro
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
where ro.dimension_id = (select id from dimension where name = "Region, Manufacturer Group, Year, Month")
    and ro.name like '%Manufacturer#4%'
    and ro.name like '%199211%';


select '#Q5.2';

select ro.name, revenue.value as 'revenue' from reference_object ro
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
where ro.dimension_id = (select id from dimension where name = "Region, Manufacturer Group, Year, Month")
    and (ro.name like '%manufacturer#1%' or ro.name like '%manufacturer#3%')
    and (ro.name like '%1996%' or ro.name like '%1997%' or ro.name like '%1998%');


select '#Q6.1';

select ro.name, product_cost.value as 'product_cost', selling_prince.value as 'selling_price', purchase_amount.value as 'purchase_amount' from reference_object ro
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact selling_prince on selling_prince.reference_object_id = ro.id and selling_prince.ratio_id = 4
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Market Segment, Product Type, Supplier Name, Year")
    and ro.name like '%MACHINERY%'
    and ro.name like '%Supplier#000000093%'
    and (ro.name like '%1995%' or ro.name like '%1996%' or ro.name like '%1997%');


select '#q7.1';

select ro.name, purchase_amount.value as 'purchase_amount'  from reference_object ro
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Customer Name, Product Brand, Year, Month")
    and purchase_amount.value > 199;


select '#q7.2';

select ro.name, purchase_amount.value as 'purchase_amount'  from reference_object ro
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Customer Name, Product Brand, Year, Month")
    and ro.name like concat('%',@max_customer,'%');


select '#q8.1';

select ro.name, revenue.value as 'revenue', product_cost.value as 'product_cost', profit.value as 'profit', selling_prince.value as 'selling_price', purchase_amount.value as 'purchase_amount' from reference_object ro
inner join fact revenue         on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
inner join fact product_cost    on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit          on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact selling_prince  on selling_prince.reference_object_id = ro.id and selling_prince.ratio_id = 4
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Clerk Name, Region, Market Segment")
    and ro.name like '%Clerk#000000535%'
    and ro.name like '%FURNITURE%';


select '#q9.1';

select ro.name, revenue.value as 'revenue', product_cost.value as 'product_cost', selling_prince.value as 'selling_price' from reference_object ro
inner join fact revenue         on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
inner join fact product_cost    on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact selling_prince  on selling_prince.reference_object_id = ro.id and selling_prince.ratio_id = 4
where ro.dimension_id = (select id from dimension where name = "Nation, Market Segment, Supplier Name")
    and ro.name like '%Supplier#000000024%'
    and (ro.name like '%CANADA%' or ro.name like '%UNITED STATES%');


select '#q10.1';

select ro.name, profit.value as 'profit', purchase_amount.value as 'purchase_amount' from reference_object ro
inner join fact profit          on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Clerk Name, Product Brand, Product Name")
    and ro.name like '%Brand#41%';


select '#q10.2';

select ro.name, profit.value as 'profit', purchase_amount.value as 'purchase_amount' from reference_object ro
inner join fact profit          on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id = (select id from dimension where name = "Product Brand, Product Name")
    and profit.value < 0;

select 'end :)';
