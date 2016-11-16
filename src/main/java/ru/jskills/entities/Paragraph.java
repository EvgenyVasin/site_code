package ru.jskills.entities;

import javax.persistence.*;

/**
 * Created by safin.v on 29.09.2016.
 */
@Entity
@Table(name = "paragraphs")
public class Paragraph  extends CustomCourses{


    @ManyToOne
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;



    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


    @Override
    public String toString() {
        return "Paragraph{" +
                "page=" + page +
                ", caption='" + getCaption() + '\'' +
                ", text='" + getText() + '\'' +
                '}';
    }
}
