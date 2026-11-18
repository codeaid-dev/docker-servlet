package model;

public class Question {
  private int id;
  private int categoryId;
  private String categoryName;
  private String question;
  private String choice1;
  private String choice2;
  private String choice3;
  private String choice4;
  private int correctAnswer;
  private String explanation;
  public Question(
          int id,
          int categoryId,
          String categoryName,
          String question,
          String choice1,
          String choice2,
          String choice3,
          String choice4,
          int correctAnswer,
          String explanation) {
    this.id = id;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.question = question;
    this.choice1 = choice1;
    this.choice2 = choice2;
    this.choice3 = choice3;
    this.choice4 = choice4;
    this.correctAnswer = correctAnswer;
    this.explanation = explanation;
  }
  public int getId() {
    return id;
  }
  public int getCategoryId() {
    return categoryId;
  }
  public String getCategoryName() {
    return categoryName;
  }
  public String getQuestion() {
    return question;
  }
  public String getChoice1() {
    return choice1;
  }
  public String getChoice2() {
    return choice2;
  }
  public String getChoice3() {
    return choice3;
  }
  public String getChoice4() {
    return choice4;
  }
  public int getCorrectAnswer() {
    return correctAnswer;
  }
  public String getExplanation() {
    return explanation;
  }
}
