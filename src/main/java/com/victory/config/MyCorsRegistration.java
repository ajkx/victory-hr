package com.victory.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

/**
 * Created by ajkx
 * Date: 2017/9/5.
 * Time:10:22
 */
public class MyCorsRegistration extends CorsRegistration{
    /**
     * Create a new {@link CorsRegistration} that allows all origins, headers, and
     * credentials for {@code GET}, {@code HEAD}, and {@code POST} requests with
     * max age set to 1800 seconds (30 minutes) for the specified path.
     *
     * @param pathPattern the path that the CORS configuration should apply to;
     *                    exact path mapping URIs (such as {@code "/admin"}) are supported as well
     *                    as Ant-style path patterns (such as {@code "/admin/**"}).
     */
    public MyCorsRegistration(String pathPattern) {
        super(pathPattern);
    }

    @Override
    protected CorsConfiguration getCorsConfiguration() {
        return super.getCorsConfiguration();
    }
}
