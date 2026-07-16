//package in.karthickprassana.urlshortener.utils;
//
//import com.maxmind.geoip2.DatabaseReader;
//import com.maxmind.geoip2.model.CountryResponse;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ResourceUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.InetAddress;
//
//@Service
//public class GeoIPService {
//
//    private final DatabaseReader reader;
//
//    public GeoIPService() throws IOException {
//        File database = ResourceUtils.getFile(
//                "classpath:GeoLite2-Country.mmdb");
//
//        reader = new DatabaseReader.Builder(database).build();
//    }
//
//    public String getCountry(String ip) {
//
//        try {
//
//            InetAddress ipAddress = InetAddress.getByName(ip);
//
//            CountryResponse response =
//                    reader.country(ipAddress);
//
//            return response.country().name();
//
//        } catch (Exception e) {
//            return "Unknown";
//        }
//    }
//}