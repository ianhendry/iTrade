package com.gracefl.tradeplus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A VideoPost.
 */
@Entity
@Table(name = "video_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VideoPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_body")
    private String postBody;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private ZonedDateTime dateAdded;

    @Column(name = "date_approved")
    private LocalDate dateApproved;

    @Lob
    @Column(name = "media")
    private byte[] media;

    @Column(name = "media_content_type")
    private String mediaContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public VideoPost postTitle(String postTitle) {
        this.postTitle = postTitle;
        return this;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public VideoPost postBody(String postBody) {
        this.postBody = postBody;
        return this;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public ZonedDateTime getDateAdded() {
        return dateAdded;
    }

    public VideoPost dateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public VideoPost dateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
        return this;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }

    public byte[] getMedia() {
        return media;
    }

    public VideoPost media(byte[] media) {
        this.media = media;
        return this;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }

    public String getMediaContentType() {
        return mediaContentType;
    }

    public VideoPost mediaContentType(String mediaContentType) {
        this.mediaContentType = mediaContentType;
        return this;
    }

    public void setMediaContentType(String mediaContentType) {
        this.mediaContentType = mediaContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoPost)) {
            return false;
        }
        return id != null && id.equals(((VideoPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoPost{" +
            "id=" + getId() +
            ", postTitle='" + getPostTitle() + "'" +
            ", postBody='" + getPostBody() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateApproved='" + getDateApproved() + "'" +
            ", media='" + getMedia() + "'" +
            ", mediaContentType='" + getMediaContentType() + "'" +
            "}";
    }
}
