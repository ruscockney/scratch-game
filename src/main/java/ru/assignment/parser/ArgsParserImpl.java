package ru.assignment.parser;

import ru.assignment.model.Args;

public class ArgsParserImpl implements ArgsParser {

    private static final String CONFIG_ARG = "--config";
    private static final String BET_ARG = "--betting-amount";

    @Override
    public Args parse(String[] args) {
        Args argsPojo = new Args();
        int idx = 0;
        while (idx < args.length) {
            switch (args[idx]) {
                case CONFIG_ARG:
                    argsPojo.setConfigPath(args[idx + 1]);
                    break;
                case BET_ARG:
                    argsPojo.setBet(Double.parseDouble(args[idx + 1]));
                    break;
                default:
                    break;
            }
            idx += 2;
        }
        return argsPojo;
    }
}
