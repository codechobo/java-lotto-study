package lotto;

import lotto.dto.WinningLottoNumberDto;
import lotto.exception.ErrorCode;
import lotto.service.LottoShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WriteValidationTest {

    private LottoShopService lottoShopService = new LottoShopService();

    @Test
    @DisplayName("유효한 당첨번호 6개를 입력하고 보너스 번호 1개를 입력하여 테스트 성공")
    void winningLottoNumbers_case1() {
        // given
        String writeLottoNumber = "1,2,3,4,5,6";
        String writeBonusNumber = "7";

        List<Integer> expectedLottoNumber = List.of(1, 2, 3, 4, 5, 6);
        int expectedBonusNumber = 7;

        // when
        WinningLottoNumberDto winningLottoNumber = lottoShopService.getWinningLottoNumber(writeLottoNumber, writeBonusNumber);

        // then
        assertThat(expectedLottoNumber).isEqualTo(winningLottoNumber.getWinningNumbers());
        assertThat(expectedBonusNumber).isEqualTo(winningLottoNumber.getBonusNumber());
    }

    @Test
    @DisplayName("중복된 당첨 로또 번호를 입력하여 IllegalArgumentException 예외 발생")
    void winningLottoNumbers_case2() {
        // given
        String writeLottoNumber = "1,2,3,4,5,5";
        String writeBonusNumber = "7";

        // when && then
        assertThatThrownBy(() -> lottoShopService.getWinningLottoNumber(writeLottoNumber, writeBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ERROR.getMessage());
    }

    @Test
    @DisplayName("2개 이상의 로또 번호를 입력하여 IllegalArgumentException 예외 발생")
    void winningLottoNumbers_case3() {
        // given
        String writeLottoNumber = "1,2,3,4,5,6";
        String writeBonusNumber = "7,1";

        // when && then
        assertThatThrownBy(() -> lottoShopService.getWinningLottoNumber(writeLottoNumber, writeBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ERROR.getMessage());
    }

    @Test
    @DisplayName("보너스 번호 입력시 번호의 범위에 벗어나 IllegalArgumentException 예외 발생")
    void winningLottoNumbers_case4() {
        // given
        String writeLottoNumber = "1,2,3,4,5,6";
        String writeBonusNumber = "46";

        // when && then
        assertThatThrownBy(() -> lottoShopService.getWinningLottoNumber(writeLottoNumber, writeBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ERROR.getMessage());
    }
}