package ru.jskills.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by safin.v on 07.11.2016.
 */
@Entity
@Table(name = "pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Paragraph> paragraphs = new HashSet<Paragraph>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
