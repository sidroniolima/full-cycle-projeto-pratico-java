package br.com.sidroniolima.admin.infrastructure.genre.persistence;

import br.com.sidroniolima.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreJpaEntity, String> {
    Page<GenreJpaEntity> findAll(Specification<GenreJpaEntity> whereClause, Pageable page);
}
