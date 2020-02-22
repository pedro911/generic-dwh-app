set @dimension_with_atomic_levels := (select max(id) from tpch_gdwh_1gb_ncb.dimension);

USE tpch_gdwh_small_dyn;

# first delete facts w.r.t the new reference objects created

delete from fact where reference_object_id in (
    select id from reference_object where dimension_id in
        (select id from dimension where id > @dimension_with_atomic_levels)
);

#delete reference objects because facts were already deleted
delete from reference_object where dimension_id > @dimension_with_atomic_levels;

#delete all new dimension combinations
delete from dimension_combination where combination_id > @dimension_with_atomic_levels;

#delete all dimensions created with DYN ETL
delete from dimension where id > @dimension_with_atomic_levels;