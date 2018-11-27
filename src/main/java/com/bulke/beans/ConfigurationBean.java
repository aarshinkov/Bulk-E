package com.bulke.beans;

import com.bulke.base.Base;
import com.bulke.beans.settings.EmailSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;

public class ConfigurationBean extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EmailSettings emailSettings;

    private String env = "TEST";

    public ConfigurationBean() {
    }

    @PostConstruct
    private void init() {
        log.debug("Loading configuration params...");
//        emailSettings = loadEmailSettings();
        log.debug("Configuration params loaded.");
    }

    public EmailSettings getEmailSettings() {
        return emailSettings;
    }

    @Async
    public EmailSettings loadEmailSettings() {
        EmailSettings emailSettings = new EmailSettings();

        try {
            String sql = "SELECT name, value FROM SYS_PARAMS WHERE name LIKE 'MAIL.%'";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

            while (rs.next()) {
                if ("MAIL.HOST".equalsIgnoreCase(rs.getString("name"))) {
                    emailSettings.setHost(rs.getString("value"));
                    log.debug("MAIL.HOST = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.PORT".equalsIgnoreCase(rs.getString("name"))) {
                    emailSettings.setPort(rs.getInt("value"));
                    log.debug("MAIL.PORT = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.PROTOCOL".equalsIgnoreCase(rs.getString("name"))) {
                    emailSettings.setProtocol(rs.getString("value"));
                    log.debug("MAIL.PROTOCOL = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.SENDER".equalsIgnoreCase(rs.getString("name"))) {
                    emailSettings.setSender(rs.getString("value"));
                    log.debug("MAIL.SENDER = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.USERNAME".equalsIgnoreCase(rs.getString("name"))) {
                    emailSettings.setUsername(rs.getString("value"));
                    log.debug("MAIL.USERNAME = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.PASSWORD".equalsIgnoreCase(rs.getString("name"))) {
                    emailSettings.setPassword(rs.getString("value"));
                    log.debug("MAIL.PASSWORD = " + rs.getString("value"));
                    //continue
                }

//                if ("MAIL.DELAY_CHECK".equalsIgnoreCase(rs.getString("name")))
//                {
//                    emailSettings.setDelayCheck(rs.getInt("value"));
//                    log.debug("MAIL.DELAY_CHECK = " + rs.getString("value"));
//                    continue;
//                }
//
//                if ("MAIL.INITIAL_DELAY".equalsIgnoreCase(rs.getString("name")))
//                {
//                    emailSettings.setInitialDelay(rs.getInt("value"));
//                    log.debug("MAIL.INITIAL_DELAY = " + rs.getString("value"));
//                    continue;
//                }
            }
        } catch (Exception e) {
            log.error("Error loading e-mail configuration from database!", e);
        }
        return emailSettings;
    }

    public String getEnv() {
        return env;
    }

    public String getEnvStyled() {
        return this.env.equalsIgnoreCase("TEST") ? "Test" : "Production";
    }
}
