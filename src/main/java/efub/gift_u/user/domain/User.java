package efub.gift_u.user.domain;

import efub.gift_u.friend.domain.Friend;
import efub.gift_u.funding.domain.Funding;
import efub.gift_u.notice.domain.Notice;
import efub.gift_u.participation.domain.Participation;
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

    @Column(nullable = false, unique = true, length = 16)
    private String nickname;

    @Column(updatable = false)
    private String email;

    @Column
    private Date birthday;

    @Column
    private String userImageUrl;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendsAsFirstUser = new ArrayList<>();

    @OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendsAsSecondUser = new ArrayList<>();

    @OneToMany(mappedBy = "noticeId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Notice> noticeList = new ArrayList<>();

    @OneToMany(mappedBy = "participationId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Participation> participationList = new ArrayList<>();

    @OneToMany(mappedBy = "fundingId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Funding> fundingList = new ArrayList<>();

    @Builder
    public User(String nickname, String email, Date birthday, String userImageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.birthday = birthday;
        this.userImageUrl = userImageUrl;
    }
}
