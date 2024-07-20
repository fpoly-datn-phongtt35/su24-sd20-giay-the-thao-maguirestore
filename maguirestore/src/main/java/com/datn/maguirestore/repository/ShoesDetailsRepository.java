package com.datn.maguirestore.repository;

import com.datn.maguirestore.dto.ShopShoesDTO;
import com.datn.maguirestore.entity.ShoesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


//    @Query(
//            nativeQuery = true,
//            value = "    \n" +
//                    "SELECT\n" +
//                    "  size_ids,\n" +
//                    "  size_names,\n" +
//                    "  color_ids,\n" +
//                    "  color_names,\n " +
//                    "  sz.name as size_name,\n" +
//                    "  cl.name as color_name ," +
//                    "  sd.*,\n" +
//                    "  CONCAT(br.name, ' ', sh.name) as name,\n" +
//                    "  iu.path,\n" +
//                    "  GROUP_CONCAT(iu.path) as paths ,\n " +
//                    "d.name as discount_name ," +
//                    "d.discount_method as discount_method ,  " +
//                    "dsd.discount_amount as discount_amount ,  " +
//                    "dsd.discount_amount as discount_amount_3_4 ,  " +
//                    " (SELECT CAST(COALESCE(avg(fb.rate), 0) AS SIGNED)\n" +
//                    "FROM feed_back fb\n" +
//                    "JOIN shoes_details ad ON fb.shoes_id in (select id from shoes_details where brand_id = sd.brand_id and shoes_id = sd.shoes_id)  AND fb.status = 1 ) as rating\n" +
//                    "FROM\n" +
//                    "    (\n" +
//                    "        SELECT\n" +
//                    "            GROUP_CONCAT(DISTINCT sz.id order by sz.id) as size_ids,\n" +
//                    "            GROUP_CONCAT(DISTINCT sz.name order by sz.id) as size_names\n" +
//                    "        FROM\n" +
//                    "            `shoes-store`.shoes_details sd\n" +
//                    "            JOIN `shoes-store`.size sz ON sd.size_id = sz.id\n" +
//                    "            JOIN `shoes-store`.color cl ON sd.color_id = cl.id and cl.id = :clid \n" +
//                    "        WHERE\n" +
//                    "            sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1\n" +
//                    "    ) size_subquery,\n" +
//                    "    (\n" +
//                    "        SELECT\n" +
//                    "            GROUP_CONCAT(DISTINCT cl.id order by cl.id) as color_ids,\n" +
//                    "            GROUP_CONCAT(DISTINCT cl.name order by cl.id) as color_names\n" +
//                    "        FROM\n" +
//                    "            `shoes-store`.shoes_details sd\n" +
//                    "            JOIN `shoes-store`.color cl ON sd.color_id = cl.id\n" +
//                    "        WHERE\n" +
//                    "            sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1\n" +
//                    "    ) color_subquery\n" +
//                    "JOIN `shoes-store`.shoes_details sd ON 1 = 1\n" +
//                    "JOIN `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id\n" +
//                    "JOIN `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id\n" +
//                    "JOIN `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
//                    "JOIN `shoes-store`.brand br ON sd.brand_id = br.id \n" +
//                    "JOIN `shoes-store`.size sz ON sd.size_id = sz.id and (:siid IS NULL OR sz.id = :siid) \n" +
//                    "JOIN `shoes-store`.color cl ON sd.color_id = cl.id and cl.id = :clid \n" +
//                    "LEFT JOIN `shoes-store`.discount_details dsd\n" +
//                    "ON dsd.shoes_id = sd.shoes_id and dsd.status = 1 and dsd.brand_id = sd.brand_id\n" +
//                    "LEFT JOIN `shoes-store`.discount d \n" +
//                    "ON dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1 " +
//                    "WHERE  " +
//                    "sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1;\n"
//    )
//    ShopShoesDTO findDistinctByShoesAndBrandOrderBySellPriceDescOne(
//            @Param("shid") Integer shid,
//            @Param("brid") Integer brid,
//            @Param("siid") Integer siid,
//            @Param("clid") Integer clid
//    );


}
