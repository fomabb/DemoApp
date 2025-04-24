package org.fomabb.demo.exceptionhandler.exception;

/**
 * Исключение, представляющее ошибки бизнес-логики.
 * <p>
 * Это исключение может быть выброшено, когда возникает ошибка, связанная с бизнес-правилами
 * или логикой приложения. Упаковка сообщений об ошибках в это исключение позволяет централизованно
 * обрабатывать бизнес-ошибки в приложении.
 *
 * @author Ваше Имя
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
