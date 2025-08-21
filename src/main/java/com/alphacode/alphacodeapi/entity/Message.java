package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "conversation_id", nullable = false, columnDefinition = "uuid")
    private UUID conversationId;

    @Column(name = "sender_id", nullable = false, columnDefinition = "uuid")
    private UUID senderId;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "message_type", nullable = false)
    private Integer messageType;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    private Account sender;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private List<MessageReadStatus> messageReadStatuses;
}