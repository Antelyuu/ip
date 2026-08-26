# Monkey project template

This is a project template for a greenfield Java project. It was initially named after the Java mascot _Duke_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/Monkey.java` file, right-click it, and choose `Run Monkey.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
    __  __              _
   |  \/  | ___  _ __  | | _____ _   _
   | |\/| |/ _ \| '_ \ | |/ / _ \ | | |
   | |  | | (_) | | | ||   <  __/ |_| |
   |_|  |_|\___/|_| |_||_|\_\___|\__, |
                                 |___/
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Command-line UI tests

The project-specific `test-ui` skill uses the test cases in
[`test/ui-test-plan.md`](test/ui-test-plan.md). From the repository root,
compile the application and run the plan with:

```bash
rm -rf /tmp/monkey-ui-classes
mkdir -p /tmp/monkey-ui-classes
javac -d /tmp/monkey-ui-classes $(find src/main/java -name '*.java')
python3 .codex/skills/test-ui/scripts/run-ui-tests.py \
  --command 'java -cp /tmp/monkey-ui-classes monkey.Monkey' \
  --plan test/ui-test-plan.md
```

The runner prints the console session and stops at the first failed test.
