package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Test {

    private List<Question> questionList;

    public Test(Path path) {
        try {
            questionList = new ArrayList<>();
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i += 3) { //Цикл пока не закончятся скроки в файле
                String question = lines.get(i); //Вытаскиние вопроса из списка строк из файла
                if (lines.get(i + 1).toString().contains("//")){ //Проверка на наличие у вопроса вариантов ответа
                    String[] answers = lines.get(i + 1).toString().split("//");//Разделение вариантов ответа
                    Set<String> answersSet = new HashSet<>(Arrays.asList(answers));
                    String rightAnswer = lines.get(i + 2).toString().trim();
                    questionList.add(new Question(question, answersSet, rightAnswer));
                } else { //Если ответов нет
                    String rightAnswer = lines.get(i + 1).toString().trim();
                    questionList.add(new Question(question, rightAnswer));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTest(String question,Set<String> answersSet,String rightAnswer){
        if (answersSet != null) {
            questionList.add(new Question(question, answersSet, rightAnswer));
        } else {
            questionList.add(new Question(question, rightAnswer));
        }
    }

    public int checkAnswer(String answer,int index){
        String rightAnswer = questionList.get(index).getRightAnswer();
        if (rightAnswer.equalsIgnoreCase(answer)) {
            return 1;
        } else {
            return 0;
        }
    }

    private String showQuestion(int index){
        return questionList.get(index).getQuestion();
    }

    //вывод ответов на вопрос(если есть)
    private String showAnswers(int index){
        if (questionList.get(index).getAnswers() != null) {
            return questionList.get(index).getAnswers().toString();
        } else {
            return "";
        }
    }

    public String showCase(int index){
        return showQuestion(index) +"\n"+ showAnswers(index);
    }

    public int getQuestionListSize (){
        return questionList.size();
    }

    public void saveTest(String name) throws IOException {
        Path path = Path.of("d:\\Test");
        if (!Files.exists(path)){
            Files.createDirectory(path);
        }
        Path pathTest = Path.of(path+ "\\" + name + ".tst");
        if (!Files.exists(pathTest)) {
            Files.createFile(pathTest);
        }
        String contains;//Общая строка для записи в файл
        for (int i = 0; i < questionList.size(); i++) {
            contains = questionList.get(i).getQuestion().toString() + "\n";// вопрос
            if (questionList.get(i).getAnswers() != null) { // есть ли варианты ответов вообще?
                String[] answers = questionList.get(i).getAnswers().toArray(new String[questionList.get(i).getAnswers().size()]); //список вариантов
                for (int j = 0; j < questionList.get(i).getAnswers().size(); j++) {
                    contains += answers[j] + "\\\\";
                }
                contains += "\n";
            }
            contains += questionList.get(i).getRightAnswer().toString() + "\n";
            Files.writeString(pathTest, contains, StandardOpenOption.APPEND);// запись в файл с добавлением в конец файла без его перезаписи
        }
    }
}

