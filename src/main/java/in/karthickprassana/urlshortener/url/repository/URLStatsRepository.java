package in.karthickprassana.urlshortener.url.repository;

import in.karthickprassana.urlshortener.url.entity.URLStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLStatsRepository extends JpaRepository<URLStats, Long> {
    Optional<URLStats> findByUrlId(Long urlId);
}
