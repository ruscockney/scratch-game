package ru.assignment.parser;

import ru.assignment.model.config.Config;

public interface ConfigParser {
    Config parse(String configPath);
}
