import Model.Question;
import Model.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Path pathTest = Path.of("Test.txt");
        Test test = new Test(pathTest);
        Scanner scanner = new Scanner(System.in);
        String answer;
        int sum = 0;

        System.out.println("Начало теста");
        for (int i = 0; i < test.getQuestionListSize(); i++) {
            System.out.println(test.showCase(i));
            answer = scanner.nextLine();
            sum += test.checkAnswer(answer, i);
            System.out.println();
        }
        System.out.println("Результат: "+ sum +" из "+ test.getQuestionListSize() + " правильных ответов");
        System.out.println("Конец");

        test.saveTest("f");
    }
}