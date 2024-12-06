package sudoku.Configuration;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

public class CorsConfig implements CorsConfigurationSource {

    private final List<String> allowedOrigins = List.of(
            "http://localhost:[*]",
            "http://192.168.28.122:[*]",
            "http://10.58.83.161:[*]",
            "http://192.168.1.107:[*]",
            "http://192.168.1.106:[*]",
            "http://172.31.6.155:[*]"
    );

    private final List<String> allowedMethods = List.of(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "OPTIONS"
    );

    private final List<String> allowedHeaders = List.of("*");

    @SuppressWarnings("null")
    @Override
    public org.springframework.web.cors.CorsConfiguration getCorsConfiguration(
            HttpServletRequest request
    ) {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.setAllowedOriginPatterns(allowedOrigins);
        cors.setAllowedMethods(allowedMethods);
        cors.setAllowedHeaders(allowedHeaders);

        return cors;
    }
}
