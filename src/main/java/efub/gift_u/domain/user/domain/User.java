package efub.gift_u.domain.user.domain;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.participation.domain.Participation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(length = 8, unique = true) //닉네임은 중복 불가
    private String nickname;

    @Column(updatable = false)
    private String email;

    @Temporal(TemporalType.DATE) // 날짜 형식으로 지정
    @Column
    private Date birthday;

    @Column
    private String userImageUrl;

    @Column
    private String kakaoAccessToken;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendsAsFirstUser = new ArrayList<>();

    @OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendsAsSecondUser = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Participation> participationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Funding> fundingList = new ArrayList<>();

    @Builder
    public User(String nickname, String email, Date birthday, String userImageUrl, String kakaoAccessToken) {
        this.nickname = nickname;
        this.email = email;
        this.birthday = birthday;
        this.userImageUrl = userImageUrl;
        this.kakaoAccessToken = kakaoAccessToken;
    }

    // 액세스 토큰 업데이트
    public void updateKakaoAccessToken(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }

    // 회원 정보 수정
    public void updateUser(String nickname, Date birthday, String userImageUrl){
        this.nickname = nickname;
        this.birthday = birthday;
        this.userImageUrl = userImageUrl;
    }

}

