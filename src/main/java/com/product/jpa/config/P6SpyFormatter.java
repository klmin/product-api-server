package com.product.jpa.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class P6SpyFormatter implements MessageFormattingStrategy {

    @PostConstruct
    public void init() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        sql = formatSql(category, sql);
        return String.format("[%s] | %d ms | %s", category, elapsed, formatSql(category, sql));
    }

    private String formatSql(String category, String sql) {

        if (StringUtils.isNotBlank(sql) && Category.STATEMENT.getName().equals(category)) {

            String trimmedSQL = sql.trim().toLowerCase(Locale.ROOT);

            if (trimmedSQL.startsWith("create") || trimmedSQL.startsWith("alter") || trimmedSQL.startsWith("comment")) {
                return FormatStyle.DDL.getFormatter().format(sql);
            }

            return  FormatStyle.BASIC.getFormatter().format(sql);

        }

        return sql;
    }
}

