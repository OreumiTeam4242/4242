package team.ftft.project4242.dto;

public class ScrapResponseDto {
    private boolean isScrapped;

    public ScrapResponseDto(boolean isScrapped) {
        this.isScrapped = isScrapped;
    }

    public boolean isScrapped() {
        return isScrapped;
    }

    public void setScrapped(boolean isScrapped) {
        this.isScrapped = isScrapped;
    }
}
