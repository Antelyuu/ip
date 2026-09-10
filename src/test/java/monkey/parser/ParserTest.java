package monkey.parser;

import org.junit.jupiter.api.Test;

import monkey.command.SnoozeCommand;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ParserTest {
    @Test
    void parseAction_snoozeCommand_createsSnoozeCommand() {
        assertInstanceOf(SnoozeCommand.class, new Parser().parseAction("snooze 1 2099-02-01"));
    }
}
