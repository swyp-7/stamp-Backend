package com.stamp.global;

import static org.assertj.core.api.Assertions.assertThat;

import com.stamp.global.exception.Error;
import com.stamp.global.exception.GlobalErrorCode;
import com.stamp.global.response.ApplicationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("컨트롤러 테스트 - 공통 응답 테스트")
public class ResponseControllerTest {

    @DisplayName("데이터가 없는 성공 응답 요청이 오면 빈 공통 응답을 반환한다.")
    @Test
    public void givenNothing_whenCallingResponse_thenReturnEmptyOK() {
        ApplicationResponse<Void> response = ApplicationResponse.ok();

        // assert
        assertThat((response)).isNotNull();
        assertThat((response.getCode())).isEqualTo(HttpStatus.OK.value());
        assertThat((response.getMessage())).isEqualTo("SUCCESS");
        assertThat((response.getData())).isNull();
        assertThat((response.getLocalDateTime())).isNotNull();
    }

    @DisplayName("데이터가 있는 성공 응답 요청이 오면 데이터를 포함한 공통 응답을 반환한다.")
    @Test
    public void givenData_whenCallingResponse_thenReturnOK() {
        ApplicationResponse<String> response = ApplicationResponse.ok("test");

        // assert
        assertThat((response)).isNotNull();
        assertThat((response.getCode())).isEqualTo(HttpStatus.OK.value());
        assertThat((response.getMessage())).isEqualTo("SUCCESS");
        assertThat((response.getData())).isEqualTo("test");
        assertThat((response.getLocalDateTime())).isNotNull();
    }

    @DisplayName("예외, 오류 응답 요청이 오면 예외 공통 응답을 반환한다.")
    @Test
    public void givenError_whenCallingResponse_thenReturnError() {
        ApplicationResponse<Error> response = ApplicationResponse.error(GlobalErrorCode.BAD_REQUEST);

        // assert
        assertThat((response)).isNotNull();
        assertThat((response.getCode()))
                .isEqualTo(GlobalErrorCode.BAD_REQUEST.getStatus().value());
        assertThat((response.getMessage())).isEqualTo(GlobalErrorCode.BAD_REQUEST.getMessage());
        assertThat((response.getData())).isNull();
        assertThat((response.getLocalDateTime())).isNotNull();
    }
}
