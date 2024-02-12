## How to run ##
`java -jar scratch-game.jar --config config.json --betting-amount 100`

## How to build ##
`./mvnw clean package`

Result in `target/scratch-game.jar`

## Implementation ##

- `parser.ConfigParser` parses input json config;
- `processor.SymbolSelector.class` generates symbol by probability map;
- `service.MatrixGenerator.class` generates matrix using `processor.SymbolSelector.class` for each cell;
- `service.RewardCounter.class` counts reward by matrix and config;
- `detectors.WinDetector.class` detect win combinations for each symbol and groups them by group name from config. Has 2 implementations. `detectors.LinearSymbolsWinDetector.class` detects win combinations for linear_symbols type (horizontally, vertically, diagonally). `detectors.SameSymbolsWinDetector.class` detects win combinations for same_symbols type;
- `facade.GameFacade.class` uses above classes in needing order. Generates matrix, detects win combinations, combines them in 1 map, count reward;
- `AppRunner.class` parses config, creates all necessary objects (because we don't use Spring) and runs `GameFacade.class`.

## Description ##

- The program logs result json. So you can see it in CLI;
- This version of game generates just 1 bonus symbol for every size of matrix. The needing amount of bonus symbols is not clear from issue. And I decided to generate 1 because there are just 1 bonus symbol in output example;
- When program choose the best combination for same_symbol type in `SameSymbolsWinDetector.class` it compares by `count` from config. For `LinearSymbolsWinDetector.class` I use `reward_multiplier` as comparator;
- The program needs config validation, but I don't implement it;
- It will be good to implement more complicated exception handling and logging.
