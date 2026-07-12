package in.karthickprassana.urlshortener.url.repository;

import in.karthickprassana.urlshortener.url.entity.ClickEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    Optional<List<ClickEvent>> findByUrlId(Long urlId, Pageable pageable);
}
