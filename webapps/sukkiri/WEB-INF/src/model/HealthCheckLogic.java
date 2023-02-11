package model;

public class HealthCheckLogic {
  public void execute(Health health) {
    double weight = health.getWeight();
    double height = health.getHeight();
    double bmi = weight / (height / 100.0 * height / 100.0);
    health.setBmi(bmi);

    String bodyType;
    if (bmi < 18.5) {
      bodyType = "痩せ型";
    } else if (bmi < 25) {
      bodyType = "普通";
    } else {
      bodyType = "肥満";
    }
    health.setBodyType(bodyType);
  }
}
