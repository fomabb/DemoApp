package org.fomabb.demo.exceptionhandler.exception;

/**
 * Исключение, представляющее ошибки валидации данных.
 * <p>
 * Это исключение выбрасывается, когда входные данные не соответствуют необходимым
 * критериям или правилам валидации. Оно позволяет централизованно обрабатывать
 * ошибки валидации в приложении.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
