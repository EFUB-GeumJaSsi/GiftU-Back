package efub.gift_u.message.domain;

import efub.gift_u.global.entity.BaseTimeEntity;
import efub.gift_u.participation.domain.Participation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "participationId", nullable = false)
    private Participation participation;

    @Column(nullable = false)
    private String content;

    @Builder
    public Message(Participation participation, String content) {
        this.participation = participation;
        this.content = content;
    }
}
