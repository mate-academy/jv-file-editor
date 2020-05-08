package core.basesyntax.service;

public interface ConsoleService {
    void create(String... args);

    void execDefaultCommand(String... args);

    void exit();

    void help(String... args);

    void info(String... args);

    void read(String... args);
}
