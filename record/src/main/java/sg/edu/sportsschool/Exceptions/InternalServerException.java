package sg.edu.sportsschool.Exceptions;

public class InternalServerException extends RuntimeException{
  public InternalServerException(String message) {
    super(message);
  }
}
