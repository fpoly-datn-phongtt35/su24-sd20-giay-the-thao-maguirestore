package com.datn.maguirestore.repository;

import com.datn.maguirestore.dto.ShoesDetailDTOCustom;
import com.datn.maguirestore.dto.ShopShoesDTO;
import com.datn.maguirestore.entity.ShoesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoesDetailsRepository extends JpaRepository<ShoesDetails, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT " +
                    "   size_ids, " +
                    "   size_names, " +
                    "   color_ids, " +
                    "   color_names, " +
                    "   sz.name as size_name, " +
                    "   cl.name as color_name, " +
                    "   sh.code, " +
                    "   sh.id, " +
                    "   CONCAT(br.name, ' ', sh.name) as name, " +
                    "   iu.path, " +
                    "   GROUP_CONCAT(iu.path) as paths, " +
                    "   d.name as discount_name, " +
                    "   d.discount_method as discount_method, " +
                    "   dsd.discount_amount as discount_amount, " +
                    "   dsd.discount_amount as discount_amount_3_4, " +
                    "   (SELECT CAST(COALESCE(avg(fb.rate), 0) AS SIGNED) " +
                    "    FROM feed_back fb " +
                    "    JOIN shoes_details ad ON fb.shoes_id = ad.id AND fb.status = 1 " +
                    "    WHERE ad.brand_id = sd.brand_id AND ad.shoes_id = sd.shoes_id " +
                    "   ) as rating " +
                    "FROM " +
                    "   (SELECT " +
                    "       GROUP_CONCAT(DISTINCT sz.id ORDER BY sz.id) as size_ids, " +
                    "       GROUP_CONCAT(DISTINCT sz.name ORDER BY sz.id) as size_names " +
                    "    FROM `shoes-store`.shoes_details sd " +
                    "    JOIN `shoes-store`.size sz ON sd.size_id = sz.id " +
                    "    JOIN `shoes-store`.color cl ON sd.color_id = cl.id AND cl.id = :clid " +
                    "    WHERE sd.brand_id = :brid AND sd.shoes_id = :shid AND sd.status = 1 " +
                    "   ) size_subquery, " +
                    "   (SELECT " +
                    "       GROUP_CONCAT(DISTINCT cl.id ORDER BY cl.id) as color_ids, " +
                    "       GROUP_CONCAT(DISTINCT cl.name ORDER BY cl.id) as color_names " +
                    "    FROM `shoes-store`.shoes_details sd " +
                    "    JOIN `shoes-store`.color cl ON sd.color_id = cl.id " +
                    "    WHERE sd.brand_id = :brid AND sd.shoes_id = :shid AND sd.status = 1 " +
                    "   ) color_subquery " +
                    "JOIN `shoes-store`.shoes_details sd ON 1 = 1 " +
                    "JOIN `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id " +
                    "JOIN `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id " +
                    "JOIN `shoes-store`.shoes sh ON sd.shoes_id = sh.id AND sh.status = 1 " +
                    "JOIN `shoes-store`.brand br ON sd.brand_id = br.id " +
                    "JOIN `shoes-store`.size sz ON sd.size_id = sz.id AND (:siid IS NULL OR sz.id = :siid) " +
                    "JOIN `shoes-store`.color cl ON sd.color_id = cl.id AND cl.id = :clid " +
                    "LEFT JOIN `shoes-store`.discount_details dsd " +
                    "ON dsd.shoes_id = sd.shoes_id AND dsd.status = 1 AND dsd.brand_id = sd.brand_id " +
                    "LEFT JOIN `shoes-store`.discount d " +
                    "ON dsd.discount_id = d.id AND d.start_date <= now() AND d.end_date >= now() AND d.status = 1 " +
                    "WHERE sd.brand_id = :brid AND sd.shoes_id = :shid AND sd.status = 1 " +
                    "GROUP BY size_ids, size_names, color_ids, color_names, sd.id, sz.name, cl.name, br.name, sh.code, sh.id, iu.path, d.name, d.discount_method, discount_amount;"
    )
    ShopShoesDTO findDistinctByShoesAndBrandOrderBySellPriceDescOne(
            @Param("shid") Integer shid,
            @Param("brid") Integer brid,
            @Param("siid") Integer siid,
            @Param("clid") Integer clid
    );

    @Query(
            value = "select fu.path,sd.price,s.name,s.id as idsh,sz.id as idsz,c.id as idc,b.id as idb,\n" +
                    "    coalesce(max(discount_method),min(discount_method)) AS discountmethod,\n" +
                    "    max(\n" +
                    "    case\n" +
                    "    when discount_method is not null && (discount_method = 3 || discount_method = 4)  then dsd.discount_amount\n" +
                    "    else null\n" +
                    "    end \n" +
                    "    ) as discountamount_3_4,\n" +
                    "    coalesce(max(d.discount_amount),min(d.discount_amount)) AS discountamount_1_2\n" +
                    "from (\n" +
                    "    WITH shoes_details AS (\n" +
                    "        SELECT\n" +
                    "            *,\n" +
                    "            ROW_NUMBER() OVER(PARTITION BY shoes_id, brand_id ORDER BY id DESC) AS rn\n" +
                    "        FROM\n" +
                    "            shoes_details\n" +
                    "    )\n" +
                    "    SELECT *\n" +
                    "    FROM shoes_details\n" +
                    "    WHERE rn = 1\n" +
                    ") sd\n" +
                    "join shoes s on  sd.shoes_id = s.id\n" +
                    "join size sz on sd.size_id = sz.id\n" +
                    "join color c on sd.color_id = c.id\n" +
                    "join brand b on sd.brand_id = b.id\n" +
                    "LEFT JOIN discount_details AS dsd \n" +
                    "ON sd.shoes_id  = dsd.shoes_id and dsd.status = 1 and dsd.brand_id = b.id\n" +
                    "LEFT JOIN discount AS d \n" +
                    "ON dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1\n" +
                    "join (\n" +
                    "\twith shoes_file_upload_mapping as(\n" +
                    "\t\tselect * ,row_number() over(partition by shoes_details_id order by id) as rn\n" +
                    "        from shoes_file_upload_mapping\n" +
                    "    )\n" +
                    "    select * from shoes_file_upload_mapping\n" +
                    "    where rn = 1\n" +
                    ") sfum on sd.id = sfum.shoes_details_id\n" +
                    "join file_upload fu on sfum.file_upload_id = fu.id \n" +
                    "GROUP BY sd.shoes_id,sd.brand_id, fu.path,sd.price,s.name,s.id,sz.id,c.id,b.id, sd.created_date\n" +
                    "ORDER BY sd.created_date DESC\n" +
                    "LIMIT 10;",
            nativeQuery = true
    )
    List<ShoesDetailDTOCustom> getNewShoesDetail();

    @Query(
            value = "SELECT fu.path, sd.price, s.name, s.id AS idsh, sz.id AS idsz, c.id AS idc, b.id AS idb,\n" +
                    "\t\td.discount_method as discountmethod,dsd.discount_amount as discountamount_3_4,\n" +
                    "        d.discount_amount as discountamount_1_2\n" +
                    "FROM (\n" +
                    "    SELECT *\n" +
                    "    FROM (\n" +
                    "        SELECT *,\n" +
                    "               ROW_NUMBER() OVER(PARTITION BY shoes_id, brand_id ORDER BY id DESC) AS rn\n" +
                    "        FROM shoes_details\n" +
                    "    ) AS shoes_details\n" +
                    "    WHERE rn = 1\n" +
                    ") AS sd\n" +
                    "JOIN shoes AS s ON sd.shoes_id = s.id\n" +
                    "JOIN size AS sz ON sd.size_id = sz.id\n" +
                    "JOIN color AS c ON sd.color_id = c.id\n" +
                    "JOIN brand AS b ON sd.brand_id = b.id\n" +
                    "JOIN discount_details AS dsd \n" +
                    "ON sd.shoes_id  = dsd.shoes_id and dsd.status = 1 and dsd.brand_id = b.id\n" +
                    "JOIN discount AS d \n" +
                    "ON dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1\n" +
                    "JOIN (\n" +
                    "    SELECT *\n" +
                    "    FROM (\n" +
                    "        SELECT *,\n" +
                    "               ROW_NUMBER() OVER(PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
                    "        FROM shoes_file_upload_mapping\n" +
                    "    ) AS shoes_file_upload_mapping\n" +
                    "    WHERE rn = 1\n" +
                    ") AS sfum ON sd.id = sfum.shoes_details_id\n" +
                    "JOIN file_upload AS fu ON sfum.file_upload_id = fu.id\n" +
                    "ORDER BY dsd.created_date DESC\n" +
                    "LIMIT 10;\n",
            nativeQuery = true
    )
    List<ShoesDetailDTOCustom> getNewDiscountShoesDetail();

    @Query(
            value = "SELECT \n" +
                    "    fu.path,\n" +
                    "    sd.price,\n" +
                    "    s.name,\n" +
                    "    sd.id AS shoesdetailid,\n" +
                    "    s.id AS idsh,\n" +
                    "    sz.id AS idsz,\n" +
                    "    c.id AS idc,\n" +
                    "    b.id AS idb,\n" +
                    "    sum(od.quantity) as totalQuantity\n" +
                    "FROM \n" +
                    "    shoes_details sd\n" +
                    "JOIN \n" +
                    "    shoes s ON sd.shoes_id = s.id\n" +
                    "JOIN \n" +
                    "    size sz ON sd.size_id = sz.id\n" +
                    "JOIN \n" +
                    "    color c ON sd.color_id = c.id\n" +
                    "JOIN \n" +
                    "    brand b ON sd.brand_id = b.id\n" +
                    "JOIN order_details od on od.shoes_details_id = sd.id\n" +
                    "JOIN (\n" +
                    "    SELECT \n" +
                    "        sfum.*,\n" +
                    "        ROW_NUMBER() OVER (PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
                    "    FROM \n" +
                    "        shoes_file_upload_mapping sfum\n" +
                    ") sfum ON sd.id = sfum.shoes_details_id\n" +
                    "JOIN \n" +
                    "    file_upload fu ON sfum.file_upload_id = fu.id \n" +
                    "WHERE rn = 1 \n" +
                    "GROUP BY sd.id\n" +
                    "order by totalQuantity desc\n" +
                    "limit 10\n",
            nativeQuery = true
    )
    List<ShoesDetailDTOCustom> getBestSeller();
}
