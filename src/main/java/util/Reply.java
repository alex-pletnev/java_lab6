package util;

import java.io.Serializable;

public class Reply implements Serializable {
    private String answer;

    public Reply(String answer) {
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return answer;
    }
}
