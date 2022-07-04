package com.rafaelzabotto.dscatalog.repositories;

import com.rafaelzabotto.dscatalog.entities.Category;
import com.rafaelzabotto.dscatalog.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    //Associação muitos para muitos é um pouco mais trabalhosa no JPQL
    @Query("SELECT DISTINCT obj " +
            "FROM Product obj INNER JOIN obj.categories cats " +
            "WHERE (COALESCE(:categories) IS NULL OR cats IN :categories) " +
            "AND (LOWER(obj.name) LIKE LOWER(CONCAT('%', :name, '%')) ) ")

    Page<Product> find(List<Category> categories, String name, Pageable pageable);

}
