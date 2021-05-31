package hello.datajpa.repository.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
