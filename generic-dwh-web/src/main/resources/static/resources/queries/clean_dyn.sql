set @dimension_with_atomic_levels := (select max(id) from tpch_gdwh_1gb_ncb.dimension);

ANALYZE TABLE tpch_gdwh_10gb_dyn.dimension;
ANALYZE TABLE tpch_gdwh_10gb_dyn.dimension_combination;
ANALYZE TABLE tpch_gdwh_10gb_dyn.dimension_hierarchy;
ANALYZE TABLE tpch_gdwh_10gb_dyn.fact;
ANALYZE TABLE tpch_gdwh_10gb_dyn.reference_object;
ANALYZE TABLE tpch_gdwh_10gb_dyn.reference_object_combination;
ANALYZE TABLE tpch_gdwh_10gb_dyn.reference_object_hierarchy;


SELECT table_schema "DB Name",
        ROUND(SUM(data_length + index_length) / 1024 / 1024, 1) "DB Size in MB"
FROM information_schema.tables
GROUP BY table_schema;


USE tpch_gdwh_10gb_dyn;

#first delete facts w.r.t the new reference objects created

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