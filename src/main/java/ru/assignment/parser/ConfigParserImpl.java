package ru.assignment.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.assignment.model.config.Config;

import java.io.File;

@AllArgsConstructor
public class ConfigParserImpl implements ConfigParser {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Config parse(String configPath) {
        return objectMapper.readValue(new File(configPath), Config.class);
    }
}
