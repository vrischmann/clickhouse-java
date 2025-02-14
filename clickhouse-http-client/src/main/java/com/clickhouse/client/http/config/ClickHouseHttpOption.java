package com.clickhouse.client.http.config;

import com.clickhouse.config.ClickHouseOption;
import com.clickhouse.data.ClickHouseChecker;

import java.io.Serializable;

/**
 * Http client options.
 */
public enum ClickHouseHttpOption implements ClickHouseOption {
    /**
     * HTTP connection provider.
     */
    CONNECTION_PROVIDER("http_connection_provider", HttpConnectionProvider.APACHE_HTTP_CLIENT,
            "APACHE HTTP CLIENT connection provider. HTTP_CLIENT is only supported in JDK 11 or above."),
    /**
     * Custom HTTP headers.
     */
    CUSTOM_HEADERS("custom_http_headers", "", "Custom HTTP headers."),
    /**
     * Custom HTTP query parameters. Consider
     * {@link com.clickhouse.client.config.ClickHouseClientOption#CUSTOM_SETTINGS}
     * if you don't want your implementation ties to http protocol.
     */
    CUSTOM_PARAMS("custom_http_params", "", "Custom HTTP query parameters."),
    /**
     * Default server response.
     */
    DEFAULT_RESPONSE("http_server_default_response", "Ok.\n",
            "Default server response, which is used for validating connection."),
    /**
     * Whether to enable keep-alive or not.
     */
    KEEP_ALIVE("http_keep_alive", true, "Whether to use keep-alive or not"),
    /**
     * Max open connections apply with Apache HttpClient only.
     */
    MAX_OPEN_CONNECTIONS("max_open_connections", 10, "Max open connections apply with Apache HttpClient only."),
    /**
     * Whether to receive information about the progress of a query in response
     * headers.
     */
    RECEIVE_QUERY_PROGRESS("receive_query_progress", true,
            "Whether to receive information about the progress of a query in response headers."),
    /**
     * Indicates whether http client would send its identification through Referer header to server.
     * Valid values:
     *      1. empty string - nothing is sent
     *      2. IP_ADDRESS - client's IP address is used
     *      3. HOST_NAME - host name is used
     */
    SEND_HTTP_CLIENT_ID("send_http_client_id", "", "Indicates whether http client would send its identification through Referer header to server. " +
            "Valid values: empty string - nothing is sent. IP_ADDRESS - client's IP address is used. HOST_NAME - host name is used."),

    // SEND_PROGRESS("send_progress_in_http_headers", false,
    // "Enables or disables X-ClickHouse-Progress HTTP response headers in
    // clickhouse-server responses."),
    // SEND_PROGRESS_INTERVAL("http_headers_progress_interval_ms", 3000, ""),
    // WAIT_END_OF_QUERY("wait_end_of_query", false, ""),

    /**
     * Whether to remember last set role and send them in every next requests as query parameters.
     * Only one role can be set at a time.
     */
    REMEMBER_LAST_SET_ROLES("remember_last_set_roles", false,
            "Whether to remember last set role and send them in every next requests as query parameters."),

    /**
     * The time in milliseconds after which the connection is validated after inactivity.
     * Default value is 5000 ms. If set to negative value, the connection is never validated.
     * It is used only for Apache Http Client connection provider.
     */
    AHC_VALIDATE_AFTER_INACTIVITY("ahc_validate_after_inactivity", 5000L,
            "The time in milliseconds after which the connection is validated after inactivity."),
    ;

    private final String key;
    private final Serializable defaultValue;
    private final Class<? extends Serializable> clazz;
    private final String description;
    private final boolean sensitive;

    <T extends Serializable> ClickHouseHttpOption(String key, T defaultValue, String description) {
        this(key, defaultValue, description, false);
    }

    <T extends Serializable> ClickHouseHttpOption(String key, T defaultValue, String description, boolean sensitive) {
        this.key = ClickHouseChecker.nonNull(key, "key");
        this.defaultValue = ClickHouseChecker.nonNull(defaultValue, "defaultValue");
        this.clazz = defaultValue.getClass();
        this.description = ClickHouseChecker.nonNull(description, "description");
        this.sensitive = sensitive;
    }

    @Override
    public Serializable getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Class<? extends Serializable> getValueType() {
        return clazz;
    }

    @Override
    public boolean isSensitive() {
        return sensitive;
    }
}
