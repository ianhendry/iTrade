package com.gracefl.tradeplus.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "comment_title", nullable = false)
    private String commentTitle;

    @Column(name = "comment_body")
    private String commentBody;

    @Lob
    @Column(name = "comment_media")
    private byte[] commentMedia;

    @Column(name = "comment_media_content_type")
    private String commentMediaContentType;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    @Column(name = "date_approved")
    private LocalDate dateApproved;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public Comment commentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
        return this;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public Comment commentBody(String commentBody) {
        this.commentBody = commentBody;
        return this;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public byte[] getCommentMedia() {
        return commentMedia;
    }

    public Comment commentMedia(byte[] commentMedia) {
        this.commentMedia = commentMedia;
        return this;
    }

    public void setCommentMedia(byte[] commentMedia) {
        this.commentMedia = commentMedia;
    }

    public String getCommentMediaContentType() {
        return commentMediaContentType;
    }

    public Comment commentMediaContentType(String commentMediaContentType) {
        this.commentMediaContentType = commentMediaContentType;
        return this;
    }

    public void setCommentMediaContentType(String commentMediaContentType) {
        this.commentMediaContentType = commentMediaContentType;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public Comment dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public Comment dateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
        return this;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", commentTitle='" + getCommentTitle() + "'" +
            ", commentBody='" + getCommentBody() + "'" +
            ", commentMedia='" + getCommentMedia() + "'" +
            ", commentMediaContentType='" + getCommentMediaContentType() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateApproved='" + getDateApproved() + "'" +
            "}";
    }
}
