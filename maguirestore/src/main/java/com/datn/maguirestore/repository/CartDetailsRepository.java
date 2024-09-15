package com.datn.maguirestore.repository;

import com.datn.maguirestore.dto.CartDetailDTO;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.CartDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {
    @Query("SELECT c " +
            "FROM CartDetails c " +
            "WHERE c.cart.id = :id")
    Page<CartDetails> getAllByCart(Long id, Pageable pageable);

    @Query(
            value = "SELECT \n" +
                    "    cd.*, fu.path, sd.price, s.name AS nameshoes,\n" +
                    "    sz.name AS namesize, c.name AS namecolor, sd.id AS shoesdetailid, sd.quantity AS quantityShoesDetail,\n" +
                    "    s.id AS idsh, sz.id AS idsz, c.id AS idc, b.id AS idb,\n" +
                    "    coalesce(max(discount_method),min(discount_method)) AS discountmethod,\n" +
                    "    max(\n" +
                    "    case\n" +
                    "    when discount_method is not null && (discount_method = 3 || discount_method = 4)  then dsd.discount_amount\n" +
                    "    else null\n" +
                    "    end \n" +
                    "    ) as discountamount_3_4,\n" +
                    "    coalesce(max(d.discount_amount),min(d.discount_amount)) AS discountamount_1_2\n" +
                    "FROM \n" +
                    "    cart_details cd\n" +
                    "JOIN \n" +
                    "    shoes_details sd ON cd.shoes_details_id = sd.id\n" +
                    "JOIN \n" +
                    "    shoes s ON sd.shoes_id = s.id\n" +
                    "JOIN \n" +
                    "    size sz ON sd.size_id = sz.id\n" +
                    "JOIN \n" +
                    "    color c ON sd.color_id = c.id\n" +
                    "JOIN \n" +
                    "    brand b ON sd.brand_id = b.id\n" +
                    "LEFT JOIN discount_details\n" +
                    "    dsd ON dsd.shoes_id = sd.shoes_id \n" +
                    "    AND dsd.status = 1 \n" +
                    "    AND dsd.brand_id = b.id\n" +
                    "LEFT JOIN \n" +
                    "    discount d ON dsd.discount_id = d.id \n" +
                    "    AND d.start_date <= NOW() \n" +
                    "    AND d.end_date >= NOW() \n" +
                    "    AND d.status = 1\n" +
                    "JOIN (\n" +
                    "    WITH shoes_file_upload_mapping AS (\n" +
                    "        SELECT \n" +
                    "            *, ROW_NUMBER() OVER (PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
                    "        FROM \n" +
                    "            shoes_file_upload_mapping\n" +
                    "    )\n" +
                    "    SELECT * FROM shoes_file_upload_mapping\n" +
                    ") sfum ON sd.id = sfum.shoes_details_id\n" +
                    "JOIN \n" +
                    "    file_upload fu ON sfum.file_upload_id = fu.id \n" +
                    "WHERE \n" +
                    "    cd.cart_id = :idCart \n" +
                    "    AND rn = 1\n" +
                    "GROUP BY sd.id\n",
            nativeQuery = true
    )
    List<CartDetailDTO> findCartDetailsByCart_Id(@Param("idCart") Long idCart);

    List<CartDetails> findCartDetailsByCart(Cart cart);
}
