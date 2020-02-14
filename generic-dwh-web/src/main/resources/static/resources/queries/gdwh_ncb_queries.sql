use tpch_gdwh_1gb_ncb;
set @sf := 1;

#for SF1:
set @quantity_limit := 199;
set @max_customer := "Customer#000142346";

#for SF10:
#set @quantity_limit := 499;
#set @max_customer := "Customer#001371611";


select '#q1.1';
select _region.name as 'region',_year.name as 'year', sum(revenue.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
inner join reference_object_hierarchy _nation_region on _nation_region.child_id = _nation.id
inner join reference_object _region on _region.id = _nation_region.parent_id and _region.dimension_id = 1
    and _region.name = "europe"
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
    and _year.name = "1998"
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
where ro.dimension_id in(20);


select '#q2.1';
select _market_segment.name as 'market_segment',_month.name as 'month', sum(profit.value),sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id
    and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
    and _month.name = "199512"
inner join reference_object_hierarchy _customer_name_market_segment on _customer_name_market_segment.child_id = _customer_name.id
inner join reference_object _market_segment on _market_segment.id = _customer_name_market_segment.parent_id and _market_segment.dimension_id = 4
    and _market_segment.name = "automobile"
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20);


select '#q2.2';
select _market_segment.name as 'market_segment',_month.name as 'month', sum(profit.value),sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
	and (_month.name = "199403" or _month.name = "199406" or _month.name = "199412")
inner join reference_object_hierarchy _customer_name_market_segment on _customer_name_market_segment.child_id = _customer_name.id
inner join reference_object _market_segment on _market_segment.id = _customer_name_market_segment.parent_id and _market_segment.dimension_id = 4
	and (_market_segment.name = "automobile" or _market_segment.name = "furniture" or _market_segment.name = "machinery")
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _market_segment.id,_month.id
having sum(purchase_amount.value) >= 4000*@sf;


select '#q3.1';
select _nation.name as 'nation',_product_type.name as 'product_type',_year.name as 'year', sum(product_cost.value),sum(profit.value),sum(sell_price.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2 and _nation.name = "brazil"
inner join reference_object_hierarchy _product_name_product_type on _product_name_product_type.child_id = _product_name.id
inner join reference_object _product_type on _product_type.id = _product_name_product_type.parent_id and _product_type.dimension_id = 9 and _product_type.name = "small plated tin"
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17 and _year.name = "1997"
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact sell_price on sell_price.reference_object_id = ro.id and sell_price.ratio_id = 4
where ro.dimension_id in(20);


select '#q3.2';
select _nation.name as 'nation',_product_type.name as 'product_type',_year.name as 'year', sum(product_cost.value),sum(profit.value),sum(sell_price.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
inner join reference_object_hierarchy _product_name_product_type on _product_name_product_type.child_id = _product_name.id
inner join reference_object _product_type on _product_type.id = _product_name_product_type.parent_id and _product_type.dimension_id = 9
	and _product_type.name = "promo polished copper"
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
	and (_year.name = "1993" or  _year.name = "1994" or  _year.name = "1995" or _year.name = "1996")
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact sell_price on sell_price.reference_object_id = ro.id and sell_price.ratio_id = 4
where ro.dimension_id in(20)
group by _nation.id,_product_type.id,_year.id;


select '#q3.3';
select _nation.name as 'nation',_product_type.name as 'product_type',_year.name as 'year', sum(product_cost.value),sum(profit.value),sum(sell_price.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
	and (_nation.name = "brazil" or _nation.name = "united states" or _nation.name = "japan")
inner join reference_object_hierarchy _product_name_product_type on _product_name_product_type.child_id = _product_name.id
inner join reference_object _product_type on _product_type.id = _product_name_product_type.parent_id and _product_type.dimension_id = 9
	and (_product_type.name = "large anodized nickel" or _product_type.name = "large anodized steel" or _product_type.name = "large anodized tin")
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
	and _year.name = "1995"
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact sell_price on sell_price.reference_object_id = ro.id and sell_price.ratio_id = 4
where ro.dimension_id in(20)
group by _nation.id,_product_type.id,_year.id
order by _nation.name,_product_type.name,_year.name;


select '#q4.1';
select _clerk_name.name as 'clerk_name',_manufacturer_group.name as 'manufacturer_group',_year.name as 'year', sum(revenue.value),sum(profit.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_clerk_name on _ro_clerk_name.combination_id = ro.id
inner join reference_object _clerk_name on _clerk_name.id = _ro_clerk_name.subordinate_id and _clerk_name.dimension_id = 5
	and _clerk_name.name = "clerk#000000015"
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
inner join reference_object_hierarchy _product_brand_manufacturer_group on _product_brand_manufacturer_group.child_id = _product_brand.id
inner join reference_object _manufacturer_group on _manufacturer_group.id = _product_brand_manufacturer_group.parent_id and _manufacturer_group.dimension_id = 6
	and (_manufacturer_group.name = "manufacturer#2" or _manufacturer_group.name = "manufacturer#5")
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17 and _year.name = "1993"
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
where ro.dimension_id in(20)
group by _manufacturer_group.id;


select '#q5.1';
select _region.name as 'region',_manufacturer_group.name as 'manufacturer_group',_year.name as 'year',_month.name as 'month', sum(revenue.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
	and _month.name = "199211"
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
inner join reference_object_hierarchy _nation_region on _nation_region.child_id = _nation.id
inner join reference_object _region on _region.id = _nation_region.parent_id and _region.dimension_id = 1
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
inner join reference_object_hierarchy _product_brand_manufacturer_group on _product_brand_manufacturer_group.child_id = _product_brand.id
inner join reference_object _manufacturer_group on _manufacturer_group.id = _product_brand_manufacturer_group.parent_id and _manufacturer_group.dimension_id = 6
	and _manufacturer_group.name = "manufacturer#4"
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
where ro.dimension_id in(20)
group by _region.id,_manufacturer_group.id,_year.id,_month.id;


select '#q5.2';
select _region.name as 'region',_manufacturer_group.name as 'manufacturer_group',_year.name as 'year',_month.name as 'month', sum(revenue.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
inner join reference_object_hierarchy _nation_region on _nation_region.child_id = _nation.id
inner join reference_object _region on _region.id = _nation_region.parent_id and _region.dimension_id = 1
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
inner join reference_object_hierarchy _product_brand_manufacturer_group on _product_brand_manufacturer_group.child_id = _product_brand.id
inner join reference_object _manufacturer_group on _manufacturer_group.id = _product_brand_manufacturer_group.parent_id and _manufacturer_group.dimension_id = 6
	and (_manufacturer_group.name = "manufacturer#1" or _manufacturer_group.name = "manufacturer#3")
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
	and (_year.name = "1996" or _year.name = "1997" or _year.name = "1998")
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
where ro.dimension_id in(20)
group by _region.id,_manufacturer_group.id,_year.id,_month.id;


select '#q6.1';
select _market_segment.name as 'market_segment',_product_type.name as 'product_type',_supplier_name.name as 'supplier_name',_year.name as 'year', sum(product_cost.value),sum(sell_price.value),sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_supplier_name on _ro_supplier_name.combination_id = ro.id
inner join reference_object _supplier_name on _supplier_name.id = _ro_supplier_name.subordinate_id and _supplier_name.dimension_id = 10
	and _supplier_name.name = "supplier#000000093"
inner join reference_object_hierarchy _customer_name_market_segment on _customer_name_market_segment.child_id = _customer_name.id
inner join reference_object _market_segment on _market_segment.id = _customer_name_market_segment.parent_id and _market_segment.dimension_id = 4
	and _market_segment.name = "machinery"
inner join reference_object_hierarchy _product_name_product_type on _product_name_product_type.child_id = _product_name.id
inner join reference_object _product_type on _product_type.id = _product_name_product_type.parent_id and _product_type.dimension_id = 9
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
	and (_year.name = "1995" or _year.name = "1996" or _year.name = "1997")
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact sell_price on sell_price.reference_object_id = ro.id and sell_price.ratio_id = 4
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _market_segment.id,_product_type.id,_supplier_name.id,_year.id
order by _market_segment.name,_product_type.name,_supplier_name.name,_year.name;


select '#q7.1';
select _customer_name.name as 'customer_name',_product_brand.name as 'product_brand',_year.name as 'year',_month.name as 'month', sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _customer_name.id,_product_brand.id,_year.id,_month.id
having sum(purchase_amount.value) > @quantity_limit
order by _customer_name.name,_product_brand.name,_year.name,_month.name;


select '#q7.2';
select _customer_name.name as 'customer_name',_product_brand.name as 'product_brand',_year.name as 'year',_month.name as 'month', sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_month on _ro_month.combination_id = ro.id
inner join reference_object _month on _month.id = _ro_month.subordinate_id and _month.dimension_id = 18
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
	and _customer_name.name = @max_customer
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
inner join reference_object_hierarchy _month_year on _month_year.child_id = _month.id
inner join reference_object _year on _year.id = _month_year.parent_id and _year.dimension_id = 17
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _customer_name.id,_product_brand.id,_year.id,_month.id
order by _customer_name.name,_product_brand.name,_year.name,_month.name;


select '#q8.1';
select _clerk_name.name as 'clerk_name',_region.name as 'region',_market_segment.name as 'market_segment', sum(revenue.value),sum(product_cost.value),sum(profit.value),sum(sell_price.value),sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_clerk_name on _ro_clerk_name.combination_id = ro.id
inner join reference_object _clerk_name on _clerk_name.id = _ro_clerk_name.subordinate_id and _clerk_name.dimension_id = 5
	and _clerk_name.name = "clerk#000000535"
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
inner join reference_object_hierarchy _nation_region on _nation_region.child_id = _nation.id
inner join reference_object _region on _region.id = _nation_region.parent_id and _region.dimension_id = 1
inner join reference_object_hierarchy _customer_name_market_segment on _customer_name_market_segment.child_id = _customer_name.id
inner join reference_object _market_segment on _market_segment.id = _customer_name_market_segment.parent_id and _market_segment.dimension_id = 4
	and _market_segment.name = "furniture"
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact sell_price on sell_price.reference_object_id = ro.id and sell_price.ratio_id = 4
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _clerk_name.id,_region.id,_market_segment.id
order by _clerk_name.name,_region.name,_market_segment.name;


select '#q9.1';
select _nation.name as 'nation',_market_segment.name as 'market_segment',_supplier_name.name as 'supplier_name', sum(revenue.value),sum(product_cost.value),sum(sell_price.value) from reference_object ro
inner join reference_object_combination _ro_customer_name on _ro_customer_name.combination_id = ro.id
inner join reference_object _customer_name on _customer_name.id = _ro_customer_name.subordinate_id and _customer_name.dimension_id = 3
inner join reference_object_combination _ro_supplier_name on _ro_supplier_name.combination_id = ro.id
inner join reference_object _supplier_name on _supplier_name.id = _ro_supplier_name.subordinate_id and _supplier_name.dimension_id = 10
	and _supplier_name.name = "supplier#000000024"
inner join reference_object_hierarchy _customer_name_nation on _customer_name_nation.child_id = _customer_name.id
inner join reference_object _nation on _nation.id = _customer_name_nation.parent_id and _nation.dimension_id = 2
	and (_nation.name ="canada" or _nation.name = "united states")
inner join reference_object_hierarchy _customer_name_market_segment on _customer_name_market_segment.child_id = _customer_name.id
inner join reference_object _market_segment on _market_segment.id = _customer_name_market_segment.parent_id and _market_segment.dimension_id = 4
inner join fact revenue on revenue.reference_object_id = ro.id and revenue.ratio_id = 1
inner join fact product_cost on product_cost.reference_object_id = ro.id and product_cost.ratio_id = 2
inner join fact sell_price on sell_price.reference_object_id = ro.id and sell_price.ratio_id = 4
where ro.dimension_id in(20)
group by _nation.id,_market_segment.id,_supplier_name.id
order by _nation.name,_market_segment.name,_supplier_name.name;


select '#q10.1';
select _clerk_name.name as 'clerk_name',_product_brand.name as 'product_brand',_product_name.name as 'product_name', sum(profit.value),sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_combination _ro_clerk_name on _ro_clerk_name.combination_id = ro.id
inner join reference_object _clerk_name on _clerk_name.id = _ro_clerk_name.subordinate_id and _clerk_name.dimension_id = 5
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
	and _product_brand.name = "brand#41"
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _clerk_name.id,_product_brand.id,_product_name.id
having sum(profit.value) < 0
order by _clerk_name.name,_product_brand.name,_product_name.name;


select '#q10.2';
select _product_brand.name as 'product_brand',_product_name.name as 'product_name', sum(profit.value),sum(purchase_amount.value) from reference_object ro
inner join reference_object_combination _ro_product_name on _ro_product_name.combination_id = ro.id
inner join reference_object _product_name on _product_name.id = _ro_product_name.subordinate_id and _product_name.dimension_id = 8
inner join reference_object_hierarchy _product_name_product_brand on _product_name_product_brand.child_id = _product_name.id
inner join reference_object _product_brand on _product_brand.id = _product_name_product_brand.parent_id and _product_brand.dimension_id = 7
inner join fact profit on profit.reference_object_id = ro.id and profit.ratio_id = 3
inner join fact purchase_amount on purchase_amount.reference_object_id = ro.id and purchase_amount.ratio_id = 5
where ro.dimension_id in(20)
group by _product_brand.id,_product_name.id
having sum(profit.value) < 0
order by sum(profit.value);

