package br.com.gabrieudev.emporium.infrastructrure.web;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.stripe.exception.StripeException;

import br.com.gabrieudev.emporium.application.exceptions.BadCredentialsException;
import br.com.gabrieudev.emporium.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.InvalidCouponException;
import br.com.gabrieudev.emporium.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.exceptions.TransactionFailedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardException> handleUserNotFoundException(EntityNotFoundException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardException> handleBadCredentialsException(BadCredentialsException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<StandardException> handleUserAlreadyExistsException(EntityAlreadyExistsException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StandardException> handleInvalidTokenException(InvalidTokenException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StandardException standardException = new StandardException(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<StandardException> handleStripeException(StripeException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<StandardException> handleTransactionFailedException(TransactionFailedException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCouponException.class)
    public ResponseEntity<StandardException> handleInvalidCouponException(InvalidCouponException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.CONFLICT);
    }
}
