package in.karthickprassana.urlshortener.url.repository;

import in.karthickprassana.urlshortener.url.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {

    Optional<URL> findShortenedURL(String url);
    boolean existsURLByShortenedURL(String url);
}
