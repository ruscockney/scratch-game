package ru.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.assignment.facade.GameFacade;
import ru.assignment.facade.GameFacadeImpl;
import ru.assignment.model.Args;
import ru.assignment.model.config.Config;
import ru.assignment.model.output.Result;
import ru.assignment.parser.ArgsParser;
import ru.assignment.parser.ArgsParserImpl;
import ru.assignment.parser.ConfigParser;
import ru.assignment.parser.ConfigParserImpl;
import ru.assignment.processor.SymbolSelector;
import ru.assignment.processor.SymbolSelectorImpl;
import ru.assignment.service.MatrixGenerator;
import ru.assignment.service.MatrixGeneratorImpl;
import ru.assignment.service.RewardCounter;
import ru.assignment.service.RewardCounterImpl;

import java.util.Random;

@Slf4j
public class AppRunner {

    //create all necessary objects, create Config object and execute GameFacade
    @SneakyThrows
    public static void run(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Random random = new Random();

        ArgsParser argsParser = new ArgsParserImpl();
        Args argsPojo = argsParser.parse(args);

        ConfigParser configParser = new ConfigParserImpl(objectMapper);
        Config config = configParser.parse(argsPojo.getConfigPath());

        SymbolSelector symbolSelector = new SymbolSelectorImpl(random);

        MatrixGenerator matrixGenerator = new MatrixGeneratorImpl(config, random, symbolSelector);

        RewardCounter rewardCounter = new RewardCounterImpl(config);

        GameFacade gameFacade = new GameFacadeImpl(config, matrixGenerator, rewardCounter);
        Result result = gameFacade.execute(argsPojo.getBet());

        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
}
