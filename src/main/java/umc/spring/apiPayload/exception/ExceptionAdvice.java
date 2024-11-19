package umc.spring.apiPayload.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.apiPayload.code.ErrorReasonDTO;
import umc.spring.apiPayload.code.status.ErrorStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j // Lombok을 사용한 로깅 설정
@RestControllerAdvice(annotations = {RestController.class}) // REST 컨트롤러에서 발생하는 예외만 처리(REST 컨트롤러 전역적으로 예외를 처리하는 어노테이션)
// ResponseEntityExceptionHandler 를 상속받아, Spring에서 제공하는 기본적인 예외 처리 방식을 사용할 수 있다.
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * 객체의 유효성 검사 실패 시 발생하는 메서드로 예외 메시지를 추출하여, 적절한 HTTP 응답을 반환한다.
     * @param e ConstraintViolationException은 Bean Validation(ex. @valid)에서 발생하는 예외로, 객체의 유효성 검사 실패 시 발생.
     * @param request
     * @return
     */
    @ExceptionHandler // 특정 예외를 처리하는 메서드를 지정한다. 예외가 발생하면 해당 메서드가 호출되어 예외를 처리.
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage), HttpHeaders.EMPTY,request);
    }

    /**
     * @Valid 또는 @Validated로 요청을 검증할 떄 발생하는 예외로 필드 오류 메시지를 errors 맵에 담아서, handleExceptionInternalArgs 메서드를 호출하여 응답을 반환.
     * @param e the exception to handle - MethodArgumentNotValidException는 @Valid 또는 @Validated로 요청을 검증할 때 발생하는 예외.
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(e,HttpHeaders.EMPTY,ErrorStatus.valueOf("_BAD_REQUEST"),request,errors);
    }

    /**
     * 일반적인 Exception을 처리하는 메서드로, 예외가 발생하면 예외의 printStackTrace()를 출력하고 , handleExceptionInternalFalse 메서드를 호출하여 응답을 반환.
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        e.printStackTrace();

        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(),request, e.getMessage());
    }

    /**
     * 이 예외는 ErrorStatus를 기반으로 처리됩니다. 예외의 ErrorReasonDTO를 가져와 handleExceptionInternal을 호출하여 응답을 반환합니다.
     * @param generalException 사용자 정의 예외
     * @param request
     * @return
     */
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException,errorReasonHttpStatus,null,request);
    }

    // handleExceptionInternal 메서드들
    // handleExceptionInternal은 예외가 발생했을 때 실제로 HTTP 응답을 생성하는 핵심 메서드입니다. 이 메서드들은 ApiResponse 객체를 사용하여 응답을 구성합니다.

    /**
     * 이 메서드는 기본적인 예외 처리를 수행하며, 응답 바디를 ApiResponse.onFailure()로 구성하여 반환합니다.
     * @param e
     * @param reason
     * @param headers
     * @param request
     * @return
     */
    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(),reason.getMessage(),null);
//        e.printStackTrace();

        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                reason.getHttpStatus(),
                webRequest
        );
    }

    /**
     * 이 메서드는 일반 예외 처리로, ApiResponse.onFailure()를 사용하여 응답을 생성하고, 예외가 발생한 포인트를 바디에 담아 반환합니다.
     * @param e
     * @param errorCommonStatus
     * @param headers
     * @param status
     * @param request
     * @param errorPoint
     * @return
     */
    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorPoint);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                status,
                request
        );
    }

    /**
     * 이 메서드는 기본적인 예외 처리를 수행하며, 응답 바디를 ApiResponse.onFailure()로 구성하여 반환.
     * @param e
     * @param headers
     * @param errorCommonStatus
     * @param request
     * @param errorArgs
     * @return
     */
    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorArgs);
        return super.handleExceptionInternal(
                e, // 발생한 예외 객체
                body, // 응답 본문 (ApiResponse 객체)
                headers, // HTTP 헤더
                errorCommonStatus.getHttpStatus(), // HTTP 상태 코드
                request // 웹 요청 정보
        );
    }

    /**
     * ConstraintViolationException을 처리하는 메서드로, ApiResponse.onFailure()를 사용하여 응답을 반환.
     * @param e
     * @param errorCommonStatus
     * @param headers
     * @param request
     * @return
     */
    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }
}