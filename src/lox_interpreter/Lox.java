package lox_interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String arg) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(arg));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError)
            System.exit(65);
    }
    private static void runPrompt() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        for(;;) {
            System.out.print("> ");
            run(bufferedReader.readLine());
            hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {
        report (line, ": ", message);
    }

    private static void report(int Line, String where, String message) {
        System.err.println(
                String.format("[Line %d] Error %s : %s ", Line, where, message)
        );
        hadError = true;
    }
}
