package in.karthickprassana.urlshortener.utils;

import com.maxmind.geoip2.DatabaseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoIPReader {

    private final DatabaseReader reader;

    public 
}
